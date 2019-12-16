package com.android.server.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.net.ConnectivityManager;
import android.net.INetworkManagementEventObserver;
import android.net.IpPrefix;
import android.net.LinkProperties;
import android.net.NetworkAgent;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.RouteInfo;
import android.net.UidRange;
import android.os.Binder;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import com.android.internal.annotations.GuardedBy;
import com.android.server.net.BaseNetworkObserver;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * create By tangshiyuan 20191024
 * add tun0 vpn network agent
 * @hide
 */
public class CootelVpn {
    private static final String NETWORKTYPE = "COOTEL_VPN";
    private static final String TAG = "CootelVpn";
    private Context mContext;
    private NetworkInfo mNetworkInfo;
    private String mInterface = "tun0";
    private NetworkAgent mNetworkAgent;
    private final Looper mLooper;
    private final NetworkCapabilities mNetworkCapabilities;

    /* list of users using this VPN. */
    @GuardedBy("this")
    private List<UidRange> mVpnUsers = null;
    private BroadcastReceiver mUserIntentReceiver = null;
    // Handle of user initiating VPN.
    private final int mUserHandle;

    public CootelVpn(Looper looper, Context context, INetworkManagementService netService, int userHandle) {
        mContext = context;
        mLooper = looper;
        mUserHandle = userHandle;
        try {
            netService.registerObserver(mObserver);
        } catch (RemoteException e) {
            Log.wtf(TAG, "Problem registering observer", e);
        }

//        if (userHandle == UserHandle.USER_OWNER) {
//            // Owner's VPN also needs to handle restricted users
//            mUserIntentReceiver = new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    final String action = intent.getAction();
//                    final int userHandle = intent.getIntExtra(Intent.EXTRA_USER_HANDLE,  UserHandle.USER_NULL);
//                    if (userHandle == UserHandle.USER_NULL) return;
//                    if (Intent.ACTION_USER_ADDED.equals(action)) {
//                        onUserAdded(userHandle);
//                    } else if (Intent.ACTION_USER_REMOVED.equals(action)) {
//                        onUserRemoved(userHandle);
//                    }
//                }
//            };
//
//            IntentFilter intentFilter = new IntentFilter();
//            intentFilter.addAction(Intent.ACTION_USER_ADDED);
//            intentFilter.addAction(Intent.ACTION_USER_REMOVED);
//            mContext.registerReceiverAsUser(mUserIntentReceiver, UserHandle.ALL, intentFilter, null, null);
//        }

        mNetworkInfo = new NetworkInfo(ConnectivityManager.TYPE_CTOOLVPN, 0, NETWORKTYPE, "");
        // TODO: Copy metered attribute and bandwidths from physical transport, b/16207332
        mNetworkCapabilities = new NetworkCapabilities();
        mNetworkCapabilities.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
//        mNetworkCapabilities.addTransportType(NetworkCapabilities.TRANSPORT_VPN);
//        mNetworkCapabilities.removeCapability(NetworkCapabilities.NET_CAPABILITY_NOT_VPN);
    }

    private void onUserAdded(int userHandle) {
        // If the user is restricted tie them to the owner's VPN
        synchronized(CootelVpn.this) {
            UserManager mgr = UserManager.get(mContext);
            UserInfo user = mgr.getUserInfo(userHandle);
            if (user.isRestricted()) {
                try {
                    addVpnUserLocked(userHandle);
                    if (mNetworkAgent != null) {
                        final List<UidRange> ranges = uidRangesForUser(userHandle);
                        mNetworkAgent.addUidRanges(ranges.toArray(new UidRange[ranges.size()]));
                    }
                } catch (Exception e) {
                    Log.wtf(TAG, "Failed to add restricted user to owner", e);
                }
            }
        }
    }

    // Note: This function adds to mVpnUsers but does not publish list to NetworkAgent.
    private void addVpnUserLocked(int userHandle) {
        if (mVpnUsers == null) {
            throw new IllegalStateException("VPN is not active");
        }
            // Add all UIDs for the user.
        mVpnUsers.add(UidRange.createForUser(userHandle));
    }

    private void onUserRemoved(int userHandle) {
        // clean up if restricted
        synchronized(CootelVpn.this) {
            UserManager mgr = UserManager.get(mContext);
            UserInfo user = mgr.getUserInfo(userHandle);
            if (user.isRestricted()) {
                try {
                    removeVpnUserLocked(userHandle);
                } catch (Exception e) {
                    Log.wtf(TAG, "Failed to remove restricted user to owner", e);
                }
            }
        }
    }

    private void removeVpnUserLocked(int userHandle) {
        if (mVpnUsers == null) {
            throw new IllegalStateException("VPN is not active");
        }
        final List<UidRange> ranges = uidRangesForUser(userHandle);
        if (mNetworkAgent != null) {
            mNetworkAgent.removeUidRanges(ranges.toArray(new UidRange[ranges.size()]));
        }
        mVpnUsers.removeAll(ranges);
    }

    // Returns the subset of the full list of active UID ranges the VPN applies to (mVpnUsers) that
    // apply to userHandle.
    private List<UidRange> uidRangesForUser(int userHandle) {
        final UidRange userRange = UidRange.createForUser(userHandle);
        final List<UidRange> ranges = new ArrayList<UidRange>();
        for (UidRange range : mVpnUsers) {
            if (range.start >= userRange.start && range.stop <= userRange.stop) {
                ranges.add(range);
            }
        }
        return ranges;
    }

    public synchronized void agentConnect() {
        if(mNetworkAgent!=null) {
            Log.d(TAG,"tsylog cootelvpn network is already create.");
            return;
        }
        mInterface = "tun0";
        mVpnUsers = new ArrayList<UidRange>();
        LinkProperties lp = new LinkProperties();
        lp.setInterfaceName(mInterface);
        try {
//            lp.addRoute(new RouteInfo(new IpPrefix(InetAddress.getByName("::"), Integer.parseInt("0")), null));
            lp.addRoute(new RouteInfo(new IpPrefix(InetAddress.getByName("0.0.0.0"), Integer.parseInt("0")), null));
            String dns = SystemProperties.get("persist.sys.mag.dns","172.16.251.77");
            lp.addDnsServer(InetAddress.getByName(dns));
        }catch (Exception e){
            Log.e(TAG,"tsylog init route and dns server error"+e.toString());
        }
//        lp.setDomains(buffer.toString().trim());

        mNetworkInfo.setIsAvailable(true);
        mNetworkInfo.setDetailedState(DetailedState.CONNECTED, null, null);

        long token = Binder.clearCallingIdentity();
        try {
            Log.d(TAG, "tsylog create vpn NetworkAgent!");
            mNetworkAgent = new NetworkAgent(mLooper, mContext, NETWORKTYPE,
                    mNetworkInfo, mNetworkCapabilities, lp, 100, null) {
                @Override
                public void unwanted() {
                    // We are user controlled, not driven by NetworkRequest.
                }
            };
        } finally {
            Binder.restoreCallingIdentity(token);
        }

//        addVpnUserLocked(mUserHandle);
//        // If we are owner assign all Restricted Users to this VPN
//        if (mUserHandle == UserHandle.USER_OWNER) {
//            token = Binder.clearCallingIdentity();
//            List<UserInfo> users;
//            try {
//                users = UserManager.get(mContext).getUsers();
//            } finally {
//                Binder.restoreCallingIdentity(token);
//            }
//            for (UserInfo user : users) {
//                if (user.isRestricted()) {
//                    addVpnUserLocked(user.id);
//                }
//            }
//        }
//        mNetworkAgent.addUidRanges(mVpnUsers.toArray(new UidRange[mVpnUsers.size()]));

    }

    private synchronized void agentDisconnect() {
        mNetworkInfo.setIsAvailable(false);
        mNetworkInfo.setDetailedState(DetailedState.DISCONNECTED, null, null);
        if (mNetworkAgent != null) {
            mNetworkAgent.sendNetworkInfo(mNetworkInfo);
        }
        mNetworkAgent = null;
        Log.d(TAG,"tsylog cootelvpn network is destroy!");
    }

    private INetworkManagementEventObserver mObserver = new BaseNetworkObserver() {

        @Override
        public void interfaceAdded(String interfaze) {
            Log.d(TAG, "tsylog interfaceAdded " + interfaze);
            synchronized (CootelVpn.this) {
//                if(interfaze.startsWith("tun0")){
//                    mInterface = interfaze;
//                    agentConnect();
//                }
            }
        }

        @Override
        public void interfaceRemoved(String interfaze) {
            Log.d(TAG, "tsylog interfaceRemoved " + interfaze);
            synchronized (CootelVpn.this) {
                if (interfaze.equals(mInterface)) {
                    mInterface = null;
                    agentDisconnect();
                }
            }
        }
    };

}

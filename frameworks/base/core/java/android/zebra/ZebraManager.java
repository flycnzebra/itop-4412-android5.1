package android.zebra;

import android.content.Context;
import android.os.RemoteException;
import android.zebra.IZebraService;
import android.util.Slog;

public class ZebraManager
{
    private static final String TAG = "ZebraManager";

    public String read(int maxLength) {
        try {
            return mService.read(maxLength);
        } catch (RemoteException e) {
            Slog.e(TAG, "read error!");
            return null;
        }
    }

    public int write(String mString) {
        try {
            return mService.write(mString);
        } catch (RemoteException e) {
            Slog.e(TAG, "write error!");
            return 0;
        }    
    }

    public ZebraManager(Context context, IZebraService service) {
        mService = service;
    }

    IZebraService mService;
}
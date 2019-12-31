package com.android.server.zebra;

import android.content.Context;
import android.os.Handler;
import android.zebra.IZebraService;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Slog;
import android.os.RemoteException;
import android.content.Intent;
import android.content.ComponentName;

public class ZebraService extends IZebraService.Stub {
    private static final String TAG = "ZebraService";
    private Context mContext;
    private int mNativePointer;

    public ZebraService(Context context) {
        super();
        mContext = context;
        Slog.i(TAG, "Zebra Service started");
        mNativePointer = init_native();
        Slog.i(TAG, "test() returns " + test_native(mNativePointer, 20));
    }

    protected void finalize() throws Throwable {
        finalize_native(mNativePointer);
        super.finalize();
    }

    public String read(int maxLength) throws RemoteException
    {
        int length;
        byte[] buffer = new byte[maxLength];
        length = read_native(mNativePointer, buffer);
        try {
            return new String(buffer, 0, length, "UTF-8");
        } catch (Exception e) {
            Slog.e(TAG, "read buffer error!");
            return null;
        }
    }

    public int write(String mString) throws RemoteException
    {
        byte[] buffer = mString.getBytes();

        return write_native(mNativePointer, buffer);
    }

    private static native int init_native();
    private static native void finalize_native(int ptr);
    private static native int read_native(int ptr, byte[] buffer);
    private static native int write_native(int ptr, byte[] buffer);
    private static native int test_native(int ptr, int value);
}
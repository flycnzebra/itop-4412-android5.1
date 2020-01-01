package android.zebra;

import android.util.Log;

/**
 * @hide
 * Created by FlyZebra on 2016/3/24.
 */
public class FlyLog {
    public static final String TAG = "ZEBRA-SYS";

    public static void v() {
        Log.v(TAG, buildLogString(""));
    }

    public static void v(String logString, Object... args) {
        Log.v(TAG, buildLogString(logString, args));
    }

    public static void vv(String TAG, String logString, Object... args) {
        Log.v(TAG, buildLogString(logString, args));
    }

    public static void d() {
        Log.d(TAG, buildLogString(""));
    }

    public static void d(String logString, Object... args) {
        Log.d(TAG, buildLogString(logString, args));
    }

    public static void dd(String TAG, String logString, Object... args) {
        Log.d(TAG, buildLogString(logString, args));
    }

    public static void i() {
        Log.i(TAG, buildLogString(""));
    }

    public static void i(String logString, Object... args) {
        Log.i(TAG, buildLogString(logString, args));
    }

    public static void ii(String TAG, String logString, Object... args) {
        Log.i(TAG, buildLogString(logString, args));
    }

    public static void w() {
        Log.w(TAG, buildLogString(""));
    }

    public static void w(String logString, Object... args) {
        Log.w(TAG, buildLogString(logString, args));
    }

    public static void ww(String TAG, String logString, Object... args) {
        Log.w(TAG, buildLogString(logString, args));
    }

    public static void e() {
        Log.e(TAG, buildLogString(""));
    }

    public static void e(String logString, Object... args) {
        Log.e(TAG, buildLogString(logString, args));
    }

    public static void ee(String TAG, String logString, Object... args) {
        Log.e(TAG, buildLogString(logString, args));
    }

    private static String buildLogString(String str, Object... args) {
        if (args.length > 0) {
            str = String.format(str, args);
        }
        //进程消息
        Thread thread = Thread.currentThread();

        //打印位置
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("[")
                .append(thread.getName())
//                .append("][")
//                .append(thread.getId())
                .append("](")
                .append(caller.getFileName())
                .append(":")
                .append(caller.getLineNumber())
                .append(")")
//                .append(caller.getMethodName())
//                .append("()")
//                .append(">>>>")
                .append(str);
//        }
        return stringBuilder.toString();
    }

}
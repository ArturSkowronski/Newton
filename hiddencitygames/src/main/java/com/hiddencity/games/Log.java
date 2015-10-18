package com.hiddencity.games;

import com.crashlytics.android.Crashlytics;

/**
 * Created by arturskowronski on 18/10/15.
 */
public class Log {


    public static void e(String tag, String msg) {
        android.util.Log.e(tag, msg);
        Crashlytics.log(android.util.Log.ERROR, tag, msg);

    }

    public static void i(String tag, String msg) {
        android.util.Log.e(tag, msg);
        Crashlytics.log(android.util.Log.INFO, tag, msg);

    }

    public static void d(String tag, String msg) {
        android.util.Log.e(tag, msg);
        Crashlytics.log(android.util.Log.DEBUG, tag, msg);

    }

    public static void d(String tag, String msg, Exception e) {
        android.util.Log.e(tag, msg + e.getMessage());
        Crashlytics.log(android.util.Log.DEBUG, tag, msg);

    }
}

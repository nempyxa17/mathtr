package com.nempyxa.mathtr.log;

import android.util.Log;

import com.nempyxa.mathtr.BuildConfig;

public class Logger {

    private static final boolean DEBUG = BuildConfig.BUILD_TYPE.equals("debug");
    private static final String TAG = "MATHTR";

    public static void d(String log) {
        if (DEBUG) {
            Log.d(TAG, log);
        }
    }

}

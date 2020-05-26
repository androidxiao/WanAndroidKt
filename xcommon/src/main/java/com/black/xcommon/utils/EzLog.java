package com.black.xcommon.utils;

import android.util.Log;

import com.black.xcommon.BuildConfig;


public class EzLog {

    public static final String TAG = "ez";

    public static void d(String s) {
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "--->" + s);
        }
    }
}

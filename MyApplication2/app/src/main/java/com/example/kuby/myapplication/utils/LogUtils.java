package com.example.kuby.myapplication.utils;

import android.util.Log;

/**
 * Created by Kuby on 2016/10/10.
 */
public class LogUtils {
    private static final String TAG = "kuby_test";

    public static void i(String msg){
        Log.i(TAG, "info: "+msg);
    }
}

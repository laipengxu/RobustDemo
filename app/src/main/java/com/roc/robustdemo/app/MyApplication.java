package com.roc.robustdemo.app;

import android.app.Application;

/**
 * Created by 赖鹏旭 on 2018/5/18.
 */
public class MyApplication extends Application {

    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static Application getInstance() {
        return mInstance;
    }
}

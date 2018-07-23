package com.nj.zddemo;

import android.app.Application;

import com.haoge.easyandroid.EasyAndroid;

/**
 * Created by Administrator on 2018-07-23.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EasyAndroid.init(this);
    }
}

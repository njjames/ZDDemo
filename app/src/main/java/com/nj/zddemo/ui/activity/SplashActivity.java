package com.nj.zddemo.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nj.zddemo.R;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {
    private static final int SHOW_LOGIN = 100;
    private static final int SHOW_GUIDE = 101;

    private static class MyHandler extends Handler {
        private final WeakReference<SplashActivity> mActivityWeakReference;

        public MyHandler(SplashActivity activity) {
            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity activity = mActivityWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case SHOW_GUIDE:
                        activity.showGuide();
                        break;
                    case SHOW_LOGIN:
                        activity.showLogin();
                        break;
                }
            }
        }
    }

    private void showLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        SharedPreferences.Editor editor = getSharedPreferences("First", MODE_PRIVATE).edit();
        editor.putBoolean("First", false);
        editor.apply();
        finish();
    }

    private void showGuide() {
        startActivity(new Intent(this, GuideActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        SharedPreferences sp = getSharedPreferences("First", MODE_PRIVATE);
        boolean firstEntrance = sp.getBoolean("FirstEntrance", true);
        MyHandler myHandler = new MyHandler(this);
        if (firstEntrance) {
            myHandler.sendEmptyMessageDelayed(SHOW_GUIDE, 1000);
        } else {
            myHandler.sendEmptyMessageDelayed(SHOW_LOGIN, 1000);
        }
    }
}

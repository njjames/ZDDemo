package com.nj.zddemo.mvp.presenter.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.nj.zddemo.mvp.view.base.MVPView;

/**
 * 基础P层
 * 用来进行P-V绑定和解绑
 * Created by Administrator on 2018-07-26.
 */

public class MVPPresenter<T extends MVPView> {
    private T mView;

    public MVPPresenter(T view) {
        attach(view);
    }

    public void attach(T view) {
        mView = view;
    }

    public void detach() {
        mView = null;
    }

    /**
     * 专门为异步回调任务所设计
     * @return
     */
    public boolean isViewAttached() {
        return mView != null;
    }

    public T getView() {
        return mView;
    }

    /**
     * 从绑定的view中去获取正确的Activity实例
     * @return
     */
    public Activity getActivity() {
        if (mView.getHostActivity() != null) {
            return mView.getHostActivity();
        } else {
            throw new RuntimeException("Could not call getActivity if the View is not attached");
        }
    }

    //声明周期相关的方法，用来和V层的生命周期方法绑定
    public void onCreate(Bundle bundle) {}
    public void onStart() {}
    public void onRestart() {}
    public void onResume() {}
    public void onPause() {}
    public void onStop() {}
    public void onActivityResult(int requestCode, int resultCode, Intent data) {}
    public void onDestroy() {
        detach();
    }
    public void onSaveInstanceState(Bundle bundle) {}
    public void onRestoreInstanceState(Bundle bundle) {}
}

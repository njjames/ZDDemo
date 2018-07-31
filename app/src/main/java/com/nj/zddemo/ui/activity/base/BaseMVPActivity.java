package com.nj.zddemo.ui.activity.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nj.zddemo.R;
import com.nj.zddemo.mvp.MVPDispatcher;
import com.nj.zddemo.mvp.presenter.base.MVPPresenter;
import com.nj.zddemo.mvp.view.base.MVPView;

import java.util.ArrayList;
import java.util.List;

/**
 * 真正的V层的基类
 * Created by Administrator on 2018-07-26.
 */

public abstract class BaseMVPActivity extends AppCompatActivity implements MVPView,View.OnClickListener {
    //一个Activity持有一个唯一的Dispatcher派发器
    private MVPDispatcher mDispatcher = new MVPDispatcher();
    private Dialog mLoadingDialog;
    private List<MVPPresenter> mPresenters = new ArrayList<>();

    //生命周期相关方法
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            setContentView(layoutId);
        }
        initPage(savedInstanceState);
        createPresenters(mPresenters);
        for (MVPPresenter presenter : mPresenters) {
            mDispatcher.addPresenter(presenter);
        }
        mDispatcher.dispatchOnCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDispatcher.dispatchOnStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDispatcher.dispatchOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDispatcher.dispatchOnPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDispatcher.dispatchOnStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mDispatcher.dispatchOnRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDispatcher.dispatchOnDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mDispatcher.dispatchOnActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mDispatcher.dispatchOnSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mDispatcher.dispatchOnRestoreInstanceState(savedInstanceState);
    }

    /**
     * 获取布局id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化页面
     * @param savedInstanceState
     */
    protected abstract void initPage(Bundle savedInstanceState);

    /**
     * 创建与子页面相关的presenter实例，可以绑定多个
     * @return
     * @param presenters
     */
    protected abstract void createPresenters(List<MVPPresenter> presenters);

    @Override
    public Activity getHostActivity() {
        return this;
    }

    @Override
    public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new Dialog(this, R.style.LoadingDialog);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            //TODO 是否不让返回，有待确定
//            mLoadingDialog.setCancelable(false);
        }
        mLoadingDialog.setContentView(R.layout.loading_dialog_layout);
        mLoadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 点击事件的回调
     * @param v
     */
    @Override
    public void onClick(View v) {}
}

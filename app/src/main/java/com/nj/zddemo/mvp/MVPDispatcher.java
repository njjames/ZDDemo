package com.nj.zddemo.mvp;

import android.content.Intent;
import android.os.Bundle;

import com.nj.zddemo.mvp.presenter.base.MVPPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * V-P连接器：实现V-P 一对多的绑定，便于管理
 * Created by Administrator on 2018-07-26.
 */

public class MVPDispatcher {
    //用来存储V层用到的所有的P层实例
    private List<MVPPresenter> mPresenters = new ArrayList<>();

    /**
     * 向集合中添加P层实例
     * 只添加已经绑定过view，并且还有添加过的实例
     * @param presenter
     */
    public void addPresenter(MVPPresenter presenter) {
        if (presenter.isViewAttached() && !mPresenters.contains(presenter)) {
            mPresenters.add(presenter);
        }
    }

    /**
     * 移除指定的Presenter实例,并将其与View解绑。
     */
    public void removePresenter(MVPPresenter presenter) {
        if (mPresenters.contains(presenter)) {
            mPresenters.remove(presenter);
            if (presenter.isViewAttached()) {
                presenter.detach();
            }
        }
    }

    //连接V-P的声明周期方法
    public void dispatchOnCreate(Bundle bundle) {
        for (MVPPresenter presenter : mPresenters) {
            if (presenter.isViewAttached()) {
                presenter.onCreate(bundle);
            }
        }
    }

    public void dispatchOnStart() {
        for (MVPPresenter presenter : mPresenters) {
            if (presenter.isViewAttached()) {
                presenter.onStart();
            }
        }
    }

    public void dispatchOnRestart() {
        for (MVPPresenter presenter : mPresenters) {
            if (presenter.isViewAttached()) {
                presenter.onRestart();
            }
        }
    }

    public void dispatchOnResume() {
        for (MVPPresenter presenter : mPresenters) {
            if (presenter.isViewAttached()) {
                presenter.onResume();
            }
        }
    }

    public void dispatchOnPause() {
        for (MVPPresenter presenter : mPresenters) {
            if (presenter.isViewAttached()) {
                presenter.onPause();
            }
        }
    }

    public void dispatchOnStop() {
        for (MVPPresenter presenter : mPresenters) {
            if (presenter.isViewAttached()) {
                presenter.onStop();
            }
        }
    }

    public void dispatchOnActivityResult(int requestCode, int resultCode, Intent data) {
        for (MVPPresenter presenter : mPresenters) {
            if (presenter.isViewAttached()) {
                presenter.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void dispatchOnDestroy() {
        for (MVPPresenter presenter : mPresenters) {
            if (presenter.isViewAttached()) {
                presenter.onDestroy();
            }
        }
    }

    public void dispatchOnSaveInstanceState(Bundle bundle) {
        for (MVPPresenter presenter : mPresenters) {
            if (presenter.isViewAttached()) {
                presenter.onSaveInstanceState(bundle);
            }
        }
    }

    public void dispatchOnRestoreInstanceState(Bundle bundle) {
        for (MVPPresenter presenter : mPresenters) {
            if (presenter.isViewAttached()) {
                presenter.onRestoreInstanceState(bundle);
            }
        }
    }

}

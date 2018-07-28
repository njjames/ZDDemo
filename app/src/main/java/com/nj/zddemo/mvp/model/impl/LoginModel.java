package com.nj.zddemo.mvp.model.impl;

import com.nj.zddemo.api.ApiManager;
import com.nj.zddemo.bean.OnlineInfo;
import com.nj.zddemo.mvp.presenter.impl.LoginPresenter;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018-07-28.
 */

public class LoginModel {

    public Observable<OnlineInfo> getMobileOnlineInfo() {
        return ApiManager.getInstance().getCommonApi().getMobileOnlineInfo();
    }
}

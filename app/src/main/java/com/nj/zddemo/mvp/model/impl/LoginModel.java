package com.nj.zddemo.mvp.model.impl;

import com.nj.zddemo.api.ApiManager;
import com.nj.zddemo.bean.LoginResult;
import com.nj.zddemo.bean.OnlineInfo;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018-07-28.
 */

public class LoginModel {

    public Observable<OnlineInfo> getMobileOnlineInfo() {
        return ApiManager.getInstance().getCommonApi().getMobileOnlineInfo();
    }

    public Observable<LoginResult> getLogin(String method, String name, String pwd, String p_number) {
        return ApiManager.getInstance().getCommonApi().getLogin(method, name, pwd, p_number);
    }

    public Observable<String> getWZZLIP(String username, String pass) {
        return ApiManager.getInstance().getCommonApi().getWZZLIP(username, pass);
    }
}

package com.nj.zddemo.mvp.presenter.impl;

import com.nj.zddemo.mvp.presenter.base.MVPPresenter;
import com.nj.zddemo.mvp.view.impl.LoginView;

/**
 * 登录功能的P层
 * Created by Administrator on 2018-07-26.
 */

public class LoginPresenter extends MVPPresenter<LoginView> {

    public LoginPresenter(LoginView view) {
        super(view);
    }


}

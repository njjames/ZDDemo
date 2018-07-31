package com.nj.zddemo.mvp.presenter.impl;

import com.nj.zddemo.bean.LoginResult;
import com.nj.zddemo.bean.OnlineInfo;
import com.nj.zddemo.mvp.model.impl.LoginModel;
import com.nj.zddemo.mvp.presenter.base.MVPPresenter;
import com.nj.zddemo.mvp.view.impl.LoginView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录功能的P层
 * Created by Administrator on 2018-07-26.
 */

public class LoginPresenter extends MVPPresenter<LoginView> {

    private LoginModel mLoginModel;

    public LoginPresenter(LoginView view) {
        super(view);
        mLoginModel = new LoginModel();
    }

    public void getMobileOnlineInfo() {
        if (!isViewAttached()) {
            return;
        }
        mLoginModel.getMobileOnlineInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OnlineInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscription(d);
                    }

                    @Override
                    public void onNext(OnlineInfo onlineInfo) {
                        if (isViewAttached()) {
                            getView().loadMobileOnlineInfo(onlineInfo);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            getView().onRequestError("数据加载失败o(╥﹏╥)o");
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getLogin(String method, String name, String pwd, String p_number) {
        if (!isViewAttached()) {
            return;
        }
        mLoginModel.getLogin(method, name, pwd, p_number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscription(d);
                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        if (isViewAttached()) {
                            getView().loadLoginResult(loginResult);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            getView().onRequestError("数据加载失败o(╥﹏╥)o");
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getWZZLIP(String username, String pass) {
        if (!isViewAttached()) {
            return;
        }
        mLoginModel.getWZZLIP(username, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscription(d);
                    }

                    @Override
                    public void onNext(String s) {
                        if (isViewAttached()) {
                            getView().loadWZZLIP(s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            getView().onRequestError("数据请求失败o(╥﹏╥)o");
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}

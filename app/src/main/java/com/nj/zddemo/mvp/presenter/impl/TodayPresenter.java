package com.nj.zddemo.mvp.presenter.impl;

import com.nj.zddemo.bean.SalesInfoByBill;
import com.nj.zddemo.bean.TodayBill;
import com.nj.zddemo.mvp.model.impl.TodayModel;
import com.nj.zddemo.mvp.presenter.base.MVPPresenter;
import com.nj.zddemo.mvp.view.impl.TodayView;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-08-02.
 */

public class TodayPresenter extends MVPPresenter<TodayView> {
    private TodayModel mTodayModel;

    public TodayPresenter(TodayView view) {
        super(view);
        mTodayModel = new TodayModel();
    }

    public void getTodayBill(String czyid) {
        if (!isViewAttached()) {
            return;
        }
        mTodayModel.getTodayXiaosh(czyid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TodayBill>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscription(d);
                    }

                    @Override
                    public void onNext(TodayBill todayBill) {
                        if (isViewAttached()) {
                            getView().loadToadyBill(todayBill);
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

    public void getSalesInfoByBill(Map<String, String> map) {
        if (!isViewAttached()) {
            return;
        }
        mTodayModel.getSalesInfoByBill(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SalesInfoByBill>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscription(d);
                    }

                    @Override
                    public void onNext(SalesInfoByBill salesInfoByBill) {
                        if (isViewAttached()) {
                            getView().loadSalesInfo(salesInfoByBill);
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
}

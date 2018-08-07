package com.nj.zddemo.mvp.presenter.impl;

import com.nj.zddemo.bean.PartInfoOfStock;
import com.nj.zddemo.mvp.model.impl.StockModel;
import com.nj.zddemo.mvp.presenter.base.MVPPresenter;
import com.nj.zddemo.mvp.view.base.MVPView;
import com.nj.zddemo.mvp.view.impl.StockView;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-08-07.
 */

public class StockPresenter extends MVPPresenter<StockView> {
    private StockModel mStockModel;

    public StockPresenter(StockView view) {
        super(view);
        mStockModel = new StockModel();
    }

    public void getPartInfoOfStock(Map<String, String> map) {
        if (!isViewAttached()) {
            return;
        }
        mStockModel.getPartInfoOfStock(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PartInfoOfStock>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscription(d);
                    }

                    @Override
                    public void onNext(PartInfoOfStock partInfoOfStock) {
                        if (isViewAttached()) {
                            getView().loadPartInfoOfStock(partInfoOfStock);
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

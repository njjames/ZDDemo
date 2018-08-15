package com.nj.zddemo.mvp.presenter.impl;

import android.text.TextUtils;

import com.nj.zddemo.bean.PartCategory;
import com.nj.zddemo.bean.PartInfoOfStock;
import com.nj.zddemo.bean.StockInfo;
import com.nj.zddemo.bean.TypeCategory;
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
                            if (!TextUtils.isEmpty(partInfoOfStock.code)) {
                                getView().onRequestPartInfoError(partInfoOfStock.msg);
                            } else {
                                getView().loadPartInfoOfStock(partInfoOfStock);
                            }
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

    public void getAllPartCatrgory() {
        if (!isViewAttached()) {
            return;
        }
        mStockModel.getAllPartCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PartCategory>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscription(d);
                    }

                    @Override
                    public void onNext(PartCategory partCategory) {
                        if (isViewAttached()) {
                            getView().loadAllPartCategory(partCategory);
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

    public void getAllTypeCategory() {
        if (!isViewAttached()) {
            return;
        }
        mStockModel.getAllTypeCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TypeCategory>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscription(d);
                    }

                    @Override
                    public void onNext(TypeCategory typeCategory) {
                        if (isViewAttached()) {
                            getView().loadAllTypeCategory(typeCategory);
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

    public void getStockInfo(String czyid, int type) {
        if (!isViewAttached()) {
            return;
        }
        mStockModel.getStockInfo(czyid, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StockInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscription(d);
                    }

                    @Override
                    public void onNext(StockInfo stockInfo) {
                        if (isViewAttached()) {
                            getView().loadStockInfo(stockInfo);
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

    public void getPartInfoOfStockPre(Map<String, String> map) {
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
                            getView().loadPartInfoOfStockPre(partInfoOfStock);
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

package com.nj.zddemo.mvp.model.impl;

import com.nj.zddemo.api.ApiManager;
import com.nj.zddemo.bean.PartCategory;
import com.nj.zddemo.bean.PartInfoOfStock;
import com.nj.zddemo.bean.StockInfo;
import com.nj.zddemo.bean.TypeCategory;

import java.security.PublicKey;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018-08-07.
 */

public class StockModel {
    public Observable<PartInfoOfStock> getPartInfoOfStock(Map<String, String> map) {
        return ApiManager.getInstance().getCommonApi().getPartInfoOfStock(map);
    }

    public Observable<PartCategory> getAllPartCategory() {
        return ApiManager.getInstance().getCommonApi().getAllPartCategory();
    }

    public Observable<TypeCategory> getAllTypeCategory() {
        return ApiManager.getInstance().getCommonApi().getAllTypeCategory();
    }

    public Observable<StockInfo> getStockInfo(String czyid, int type) {
        return ApiManager.getInstance().getCommonApi().getStockInfo(czyid, type);
    }
}

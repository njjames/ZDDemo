package com.nj.zddemo.mvp.model.impl;

import com.nj.zddemo.api.ApiManager;
import com.nj.zddemo.bean.SalesInfoByBill;
import com.nj.zddemo.bean.TodayBill;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018-08-02.
 */

public class TodayModel {

    public Observable<TodayBill> getTodayXiaosh(String cayId) {
        return ApiManager.getInstance().getCommonApi().getTodayXiaosh(cayId);
    }

    public Observable<SalesInfoByBill> getSalesInfoByBill(Map<String, String> map) {
        return ApiManager.getInstance().getCommonApi().getSalesInfoByBill(map);
    }
}

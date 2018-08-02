package com.nj.zddemo.mvp.view.impl;

import com.nj.zddemo.bean.SalesInfoByBill;
import com.nj.zddemo.bean.TodayBill;
import com.nj.zddemo.mvp.view.base.MVPView;

import java.util.List;

/**
 * Created by Administrator on 2018-08-02.
 */

public interface TodayView extends MVPView {

    void onRequestError(String msg);

    void loadToadyBill(TodayBill todayBill);

    void loadSalesInfo(SalesInfoByBill salesInfoByBill);

}

package com.nj.zddemo.mvp.view.impl;

import com.nj.zddemo.bean.PartCategory;
import com.nj.zddemo.bean.PartInfoOfStock;
import com.nj.zddemo.bean.StockInfo;
import com.nj.zddemo.bean.TypeCategory;
import com.nj.zddemo.mvp.view.base.MVPView;

/**
 * Created by Administrator on 2018-08-07.
 */

public interface StockView extends MVPView {
    /**
     * 这个方法用于所有网络请求的异常回调, 而不是code有返回值的异常
     * @param msg
     */
    void onRequestError(String msg);

    void loadPartInfoOfStock(PartInfoOfStock partInfoOfStock);

    void onRequestPartInfoError(String msg);

    void loadNextPagePartInfoOfStock(PartInfoOfStock partInfoOfStock);

    void onRequestNextPagePartInfoError(String msg);

    void loadAllPartCategory(PartCategory partCategory);

    void loadAllTypeCategory(TypeCategory typeCategory);

    void loadStockInfo(StockInfo stockInfo);
}

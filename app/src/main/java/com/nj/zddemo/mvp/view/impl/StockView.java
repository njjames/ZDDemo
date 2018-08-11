package com.nj.zddemo.mvp.view.impl;

import com.nj.zddemo.bean.PartCategory;
import com.nj.zddemo.bean.PartInfoOfStock;
import com.nj.zddemo.mvp.view.base.MVPView;

/**
 * Created by Administrator on 2018-08-07.
 */

public interface StockView extends MVPView {
    void onRequestError(String msg);

    void loadPartInfoOfStock(PartInfoOfStock partInfoOfStock);

    void loadAllPartCategory(PartCategory partCategory);
}

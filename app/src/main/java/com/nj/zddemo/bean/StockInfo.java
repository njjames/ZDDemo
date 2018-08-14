package com.nj.zddemo.bean;

import java.util.List;

/**
 * Created by Administrator on 2018-08-12.
 */

public class StockInfo {
    public String count;
    public List<RowsBean> rows;

    public static class RowsBean {
        public String cangk_dm;
        public String cangk_mc;
        public boolean isChoose;
    }
}

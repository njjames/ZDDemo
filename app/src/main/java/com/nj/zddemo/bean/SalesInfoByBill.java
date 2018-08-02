package com.nj.zddemo.bean;

import java.util.List;

/**
 * Created by nj on 2018/8/2.
 */

public class SalesInfoByBill {
    public String totalCout;
    public String code;
    public String msg;
    public List<RowsBean> rows;

    public static class RowsBean {
        public String kehu_mc;
        public String list_no;
        public String list_rq;
        public String xiao_list_ze;
    }
}

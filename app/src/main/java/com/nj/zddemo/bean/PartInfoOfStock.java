package com.nj.zddemo.bean;

import java.util.List;

/**
 * Created by Administrator on 2018-08-07.
 */

public class PartInfoOfStock {

    public String pageindex;
    public String count;
    public String totalCout;
    public List<RowsBean> rows;
    public String code;
    public String msg;

    public static class RowsBean {
        public String reco_no1;
        public String peij_no;
        public String peij_tiaoma;
        public String peij_mc;
        public String peij_th;
        public String peij_pp;
        public String peijlb_mc;
        public String peijlb_dm;
        public String peij_cx;
        public String peij_cd;
        public String jiag_x1;
        public String jg;
        public String peij_kc;
        public String pic;
    }
}

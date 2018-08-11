package com.nj.zddemo.bean;

import java.util.List;

/**
 * Created by nj on 2018/8/11.
 */

public class PartCategory {

    /**
     * thisclick :
     * count : 23
     * rows : [{"peijlb_dm":"01","peijlb_top":"","peijlb_mc":"奔驰    BENZ        ","havelow":"0"},{"peijlb_dm":"02","peijlb_top":"","peijlb_mc":"毛坯","havelow":"0"},{"peijlb_dm":"03","peijlb_top":"","peijlb_mc":"发动机","havelow":"0"},{"peijlb_dm":"04","peijlb_top":"","peijlb_mc":"油品","havelow":"1"},{"peijlb_dm":"04/01","peijlb_top":"04","peijlb_mc":"AAA","havelow":"0"},{"peijlb_dm":"04/02","peijlb_top":"04","peijlb_mc":"bbb","havelow":"0"},{"peijlb_dm":"04/03","peijlb_top":"04","peijlb_mc":"ccc","havelow":"0"},{"peijlb_dm":"04/04","peijlb_top":"04","peijlb_mc":"ddd","havelow":"0"},{"peijlb_dm":"04/05","peijlb_top":"04","peijlb_mc":"eee","havelow":"0"},{"peijlb_dm":"04/06","peijlb_top":"04","peijlb_mc":"fff","havelow":"0"},{"peijlb_dm":"04/07","peijlb_top":"04","peijlb_mc":"hhh","havelow":"0"},{"peijlb_dm":"05","peijlb_top":"","peijlb_mc":"油品","havelow":"0"},{"peijlb_dm":"06","peijlb_top":"","peijlb_mc":"废弃配件","havelow":"0"},{"peijlb_dm":"07","peijlb_top":"","peijlb_mc":"货架","havelow":"1"},{"peijlb_dm":"07/01","peijlb_top":"07","peijlb_mc":"AAAA","havelow":"0"},{"peijlb_dm":"08","peijlb_top":"","peijlb_mc":"电器","havelow":"0"},{"peijlb_dm":"09","peijlb_top":"","peijlb_mc":"电喷件","havelow":"0"},{"peijlb_dm":"10","peijlb_top":"","peijlb_mc":"拆装","havelow":"0"},{"peijlb_dm":"11","peijlb_top":"","peijlb_mc":"123","havelow":"0"},{"peijlb_dm":"12","peijlb_top":"","peijlb_mc":"其他","havelow":"0"},{"peijlb_dm":"18","peijlb_top":"","peijlb_mc":"道依茨  DEUTZ       ","havelow":"1"},{"peijlb_dm":"18/01","peijlb_top":"18","peijlb_mc":"机油格","havelow":"1"},{"peijlb_dm":"18/01/01","peijlb_top":"18/01","peijlb_mc":"888","havelow":"0"}]
     */

    private String thisclick;
    private String count;
    private List<RowsBean> rows;

    public String getThisclick() {
        return thisclick;
    }

    public void setThisclick(String thisclick) {
        this.thisclick = thisclick;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * peijlb_dm : 01
         * peijlb_top :
         * peijlb_mc : 奔驰    BENZ
         * havelow : 0
         */

        private String peijlb_dm;
        private String peijlb_top;
        private String peijlb_mc;
        private String havelow;

        public String getPeijlb_dm() {
            return peijlb_dm;
        }

        public void setPeijlb_dm(String peijlb_dm) {
            this.peijlb_dm = peijlb_dm;
        }

        public String getPeijlb_top() {
            return peijlb_top;
        }

        public void setPeijlb_top(String peijlb_top) {
            this.peijlb_top = peijlb_top;
        }

        public String getPeijlb_mc() {
            return peijlb_mc;
        }

        public void setPeijlb_mc(String peijlb_mc) {
            this.peijlb_mc = peijlb_mc;
        }

        public String getHavelow() {
            return havelow;
        }

        public void setHavelow(String havelow) {
            this.havelow = havelow;
        }
    }
}
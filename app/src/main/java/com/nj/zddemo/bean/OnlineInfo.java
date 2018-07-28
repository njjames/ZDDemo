package com.nj.zddemo.bean;

import java.util.List;

/**
 * Created by Administrator on 2018-07-28.
 */

public class OnlineInfo {
    /**
     * {
     *     "count": "1",
     *     "rows": [
     *          {
     *              "caozuoyuan_xm": "管理员",
     *              "ReportTime": "2分钟内",
     *              "ReportTime1": "2018-07-28 14:51:09"
     *          }
     *      ],
     *     "totalCout": "90",
     *     "msg": "2018-07-28 14:52:40"
     * }
     */

    public String count;
    public String totalCout;
    public String msg;
    public List<RowsBean> rows;

    public static class RowsBean {
        public String caozuoyuan_xm;
        public String ReportTime;
        public String ReportTime1;
    }

}

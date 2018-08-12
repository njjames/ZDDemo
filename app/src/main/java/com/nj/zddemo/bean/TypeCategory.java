package com.nj.zddemo.bean;

import com.nj.zddemo.ui.adapter.tree.TreeNodeId;
import com.nj.zddemo.ui.adapter.tree.TreeNodeLabel;
import com.nj.zddemo.ui.adapter.tree.TreeNodePid;

import java.util.List;

/**
 * Created by Administrator on 2018-08-12.
 */

public class TypeCategory {
    public String thisclick;
    public String count;
    public List<RowsBean> rows;

    public static class RowsBean {
        @TreeNodeId
        public String chex_dm;
        @TreeNodeLabel
        public String chex_mc;
        @TreeNodePid
        public String chex_top;
        public String havelow;
    }
}

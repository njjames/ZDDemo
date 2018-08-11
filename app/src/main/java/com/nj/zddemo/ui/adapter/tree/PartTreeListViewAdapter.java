package com.nj.zddemo.ui.adapter.tree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nj.zddemo.R;
import com.nj.zddemo.bean.PartCategory;

import java.util.List;

/**
 * Created by nj on 2018/8/11.
 */

public class PartTreeListViewAdapter extends TreeListViewAdapter<PartCategory> {

    /**
     * @param mTree
     * @param context
     * @param datas
     * @param defaultExpandLevel 默认展开几级树
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public PartTreeListViewAdapter(ListView mTree, Context context, List<PartCategory> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        super(mTree, context, datas, defaultExpandLevel);
    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.category_listview_item, parent, false);
        return view;
    }
}

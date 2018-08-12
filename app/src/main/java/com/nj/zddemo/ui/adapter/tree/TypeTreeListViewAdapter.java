package com.nj.zddemo.ui.adapter.tree;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nj.zddemo.R;
import com.nj.zddemo.bean.PartCategory;
import com.nj.zddemo.bean.TypeCategory;

import java.util.List;

/**
 * Created by nj on 2018/8/11.
 */

public class TypeTreeListViewAdapter extends TreeListViewAdapter<TypeCategory.RowsBean> {

    private ImageView mChoose;
    private TextView mCategory;

    /**
     * @param mTree
     * @param context
     * @param datas
     * @param defaultExpandLevel 默认展开几级树
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public TypeTreeListViewAdapter(ListView mTree, Context context, List<TypeCategory.RowsBean> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        super(mTree, context, datas, defaultExpandLevel);

    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent, int currentPosition) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.category_listview_item, parent, false);
        mCategory = view.findViewById(R.id.tv_category_name);
        mChoose = view.findViewById(R.id.iv_choose);
        mCategory.setText(node.getId() + ":" + node.getName());
        if (currentPosition != -1 && currentPosition == position) {
            mCategory.setTextColor(Color.parseColor("#1bd6e5"));
            mChoose.setVisibility(View.VISIBLE);
        } else {
            mCategory.setTextColor(Color.parseColor("#333333"));
            mChoose.setVisibility(View.INVISIBLE);
        }
        return view;
    }
}

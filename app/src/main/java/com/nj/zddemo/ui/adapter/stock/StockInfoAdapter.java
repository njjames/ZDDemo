package com.nj.zddemo.ui.adapter.stock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nj.zddemo.R;
import com.nj.zddemo.bean.StockInfo;

import java.util.List;

/**
 * Created by Administrator on 2018-08-12.
 */

public class StockInfoAdapter extends BaseAdapter {
    private List<StockInfo.RowsBean> mList;

    public StockInfoAdapter(List<StockInfo.RowsBean> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_gridview_item, parent, false);
        StockInfo.RowsBean rowsBean = mList.get(position);
        TextView stockName = view.findViewById(R.id.tv_stock_name);
        ImageView gvChoose = view.findViewById(R.id.iv_gv_choose);
        stockName.setText(rowsBean.cangk_mc);
        if (rowsBean.isChoose) {
            view.setBackgroundResource(R.drawable.stock_gv_press_bg);
            gvChoose.setVisibility(View.VISIBLE);
        }else {
            view.setBackgroundResource(R.drawable.stock_gv_normal_bg);
            gvChoose.setVisibility(View.INVISIBLE);
        }
        return view;
    }
}

package com.nj.zddemo.ui.adapter.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nj.zddemo.R;
import com.nj.zddemo.bean.SalesInfoByBill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nj on 2018/8/2.
 */

public class SalesInfoAdapter extends RecyclerView.Adapter<SalesInfoAdapter.ViewHolder> {
    private List<SalesInfoByBill.RowsBean> mList;

    public SalesInfoAdapter(List<SalesInfoByBill.RowsBean> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salesinfo_recyler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SalesInfoByBill.RowsBean rowsBean = mList.get(position);
        holder.tvKehuMc.setText(rowsBean.kehu_mc);
        holder.tvListNo.setText(rowsBean.list_no);
        holder.tvListRq.setText(rowsBean.list_rq);
        holder.tvListZe.setText(rowsBean.xiao_list_ze + "元");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvKehuMc;
        public TextView tvListNo;
        public TextView tvListRq;
        public TextView tvListZe;

        public ViewHolder(View itemView) {
            super(itemView);
            tvKehuMc = itemView.findViewById(R.id.tv_kehu_mc);
            tvListNo = itemView.findViewById(R.id.tv_list_no);
            tvListRq = itemView.findViewById(R.id.tv_list_rq);
            tvListZe = itemView.findViewById(R.id.tv_list_ze);
        }
    }
}

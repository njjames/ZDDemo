package com.nj.zddemo.ui.adapter.stock;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nj.zddemo.R;
import com.nj.zddemo.bean.PartInfoOfStock;
import com.nj.zddemo.ui.adapter.loadmore.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018-08-07.
 */

public class StockQueryAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<PartInfoOfStock.RowsBean> mList;
    private ConstraintSet mConstraintSet_shrink = new ConstraintSet();
    private ConstraintSet mConstraintSet_spread = new ConstraintSet();

    public StockQueryAdapter(List<PartInfoOfStock.RowsBean> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_recycler_shrink_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(parent.getContext(), view);
        //1，All children of ConstraintLayout must have ids to use ConstraintSet
        //2，控件的一些属性必须都一样，才能达到一致的效果，例如android:gravity="end"
        //3，原页面中有，第二个页面没有写的话也会显示出来
        final ConstraintLayout constraintLayout = view.findViewById(R.id.constraintlayout);
        mConstraintSet_shrink.clone(constraintLayout);
        mConstraintSet_spread.clone(parent.getContext(), R.layout.stock_recycler_spread_item);
        viewHolder.setOnClickListener(R.id.iv_zoom, new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(constraintLayout);
                mConstraintSet_spread.applyTo(constraintLayout);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PartInfoOfStock.RowsBean rowsBean = mList.get(position);
        holder.setText(R.id.tv_name, rowsBean.peij_mc);
        holder.setText(R.id.tv_no, "(" + rowsBean.peij_no + ")");
        holder.setText(R.id.tv_tp, rowsBean.peij_th);
        holder.setText(R.id.tv_type, rowsBean.peij_cx);
        holder.setText(R.id.tv_place, rowsBean.peij_cd);
        holder.setText(R.id.tv_price, rowsBean.jiag_x1);
        holder.setText(R.id.tv_stock, rowsBean.peij_kc);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

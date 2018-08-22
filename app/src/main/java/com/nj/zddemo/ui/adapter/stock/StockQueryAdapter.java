package com.nj.zddemo.ui.adapter.stock;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;

import com.nj.zddemo.R;
import com.nj.zddemo.bean.PartInfoOfStock;
import com.nj.zddemo.ui.adapter.loadmore.FooterAdapter;
import com.nj.zddemo.ui.adapter.loadmore.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018-08-07.
 */

public class StockQueryAdapter extends FooterAdapter<PartInfoOfStock.RowsBean> {
    private ConstraintSet mConstraintSet_shrink = new ConstraintSet();
    private ConstraintSet mConstraintSet_spread = new ConstraintSet();

    public StockQueryAdapter(List<PartInfoOfStock.RowsBean> datas) {
        super(datas);
    }

    @Override
    protected int getNormalLayoutId(int layoutType) {
        if (layoutType == 1) {
            return R.layout.stock_recycler_shrink_item;
        } else if (layoutType == 2) {
            return R.layout.stock_recycler_grid_item;
        } else {
            return 0;
        }
    }

    @Override
    protected int getFooterLayoutId() {
        return R.layout.footer_item_layout;
    }

    @Override
    protected void bindNormalHolder(ViewHolder holder, PartInfoOfStock.RowsBean data, int layoutType) {
        holder.setText(R.id.tv_name, data.peij_mc);
        holder.setText(R.id.tv_no, "(" + data.peij_no + ")");
        holder.setText(R.id.tv_tp, data.peij_th);
        holder.setText(R.id.tv_brand, data.peij_pp);
        holder.setText(R.id.tv_type, data.peij_cx);
        holder.setText(R.id.tv_place, data.peij_cd);
        holder.setText(R.id.tv_price, data.jiag_x1);
        holder.setText(R.id.tv_stock, data.peij_kc);
        if (layoutType == 1) {
            final ConstraintLayout constraintLayout = holder.getView(R.id.constraintlayout);
            mConstraintSet_shrink.clone(constraintLayout);
            mConstraintSet_spread.clone(holder.getContext(), R.layout.stock_recycler_spread_item);
            //        ConstraintLayout spreadConstraintLayout = (ConstraintLayout) LayoutInflater.from(holder.getContext()).inflate(R.layout.stock_recycler_spread_item, null);
            //        spreadConstraintLayout.findViewById(R.id.iv_zoom).setOnClickListener(new View.OnClickListener() {
            //            @Override
            //            public void onClick(View v) {
            //                TransitionManager.beginDelayedTransition(constraintLayout);
            //                mConstraintSet_shrink.applyTo(constraintLayout);
            //            }
            //        });
            //        mConstraintSet_spread.clone(spreadConstraintLayout);
            holder.setOnClickListener(R.id.iv_zoom, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    mConstraintSet_spread.applyTo(constraintLayout);
                }
            });
        }
    }

    @Override
    protected void bindFooterHolder(ViewHolder holder) {
        if (isHasMore()) {
            holder.setText(R.id.tv_load_tips, "正在加载更多...");
        } else {
            holder.setVisible(R.id.progressbar, false);
            holder.setText(R.id.tv_load_tips, "已显示全部商品");
        }
    }
}

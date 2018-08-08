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
import android.widget.ImageView;
import android.widget.TextView;

import com.nj.zddemo.R;
import com.nj.zddemo.bean.PartInfoOfStock;

import java.util.List;

/**
 * Created by Administrator on 2018-08-07.
 */

public class StockQueryAdapter extends RecyclerView.Adapter<StockQueryAdapter.ViewHolder> {
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
        ViewHolder viewHolder = new ViewHolder(view);
        //All children of ConstraintLayout must have ids to use ConstraintSet
        final ConstraintLayout constraintLayout = view.findViewById(R.id.constraintlayout);
        mConstraintSet_shrink.clone(constraintLayout);
        mConstraintSet_spread.clone(parent.getContext(), R.layout.stock_recycler_spread_item);
        viewHolder.mZoom.setOnClickListener(new View.OnClickListener() {
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
        holder.mName.setText(rowsBean.peij_mc);
        holder.mNo.setText("(" + rowsBean.peij_no + ")");
        holder.mTp.setText(rowsBean.peij_th);
        holder.mType.setText(rowsBean.peij_cx);
        holder.mPlace.setText(rowsBean.peij_cd);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIamge;
        private final TextView mName;
        private final TextView mNo;
        private final TextView mTp;
        private final TextView mType;
        private final TextView mPlace;
        private final TextView mPrice;
        private final TextView mStock;
        private final ImageView mZoom;

        public ViewHolder(View itemView) {
            super(itemView);
            mIamge = itemView.findViewById(R.id.iv_image);
            mName = itemView.findViewById(R.id.tv_name);
            mNo = itemView.findViewById(R.id.tv_no);
            mTp = itemView.findViewById(R.id.tv_tp);
            mType = itemView.findViewById(R.id.tv_type);
            mPlace = itemView.findViewById(R.id.tv_place);
            mPrice = itemView.findViewById(R.id.tv_price);
            mStock = itemView.findViewById(R.id.tv_stock);
            mZoom = itemView.findViewById(R.id.iv_zoom);
        }
    }
}

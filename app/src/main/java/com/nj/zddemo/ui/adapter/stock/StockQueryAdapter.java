package com.nj.zddemo.ui.adapter.stock;

import android.support.constraint.ConstraintSet;

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
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.stock_recycler_shrink_item, parent, false);
//        ViewHolder viewHolder = new ViewHolder(parent.getContext(), view);
//        //1，All children of ConstraintLayout must have ids to use ConstraintSet
//        //2，控件的一些属性必须都一样，才能达到一致的效果，例如android:gravity="end"
//        //3，原页面中有，第二个页面没有写的话也会显示出来
//        final ConstraintLayout constraintLayout = view.findViewById(R.id.constraintlayout);
//        mConstraintSet_shrink.clone(constraintLayout);
//        mConstraintSet_spread.clone(parent.getContext(), R.layout.stock_recycler_spread_item);
//        viewHolder.setOnClickListener(R.id.iv_zoom, new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onClick(View v) {
//                TransitionManager.beginDelayedTransition(constraintLayout);
//                mConstraintSet_spread.applyTo(constraintLayout);
//            }
//        });
//        return viewHolder;
//    }
    @Override
    protected int getNormalLayoutId() {
        return R.layout.stock_recycler_shrink_item;
    }

    @Override
    protected int getFooterLayoutId() {
        return R.layout.footer_item_layout;
    }

    @Override
    protected void bindNormalHolder(ViewHolder holder, PartInfoOfStock.RowsBean data) {
        holder.setText(R.id.tv_name, data.peij_mc);
        holder.setText(R.id.tv_no, "(" + data.peij_no + ")");
        holder.setText(R.id.tv_tp, data.peij_th);
        holder.setText(R.id.tv_type, data.peij_cx);
        holder.setText(R.id.tv_place, data.peij_cd);
        holder.setText(R.id.tv_price, data.jiag_x1);
        holder.setText(R.id.tv_stock, data.peij_kc);
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

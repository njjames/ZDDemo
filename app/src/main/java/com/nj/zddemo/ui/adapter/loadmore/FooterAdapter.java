package com.nj.zddemo.ui.adapter.loadmore;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 实现上拉加载的adapter
 * Created by Administrator on 2018-08-20.
 */

public abstract class FooterAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private static final int NORMAL_TYPE = 0;
    private static final int FOOTER_TYPE = 1;
    private List<T> datas;
    private boolean hasMore;

    public FooterAdapter(List<T> datas) {
        this.datas = datas;
    }

    // 如果item的位置是最后一位，则是footer，否则是正常的
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return FOOTER_TYPE;
        }
        return NORMAL_TYPE;
    }

    /**
     * 根据不同的viewType，显示不同的view
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL_TYPE) {
            return ViewHolder.createViewHolder(parent.getContext(), parent, getNormalLayoutId());
        } else {
            return ViewHolder.createViewHolder(parent.getContext(), parent, getFooterLayoutId());
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            if (datas.size() > 0) {
                holder.itemView.setVisibility(View.VISIBLE);
                bindFooterHolder(holder);
            } else {
                holder.itemView.setVisibility(View.GONE);
            }
        } else {
            bindNormalHolder(holder, datas.get(position));
        }
    }

    // item的数量是数据源集合大小+footer
    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    /**
     * 提供更新的方法，让调用者决定是否还有更多的数据
     * @param hasMore
     */
    public void updateList(boolean hasMore) {
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }

    public boolean isHasMore() {
        return hasMore;
    }

    /**
     * 正常item的布局
     * @return
     */
    protected abstract int getNormalLayoutId();

    /**
     * footeritem的布局
     * @return
     */
    protected abstract int getFooterLayoutId();

    /**
     * 用来绑定正常item的UI和事件
     * @param holder
     * @param data
     */
    protected abstract void bindNormalHolder(ViewHolder holder, T data);

    /**
     * 用来绑定footer的UI和事件
     * @param holder
     */
    protected abstract void bindFooterHolder(ViewHolder holder);

}

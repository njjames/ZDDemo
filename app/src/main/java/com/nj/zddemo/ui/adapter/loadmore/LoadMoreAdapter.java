package com.nj.zddemo.ui.adapter.loadmore;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nj on 2018/8/15.
 */

public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private RecyclerView.Adapter mInnerAdapter;
    private View mLoadMoreView;
    private int mLoadMoreLayoutId;

    public LoadMoreAdapter(RecyclerView.Adapter innerAdapter) {
        mInnerAdapter = innerAdapter;
    }

    @Override
    public int getItemViewType(int position) {
        //  如果显示加载更多的item，则itemviewtype为ITEM_TYPE_LOAD_MORE
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return super.getItemViewType(position);
    }

    /**
     * 是否显示加载更多的item
     * @param position
     * @return
     */
    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && position >= mInnerAdapter.getItemCount();
    }

    /**
     * 判断是否添加了加载更多的view或者布局文件
     * @return
     */
    private boolean hasLoadMore() {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {

        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

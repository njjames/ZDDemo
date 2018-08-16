package com.nj.zddemo.ui.adapter.loadmore;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * 装饰类，在原有的adpter基础上，增加上拉刷新的功能
 * Created by nj on 2018/8/15.
 */

public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private RecyclerView.Adapter mInnerAdapter;
    private View mLoadMoreView;
    private int mLoadMoreLayoutId;
    private OnLoadMoreListener mOnLoadMoreListener;

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
        // 如果是加载更过的类型，就显示加载更多的布局
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            ViewHolder viewHolder;
            if (mLoadMoreView != null) {
                viewHolder = ViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            } else {
                viewHolder = ViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
            }
            return viewHolder;
        }
        // 否则显示正常类型的itemview
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // 如果显示了加载更多的item，则执行我们自定义的加载更多回调接口方法
        if (isShowLoadMore(position)) {
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.onLoadMoreRequested();
            }
            return; //这里需要return，否则会执行到正常item的onBindViewHolder，此时如果没有item，那么就会报错了
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        // 如果添加了加载更多的item，则数量+1
        return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }

    // 重写这个方法来处理GridLayoutManager的情况，不处理会出现，加载更多的item不单独占一行，而是成为gridlayout的cell
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return isShowLoadMore(position) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    // 重写这个方法是开处理StaggeredGridLayoutManager的情况
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            // setFullSpan方法来设置占领全部空间，也就是如果是加载更多的item就占据全部空间
            params.setFullSpan(isShowLoadMore(holder.getLayoutPosition()));
        }
    }

    public LoadMoreAdapter setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
        return this;
    }

    public LoadMoreAdapter setLoadMoreLayoutId(int layoutId) {
        mLoadMoreLayoutId = layoutId;
        return this;
    }

    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }

    public LoadMoreAdapter setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
        return this;
    }

}

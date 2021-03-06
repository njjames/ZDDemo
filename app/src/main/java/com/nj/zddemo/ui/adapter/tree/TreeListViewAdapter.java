package com.nj.zddemo.ui.adapter.tree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * 原理就是：展开个关闭的时候更新集合，然后刷新试图，试图出再根据不同级别的类别再进行不同的缩进显示
 * Created by Administrator on 2018-08-09.
 */

public abstract class TreeListViewAdapter<T> extends BaseAdapter {

    protected Context mContext;
    /**
     * 存储所有可见的Node
     */
    protected List<Node> mNodes;
    protected LayoutInflater mInflater;
    /**
     * 存储所有的Node
     */
    protected List<Node> mAllNodes;

    // 当前点击的位置，默认为-1
    private int currentPosition = -1;

    /**
     * 点击的回调接口
     */
    private OnTreeNodeClickListener onTreeNodeClickListener;

    // 这是添加的自定义的点击事件
    public interface OnTreeNodeClickListener {
        void onClick(View view, Node node, int position);
    }

    public void setOnTreeNodeClickListener(OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    /**
     * @param mTree
     * @param context
     * @param datas
     * @param defaultExpandLevel 默认展开几级树
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public TreeListViewAdapter(ListView mTree, Context context, List<T> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        mContext = context;
        // 对所有的Node进行排序
        mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
        // 过滤出可见的Node
        mNodes = TreeHelper.filterVisibleNode(mAllNodes);
        mInflater = LayoutInflater.from(context);

        // 设置节点点击时，可以展开以及关闭；并且将ItemClick事件继续往外公布
        mTree.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                expandOrCollapse(position);
                if (onTreeNodeClickListener != null) {
                    onTreeNodeClickListener.onClick(view, mNodes.get(position), position);
                }
            }
        });
    }

    /**
     * 相应ListView的点击事件 展开或关闭某节点
     *
     * @param position
     */
    private void expandOrCollapse(int position) {
        Node n = mNodes.get(position);
        if (n != null) { // 排除传入参数错误异常
            if (!n.isLeaf()) {
                n.setExpand(!n.isExpand());
                mNodes = TreeHelper.filterVisibleNode(mAllNodes);
            }
            // 只要点击就刷新试图，用来更新点击的颜色
            notifyDataSetChanged();// 刷新视图
        }
    }

    @Override
    public int getCount() {
        return mNodes.size();
    }

    @Override
    public Object getItem(int position) {
        return mNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Node node = mNodes.get(position);
        convertView = getConvertView(node, position, convertView, parent, currentPosition);
        // 设置内边距
        convertView.setPadding(node.getLevel() * 70, 0, 0, 0);
        return convertView;
    }

    public abstract View getConvertView(Node node, int position, View convertView, ViewGroup parent, int currentPosition);

}

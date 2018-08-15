package com.nj.zddemo.ui.adapter.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nj.zddemo.R;

import java.util.List;

/**
 * Created by Administrator on 2018-08-14.
 */

public class ConditionAdapter extends RecyclerView.Adapter<ConditionAdapter.ViewHolder> {
    private List<String> mList;
    private OnClickItemListener onClickItemListener;

    public ConditionAdapter(List<String> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.condition_list_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener != null) {
                    onClickItemListener.onClickCondition(viewHolder.mCondition.getText().toString());
                }
            }
        });
        viewHolder.mLeftUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener != null) {
                    onClickItemListener.onClickLeftUp(viewHolder.mCondition.getText().toString());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mCondition.setText(mList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mLeftUp;
        private final TextView mCondition;

        public ViewHolder(View itemView) {
            super(itemView);
            mCondition = itemView.findViewById(R.id.tv_condition);
            mLeftUp = itemView.findViewById(R.id.iv_left_up);
        }
    }

    public interface OnClickItemListener {
        void onClickCondition(String condition);
        void onClickLeftUp(String condition);
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }
}

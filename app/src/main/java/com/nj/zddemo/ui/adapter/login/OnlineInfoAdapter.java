package com.nj.zddemo.ui.adapter.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nj.zddemo.R;
import com.nj.zddemo.bean.OnlineInfo;

import java.util.List;

/**
 * Created by Administrator on 2018-07-28.
 */

public class OnlineInfoAdapter extends BaseAdapter {
    private List<OnlineInfo.RowsBean> mRowsBeanList;

    public OnlineInfoAdapter(List<OnlineInfo.RowsBean> rowsBeanList) {
        mRowsBeanList = rowsBeanList;
    }

    @Override
    public int getCount() {
        return mRowsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRowsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.tv_name);
            viewHolder.time = convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(mRowsBeanList.get(position).caozuoyuan_xm);
        viewHolder.time.setText(mRowsBeanList.get(position).ReportTime1);
        return convertView;
    }

    class ViewHolder {
        public TextView name;
        public TextView time;
    }
}

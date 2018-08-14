package com.nj.zddemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.haoge.easyandroid.easy.PreferenceSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-08-14.
 */

public class SearchCondition implements Parcelable {
    //public int type;  // 查询条件的类型，也就是属于什么的查询
    public String czyid; // 输入查询条件的操作员id
    public String content; // 查询条件内容

    public SearchCondition() {
    }

    protected SearchCondition(Parcel in) {
        czyid = in.readString();
        content = in.readString();
    }

    public static final Creator<SearchCondition> CREATOR = new Creator<SearchCondition>() {
        @Override
        public SearchCondition createFromParcel(Parcel in) {
            return new SearchCondition(in);
        }

        @Override
        public SearchCondition[] newArray(int size) {
            return new SearchCondition[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(czyid);
        dest.writeString(content);
    }
}

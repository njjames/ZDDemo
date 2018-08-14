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

public class SearchCondition  {
    //public int type;  // 查询条件的类型，也就是属于什么的查询
    public String czyid; // 输入查询条件的操作员id
    public String content; // 查询条件内容

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SearchCondition that = (SearchCondition) o;

        if (!czyid.equals(that.czyid))
            return false;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        int result = czyid.hashCode();
        result = 31 * result + content.hashCode();
        return result;
    }
}

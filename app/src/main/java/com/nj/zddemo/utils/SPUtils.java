package com.nj.zddemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-08-14.
 */

public class SPUtils {

    private final SharedPreferences sp;

    public SPUtils(Context context, String spname) {
        sp = context.getSharedPreferences(spname, Context.MODE_PRIVATE);
    }

    public <T> void saveList(String key, List<T> list) {
        if (list != null && list.size() > 0) {
            Gson gson = new Gson();
            String json = gson.toJson(list);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, json);
            editor.apply();
        }
    }

    public <T> List<T> getList(String key, Type type) {
        List<T> data = new ArrayList<>();
        String json = sp.getString(key, "");
        Gson gson = new Gson();
//        data = gson.fromJson(json, new TypeToken<List<T>>() {
//        }.getType());
        data = gson.fromJson(json, type);
        return data;
    }
}

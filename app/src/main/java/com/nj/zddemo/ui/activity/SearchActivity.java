package com.nj.zddemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.haoge.easyandroid.easy.EasySharedPreferences;
import com.haoge.easyandroid.easy.EasyToast;
import com.nj.zddemo.R;
import com.nj.zddemo.bean.LoginResult;
import com.nj.zddemo.bean.SearchCondition;
import com.nj.zddemo.mvp.presenter.base.MVPPresenter;
import com.nj.zddemo.ui.activity.base.BaseMVPActivity;
import com.nj.zddemo.ui.adapter.search.ConditionAdapter;
import com.nj.zddemo.utils.ConditionUtils;
import com.nj.zddemo.utils.SPUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseMVPActivity {
    private List<SearchCondition> mAllCondition = new ArrayList<>();
    private List<String> mConditionList = new ArrayList<>();

    private EditText mCondition;
    private ImageView mScan;
    private TextView mSearch;
    private RecyclerView mRecyclerView;
    private Button mSpeaker;
    private ConditionAdapter mConditionAdapter;
    private SPUtils mSpUtils;
    private List<SearchCondition> mSavelist;

    @Override
    protected int getLayoutId() {
        return R.layout.search_activity;
    }

    @Override
    protected void initPage(Bundle savedInstanceState) {
        mCondition = findViewById(R.id.et_condition);
        mScan = findViewById(R.id.iv_scan);
        mSearch = findViewById(R.id.tv_search);
        mRecyclerView = findViewById(R.id.rv_condition);
        mSpeaker = findViewById(R.id.btn_speaker);
        mScan.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mSpeaker.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mConditionAdapter = new ConditionAdapter(mConditionList);
        mConditionAdapter.setOnClickItemListener(new ConditionAdapter.OnClickItemListener() {
            @Override
            public void onClickCondition(String condition) {

            }

            @Override
            public void onClickLeftUp(String condition) {
                mCondition.setText(condition);
            }
        });
        // 添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setAdapter(mConditionAdapter);
    }

    @Override
    protected void initData() {
        LoginResult loginResult = EasySharedPreferences.load(LoginResult.class);
        mSpUtils = new SPUtils(this, "searchcondition");
        // java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to com.nj.zddemo.bean.SearchCondition$ConditionInfo
        // 解决方法就是在调用的时候把类型确定，然后在解析的时候告诉java。
        Type type = new TypeToken<List<SearchCondition>>() {}.getType();
        mSavelist = mSpUtils.getList(ConditionUtils.STOCK_CONDITION_KEY, type);
        if (mSavelist != null) {
            for (SearchCondition searchCondition : mSavelist) {
                if (searchCondition.czyid.equals(loginResult.Id)) {
                    mConditionList.add(searchCondition.content);
                }
            }
        } else { // 这里保证点击搜索时，这个集合不为空
            mSavelist = new ArrayList<>();
        }
        mConditionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                search();
                break;
        }
    }

    private void search() {
        String conditionStr = mCondition.getText().toString();
        if (TextUtils.isEmpty(conditionStr)) {
            EasyToast.newBuilder().build().show("请输查询条件");
            return;
        }
        // 读取sp中保存的查询条件
        LoginResult loginResult = EasySharedPreferences.load(LoginResult.class);
        SearchCondition currentCondition = new SearchCondition();
        currentCondition.czyid = loginResult.Id;
        currentCondition.content = conditionStr;
        // 判断当前的查询条件中是否含有当前的查询条件，有就删除
        if (mSavelist.contains(currentCondition)) {
            mSavelist.remove(currentCondition);
        }
        //最后再加一遍
        mSavelist.add(currentCondition);
        // 保存到sp中
        mSpUtils.saveList(ConditionUtils.STOCK_CONDITION_KEY, mSavelist);
        Intent result = new Intent();
        result.putExtra(ConditionUtils.STOCK_CONDITION_KEY, conditionStr);
        setResult(ConditionUtils.STOCK_SEARCH_RESULTCODE, result);
        finish();
    }

    @Override
    protected void createPresenters(List<MVPPresenter> presenters) {

    }
}

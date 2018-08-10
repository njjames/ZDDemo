package com.nj.zddemo.ui.activity;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.haoge.easyandroid.easy.EasySharedPreferences;
import com.haoge.easyandroid.easy.EasyToast;
import com.nj.zddemo.R;
import com.nj.zddemo.api.APIConstants;
import com.nj.zddemo.bean.LoginResult;
import com.nj.zddemo.bean.PartInfoOfStock;
import com.nj.zddemo.mvp.presenter.base.MVPPresenter;
import com.nj.zddemo.mvp.presenter.impl.StockPresenter;
import com.nj.zddemo.mvp.view.impl.StockView;
import com.nj.zddemo.ui.activity.base.BaseMVPActivity;
import com.nj.zddemo.ui.adapter.stock.StockQueryAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockQueryActivity extends BaseMVPActivity implements StockView {
    private List<PartInfoOfStock.RowsBean> mList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mFilter;
    private StockPresenter mStockPresenter;
    private StockQueryAdapter mStockQueryAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.stock_query_activity;
    }

    @Override
    protected void initPage(Bundle savedInstanceState) {
        mRecyclerView = findViewById(R.id.rv_stock);
        mDrawerLayout = findViewById(R.id.drawerlayout);
        findViewById(R.id.ll_filter_drawer);
        mFilter = findViewById(R.id.ll_filter);
        mFilter.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mStockQueryAdapter = new StockQueryAdapter(mList);
        mRecyclerView.setAdapter(mStockQueryAdapter);
    }

    @Override
    protected void createPresenters(List<MVPPresenter> presenters) {
        mStockPresenter = new StockPresenter(this);
        presenters.add(mStockPresenter);
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData() {
        LoginResult loginResult = EasySharedPreferences.load(LoginResult.class);
        loginResult.Id = "16";
        Map<String, String> map = new HashMap<>();
        map.put("method", APIConstants.METHOD_GETKUCSHP);
        map.put("kuc_czyid", loginResult.Id);
        map.put("kuc_key", "");
        map.put("kuc_lb", "");
        map.put("kuc_tiaoma", "");
        map.put("kuc_cx", "");
        map.put("kuc_th", "");
        map.put("kuc_ckdm", "");
        map.put("kuc_hwone", "");
        map.put("kuc_hwtwo", "");
        map.put("pageindex", "1");
//        map.put("No_map",(new PeijianDisplayShare(LlQueryActivity.this).isImageDisplay()?"1":"0"));
        mStockPresenter.getPartInfoOfStock(map);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_filter:
//                mDrawerLayout.openDrawer();
                break;
        }
    }

    @Override
    public void onRequestError(String msg) {
        EasyToast.newBuilder().build().show(msg);
    }

    @Override
    public void loadPartInfoOfStock(PartInfoOfStock partInfoOfStock) {
        mList.clear();
        mList.addAll(partInfoOfStock.rows);
        mStockQueryAdapter.notifyDataSetChanged();
    }
}

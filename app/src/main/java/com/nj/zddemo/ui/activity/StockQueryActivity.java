package com.nj.zddemo.ui.activity;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.haoge.easyandroid.easy.EasySharedPreferences;
import com.haoge.easyandroid.easy.EasyToast;
import com.nj.zddemo.R;
import com.nj.zddemo.api.APIConstants;
import com.nj.zddemo.bean.LoginResult;
import com.nj.zddemo.bean.PartCategory;
import com.nj.zddemo.bean.PartInfoOfStock;
import com.nj.zddemo.bean.StockInfo;
import com.nj.zddemo.bean.TypeCategory;
import com.nj.zddemo.mvp.presenter.base.MVPPresenter;
import com.nj.zddemo.mvp.presenter.impl.StockPresenter;
import com.nj.zddemo.mvp.view.impl.StockView;
import com.nj.zddemo.ui.activity.base.BaseMVPActivity;
import com.nj.zddemo.ui.adapter.stock.StockInfoAdapter;
import com.nj.zddemo.ui.adapter.stock.StockQueryAdapter;
import com.nj.zddemo.ui.adapter.tree.Node;
import com.nj.zddemo.ui.adapter.tree.PartTreeListViewAdapter;
import com.nj.zddemo.ui.adapter.tree.TreeListViewAdapter;
import com.nj.zddemo.ui.adapter.tree.TypeTreeListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockQueryActivity extends BaseMVPActivity implements StockView {
    private List<PartInfoOfStock.RowsBean> mList = new ArrayList<>();
    private List<PartCategory.RowsBean> mPartCategoryList = new ArrayList<>();
    private List<TypeCategory.RowsBean> mTypeCategoryList = new ArrayList<>();
    private List<StockInfo.RowsBean> mStockShowList = new ArrayList<>(); // 用来展示的list，这个是数据源
    private List<StockInfo.RowsBean> mStockAllList = new ArrayList<>();  // 存储全部仓库的list，这个只是一个暂存的

    private RecyclerView mRecyclerView;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mFilter;
    private StockPresenter mStockPresenter;
    private StockQueryAdapter mStockQueryAdapter;
    private View mFilterDrawer;
    private View mCategoryDrawer;
    private LinearLayout mFilterDrawerAllCategory;
    private ImageView mBack;
    private ListView mCategortListView;
    private PartTreeListViewAdapter mPartTreeAdapter;
    private TypeTreeListViewAdapter mTypeTreeAdapter;
    private LinearLayout mCategoryDrawerAllCategory;
    private TextView mTopAllCategory;
    private ImageView mTopChoose;
    private TextView mCategoryConfirm;
    private PartCategory.RowsBean currentPartCategory = new PartCategory.RowsBean(); // 当前选择的类别，默认是全部类别
    private TypeCategory.RowsBean currentTypeCategory = new TypeCategory.RowsBean(); // 当前选择的类别，默认是全部类别
    private TextView mFilterDrawerPartCategory;
    private LinearLayout mAllType;
    private TextView mFilterDrawerTypeCategory;
    private GridView mStrockGridView;
    private StockInfoAdapter mStockInfoAdapter;
    private TextView mChooseStockName;
    private LinearLayout mAllStock;
    private ImageView mShowMoreStock;

    @Override
    protected int getLayoutId() {
        return R.layout.stock_query_activity;
    }

    @Override
    protected void initPage(Bundle savedInstanceState) {
        mRecyclerView = findViewById(R.id.rv_stock);
        mDrawerLayout = findViewById(R.id.drawerlayout);
        mFilterDrawer = findViewById(R.id.ll_filter_drawer);
        mCategoryDrawer = findViewById(R.id.ll_category_drawer);
        mFilterDrawerAllCategory = mFilterDrawer.findViewById(R.id.ll_show_all_category);
        mFilterDrawerPartCategory = mFilterDrawer.findViewById(R.id.tv_category_name);
        mFilterDrawerTypeCategory = mFilterDrawer.findViewById(R.id.tv_type_name);
        mAllType = mFilterDrawer.findViewById(R.id.ll_show_all_type);
        mStrockGridView = mFilterDrawer.findViewById(R.id.gv_stock);
        mChooseStockName = mFilterDrawer.findViewById(R.id.tv_choose_stock_name);
        mAllStock = mFilterDrawer.findViewById(R.id.ll_show_all_stock);
        mShowMoreStock = mFilterDrawer.findViewById(R.id.iv_showmore_stock);
        mBack = mCategoryDrawer.findViewById(R.id.iv_back);
        mCategortListView = mCategoryDrawer.findViewById(R.id.lv_category);
        mCategoryDrawerAllCategory = mCategoryDrawer.findViewById(R.id.ll_categorydrawer_all);
        mTopAllCategory = mCategoryDrawer.findViewById(R.id.tv_top_all_catrgory);
        mTopChoose = mCategoryDrawer.findViewById(R.id.iv_top_choose);
        mCategoryConfirm = mCategoryDrawer.findViewById(R.id.tv_category_confirm);
        mFilter = findViewById(R.id.ll_filter);
        mFilter.setOnClickListener(this);
        mFilterDrawerAllCategory.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mCategoryDrawerAllCategory.setOnClickListener(this);
        mCategoryConfirm.setOnClickListener(this);
        mAllType.setOnClickListener(this);
        mAllStock.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mStockQueryAdapter = new StockQueryAdapter(mList);
        mRecyclerView.setAdapter(mStockQueryAdapter);
        mStockInfoAdapter = new StockInfoAdapter(mStockShowList);
        mStrockGridView.setAdapter(mStockInfoAdapter);
        mStrockGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mChooseStockName.setText(mStockShowList.get(position).cangk_mc);
            }
        });
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
                openFilterDrawer();
                break;
            case R.id.ll_show_all_category:
                getPartCategory();
                mDrawerLayout.openDrawer(mCategoryDrawer);
                break;
            case R.id.iv_back:
                mDrawerLayout.closeDrawer(mCategoryDrawer);
                break;
            case R.id.ll_categorydrawer_all:
                mTopAllCategory.setTextColor(Color.parseColor("#1bd6e5"));
                mTopChoose.setVisibility(View.VISIBLE);
                currentPartCategory.peijlb_dm = "";
                currentPartCategory.peijlb_mc = "全部";
                currentTypeCategory.chex_dm = "";
                currentTypeCategory.chex_mc = "全部";
                getPartCategory();
                break;
            case R.id.tv_category_confirm:
                getChooseCategory();
                break;
            case R.id.ll_show_all_type:
                getTypeCategory();
                mDrawerLayout.openDrawer(mCategoryDrawer);
                break;
            case R.id.ll_show_all_stock:
                showMoreStock();
                break;
        }
    }

    private void showMoreStock() {
        // Todo 这里逻辑还是有点问题，需要改进
        if (mStockShowList.size() == 6 && mStockAllList.size() > 6) { // 当现在显示的是6个，并且全部的总数大于6个，就打开显示全部的
            mStockShowList.clear();
            mStockShowList.addAll(mStockAllList);
            mStockInfoAdapter.notifyDataSetChanged();
        }
        if (mStockShowList.size() > 6) { // 表示已经全部显示，那么点击之后只显示6个
            mStockShowList.clear();
            for (int i = 0; i < 6; i++) {
                mStockShowList.add(mStockAllList.get(i));
            }
            mStockInfoAdapter.notifyDataSetChanged();
        }

    }

    private void openFilterDrawer() {
        showLoadingDialog();
        LoginResult loginResult = EasySharedPreferences.load(LoginResult.class);
        String czyId = loginResult.Id;
        mStockPresenter.getStockInfo(czyId, 0);
        mDrawerLayout.openDrawer(mFilterDrawer);
    }

    /**
     * 从网络获取全部车型信息
     */
    private void getTypeCategory() {
        showLoadingDialog();
        mStockPresenter.getAllTypeCategory();
    }

    /**
     * 把当前的分类信息带回到filterdrawer中，并关闭categorydrawer
     */
    private void getChooseCategory() {
        mFilterDrawerPartCategory.setText(currentPartCategory.peijlb_mc);
        mFilterDrawerTypeCategory.setText(currentTypeCategory.chex_mc);
        mDrawerLayout.closeDrawer(mCategoryDrawer);
    }

    /**
     * 从网络获取全部的分类信息
     */
    private void getPartCategory() {
        showLoadingDialog();
        mStockPresenter.getAllPartCatrgory();
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

    @Override
    public void loadAllPartCategory(PartCategory partCategory) {
        mPartCategoryList.clear();
        mPartCategoryList.addAll(partCategory.rows);
        try {
            mPartTreeAdapter = new PartTreeListViewAdapter(mCategortListView, this, mPartCategoryList, 0);
            mPartTreeAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(View view, Node node, int position) {
                    mTopAllCategory.setTextColor(Color.parseColor("#333333"));
                    mTopChoose.setVisibility(View.INVISIBLE);
                    currentPartCategory.peijlb_dm = node.getId();
                    currentPartCategory.peijlb_mc = node.getName();
                }
            });
            mCategortListView.setAdapter(mPartTreeAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        hideLoadingDialog();
        // 不能notify,没有作用
        //mPartTreeAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadAllTypeCategory(TypeCategory typeCategory) {
        mTypeCategoryList.clear();
        mTypeCategoryList.addAll(typeCategory.rows);
        try {
            mTypeTreeAdapter = new TypeTreeListViewAdapter(mCategortListView, this, mTypeCategoryList, 0);
            mTypeTreeAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(View view, Node node, int position) {
                    mTopAllCategory.setTextColor(Color.parseColor("#333333"));
                    mTopChoose.setVisibility(View.INVISIBLE);
                    currentTypeCategory.chex_dm = node.getId();
                    currentTypeCategory.chex_mc = node.getName();
                }
            });
            mCategortListView.setAdapter(mTypeTreeAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        hideLoadingDialog();
    }

    @Override
    public void loadStockInfo(StockInfo stockInfo) {
        mStockShowList.clear();
        StockInfo.RowsBean rowsBean = new StockInfo.RowsBean();
        rowsBean.cangk_dm = "";
        rowsBean.cangk_mc = "全部";
        mStockShowList.add(rowsBean);
        // 如果仓库数量大于5，就只显示前5个+一个全部
        if (stockInfo.rows.size() > 5) {
            mShowMoreStock.setVisibility(View.VISIBLE);
            for (int i = 0; i < 5; i++) {
                mStockShowList.add(stockInfo.rows.get(i));
            }
        } else {
            mShowMoreStock.setVisibility(View.INVISIBLE);
            mStockShowList.addAll(stockInfo.rows);
        }
        mStockAllList.add(rowsBean);
        mStockAllList.addAll(stockInfo.rows);
        mStockInfoAdapter.notifyDataSetChanged();
        hideLoadingDialog();
    }
}

package com.nj.zddemo.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.nj.zddemo.ui.adapter.loadmore.LoadMoreAdapter;
import com.nj.zddemo.ui.adapter.stock.StockInfoAdapter;
import com.nj.zddemo.ui.adapter.stock.StockQueryAdapter;
import com.nj.zddemo.ui.adapter.tree.Node;
import com.nj.zddemo.ui.adapter.tree.PartTreeListViewAdapter;
import com.nj.zddemo.ui.adapter.tree.TreeListViewAdapter;
import com.nj.zddemo.ui.adapter.tree.TypeTreeListViewAdapter;
import com.nj.zddemo.utils.ConditionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockQueryActivity extends BaseMVPActivity implements StockView {
    private List<PartInfoOfStock.RowsBean> mList = new ArrayList<>();
    private List<PartInfoOfStock.RowsBean> mPreList = new ArrayList<>();
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
    private List<StockInfo.RowsBean> currentStockInfoList = new ArrayList<>(); // 当前选择的仓库们可以多选，默认是没有
    private TextView mFilterDrawerPartCategory;
    private LinearLayout mAllType;
    private TextView mFilterDrawerTypeCategory;
    private GridView mStrockGridView;
    private StockInfoAdapter mStockInfoAdapter;
    private TextView mChooseStockName;
    private LinearLayout mAllStock;
    private ImageView mShowMoreStock;
    private EditText mTpName;
    private EditText mLocaterBegin;
    private EditText mLocaterEnd;
    private Button mBtnRest;
    private Button mBtnConfirm;
    private RelativeLayout mSearchLayout;
    private ImageView mSpeaker;
    private TextView mCondition;
    private ImageView mScan;
    private int REQUEST_CODE = 100;
    private String mConditionKey = "";
    private String mTotalCount;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LoadMoreAdapter mStockQueryLoadMoreAdapter;
    private int mCurrentPageIndex = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.stock_query_activity;
    }

    @Override
    protected void initPage(Bundle savedInstanceState) {
        initMainView();
        initFilterDrawer();
        initCategoryDrawer();
        mFilter.setOnClickListener(this);
        mFilterDrawerAllCategory.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mCategoryDrawerAllCategory.setOnClickListener(this);
        mCategoryConfirm.setOnClickListener(this);
        mAllType.setOnClickListener(this);
        mAllStock.setOnClickListener(this);
        mBtnRest.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);
        mSearchLayout.setOnClickListener(this);
        mSpeaker.setOnClickListener(this);
        mScan.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mStockQueryAdapter = new StockQueryAdapter(mList);
        mStockQueryLoadMoreAdapter = new LoadMoreAdapter(mStockQueryAdapter)
                .setLoadMoreLayoutId(R.layout.load_more_layout)
                .setOnLoadMoreListener(new LoadMoreAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        mCurrentPageIndex++;
                        requestStockQuryData();
                    }
                });
        mRecyclerView.setAdapter(mStockQueryLoadMoreAdapter);
        mStockInfoAdapter = new StockInfoAdapter(mStockShowList);
        mStrockGridView.setAdapter(mStockInfoAdapter);
        mStrockGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView gvChoose = view.findViewById(R.id.iv_gv_choose);
                // 点击时时候判断点击的仓库是否在集合中，如果再则从集合中删除，否则添加进几个表示选中了
                StockInfo.RowsBean rowsBean = mStockShowList.get(position);
                if (currentStockInfoList.contains(rowsBean)) { // 如果包含，则删除，并把选择状态置为false
                    mStockShowList.get(position).isChoose = false;
                    currentStockInfoList.remove(rowsBean);
                } else {
                    mStockShowList.get(position).isChoose = true;
                    currentStockInfoList.add(rowsBean);
                }
                // noyify,用来更新背景，和右下角的图是否显示
                mStockInfoAdapter.notifyDataSetChanged();
                StringBuilder stockNames = new StringBuilder();
                for (StockInfo.RowsBean bean : currentStockInfoList) {
                    stockNames.append(bean.cangk_mc);
                    stockNames.append("、");
                }
                if (!stockNames.toString().equals("")) {
                    stockNames.deleteCharAt(stockNames.length() - 1);
                }
                mChooseStockName.setText(stockNames.toString());
//                preRequestStockQueryData();
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mCurrentPageIndex = 1;
                requestStockQuryData();
            }
        });
    }

    private void initMainView() {
        mRecyclerView = findViewById(R.id.rv_stock);
        mDrawerLayout = findViewById(R.id.drawerlayout);
        mFilterDrawer = findViewById(R.id.ll_filter_drawer);
        mCategoryDrawer = findViewById(R.id.ll_category_drawer);
        mSearchLayout = findViewById(R.id.rl_search);
        mSpeaker = findViewById(R.id.iv_speaker);
        mCondition = findViewById(R.id.tv_condition);
        mScan = findViewById(R.id.iv_scan);
        mSwipeRefreshLayout = findViewById(R.id.swiperefreshlayout);
        mFilter = findViewById(R.id.ll_filter);
    }

    private void initCategoryDrawer() {
        mBack = mCategoryDrawer.findViewById(R.id.iv_category_back);
        mCategortListView = mCategoryDrawer.findViewById(R.id.lv_category);
        mCategoryDrawerAllCategory = mCategoryDrawer.findViewById(R.id.ll_categorydrawer_all);
        mTopAllCategory = mCategoryDrawer.findViewById(R.id.tv_top_all_catrgory);
        mTopChoose = mCategoryDrawer.findViewById(R.id.iv_top_choose);
        mCategoryConfirm = mCategoryDrawer.findViewById(R.id.tv_category_confirm);
    }

    private void initFilterDrawer() {
        mFilterDrawerAllCategory = mFilterDrawer.findViewById(R.id.ll_show_all_category);
        mFilterDrawerPartCategory = mFilterDrawer.findViewById(R.id.tv_category_name);
        mFilterDrawerTypeCategory = mFilterDrawer.findViewById(R.id.tv_type_name);
        mAllType = mFilterDrawer.findViewById(R.id.ll_show_all_type);
        mStrockGridView = mFilterDrawer.findViewById(R.id.gv_stock);
        mChooseStockName = mFilterDrawer.findViewById(R.id.tv_choose_stock_name);
        mAllStock = mFilterDrawer.findViewById(R.id.ll_show_all_stock);
        mShowMoreStock = mFilterDrawer.findViewById(R.id.iv_showmore_stock);
        mTpName = mFilterDrawer.findViewById(R.id.et_tp_name);
        mLocaterBegin = mFilterDrawer.findViewById(R.id.et_locater_begin);
        mLocaterEnd = mFilterDrawer.findViewById(R.id.et_locater_end);
        mBtnRest = mFilterDrawer.findViewById(R.id.btn_reset);
        mBtnConfirm = mFilterDrawer.findViewById(R.id.btn_confirm);
    }

    @Override
    protected void createPresenters(List<MVPPresenter> presenters) {
        mStockPresenter = new StockPresenter(this);
        presenters.add(mStockPresenter);
    }

    @Override
    protected void initData() {
        currentPartCategory.peijlb_dm = "";
        currentPartCategory.peijlb_mc = "全部";
        currentTypeCategory.chex_dm = "";
        currentTypeCategory.chex_mc = "全部";
        showLoadingDialog();
        requestStockQuryData();
        requestStockInfoData();
    }

    private void requestStockInfoData() {
        LoginResult loginResult = EasySharedPreferences.load(LoginResult.class);
        // 把获取仓库信息的过程写在这里，而不是写在每次打开筛选菜单时，否则仓库库不会选中
        mStockPresenter.getStockInfo(loginResult.Id, 0);
    }

    private void requestStockQuryData() {
        LoginResult loginResult = EasySharedPreferences.load(LoginResult.class);
        Map<String, String> map = new HashMap<>();
        String type;
        if (TextUtils.isEmpty(currentTypeCategory.chex_dm)) {
            type = "";
        } else {
            type = currentTypeCategory.chex_mc;
        }
        StringBuilder stockNos = new StringBuilder();
        for (StockInfo.RowsBean rowsBean : currentStockInfoList) {
            stockNos.append(rowsBean.cangk_dm);
            stockNos.append(",");
        }
        if (!stockNos.toString().equals("")) {
            stockNos.deleteCharAt(stockNos.length() - 1);
        }
        map.put("method", APIConstants.METHOD_GETKUCSHP);
        map.put("kuc_czyid", loginResult.Id);
        map.put("kuc_key", mConditionKey);
        map.put("kuc_lb", currentPartCategory.peijlb_dm);
        map.put("kuc_tiaoma", "");
        map.put("kuc_cx", type);
        map.put("kuc_th", mTpName.getText().toString());
        map.put("kuc_ckdm", stockNos.toString());
        map.put("kuc_hwone", mLocaterBegin.getText().toString());
        map.put("kuc_hwtwo", mLocaterEnd.getText().toString());
        map.put("pageindex", String.valueOf(mCurrentPageIndex));
//        map.put("No_map",(new PeijianDisplayShare(LlQueryActivity.this).isImageDisplay()?"1":"0"));
        mStockPresenter.getPartInfoOfStock(map);
    }

    /**
     * 欲加载配件查询信息，其实就是查询一遍
     */
    private void preRequestStockQueryData() {
        showLoadingDialog();
        LoginResult loginResult = EasySharedPreferences.load(LoginResult.class);
        Map<String, String> map = new HashMap<>();
        String type;
        if (TextUtils.isEmpty(currentTypeCategory.chex_dm)) {
            type = "";
        } else {
            type = currentTypeCategory.chex_mc;
        }
        StringBuilder stockNos = new StringBuilder();
        for (StockInfo.RowsBean rowsBean : currentStockInfoList) {
            stockNos.append(rowsBean.cangk_dm);
            stockNos.append(",");
        }
        if (!stockNos.toString().equals("")) {
            stockNos.deleteCharAt(stockNos.length() - 1);
        }
        map.put("method", APIConstants.METHOD_GETKUCSHP);
        map.put("kuc_czyid", loginResult.Id);
        map.put("kuc_key", mConditionKey);
        map.put("kuc_lb", currentPartCategory.peijlb_dm);
        map.put("kuc_tiaoma", "");
        map.put("kuc_cx", type);
        map.put("kuc_th", mTpName.getText().toString());
        map.put("kuc_ckdm", stockNos.toString());
        map.put("kuc_hwone", mLocaterBegin.getText().toString());
        map.put("kuc_hwtwo", mLocaterEnd.getText().toString());
        map.put("pageindex", "1");
        mStockPresenter.getPartInfoOfStockPre(map);
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
            case R.id.iv_category_back:
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
            case R.id.btn_reset:
                clearAll();
                break;
            case R.id.btn_confirm:
                confirmFilter();
                break;
            case R.id.rl_search: // 输入查询条件
                Intent intent = new Intent(this, SearchActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.iv_speaker:
                break;
            case R.id.iv_scan:
                break;
        }
    }

    // 确定的时候，不在去请求网络，而是把预加载的数据传递过来
    // 还是去请求网络，不用预加载了
    private void confirmFilter() {
        showLoadingDialog();
        requestStockQuryData();
        mDrawerLayout.closeDrawer(mFilterDrawer);
//        mStockQueryAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ConditionUtils.STOCK_SEARCH_RESULTCODE) {
            mConditionKey = data.getStringExtra(ConditionUtils.STOCK_CONDITION_KEY);
            if (TextUtils.isEmpty(mConditionKey)) {
                mCondition.setText(R.string.condition_hint_text);
            } else {
                mCondition.setText(mConditionKey);
            }
            showLoadingDialog();
            requestStockQuryData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 重置
     */
    private void clearAll() {
        // UI要重置
        mFilterDrawerPartCategory.setText("");
        mFilterDrawerTypeCategory.setText("");
        mTpName.getText().clear();
        mChooseStockName.setText("");
        mLocaterBegin.getText().clear();
        mLocaterEnd.getText().clear();
        // 数据也要重置
        currentPartCategory.peijlb_dm = "";
        currentPartCategory.peijlb_mc = "全部";
        currentTypeCategory.chex_dm = "";
        currentTypeCategory.chex_mc = "全部";
        // 如果当前仓库显示多于6个，那么就收缩回去
        if (mStockShowList.size() > 6) {
            mStockShowList.clear();
            for (int i = 0; i < 6; i++) {
                mStockShowList.add(mStockAllList.get(i));
            }
            mStockInfoAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 显示更过仓库，其实是全部显示或者收缩显示
     */
    private void showMoreStock() {
        if (mStockShowList.size() == 6 && mStockAllList.size() > 6) { // 当现在显示的是6个，并且全部的总数大于6个，就打开显示全部的
            mStockShowList.clear();
            mStockShowList.addAll(mStockAllList);
            mStockInfoAdapter.notifyDataSetChanged();
        } else if (mStockShowList.size() > 6) { // 表示已经全部显示，那么点击之后只显示6个
            mStockShowList.clear();
            for (int i = 0; i < 6; i++) {
                mStockShowList.add(mStockAllList.get(i));
            }
            mStockInfoAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 打开筛选的菜单
     */
    private void openFilterDrawer() {
        mDrawerLayout.openDrawer(mFilterDrawer);
        mStockInfoAdapter.notifyDataSetChanged();
//        preRequestStockQueryData();
        // 打开菜单的时候，把总数量带过来
        mBtnConfirm.setText("确定(共" + mTotalCount + "个商品)");
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
//        preRequestStockQueryData();
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
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        hideLoadingDialog();
        EasyToast.newBuilder().build().show(msg);
    }

    @Override
    public void loadPartInfoOfStock(PartInfoOfStock partInfoOfStock) {
        // 如果是请求的是第一页数据，就清空，否则不清空（这里如果都清空的话会出现一个问题，那就是一上拉加载，就会无限加载）
        if (mCurrentPageIndex == 1) {
            mList.clear();
        }
        mList.addAll(partInfoOfStock.rows);
        mTotalCount = partInfoOfStock.totalCout;
        mStockQueryLoadMoreAdapter.notifyDataSetChanged();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        hideLoadingDialog();
    }

    @Override
    public void loadPartInfoOfStockPre(PartInfoOfStock partInfoOfStock) {
        int count= 0;
        mPreList.clear();
        if (TextUtils.isEmpty(partInfoOfStock.code)) {
            mPreList.addAll(partInfoOfStock.rows);
            count = Integer.parseInt(partInfoOfStock.totalCout);
        }
        mBtnConfirm.setText("确定(共" + count + "个商品)");
        hideLoadingDialog();
    }

    @Override
    public void onRequestPartInfoError(String msg) {
        // TODO
        // 在请求之前页数+1了，如果请求失败，页数需要减回去(这有问题)
        if (mCurrentPageIndex > 1) {
            mCurrentPageIndex--;
        }
        mList.clear();
        mTotalCount = "0";
        mStockQueryLoadMoreAdapter.notifyDataSetChanged();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        hideLoadingDialog();
        EasyToast.newBuilder().build().show(msg);
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
        mStockAllList.clear();
        mStockAllList.addAll(stockInfo.rows);
        // 如果仓库数量大于5，就只显示前5个+一个全部
        if (mStockAllList.size() > 6) {
            mShowMoreStock.setVisibility(View.VISIBLE);
            for (int i = 0; i < 6; i++) {
                mStockShowList.add(mStockAllList.get(i));
            }
        } else {
            mShowMoreStock.setVisibility(View.INVISIBLE);
            mStockShowList.addAll(mStockAllList);
        }
        mStockInfoAdapter.notifyDataSetChanged();
    }
}

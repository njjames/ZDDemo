package com.nj.zddemo.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import com.nj.zddemo.bean.FilterCondition;
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
import com.nj.zddemo.ui.adapter.stock.SpaceItemDecoration;
import com.nj.zddemo.ui.adapter.stock.StockInfoAdapter;
import com.nj.zddemo.ui.adapter.stock.StockQueryAdapter;
import com.nj.zddemo.ui.adapter.tree.Node;
import com.nj.zddemo.ui.adapter.tree.PartTreeListViewAdapter;
import com.nj.zddemo.ui.adapter.tree.TreeListViewAdapter;
import com.nj.zddemo.ui.adapter.tree.TypeTreeListViewAdapter;
import com.nj.zddemo.utils.ConditionUtils;
import com.nj.zddemo.view.DoubleDrawerLayout;

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
    private DoubleDrawerLayout mDrawerLayout;
    private LinearLayout mFilter;
    private StockPresenter mStockPresenter;
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
    private Node mCurrentPartNode;
    private Node mCurrentTypeNode;
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
    private String mTotalCount;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LoadMoreAdapter mStockQueryLoadMoreAdapter;
    private int mCurrentPageIndex = 1;
    private StockQueryAdapter mStockQueryAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private int mLastVisibleItemPosition;
    private FilterCondition mFilterCondition = new FilterCondition();
    private TextView mCategoryTitle;
    private ImageView mLayoutMode;
    private LinearLayout mSortAll;
    private int mCurrentLayoutMode = 1;
    private GridLayoutManager mGridLayoutManager;
    private LinearLayout mSortCondition;
    private ImageView mSortDown;
    private SpaceItemDecoration mSpaceItemDecoration;

    @Override
    protected int getLayoutId() {
        return R.layout.stock_query_activity;
    }

    @Override
    protected void initPage(Bundle savedInstanceState) {
        initMainView();
        initSortView();
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
    }

    private void initSortView() {
        mLayoutMode = findViewById(R.id.iv_sort_mode);
        mSortAll = findViewById(R.id.ll_sort_all);
        mSortCondition = findViewById(R.id.ll_sort_condition);
        mSortDown = findViewById(R.id.iv_sort_down);
        mLayoutMode.setOnClickListener(this);
        mSortAll.setOnClickListener(this);

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
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                requestStockQuryData();
            }
        });
        mLinearLayoutManager = new LinearLayoutManager(this);
        mGridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mStockQueryAdapter = new StockQueryAdapter(mList);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 如果滚动停止
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 并且最后显示的是footerview
                    if (mLastVisibleItemPosition == mStockQueryAdapter.getItemCount() - 1) {
                        requestNextPageStockQueryData();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mCurrentLayoutMode == 1) {
                    mLastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
                } else {
                    mLastVisibleItemPosition = mGridLayoutManager.findLastVisibleItemPosition();
                }
            }
        });
        mRecyclerView.setAdapter(mStockQueryAdapter);
    }

    private void initCategoryDrawer() {
        mBack = mCategoryDrawer.findViewById(R.id.iv_category_back);
        mCategortListView = mCategoryDrawer.findViewById(R.id.lv_category);
        mCategoryDrawerAllCategory = mCategoryDrawer.findViewById(R.id.ll_categorydrawer_all);
        mTopAllCategory = mCategoryDrawer.findViewById(R.id.tv_top_all_catrgory);
        mTopChoose = mCategoryDrawer.findViewById(R.id.iv_top_choose);
        mCategoryConfirm = mCategoryDrawer.findViewById(R.id.tv_category_confirm);
        mCategoryTitle = mCategoryDrawer.findViewById(R.id.tv_category_title);
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
        currentPartCategory.peijlb_dm = "";
        currentPartCategory.peijlb_mc = "全部";
        currentTypeCategory.chex_dm = "";
        currentTypeCategory.chex_mc = "全部";
        // 初始化筛选条件
        mFilterCondition.kuc_key = "";
        mFilterCondition.kuc_lb = "";
        mFilterCondition.kuc_lbmc = "全部";
        mFilterCondition.kuc_cxdm = "";
        mFilterCondition.kuc_cx = "";
        mFilterCondition.kuc_th = "";
        mFilterCondition.kuc_cklist = new ArrayList<>();
        mFilterCondition.kuc_ckdm = "";
        mFilterCondition.kuc_ckmc = "";
        mFilterCondition.kuc_hwone = "";
        mFilterCondition.kuc_hwtwo = "";
        showLoadingDialog();
        requestStockQuryData();
        requestStockInfoData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ConditionUtils.STOCK_SEARCH_RESULTCODE) {
            mFilterCondition.kuc_key = data.getStringExtra(ConditionUtils.STOCK_CONDITION_KEY);
            if (TextUtils.isEmpty(mFilterCondition.kuc_key)) {
                mCondition.setText(R.string.condition_hint_text);
            } else {
                mCondition.setText(mFilterCondition.kuc_key);
            }
            showLoadingDialog();
            requestStockQuryData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mCategoryDrawer)) {
            mDrawerLayout.closeDrawer(mCategoryDrawer);
        } else if (mDrawerLayout.isDrawerOpen(mFilterDrawer)) {
            mDrawerLayout.closeDrawer(mFilterDrawer);
        } else {
            super.onBackPressed();
        }
    }

    private void requestStockInfoData() {
        LoginResult loginResult = EasySharedPreferences.load(LoginResult.class);
        // 把获取仓库信息的过程写在这里，而不是写在每次打开筛选菜单时，否则仓库库不会选中
        mStockPresenter.getStockInfo(loginResult.Id, 0);
    }

    private void requestStockQuryData() {
        mCurrentPageIndex = 1;
        LoginResult loginResult = EasySharedPreferences.load(LoginResult.class);
        Map<String, String> map = new HashMap<>();
        map.put("method", APIConstants.METHOD_GETKUCSHP);
        map.put("kuc_czyid", loginResult.Id);
        map.put("kuc_key", mFilterCondition.kuc_key);
        map.put("kuc_lb", mFilterCondition.kuc_lb);
        map.put("kuc_tiaoma", "");
        map.put("kuc_cx", mFilterCondition.kuc_cx);
        map.put("kuc_th", mFilterCondition.kuc_th);
        map.put("kuc_ckdm", mFilterCondition.kuc_ckdm);
        map.put("kuc_hwone", mFilterCondition.kuc_hwone);
        map.put("kuc_hwtwo", mFilterCondition.kuc_hwtwo);
        map.put("pageindex", String.valueOf(mCurrentPageIndex));
//        map.put("No_map",(new PeijianDisplayShare(LlQueryActivity.this).isImageDisplay()?"1":"0"));
        mStockPresenter.getPartInfoOfStock(map);
    }

    /**
     * 加载下一页的数据
     */
    private void requestNextPageStockQueryData() {
        mCurrentPageIndex++;
        LoginResult loginResult = EasySharedPreferences.load(LoginResult.class);
        Map<String, String> map = new HashMap<>();
        map.put("method", APIConstants.METHOD_GETKUCSHP);
        map.put("kuc_czyid", loginResult.Id);
        map.put("kuc_key", mFilterCondition.kuc_key);
        map.put("kuc_lb", mFilterCondition.kuc_lb);
        map.put("kuc_tiaoma", "");
        map.put("kuc_cx", mFilterCondition.kuc_cx);
        map.put("kuc_th", mFilterCondition.kuc_th);
        map.put("kuc_ckdm", mFilterCondition.kuc_ckdm);
        map.put("kuc_hwone", mFilterCondition.kuc_hwone);
        map.put("kuc_hwtwo", mFilterCondition.kuc_hwtwo);
        map.put("pageindex", String.valueOf(mCurrentPageIndex));
        mStockPresenter.getNextPagePartInfoOfStock(map);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_filter:
                openFilterDrawer();
                break;
            case R.id.ll_show_all_category:
                getPartCategory();
                mCategoryTitle.setText("商品分类");
                mDrawerLayout.openDrawer(mCategoryDrawer);
                break;
            case R.id.iv_category_back:
                mDrawerLayout.closeDrawer(mCategoryDrawer);
                break;
            case R.id.ll_categorydrawer_all:
                clickAllCategory();
                break;
            case R.id.tv_category_confirm:
                getChooseCategory();
                break;
            case R.id.ll_show_all_type:
                getTypeCategory();
                mCategoryTitle.setText("车型分类");
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
            case R.id.iv_sort_mode:
                changeLayoutMode();
                break;
            case R.id.ll_sort_all:
                showSortCondition();
                break;
        }
    }

    private void showSortCondition() {
        if (mSortCondition.getVisibility() == View.GONE) {
            mSortCondition.setVisibility(View.VISIBLE);
            mSortDown.setImageResource(R.drawable.ic_arrow_drop_up_selected);
        } else {
            mSortCondition.setVisibility(View.GONE);
            mSortDown.setImageResource(R.drawable.ic_arrow_drop_down_selected);
        }
    }

    private void changeLayoutMode() {
        int firstVisiablePosition = 0;
        if (mCurrentLayoutMode == 1) {
            firstVisiablePosition = mLinearLayoutManager.findFirstVisibleItemPosition();
            mLayoutMode.setImageResource(R.drawable.ic_gride_mode);
            mCurrentLayoutMode = 2;
//            mSpaceItemDecoration = new SpaceItemDecoration(this, R.dimen.qb_px_10);
//            mRecyclerView.addItemDecoration(mSpaceItemDecoration);
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mStockQueryAdapter.switchLayoutType(2);
        } else {
            firstVisiablePosition = mGridLayoutManager.findFirstVisibleItemPosition();
            mLayoutMode.setImageResource(R.drawable.ic_linear_mode);
            mCurrentLayoutMode = 1;
//            mSpaceItemDecoration = new SpaceItemDecoration(this, 0);
//            mRecyclerView.addItemDecoration(mSpaceItemDecoration);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mStockQueryAdapter.switchLayoutType(1);
        }
        // setAdapter可以实现切换，但是切换之后总是回到第一条记录
        mRecyclerView.setAdapter(mStockQueryAdapter);
        // 再把recyclerview定位到之前记录的第一条可见的位置
        mRecyclerView.scrollToPosition(firstVisiablePosition);
        // notifyDataSetChanged对于可见的item值是刷新数据源，不会重新调用onCreateViewHolder，也就不会改变item的布局
//        mStockQueryAdapter.notifyDataSetChanged();
    }

    private void clickAllCategory() {
        mTopAllCategory.setTextColor(Color.parseColor("#1bd6e5"));
        mTopChoose.setVisibility(View.VISIBLE);
        if (mCategoryTitle.getText().toString().equals("商品分类")) {
            mCurrentPartNode.setId("");
            mCurrentPartNode.setName("全部");
        } else if (mCategoryTitle.getText().toString().equals("车型分类")){
            mCurrentTypeNode.setId("");
            mCurrentTypeNode.setName("全部");
        }
        getPartCategory();
    }

    // 确认筛选条件
    private void confirmFilter() {
        showLoadingDialog();
        // 如果是点击了确定，就把筛选条件保存到filtercondition对象中
        mFilterCondition.kuc_lb = currentPartCategory.peijlb_dm;
        mFilterCondition.kuc_lbmc = currentPartCategory.peijlb_mc;
        String type;
        if (TextUtils.isEmpty(currentTypeCategory.chex_dm)) {
            type = "";
        } else {
            type = currentTypeCategory.chex_mc;
        }
        mFilterCondition.kuc_cx = type;
        mFilterCondition.kuc_cxdm = currentTypeCategory.chex_dm;
        mFilterCondition.kuc_th = mTpName.getText().toString();
        // 把原来筛选条件的仓库清除，并把这次的筛选条件加上
        mFilterCondition.kuc_cklist.clear();
        mFilterCondition.kuc_cklist.addAll(currentStockInfoList);
        // 并算出代码和名称，代码用于查询，名称用于显示
        StringBuilder stockNos = new StringBuilder();
        StringBuilder stockMcs = new StringBuilder();
        for (StockInfo.RowsBean rowsBean : mFilterCondition.kuc_cklist) {
            stockNos.append(rowsBean.cangk_dm);
            stockNos.append(",");
            stockMcs.append(rowsBean.cangk_mc);
            stockMcs.append(",");
        }
        if (!stockNos.toString().equals("")) {
            stockNos.deleteCharAt(stockNos.length() - 1);
        }
        if (!stockMcs.toString().equals("")) {
            stockMcs.deleteCharAt(stockMcs.length() - 1);
        }
        mFilterCondition.kuc_ckdm = stockNos.toString();
        mFilterCondition.kuc_ckmc = stockMcs.toString();
        mFilterCondition.kuc_hwone = mLocaterBegin.getText().toString();
        mFilterCondition.kuc_hwtwo = mLocaterEnd.getText().toString();
        requestStockQuryData();
        mDrawerLayout.closeDrawer(mFilterDrawer);
    }

    /**
     * 重置
     */
    private void clearAll() {
        // UI要重置
        mFilterDrawerPartCategory.setText("全部");
        mFilterDrawerTypeCategory.setText("全部");
        mTpName.getText().clear();
        mChooseStockName.setText("");
        mLocaterBegin.getText().clear();
        mLocaterEnd.getText().clear();
        // 数据也要重置
        currentPartCategory.peijlb_dm = "";
        currentPartCategory.peijlb_mc = "全部";
        currentTypeCategory.chex_dm = "";
        currentTypeCategory.chex_mc = "全部";
        currentStockInfoList.clear();
        // 如果当前仓库显示多于6个，那么就收缩回去
        if (mStockShowList.size() > 6) {
            mStockShowList.clear();
            for (int i = 0; i < 6; i++) {
                mStockShowList.add(mStockAllList.get(i));
            }
        }
        // 并把所有仓库设置为没有选中
        for (StockInfo.RowsBean rowsBean : mStockShowList) {
            rowsBean.isChoose = false;
        }
        mStockInfoAdapter.notifyDataSetChanged();
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
        // 打开的时候读取当前的筛选条件到控件中
        mFilterDrawerPartCategory.setText(mFilterCondition.kuc_lbmc);
        if (TextUtils.isEmpty(mFilterCondition.kuc_cxdm)) {
            mFilterDrawerTypeCategory.setText("全部");
        } else {
            mFilterDrawerTypeCategory.setText(mFilterCondition.kuc_cx);
        }
        mTpName.setText(mFilterCondition.kuc_th);
        mLocaterBegin.setText(mFilterCondition.kuc_hwone);
        mLocaterEnd.setText(mFilterCondition.kuc_hwtwo);
        mChooseStockName.setText(mFilterCondition.kuc_ckmc);
        // 遍历展示的仓库，只要筛选条件中包含就设置为选中状态
        for (StockInfo.RowsBean rowsBean : mStockShowList) {
            rowsBean.isChoose = false;
            if (mFilterCondition.kuc_cklist.contains(rowsBean)) {
                rowsBean.isChoose = true;
            }
        }
        mStockInfoAdapter.notifyDataSetChanged();
        // 还需要把筛选条件重新赋值给临时的筛选条件，否则会有bug
        currentPartCategory.peijlb_dm = mFilterCondition.kuc_lb;
        currentPartCategory.peijlb_mc = mFilterCondition.kuc_lbmc;
        currentTypeCategory.chex_dm = mFilterCondition.kuc_cxdm;
        if (TextUtils.isEmpty(mFilterCondition.kuc_cxdm)) {
            currentTypeCategory.chex_mc = "全部";
        } else {
            currentTypeCategory.chex_mc = mFilterCondition.kuc_cx;
        }
        currentStockInfoList.clear();
        currentStockInfoList.addAll(mFilterCondition.kuc_cklist);
        mDrawerLayout.openDrawer(mFilterDrawer);
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
        if (mCategoryTitle.getText().toString().equals("商品分类")) {
            currentPartCategory.peijlb_dm = mCurrentPartNode.getId();
            currentPartCategory.peijlb_mc = mCurrentPartNode.getName();
            mFilterDrawerPartCategory.setText(currentPartCategory.peijlb_mc);
        } else if (mCategoryTitle.getText().toString().equals("车型分类")) {
            currentTypeCategory.chex_dm = mCurrentTypeNode.getId();
            currentTypeCategory.chex_mc = mCurrentTypeNode.getName();
            mFilterDrawerTypeCategory.setText(currentTypeCategory.chex_mc);
        }
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

    /**
     * 请求商品数据成功（请求的都是第一页）
     * @param partInfoOfStock
     */
    @Override
    public void loadPartInfoOfStock(PartInfoOfStock partInfoOfStock) {
        mList.clear();
        mList.addAll(partInfoOfStock.rows);
        mTotalCount = partInfoOfStock.totalCout;
        // 如果当前集合总数和总数相等，表示没有更多数据，如果小于，则表示有更多数据
        mStockQueryAdapter.updateList(mList.size() < Integer.parseInt(mTotalCount));
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        hideLoadingDialog();
    }

    /**
     * 请求商品数据失败，指的是数量为0（请求的都是第一页）
     * @param msg
     */
    @Override
    public void onRequestPartInfoError(String msg) {
        mList.clear();
        mTotalCount = "0";
        mStockQueryAdapter.updateList(false);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        hideLoadingDialog();
        EasyToast.newBuilder().build().show(msg);
    }

    /**
     * 加载下一页成功
     * @param partInfoOfStock
     */
    @Override
    public void loadNextPagePartInfoOfStock(PartInfoOfStock partInfoOfStock) {
        mList.addAll(partInfoOfStock.rows);
        mStockQueryAdapter.updateList(true); // 请求成功表示获取到了数据，就设置为有更多的数据
    }

    /**
     * 加载下一页失败（这里的失败指的是没有更多）
     * @param msg
     */
    @Override
    public void onRequestNextPagePartInfoError(String msg) {
        mCurrentPageIndex--;
        mStockQueryAdapter.updateList(false); //这里的加载失败是指没有更多数据了
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
                    // 让全部不被选中
                    mTopAllCategory.setTextColor(Color.parseColor("#333333"));
                    mTopChoose.setVisibility(View.INVISIBLE);
                    mCurrentPartNode = node;
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
                    mCurrentTypeNode = node;
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

package com.nj.zddemo.ui.activity;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.haoge.easyandroid.easy.EasySharedPreferences;
import com.haoge.easyandroid.easy.EasyToast;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nj.zddemo.R;
import com.nj.zddemo.api.APIConstants;
import com.nj.zddemo.bean.LoginResult;
import com.nj.zddemo.bean.SalesInfoByBill;
import com.nj.zddemo.bean.TodayBill;
import com.nj.zddemo.mvp.presenter.base.MVPPresenter;
import com.nj.zddemo.mvp.presenter.impl.TodayPresenter;
import com.nj.zddemo.mvp.view.impl.TodayView;
import com.nj.zddemo.ui.activity.base.BaseMVPActivity;
import com.nj.zddemo.ui.adapter.main.SalesInfoAdapter;
import com.nj.zddemo.utils.BuilderManager;
import com.nj.zddemo.utils.CalendarUtils;
import com.nj.zddemo.utils.PhoneUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseMVPActivity implements TodayView {

    private BoomMenuButton mBoomMenuButtonBill;
    private BoomMenuButton mBoomMenuButtonStats;
    private Toolbar mToolbar;
    private View mToolbarOpenLayout;
    private View mToolbarCloseLayout;
    private AppBarLayout mAppbar;
    private View mToadyContent;
    private TextView mContentCount;
    private TextView mContentMoney;
    private TextView mToobarTitle;
    private TodayPresenter mTodayPresenter;
    private List<SalesInfoByBill.RowsBean> mRowsBeans = new ArrayList<>();
    private RecyclerView mRecycleview;
    private SalesInfoAdapter mSalesInfoAdapter;
    private TextView mName;
    private TextView mGongsimc;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionMenu mFloatingActionMenu;
    private FloatingActionButton mFabMy;
    private FloatingActionButton mFabStas;
    private FloatingActionButton mFabBill;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initPage(Bundle savedInstanceState) {
        initTitle();
        initBmbView();
        initRecyclerView();
        initFalb();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_my:
                EasyToast.newBuilder().build().show("我的");
                break;
            case R.id.fab_stats:
                EasyToast.newBuilder().build().show("统计");
                break;
            case R.id.fab_bill:
                EasyToast.newBuilder().build().show("业务");
                break;
        }
    }

    private void initFalb() {
        mFloatingActionMenu = findViewById(R.id.fam);
        mFloatingActionMenu.setClosedOnTouchOutside(true);
        mFabMy = findViewById(R.id.fab_my);
        mFabStas = findViewById(R.id.fab_stats);
        mFabBill = findViewById(R.id.fab_bill);
        mFabMy.setOnClickListener(this);
        mFabStas.setOnClickListener(this);
        mFabBill.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mRecycleview = findViewById(R.id.rv_today);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycleview.setLayoutManager(linearLayoutManager);
        mSalesInfoAdapter = new SalesInfoAdapter(mRowsBeans);
        mRecycleview.setAdapter(mSalesInfoAdapter);
    }

    @Override
    protected void createPresenters(List<MVPPresenter> presenters) {
        mTodayPresenter = new TodayPresenter(this);
        presenters.add(mTodayPresenter);
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData() {
        LoginResult loginResult = EasySharedPreferences.load(LoginResult.class);
        mName.setText("操作员：" + loginResult.caozuoyuan_xm);
        mGongsimc.setText("公司：" + loginResult.GongSiMc);
        loginResult.Id = "16";
        mTodayPresenter.getTodayBill(loginResult.Id);
        Map<String, String> map = new HashMap<>();
        map.put("method", APIConstants.METHOD_GETLIST);
        map.put("ch", PhoneUtils.getImei(this));
        map.put("listCode", APIConstants.LISTCODE_XS);
        map.put("listinfo", "");
        map.put("state", APIConstants.STATE_SEARCH);
        map.put("czyid", loginResult.Id);
        map.put("pageindex", "1");
        map.put("beginDate", CalendarUtils.getToday());
        map.put("endDate", CalendarUtils.getToday());
        mTodayPresenter.getSalesInfoByBill(map);
    }

    private void initTitle() {
        mToadyContent = findViewById(R.id.today_content);
        mContentCount = mToadyContent.findViewById(R.id.tv_count);
        mContentMoney = mToadyContent.findViewById(R.id.tv_money);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbarOpenLayout = findViewById(R.id.toobar_open_layout);
        mToolbarCloseLayout = findViewById(R.id.toobar_close_layout);
        mToobarTitle = mToolbarCloseLayout.findViewById(R.id.tv_title);
        mAppbar = findViewById(R.id.appbar);
        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalScrollRange = mAppbar.getTotalScrollRange();
                int offset = Math.abs(verticalOffset);
                if (offset <= totalScrollRange/2) {
                    mToolbarOpenLayout.setVisibility(View.VISIBLE);
                    mToolbarCloseLayout.setVisibility(View.GONE);
                    mToadyContent.setVisibility(View.VISIBLE);
                    float scale = offset * 1.0f / (totalScrollRange / 2);
                    float alpha = 1 - scale;
                    mToolbarOpenLayout.setAlpha(alpha);
                    mToadyContent.setAlpha(alpha);
                } else {
                    mToolbarOpenLayout.setVisibility(View.GONE);
                    mToolbarCloseLayout.setVisibility(View.VISIBLE);
                    mToadyContent.setVisibility(View.INVISIBLE);
                    float scale = (totalScrollRange - offset) * 1.0f / (totalScrollRange / 2);
                    float alpha = 1 - scale;
                    mToolbarCloseLayout.setAlpha(alpha);
                }
            }
        });

        mName = findViewById(R.id.tv_name);
        mGongsimc = findViewById(R.id.tv_gongsimc);
        mSwipeRefreshLayout = findViewById(R.id.swiperefreshlayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                requestData();
            }
        });

    }

    private void initBmbView() {
        mBoomMenuButtonBill = findViewById(R.id.bmb_bill);
        mBoomMenuButtonBill.setShadowEffect(false);
        //设置弹出菜单按钮的样式
        mBoomMenuButtonBill.setButtonEnum(ButtonEnum.TextInsideCircle);
        //设置右下角按钮中的显示样式（点的个数和布局）
        mBoomMenuButtonBill.setPiecePlaceEnum(PiecePlaceEnum.DOT_7_3);
        //设置弹出菜单按钮样式（菜单按钮的个数和布局）
        mBoomMenuButtonBill.setButtonPlaceEnum(ButtonPlaceEnum.SC_7_3);
        //循环设置每个菜单按钮的图片和文字
        mBoomMenuButtonBill.clearBuilders();
        for (int i = 0; i < mBoomMenuButtonBill.getPiecePlaceEnum().pieceNumber(); i++) {
            TextInsideCircleButton.Builder builder = BuilderManager.getInstance().getTextInsideCircleButtonBuilder()
                    .listener(new OnBMClickListener() { //添加点击事件
                        @Override
                        public void onBoomButtonClick(int index) {
                            Toast.makeText(MainActivity.this, "您点击了" + index, Toast.LENGTH_SHORT).show();
                        }
                    });
            mBoomMenuButtonBill.addBuilder(builder);
        }
        mBoomMenuButtonStats = findViewById(R.id.bmb_stats);
        mBoomMenuButtonStats.setShadowEffect(false);
        mBoomMenuButtonStats.setButtonEnum(ButtonEnum.TextInsideCircle);
        mBoomMenuButtonStats.setPiecePlaceEnum(PiecePlaceEnum.DOT_6_2);
        mBoomMenuButtonStats.setButtonPlaceEnum(ButtonPlaceEnum.SC_6_2);
        mBoomMenuButtonStats.clearBuilders();
        for (int i = 0; i < mBoomMenuButtonStats.getPiecePlaceEnum().pieceNumber(); i++) {
            TextInsideCircleButton.Builder builder = BuilderManager.getInstance().getSquareTextInsideCircleButtonBuilder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {

                        }
                    });
            mBoomMenuButtonStats.addBuilder(builder);
        }
    }

    @Override
    public void onRequestError(String msg) {
        EasyToast.newBuilder().build().show(msg);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadToadyBill(TodayBill todayBill) {
        mContentCount.setText(todayBill.code + "单");
        mContentMoney.setText(todayBill.msg + "元");
        mToobarTitle.setText("开单数量" + todayBill.code + "单,开单金额" + todayBill.msg + "元");
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void loadSalesInfo(SalesInfoByBill salesInfoByBill) {
        if (!TextUtils.isEmpty(salesInfoByBill.code)) {
            EasyToast.newBuilder().build().show(salesInfoByBill.msg);
        } else {
            mRowsBeans.clear();
            mRowsBeans.addAll(salesInfoByBill.rows);
            mSalesInfoAdapter.notifyDataSetChanged();
        }
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}

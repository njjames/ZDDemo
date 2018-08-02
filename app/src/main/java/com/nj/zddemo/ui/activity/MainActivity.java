package com.nj.zddemo.ui.activity;

import android.support.design.widget.AppBarLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.haoge.easyandroid.easy.EasySharedPreferences;
import com.haoge.easyandroid.easy.EasyToast;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nj.zddemo.R;
import com.nj.zddemo.bean.LoginResult;
import com.nj.zddemo.bean.LoginServerInfo;
import com.nj.zddemo.bean.TodayBill;
import com.nj.zddemo.mvp.presenter.base.MVPPresenter;
import com.nj.zddemo.mvp.presenter.impl.TodayPresenter;
import com.nj.zddemo.mvp.view.impl.TodayView;
import com.nj.zddemo.ui.activity.base.BaseMVPActivity;

import java.util.List;

public class MainActivity extends BaseMVPActivity implements TodayView {

    private BoomMenuButton mBoomMenuButton;
    private Toolbar mToolbar;
    private View mToolbarOpenLayout;
    private View mToolbarCloseLayout;
    private AppBarLayout mAppbar;
    private View mToadyContent;
    private TextView mContentCount;
    private TextView mContentMoney;
    private TextView mToobarTitle;
    private TodayPresenter mTodayPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initPage(Bundle savedInstanceState) {
        initTitle();
        initBmbView();
    }

    @Override
    protected void createPresenters(List<MVPPresenter> presenters) {
        mTodayPresenter = new TodayPresenter(this);
        presenters.add(mTodayPresenter);
    }

    @Override
    protected void initData() {
        LoginResult loginResult = EasySharedPreferences.load(LoginResult.class);
        loginResult.Id = "16";
        mTodayPresenter.getTodayBill(loginResult.Id);
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
    }

    private void initBmbView() {
        mBoomMenuButton = findViewById(R.id.bmb);
        //设置弹出菜单按钮的样式
        mBoomMenuButton.setButtonEnum(ButtonEnum.TextOutsideCircle);
        //设置右下角按钮中的显示样式（点的个数和布局）
        mBoomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_7_3);
        //设置弹出菜单按钮样式（菜单按钮的个数和布局）
        mBoomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_7_3);
        //循环设置每个菜单按钮的图片和文字
        mBoomMenuButton.clearBuilders();
        for (int i = 0; i < mBoomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
//                    .rotateText(true)   //设置文字循环滚动，好像不是这个
                    .listener(new OnBMClickListener() { //添加点击事件
                        @Override
                        public void onBoomButtonClick(int index) {
                            Toast.makeText(MainActivity.this, "您点击了" + index, Toast.LENGTH_SHORT).show();
                        }
                    });
            switch (i) {
                case 0:
                    builder.normalImageRes(R.drawable.icon_sales_page).normalTextRes(R.string.sale_name);
                    break;
                case 1:
                    builder.normalImageRes(R.drawable.icon_salessearch).normalTextRes(R.string.sale_search_name);
                    break;
                case 2:
                    builder.normalImageRes(R.drawable.icon_partsmanager).normalTextRes(R.string.part_name);
                    break;
                case 3:
                    builder.normalImageRes(R.drawable.icon_housesearch).normalTextRes(R.string.house_name);
                    break;
                case 4:
                    builder.normalImageRes(R.drawable.icon_pan_page).normalTextRes(R.string.pan_name);
                    break;
                case 5:
                    builder.normalImageRes(R.drawable.icon_clintmanager).normalTextRes(R.string.clint_name);
                    break;
                case 6:
                    builder.normalImageRes(R.drawable.icon_supplymanager).normalTextRes(R.string.supply_name);
                    break;
            }
            mBoomMenuButton.addBuilder(builder);
        }
    }

    @Override
    public void onRequestError(String msg) {
        EasyToast.newBuilder().build().show(msg);
    }

    @Override
    public void loadToadyBill(TodayBill todayBill) {
        mContentCount.setText(todayBill.code + "单");
        mContentMoney.setText(todayBill.msg + "元");
        mToobarTitle.setText("开单数量" + todayBill.code + "单,开单金额" + todayBill.msg + "元");
    }
}

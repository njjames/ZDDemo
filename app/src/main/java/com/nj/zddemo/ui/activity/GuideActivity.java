package com.nj.zddemo.ui.activity;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nj.zddemo.R;
import com.nj.zddemo.mvp.presenter.base.MVPPresenter;
import com.nj.zddemo.ui.activity.base.BaseMVPActivity;
import com.nj.zddemo.ui.adapter.SplashPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseMVPActivity {

    private ViewPager mViewPager;
    private LinearLayout mIndicator;
    private static final int GUIDE_COUNT = 3;
    private List<ImageView> mImageViewList = new ArrayList<>();
    private ImageView mImmeditate;
    private AnimatorSet mAnimatorSet;
    private ScaleAnimation mAnimation;


    @Override
    protected int getLayoutId() {
        return R.layout.guide_activity;
    }

    @Override
    protected void initPage(Bundle savedInstanceState) {
        mViewPager = findViewById(R.id.vp_guide);
        mIndicator = findViewById(R.id.ll_indicator);
        mImmeditate = findViewById(R.id.iv_immeditate);
        //给 立即体验 按钮添加点击事件
        mImmeditate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
            }
        });
        initImmeditateAnimator();
        initIndicator();
        initViewPager();
    }

    @Override
    protected void createPresenters(List<MVPPresenter> presenters) {
    }

    /**
     * 初始化立即体验按钮的动画
     */
    private void initImmeditateAnimator() {
        mAnimation = new ScaleAnimation(0.8f, 1.2f, 0.8f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setDuration(750);
        mAnimation.setRepeatMode(Animation.REVERSE); // 放大并缩小
        mAnimation.setRepeatCount(Animation.INFINITE); // 无限循环
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        initImageData();
        SplashPagerAdapter pagerAdapter = new SplashPagerAdapter(mImageViewList);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mIndicator.getChildCount(); i++) {
                    if (position == i) {
                        mIndicator.getChildAt(i).setBackgroundResource(R.drawable.ic_guide_pount_selected);
                    } else {
                        mIndicator.getChildAt(i).setBackgroundResource(R.drawable.ic_guide_pount_normal);
                    }
                }
                if (position == mImageViewList.size() - 1) {
                    mImmeditate.setVisibility(View.VISIBLE);
                    //切换到最后一页的时候才设置动画，如果一开始就设置上，这个按钮就是可见的
                    mImmeditate.setAnimation(mAnimation);
                    mImmeditate.startAnimation(mImmeditate.getAnimation());
                } else {
                    mImmeditate.setVisibility(View.INVISIBLE);
                    mImmeditate.clearAnimation();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化viewpager的图片数据
     */
    private void initImageData() {
        ImageView imageView1 = new ImageView(this);
        ImageView imageView2 = new ImageView(this);
        ImageView imageView3 = new ImageView(this);
        imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView3.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView1.setImageResource(R.drawable.guide_bg1);
        imageView2.setImageResource(R.drawable.guide_bg2);
        imageView3.setImageResource(R.drawable.guide_bg3);
        mImageViewList.add(imageView1);
        mImageViewList.add(imageView2);
        mImageViewList.add(imageView3);
    }

    /**
     * 初始化点指示器
     */
    private void initIndicator() {
        for (int i = 0; i < GUIDE_COUNT; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 15;
            layoutParams.rightMargin = 15;
            imageView.setLayoutParams(layoutParams);
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.ic_guide_pount_selected);
            } else {
                imageView.setBackgroundResource(R.drawable.ic_guide_pount_normal);
            }
            mIndicator.addView(imageView);
        }
    }
}

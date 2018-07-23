package com.nj.zddemo.ui.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nj.zddemo.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private LinearLayout mIndicator;
    private static final int GUIDE_COUNT = 3;
    private List<ImageView> mImageViewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_activity);
        initView();
        initData();
    }

    private void initData() {
        ImageView imageView1 = new ImageView(this);
        imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView1.setImageResource(R.drawable.v0);
        mImageViewList.add(imageView1);
    }

    private void initView() {
        mViewPager = findViewById(R.id.vp_guide);
        mIndicator = findViewById(R.id.ll_indicator);
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

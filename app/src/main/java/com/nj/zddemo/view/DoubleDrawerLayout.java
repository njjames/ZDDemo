package com.nj.zddemo.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * 双层Drawer的DrawerLayout
 * Created by nj on 2018/8/10.
 */

public class DoubleDrawerLayout extends DrawerLayout {
    // mRightBelowView为第一层Drawer
    private View mRightBelowView;
    // mRightAboveView为第二层Drawer
    private View mRightAboveView;
    private int mTouchSlop;
    private float mInitialMotionX;
    private float mInitialMotionY;

    public DoubleDrawerLayout(Context context) {
        super(context);
    }

    public DoubleDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DoubleDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } catch (IllegalStateException e) {
            final int childCount = getChildCount();
            final View child = getChildAt(childCount - 1);
            final float density = getResources().getDisplayMetrics().density;
            int minDrawerMargin = (int) (64 * density + 0.5f);
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            final int drawerWidthSpec =
                    getChildMeasureSpec(widthMeasureSpec, minDrawerMargin + lp.leftMargin + lp.rightMargin, lp.width);
            final int drawerHeightSpec =
                    getChildMeasureSpec(heightMeasureSpec, lp.topMargin + lp.bottomMargin, lp.height);
            child.measure(drawerWidthSpec, drawerHeightSpec);
            mRightBelowView = getChildAt(childCount - 2);
            mRightAboveView = getChildAt(childCount - 1);
            mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // 记录坐标参数
                mInitialMotionX = ev.getX();
                mInitialMotionY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                final float x = ev.getX();
                final float y = ev.getY();
                if (x < mRightBelowView.getLeft()) {
                    // 判断点击的是阴影区域
                    final float dx = x - mInitialMotionX;
                    final float dy = y - mInitialMotionY;
                    final int slop = mTouchSlop;
                    if (dx * dx + dy * dy < slop * slop) { // 判断不是滑动
                        // 当第二层Drawer没有打开而第一层Drawer打开时, 收起第一层Drawer
                        if (!isDrawerOpen(mRightAboveView) && isDrawerOpen(mRightBelowView)) {
                            closeDrawer(mRightBelowView);
                            // 直接返回不执行默认代码
                            return true;
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}

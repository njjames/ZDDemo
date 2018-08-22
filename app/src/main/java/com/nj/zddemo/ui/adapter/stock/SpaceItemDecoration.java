package com.nj.zddemo.ui.adapter.stock;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018-08-22.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceItemDecoration(Context context, int space) {
        final float scale = context.getResources().getDisplayMetrics().density;
        this.space = (int) (space * scale + 0.5f);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildLayoutPosition(view) % 2 == 0) {
            outRect.left = 0;
        } else {
            outRect.left = space;
        }
        outRect.bottom = space;
//        super.getItemOffsets(outRect, view, parent, state);
    }
}

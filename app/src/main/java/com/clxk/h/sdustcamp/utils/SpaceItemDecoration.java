package com.clxk.h.sdustcamp.utils;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecycleView加divideHeight
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int mheight;
    private Paint mpaint;

    public SpaceItemDecoration(int mheight) {
        this.mheight = mheight;
        mpaint = new Paint();
        mpaint.setColor(Color.parseColor("#f0eef4"));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
            outRect.left = mheight;
            outRect.right = mheight;
            outRect.bottom = mheight;
            if(parent.getChildAdapterPosition(view) == 0) {
                outRect.top = mheight;
            }
    }
}

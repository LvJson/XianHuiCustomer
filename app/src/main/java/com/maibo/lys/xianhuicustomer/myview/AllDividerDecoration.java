package com.maibo.lys.xianhuicustomer.myview;

/**
 * Created by LYS on 2016/10/27.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wli on 15/12/8.
 * RecyclerView 的分割线
 */
public class AllDividerDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;

    public AllDividerDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    /*parent.getPaddingLeft()*/
        final int left = 0;
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
//            Log.e("top:",top+"  botton:"+bottom+"  left:"+left+"  right:"+right);
//            Log.e("left:",left+" :"+child.getPaddingLeft());
            if (i==0||i==childCount-2){
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }else {
                mDivider.setBounds(30, top, right, bottom);
                mDivider.draw(c);
            }

        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
    }
}


package cn.cloudworkshop.miaoding.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * Author：Libin on 2016/9/26 12:30
 * Email：1993911441@qq.com
 * Describe：RecyclerView网格分割线
 */
public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;


    public RecyclerItemDecoration(int space) {
        this.mSpace = space;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        if (position == 0) {
            outRect.set(mSpace, 0, mSpace, 0);
        } else {
            outRect.set(0, 0, mSpace, 0);
        }

    }

}

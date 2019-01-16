package cn.cloudworkshop.miaoding.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author：Libin on 2019/1/14 13:37
 * Email：1993911441@qq.com
 * Describe：
 */
public class RVItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;


    public RVItemDecoration(int space) {
        this.mSpace = space;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);

        if (position == 0) {
            outRect.set(0, mSpace, 0, mSpace);
        } else {
            outRect.set(0, 0, 0, mSpace);
        }

    }
}

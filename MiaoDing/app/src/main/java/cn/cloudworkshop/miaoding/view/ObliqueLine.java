package cn.cloudworkshop.miaoding.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import cn.cloudworkshop.miaoding.R;

/**
 * Author: Libin on 2019/5/7 21:05
 * Contact: 1993911441@qq.com
 */
public class ObliqueLine extends View {
    private Paint mPaint;

    public ObliqueLine(Context context) {
        super(context);
    }

    public ObliqueLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(context, R.color.color_d9));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, getHeight(), getWidth(), 0, mPaint);
    }
}

package com.launcher.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class TestBackgroundView extends View {


    public TestBackgroundView(Context context) {
        super(context);
        initBackgroundGradient();
    }

    public TestBackgroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBackgroundGradient();
    }

    public TestBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBackgroundGradient();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBackgroundPaint.setColor(Color.BLACK);
        mBackgroundPaint.setShader(horizontalBackground);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mBackgroundPaint);
    }

    Paint mBackgroundPaint;
    LinearGradient horizontalBackground, verticalBackground;

    private void initBackgroundGradient() {
        mBackgroundPaint = new Paint();
        int[] colors = new int[]{
        };

        verticalBackground = new LinearGradient(
                0, 0, getMeasuredWidth(), 0, colors, null,
                LinearGradient.TileMode.CLAMP);

        horizontalBackground = new LinearGradient(
                0, 0, 0, getMeasuredHeight(), colors, null,
                LinearGradient.TileMode.CLAMP);
    }
}

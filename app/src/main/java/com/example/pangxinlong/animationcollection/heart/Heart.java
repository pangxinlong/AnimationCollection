package com.example.pangxinlong.animationcollection.heart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pangxinlong on 2017/6/13.
 */

public class Heart extends View {


    private Path mPath;

    private Paint mPaint;

    public Heart(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.BLUE);

        mPath = new Path();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        int width = getWidth();
        int height = getHeight();
        mPath.moveTo(width >> 1, height >> 1);
        mPath.rQuadTo(width >> 1 - 50, height >> 1 - 50, width >> 1 - 100, height);
        mPath.rQuadTo(width >> 1 - 50, height >> 1 + 50, width >> 1, height + 200);
//        mPath.rQuadTo(getWidth()/2+50,getHeight()/2-50,getWidth()/2,getHeight()/2);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }
}

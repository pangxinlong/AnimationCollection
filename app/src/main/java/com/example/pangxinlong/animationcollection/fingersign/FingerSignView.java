package com.example.pangxinlong.animationcollection.fingersign;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Date: 2020-03-02
 * author: pangxinlong
 * Description:
 */
public class FingerSignView extends View {
    public FingerSignView(Context context) {
        super(context);
        init();
    }

    public FingerSignView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FingerSignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint paint;
    private Path path;

    private int height;
    private int width;

    /**
     * 签名画笔
     */
    private Canvas cacheCanvas;
    /**
     * 签名画布
     */
    private Bitmap cacheBitmap;

    private boolean isDraw = false;

    private void init() {
        isDraw = false;
        path = new Path();
        paint = new Paint();
        paint.setColor(getResources().getColor(android.R.color.black));
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //创建跟view一样大的bitmap，用来保存签名
        height = getHeight();
        width = getWidth();
        cacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas(cacheBitmap);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
        cacheCanvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isDraw = true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                invalidate();
                break;
        }
        return true;
    }


    public Bitmap getBitmap() {
        if (!isDraw)
            return null;
        return cacheBitmap;
    }

    public void clear() {
        isDraw = false;
        path.reset();
        cacheBitmap.recycle();
        cacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas(cacheBitmap);
        invalidate();
    }
}

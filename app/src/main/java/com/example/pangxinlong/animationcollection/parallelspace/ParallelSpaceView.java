package com.example.pangxinlong.animationcollection.parallelspace;

import com.example.pangxinlong.animationcollection.R;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * Created by pangxinlong on 2017/6/17.
 */

public class ParallelSpaceView extends View {


    private ImageView mImageView;

    private ImageView mInsideImageView;

    private ParallelSpaceView mInsideView;

    public ParallelSpaceView(Context context) {
        super(context);
    }

    private float mHeight;

    private float mWidth;

    private Paint mPaint;

    Bitmap phoneBitmap;
    Bitmap phoneNextBitmap;

    Bitmap currentBitmap;

    Bitmap nextBitmap;

    int bitmapHeight;

    int bitmapWidth;

    public ParallelSpaceView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        init(context);
    }

    private void init(Context context) {
        phoneBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.phone_bg1);
        bitmapHeight = phoneBitmap.getWidth();
        bitmapWidth = phoneBitmap.getHeight();

        currentBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pg1_1);
        nextBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pg1_2);
        mInsideView = new ParallelSpaceView(context);
        mInsideView.IsDrawPhone(false);
    }

    private boolean isDrawPhone = true;

    public void IsDrawPhone(boolean isDrawPhone) {
        this.isDrawPhone = isDrawPhone;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    private float translationX=0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(currentBitmap, -translationX, 0, mPaint);
        canvas.drawBitmap(nextBitmap, mWidth-translationX, 0, mPaint);
        if (isDrawPhone) {
            canvas.drawBitmap(phoneBitmap, mWidth / 2 - bitmapWidth / 2, mHeight - bitmapHeight,
                    mPaint);
        }
        setTranslationX(-translationX);
        mInsideImageView.setTranslationX(-translationX / mWidth * mInsideImageView.getWidth());

    }

    public void startAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mWidth);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                translationX = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }


}

package com.example.pangxinlong.animationcollection.wave_ship;

import com.example.pangxinlong.animationcollection.R;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by pxl on 2017/6/6.
 */

public class CustomWaveView extends View {


    private Paint mPaint;

    private Path mPath;

    private int mWaveLength = 1000;

    private int mWaveHeight = 50;

    private int orginY = 800;

    private int speed = 1000;

    private float mDeltax;

    private Bitmap mBitmap;

    PathMeasure mPathMeasure;

    float positionX;

    float length;

    float pos[] = new float[2];

    float tan[] = new float[2];

    public CustomWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomWaveView);
        mWaveLength = (int) typedArray.getDimension(R.styleable.CustomWaveView_wave_length, 6000f);
        mWaveHeight = (int) typedArray.getDimension(R.styleable.CustomWaveView_wave_height, 50f);
        speed = typedArray.getInteger(R.styleable.CustomWaveView_wave_speed, 5000);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        initView();
    }

    private void initView() {
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
        mPath.moveTo(-mWaveLength + mDeltax, orginY);
        for (int i = -mWaveLength; i < mWaveLength + getWidth(); i += mWaveLength) {
            mPath.rQuadTo(mWaveLength / 4, mWaveHeight, mWaveLength / 2, 0);
            mPath.rQuadTo(mWaveLength / 4, -mWaveHeight, mWaveLength / 2, 0);
        }

//        orginY--;//自动上涨  orginY++：自动下降
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);

        Region region = new Region();
        Region clip = new Region((int) (getWidth() / 2 - 0.1), 0, getWidth() / 2, getHeight());
        boolean b = region.setPath(mPath, clip);
        if (b) {
            Rect bounds = region.getBounds();
            canvas.drawBitmap(mBitmap, bounds.left - mBitmap.getWidth() / 2,
                    bounds.top - mBitmap.getHeight() / 2, mPaint);
        }
        //水禁止，船滑动
//        mPathMeasure = new PathMeasure(mPath, false);
//        Matrix matrix = new Matrix();
//        matrix.reset();
//        length = mPathMeasure.getLength();
////        boolean b= mPathMeasure.getPosTan(positionX,pos,tan);
////        if (b) {
//        mPathMeasure.getMatrix(positionX, matrix,
//                PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
//        matrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);
//        canvas.drawBitmap(mBitmap, matrix, mPaint);
////        }
    }

    public void startAnim() {
        //水流动
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(speed);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDeltax = mWaveLength * (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();

        //水禁止，船滑动动画
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0, 1);
        valueAnimator2.setDuration(speed + 5000);
        valueAnimator2.setInterpolator(new LinearInterpolator());
        valueAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                positionX = length * (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
//        valueAnimator2.start();
    }

}

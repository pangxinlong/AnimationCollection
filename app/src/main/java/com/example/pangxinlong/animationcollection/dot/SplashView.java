package com.example.pangxinlong.animationcollection.dot;

import com.example.pangxinlong.animationcollection.R;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by pangxinlong on 2017/6/15.
 */

public class SplashView extends View {

    /**
     * 实现原理
     *
     * 1、整个动画分为三个部分，旋转，收缩，水波纹
     * 2、模拟数据请求，请求时开始开始旋转动画，当请求返回后开始收缩动画并监听改动画，若完成则开始水波纹动画
     */

    private int mDotRadius = 12;//小球半径

    private int mCircleRadius = 70;//圆半径

    private int mDotNumber = 6;//小球数量

    private int[] mDotColor = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA,
            Color.DKGRAY};

    //旋转动画时间
    private int mDotRotationalTime = 1500;

    //收缩动画时间
    private int mCircleShrinkTime = 1000;

    //水波纹动画时间
    private int mWaterRippleTime = 1000;

    //收缩动画反弹系数
    private float mTensionRatio = 10.0f;

    // 绘制圆的画笔
    private Paint mPaint = new Paint();

    // 绘制背景的画笔
    private Paint mPaintBackground = new Paint();

    //中心点
    private float mCenterX, mCenterY;

    //屏幕对角线一半
    private float mDiagonalDist;

    //动画状态
    private AnimState animState;

    public SplashView(Context context) {
        super(context);
        init();
    }

    public SplashView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SplashView);
        mDotRadius = typedArray.getInteger(R.styleable.SplashView_circle_radius, 12);
        mCircleRadius = typedArray.getInteger(R.styleable.SplashView_circle_radius, 70);
        mDotNumber = typedArray.getInteger(R.styleable.SplashView_dot_num, 6);
        mDotRotationalTime = typedArray
                .getInteger(R.styleable.SplashView_dot_rotational_time, 1500);
        mCircleShrinkTime = typedArray.getInteger(R.styleable.SplashView_circle_shrink_time, 1000);
        mWaterRippleTime = typedArray.getInteger(R.styleable.SplashView_water_ripple_time, 1000);
        init();
    }

    private void init() {
        //消除锯齿
        mPaint.setAntiAlias(true);
        mPaintBackground.setAntiAlias(true);
        //设置样式---边框样式--描边
        mPaintBackground.setStyle(Paint.Style.STROKE);
        mPaintBackground.setColor(Color.WHITE);
    }

    public void setDotColors(int[] colors) {
        mDotColor = colors;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = getWidth() / 2f;
        mCenterY = getHeight() / 2f;
        mDiagonalDist = (float) Math.sqrt((w * w + h * h)) / 2f;//勾股定律
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (animState == null) {
            animState = new RotateState();

        }
        animState.drawView(canvas);

    }

    public void cancelAnim() {
        if (animState instanceof RotateState) {
            animState.cancelView();
            animState = new ShrinkState();
        }
    }


    interface AnimState {

        void drawView(Canvas canvas);

        void cancelView();
    }

    /**
     * 旋转状态
     */
    class RotateState implements AnimState {

        ValueAnimator valueAnimator;

        private float rotationAngle;//旋转角度

        public RotateState() {
            valueAnimator = ValueAnimator.ofFloat(0f, (float) Math.PI * 2);//旋转角度0~2π
            valueAnimator.setDuration(mDotRotationalTime);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    rotationAngle = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.start();

        }

        @Override
        public void drawView(Canvas canvas) {
            //1.背景--擦黑板，涂成白色
            canvas.drawColor(Color.WHITE);
            float x, y;
            for (int i = 0; i < mDotNumber; i++) {
                x = (float) Math.cos(i * Math.PI * 2 / mDotNumber + rotationAngle) * mCircleRadius;
                y = (float) Math.sin(i * Math.PI * 2 / mDotNumber + rotationAngle) * mCircleRadius;
                if (i >= mDotColor.length) {
                    mPaint.setColor(Color.RED);
                } else {
                    mPaint.setColor(mDotColor[i]);
                }
                canvas.drawCircle(x + mCenterX, y + mCenterY, mDotRadius, mPaint);
            }
        }

        @Override
        public void cancelView() {
            valueAnimator.cancel();
        }
    }

    /**
     * 收缩状态
     */
    class ShrinkState implements AnimState {

        private ValueAnimator mValueAnimator;

        private float currentCircleRadius;

        public ShrinkState() {
            mValueAnimator = ValueAnimator.ofFloat(0f, mCircleRadius);
            mValueAnimator.setDuration(mCircleShrinkTime);
            mValueAnimator.setInterpolator(new OvershootInterpolator(mTensionRatio));
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentCircleRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            mValueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //收缩动画结束，开始水波纹动画
                    mValueAnimator.cancel();
                    animState = new WaterRippleState();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }

            });
            mValueAnimator.reverse();
        }

        @Override
        public void drawView(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            float x, y;
            for (int i = 0; i < mDotNumber; i++) {
                x = (float) Math.cos(i * Math.PI * 2 / mDotNumber) * currentCircleRadius;
                y = (float) Math.sin(i * Math.PI * 2 / mDotNumber) * currentCircleRadius;
                if (i >= mDotColor.length) {
                    mPaint.setColor(Color.RED);
                } else {
                    mPaint.setColor(mDotColor[i]);
                }
                canvas.drawCircle(x + mCenterX, y + mCenterY, mDotRadius, mPaint);
            }
        }

        @Override
        public void cancelView() {
            mValueAnimator.cancel();
        }
    }

    /**
     * 水波纹状态
     */
    class WaterRippleState implements AnimState {

        private ValueAnimator valueAnimator;

        private float currentRadius;

        public WaterRippleState() {
            valueAnimator = ValueAnimator.ofFloat(mCircleRadius, mDiagonalDist);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setDuration(mWaterRippleTime);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.start();
        }

        @Override
        public void drawView(Canvas canvas) {
            //得到画笔的宽度 = 对角线/2 - 空心圆的半径
            float strokeWidth = mDiagonalDist - currentRadius;
            mPaintBackground.setStrokeWidth(strokeWidth);
            //画圆的半径 = 空心圆的半径 + 画笔的宽度/2
            float radius = currentRadius + strokeWidth / 2;
            canvas.drawCircle(mCenterX, mCenterY, radius, mPaintBackground);
        }

        @Override
        public void cancelView() {
            valueAnimator.cancel();
        }
    }

}

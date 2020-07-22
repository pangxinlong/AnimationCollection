package com.example.pangxinlong.animationcollection.fish;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Date: 2020/7/20
 * author: pangxinlong
 * Description:
 */
public class FishLayout extends RelativeLayout {

    private FishDrawable fishDrawable;
    private ImageView ivFish;

    private Paint mPaint;

    public FishLayout(Context context) {
        super(context);
        init(context);
    }

    public FishLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FishLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // ViewGroup 默认 不执行 onDraw
        setWillNotDraw(false);

        fishDrawable = new FishDrawable();
        ivFish = new ImageView(context);
        ivFish.setImageDrawable(fishDrawable);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#200000ff"));
        mPaint.setStrokeWidth(8);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        addView(ivFish);
        ivFish.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (radius != 0) {
            mPaint.setAlpha((int) (255 - (radius * 1.0f / 100 * 255)));
            canvas.drawCircle(touchX, touchY, radius, mPaint);
        }
    }

    private float touchX, touchY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            radius = 0;
            touchX = event.getX();
            touchY = event.getY();
            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "radius", 0, 100);
            objectAnimator.setInterpolator(new LinearInterpolator());
            objectAnimator.setDuration(1000);
            objectAnimator.start();
            makeTrail();
        }
        return super.onTouchEvent(event);
    }

    private void makeTrail() {
        //鱼当前位置到触摸点的移动路径为3阶贝塞尔曲线所以需要计算4个点
        PointF fishMiddle = fishDrawable.getMiddlePoint();
        PointF fishHeadPoint = fishDrawable.getFishHeadPoint();
        //起点（当前坐标）
        PointF startPoint = new PointF(fishMiddle.x + ivFish.getX(), fishMiddle.y + ivFish.getY());
        //结束点 一（触摸位置）
        PointF endPoint = new PointF(touchX, touchY);
        //控制点1 一（鱼头）
        PointF controlPoint1 = new PointF(fishHeadPoint.x + ivFish.getX(), fishHeadPoint.y + ivFish.getY());
        //控制点2（难点）根据自己算法来定，这里取的是(起点 O) （控制点1 A）（结束点B）角AOB夹角一半的中心线上的1/2
        float angle = includeAngle(startPoint, controlPoint1, endPoint);
        float delta = includeAngle(startPoint, new PointF(fishMiddle.x + 1, fishMiddle.y), controlPoint1);
        // 控制点2 的坐标
        PointF controlPoint2 = fishDrawable.calculationPoint(startPoint,
                fishDrawable.getMiddleToHeadLength() * 0.5f, (int) (angle + delta));

//        PointF controlPoint2 = new PointF(fishHeadPoint.x + ivFish.getX() + 100, fishHeadPoint.y + ivFish.getY() - 200);


        Path path = new Path();
        path.moveTo(startPoint.x - fishMiddle.x, startPoint.y - fishMiddle.y);
        path.cubicTo(controlPoint1.x - fishMiddle.x, controlPoint1.y - fishMiddle.y,
                controlPoint2.x - fishMiddle.x, controlPoint2.y - fishMiddle.y,
                endPoint.x - fishMiddle.x, endPoint.y - fishMiddle.y);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivFish, "x", "y", path);
        objectAnimator.setDuration(2000);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fishDrawable.setSwingSpeed(1);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                fishDrawable.setSwingSpeed(3);
            }
        });
        objectAnimator.start();

        final PathMeasure pathMeasure = new PathMeasure(path, false);
        final float[] tan = new float[2];
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //当前path路径执行长度
                float currentLength = animation.getAnimatedFraction() * pathMeasure.getLength();
                //获取当前长度位置tan值坐标
                pathMeasure.getPosTan(currentLength, null, tan);
                //-tan[1] 因为android坐标系Y轴与数学坐标系正好相反
                int angle = (int) Math.toDegrees(Math.atan2(-tan[1], tan[0]));
                fishDrawable.setFishAngle(angle);
            }
        });

    }

    public float includeAngle(PointF O, PointF A, PointF B) {
        // cosAOB
        // OA*OB=(Ax-Ox)(Bx-Ox)+(Ay-Oy)*(By-Oy)
        float AOB = (A.x - O.x) * (B.x - O.x) + (A.y - O.y) * (B.y - O.y);
        float OALength = (float) Math.sqrt((A.x - O.x) * (A.x - O.x) + (A.y - O.y) * (A.y - O.y));
        // OB 的长度
        float OBLength = (float) Math.sqrt((B.x - O.x) * (B.x - O.x) + (B.y - O.y) * (B.y - O.y));
        float cosAOB = AOB / (OALength * OBLength);

        // 反余弦
        float angleAOB = (float) Math.toDegrees(Math.acos(cosAOB));

        // AB连线与X的夹角的tan值 - OB与x轴的夹角的tan值
        float direction = (A.y - B.y) / (A.x - B.x) - (O.y - B.y) / (O.x - B.x);

        if (direction == 0) {
            if (AOB >= 0) {
                return 0;
            } else {
                return 180;
            }
        } else {
            if (direction > 0) {
                return -angleAOB;
            } else {
                return angleAOB;
            }
        }

    }

    private int radius = 1;

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

    public int getRadius() {
        return radius;
    }
}

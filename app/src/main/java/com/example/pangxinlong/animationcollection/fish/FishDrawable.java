package com.example.pangxinlong.animationcollection.fish;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import static android.animation.ValueAnimator.INFINITE;


/**
 * Date: 2020/7/17
 * author: pangxinlong
 * Description:
 */
public class FishDrawable extends Drawable {

    private Paint mPaint;
    private Path mPath;
    private PointF middlePoint;

    public FishDrawable() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#60ff0000"));
        mPath = new Path();
        middlePoint = new PointF(4.18f * BASE_LENGTH, 4.18f * BASE_LENGTH);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 3600);
        valueAnimator.setDuration(15 * 1000);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                setAngleOne(value);
            }
        });
        valueAnimator.start();
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) (9 * BASE_LENGTH);
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) (9 * BASE_LENGTH);
    }

    //基准长度 （头部半径）
    private final float BASE_LENGTH = 50;
    private int fishAngle = 90;

    //重心到头部中心点长度
    private final float middleToHeadLength = 1.3f * BASE_LENGTH;
    //头部中心点到鱼鳍起点长度
    private final float headToWingLength = BASE_LENGTH;
    //鱼鳍起点到终点长度
    private final float wingLength = 1.2f * BASE_LENGTH;
    //鱼鳍控制点
    private final float wingsControlLength = 1.8f * BASE_LENGTH;

    //重心到肢节底部中心点长度
    private final float middleToBottomCenterLength = 1.3f * BASE_LENGTH;
    //梯形下底中心圆半径
    private final float bottomCircleR = 0.8f * BASE_LENGTH;
    //梯形上底中心圆半径
    private final float topCircleR = 0.5f * BASE_LENGTH;

    //梯形上底中心圆到小圆重新距离
    private final float topCircleCenterToSmallCircle = 1.3f * BASE_LENGTH;
    private final float smallCircleR = 0.2f * BASE_LENGTH;

    //尾巴大三角形边长
    private final float bigTriangleLength = BASE_LENGTH * 1.2f;
    //尾巴小三角形边长
    private final float smallTriangleLength = BASE_LENGTH * 1.1f;

    private PointF fishHeadPoint;

    @Override
    public void draw(@NonNull Canvas canvas) {
        int bodyAngle = fishAngle + swingBodyAngle;
        //画鱼头
        fishHeadPoint = calculationPoint(middlePoint, middleToHeadLength, bodyAngle);
        canvas.drawCircle(fishHeadPoint.x, fishHeadPoint.y, BASE_LENGTH, mPaint);

        //画鱼鳍
        makeWings(canvas, fishHeadPoint, true, bodyAngle);
        makeWings(canvas, fishHeadPoint, false, bodyAngle);

        //画肢节1
        int limbSegmentAngle1 = fishAngle + swingLimbSegmentAngle1;
        //1大圆
        PointF bottomCenterPoint = calculationPoint(middlePoint, middleToBottomCenterLength, bodyAngle - 180);
        canvas.drawCircle(bottomCenterPoint.x, bottomCenterPoint.y, bottomCircleR, mPaint);
        //2小圆
        PointF topCenterPoint = calculationPoint(bottomCenterPoint, bottomCircleR + topCircleR, limbSegmentAngle1 - 180);
        canvas.drawCircle(topCenterPoint.x, topCenterPoint.y, topCircleR, mPaint);
        //3梯形
        PointF rightBottomPoint = calculationPoint(bottomCenterPoint, bottomCircleR, limbSegmentAngle1 - 90);
        PointF leftBottomPoint = calculationPoint(bottomCenterPoint, bottomCircleR, limbSegmentAngle1 + 90);
        PointF rightTopPoint = calculationPoint(topCenterPoint, topCircleR, limbSegmentAngle1 - 90);
        PointF leftTopPoint = calculationPoint(topCenterPoint, topCircleR, limbSegmentAngle1 + 90);
        mPath.reset();
        mPath.moveTo(leftBottomPoint.x, leftBottomPoint.y);
        mPath.lineTo(rightBottomPoint.x, rightBottomPoint.y);
        mPath.lineTo(rightTopPoint.x, rightTopPoint.y);
        mPath.lineTo(leftTopPoint.x, leftTopPoint.y);
        canvas.drawPath(mPath, mPaint);

        //画肢节2
        int limbSegmentAngle2 = fishAngle + swingLimbSegmentAngle2;

        //最小圆
        PointF smallCirclePoint = calculationPoint(topCenterPoint, topCircleCenterToSmallCircle, limbSegmentAngle2 - 180);
        canvas.drawCircle(smallCirclePoint.x, smallCirclePoint.y, smallCircleR, mPaint);
        //梯形
        PointF smallBottomRightPoint = calculationPoint(topCenterPoint, topCircleR, limbSegmentAngle2 + 90);
        PointF smallBottomLeftPoint = calculationPoint(topCenterPoint, topCircleR, limbSegmentAngle2 - 90);
        PointF smallTopLeftPoint = calculationPoint(smallCirclePoint, smallCircleR, limbSegmentAngle2 + 90);
        PointF smallTopRightPoint = calculationPoint(smallCirclePoint, smallCircleR, limbSegmentAngle2 - 90);
        mPath.reset();
        mPath.moveTo(smallBottomRightPoint.x, smallBottomRightPoint.y);
        mPath.lineTo(smallBottomLeftPoint.x, smallBottomLeftPoint.y);
        mPath.lineTo(smallTopRightPoint.x, smallTopRightPoint.y);
        mPath.lineTo(smallTopLeftPoint.x, smallTopLeftPoint.y);
        canvas.drawPath(mPath, mPaint);
        //画尾巴
        makeTail(canvas, topCenterPoint, limbSegmentAngle2);
        //身体
        makeBody(canvas, fishHeadPoint, bottomCenterPoint, bodyAngle);

    }

    private void makeTail(@NonNull Canvas canvas, PointF topCenterPoint, int limbSegmentAngle2) {
        //大三角
        float findEdgeLength = (float) Math.abs(Math.sin(Math.toRadians(currentValue) * 1.5f) * bigTriangleLength / 1.8f);
        PointF bigTriangleCenterPoint = calculationPoint(topCenterPoint, bigTriangleLength, limbSegmentAngle2 - 180);
        PointF bigTriangleRightPoint = calculationPoint(bigTriangleCenterPoint, findEdgeLength, limbSegmentAngle2 - 90);
        PointF bigTriangleLeftPoint = calculationPoint(bigTriangleCenterPoint, findEdgeLength, limbSegmentAngle2 + 90);

        mPath.reset();
        mPath.moveTo(topCenterPoint.x, topCenterPoint.y);
        mPath.lineTo(bigTriangleRightPoint.x, bigTriangleRightPoint.y);
        mPath.lineTo(bigTriangleLeftPoint.x, bigTriangleLeftPoint.y);
        canvas.drawPath(mPath, mPaint);
        //小三角

        float findEdgeLength2 = (float) Math.abs(Math.sin(Math.toRadians(currentValue * 1.5f)) * (bigTriangleLength / 1.8 - 20));
        PointF smallTriangleCenterPoint = calculationPoint(topCenterPoint, bigTriangleLength - 10, fishAngle - 180 + swingLimbSegmentAngle2);
        PointF smallTriangleRightPoint = calculationPoint(smallTriangleCenterPoint, findEdgeLength2, (int) (fishAngle - 90 + swingLimbSegmentAngle2));
        PointF smallTriangleLeftPoint = calculationPoint(smallTriangleCenterPoint, findEdgeLength2, (int) (fishAngle + 90 + swingLimbSegmentAngle2));
        mPath.reset();
        mPath.moveTo(topCenterPoint.x, topCenterPoint.y);
        mPath.lineTo(smallTriangleRightPoint.x, smallTriangleRightPoint.y);
        mPath.lineTo(smallTriangleLeftPoint.x, smallTriangleLeftPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    private void makeBody(@NonNull Canvas canvas, PointF fishHeadPoint, PointF bottomCenterPoint, int fishAngle) {
        PointF boyLeftTopPoint = calculationPoint(fishHeadPoint, BASE_LENGTH, fishAngle + 90);
        PointF boyLeftBottomPoint = calculationPoint(bottomCenterPoint, bottomCircleR, fishAngle + 90);
        PointF boyLeftControlPoint = calculationPoint(middlePoint, BASE_LENGTH * 1.4f, fishAngle + 70);

        PointF boyRightTopPoint = calculationPoint(fishHeadPoint, BASE_LENGTH, fishAngle - 90);
        PointF boyRightBottomPoint = calculationPoint(bottomCenterPoint, bottomCircleR, fishAngle - 90);
        PointF boyRightControlPoint = calculationPoint(middlePoint, BASE_LENGTH * 1.4f, fishAngle - 70);

        mPath.reset();
        mPath.moveTo(boyLeftTopPoint.x, boyLeftTopPoint.y);
        mPath.quadTo(boyLeftControlPoint.x, boyLeftControlPoint.y, boyLeftBottomPoint.x, boyLeftBottomPoint.y);
        mPath.lineTo(boyRightBottomPoint.x, boyRightBottomPoint.y);
        mPath.quadTo(boyRightControlPoint.x, boyRightControlPoint.y, boyRightTopPoint.x, boyRightTopPoint.y);
        mPath.lineTo(boyLeftTopPoint.x, boyLeftTopPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    private void makeWings(@NonNull Canvas canvas, PointF fishHeadPoint, boolean isRight, int fishAngle) {
        //方案一
//        float wingsControlLengthRate;
//        if (swingSpeed == 1) {//原地不动
//            wingsControlLengthRate = 1;
//        } else {//游动时
//            wingsControlLengthRate = (float) Math.abs(Math.sin(Math.toRadians(currentValue)));//0～1的值
//        }

        //方案二
        float wingsAngleRate;
//        if (swingSpeed == 1) {//原地不动
//            wingsAngleRate = 0
//        } else {//游动时
//            wingsAngleRate = (float) Math.abs(Math.sin(Math.toRadians(currentValue))) * 20;//0～1的值 *
//        }

        //最终版
        wingsAngleRate = (float) Math.abs(Math.sin(Math.toRadians(currentValue * (swingSpeed > 1 ? 2 : 1)))) * 20;//0～1的值 *20

        PointF startWingPoint = isRight ? calculationPoint(fishHeadPoint, headToWingLength, fishAngle - 105) :
                calculationPoint(fishHeadPoint, headToWingLength, fishAngle + 105);

        PointF endWingPoint = calculationPoint(startWingPoint, wingLength, fishAngle - 180);
        PointF controlPoint = isRight ? calculationPoint(startWingPoint, wingsControlLength, (int) (fishAngle - 120 - wingsAngleRate)) :
                calculationPoint(startWingPoint, wingsControlLength, (int) (fishAngle + 120 + wingsAngleRate));

        mPath.reset();
        mPath.moveTo(startWingPoint.x, startWingPoint.y);
        mPath.quadTo(controlPoint.x, controlPoint.y, endWingPoint.x, endWingPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 根据起点计算终点
     *
     * @param startPoint
     * @param length
     * @param angle
     * @return
     */
    public static PointF calculationPoint(PointF startPoint, float length, int angle) {
        float pointX = (float) Math.cos(Math.toRadians(angle)) * length;
        float pointY = (float) Math.sin(Math.toRadians(angle - 180)) * length;
        PointF pointEnd = new PointF(pointX + startPoint.x, pointY + startPoint.y);
        return pointEnd;
    }

    //肢解1
    private float currentValue = 0;
    private int swingBodyAngle = 0;
    private int swingLimbSegmentAngle1 = 0;
    private int swingLimbSegmentAngle2 = 0;

    public void setAngleOne(int angle) {
        currentValue = angle * swingSpeed;
        //身体摆动幅度
        swingBodyAngle = (int) (Math.sin(Math.toRadians(angle * 1.2f * swingSpeed)) * 10);
        //肢节1
        swingLimbSegmentAngle1 = (int) (Math.cos(Math.toRadians(angle * 1.5f * swingSpeed)) * 15);
        //肢节2/鱼尾部三角
        swingLimbSegmentAngle2 = (int) (Math.sin(Math.toRadians(angle * 1.5f * swingSpeed)) * 25);
        invalidateSelf();
    }

    //摆动速度
    private int swingSpeed = 1;

    public void setSwingSpeed(int swingSpeed) {
        this.swingSpeed = swingSpeed;
    }

    //设置鱼的角度
    public void setFishAngle(int angle) {
        fishAngle = angle;
    }

    public PointF getMiddlePoint() {
        return middlePoint;
    }

    public PointF getFishHeadPoint() {
        return fishHeadPoint;
    }

    public float getMiddleToHeadLength() {
        return middleToHeadLength;
    }


}

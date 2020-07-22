package com.example.pangxinlong.animationcollection.carddrag;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2020-02-11
 * author: pangxinlong
 * Description:
 */
public class CustomCardView extends FrameLayout {

    private List<View> childViewList;

    public CustomCardView(Context context) {
        super(context);
        initView();
    }

    public CustomCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        childViewList = new ArrayList<>();
    }


    public void addCard(final View view) {
        LayoutParams layoutParams = new LayoutParams(800, 200);
        layoutParams.gravity = Gravity.CENTER;
        if (childViewList.size() != 0) {
            layoutParams.setMargins(0, 0, (childViewList.size()) * 10, (childViewList.size()) * 10);
        }
        view.setLayoutParams(layoutParams);
        childViewList.add(0, view);
        addView(view);

        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("X", view.getX() + "");
                        Log.e("Y", view.getY() + "");
                        orginX = event.getX();
                        orginY = event.getY();
                        orginViewX = v.getX();
                        orginViewY = v.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float translateX = event.getX() - orginX;
                        float translateY = event.getY() - orginY;
                        view.layout(view.getLeft() + (int) translateX, view.getTop() + (int) translateY,
                                view.getRight() + (int) translateX, view.getBottom() + (int) translateY);
                        if (view.getX() < -v.getWidth() / 2 || view.getX() > getWidth() - v.getWidth() / 2) {
                            removeView(view);
                            childViewList.remove(view);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (view.getX() > -v.getWidth() / 2 && view.getX() < getWidth() - v.getWidth() / 2) {
//                            view.layout((int) orginViewX, (int) orginViewY, (int) orginViewX + view.getWidth(), (int) orginViewY + view.getHeight());


                            //松手时view的位置
                            final float currentX = view.getX();
                            final float currentY = view.getY();

                            //view拖动距离
                            final float translateTotalX = currentX - orginViewX;
                            final float translateTotalY = currentY - orginViewY;

                            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                            valueAnimator.setInterpolator(new LinearInterpolator());
                            valueAnimator.setDuration(200);
                            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    float value = (float) animation.getAnimatedValue();


                                    int viewLeft = (int) (currentX - translateTotalX * value);
                                    int viewTop = (int) (currentY - translateTotalY * value);
                                    view.layout(viewLeft, viewTop
                                            , viewLeft + view.getWidth(), viewTop + view.getHeight());
                                }
                            });
                            valueAnimator.start();
                        }
                        break;
                }
                return true;
            }
        });
    }

    //按下时的位置
    private float orginX;
    private float orginY;

    //view 原本位置
    private float orginViewX;
    private float orginViewY;

}

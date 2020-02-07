package com.example.pangxinlong.animationcollection.zan;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pangxinlong.animationcollection.R;

import java.util.Random;


/**
 * Date: 2019-11-27
 * author: pangxinlong
 * Description: 点赞view
 */
public class ThumbsUpView extends FrameLayout {

    private Context context;

    private int[] imgSource;
    private float translationY = 1000f;
    private TextView tvZanNum;

    public ThumbsUpView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ThumbsUpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ThumbsUpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        imgSource = new int[]{R.mipmap.ic_living_zan1, R.mipmap.ic_living_zan2,
                R.mipmap.ic_living_zan3, R.mipmap.ic_living_zan4,
                R.mipmap.ic_living_zan5, R.mipmap.ic_living_zan6,
                R.mipmap.ic_living_zan7, R.mipmap.ic_living_zan8,
                R.mipmap.ic_living_zan9, R.mipmap.ic_living_zan10,
                R.mipmap.ic_living_zan11};
        View view = LayoutInflater.from(context).inflate(R.layout.common_zan_item, this, true);
        tvZanNum = (TextView) view.findViewById(R.id.tv_zan_num);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvZanNum.setText(Integer.valueOf(tvZanNum.getText().toString()) + 1 + "");
                createAnimator((ViewGroup) getParent());
            }
        });
    }

    private void createAnimator(final ViewGroup viewGroup) {
        final double randomNum = Math.random();
        final ImageView imageView = new ImageView(context);
        imageView.setX(getX() + getWidth() / 2);
        imageView.setY(getY() + getHeight() / 2);
        int value = new Random().nextInt(imgSource.length);
        imageView.setBackgroundResource(imgSource[value]);
        viewGroup.addView(imageView);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        ((LayoutParams) layoutParams).gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        layoutParams.width = LayoutParams.WRAP_CONTENT;
//        if (viewGroup instanceof FrameLayout) {
//            ((LayoutParams) layoutParams).gravity = Gravity.RIGHT | Gravity.BOTTOM;
//            layoutParams.height = LayoutParams.WRAP_CONTENT;
//            layoutParams.width = LayoutParams.WRAP_CONTENT;
//            ((LayoutParams) layoutParams).setMargins(0, 0, getWidth() / 4, getBottom() + getHeight() / 3);
//        } else if (viewGroup instanceof LinearLayout) {
//            throw new RuntimeException("parent no support LinearLayout");
//        } else if (viewGroup instanceof RelativeLayout) {
//            ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//            ((RelativeLayout.LayoutParams) layoutParams).setMargins(0, 0, getWidth() / 4, getBottom() + getHeight() / 3);
//        }
        imageView.setLayoutParams(layoutParams);

        //startY=自身距离父布局高度
        final float startY = getY();

        //若位移距离高出viewGroup范围，则位移到顶端结束
        translationY = Math.min(startY, translationY);
        final float endY = Math.abs(startY - translationY);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                return startY - fraction * translationY;
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                double y = (randomNum * 100 - 25) * Math.sin(value * (Math.PI * 2) / (translationY));
                imageView.setTranslationX((float) y);
                imageView.setY(value);
                imageView.setAlpha(1 - ((startY - value) / translationY));
                if (value == endY) {
                    imageView.setVisibility(View.GONE);
                    viewGroup.removeView(imageView);
                }
            }
        });
        valueAnimator.start();
    }
}

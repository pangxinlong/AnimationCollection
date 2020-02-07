package com.example.pangxinlong.animationcollection.animset;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pangxinlong.animationcollection.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2019-12-26
 * author: pangxinlong
 * Description:
 */
public class LayoutAnimationsDemo extends Activity {

    private ViewGroup layoutAnimation;

    private List<View> list;

    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation);
        layoutAnimation = (ViewGroup) findViewById(R.id.ll_animate);
        list = new ArrayList<>();
    }


    public void add(View v) {
        ObjectAnimator addAnimator = ObjectAnimator.ofFloat(v, "rotation", 0f, 360f);
        ObjectAnimator addAnimatorY = ObjectAnimator.ofFloat(v, "translationX", (list.size() % 2 - 0.5f) * 100);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(addAnimator, addAnimatorY);
        animatorSet.start();

        i++;
        Button button = new Button(this);
        button.setText("button" + i);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(layoutParams);
        button.setStateListAnimator(AnimatorInflater.loadStateListAnimator(this, R.drawable.animate_scale));
        list.add(button);
        layoutAnimation.addView(button);
    }

    private boolean isRunning = true;

    public void delete(View v) {
        if (isRunning) {
            isRunning = false;
            ObjectAnimator delAnimator = ObjectAnimator.ofFloat(v, "rotation", v.getRotation(), 180f + v.getRotation());
            ObjectAnimator addAnimatorY = ObjectAnimator.ofFloat(v, "translationY", (list.size() % 2 - 0.5f) * 100);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(delAnimator, addAnimatorY);
            animatorSet.start();
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isRunning = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    isRunning = true;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            if (list.size() != 0) {
                View view = list.remove(0);
                layoutAnimation.removeView(view);
            }
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LayoutAnimationsDemo.class);
        context.startActivity(intent);
    }
}

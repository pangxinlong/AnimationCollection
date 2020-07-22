package com.example.pangxinlong.animationcollection.fish;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.pangxinlong.animationcollection.R;
import com.example.pangxinlong.animationcollection.fingersign.FingerSignActivity;

import static android.animation.ValueAnimator.INFINITE;
import static android.animation.ValueAnimator.REVERSE;

/**
 * Date: 2020/7/17
 * author: pangxinlong
 * Description:
 */
public class FishActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish);
//        ImageView imageView = findViewById(R.id.iv_fish);
//        final FishDrawable fishDrawable = new FishDrawable();
//        imageView.setBackground(fishDrawable);

//        ImageView imageView2 = findViewById(R.id.iv_fish2);
//        imageView2.setImageDrawable(new FishDrawable2());
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, FishActivity.class);
        context.startActivity(intent);
    }
}

package com.example.pangxinlong.animationcollection.dot;

import com.example.pangxinlong.animationcollection.R;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

/**
 * Created by pangxinlong on 2017/6/15.
 */

public class SplashActivity extends AppCompatActivity {


    private FrameLayout mFrameLayout;

    private ContentView mContentView;

    private SplashView mSplashView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFrameLayout = new FrameLayout(this);
        mContentView = new ContentView(this);

        mSplashView = new SplashView(this);
        mFrameLayout.addView(mContentView);
        mFrameLayout.addView(mSplashView);
        setContentView(mFrameLayout);
        load();
    }

    Handler mHandler;

    private void load() {
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSplashView.cancelAnim();
            }
        }, 5000);
    }


    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SplashActivity.class);
        context.startActivity(intent);
    }
}

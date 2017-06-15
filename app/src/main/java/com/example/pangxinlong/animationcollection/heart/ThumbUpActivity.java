package com.example.pangxinlong.animationcollection.heart;

import com.example.pangxinlong.animationcollection.BaseActivity;
import com.example.pangxinlong.animationcollection.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by pangxinlong on 2017/6/13.
 */

public class ThumbUpActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);
    }

    public static void start(Context context){
        Intent intent=new Intent();
        intent.setClass(context,ThumbUpActivity.class);
        context.startActivity(intent);
    }
}

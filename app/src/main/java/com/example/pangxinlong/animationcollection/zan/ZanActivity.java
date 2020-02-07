package com.example.pangxinlong.animationcollection.zan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.pangxinlong.animationcollection.R;

/**
 * Date: 2020-02-07
 * author: pangxinlong
 * Description:
 */
public class ZanActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zan);
    }

    public static void start(Context context){
        Intent intent=new Intent();
        intent.setClass(context,ZanActivity.class);
        context.startActivity(intent);
    }
}

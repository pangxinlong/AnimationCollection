package com.example.pangxinlong.animationcollection.scroll;

import com.example.pangxinlong.animationcollection.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by pangxinlong on 2017/6/18.
 */

public class ScrollAnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_scroll_anim);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ScrollAnimActivity.class);
        context.startActivity(intent);
    }
}

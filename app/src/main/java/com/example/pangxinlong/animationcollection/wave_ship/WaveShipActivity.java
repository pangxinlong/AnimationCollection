package com.example.pangxinlong.animationcollection.wave_ship;

import com.example.pangxinlong.animationcollection.BaseActivity;
import com.example.pangxinlong.animationcollection.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pxl on 2017/6/6.
 */

public class WaveShipActivity extends BaseActivity {

    @BindView(R.id.wv_custom)
    CustomWaveView mWvCustom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_ship);
        ButterKnife.bind(this);
        mWvCustom.startAnim();
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, WaveShipActivity.class);
        context.startActivity(intent);
    }
}

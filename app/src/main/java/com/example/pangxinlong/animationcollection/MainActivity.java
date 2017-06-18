package com.example.pangxinlong.animationcollection;

import com.example.pangxinlong.animationcollection.dot.SplashActivity;
import com.example.pangxinlong.animationcollection.heart.ThumbUpActivity;
import com.example.pangxinlong.animationcollection.parallelspace.ParallelSpaceActivity;
import com.example.pangxinlong.animationcollection.scroll.ScrollAnimActivity;
import com.example.pangxinlong.animationcollection.wave_ship.WaveShipActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.lv_anim)
    ListView mLvAnim;

    private Unbinder mUnbinder;

    private List<String> mDataList;

    private ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        mListAdapter = new ListAdapter(this);
        mLvAnim.setAdapter(mListAdapter);
    }

    private void initData() {
        mDataList = new ArrayList<>();
        mDataList.add("WaveShipActivity");//小船，水流动画
        mDataList.add("ThumbUpActivity");
        mDataList.add("SplashActivity");//启动页小点动画
        mDataList.add("ParallelSpaceActivity");//平行空间引导页动画
        mDataList.add("ScrollAnimActivity");//
        mListAdapter.setData(mDataList);
    }

    private void setListener() {
        mLvAnim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        WaveShipActivity.start(MainActivity.this);
                        break;
                    case 1:
                        ThumbUpActivity.start(MainActivity.this);
                        break;
                    case 2:
                        SplashActivity.start(MainActivity.this);
                        break;
                    case 3:
                        ParallelSpaceActivity.start(MainActivity.this);
                        break;
                    case 4:
                        ScrollAnimActivity.start(MainActivity.this);
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}

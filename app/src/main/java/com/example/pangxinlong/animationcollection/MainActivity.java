package com.example.pangxinlong.animationcollection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pangxinlong.animationcollection.animset.LayoutAnimationsDemo;
import com.example.pangxinlong.animationcollection.carddrag.CardDragActivity;
import com.example.pangxinlong.animationcollection.dot.SplashActivity;
import com.example.pangxinlong.animationcollection.fingersign.FingerSignActivity;
import com.example.pangxinlong.animationcollection.parallelspace.ParallelSpaceActivity;
import com.example.pangxinlong.animationcollection.scroll.ScrollAnimActivity;
import com.example.pangxinlong.animationcollection.wave_ship.WaveShipActivity;
import com.example.pangxinlong.animationcollection.zan.ZanActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
//        mDataList.add("ThumbUpActivity");
        mDataList.add("启动页动画");//启动页小点动画
        mDataList.add("ParallelSpaceActivity");//平行空间引导页动画
        mDataList.add("ScrollAnimActivity");//
        mDataList.add("直播间点赞效果");
        mDataList.add("AnimationsSetDemo");
        mDataList.add("重叠卡片拖动");
        mDataList.add("电子签名");
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
//                    case 1:
//                        ThumbUpActivity.start(MainActivity.this);
//                        break;
                    case 1:
                        SplashActivity.start(MainActivity.this);
                        break;
                    case 2:
                        ParallelSpaceActivity.start(MainActivity.this);
                        break;
                    case 3:
                        ScrollAnimActivity.start(MainActivity.this);
                        break;
                    case 4:
                        ZanActivity.start(MainActivity.this);
                        break;
                    case 5:
                        LayoutAnimationsDemo.start(MainActivity.this);
                        break;
                    case 6:
                        CardDragActivity.start(MainActivity.this);
                        break;
                    case 7:
                        FingerSignActivity.start(MainActivity.this);
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

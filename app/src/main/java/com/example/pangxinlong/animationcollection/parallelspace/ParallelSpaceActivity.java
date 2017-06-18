package com.example.pangxinlong.animationcollection.parallelspace;

import com.example.pangxinlong.animationcollection.MainActivity;
import com.example.pangxinlong.animationcollection.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pangxinlong on 2017/6/17.
 */

public class ParallelSpaceActivity extends AppCompatActivity {


    @BindView(R.id.vg_boot_page)
    ViewPager mVgBootPage;

    private List<ImageView> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallel_space);
        ButterKnife.bind(this);

        for (int i = 0; i < 4; i++) {

            ImageView imageView = new ImageView(ParallelSpaceActivity.this);
            imageView.setBackground(getResources().getDrawable(R.mipmap.ic_launcher));
            mList.add(imageView);
        }

        mVgBootPage.setAdapter(new MyAdapter());
        mVgBootPage.setPageTransformer(true, new DepthPageTransformer());
    }


    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position));
            return mList.get(position);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView(mList.get(position));
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }


    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ParallelSpaceActivity.class);
        context.startActivity(intent);
    }
}

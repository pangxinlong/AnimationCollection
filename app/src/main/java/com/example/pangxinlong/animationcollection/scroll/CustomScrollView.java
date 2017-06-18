package com.example.pangxinlong.animationcollection.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by pangxinlong on 2017/6/17.
 */

public class CustomScrollView extends ScrollView {

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private CustomLinearLayout mCustomLinearLayout;

    private float mHeight;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCustomLinearLayout = (CustomLinearLayout) getChildAt(0);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int childCount = mCustomLinearLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = mCustomLinearLayout.getChildAt(i);
            if (view instanceof OnScrollListener) {
                float viewTop = view.getTop() - t;//view到屏幕顶端的距离   scrollview顶部的距离-滑动距离
                float childCurrentHeight = mHeight - viewTop;//当前子view从底部漏出屏幕的高度
                float ratio = clamp((childCurrentHeight / (float) view.getHeight()), 1f, 0f);
                if (childCurrentHeight <= mHeight) {
                    ((OnScrollListener) view).startAnim(ratio);
                } else {
                    ((OnScrollListener) view).resetAnim();
                }
            }
        }
    }

    private float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, max), min);
    }

}

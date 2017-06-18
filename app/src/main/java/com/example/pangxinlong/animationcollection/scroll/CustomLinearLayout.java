package com.example.pangxinlong.animationcollection.scroll;

import com.example.pangxinlong.animationcollection.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by pangxinlong on 2017/6/17.
 */

public class CustomLinearLayout extends LinearLayout {


    public CustomLinearLayout(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new FilterLayoutParams(getContext(), attrs);
    }

    public void addView(View child, ViewGroup.LayoutParams params) {
        FilterLayoutParams param = (FilterLayoutParams) params;
        if (isContainAnim(param)) {
            HideLayout hideLayout = new HideLayout(getContext());
            hideLayout.addView(child);
            hideLayout.setIsAlpha(param.mIsAlpha);
            hideLayout.setIsScaleX(param.mIsScaleX);
            hideLayout.setIsScaleY(param.mIsScaleY);
            hideLayout.setTranslationValue(param.translateValue);
            super.addView(hideLayout, params);
        } else {
            super.addView(child, params);
        }
    }

    private boolean isContainAnim(FilterLayoutParams param) {
        return param.mIsAlpha || param.mIsScaleX || param.mIsScaleY || param.translateValue != -1;
    }


    class FilterLayoutParams extends LinearLayout.LayoutParams {

        //属性变量
        private boolean mIsAlpha;

        private boolean mIsScaleX;

        private boolean mIsScaleY;

        private int translateValue;

        public FilterLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.CustomScrollView);
            mIsAlpha = typedArray.getBoolean(R.styleable.CustomScrollView_isalpha, false);
            mIsScaleX = typedArray.getBoolean(R.styleable.CustomScrollView_isscale_x, false);
            mIsScaleY = typedArray.getBoolean(R.styleable.CustomScrollView_isscale_y, false);
            translateValue = typedArray.getInt(R.styleable.CustomScrollView_translateValue,-1);
            typedArray.recycle();
        }

    }


}

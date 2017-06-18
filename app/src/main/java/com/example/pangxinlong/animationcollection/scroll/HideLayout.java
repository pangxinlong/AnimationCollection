package com.example.pangxinlong.animationcollection.scroll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

/**
 * Created by pangxinlong on 2017/6/18.
 * 添加隐藏布局
 */

public class HideLayout extends FrameLayout implements OnScrollListener {

    private final int TRANSLATION_LEFT = 0x01;

    private final int TRANSLATION_TOP = 0x02;

    private final int TRANSLATION_RIGHT = 0x04;

    private final int TRANSLATION_BOTTOM = 0x08;

    private boolean mIsAlpha;

    private boolean mIsScaleX;

    private boolean mIsScaleY;

    private int mTranslationValue;

    public HideLayout(@NonNull Context context) {
        super(context);
    }

    public void setIsScaleX(boolean isScaleX) {
        mIsScaleX = isScaleX;
    }

    public void setIsScaleY(boolean isScaleY) {
        mIsScaleY = isScaleY;
    }

    public void setIsAlpha(boolean alpha) {
        mIsAlpha = alpha;
    }

    public void setTranslationValue(int translationValue) {
        mTranslationValue = translationValue;
    }


    private float mWidth;

    private float mHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        resetAnim();
    }

    @Override
    public void startAnim(float ratio) {
        if (mIsAlpha) {
            setAlpha(ratio);
        }
        if (mIsScaleX) {
            setScaleX(ratio);
        }

        if (mIsScaleY) {
            setScaleY(ratio);
        }

        if (mTranslationValue != -1) {
            if ((mTranslationValue & TRANSLATION_LEFT) == TRANSLATION_LEFT) {
                setTranslationX(mWidth * (ratio - 1));
            }

            if ((mTranslationValue & TRANSLATION_TOP) == TRANSLATION_TOP) {
                setTranslationY(mHeight * (ratio - 1));
            }

            if ((mTranslationValue & TRANSLATION_RIGHT) == TRANSLATION_RIGHT) {
                setTranslationX(mWidth * (1 - ratio));
            }

            if ((mTranslationValue & TRANSLATION_BOTTOM) == TRANSLATION_BOTTOM) {
                setTranslationY(mHeight * (1 - ratio));
            }
        }
    }

    @Override
    public void resetAnim() {
        if (mIsAlpha) {
            setAlpha(0);
        }
        if (mIsScaleX) {
            setScaleX(0);
        }
        if (mIsScaleY) {
            setScaleY(0);
        }
        if (mTranslationValue != -1) {
            if ((mTranslationValue & TRANSLATION_LEFT) == TRANSLATION_LEFT) {
                setTranslationX(mWidth);
            }

            if ((mTranslationValue & TRANSLATION_TOP) == TRANSLATION_TOP) {
                setTranslationY(mHeight);
            }

            if ((mTranslationValue & TRANSLATION_RIGHT) == TRANSLATION_RIGHT) {
                setTranslationX(-mWidth);
            }

            if ((mTranslationValue & TRANSLATION_BOTTOM) == TRANSLATION_BOTTOM) {
                setTranslationY(-mHeight);
            }
        }
    }
}

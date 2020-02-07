package com.example.pangxinlong.animationcollection.zan;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.util.Random;

import cn.uniwa.uniwa.R;
import cn.uniwa.uniwa.base.BaseBean;
import cn.uniwa.uniwa.bean.teacherhome.TeacherLivingZanBean;
import cn.uniwa.uniwa.model.common.impl.CommonModelImpl;
import cn.uniwa.uniwa.net.CommonDataListener;

/**
 * Date: 2019-11-27
 * author: pangxinlong
 * Description: 点赞view
 */
public class ThumbsUpView extends FrameLayout {

    private Context context;
    private TextView tvZanNum;
    private ImageView ivZan;

    private int[] imgSource;
    private float translationY = 1000f;

    public ThumbsUpView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ThumbsUpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ThumbsUpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        imgSource = new int[]{R.mipmap.ic_living_zan1, R.mipmap.ic_living_zan2,
                R.mipmap.ic_living_zan3, R.mipmap.ic_living_zan4,
                R.mipmap.ic_living_zan5, R.mipmap.ic_living_zan6,
                R.mipmap.ic_living_zan7, R.mipmap.ic_living_zan8,
                R.mipmap.ic_living_zan9, R.mipmap.ic_living_zan10,
                R.mipmap.ic_living_zan11};
        LayoutInflater.from(context).inflate(R.layout.common_zan_item, this, true);
        tvZanNum = findViewById(R.id.tv_zan_num);
        ivZan = findViewById(R.id.iv_teacher_zan);
    }

    private int zanNum;

    public void setZanNum(int num) {
        tvZanNum.setText(num + "");
        zanNum = num;

    }


    private void createAnimator(ViewGroup viewGroup) {
        final double randomNum = Math.random();
        ImageView imageView = new ImageView(context);
        int value = new Random().nextInt(imgSource.length);
        imageView.setBackgroundResource(imgSource[value]);
        viewGroup.addView(imageView);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        if (viewGroup instanceof FrameLayout) {
            ((LayoutParams) layoutParams).gravity = Gravity.RIGHT | Gravity.BOTTOM;
            layoutParams.height = LayoutParams.WRAP_CONTENT;
            layoutParams.width = LayoutParams.WRAP_CONTENT;
            ((LayoutParams) layoutParams).setMargins(0, 0, getWidth() / 4, ((View) getParent()).getBottom() + getHeight() / 3);
        } else if (viewGroup instanceof LinearLayout) {
            throw new RuntimeException("parent no support LinearLayout");
        } else if (viewGroup instanceof RelativeLayout) {
            ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            ((RelativeLayout.LayoutParams) layoutParams).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            ((RelativeLayout.LayoutParams) layoutParams).setMargins(0, 0, getWidth() / 4, ((View) getParent()).getBottom() + getHeight() / 3);
        }
        imageView.setLayoutParams(layoutParams);

        //startY=自身距离父布局高度+父布局距离上级父布局高度
        final float startY = getY() + ((View) getParent()).getTop();

        //若位移距离高出viewGroup范围，则位移到顶端结束
        translationY = Math.min(startY, translationY);
        final float endY = Math.abs(startY - translationY);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                return startY - fraction * translationY;
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();
                double y = (randomNum * 50 - 25) * Math.sin(value * (Math.PI * 2) / (translationY));
                imageView.setTranslationX((float) y);
                imageView.setY(value);
                imageView.setAlpha(1 - ((startY - value) / translationY));
                if (value == endY) {
                    imageView.setVisibility(View.GONE);
                    viewGroup.removeView(imageView);
                }
            }
        });
        valueAnimator.start();
    }


    private int clickNum = 0;

    public void click(ViewGroup viewGroup) {
        try {
            ivZan.setImageDrawable(getResources().getDrawable(R.mipmap.ic_teacher_livling_zan_red));
            createAnimator(viewGroup);
            clickNum++;
            tvZanNum.setText(zanNum + clickNum + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 点赞
     *
     * @param lectureId
     */
    public void requestZan(final int lectureId) {
        if (clickNum == 0) {
            return;
        }
        CommonModelImpl.getInstance().teacherPagerZan(lectureId, clickNum, new CommonDataListener<TeacherLivingZanBean>() {
            @Override
            public void onSuccess(TeacherLivingZanBean teacherLivingZanBean) {
//                String zanStr = tvZanNum.getText().toString();
//                if (!Util.isEmpty(zanStr)) {
//                    if (teacherLivingZanBean.data > Integer.valueOf(zanStr)) {
//                        tvZanNum.setText(teacherLivingZanBean.data + "");
//                    } else {
//                        tvZanNum.setText(Integer.valueOf(zanStr) + 1 + "");
//                    }
//                    ivZan.setImageDrawable(getResources().getDrawable(R.mipmap.ic_teacher_livling_zan_red));
//                }
//                if (onClickListener != null) {
//                    onClickListener.OnSuccess();
//                }
            }

            @Override
            public void onError(BaseBean baseBean) {
//                ToastUtils.makeText(TeacherPagerActivity.this, baseBean.msg, ToastUtils.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(VolleyError e) {

            }
        });
    }

    public interface OnClickListener {
        void OnSuccess();

        void onError();
    }
}

package com.hldj.hmyg.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.hldj.hmyg.R;
import com.hldj.hmyg.saler.SaveSeedlingActivity;
import com.hldj.hmyg.saler.StorageSaveActivity;
import com.hldj.hmyg.saler.Ui.ManagerQuoteListActivity_new;
import com.jerome.weibo.KickBackAnimator;

/**
 * 发布 pop window
 */

public class PublishPopWindow extends PopupWindow implements View.OnClickListener {

    private View rootView;
    private RelativeLayout contentView;
    private Activity mContext;

    public PublishPopWindow(Activity context) {
        this.mContext = context;
    }



    public void showMoreWindow(View anchor) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.dialog_publish, null);
        rootView.setBackgroundColor(Color.WHITE);
        rootView.setAlpha((float) 0.9);
        int h = mContext.getWindowManager().getDefaultDisplay().getHeight();
        int w = mContext.getWindowManager().getDefaultDisplay().getWidth();
        setContentView(rootView);
        this.setWidth(w);
        this.setHeight(h - 25);

        contentView = (RelativeLayout) rootView.findViewById(R.id.contentView);
        LinearLayout close = (LinearLayout) rootView.findViewById(R.id.ll_close);
        close.setBackgroundColor(0xFFFFFFFF);
        close.setOnClickListener(this);
        showAnimation(contentView);
        setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.trans_bg));
        setOutsideTouchable(true);
        setFocusable(true);
        new Handler().postDelayed(() -> {
            RotateAnimation rotateAnim = new RotateAnimation(0, 45, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnim.setDuration(300);
            rotateAnim.setFillAfter(true);
            rootView.findViewById(R.id.iv_jiahao).setAnimation(rotateAnim);
            rootView.findViewById(R.id.iv_jiahao).startAnimation(rotateAnim);
//            ObjectAnimator animator = new ObjectAnimator();
//            animator.setDuration(300);
            /**
             * RotateAnimation  rotateAnim =newRotateAnimation(0,-720,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);

             TranslateAnimation translateAnim=newTranslateAnimation(Animation.ABSOLUTE,0,Animation.ABSOLUTE,-80,Animation.ABSOLUTE,0,Animation.ABSOLUTE,-80);

             AnimationSet set=newAnimationSet(false);

             set.addAnimation(translateAnim);

             set.addAnimation(rotateAnim);

             set.setFillAfter(true);

             set.setDuration(300);

             less.startAnimation(set);
             */
        }, 300);


        showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 显示进入动画效果
     *
     * @param layout
     */
    private void showAnimation(ViewGroup layout) {
        //遍历根试图下的一级子试图
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
            //忽略关闭组件
            if (child.getId() == R.id.ll_close) {
                continue;
            }
            //设置所有一级子试图的点击事件
            child.setOnClickListener(this);
            child.setVisibility(View.INVISIBLE);
            //延迟显示每个子试图(主要动画就体现在这里)


            new Handler().postDelayed(() -> {
                child.setVisibility(View.VISIBLE);
                ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                fadeAnim.setDuration(300);
                KickBackAnimator kickAnimator = new KickBackAnimator();
                kickAnimator.setDuration(150);
                fadeAnim.setEvaluator(kickAnimator);
                fadeAnim.start();

            }, i * 50);


        }

    }

    /**
     * 关闭动画效果
     *
     * @param layout
     */
    private void closeAnimation(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
            if (child.getId() == R.id.ll_close) {
                continue;
            }
            new Handler().postDelayed(() -> {
                child.setVisibility(View.VISIBLE);
                ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
                fadeAnim.setDuration(200);
                KickBackAnimator kickAnimator = new KickBackAnimator();
                kickAnimator.setDuration(100);
                fadeAnim.setEvaluator(kickAnimator);
                fadeAnim.start();
                fadeAnim.addListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        child.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }
                });

            }, (layout.getChildCount() - i - 1) * 30);


            new Handler().postDelayed(() -> {

                dismiss();

            }, (layout.getChildCount() - i) * 30 + 80);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publish_flower:
                SaveSeedlingActivity.start2Activity(mContext);
                break;
            case R.id.publish_manager:
                ManagerQuoteListActivity_new.start2Activity(mContext);
                break;
            case R.id.publish_storage:
                StorageSaveActivity.start2Activity(mContext);
                break;
            case R.id.ll_close:
                if (isShowing()) closeAnimation(contentView);
                break;
            default:
                break;
        }
    }



    private void goCreate() {

        new Handler().postDelayed(() -> {
            closeAnimation(contentView);

        }, 500);


    }
}

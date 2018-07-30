package com.hldj.hmyg.util;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.hldj.hmyg.saler.M.PurchaseBean;
import com.hy.utils.ToastUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * https://blog.csdn.net/oQiHaoGongYuan/article/details/49869137
 */

public class AnimationUtil {
    /* 0 到 180 °  y轴旋转 开启 */
    private Animation animationOpen;
    /* 180 到  0 °  y轴旋转 关闭 */
    private Animation animationClose;


    private Disposable mDisposable;


    private long duration = 1000;


    private View openView;
    private View closeView;

    private int delayTime = 0;

    public void setDelayTime(int time) {
        this.delayTime = time;
    }

    public AnimationUtil(View openView, View closeView) {
        this.openView = openView;
        this.closeView = closeView;
        animationOpen = new ScaleAnimation(1, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        configAnimation(animationOpen);


        animationClose = new ScaleAnimation(1, 1, 1, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        configAnimation(animationClose);

    }

    private void doCountDown() {

        poase();


        try {

            if (delayTime != 0 )
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Observable.interval(delayTime, 6000, TimeUnit.MILLISECONDS)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDisposable = disposable;

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        D.i("========accept=====time=====" + aLong);


                        if (closeView.getVisibility() == View.VISIBLE) {

                            ToastUtil.showLongToast("");

                            // 显示  view  执行关闭动画

                            animationClose.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    closeView.setVisibility(View.INVISIBLE);
                                    openView.setVisibility(View.VISIBLE);
                                    openView.startAnimation(animationOpen);
//                                    closeView.setAnimation(null);

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });

                            closeView.startAnimation(animationClose);

                        } else {

                            //隐藏 view  执行 开启动画

                            animationClose.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    openView.setVisibility(View.INVISIBLE);
                                    closeView.setVisibility(View.VISIBLE);

                                    closeView.startAnimation(animationOpen);
//                                    openView.setAnimation(null);

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });

                            openView.startAnimation(animationClose);

                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtil.showLongToast(throwable.getMessage());
                    }
                })
        ;
    }


    public void configAnimation(Animation animation) {

        animation.setDuration(duration);
        animation.setFillAfter(false);

    }


    public void poase() {

        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }

    }


    public int currentPos = 0;
    public Disposable couDownDisp;


    public void setCountDownDatas(TextView downTv, List<PurchaseBean> purchaseList) {


//        List<PurchaseBean> ddd = new ArrayList<>();


//        purchaseList.addAll(ddd);

        if (couDownDisp != null && !couDownDisp.isDisposed()) {
            couDownDisp.dispose();
        }

        Observable
                .interval(2000, 2000, TimeUnit.MILLISECONDS)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        couDownDisp = disposable;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (openView.getVisibility() == View.VISIBLE) {
                            currentPos++;
                            Log.i("interval", "accept: " + currentPos % purchaseList.size());
                            PurchaseBean userPurchase = purchaseList.get(currentPos % purchaseList.size());
                            downTv.setText(userPurchase.name);
                        }


                    }
                });
    }

    public void resume() {
        doCountDown();
    }

}

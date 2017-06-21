package com.hldj.hmyg.buyer.weidet;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class MySHSwipeRefreshLayout extends SHSwipeRefreshLayout implements NestedScrollingChild {
    private NestedScrollingChildHelper childHelper;
    private float ox;
    private float oy;

    private int[] consumed = new int[2];
    private int[] offsetInWindow = new int[2];

    public MySHSwipeRefreshLayout(Context context) {
        super(context);
    }

    public MySHSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySHSwipeRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MySHSwipeRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction() == 0) {
            this.ox = ev.getX();
            this.oy = ev.getY();
            this.startNestedScroll(3);
        }

        if(ev.getAction() == 1 || ev.getAction() == 3) {
            this.stopNestedScroll();
        }

        if(ev.getAction() == 2) {
            float clampedX = ev.getX();
            float clampedY = ev.getY();
            int dx = (int)(this.ox - clampedX);
            int dy = (int)(this.oy - clampedY);
            if(this.dispatchNestedPreScroll(dx, dy, this.consumed, this.offsetInWindow)) {
                ev.setLocation(clampedX + (float)this.consumed[0], clampedY + (float)this.consumed[1]);
            }

            this.ox = ev.getX();
            this.oy = ev.getY();
        }

        return super.onTouchEvent(ev);
    }

    public void openNesScroll(boolean flag){
         this.childHelper = new NestedScrollingChildHelper(this);
         this.childHelper.setNestedScrollingEnabled(flag);
     }

    private void initNesChild() {
        setNestedScrollingEnabled(true);
    }


    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        super.setNestedScrollingEnabled(enabled);
    }



    @Override
    public boolean isNestedScrollingEnabled() {
        return super.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return super.startNestedScroll(axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
        super.onStopNestedScroll(child);
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return super.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable @Size(value = 2) int[] offsetInWindow) {
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable @Size(value = 2) int[] consumed, @Nullable @Size(value = 2) int[] offsetInWindow) {
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return super.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return super.dispatchNestedPreFling(velocityX, velocityY);
    }
}

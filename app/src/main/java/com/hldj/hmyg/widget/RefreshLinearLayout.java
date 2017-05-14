package com.hldj.hmyg.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/5/14 0014.
 */

public class RefreshLinearLayout extends LinearLayout {

    private float lastY;
    private float downY;
    private float moveY;
    private float destY;
    // 头部滚动的view
    private View headerView;
    // 能滚动的view
    private View contentView;
    // 头部高度
    private int headerViewHeight;
    // listview scrollView...
    private View mTarget;
    // 是否第一次
    private boolean isFrist = true;
    // 能刷新不
    private boolean isCanRefresh;
    // 能加载更多不
    private boolean isCanMore;
    // 是否满足最小滚动距离
    private boolean isTouchSlopOk;

    // 滚动辅助类
    private Scroller mScroller;
    private int mTouchSlop;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            autoScrollerHandler();
        };
    };
    private View fixedView;

    public RefreshLinearLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public RefreshLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public RefreshLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    private void initView(Context context) {
        // TODO Auto-generated method stub
        mScroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat
                .getScaledPagingTouchSlop(viewConfiguration);
    }

    /**
     * 控制父view 的滚动
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeCallbacksAndMessages(null);
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                downY = lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = ev.getY();
                destY = moveY - lastY;
                // 条件判断
                judgeCondination();
                if (!isCanRefresh && !isCanMore) {
                    if (!isTouchSlopOk) {
                        final float touchDestY = downY - moveY;
                        if (Math.abs(touchDestY) > mTouchSlop) {
                            isTouchSlopOk = true;
                        }
                    } else {
                        // 处理跟随滚动
                        handleMove();
                    }
                }
                lastY = moveY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                resetState();
                // 为了使头部在滚动view到头部时一定可见
                handler.sendMessageDelayed(handler.obtainMessage(), 1000);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        handler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }

    /**
     * 自动还原处理 不出现显示部分的情况
     */
    private void autoScrollerHandler() {
        // TODO Auto-generated method stub
        final int scrollY = getScrollY();
        if (mTarget instanceof AbsListView) {
            final AbsListView absListView = (AbsListView) mTarget;
            int firstVisiblePosition = absListView.getFirstVisiblePosition();
            if (firstVisiblePosition == 0) {
                View childView = getChildAt(0);
                if (childView.getTop() < headerViewHeight) {// 显示
                    if (scrollY != 0)
                        mScroller.startScroll(0, scrollY, 0, -scrollY);
                } else {// 自动判断
                    // conditionScroll(scrollY);
                }
            } else {
                // conditionScroll(scrollY);
            }
        } else if (mTarget instanceof ScrollView) {// scrollview .....
            final ScrollView scrollView = (ScrollView) mTarget;
            if (scrollView.getScrollY() < headerViewHeight) {
                if (scrollY != 0)
                    mScroller.startScroll(0, scrollY, 0, -scrollY);
            } else {
                // conditionScroll(scrollY);
            }
        }

        ViewCompat.postInvalidateOnAnimation(this);
    }

    /**
     * 暂时不用了
     *
     * @param scrollY
     */
    private void conditionScroll(final int scrollY) {
        if (scrollY < headerViewHeight / 2) {// show
            if (scrollY != 0)
                mScroller.startScroll(0, scrollY, 0, -scrollY);
        } else {// hide
            if (headerViewHeight != scrollY)
                mScroller
                        .startScroll(0, scrollY, 0, headerViewHeight - scrollY);
        }
    }

    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            int currY = mScroller.getCurrY();
            scrollTo(0, currY);
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    /**
     * 还原状态
     */
    private void resetState() {
        isFrist = true;
        isCanRefresh = true;
        isCanMore = true;
        isTouchSlopOk = false;
    }

    /**
     * 处理跟随滚动
     */
    private void handleMove() {
        final int scrollY = getScrollY();
        int sy = (int) (-destY * 1.15f);
        if (destY < 0) {// 向上滑
            final int futureY = scrollY + sy;
            if (futureY > headerViewHeight) {
                sy = headerViewHeight - scrollY;
            }
            if (scrollY < headerViewHeight) {
                scrollBy(0, sy);
            }
        } else {// 向下滑
            final int futureY = scrollY + sy;
            if (futureY < 0) {
                sy = -scrollY;
            }
            if (scrollY > 0) {
                scrollBy(0, sy);
            }
        }
    }

    /**
     * 滚动前的必要条件判断
     */
    private void judgeCondination() {
        if (isFrist && destY != 0) {
            isCanRefresh = !canChildScrollUp();
            // 向上还是能滚动
            if (destY < 0) {
                isCanRefresh = false;
            }
            isCanMore = !canChildScrollDown();
            // 向下还是能滚动
            if (destY > 0) {
                isCanMore = false;
            }
            isFrist = false;
        }
    }

    /**
     * 还能否向上滚动
     *
     * @return
     */
    private boolean canChildScrollUp() {
        if (mTarget == null) {
            return true;
        }
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView
                        .getChildAt(0).getTop() < absListView
                        .getPaddingTop());
            } else {
                return mTarget.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

    /**
     * 还能否向上滚动
     *
     * @return
     */
    private boolean canChildScrollDown() {
        if (mTarget == null) {
            return true;
        }
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                final int lastVisiblePosition = absListView
                        .getLastVisiblePosition();
                final int childIndex = lastVisiblePosition
                        - absListView.getFirstVisiblePosition();
                return absListView.getChildCount() > 0
                        && (lastVisiblePosition < absListView.getCount() || absListView
                        .getChildAt(childIndex).getBottom() > absListView
                        .getHeight() - absListView.getPaddingTop());
            } else {
                return mTarget.getScrollY() < mTarget.getHeight() - getHeight();
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, 1);
        }
    }

    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        super.onFinishInflate();
        if (getChildCount() > 2) {
            throw new IllegalStateException(
                    "RefreshLinearlayout 只能拥有两个childview");
        }
        headerView = getChildAt(0);
        contentView = getChildAt(getChildCount() - 1);
    }

    /**
     * 设置能滚动的view
     *
     * @param view
     */
    public void setTargetView(View view) {
        this.mTarget = view;
    }

    /**
     * 设置固定的view
     * @param view
     */

    public void setFixedView(View view) {
        this.fixedView = view;
    }

    /**
     * 拉长容器
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getDefaultSize(0, widthMeasureSpec);
        // 一个头一个容器
        measureChild(headerView, widthMeasureSpec, heightMeasureSpec);
        measureChild(contentView, widthMeasureSpec, heightMeasureSpec);
        headerViewHeight = headerView.getMeasuredHeight();
        int measuredHeight = getDefaultSize(0, heightMeasureSpec)
                + headerViewHeight;
        setMeasuredDimension(measuredWidth, measuredHeight);

        if (fixedView != null) {
            headerViewHeight -= fixedView.getMeasuredHeight();
        }
    }
}

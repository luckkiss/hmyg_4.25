package com.hldj.hmyg.buyer.weidet;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.buyer.weidet.animation.BaseAnimation;
import com.hldj.hmyg.buyer.weidet.listener.OnItemClickListener;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.swipeview.MySwipeRefreshLayout;


/**
 * Created by 罗擦擦 on 16/11/1.
 */

public class CoreRecyclerView extends LinearLayout implements BaseQuickAdapter.RequestLoadMoreListener, MySwipeRefreshLayout.SHSOnRefreshListener {
    private RecyclerView mRecyclerView;
    private MySwipeRefreshLayout mSwipeRefreshLayout;
    BaseQuickAdapter mQuickAdapter;
    addDataListener addDataListener;
    RefreshListener refreshListener;
    private SwipeViewHeader mViewHeader;
    private boolean isLazyShowEmpty = false;// 是否懒加载 empty 在第一次 加载数据成功之后  设置 default empty view

    public CoreRecyclerView addRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
        return this;
    }

    public interface RefreshListener {
        void refresh();
    }

    public interface addDataListener {
        void addData(int page);
    }

    private View notLoadingView;
    private int page;

    public CoreRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public CoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CoreRecyclerView initView(Context context) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_recyclerview, null);

        view.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(view);
        mSwipeRefreshLayout = (MySwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshEnable(false);
        mSwipeRefreshLayout.setLoadmoreEnable(false);
        mViewHeader = new SwipeViewHeader(context);
        mSwipeRefreshLayout.setHeaderView(mViewHeader);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);

        return this;
    }

    public CoreRecyclerView init(BaseQuickAdapter mQuickAdapter) {
        init(null, mQuickAdapter);
        return this;
    }

    public CoreRecyclerView init(BaseQuickAdapter mQuickAdapter, Boolean isRefresh) {
        init(null, mQuickAdapter, true);
        return this;
    }

    public CoreRecyclerView init(RecyclerView.LayoutManager layoutManager, BaseQuickAdapter mQuickAdapter) {
        init(layoutManager, mQuickAdapter, true);
        return this;
    }

    public CoreRecyclerView init(RecyclerView.LayoutManager layoutManager, BaseQuickAdapter mQuickAdapter, Boolean isRefresh) {
        if (isRefresh != true) {
            mSwipeRefreshLayout.setVisibility(GONE);
            mRecyclerView = (RecyclerView) findViewById(R.id.rv_list1);
            mRecyclerView.setVisibility(VISIBLE);
        }
        mRecyclerView.setLayoutManager(layoutManager != null ? layoutManager : new LinearLayoutManager(getContext()));
        this.mQuickAdapter = mQuickAdapter;
        mRecyclerView.setAdapter(mQuickAdapter);
        mQuickAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mQuickAdapter);


        D.e("=======CoreRecyclerView====init===========设置默认的空数据界面==");
        setDefaultEmptyView();

        return this;
    }

    public CoreRecyclerView addOnItemClickListener(OnItemClickListener onItemClickListener) {
        mRecyclerView.addOnItemTouchListener(onItemClickListener);
        return this;
    }


    public static final int REFRESH = 100;
    public static final int LOAD_MORE = 1001;

    @Override
    public void onRefresh() {
        page = 0;
//      mQuickAdapter.getData().clear();
//      mQuickAdapter.notifyDataSetChanged();//如果直接刷新会闪一下
//        datasState = REFRESH;//刷新时
        mQuickAdapter.setDatasState(REFRESH);

        if (addDataListener != null) {
            addDataListener.addData(0);
        }

        if (refreshListener != null) {
            refreshListener.refresh();
        }
        mQuickAdapter.openLoadMore(mQuickAdapter.getPageSize());
        mQuickAdapter.removeAllFooterView();
//        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onRefreshPulStateChange(float parent, int state) {
        switch (state) {
            case MySwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
//                mViewHeader.setLoaderViewText("下拉刷新");
                mViewHeader.setState(0);
                break;
            case MySwipeRefreshLayout.OVER_TRIGGER_POINT:
//                swipeRefreshLayout.setLoaderViewText("松开刷新");
                mViewHeader.setState(1);
                break;
            case MySwipeRefreshLayout.START:
//                swipeRefreshLayout.setLoaderViewText("正在刷新");
                mViewHeader.setState(2);
                break;
        }
    }

    @Override
    public void onLoadmorePullStateChange(float var1, int var2) {

    }

    @Override
    //添加refresh 接口回调

    public void onLoadMoreRequested() {
        mRecyclerView.post(() -> {
            if (mQuickAdapter.getData().size() < page * mQuickAdapter.getPageSize()) {
                mQuickAdapter.loadComplete();
                if (notLoadingView == null) {
                    notLoadingView = LayoutInflater.from(getContext()).inflate(R.layout.not_loading, (ViewGroup) mRecyclerView.getParent(), false);
                }
                mQuickAdapter.addFooterView(notLoadingView);
            } else {
                addDataListener.addData(page);

            }
        });
        page += 1;
    }

    public BaseQuickAdapter getAdapter() {
        return mQuickAdapter;
    }

    public CoreRecyclerView addFooterView(View footer) {
        mQuickAdapter.addFooterView(footer);
        return this;
    }

    public CoreRecyclerView addFooterView(View footer, int index) {
        mQuickAdapter.addFooterView(footer, index);
        return this;
    }

    public CoreRecyclerView addHeaderView(View header) {
        mQuickAdapter.addHeaderView(header);
        return this;
    }

    public CoreRecyclerView addHeaderView(View header, int index) {
        mQuickAdapter.addHeaderView(header, index);
        return this;
    }

    public CoreRecyclerView addHeaderView(View header, int index, int orientation) {
        mQuickAdapter.addHeaderView(header, index, orientation);
        return this;
    }

    public CoreRecyclerView hideLoadingMore() {
        mQuickAdapter.hideLoadingMore();
        return this;
    }

    public CoreRecyclerView loadComplete() {
        mQuickAdapter.loadComplete();
        return this;
    }

    public CoreRecyclerView openLoadAnimation() {
        mQuickAdapter.openLoadAnimation();
        return this;
    }

    public CoreRecyclerView openLoadAnimation(BaseAnimation animation) {
        mQuickAdapter.openLoadAnimation(animation);
        return this;
    }

    public CoreRecyclerView openLoadAnimation(@BaseQuickAdapter.AnimationType int animationType) {
        mQuickAdapter.openLoadAnimation(animationType);
        return this;
    }

    public CoreRecyclerView openLoadMore(int pageSize, addDataListener addDataListener) {
//        this.data = data == null ? new ArrayList<T>() : data;
        this.addDataListener = addDataListener;
        mQuickAdapter.openLoadMore(pageSize);
        mQuickAdapter.setOnLoadMoreListener(this);
        return this;
    }

    public CoreRecyclerView remove(int position) {
        mQuickAdapter.remove(position);
        return this;
    }

    public CoreRecyclerView removeAllFooterView() {
        mQuickAdapter.removeAllFooterView();
        return this;
    }

    public CoreRecyclerView removeAllHeaderView() {
        mQuickAdapter.removeAllHeaderView();
        return this;
    }

    public CoreRecyclerView removeFooterView(View footer) {
        mQuickAdapter.removeFooterView(footer);
        return this;
    }

    public CoreRecyclerView removeHeaderView(View header) {
        mQuickAdapter.removeHeaderView(header);
        return this;
    }

    public CoreRecyclerView setDuration(int duration) {
        mQuickAdapter.setDuration(duration);
        return this;
    }

    public CoreRecyclerView setEmptyView(boolean isHeadAndEmpty, boolean isFootAndEmpty, View emptyView) {
        mQuickAdapter.setEmptyView(isHeadAndEmpty, isFootAndEmpty, emptyView);
        return this;
    }

    public CoreRecyclerView setEmptyView(boolean isHeadAndEmpty, View emptyView) {
        mQuickAdapter.setEmptyView(isHeadAndEmpty, emptyView);
        return this;
    }


    public CoreRecyclerView closeDefaultEmptyView() {
        mQuickAdapter.setEmptyView(null);
        return this;
    }

    /* 默认是 关闭的   */
    public CoreRecyclerView lazyShowEmptyViewEnable(boolean isLazy) {
        isLazyShowEmpty = isLazy;
        if (isLazy) {
            closeDefaultEmptyView();
        }
        return this;
    }


    public CoreRecyclerView setDefaultEmptyView() {
//        View empty = LayoutInflater.from(getContext()).inflate(R.layout.dialog_custom_base, null);
//        setLoadingView(empty);
        return setDefaultEmptyView(false, null, null);
    }

    public void setRetryText() {

    }

    private boolean lazy_isOpenRetryButton = false;
    private String lazy_text = "";
    private View.OnClickListener lazy_retry;


    public CoreRecyclerView setDefaultEmptyView(boolean isOpenRetryButton, String text, View.OnClickListener retry) {
        View empty = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null);
        this.lazy_isOpenRetryButton = isOpenRetryButton;
        this.lazy_text = text;
        this.lazy_retry = retry;
        if (isOpenRetryButton) {
            TextView textView = empty.findViewById(R.id.go_to);
            textView.setVisibility(VISIBLE);
            if (!TextUtils.isEmpty(text)) {
                textView.setText(text);
            }
            textView.setOnClickListener(retry);
        }
//        View empty  = getContext(). inflate(R.layout.empty_view, null);
        empty.setOnClickListener(view1 -> onRefresh());
        final RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        final ViewGroup.LayoutParams lp = empty.getLayoutParams();
        if (lp != null) {
            layoutParams.width = lp.width;
            layoutParams.height = lp.height;
        }
        empty.setLayoutParams(layoutParams);
        mQuickAdapter.setEmptyView(empty);
        return this;
    }

    private CharSequence lazy_tip = "";

    public CoreRecyclerView showEmptyAndSetTip(CharSequence str) {
        this.lazy_tip = str;
        if (mQuickAdapter.getEmptyView() != null) {
            TextView t_tipTextView = mQuickAdapter.getEmptyView().findViewById(R.id.t_tipTextView);
            if (t_tipTextView != null) {
                t_tipTextView.setVisibility(VISIBLE);
                t_tipTextView.setText(str);
            }
        }
        return this;
    }

    private CharSequence lazy_emptyText;

    public CoreRecyclerView setEmptyText(CharSequence str) {
//        if (mQuickAdapter.getEmptyView() != null) {
//            TextView t_tipTextView = mQuickAdapter.getEmptyView().findViewById(R.id.t_tipTextView);
//            if (t_tipTextView != null) {
//                t_tipTextView.setVisibility(VISIBLE);
//                t_tipTextView.setText(str);
//            }
//        }
        this.lazy_emptyText = str;
        TextView tv_empty_err = (TextView) mQuickAdapter.getEmptyView().findViewById(R.id.t_emptyTextView);
        if (tv_empty_err != null) {
            if (!TextUtils.isEmpty(str))
                tv_empty_err.setText(str);
        }
//        this.getAdapter().notifyDataSetChanged();
        return this;
    }


    public CoreRecyclerView setEmptyView(View emptyView) {
        View empty = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null);
//        View empty  = getContext(). inflate(R.layout.empty_view, null);
        empty.setOnClickListener(view1 -> onRefresh());
        final RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        final ViewGroup.LayoutParams lp = emptyView.getLayoutParams();
        if (lp != null) {
            layoutParams.width = lp.width;
            layoutParams.height = lp.height;
        }
        emptyView.setLayoutParams(layoutParams);
        mQuickAdapter.setEmptyView(emptyView);
        return this;
    }

    public CoreRecyclerView setLoadingView(View loadingView) {
        mQuickAdapter.setLoadingView(loadingView);
        return this;
    }

    public CoreRecyclerView setLoadMoreFailedView(View view) {
        mQuickAdapter.setLoadMoreFailedView(view);
        return this;
    }

    public CoreRecyclerView setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener) {
        mQuickAdapter.setOnLoadMoreListener(requestLoadMoreListener);
        return this;
    }

    public CoreRecyclerView showLoadMoreFailedView() {

        if (page == 0) {
            Log.w("CoreRecyclerView", "showLoadMoreFailedView: page == 0   dont refresh");
            return this;
        } else {
            Log.w("CoreRecyclerView", "showLoadMoreFailedView: page == +" + page + "   refresh  page -- ");
            page -= 1;
            mQuickAdapter.showLoadMoreFailedView();
        }

        return this;
    }

    public CoreRecyclerView startAnim(Animator anim, int index) {
        mQuickAdapter.startAnim(anim, index);
        return this;
    }

    public CoreRecyclerView openRefresh() {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setRefreshEnable(true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return this;
    }

    public CoreRecyclerView closeRefresh() {
        mSwipeRefreshLayout.setRefreshEnable(false);
        mSwipeRefreshLayout.setOnRefreshListener(null);
        return this;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    public CoreRecyclerView selfRefresh(boolean b) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            new Handler().postDelayed(() -> mSwipeRefreshLayout.finishRefresh(), 500);
            mViewHeader.setState(3);
        }

        if (!b && isLazyShowEmpty) {
            setDefaultEmptyView(lazy_isOpenRetryButton, lazy_text, lazy_retry);
            if (!TextUtils.isEmpty(lazy_tip)) {
                showEmptyAndSetTip(lazy_tip);
            }
            if (!TextUtils.isEmpty(lazy_emptyText)) {
                setEmptyText(lazy_emptyText);
            }
        }


        checkIsOpenRefresh();

        return this;
    }


    private void checkIsOpenRefresh() {
        if (getAdapter().getData().size() > 0) {
            openRefresh();
        } else {
            closeRefresh();
        }
    }


    public CoreRecyclerView selfRefresh(boolean b, String errMsg) {
        this.selfRefresh(b);
        TextView tv_empty_err = (TextView) mQuickAdapter.getEmptyView().findViewById(R.id.t_emptyTextView);
        if (tv_empty_err != null) {
            if (!TextUtils.isEmpty(errMsg))
                tv_empty_err.setText(errMsg);
        }
        return this;
    }

    public CoreRecyclerView selfRefresh(boolean b, String errMsg, boolean openRetryButton) {
        this.selfRefresh(b);
        TextView tv_empty_err = (TextView) mQuickAdapter.getEmptyView().findViewById(R.id.t_emptyTextView);
        if (tv_empty_err != null) {
            if (!TextUtils.isEmpty(errMsg))
                tv_empty_err.setText(errMsg);
        }
        return this;
    }


    public CoreRecyclerView setNoData(String erMst) {
        this.setDefaultEmptyView();
        this.selfRefresh(false, erMst);
        this.getAdapter().notifyDataSetChanged();
        return this;
    }

    public boolean isDataNull() {

        return this.getAdapter().getData().size() == 0;

    }


}


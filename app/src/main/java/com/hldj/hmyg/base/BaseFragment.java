package com.hldj.hmyg.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.weavey.loading.lib.LoadingLayout;

/**
 * Created by Administrator on 2017/5/22.
 */

public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;

    // fragment是否显示了
    protected boolean mIsVisible = false;


    public View rootView;

    LoadingLayout loadingLayout;
    private View loadPage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(bindLayoutID(), null);
        this.rootView = mRootView;
        initView(mRootView);
        initLoadingView(rootView);
        initListener();
        D.e("======当前Fragment===位置=====" + this.getClass().getName());
        return rootView;
    }

    protected void initListener() {

    }

    // 获取loading view
    protected final void initLoadingView(View rootView) {
        loadingLayout = rootView.findViewById(bindLoadingLayout());
        //自定义刷新界面
        loadPage = LayoutInflater.from(mActivity).inflate(R.layout.load_dialog, null);


        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        final ViewGroup.LayoutParams lp = loadPage.getLayoutParams();
        if (lp != null) {
            layoutParams.width = lp.width;
            layoutParams.height = lp.height;
        }
        loadPage.setLayoutParams(layoutParams);

        if (loadingLayout != null) {
            loadingLayout.setLoadingPage(loadPage);
            loadingLayout.setOnReloadListener(v -> loadData());

//            默认进入页面就开启动画
//            if (!mAnimationDrawable.isRunning()) {
//                mAnimationDrawable.start();
//            }
            /**
             *   ImageView img = (ImageView) this.findViewById(R.id.iv_amin_flowar);

             // 加载动画
             mAnimationDrawable = (AnimationDrawable) img.getDrawable();
             默认进入页面就开启动画
             if (!mAnimationDrawable.isRunning()) {
             mAnimationDrawable.start();
             }
             */
        }

    }

    public int bindLoadingLayout() {
        return 0;
    }

    public void showLoading() {

        if (loadingLayout == null) {
            return;
        }
        ImageView img = loadPage.findViewById(R.id.iv_amin_flowar);
        // 加载动画
        AnimationDrawable mAnimationDrawable = (AnimationDrawable) img.getDrawable();
//            默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        loadingLayout.setStatus(LoadingLayout.Loading);
        /**
         *   ImageView img = (ImageView) this.findViewById(R.id.iv_amin_flowar);

         // 加载动画
         mAnimationDrawable = (AnimationDrawable) img.getDrawable();
         默认进入页面就开启动画
         if (!mAnimationDrawable.isRunning()) {
         mAnimationDrawable.start();
         }
         */
    }

    public void hideLoading(CoreRecyclerView coreRecyclerView) {
        if (loadingLayout == null) {
            return;
        }
        ImageView img = loadPage.findViewById(R.id.iv_amin_flowar);
        // 加载动画
        AnimationDrawable mAnimationDrawable = (AnimationDrawable) img.getDrawable();
//            默认进入页面就开启动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }

        if (coreRecyclerView.getAdapter().getData().size() != 0) {
            loadingLayout.setStatus(LoadingLayout.Success);
        } else {
            loadingLayout.setStatus(LoadingLayout.Empty);
        }
    }

    public void hideLoading(int loadState) {
        if (loadingLayout == null) {
            return;
        }
        ImageView img = loadPage.findViewById(R.id.iv_amin_flowar);
        // 加载动画
        AnimationDrawable mAnimationDrawable = (AnimationDrawable) img.getDrawable();
//            默认进入页面就开启动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        loadingLayout.setStatus(loadState);
    }

    public void hideLoading(int loadState, String str) {
        if (loadingLayout == null) {
            return;
        }
        ImageView img = loadPage.findViewById(R.id.iv_amin_flowar);
        // 加载动画
        AnimationDrawable mAnimationDrawable = (AnimationDrawable) img.getDrawable();
//            默认进入页面就开启动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (!TextUtils.isEmpty(str)) {
            loadingLayout.setErrorText(str);
        }
        loadingLayout.setStatus(loadState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        loadData();
    }

    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected void loadData() {


    }

    protected void onInvisible() {
    }


    protected void setText(TextView tv, String str) {
        tv.setText(FUtil.$(str));
    }


    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        super.onAttach(context);
    }

    protected abstract void initView(View rootView);

    protected abstract int bindLayoutID();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    /**
     * Views indexed with their IDs
     */
    private SparseArray<View> views = new SparseArray<>();

    protected <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = rootView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


}

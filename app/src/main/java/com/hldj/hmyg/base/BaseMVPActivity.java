package com.hldj.hmyg.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.hldj.hmyg.LoginActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.Rx.BaseModel;
import com.hldj.hmyg.base.Rx.BasePresenter;
import com.hldj.hmyg.base.Rx.BaseView;
import com.hldj.hmyg.base.Rx.JumpUtil;
import com.hldj.hmyg.base.Rx.TUtil;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.DialogFragment.CustomDialog;
import com.hldj.hmyg.util.D;
import com.hy.utils.StringFormatUtil;
import com.hy.utils.ToastUtil;
import com.weavey.loading.lib.LoadingLayout;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * create by luocaca base mvp to use mvp partnner
 * at 2017/6/5
 */

public abstract class BaseMVPActivity<T extends BasePresenter, E extends BaseModel> extends NeedSwipeBackActivity implements JumpUtil.JumpInterface, BaseView {

    public T mPresenter;
    public E mModel;
    private ProgressDialog progressDialog;
    private CustomDialog customDialog;

    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSwipeBackEnable(setSwipeBackEnable());

        init();
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        try {
            if (this instanceof BaseView)
                mPresenter.setVM(this, mModel);
        } catch (Exception e) {
            D.e("===mvp 设置失败====e=====" + e.getMessage());
            e.printStackTrace();
        }

        initView();
        initVH();
        initData();
        initListener();


    }

    protected void initListener() {

    }

    public void initData() {

    }

    protected void init() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(bindLayoutID());


        title = (TextView) findViewById(R.id.toolbar_title);

        if (title != null) {
            title.setText(setTitle());
            findViewById(R.id.toolbar_left_icon).setOnClickListener(view -> finish());
        }

    }

    public String setTitle() {
        return "";
    }

    public void setTitle(String tit) {
        if (title != null) {
            title.setText(tit);
        }
    }

    public abstract void initView();


    /**
     * 初始化viewHolder
     */
    public void initVH() {

    }


    /**
     * 是否 侧滑 开启关闭按钮
     *
     * @return
     */
    public abstract boolean setSwipeBackEnable();


    public abstract int bindLayoutID();


    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hindLoading() {
        super.hindLoading();
    }

    @Override
    public void showErrir(String erMst) {
        ToastUtil.showShortToast(erMst);
        hindLoading();
    }


    //成功时进来
    public static void setLoadingState(CoreRecyclerView recyclerView, LoadingLayout layout) {
        if (recyclerView.getAdapter().getData().size() == 0) {
            layout.setStatus(LoadingLayout.Empty);
        } else {
            layout.setStatus(LoadingLayout.Success);
        }
    }

    //失败时进来
    public static void setLoadingState(CoreRecyclerView recyclerView, LoadingLayout layout, String errorMsg, LoadingLayout.OnReloadListener reloadListener) {
        layout.setOnReloadListener(reloadListener);
        layout.setErrorText(errorMsg);
        layout.setStatus(LoadingLayout.Error);
    }

    //失败时进来
    public static void setLoadingState(CoreRecyclerView recyclerView, LoadingLayout layout, String errorMsg) {
        layout.setErrorText(errorMsg);
        layout.setStatus(LoadingLayout.Error);
    }

    //网络请求错误时进来
    public static void setLoadingState(CoreRecyclerView recyclerView, LoadingLayout layout, Throwable t, String errorMsg) {
        layout.setStatus(LoadingLayout.No_Network);
    }

    public SpannableStringBuilder filterColor(String wholeStr, String hightLightStr) {

        return filterColor(wholeStr, hightLightStr, R.color.red);
    }

    public SpannableStringBuilder filterColor(String wholeStr, String hightLightStr, int color) {
        StringFormatUtil formatUtil = new StringFormatUtil(mActivity, wholeStr, hightLightStr, color).fillColor();
        return formatUtil.getResult();
    }


    public int getColorByRes(int resColorId) {
        return ContextCompat.getColor(mActivity, resColorId);
    }


    public boolean isLogin() {
        boolean isLogin = MyApplication.Userinfo.getBoolean("isLogin", false);
        Log.i("LOGIN", "判断是否登录: \n" + isLogin);
        return isLogin;
    }

    /**
     * 判断是否需要登录
     *
     * @return
     */
    public boolean commitLogin() {
        if (isLogin()) {
            return true;
        } else {
            LoginActivity.start2Activity(mActivity);
            ToastUtil.showLongToast("请先登录 ^_^ ");
            return false;
        }
    }
}

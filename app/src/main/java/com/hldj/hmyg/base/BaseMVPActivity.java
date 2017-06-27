package com.hldj.hmyg.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.base.Rx.BaseModel;
import com.hldj.hmyg.base.Rx.BasePresenter;
import com.hldj.hmyg.base.Rx.BaseView;
import com.hldj.hmyg.base.Rx.JumpUtil;
import com.hldj.hmyg.base.Rx.TUtil;
import com.hldj.hmyg.buyer.weidet.DialogFragment.CustomDialog;
import com.hldj.hmyg.util.D;
import com.hy.utils.ToastUtil;

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

    public abstract void initView();


    /**
     * 初始化viewHolder
     */
    public abstract void initVH();


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
    }
}

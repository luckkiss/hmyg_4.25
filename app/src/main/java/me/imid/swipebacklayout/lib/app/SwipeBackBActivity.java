package me.imid.swipebacklayout.lib.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.hldj.hmyg.buyer.weidet.DialogFragment.CustomDialog;
import com.hldj.hmyg.util.StartBarUtils;
import com.hy.utils.Loading;
import com.hy.utils.ToastUtil;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;

public class SwipeBackBActivity extends FragmentActivity implements
        SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    public CustomDialog dialog;

    public Loading loading ;
//        && !StoreSettingActivity.this.isFinishing()) {
//        loading.showToastAlong();
//    } else if (loading == null
//            && !StoreSettingActivity.this.isFinishing()) {
//        loading = new Loading(StoreSettingActivity.this,
//                "店铺资料修改中.....");
//        loading.showToastAlong();


    public void showLoading() {
        getLoad().showToastAlong();
//        dialog.show();
    }

    public void hindLoading() {
        if (getLoad().isRunning()) {
            getLoad().cancel();
        }

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    public Loading getLoad() {
        if (loading == null) {
            loading = new Loading(SwipeBackBActivity.this, "努力加载中.....");
        }
        return loading;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new CustomDialog(this);
//        loading = new Loading(SwipeBackBActivity.this, "努力加载中.....");
        /**
         * 控制状态栏为黑色  miui flyme
         */
        StartBarUtils.FlymeSetStatusBarLightMode(getWindow(), true);
        StartBarUtils.MIUISetStatusBarLightMode(getWindow(), true);

        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }


    public void showToast(String msg) {
        ToastUtil.showShortToast(this, msg);
    }


}

package com.hldj.hmyg.CallBack;

import android.util.Log;

import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.http.AjaxCallBack;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 *
 */

public abstract class HandlerAjaxCallBack extends AjaxCallBack<String> {

    private NeedSwipeBackActivity needSwipeBackActivity;



    public HandlerAjaxCallBack() {

    }

    public HandlerAjaxCallBack(final NeedSwipeBackActivity activity) {
        this.needSwipeBackActivity = activity;
    }

    public void onStart() {
        if (needSwipeBackActivity != null) {
            needSwipeBackActivity.showLoading();
        }
    }

    public void onLoading(long count, long current) {
        super.onLoading(count, current);
    }

    public final void onSuccess(String json) {
        Log.i("HandlerAjaxCallBack", "onSuccess: \n" + GsonUtil.formatJson2String(json));
        try {
            SimpleGsonBean gsonBean = GsonUtil.formateJson2Bean(json, SimpleGsonBean.class);

            if (gsonBean.isSucceed()) {
                //执行成功操作
                onRealSuccess(gsonBean);

            } else {
                //执行失败操作
                ToastUtil.showShortToast(gsonBean.msg);
                onFailure(new Throwable(gsonBean.msg), -1, gsonBean.msg);
            }

        } catch (Exception e) {

            onFailure(e, -1, e.getMessage());
            e.printStackTrace();
        }
        onFinish();
    }

    public abstract void onRealSuccess(SimpleGsonBean gsonBean);

    public void onFailure(Throwable t, int errorNo, String strMsg) {
        ToastUtil.showShortToast(strMsg);
        onFinish();
    }


    public void onFinish() {
        if (needSwipeBackActivity != null && !needSwipeBackActivity.isFinishing()) {
            needSwipeBackActivity.hindLoading();
        }
    }

}

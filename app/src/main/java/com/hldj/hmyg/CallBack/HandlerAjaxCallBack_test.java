package com.hldj.hmyg.CallBack;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.bean.SimpleGsonBean_test;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.http.AjaxCallBack;

import java.lang.reflect.Type;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 *
 */
public abstract class HandlerAjaxCallBack_test<E extends SimpleGsonBean_test> extends AjaxCallBack<String> {

    private NeedSwipeBackActivity needSwipeBackActivity;

    public HandlerAjaxCallBack_test() {
    }


    public HandlerAjaxCallBack_test(NeedSwipeBackActivity activity) {
        this.needSwipeBackActivity = activity;
    }

    public void onStart() {
        if (needSwipeBackActivity != null) {
            needSwipeBackActivity.showLoading();
        }
    }

    public void onLoading(long count, long current) {

    }

    private E e ;
    public final void onSuccess(String json) {
//        e = TUtil.getT(this, 0);
        Log.i("HandlerAjaxCallBack", "onSuccess: \n" + json);
        try {
            Type type = new TypeToken<SimpleGsonBean_test<Moments>>() {
            }.getType();
            SimpleGsonBean_test<Moments> gsonBean_test = GsonUtil.formateJson2Bean(json, type);
            if ((gsonBean_test).isSucceed()) {
                //执行成功操作
                onRealSuccess(gsonBean_test);
            } else {
                //执行失败操作
                ToastUtil.showShortToast((gsonBean_test).msg);
                onFailure(new Throwable((gsonBean_test).msg), -1, (gsonBean_test).msg);
            }
            onFinish();
        } catch (Exception e) {

            onFailure(e, -1, e.getMessage());
            e.printStackTrace();
        }
    }

    public abstract void onRealSuccess(SimpleGsonBean_test<Moments> result);

    public void onFailure(Throwable t, int errorNo, String strMsg) {
        ToastUtil.showShortToast(strMsg);
        onFinish();
    }


    private void onFinish() {
        if (needSwipeBackActivity != null && !needSwipeBackActivity.isFinishing()) {
            needSwipeBackActivity.hindLoading();
        }
    }

}

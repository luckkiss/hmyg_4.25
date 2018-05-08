package com.hldj.hmyg.CallBack;

import android.util.Log;

import com.hldj.hmyg.bean.SimpleGsonBeanData;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.http.AjaxCallBack;

import java.lang.reflect.Type;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 *
 */
public abstract class HandlerAjaxCallBackSimpaleData<E> extends AjaxCallBack<String> {

    private NeedSwipeBackActivity needSwipeBackActivity;

    public HandlerAjaxCallBackSimpaleData() {
    }

    private java.lang.reflect.Type mType;

    public HandlerAjaxCallBackSimpaleData(NeedSwipeBackActivity activity, Type type) {
        this.needSwipeBackActivity = activity;
        this.mType = type;
    }

    public void onStart() {
        if (needSwipeBackActivity != null) {
            needSwipeBackActivity.showLoading();
        }
    }

    public void onLoading(long count, long current) {

    }

    public final void onSuccess(String json) {
//        e = TUtil.getT(this, 0);
        Log.i("HandlerAjaxCallBack", "onSuccess: \n" + json);
        try {
//            Type type = new TypeToken<SimpleGsonBeanData<E>>() { }.getType();

//            Type type = new TypeToken<testgson<E>>() { }.getType();  // 此写法 会闪退
//            testgson<E> gsonBean_test1 = GsonUtil.formateJson2Bean(json,type);

            SimpleGsonBeanData<E> gsonBean_test = GsonUtil.formateJson2Bean(json, mType);

//            testgson<E> gsonBean_test = GsonUtil.formateJson2Bean(json, testgson.class);

//            testgson<UserPurchaseGsonBean> gsonBean_test1 = GsonUtil.formateJson2Bean(json, testgson.class);

//            SimpleGsonBean simpleGsonBean = GsonUtil.formateJson2Bean(s, SimpleGsonBean.class);
            if ((gsonBean_test).isSucceed()) {
                //执行成功操作
                onRealSuccess((E) gsonBean_test.data);
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

    public abstract void onRealSuccess(E result);

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

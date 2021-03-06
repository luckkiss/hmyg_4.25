package com.hldj.hmyg.CallBack;

import android.util.Log;

import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.http.AjaxCallBack;

import java.lang.reflect.Type;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * https://www.itstrike.cn/Question/014cf28f-d1b3-487b-84d9-dfbdfbb2cecd.html
 * type  泛型 擦除问题   at com.google.gson.internal.$Gson$Types$ParameterizedTypeImpl.hashCode($Gson$Types.java:479)
 * <p>
 * <p>
 * 解决方案  --- >  将 泛型 type 和 class 传入
 * <p>
 * // 由于泛型  中   带  泛型  导致  编译器  在运行 期间 第一次泛型 神效  第二次 就失效了 （猜测）
 */
public abstract class HandlerAjaxCallBackPage<E> extends AjaxCallBack<String> {

    CoreRecyclerView mCoreRecyclerView = null;
    Type type;
    Class classTypeResponse; // 响应的 respong class

    OnTotalCallBack totalCallBack;

    private NeedSwipeBackActivity needSwipeBackActivity;
//    private Class<T> mClass;

    public HandlerAjaxCallBackPage(Type type, Class classTypeResponse) {
//        this.mClass = mClass;
        this.type = type;
        this.classTypeResponse = classTypeResponse;
    }


    public HandlerAjaxCallBackPage(NeedSwipeBackActivity activity, Type type, Class classTypeResponse) {
        this.type = type;
        this.classTypeResponse = classTypeResponse;
        this.needSwipeBackActivity = activity;
    }

    public HandlerAjaxCallBackPage(NeedSwipeBackActivity activity, Type type) {
        this.type = type;
        this.needSwipeBackActivity = activity;
    }

    public HandlerAjaxCallBackPage(NeedSwipeBackActivity activity, Type type, CoreRecyclerView recyclerView) {
        this.type = type;
        this.needSwipeBackActivity = activity;
        this.mCoreRecyclerView = recyclerView;
    }


    public HandlerAjaxCallBackPage(NeedSwipeBackActivity activity, Type type, OnTotalCallBack totalCallBack) {
        this.type = type;
        this.needSwipeBackActivity = activity;
        this.totalCallBack = totalCallBack;
    }


    public void onStart() {
        if (needSwipeBackActivity != null) {
            needSwipeBackActivity.showLoading();
        }
    }

    public void onLoading(long count, long current) {

    }

    private E e;

    public final void onSuccess(String json) {
//      e = TUtil.getT(this, 0);
//        Log.i("HandlerAjaxCallBack", "onSuccess: \n" + json);
        i("HandlerAjaxCallBack", GsonUtil.formatJson2String(json));
        try {
            /**
             *  /**
             *   Type beanType = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<SaveSeedingGsonBean.DataBean.SeedlingBean>>>>() {
             //                        }.getType();
             */


//            Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<E>>>>() {
//            }.getType();

            SimpleGsonBean_new<SimplePageBean<List<E>>> gsonBean_test = GsonUtil.formateJson2Bean(json, type);

//            Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<E>>>() {
//            }.getType();
//
//            SimpleGsonBean_new<SimplePageBean<E>> gsonBean_test = GsonUtil.formateJson2Bean(json, type);


            if ((gsonBean_test).isSucceed()) {
                //执行成功操作
                //E = List<SaveSeedingGsonBean.DataBean.SeedlingBean>
                onRealSuccess((List<E>) gsonBean_test.data.page.data);

                if (totalCallBack != null) {
                    try {
                        totalCallBack.onToatl(gsonBean_test.data.page.total);
                    } catch (Exception e) {
                        totalCallBack.onToatl(0);
                    }
                }

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

    public abstract void onRealSuccess(List<E> e);
//    public abstract <E> void onRealSuccess(Class<E> result);

    public void onFailure(Throwable t, int errorNo, String strMsg) {
        ToastUtil.showShortToast(strMsg);

        if (mCoreRecyclerView != null) {
            mCoreRecyclerView.showLoadMoreFailedView();
        }
        onFinish();
    }


    public void onFinish() {
        if (needSwipeBackActivity != null && !needSwipeBackActivity.isFinishing()) {
            needSwipeBackActivity.hindLoading();
        }
        if (mCoreRecyclerView != null) {
            mCoreRecyclerView.selfRefresh(false);
        }

    }


    /**
     * 截断输出日志
     *
     * @param msg
     */
    private static void i(String tag, String msg) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0)
            return;

        int segmentSize = 3 * 1024;
        long length = msg.length();
        if (length <= segmentSize) {// 长度小于等于限制直接打印
            Log.i(tag, msg);
        } else {
            while (msg.length() > segmentSize) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                Log.i(tag, logContent);
            }
            Log.i(tag, msg);// 打印剩余日志
        }
    }


    public static interface OnTotalCallBack {
        void onToatl(int total);
    }

}

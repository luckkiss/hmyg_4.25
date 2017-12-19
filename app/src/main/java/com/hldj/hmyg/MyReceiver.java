package com.hldj.hmyg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack_test;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.Ui.friend.child.DetailActivity;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.bean.SimpleGsonBean_test;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private String url = "";
    private String title = "详情";

    @SuppressWarnings("unused")
    @Override
    public void onReceive(final Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        D.w("---------------后台传过来的 对象------------------");
        D.d("\n[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        D.w("---------------------后台传过来的 对象----------------------");
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            D.d("[MyReceiver] 接收Registration Id : " + regId);
            // send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            D.w("[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

            String momentId = bundle.getString("cn.jpush.android.MESSAGE");
//            ToastUtil.showLongToast("momentid=\n" + momentId);
            refreshMomentItem(momentId);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            D.w("[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);

//            ToastUtil.showLongToast("通知=\n" + bundle.getString("cn.jpush.android.EXTRA"));

            D.d("[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            D.w("[MyReceiver] 用户点击打开了通知");
            JPushInterface.reportNotificationOpened(context, bundle.getString(JPushInterface.EXTRA_MSG_ID));
            // 打开自定义的Activity
            // Intent ii = new Intent(context, MainActivity.class);
            D.e("推送过来的杰森" + bundle.getString(JPushInterface.EXTRA_EXTRA));
            String momentId = bundle.getString("cn.jpush.android.MESSAGE");
//            String extra = bundle.getString("cn.jpush.android.EXTRA");
            String extra = bundle.getString("cn.jpush.android.EXTRA");
            try {
                JSONObject jsonObject = new JSONObject(extra);
                momentId = jsonObject.getString("momentId");
            } catch (JSONException e) {
                momentId = "";
                e.printStackTrace();
            }
            if (MyApplication.Userinfo.getBoolean("isDin", false)) {
                Intent ii = new Intent();
                ii.setAction("com.jpush.hmeg.message");
                ii.putExtras(bundle);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ii);
            } else if (!TextUtils.isEmpty(momentId)) {
                DetailActivity.start(context, momentId);
            } else {
                Intent ii = new Intent();
                ii.setAction("com.jpush.hmeg.main");
                ii.putExtras(bundle);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ii);
                try {
                    JSONObject jo = new JSONObject(
                            bundle.getString(JPushInterface.EXTRA_EXTRA));
                    D.e("推送过来的杰森" +
                            bundle.getString(JPushInterface.EXTRA_EXTRA));
                    url = new JSONObject(
                            bundle.getString(JPushInterface.EXTRA_EXTRA))
                            .getString("url");
                    // title = new JSONOb..);
                    Intent intent1 = new Intent(context, MainActivity.class);
                    intent1.putExtra("url", url);
                    intent1.putExtra("title", title);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
                .getAction())) {
            D.w(
                    "[MyReceiver] 用户收到到RICH PUSH CALLBACK: "
                            + bundle.getString(JPushInterface.EXTRA_EXTRA));
            // 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
            // 打开一个网页等..

        } else {
            D.w("[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    private void refreshMomentItem(String momentId) {
        Log.i(TAG, "开始请求");
        new BasePresenter()
                .putParams("id", momentId)
                .doRequest("moments/detail", true, new HandlerAjaxCallBack_test<SimpleGsonBean_test<Moments>>() {
                    @Override
                    public void onRealSuccess(SimpleGsonBean_test<Moments> result) {
                        Log.i(TAG, "onRealSuccess: " + result);
                        RxBus.getInstance().post(RxBus.TAG_UPDATE, result.data.moments);
                    }
                });
        Log.i(TAG, "结束请求");
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    // send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        // if (MainActivity.isForeground) {
        // String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        // String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        // Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
        // msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
        // if (!ExampleUtil.isEmpty(extras)) {
        // try {
        // JSONObject extraJson = new JSONObject(extras);
        // if (null != extraJson && extraJson.length() > 0) {
        // msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
        // }
        // } catch (JSONException e) {
        //
        // }
        // }
        // context.sendBroadcast(msgIntent);
        // }
    }
}

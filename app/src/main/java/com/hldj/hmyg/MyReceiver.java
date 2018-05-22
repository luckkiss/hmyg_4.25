package com.hldj.hmyg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack_test;
import com.hldj.hmyg.Ui.Eactivity3_0;
import com.hldj.hmyg.Ui.friend.bean.Message;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.Ui.friend.child.DetailActivity;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.bean.SimpleGsonBean_test;
import com.hldj.hmyg.buyer.Ui.LoginOutDialogActivity;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;
import com.hy.utils.GetServerUrl;

import net.tsz.afinal.FinalDb;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

import static com.hldj.hmyg.DActivity_new_mp.refresh;
import static com.hldj.hmyg.Ui.Eactivity3_0.refresh_tip;

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

            /*接收到推送下来的自定义消息*/
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            D.w("[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

            String contetn = bundle.getString("cn.jpush.android.MESSAGE");
            Log.i(TAG, "onReceive: contetn \n" + contetn);
            String extras = bundle.getString("cn.jpush.android.EXTRA");
            String momentId = "";
            try {
                JSONObject jsonObject = new JSONObject(extras);

                String messageType = jsonObject.getString("messageType");

                if (messageType.equals("notice")) {
                    Log.w(TAG, "notice: ");
//                    ToastUtil.showLongToast(messageType);
                    return;
                }
                if (messageType.equals("userGetOut")) {
                    processLoginOut(extras, context);
//                    ToastUtil.showLongToast(messageType);


                    return;
                }

                if (messageType.equals("userPoint")) {
                    processUserPoint(extras);

                    return;
                }

                if (messageType.equals("menuTip")) {
                    processTip(extras);
                    return;
                }


                momentId = jsonObject.getString("momentsId");
            } catch (JSONException e) {
                momentId = "";
                e.printStackTrace();
            }


            //  key:cn.jpush.android.EXTRA,
            //{"sourceId":"4966be553cf84d92856254b08a3adb34","momentsId":"f0e83bf9c5564eeeaa3510e8ad810051","type":"thumbUp","option":"add","messageType":"moments"}

//            ToastUtil.showLongToast("momentid=\n" + momentId);
            refreshMomentItem(momentId);




        /*接收到推送下来的通知*/
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            D.w("[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);

//          ToastUtil.showLongToast("通知=\n" + bundle.getString("cn.jpush.android.EXTRA"));
            String extra = bundle.getString("cn.jpush.android.EXTRA");
            String momentId = bundle.getString("cn.jpush.android.MESSAGE");

            try {
                JSONObject jsonObject = new JSONObject(extra);
                String messageType = jsonObject.getString("messageType");

                if (messageType.equals("notice")) {
                    Log.w(TAG, "notice: ");
                    return;
                }
                if (messageType.equals("userGetOut")) {
                    Log.w(TAG, "userGetOut: ");
                    return;
                }


//                if (messageType.equals("notice")) {
//                    processToMessagerActivity(context);
//                    return;
//                }


//                ToastUtil.showLongToast(messageType);

                /**
                 * // db.delete(user); //根据对象主键进行删除
                 // db.deleteById(user, 1); //根据主键删除数据
                 // db.deleteByWhere(User.class, "name=AoTuMan"); //自定义where条件删除
                 db.deleteAll(User.class); //删除Bean对应的数据表中的所有数据
                 */


                /**
                 * Bundle[{cn.jpush.android.ALERT=您有新的评论,
                 * cn.jpush.android.EXTRA=
                 * {"sourceId":"0e6678eb19a542b997c9f911278d5c8e",
                 * "momentsId":"955a53481d514a3eb424c7e74463f03a","type":"reply","option":"add","messageType":"moments"},
                 * cn.jpush.android.NOTIFICATION_ID=521470835, cn.jpush.android.ALERT_TYPE=-1, cn.jpush.android.NOTIFICATION_CONTENT_TITLE=花木易购, cn.jpush.android.MSG_ID=65302197350662344}]
                 */

                /**
                 * jsonObject = {JSONObject@6716} "{"sourceId":"459c3b5a1c4b4ebfba88fe9272c73243","notyfyType":"collect","momentId":"83e5cb7bdda14d49b5ca16b1197c8134","type":"reply","option":"add"}"
                 nameValuePairs = {LinkedHashMap@6722}  size = 5

                 "sourceId" -> "459c3b5a1c4b4ebfba88fe9272c73243"
                 messageType = moments;
                 "momentId" -> "83e5cb7bdda14d49b5ca16b1197c8134"
                 "type" -> "reply"
                 "option" -> "add"
                 */
//                String messageType = jsonObject.getString("messageType");
                String sourceId = jsonObject.getString("sourceId");
                String type = jsonObject.getString("type");
                String option = jsonObject.getString("option");
                momentId = jsonObject.getString("momentsId");


                Message message = new Message();
                message.setMessageType(messageType);
                message.setType(type);
                message.setSourceId(sourceId);
                message.setOption(option);
                message.setMomentsId(momentId);
                FinalDb db = FinalDb.create(context);
//                db.deleteByWhere(Message.class, "id=1");
                // DELETE FROM com_hldj_hmyg_Ui_friend_bean_Message WHERE id=1

                try {
                    /*此条删除语句可用*/
//                    db.deleteByWhere(Message.class, "momentsId=\"" + momentId + "\"");
//                  db.deleteByWhere(Message.class, "momentsId=\"" + momentId + "\"");
//                  db.deleteByWhere(Message.class, "momentsId=" + momentId);
                } catch (Exception e) {
                    Log.i(TAG, "onReceive: 失败5");
                    e.printStackTrace();
                }

//                try {
//                    db.deleteByWhere(Message.class, "momentsId=" + momentId);
//                } catch (Exception e) {
//                    Log.i(TAG, "onReceive: 失败一");
//                    e.printStackTrace();
//                }


                //DELETE FROM com_hldj_hmyg_Ui_friend_bean_Message WHERE momentsId=955a53481d514a3eb424c7e74463f03a
                db.save(message);
                // INSERT INTO com_hldj_hmyg_Ui_friend_bean_Message (time,other,option,momentsId) VALUES ( ?,?,?,?)
                Log.w(TAG, " 保存消息 Message: " + message.toString());

//                ToastUtil.showLongToast("option\n" + message.toString());

            } catch (JSONException e) {
                momentId = "";
                e.printStackTrace();
            }
            refreshMomentItem(momentId);
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

                if (jsonObject.getString("messageType").equals("notice")) {
//                    ToastUtil.showLongToast(jsonObject.getString("messageType"));
                    Intent toMessageListActivity = new Intent(context, MessageListActivity.class);
                    context.startActivity(toMessageListActivity);
                    return;
                }

                if (jsonObject.getString("messageType").equals("userGetOut")) {
//                    ToastUtil.showLongToast(jsonObject.getString("messageType"));
//                    Intent toMessageListActivity = new Intent(context, MessageListActivity.class);
//                    context.startActivity(toMessageListActivity);
                    return;
                }


                momentId = jsonObject.getString("momentId");

                String option = jsonObject.getString("option");
//                ToastUtil.showLongToast("option\n" + option);

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
                try {
                    FinalDb db = FinalDb.create(context);
                    /*此条删除语句可用*/
                    db.deleteByWhere(Message.class, "momentsId=\"" + momentId + "\"");
                    Log.i(TAG, "删除单条 message: 成功");
                } catch (Exception e) {
                    Log.i(TAG, "删除单条 message: 失败5");
                    e.printStackTrace();
                }
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


    private void processTip(String extras) {

        try {
            JSONObject jsonObject = new JSONObject(extras);

            String targetMenu = jsonObject.getString("targetMenu");

            if (targetMenu.equals("store")) {
//                ToastUtil.showShortToast("store  刷新");
                Log.i(TAG, "store: 刷新");
                Log.i(TAG, "store: 刷新");
                Log.i(TAG, "store: 刷新");
                Log.i(TAG, "store: 刷新");
                Log.i(TAG, "store: 刷新");


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (MainActivity.radio_d != null) {
                            MainActivity.radio_d.setShowSmallDot(true);
                        }
                    }
                }, 500);


                RxBus.getInstance().post(refresh, new Eactivity3_0.OnlineEvent(true));

            } else if (targetMenu.equals("personal")) {
//                ToastUtil.showShortToast("personal  刷新");
                Log.i(TAG, "personal:刷新 ");
                Log.i(TAG, "personal:刷新 ");
                Log.i(TAG, "personal:刷新 ");
                Log.i(TAG, "personal:刷新 ");
                Log.i(TAG, "personal:刷新 ");
                Log.i(TAG, "personal:刷新 ");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (MainActivity.radio_e != null) {
                            MainActivity.radio_e.setShowSmallDot(true);
                        }
                    }
                }, 500);
                RxBus.getInstance().post(refresh_tip, new Eactivity3_0.OnlineEvent(true));


            }


        } catch (JSONException e) {
            Log.w(TAG, "processUserPoint: " + e.getMessage());
            e.printStackTrace();
        }


    }

    /**
     * 处理积分消息 通知
     *
     * @param extras
     */
    private void processUserPoint(String extras) {

        try {
            JSONObject jsonObject = new JSONObject(extras);

            String title = jsonObject.getString("title");
            String message = jsonObject.getString("message");
//            ToastUtil.showPointAdd(title, message);


        } catch (JSONException e) {
            Log.w(TAG, "processUserPoint: " + e.getMessage());
            e.printStackTrace();
        }


    }

    /**
     * 执行退出登录操作
     */
    private void processLoginOut(String extras, Context mContent) {
        String loginOut = "";
        try {
            JSONObject jsonObject = new JSONObject(extras);
//          loginOut = jsonObject.getString("loginOut");


            loginOut = jsonObject.getString("deviceId");

            if (loginOut.equals(GetServerUrl.deviceId)) {
//            ToastUtil.showLongToast("是自己,不需要退出登录");
            } else {
//            ToastUtil.showLongToast("执行退出登录" + loginOut);
//              Call_Phone(context);

                SettingActivity.clearCache(MyApplication.Userinfo.edit());
                Intent intent1 = new Intent(mContent, LoginOutDialogActivity.class);
                mContent.startActivity(intent1);
            }
        } catch (JSONException e) {
            loginOut = "";
            e.printStackTrace();
        }


    }

//    private void processToMessagerActivity(Context mcontext) {
//
//        Intent toMessageListActivity = new Intent(mcontext.this, MessageListActivity.class);
//        mcontext.startActivity(toMessageListActivity);
//
//
//    }

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

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        onFinish();
                        Log.w(TAG, "onFailure: 请求帖子失败---次请求无效---");
                    }
                });
        Log.i(TAG, "结束请求");
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        if (bundle == null || bundle.keySet() == null) {
            Log.w(TAG, "printBundle: is null");
            return "";
        }

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

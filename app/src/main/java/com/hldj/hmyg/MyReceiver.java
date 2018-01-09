package com.hldj.hmyg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack_test;
import com.hldj.hmyg.Ui.friend.bean.Message;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.Ui.friend.child.DetailActivity;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.bean.SimpleGsonBean_test;
import com.hldj.hmyg.buyer.Ui.LoginOutDialogActivity;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalDb;

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


    public static void Call_Phone() {


//        MyApplication.showDialog();


//        if (dialog!=null)
//        {
//            dialog.show();
//        }
//        ToastUtil.showLongToast("hello world");
//        Log.w(TAG, "Call_Phone: " + "hello world" + context);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.Theme_AppCompat);
//        builder.setMessage("Are you sure you want to exit?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//
//        AlertDialog alert = builder.create();
//        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        alert.show();


//        new AlertDialog(context).builder()
//                .setTitle("确定退出登录?")
//                .setPositiveButton("退出登录", v1 -> {
//                    SettingActivity.exit2Home
//                            ((Activity) context, MyApplication.Userinfo.edit(), false);
//                }).setNegativeButton("取消", v2 -> {
//        }).show();

//
//            dialog = new com.flyco.dialog.widget.MaterialDialog(ac);
//            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//            dialog.title("客服热线4006-579-888").content("客服热线 周一至周日9:00-18:00")//
//                    .btnText("确认拨号", "取消")//
//                    .show();
//            dialog.setOnBtnClickL(() -> {
//                dialog.dismiss();
//            }, () -> dialog.dismiss());
//


    }

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
                momentId = jsonObject.getString("momentsId");
            } catch (JSONException e) {
                momentId = "";
                e.printStackTrace();
            }

            String loginOut = "";
            try {
                JSONObject jsonObject = new JSONObject(extras);
                loginOut = jsonObject.getString("loginOut");
            } catch (JSONException e) {
                loginOut = "";
                e.printStackTrace();
            }

            if (TextUtils.isEmpty(loginOut)) {
                ToastUtil.showLongToast("不需要退出登录");
            } else {
                ToastUtil.showLongToast("执行退出登录" + loginOut);
//              Call_Phone(context);
                Call_Phone();
                SettingActivity.clearCache(MyApplication.Userinfo.edit());
                Intent intent1 = new Intent(context, LoginOutDialogActivity.class);
                context.startActivity(intent1);
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
                momentId = jsonObject.getString("momentsId");
                String messageType = jsonObject.getString("messageType");
                String sourceId = jsonObject.getString("sourceId");
                String type = jsonObject.getString("type");
                String option = jsonObject.getString("option");

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
                momentId = jsonObject.getString("momentId");

                String option = jsonObject.getString("option");
                ToastUtil.showLongToast("option\n" + option);

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

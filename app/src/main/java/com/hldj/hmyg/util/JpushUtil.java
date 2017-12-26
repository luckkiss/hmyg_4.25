package com.hldj.hmyg.util;

import android.content.Context;

import com.hldj.hmyg.application.MyApplication;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.jiguang.commom.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.android.data.JPushLocalNotification;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

/**
 * Created by Administrator on 2017/4/12.
 */

public class JpushUtil {

    //    public static String APP_KEY = "27b97eb500b6ac59f8de2a97";  // luocaca
    public static String APP_KEY = "5ea65acf72f09d1fa4664a00";   // hmeg
    //    public static String MASTER_SECRET = "e26a442d2e0925a95345d9d9";  // luocaca
    public static String MASTER_SECRET = "cb2fe7bb39be10f2be500ab1";
    public static String ALERT = "花木易购客户端";
    public static String TITLE = "花木易购 ";

    /**
     * 手动推送极光消息到服务器
     */

    public static void sendCustommPush(String momentId, String alies) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());

                // For push, all you need do is to build PushPayload object.
                PushPayload payload = buildPushObject_with_extra(momentId, alies);
//                PushPayload payload = buildPushObject_all_all_alert();


                try {
                    PushResult result = jpushClient.sendPush(payload);
                    System.out.println("Got result - " + result);

                } catch (APIConnectionException e) {
                    // Connection error, should retry later
                    System.out.println("Connection error, should retry later");

                } catch (APIRequestException e) {
                    // Should review the error, and fix the request
                    System.out.println("Should review the error, and fix the request");
                    System.out.println("HTTP Status: " + e.getStatus());
                    System.out.println("Error Code: " + e.getErrorCode());
                    System.out.println("Error Message: " + e.getErrorMessage());
                }


            }
        }).start();
    }


    //快捷地构建推送对象：所有平台，所有设备，内容为 ALERT 的通知。
    public static PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll(ALERT);
    }


    public static PushPayload buildPushObject_with_extra(String momentId, String alies) {
        //id
        PushPayload payload = PushPayload
                .newBuilder()
                .setPlatform(Platform.android_ios())
//              .setAudience(Audience.all())
                .setAudience(Audience.alias(alies))
                .setMessage(Message.newBuilder().setMsgContent(momentId).addExtra("momentId", momentId).build())
//              .setNotification(
//                        Notification
//                                .newBuilder()
//                                .setAlert("您有新的动态，点击查看哦^_^")
//                                .addPlatformNotification(
//                                        AndroidNotification.newBuilder().setTitle("花木易购")
//                                                .addExtra("booleanExtra", false)
//                                                .addExtra("numberExtra", 1)
//                                                .addExtra("momentId", momentId)
//                                                .build())
//                                .addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtra("extra_key", "extra_value").build()).build())
                .build();
        System.out.println(payload.toJSON());
        return payload;
    }

//    public static PushPayload buildPushObject_all_alias_alert() {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.all())
//                .setAudience(Audience.alias("alias1"))
//                .setNotification(Notification.alert(ALERT))
//                .build();
//    }


    /**
     * 为极光推送设置别名
     *
     * @param id
     */
    public static void setAlias(String id) {
        JPushInterface.setAlias(MyApplication.getInstance(), id, new TagAliasCallback() {
            @Override
            public void gotResult(int code, String arg1, Set<String> arg2) {

                D.e("极光推送返回结果 code=" + code + " arg1" + arg1 + " arg2" + arg2);
            }
        });
    }


    /**
     * 本地广播  通知
     *
     * @param context
     */
    public static void sendLocalNotices(Context context) {
        JPushLocalNotification ln = new JPushLocalNotification();
        ln.setBuilderId(0);
        ln.setContent("hhh");
        ln.setTitle("ln");
        ln.setNotificationId(111111125);
        ln.setBroadcastTime(System.currentTimeMillis() + 1000);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "jpush");
        map.put("test", "111");
        JSONObject json = new JSONObject(map);
        ln.setExtras(json.toString());
        JPushInterface.addLocalNotification(context, ln);
    }


}

package com.hldj.hmyg.Ui.friend.bean.tipNum;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 菜单通知工具类
 *
 * @author linzj
 * @version 2018-5-7
 */
public class TipNumUtils {


    /**
     * 有来电记录时
     *
     * @param
     */
    public static void tipPusher(String userId, TipNumType type) {
        if (TextUtils.isEmpty(userId)) {
            Map<String, String> alertMap = new HashMap<String, String>();
            alertMap.put("message", "");
//            alertMap.put("messageType", MessageType.menuTip.getEnumValue());
            alertMap.put("targetMenu", type.value);
            alertMap.put("userId", userId);
            // 发送APP通知
//            JPushUtils.jpushMessage(alertMap);
            // 发送Wap版通知 Wap版暂时不做强时效性，暂不通知
            // msgTemplate.convertAndSendToUser(user.getId(), "/tipmenu", alertMap);
        }
    }

}

package com.hldj.hmyg.util;

import com.hldj.hmyg.application.MyApplication;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2017/4/12.
 */

public class JpushUtil {


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


}

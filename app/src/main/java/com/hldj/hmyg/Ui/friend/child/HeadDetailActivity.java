package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;

/**
 * Created by luocaca on 2017/11/27 0027.
 * <p>
 * <p>
 * 头像点击  进入 详细资料界面
 */

public class HeadDetailActivity extends BaseMVPActivity {

    private static final String TAG = "HeadDetailActivity";


    public int bindLayoutID() {
        return R.layout.activity_friend_head_detail;
    }


    @Override
    public void initView() {

        /* 他的店铺  点击事件   有 ** 条动态 >  */
        getView(R.id.textView54).setOnClickListener(v -> {
            CenterActivity.start(mActivity, getUserId());
//            CenterActivity.start(mActivity, item.ownerId);
        });
        requestData();


    }

    public String getUserId() {
        Log.i(TAG, " ownerId 个人id: \n" + getIntent().getExtras().getString(TAG));
        return getIntent().getExtras().getString(TAG);
    }

    /**
     * 网络请求，本界面数据
     */
    private void requestData() {

        Log.i(TAG, "结束请求");
    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity activity, String id) {
        Intent intent = new Intent(activity, HeadDetailActivity.class);
        intent.putExtra(TAG, id);
        activity.startActivityForResult(intent, 110);
    }


    @Override
    public String setTitle() {
        return "详细资料";
    }


}

package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hy.utils.ToastUtil;

/**
 * Created by luocaca on 2017/11/27 0027.
 * <p>
 * <p>
 * 举报界面
 */

public class ReportActivity extends BaseMVPActivity {

    private static final String TAG = "ReportActivity";


    public int bindLayoutID() {
        return R.layout.activity_friend_report;
    }


    CheckBox[] checkBoxs;


    @Override
    public void initView() {

        checkBoxs = new CheckBox[]{getView(R.id.cb_type_01), getView(R.id.cb_type_02), getView(R.id.cb_type_03), getView(R.id.cb_type_04), getView(R.id.cb_type_05),};

        /* 他的店铺  点击事件   有 ** 条动态 >  */
//        getView(R.id.textView54).setOnClickListener(v -> {
//            CenterActivity.start(mActivity, getUserId());
////            CenterActivity.start(mActivity, item.ownerId);
//        });
//        requestData();


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
        Intent intent = new Intent(activity, ReportActivity.class);
        intent.putExtra(TAG, id);
        activity.startActivityForResult(intent, 110);
    }


    @Override
    public String setTitle() {
        return "举报";
    }


    /*提交点击事件 */
    public void submit(View view) {


        ToastUtil.showLongToast("提交" + getSubmitString());


        Log.i(TAG, "submit: ");

    }


    public String getSubmitString() {
        StringBuffer stringBuffer = new StringBuffer();

        for (CheckBox checkBox : checkBoxs) {
            if (checkBox.isChecked())
            stringBuffer.append("  " + checkBox.getText());
        }

        return stringBuffer.toString();

    }

}

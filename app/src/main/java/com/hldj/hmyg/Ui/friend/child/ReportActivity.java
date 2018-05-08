package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.bean.Tipoff;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hy.utils.ToastUtil;

/**
 * Created by luocaca on 2017/11/27 0027.
 * <p>
 * <p>
 * 举报界面
 */

public class ReportActivity extends BaseMVPActivity {

    private static final String TAG = "ReportActivity";
    private String remark;
    private String sourceId;


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


        for (CheckBox checkBox : checkBoxs) {
            checkBox.setOnClickListener(v -> {
                for (int i = 0; i < checkBoxs.length; i++) {

                    checkBoxs[i].setChecked(false);

                }
                ((CheckBox) v).setChecked(true);
            });
        }


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


    public static void start(Activity activity, String id, String type) {
        Intent intent = new Intent(activity, ReportActivity.class);
        intent.putExtra(TAG, id);
        intent.putExtra("sourceType", type);
        activity.startActivityForResult(intent, 110);
    }


    @Override
    public String setTitle() {
        return "举报";
    }


    /*提交点击事件 */
    public void submit(View view) {


//        ToastUtil.showLongToast("提交" + getSubmitString());

        Tipoff tipoff = new Tipoff();
        tipoff.acceptRemarks = getRemark();
        tipoff.sourceId = getSourceId();
        tipoff.tipoffUserId = MyApplication.getUserBean().id;
        tipoff.sourceType = getSourceType();
        tipoff.title = getSubmitString();


        new BasePresenter()
                .putParams(tipoff)
                .doRequest("admin/tipoff/save", new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        ToastUtil.showLongToast(gsonBean.msg);
//                        finish();
                    }
                });


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

    public String getRemark() {
        return getText(getView(R.id.remark));
    }

    public String getSourceId() {
        return getIntent().getStringExtra(TAG);
    }

    public String getSourceType() {
        return getIntent().getStringExtra("sourceType");
    }
}

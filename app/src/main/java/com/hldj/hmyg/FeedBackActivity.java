package com.hldj.hmyg;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.buyer.weidet.DialogFragment.CommonDialogFragment;
import com.hy.utils.GetServerUrl;
import com.hy.utils.JsonGetInfo;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 用户反馈
 */
public class FeedBackActivity extends NeedSwipeBackActivity {


    private static final String TAG = "FeedBackActivity";
    /**
     */
    private ImageView btn_back;
    private TextView feedback;
    private com.hy.utils.ContainsEmojiEditText et_feedback;
    private com.hy.utils.ContainsEmojiEditText et_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);


        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        btn_back = (ImageView) findViewById(R.id.toolbar_left_icon);

        ((TextView) findViewById(R.id.toolbar_title)).setText("用户反馈");


        feedback = (TextView) findViewById(R.id.feedback);
        et_feedback = (com.hy.utils.ContainsEmojiEditText) findViewById(R.id.et_feedback);
        et_title = (com.hy.utils.ContainsEmojiEditText) findViewById(R.id.et_title);
        et_feedback.addTextChangedListener(watcher_num);
        MultipleClickProcess multipleClickProcess = new MultipleClickProcess();
        btn_back.setOnClickListener(multipleClickProcess);
        feedback.setOnClickListener(multipleClickProcess);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    TextWatcher watcher_num = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
            if (s.length() > 0) {
                if (et_feedback.getText().toString().length() > 0) {
                    feedback.setTextColor(getResources().getColor(R.color.white));
                }
            } else {

                feedback.setTextColor(getResources().getColor(R.color.white));

            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }
    };


    public class MultipleClickProcess implements OnClickListener {
        private boolean flag = true;

        private synchronized void setFlag() {
            flag = false;
        }

        public void onClick(View view) {
            if (flag) {
                switch (view.getId()) {
                    case R.id.toolbar_left_icon:

                        onBackPressed();

                        break;
                    case R.id.feedback:
                        // 判断是否为空
                        if (TextUtils.isEmpty(et_feedback.getText())) {
//                            Toast.makeText(FeedBackActivity.this, "请填写反馈内容☺", Toast.LENGTH_SHORT).show();
                            ToastUtil.showShortToast("请填写反馈内容☺");
                            return;
                        }

                        showLoading();

                        FinalHttp finalHttp = new FinalHttp();
                        GetServerUrl.addHeaders(finalHttp, true);
                        AjaxParams params = new AjaxParams();
                        params.put("title", et_title.getText().toString());
                        params.put("content", et_feedback.getText().toString());
                        params.put("source", "android");
                        finalHttp.post(GetServerUrl.getUrl()
                                        + "admin/feedback/save", params,
                                new AjaxCallBack<Object>() {

                                    @Override
                                    public void onSuccess(Object t) {
                                        // TODO Auto-generated method stub
                                        hindLoading();
                                        try {
                                            JSONObject jsonObject = new JSONObject(t.toString());
                                            String code = JsonGetInfo.getJsonString(jsonObject, "code");
                                            String msg = JsonGetInfo.getJsonString(jsonObject, "msg");

                                            if ("1".equals(code)) {
                                                CommonDialogFragment.newInstance(context -> {
                                                    Dialog dialog1 = new Dialog(context, R.style.DialogTheme);
                                                    dialog1.setContentView(R.layout.feed_back_succeed);
                                                    dialog1.findViewById(R.id.ll_feed_content).setBackgroundColor(Color.WHITE);
                                                    dialog1.findViewById(R.id.tv_feed_ok).setOnClickListener(view1 -> onBackPressed());
                                                    return dialog1;
                                                }, true, () -> {
                                                    onBackPressed();
                                                }).show(getSupportFragmentManager(), TAG);

                                            } else {

                                                ToastUtil.showShortToast(msg);
                                            }

                                        } catch (JSONException e) {
                                            ToastUtil.showShortToast("提交失败");
                                            e.printStackTrace();
                                        }
                                        super.onSuccess(t);
                                    }

                                    @Override
                                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                                        hindLoading();
                                        // TODO Auto-generated method stub
                                        Toast.makeText(FeedBackActivity.this, R.string.error_net, Toast.LENGTH_SHORT).show();
                                        super.onFailure(t, errorNo, strMsg);
                                    }

                                });

                        break;

                    default:
                        break;
                }
                setFlag();
                // do some things
                new TimeThread().start();
            }
        }

        /**
         * 计时线程（防止在一定时间段内重复点击按钮）
         */
        private class TimeThread extends Thread {
            public void run() {
                try {
                    Thread.sleep(Data.loading_time);
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void start2Activity(Context mActivity) {
        mActivity.startActivity(new Intent(mActivity, FeedBackActivity.class));
    }
}

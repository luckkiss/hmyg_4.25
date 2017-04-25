package com.hldj.hmyg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.LoginGsonBean;
import com.hldj.hmyg.bean.UserInfoGsonBean;
import com.hldj.hmyg.presenter.LoginPresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.util.JpushUtil;
import com.hldj.hmyg.util.MyUtil;
import com.hldj.hmyg.util.SPUtil;
import com.hy.utils.GetServerUrl;
import com.loginjudge.LoginJudge;
import com.mrwujay.cascade.activity.BaseActivity;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import static com.hldj.hmyg.R.id.btn_clear_num;
import static com.hldj.hmyg.R.id.btn_clear_password;
import static com.hldj.hmyg.R.id.et_passward;
import static com.hldj.hmyg.R.id.et_phone;
import static com.hldj.hmyg.util.SPUtils.UserBean;

//public class LoginActivity extends BaseActivity {
public class LoginActivity extends BaseActivity {
    /**
     */
    private ImageView btn_back;
    private TextView find_passward;
    private TextView reg;

    public SharedPreferences.Editor editor;
    private String istab_c;


    HolderPwd holderPwd;


    //密码登录控件管理
    class HolderPwd {
        private TextView login;
        private EditText et_phone;
        private EditText et_passward;
        private ImageButton btn_clear_num;
        private ImageButton btn_clear_password;
    }


    //短信登录控件管理
    HolderNote holderNote;

    public class HolderNote {
        public EditText et_phone_note;
        public TextView tv_get_code_note;
        public EditText et_passward_note;
        public ImageButton btn_clear_password_note;
        public TextView login_note;

        public HolderNote() {

            //短信登录手机号码
            this.et_phone_note = (EditText) findViewById(R.id.et_phone_note);
            //清楚号码按钮
            this.btn_clear_password_note = (ImageButton) findViewById(R.id.btn_clear_phone_note);
            //获取验证码
            this.tv_get_code_note = (TextView) findViewById(R.id.tv_get_code_note);
            this.tv_get_code_note.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    D.e("======hehe======");
                    //获取验证码，倒计时，变色，不可点
                    LoginPresenter.getCode(MyUtil.getStrWithView(holderNote.et_phone_note), holderNote.tv_get_code_note, 20, new ResultCallBack<LoginGsonBean>() {
                        @Override
                        public void onSuccess(LoginGsonBean loginGsonBean) {
                            D.e("======短信发送成功=========id=");
                            holderNote.login_note.setSelected(true);
                            holderNote.login_note.setClickable(true);
                            //通过id 获取个人 信息 userinfo
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {
                            D.e("===============");
                        }
                    });

                }
            });
            TextWatcher mTextWatcher = new LoginPresenter.MyTextWatcher(this.et_phone_note, this.btn_clear_password_note, this.tv_get_code_note);
            this.et_phone_note.addTextChangedListener(mTextWatcher);

            //验证码输入框
            this.et_passward_note = (EditText) findViewById(R.id.et_passward_note);
            //登录按钮
            this.login_note = (TextView) findViewById(R.id.login_note);
            this.login_note.setOnClickListener(new MultipleClickProcess() {
                @Override
                public void onClick(View view) {
                    showToast("hellow world");
                    toLoginWithNote("" + holderNote.et_phone_note.getText(), "" + holderNote.et_passward_note.getText());
                }
            });
        }

    }

    public void toLoginWithNote(String phone, String smsCode) {

        //登录操作
        LoginPresenter.toLoginWithNote(phone, smsCode, new ResultCallBack<LoginGsonBean>() {
            @Override
            public void onSuccess(LoginGsonBean loginGsonBean) {

                //获取个人信息
                getUserInfo(loginGsonBean);

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

            }
        });


    }

    //获取个人信息
    public void getUserInfo(LoginGsonBean loginGsonBean) {
        D.e("========登录成功，返回id===========" + loginGsonBean.toString());
        //获取个人信息操作
        LoginPresenter.getUserInfo(loginGsonBean.getData().getUserId(), new ResultCallBack<UserInfoGsonBean>() {
            @Override
            public void onSuccess(UserInfoGsonBean userInfoGsonBean) {
                D.e("个人信息获取成功" + userInfoGsonBean.toString());

                finish();

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

            }
        });
    }

    ;;

    //密码登录控件管理
    public void initHolderPwd() {
        holderPwd = new HolderPwd();
        holderPwd.login = (TextView) findViewById(R.id.login);
        holderPwd.et_phone = (EditText) findViewById(et_phone);
        holderPwd.et_passward = (EditText) findViewById(et_passward);
        holderPwd.btn_clear_num = (ImageButton) findViewById(btn_clear_num);
        holderPwd.btn_clear_password = (ImageButton) findViewById(btn_clear_password);
        holderPwd.et_phone.addTextChangedListener(watcher_num);//电话号码
        holderPwd.et_passward.addTextChangedListener(watcher_num);
        holderPwd.btn_clear_num.setOnClickListener(multipleClickProcess);
        holderPwd.btn_clear_password.setOnClickListener(multipleClickProcess);
        holderPwd.login.setOnClickListener(multipleClickProcess);


    }


    private static MultipleClickProcess multipleClickProcess;

    /**
     * 初始化共同使用的 view 控件
     *
     * @param
     */
    public void commonView() {
        btn_back = (ImageView) findViewById(R.id.btn_back);
        reg = (TextView) findViewById(R.id.reg);
        find_passward = (TextView) findViewById(R.id.find_passward);
        btn_back.setOnClickListener(multipleClickProcess);
        editor = MyApplication.Userinfo.edit();
        reg.setOnClickListener(multipleClickProcess);
        find_passward.setOnClickListener(multipleClickProcess);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_2_0);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        multipleClickProcess = new MultipleClickProcess();//初始化点击事件
        initHolderPwd();

        holderNote = new HolderNote();
        commonView();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                addAmin2Login();//为logo添加动画
//            }
//        }, 3000);
    }

//    private void addAmin2Login() {
//        //使用AnimationUtils类的loadAnimation来加载xml格式的动画文件
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_scale);
//        ImageView iv = (ImageView) findViewById(R.id.iv_login_logo);
//        iv.clearAnimation();
//        iv.startAnimation(animation);
//    }

    TextWatcher watcher_num = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
            if (s.length() > 0) {

                if (holderPwd.et_phone.getText().toString().length() > 0) {
                    holderPwd.btn_clear_num.setVisibility(View.VISIBLE);
                    if (holderPwd.et_passward.getText().toString().length() > 5) {
                        holderPwd.login.setEnabled(true);
                        holderPwd.login.setSelected(true);
                    }
//                    holderPwd.login.setTextColor(getResources().getColor(R.color.white));
                }
            } else {
                if (holderPwd.et_phone.getText().toString().length() == 0)
                    holderPwd.btn_clear_num.setVisibility(View.GONE);

                holderPwd.login.setEnabled(false);
                holderPwd.login.setSelected(false);
//                holderPwd.login.setTextColor(getResources().getColor(R.color.white));

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


    @Override
    public void onBackPressed() {
        if (LoginJudge.isTabc) {
            LoginJudge.isTabc = false;
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

//		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
        overridePendingTransition_back();

    }


    /**
     * 显示短信登录  /    显示验证码登录
     */
    public void showNoteLogin(View view) {
        findViewById(R.id.include_login_note).setVisibility(View.VISIBLE);//短信登录显示
        findViewById(R.id.include_login_pwd).setVisibility(View.GONE);//密码登录显示
    }

    public void showPwdLogin(View view) {
        findViewById(R.id.include_login_note).setVisibility(View.GONE);
        findViewById(R.id.include_login_pwd).setVisibility(View.VISIBLE);
    }


    public class MultipleClickProcess implements OnClickListener {
        private boolean flag = true;

        private synchronized void setFlag() {
            flag = false;
        }

        public void onClick(View view) {
            if (flag) {
                switch (view.getId()) {
                    case R.id.btn_back:
                        onBackPressed();
                        break;
                    case btn_clear_num:
                        holderPwd.et_phone.setText("");
                        break;
                    case btn_clear_password:
                        holderPwd.et_passward.setText("");
                        break;
                    case R.id.login:
                        if (!submit()) {
                            return;
                        }
                        toLogin();
                        break;
                    case R.id.reg://注册
                        Intent toRegisterActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(toRegisterActivity);
                        overridePendingTransition_open();
                        break;
                    case R.id.find_passward:
                        Intent toSetPasswardByGetCodeActivity = new Intent(LoginActivity.this,
                                SetPasswardByGetCodeActivity.class);
                        startActivity(toSetPasswardByGetCodeActivity);
                        overridePendingTransition_open();
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

    private void toLogin() {
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, false);
        AjaxParams params = new AjaxParams();
        params.put("userName", holderPwd.et_phone.getText().toString());
        params.put("password", holderPwd.et_passward.getText().toString());

        finalHttp.post(GetServerUrl.getUrl() + "user/login", params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                LoginGsonBean loginGsonBean = new GsonUtil().formateJson2Bean(json, LoginGsonBean.class);
                if (loginGsonBean.getCode().equals("1")) {
                    SPUtil.put(LoginActivity.this, SPUtil.USER_ID, loginGsonBean.getData().getUserId());
                    //succeed
                    showToast(getString(R.string.login_succeed));
                    getUserInfo(loginGsonBean.getData().getUserId(), "LoginActivity");
                } else {
                    showToast(loginGsonBean.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                showToast(getString(R.string.error_net));
            }

        });

    }

    private boolean submit() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null
                && getCurrentFocus().getWindowToken() != null) {
            imm.hideSoftInputFromWindow(LoginActivity.this
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        // 隐藏关闭输入法
        // 判断是否为空
        if ("".equals(holderPwd.et_phone.getText().toString())
                || "".equals(holderPwd.et_passward.getText().toString())) {
            Toast.makeText(getApplicationContext(),
                    "以上内容都需要填写！请检查...", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void getUserInfo(String userId, final String activity) {
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, false);
        finalHttp.addHeader("authc", userId);
        AjaxParams params = new AjaxParams();
        finalHttp.post(GetServerUrl.getUrl() + "admin/user/getInfo", params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                UserInfoGsonBean userInfoGsonBean = new GsonUtil().formateJson2Bean(json, UserInfoGsonBean.class);
                save2SP(json);
//                        MyApplication.spUtils.putString(UserBean, json);//把json 存储在sp中，需要的话直接通过gson 转换
                //成功
                if (userInfoGsonBean.getCode().equals(ConstantState.SUCCEED_CODE)) {
                    String id = userInfoGsonBean.getData().getUser().getId();
                    JpushUtil.setAlias(id);
                    //设置 极光推送
                    if ("LoginActivity".equals(activity)) {
                        setResult(ConstantState.LOGIN_SUCCEED);
                        finish();


                    } else if ("SetProfileActivity".equals(activity.getClass().getName())) {
                        D.e("=========SetProfileActivity===============不消失=====================");
                    }
                }
                //失败
                if (userInfoGsonBean.getCode().equals(ConstantState.SUCCEED_CODE)) {

                    D.e("================获取个人信息失败失败=====================================");
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                showToast(getString(R.string.error_net));
            }

        });

    }

    private void save2SP(String json) {
        SPUtil.put(LoginActivity.this, UserBean, json);//把json 存储在sp中，需要的话直接通过gson 转换
        LoginPresenter.Save2Sp(editor, json);//把 个人数据放入sp 中
    }


}

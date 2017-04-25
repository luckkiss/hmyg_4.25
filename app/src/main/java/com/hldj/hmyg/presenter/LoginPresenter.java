package com.hldj.hmyg.presenter;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.LoginGsonBean;
import com.hldj.hmyg.bean.UserInfoGsonBean;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.util.JpushUtil;
import com.hy.utils.GetServerUrl;
import com.hy.utils.JsonGetInfo;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录注册的逻辑代码放这里来写
 * Created by Administrator on 2017/4/12.
 */

public class LoginPresenter {




    public static void toLoginWithNote(String phone, String smsCode, final ResultCallBack<LoginGsonBean> resultCallBack) {

        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, false);
        AjaxParams params = new AjaxParams();
        params.put("phone", phone);//手机号
        params.put("smsCode", smsCode);//短信验证码

        AjaxCallBack ajaxCallBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {

                //解析json  返回
                LoginGsonBean loginGsonBean = GsonUtil.formateJson2Bean(json, LoginGsonBean.class);
                resultCallBack.onSuccess(loginGsonBean);
                if (!loginGsonBean.getCode().equals(ConstantState.SUCCEED_CODE))
                    resultCallBack.onFailure(null, Integer.parseInt(loginGsonBean.getCode()), loginGsonBean.getMsg());
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                D.e("");

            }
        };
        finalHttp.post(GetServerUrl.getUrl() + "/user/smslogin", params, ajaxCallBack);

    }


    public void sayHellowWorld() {
        ToastUtil.showShortToast(MyApplication.getInstance(), "hellow world");
    }

    public static class MyTextWatcher implements TextWatcher {
        EditText editText;
        ImageView imageView;
        TextView tv_get_code_note;

        public MyTextWatcher(final EditText editText, final ImageButton imageView, final TextView tv_get_code_note) {
            this.editText = editText;
            this.imageView = imageView;
            this.tv_get_code_note = tv_get_code_note;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText("");
                    imageView.setVisibility(View.GONE);
                    tv_get_code_note.setSelected(false);
                    tv_get_code_note.setClickable(false);
                }
            });

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) imageView.setVisibility(View.GONE);
            else{ imageView.setVisibility(View.VISIBLE); }


            if (s.length() == 11) {
                tv_get_code_note.setSelected(true);
                tv_get_code_note.setClickable(true);
            } else {
                tv_get_code_note.setSelected(false);
            }


        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }


    }


    public static void Save2Sp(SharedPreferences.Editor editor, String json) {
        JSONObject user = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            user = jsonObject.getJSONObject(
                    "data").getJSONObject("user");
            editor.putString("id",
                    JsonGetInfo.getJsonString(user, "id"));
            editor.putString("code", JsonGetInfo.getJsonString(
                    user, "storeId"));
            editor.putBoolean("isNewRecord", JsonGetInfo
                    .getJsonBoolean(user, "isNewRecord"));
            editor.putString("createDate", JsonGetInfo
                    .getJsonString(user, "createDate"));
            editor.putString("updateDate", JsonGetInfo
                    .getJsonString(user, "updateDate"));
            editor.putString("delFlag", JsonGetInfo
                    .getJsonString(user, "delFlag"));
            editor.putString("userName", JsonGetInfo
                    .getJsonString(user, "userName"));
            editor.putString("realName", JsonGetInfo
                    .getJsonString(user, "realName"));
            editor.putString("phone", JsonGetInfo.getJsonString(
                    user, "phone"));
            editor.putString("sex",
                    JsonGetInfo.getJsonString(user, "sex"));
            editor.putString("tel",
                    JsonGetInfo.getJsonString(user, "tel"));
            editor.putString("email", JsonGetInfo.getJsonString(
                    user, "email"));
            editor.putString("cityCode", JsonGetInfo
                    .getJsonString(user, "cityCode"));
            editor.putString("address", JsonGetInfo
                    .getJsonString(user, "address"));
            editor.putString("companyName", JsonGetInfo
                    .getJsonString(user, "companyName"));
            editor.putString("publicName", JsonGetInfo
                    .getJsonString(user, "publicName"));
            editor.putString("publicPhone", JsonGetInfo
                    .getJsonString(user, "publicPhone"));
            editor.putString("headImage", JsonGetInfo
                    .getJsonString(user, "headImage"));

            // if
            // (!"".equals(JsonGetInfo.getJsonString(user,
            // "companyName"))) {
            // e.putString("showUserName", JsonGetInfo
            // .getJsonString(user, "companyName"));
            // } else if
            // ("".equals(JsonGetInfo.getJsonString(
            // user, "companyName"))
            // && !"".equals(JsonGetInfo
            // .getJsonString(user,
            // "publicName"))) {
            // e.putString("showUserName", JsonGetInfo
            // .getJsonString(user, "publicName"));
            //
            // } else if
            // ("".equals(JsonGetInfo.getJsonString(
            // user, "companyName"))
            // && "".equals(JsonGetInfo.getJsonString(
            // user, "publicName"))) {
            // e.putString("showUserName", JsonGetInfo
            // .getJsonString(user, "realName"));
            //
            // } else {
            // e.putString("showUserName", JsonGetInfo
            // .getJsonString(user, "userName"));
            // }

            if (!"".equals(JsonGetInfo.getJsonString(user,
                    "realName"))) {
                editor.putString("showUserName", JsonGetInfo
                        .getJsonString(user, "realName"));
            } else {
                editor.putString("showUserName", JsonGetInfo
                        .getJsonString(user, "userName"));
            }

            editor.putString("status", JsonGetInfo
                    .getJsonString(user, "status"));
            editor.putBoolean("isInvoices", JsonGetInfo
                    .getJsonBoolean(user, "isInvoices"));
            editor.putBoolean("receiptMsg", JsonGetInfo
                    .getJsonBoolean(user, "receiptMsg"));
            editor.putBoolean("isDirectAgent", JsonGetInfo
                    .getJsonBoolean(user, "isDirectAgent"));
            editor.putBoolean("isAgent", JsonGetInfo
                    .getJsonBoolean(user, "isAgent"));
            editor.putBoolean("isPartners", JsonGetInfo
                    .getJsonBoolean(user, "isPartners"));
            editor.putBoolean("isPurchaseConfirm", JsonGetInfo
                    .getJsonBoolean(user,
                            "isPurchaseConfirm"));
            editor.putString("agentTypeName", JsonGetInfo
                    .getJsonString(user, "agentTypeName"));
            editor.putString("coCityfullName", JsonGetInfo
                    .getJsonString(JsonGetInfo
                                    .getJSONObject(user, "coCity"),
                            "fullName"));
            editor.putString("coCitycityCode", JsonGetInfo
                    .getJsonString(JsonGetInfo
                                    .getJSONObject(user, "coCity"),
                            "cityCode"));
            editor.putString("twCityfullName", JsonGetInfo
                    .getJsonString(JsonGetInfo
                                    .getJSONObject(user, "twCity"),
                            "name"));
            editor.putString("twCitycityCode", JsonGetInfo
                    .getJsonString(JsonGetInfo
                                    .getJSONObject(user, "twCity"),
                            "cityCode"));
            editor.putString("prCode", JsonGetInfo
                    .getJsonString(user, "prCode"));
            editor.putString("ciCode", JsonGetInfo
                    .getJsonString(user, "ciCode"));
            editor.putString("coCode", JsonGetInfo
                    .getJsonString(user, "coCode"));
            editor.putString("twCode", JsonGetInfo
                    .getJsonString(user, "twCode"));
            editor.putString("storeId", JsonGetInfo
                    .getJsonString(user, "storeId"));
            editor.putBoolean("isClerk", JsonGetInfo
                    .getJsonBoolean(user, "isClerk"));
            editor.putBoolean("isLogin", true);
            editor.putBoolean("notification",
                    MyApplication.Userinfo.getBoolean(
                            "notification", false));
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取验证码
     *
     * @param tv_get_code_note
     */
    public static void getCode(String phString, final TextView tv_get_code_note, int my_count, ResultCallBack<LoginGsonBean> resultCallBack) {

        //倒计时
        new TimeCount(60000, 1000, tv_get_code_note).start();

        //调用短信接口
        Checkphone(phString, resultCallBack);

    }


    /**
     * 获取个人信息
     *
     * @param userId
     * @param resultCallBack
     */
    public static void getUserInfo(final String userId, final ResultCallBack<UserInfoGsonBean> resultCallBack) {


        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, false);
        finalHttp.addHeader("authc", userId);
        AjaxParams params = new AjaxParams();
        finalHttp.post(GetServerUrl.getUrl() + "admin/user/getInfo", params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                UserInfoGsonBean userInfoGsonBean = new GsonUtil().formateJson2Bean(json, UserInfoGsonBean.class);

//                        MyApplication.spUtils.putString(UserBean, json);//把json 存储在sp中，需要的话直接通过gson 转换
                //成功
                if (userInfoGsonBean.getCode().equals(ConstantState.SUCCEED_CODE)) {
                    String id = userInfoGsonBean.getData().getUser().getId();
                    JpushUtil.setAlias(id);


                    Save2Sp(MyApplication.Userinfo.edit(), json);


                    //设置 极光推送
                    resultCallBack.onSuccess(userInfoGsonBean);


                }

                if (userInfoGsonBean.getCode().equals(ConstantState.ERROR_CODE)) {

                    D.e("================获取个人信息失败=====================================");
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                resultCallBack.onFailure(t, errorNo, strMsg);
            }

        });


    }


    public static void Checkphone(final String phString, final ResultCallBack<LoginGsonBean> resultCallBack) {
        // TODO Auto-generated method stub
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, false);
        AjaxParams params = new AjaxParams();
        params.put("phone", phString);
        //    http://hmeg.cn:93/common/getSmsCode/&phone=17074990702
        finalHttp.post(GetServerUrl.getUrl() + "common/getSmsCode", params,
                new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String t) {

                        LoginGsonBean loginGsonBean = GsonUtil.formateJson2Bean(t, LoginGsonBean.class);

                        if ("1".equals(loginGsonBean.getCode())) {
                            ToastUtil.showShortToast("验证码已经发送至：" + phString);
                            resultCallBack.onSuccess(GsonUtil.formateJson2Bean(t, LoginGsonBean.class));
                        } else {
                            ToastUtil.showShortToast("faild");
                            resultCallBack.onFailure(null, 10, "faild");
                        }


                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        Toast.makeText(MyApplication.getInstance(), R.string.error_net,
                                Toast.LENGTH_SHORT).show();
                        resultCallBack.onFailure(t, errorNo, strMsg);
                    }

                });
    }


    //倒计时线程
    static class TimeCount extends CountDownTimer {

        TextView btn_get_code;

        public TimeCount(long millisInFuture, long countDownInterval, TextView btn_get_code) {
            super(millisInFuture, countDownInterval);
            this.btn_get_code = btn_get_code;
        }

        @Override
        public void onTick(long millisUntilFinished) {

            btn_get_code.setClickable(false);
            btn_get_code.setSelected(false);//选中 字体变灰色
            btn_get_code.setText("重新获取" + millisUntilFinished / 1000 + "S");
        }

        @Override
        public void onFinish() {
            btn_get_code.setText("重新获取");
            btn_get_code.setSelected(true);
            btn_get_code.setClickable(true);
        }
    }


}

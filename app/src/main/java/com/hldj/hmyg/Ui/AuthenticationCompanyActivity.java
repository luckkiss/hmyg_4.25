package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.M.userIdentity.CompanyIdentity;
import com.hldj.hmyg.M.userIdentity.enums.CompanyIdentityStatus;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.MyImageViewShowUserCard;
import com.hldj.hmyg.widget.StepView;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;

import java.util.ArrayList;

import static com.hldj.hmyg.R.id.et_name;

/**
 * 实名认证界面
 */

public class AuthenticationCompanyActivity extends AuthenticationActivity {


    /* 企业认证实体 */
    private CompanyIdentity companyIdentity;
    private String legalPersonName;

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_authentication_company;
    }


    public static void start(Activity mActivity, int state, String failedMsg) {
        Intent intent = new Intent(mActivity, AuthenticationCompanyActivity.class);
        intent.putExtra("state", state);
        intent.putExtra("failedMsg", failedMsg);
        mActivity.startActivity(intent);
    }

    @Override
    public String setTitle() {
        return "企业认证";
    }


    @Override
    public void requestData() {
        new BasePresenter()
                .putParams("storeId", MyApplication.getUserBean().storeId)
                .doRequest("admin/companyIdentity/detail", true, new HandlerAjaxCallBack(mActivity) {


                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {

                        /* 获取 审核数据成功  */

                        D.i("onRealSuccess: " + gsonBean.getData().companyIdentity.toString());


                        companyIdentity = gsonBean.getData().companyIdentity;

                        initExtrasCompanyIdentity(companyIdentity);


                    }
                });
    }

    /**
     * 初始企业认证 实体
     *
     * @param identity
     */
    private void initExtrasCompanyIdentity(CompanyIdentity identity) {

        if (identity == null) {
            D.w(" identity  instance is null ---   ");
            return;
        }


        http_zheng = companyIdentity.licenceImageUrl;
        http_fan = companyIdentity.legalPersonImageUrl;


        TextView tv_top_tip = getView(R.id.tv_top_tip);

        StepView stepView = getView(R.id.step_view);

        if (!TextUtils.isEmpty(getFailedMsg()) && getAuthingState() == failed) {
            tv_top_tip.setVisibility(View.VISIBLE);
        } else {
            tv_top_tip.setVisibility(View.GONE);
        }
        tv_top_tip.setText(getFailedMsg());
        ImageView imageView = getView(R.id.iv_auth_states);


        switch (getAuthingState()) {
            case no_auth:/* 未认证 --  发起认证 */

                doNoAuth();
                break;

            case succeed:/*认证成功 */
                imageView.setImageResource(R.mipmap.auth_shtg);
                imageView.setVisibility(View.VISIBLE);
                doAuthing();
                break;

            case authing:/*认证中*/
                imageView.setImageResource(R.mipmap.auth_shz);
                imageView.setVisibility(View.VISIBLE);
            /*审核中 */

                doAuthing();

                break;

            case failed:
                 /*认证失败*/

                doAuthFailed();


                break;

        }

        stepView.setStep(getAuthingState());

        if (getAuthingState() == failed) {
            stepView.setVisibility(View.GONE);
        }


    }


    /* 获取失败信息 */
    public String getFailedMsg() {
        return companyIdentity.auditLog;
//        return getIntent().getStringExtra("failedMsg");
    }

    /* 对状态进行处理 */


    /* 获取 当前认证状态 */
    public int getAuthingState() {

        if (companyIdentity == null || companyIdentity.status == null) {
            return getIntent().getIntExtra("state", no_auth);
        } else {
            if (companyIdentity.status.compareTo(CompanyIdentityStatus.unaudited) == 0) {
                return no_auth;
            } else if (companyIdentity.status.compareTo(CompanyIdentityStatus.auditing) == 0) {
                return authing;
            } else if (companyIdentity.status.compareTo(CompanyIdentityStatus.pass) == 0) {
                return succeed;
            } else if (companyIdentity.status.compareTo(CompanyIdentityStatus.back) == 0) {
                return failed;
            } else {
                return getIntent().getIntExtra("state", no_auth);
            }
        }

//        return getIntent().getIntExtra("state", no_auth);
//        return getIntent().getIntExtra("state", no_auth);
    }


    /*审核中*/
    public void doAuthing() {
        getView(R.id.btn_save).setVisibility(View.GONE);
        getView(R.id.iv_zheng).setVisibility(View.GONE);
        getView(R.id.iv_fan).setVisibility(View.GONE);
        getView(R.id.tv_zheng).setVisibility(View.GONE);
        getView(R.id.tv_fan).setVisibility(View.GONE);

        getView(R.id.iv_zheng_shili).setVisibility(View.VISIBLE);
        getView(R.id.iv_fan_shili).setVisibility(View.VISIBLE);

        View.OnClickListener listener = v -> {
            ArrayList<Pic> ossUrls = new ArrayList<>();
            ossUrls.add(new Pic("", false, http_zheng, 0));
            ossUrls.add(new Pic("", false, http_fan, 1));
            GalleryImageActivity.startGalleryImageActivity(mActivity, 0, ossUrls);
        };
        View.OnClickListener listener1 = v -> {
            ArrayList<Pic> ossUrls = new ArrayList<>();
            ossUrls.add(new Pic("", false, http_zheng, 0));
            ossUrls.add(new Pic("", false, http_fan, 1));
            GalleryImageActivity.startGalleryImageActivity(mActivity, 1, ossUrls);
        };


        getView(R.id.iv_zheng_shili).setOnClickListener(listener);
        getView(R.id.iv_fan_shili).setOnClickListener(listener1);


        finalBitmap.display(getView(R.id.iv_zheng_shili), http_zheng);
        getView(R.id.iv_zheng_shili).setPadding(0, 0, 0, 0);


        finalBitmap.display(getView(R.id.iv_fan_shili), http_fan);
        getView(R.id.iv_fan_shili).setPadding(0, 0, 0, 0);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finalBitmap.display(getView(R.id.iv_zheng_shili), http_zheng);
                finalBitmap.display(getView(R.id.iv_fan_shili), http_fan);
            }
        }, 1000);


        setText(getView(R.id.et_name), companyIdentity.companyName);
        setText(getView(R.id.et_card_num), companyIdentity.licenceNum);
        setText(getView(R.id.et_card_faren_name), companyIdentity.legalPersonName);
        EditText editText = ((EditText) getView(et_name));
        editText.setFocusable(false);
        EditText et_card_num = ((EditText) getView(R.id.et_card_num));
        et_card_num.setFocusable(false);

        EditText et_card_faren_name = ((EditText) getView(R.id.et_card_faren_name));
        et_card_faren_name.setFocusable(false);

    }

    /*认证失败*/
    public void doAuthFailed() {

//        setText(getView(et_name), "大傻么么哒");
//        setText(getView(et_card_num), "350435465476416549647");

        setText(getView(R.id.et_name), companyIdentity.companyName);
        setText(getView(R.id.et_card_num), companyIdentity.licenceNum);
        setText(getView(R.id.et_card_faren_name), companyIdentity.legalPersonName);

        iv_fan.showUpAgain();
        iv_zheng.showUpAgain();

        Log.i("loadCompletedisplay", "start: ");

        Displayer displayer = new Displayer() {
            @Override
            public void loadCompletedisplay(View view, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig) {


                if (view instanceof MyImageViewShowUserCard) {
                    ImageView aa = ((ImageView) view);

                    aa.setBackground(new BitmapDrawable(bitmap));
                    aa.setImageResource(R.drawable.chongxinshangchuan);

                    if (aa == iv_fan) {
                        aa.setTag(http_fan);
                    } else {
                        aa.setTag(http_zheng);
                    }

                    Log.i("loadCompletedisplay", "loadCompletedisplay: ");

//                ToastUtil.showLongToast("-----加载结束----");
                }


            }

            @Override
            public void loadFailDisplay(View view, Bitmap bitmap) {

            }
        };


        finalBitmap.configDisplayer(displayer);

//        finalBitmap.clearCache(http_zheng);
        finalBitmap.display(iv_zheng, http_zheng);
        Log.i("loadCompletedisplay", "end: ");

        if (finalBitmap.getBitmapFromCache(http_zheng) != null) {


            iv_zheng.setBackground(new BitmapDrawable(finalBitmap.getBitmapFromCache(http_zheng)));
            iv_zheng.setImageResource(R.drawable.chongxinshangchuan);
            iv_zheng.setTag(http_zheng);
            Log.i("loadCompletedisplay", "loadCompletedisplay: ");

        }

        finalBitmap.display(iv_fan, http_fan);


        if (finalBitmap.getBitmapFromCache(http_fan) != null) {


            iv_fan.setBackground(new BitmapDrawable(finalBitmap.getBitmapFromCache(http_fan)));
            iv_fan.setImageResource(R.drawable.chongxinshangchuan);
            iv_fan.setTag(http_fan);
            Log.i("loadCompletedisplay", "loadCompletedisplay: ");

        }

//            }
//        }, 1200);


    }


    @Override
    public void doSave() {
//        CompanyIdentity identity = new CompanyIdentity();
        new BasePresenter()
                .putParams(ConstantParams.id, companyIdentity.id)
                .putParams(ConstantParams.companyName, getRealName())
                .putParams(ConstantParams.licenceNum, getIdentityNum())
                .putParams(ConstantParams.legalPersonName, getLegalPersonName())
                .putParams(ConstantParams.storeId, MyApplication.getUserBean().storeId)
                .putParams(ConstantParams.licenceData, pic_json1)
                .putParams(ConstantParams.legalPersonData, pic_json2)
                .doRequest("admin/companyIdentity/save", true, new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {

                        ToastUtil.showLongToast(gsonBean.msg);
                        finish();

                    }
                });
    }


    // 获取法人姓名
    public String getLegalPersonName() {
        return getText(getView(R.id.et_card_faren_name));
    }


    public Pic getFanImage() {
        return new Pic(companyIdentity.licenceImageId, true, companyIdentity.licenceImageUrl, 0);
    }

    public Pic getBackImage() {
        return new Pic(companyIdentity.legalPersonImageId, true, companyIdentity.legalPersonImageUrl, 1);
    }
}

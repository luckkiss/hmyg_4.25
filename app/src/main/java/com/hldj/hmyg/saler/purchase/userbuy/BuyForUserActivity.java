package com.hldj.hmyg.saler.purchase.userbuy;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.CityGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.Ui.CityWheelDialogF;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.bean.UserPurchase;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.FUtil;
import com.hy.utils.GetServerUrl;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.lang.reflect.Type;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 用户求购
 */
public class BuyForUserActivity extends BaseMVPActivity {


    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycle;


    @ViewInject(id = R.id.fbqg)
    ImageView fbqg;

    @ViewInject(id = R.id.select_city)
    TextView select_city;

    @ViewInject(id = R.id.sptv_program_do_search, click = "sptv_program_do_search")
    View sptv_program_do_search;

    @ViewInject(id = R.id.et_program_serach_text)
    EditText et_program_serach_text;

    private String mCityCode = "";


    public void sptv_program_do_search(View view) {
        recycle.onRefresh();
    }

    private String getSearchContent() {
        return et_program_serach_text.getText().toString();
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_buy_user;
    }


    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);
        fbqg.setOnClickListener(v -> 发布求购());
        et_program_serach_text.setOnEditorActionListener((arg0, arg1, arg2) -> {
            if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                sptv_program_do_search(null);
            }
            return false;
        });

        select_city
                .setOnClickListener(v -> {

//                    ComonCityWheelDialogF.instance().addSelectListener((province, city, distrect, cityCode) -> {
////                        ToastUtil.showLongToast(province + " " + city);
//                        mCityCode = cityCode.substring(0, 4);
//                        select_city.setText(province + " " + city);
//                        recycle.onRefresh();
//                    }).show(getSupportFragmentManager(), TAG);


                    CityWheelDialogF.instance()
                            .isShowCity(true)
                            .isShow全国(true)
                            .addSelectListener(new CityWheelDialogF.OnCitySelectListener() {
                                @Override
                                public void onCitySelect(CityGsonBean.ChildBeans childBeans) {

                                    mCityCode = childBeans.cityCode;
                                    select_city.setText(childBeans.fullName);
                                    recycle.onRefresh();

                                }

                                @Override
                                public void onProvinceSelect(CityGsonBean.ChildBeans childBeans) {

                                }
                            }).show(getSupportFragmentManager(), "SellectActivity2");


                });

        initRecycleView(recycle);


        requestData(0);

    }


    private void 发布求购() {
        PublishForUserActivity.start2Activity(mActivity);
    }

    private void initRecycleView(CoreRecyclerView recycle) {

        recycle
                .init(new BaseQuickAdapter<UserPurchase, BaseViewHolder>(R.layout.item_buy_for_user) {
                    @Override
                    protected void convert(BaseViewHolder helper, UserPurchase item) {
                        helper.convertView.setOnClickListener(v -> PublishForUserDetailActivity.start2Activity(mActivity, item.id, item.ownerId));
                        doConvert(helper, item, mActivity);

                        if (MyApplication.getUserBean().id.equals(item.ownerId)) {
                            if (GetServerUrl.isTest)
                                helper.setTextColorRes(R.id.title, R.color.main_color);
                            else helper.setTextColorRes(R.id.title, R.color.text_color666);
                        }


                    }
                })
                .openRefresh()
                .openLoadMore(999, page -> requestData(page))
        ;


    }

    public static void doConvert(BaseViewHolder helper, UserPurchase item, NeedSwipeBackActivity mActivity) {

        helper.setText(R.id.title, FUtil.$_zero(item.name + ""));

        helper.setText(R.id.shuliang, FUtil.$_zero(item.count + "/" + item.unitTypeName));

//        helper.setText(R.id.shuliang, FUtil.$_zero(item.count + ""));

        if (TextUtils.isEmpty(FUtil.$_zero_2_null(item.quoteCountJson))) {
            helper.setText(R.id.qubaojia, "暂无报价 ");
            helper.setTextColorRes(R.id.qubaojia, R.color.text_color999);
        } else {
            helper.setText(R.id.qubaojia, FUtil.$(item.quoteCountJson) + "条报价");
            helper.setTextColorRes(R.id.qubaojia, R.color.main_color);
        }
        helper.setText(R.id.space_text, item.specText);
        helper.setText(R.id.city, "用苗地:" + item.cityName);
        helper.setText(R.id.update_time, "结束时间:" + item.closeDateStr + "");

        if (helper.getView(R.id.state) != null)
            helper.setVisible(R.id.state, item.attrData.isUserQuoted);

        if (item.attrData.isExclude) {
            helper.setVisible(R.id.state, item.attrData.isUserQuoted);
            helper.setImageResource(R.id.state, R.mipmap.buheshi);

        }

        //                    this.rootView = rootView;
//                    this.title = (TextView) rootView.findViewById(R.id.title);
//                    this.shuliang = (TextView) rootView.findViewById(R.id.shuliang);
//                    this.qubaojia = (TextView) rootView.findViewById(R.id.qubaojia);
//                    this.space_text = (TextView) rootView.findViewById(R.id.space_text);
//                    this.city = (TextView) rootView.findViewById(R.id.city);
//                    this.update_time = (TextView) rootView.findViewById(R.id.update_time);
//                    this.bao_jia_num = (TextView) rootView.findViewById(R.id.bao_jia_num);
//                    this.state = (ImageView) rootView.findViewById(R.id.state);

    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start2Activity(Activity mActivity) {
        Intent intent = new Intent(mActivity, BuyForUserActivity.class);
        mActivity.startActivity(intent);

    }

    @Override
    public String setTitle() {
        return "用户求购";
    }


    private static final String TAG = "BuyForUserActivity";


    /*  test page gsonbean  format */
    public void requestData(int page) {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<UserPurchase>>>>() {
        }.getType();


        new BasePresenter()
                .putParams(ConstantParams.pageIndex, page + "")
                .putParams("cityCode", mCityCode)
                .putParams(ConstantParams.name, getSearchContent())
                .doRequest("userPurchase/list", true, new HandlerAjaxCallBackPage<UserPurchase>(mActivity, type, UserPurchase.class) {
                    @Override
                    public void onRealSuccess(List<UserPurchase> seedlingBeans) {
                        Log.i(TAG, "onRealSuccess: " + seedlingBeans);
                        Log.i(TAG, "onRealSuccess: " + seedlingBeans);
                        recycle.getAdapter().addData(seedlingBeans);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        recycle.selfRefresh(false);
                    }
                });
    }

    /**
     * new BasePresenter()
     * .doRequest("userPurchase/list", true, new HandlerAjaxCallBackPage<List<SaveSeedingGsonBean.DataBean.SeedlingBean>>() {
     *
     * @Override public void onRealSuccess(SimpleGsonBean_new<SimplePageBean<List<SaveSeedingGsonBean.DataBean.SeedlingBean>>> beanTest) {
     * String str = beanTest.msg;
     * Log.i(TAG, "onRealSuccess: " + str);
     * <p>
     * // 最终  想要的结果就是  传出 list数组
     * }
     * });
     */

//    public void requestData() {
//        new BasePresenter()
//                .doRequest("userPurchase/list", true, new AjaxCallBack<String>() {
//                    @Override
//                    public void onSuccess(String json) {
//                        Log.i(TAG, "onSuccess: " + json);
//                        Type beanType = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<SaveSeedingGsonBean.DataBean.SeedlingBean>>>>() {
//                        }.getType();
//                        SimpleGsonBean_new<SimplePageBean<List<SaveSeedingGsonBean.DataBean.SeedlingBean>>> bean_new = GsonUtil.formateJson2Bean(json, beanType);
//
//
//                        Log.i(TAG, "onSuccess: ");
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t, int errorNo, String strMsg) {
//                        super.onFailure(t, errorNo, strMsg);
//                    }
//                });
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConstantState.PUBLIC_SUCCEED) {
            recycle.onRefresh();
        }
    }
}

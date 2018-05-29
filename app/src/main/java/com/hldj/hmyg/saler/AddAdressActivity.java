package com.hldj.hmyg.saler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hldj.hmyg.M.AddressBean;
import com.hldj.hmyg.MainActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.CityGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.Ui.CityWheelDialogF;
import com.hldj.hmyg.buyer.Ui.OnlyDirstreetWheelDialogF;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.widget.MyOptionItemView;
import com.hy.utils.GetServerUrl;
import com.hy.utils.JsonGetInfo;
import com.hy.utils.SpanUtils;
import com.hy.utils.ToastUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.zf.iosdialog.widget.AlertDialog;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class AddAdressActivity extends BaseMVPActivity {

    private static final String TAG = "AddAdressActivity";
    AddressBean addressBean;


    @Override
    public int bindLayoutID() {
        return R.layout.activity_add_adress_new;
    }


    @Override
    public void initView() {


//        ToastUtil.showShortToast("添加地址界面");
        addressBean = new AddressBean();

//        <font color='#e75b45'> (仅用于个人管理用，不对外展示)</font>
        SpannableStringBuilder spannableStringBuilder = new SpanUtils()
                .append("苗圃联系方式")
                .append(" (仅用于个人管理用，不对外展示)").setForegroundColor(mActivity.getResources().getColor(R.color.price_orige))
                .create();
        setText(getView(R.id.lxfs_title), spannableStringBuilder);


        //商城联系人方式<font color='#999999'> (在商城显示的联系方式)</font>
        SpannableStringBuilder spannableStringBuilder1 = new SpanUtils()
                .append("商城联系方式")
                .append(" (在商城显示的联系方式)").setForegroundColor(mActivity.getResources().getColor(R.color.text_color999)).setFontSize(13, true)
                .create();
        setText(getView(R.id.sclxfs_title), spannableStringBuilder1);


        if (getExtral() != null) {
            D.e("==========extral==== \n" + getExtral().toString());
            addressBean = getExtral();
            //修改数据
            initExtral(getExtral());

            setTitle("编辑苗圃");

//            this.setBackgroAndTextAndVis(getView(R.id.toolbar_right_text), "删除", R.drawable.red_btn_selector);


            getView(R.id.toolbar_right_text).setVisibility(View.GONE);


        } else {
            //添加数据
            setTitle("新增苗圃");
            autoSetLoc();
            EditText editText = getView(R.id.et_aaa_contactPhone);
            editText.setText(MyApplication.getUserBean().phone);


            //获取定位
            //右上角 删除按钮的显示
        }


        isShowDelete(getExtral() != null);


    }


    private void isShowDelete(boolean isShow) {
        getView(R.id.tv_delete).setVisibility(isShow ? View.VISIBLE : View.GONE);

        getView(R.id.tv_delete).setOnClickListener(v -> {
            new AlertDialog(this).builder()
                    .setTitle("确定删除本条地址?")
                    .setPositiveButton("确定删除", v1 -> {
                        DelAdress(getExtral().id);
                    }).setNegativeButton("取消", v2 -> {
            }).show();

        });

    }

    /**
     * 通过定位  获取地址
     */
    private void autoSetLoc() {

        if (MainActivity.aMapLocation != null) {
            addressBean.cityCode = MainActivity.aMapLocation.getAdCode();
            addressBean.longitude = Double.parseDouble(MainActivity.longitude);
            addressBean.latitude = Double.parseDouble(MainActivity.latitude);
            addressBean.detailAddress = FUtil.$(" ", MainActivity.aMapLocation.getStreet(), MainActivity.aMapLocation.getStreetNum(), MainActivity.aMapLocation.getAoiName());

            addressBean.cityName = MainActivity.aMapLocation.getProvince() + MainActivity.aMapLocation.getCity() + MainActivity.aMapLocation.getDistrict();
        }
//        addressBean.contactPhone = MyApplication.getUserBean().phone;
//        addressBean.contactName = MyApplication.getUserBean().realName;
        initExtral(addressBean);
        /**
         * street=七星西路#streetNum=31号#aoiName=七星大厦(七星西路)
         */
    }

    @Override
    protected void initListener() {
        //提交按钮点击事件是公用的
        getView(R.id.tv_save).setOnClickListener(v -> {

            if (!submit(getView(R.id.et_aaa_contactPhone), "商城联系电话")
                    || !submit(getView(R.id.et_aaa_name), "苗圃名称")) {
                return;
            }


            MyPresenter myPresenter = new MyPresenter();

//            if (true) {
//
//                return;
//            }
            D.e("====constructionBean()====== \n" + constructionBean().toString());

            //构造 参数 进行上传
            myPresenter.doSave(constructionBean(), new AjaxCallBack<String>() {
                @Override
                public void onSuccess(String json) {

                    try {
                        SimpleGsonBean bean = GsonUtil.formateJson2Bean(json, SimpleGsonBean.class);
                        hindLoading();
                        if (bean.isSucceed()) {
                            if (isAddAddr()) {
                                AdressActivity.Address address = new AdressActivity.Address();
                                address.isDefault = bean.getData().nursery.isDefault;
                                address.addressId = bean.getData().nursery.id;
                                address.fullAddress = bean.getData().nursery.fullAddress;
                                address.contactName = bean.getData().nursery.contactName;
                                address.contactPhone = bean.getData().nursery.contactPhone;
                                address.name = bean.getData().nursery.name;
                                Intent intent = new Intent();
                                intent.putExtra("address", address);
                                setResult(ConstantState.ADD_SUCCEED, intent);
                            } else {
                                setResult(ConstantState.CHANGE_DATES);
                            }

                            finish();
                        } else {
                            ToastUtil.showShortToast(bean.msg);
                        }
                    } catch (Exception e) {
                        setResult(ConstantState.ADD_SUCCEED);
                        finish();
                        D.e("=============自动显示失败========");
                        CrashReport.postCatchedException(e);
                        e.printStackTrace();
                    }
                }


                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    ToastUtil.showShortToast("网络错误，请稍后重试");
                    hindLoading();
                    super.onFailure(t, errorNo, strMsg);
                }
            });
        });

        getView(R.id.tv_aaa_area).setOnClickListener(v -> {
            CityWheelDialogF.instance().addSelectListener(new CityWheelDialogF.OnCitySelectListenerWrap() {
                @Override
                public void onDistrectSelect(CityGsonBean.ChildBeans distrect) {
                    resetDirs(distrect.fullName);
                    setText(getView(R.id.tv_aaa_area), distrect.fullName);
//                    MyOptionItemView myOptionItemView = getView(R.id.tv_aaa_area);
//                    myOptionItemView.setRightText(distrect.fullName);

                    addressBean.cityCode = distrect.cityCode;


//                    addressBean.cityCode = "0203";
                }
            })
                    .isShowCity(true)
                    .isShowDistrict(true)
                    .show(getSupportFragmentManager(), TAG);

        });
//        getView(R.id.tv_aaa_street).setOnClickListener(v -> {
////            addressBean.cityCode
//            MyPresenter myPresenter = new MyPresenter();
//            myPresenter.getStreets(addressBean.cityCode);
//
//        });
        getView(R.id.tv_aaa_loac).setOnClickListener(v -> {
//            Intent intent = new Intent(mActivity, EventsActivity.class);
            EventsActivity.start(mActivity, addressBean.latitude, addressBean.longitude);
//            startActivityForResult(intent, 1);
        });
    }

    private boolean submit(EditText editText, String errorMsgHead) {
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            ToastUtil.showShortToast(errorMsgHead + " 必须填写");
            return false;
        }
        return true;
    }

    /**
     * 初始化 传递过来的 地址对象
     *
     * @param extral 地址对象
     */
    private void initExtral(AddressBean extral) {
        if (extral == null) {
            extral = new AddressBean();
        }

        this.setText(getView(R.id.et_aaa_name), extral.name);//苗圃名称
        this.setText(getView(R.id.et_aaa_contactName), extral.contactName);//联系人
        this.setText(getView(R.id.et_aaa_contactPhone), extral.contactPhone);//联系电话
        this.setText(getView(R.id.tv_aaa_area), extral.cityName);//所在区域
//        this.setText(getView(R.id.tv_aaa_street), extral.cityName.replace(GetCitiyNameByCode.initProvinceDatas(mActivity, extral.cityCode), ""));
// GetCitiyNameByCode.initProvinceDatas(mActivity, extral.cityCode)
//        CheckBox checkBox = getView(R.id.checkBox3);
//        checkBox.setChecked(extral.isDefault);


        if (extral.latitude != 0 && extral.longitude != 0) {
            this.setText(getView(R.id.tv_aaa_loac), "已标注");
        }
        this.setText(getView(R.id.et_aaa_detailAddress), extral.detailAddress);

        this.setText(getView(R.id.et_aaa_nurseryContactName), extral.nurseryContactName);

        this.setText(getView(R.id.et_aaa_nurseryContactPhone), extral.nurseryContactPhone);


//        id = getExtral().id;
//        name = getExtral().name;
//        cityCode = getExtral().cityCode;
//        cityName = getExtral().cityName;
//        mCurrentZipCode = getExtral().ciCode;
//        twCitycityCode = getExtral().ciCode;
//        detailAddress = getExtral().detailAddress;
//        contactName = getExtral().contactName;
//        contactPhone = getExtral().contactPhone;
//        nurseryArea = getExtral().nurseryArea + "";


    }


    /**
     * 是否 添加
     * true 添加
     * false 修改
     *
     * @return
     */
    private boolean isAddAddr() {
        return getExtral() == null;
    }

    /**
     * 不同的时候重置
     *
     * @param newName
     */
    public void resetDirs(String newName) {
        String oldName = ((MyOptionItemView) getView(R.id.tv_aaa_area)).getRightText();
        if (!newName.equals(oldName)) {
//            this.setText(getView(R.id.tv_aaa_street), "");
            addressBean.twCode = "";
        }
    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    @Override
    protected void onActivityResult(int arg0, int arg1, Intent data) {
        // TODO Auto-generated method stub
        if (arg1 == 1) {
            addressBean.latitude = data.getDoubleExtra("latitude", 0.0);
            addressBean.longitude = data.getDoubleExtra("longitude", 0.0);
            this.setText(getView(R.id.tv_aaa_loac), "已标注");
        }
        super.onActivityResult(arg0, arg1, data);
    }


    public AddressBean getExtral() {
        if (getIntent() == null) {
            return null;
        }
        return ((AddressBean) getIntent().getSerializableExtra(TAG));
    }

    public static void start2Activity(Activity activity, AddressBean addressBean) {
        Intent intent = new Intent(activity, AddAdressActivity.class);
        if (addressBean != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(TAG, addressBean);
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, 123);
    }


    public void setText(View tv, String str) {

        if (tv instanceof TextView) {

            ((TextView) tv).setText(str);
        } else if (tv instanceof MyOptionItemView) {
            ((MyOptionItemView) tv).setRightText(str);
        }
    }

    public String getText(View tv) {
        if (tv instanceof TextView) {
            if (TextUtils.isEmpty(((TextView) tv).getText())) {
                return "";
            } else {
                return ((TextView) tv).getText().toString();

            }
        } else if (tv instanceof MyOptionItemView) {
            if (TextUtils.isEmpty(((MyOptionItemView) tv).getRightText())) {
                return "";
            }
            return ((MyOptionItemView) tv).getRightText();
        }
        return "";

    }

    public void setBackgroAndTextAndVis(TextView text, String str, int drawId) {
        text.setText(str);
        text.setTextColor(Color.RED);
        text.setVisibility(View.VISIBLE);
        text.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.red_btn_selector));
    }


    /**
     * 构造一个bean 用于上传参数  新建 地址
     *
     * @return
     */
    public AddressBean constructionBean() {

        addressBean.name = this.getText(getView(R.id.et_aaa_name));
        addressBean.contactName = this.getText(getView(R.id.et_aaa_contactName));
        addressBean.contactPhone = this.getText(getView(R.id.et_aaa_contactPhone));
        addressBean.detailAddress = this.getText(getView(R.id.et_aaa_detailAddress));

        addressBean.nurseryContactName = this.getText(getView(R.id.et_aaa_nurseryContactName));
        addressBean.nurseryContactPhone = this.getText(getView(R.id.et_aaa_nurseryContactPhone));

        addressBean.isDefault = this.isDefault();//是否 默认

//      addressBean.area    地址通过   接口返回
//      addressBean.street  街道通过   接口返回
//      addressBean.longitude  latitude  经度 纬度    接口返回

        return addressBean;
    }

    private boolean isDefault() {
//        CheckBox checkBox = getView(R.id.checkBox3);
        return false;
    }


    /**
     * 内部类 用于提交 接口数据
     */
    public class MyPresenter extends BasePresenter {
        public void doSave(Serializable obj, AjaxCallBack callBack) {
            showLoading();
            putParams(obj);
            putParams("storeId", MyApplication.getUserBean().storeId);
            doRequest("admin/nursery/save", true, callBack);
        }

        public void getStreets(String cityCode) {
//            141121
            if (TextUtils.isEmpty(cityCode)) {
                ToastUtil.showShortToast("请选择地区");
                return;
            }
            if (cityCode.length() > 6) {
                cityCode = cityCode.substring(0, 6);
            }
            showLoading();
            AjaxCallBack callBack = new AjaxCallBack<String>() {
                @Override
                public void onSuccess(String json) {
                    CityGsonBean gsonBean = GsonUtil.formateJson2Bean(json, CityGsonBean.class);
                    if (gsonBean.code.equals(ConstantState.SUCCEED_CODE)) {

                        gsonBean.data.cityList.toString();
                        D.e("cityList=\n" + gsonBean.data.cityList.toString());

                        if (gsonBean.data != null && gsonBean.data.cityList != null) {
                            OnlyDirstreetWheelDialogF.instance(new OnlyDirstreetWheelDialogF.OnDirSelectListener() {
                                @Override
                                public void onDirSelect(CityGsonBean.ChildBeans childBeans) {
//                                    setText(getView(R.id.tv_aaa_street), childBeans.name);
                                    addressBean.cityCode = childBeans.cityCode;
                                    //{id='16877', name='集美街道', fullName='福建省厦门市集美区集美街道', cityCode='350211001', parentCode='350211', level='4', childs=null}, ChildBeans{id='16878', name='侨英街道',

                                }
                            }, gsonBean.data.cityList).show(getSupportFragmentManager(), TAG);
                        }

                    } else {
                        ToastUtil.showShortToast(gsonBean.msg);
                    }
                    hindLoading();
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    ToastUtil.showShortToast(getString(R.string.error_net));
                    hindLoading();
                }
            };

            //cityCode
            putParams("parentCode", cityCode);
            doRequest("city/getChilds", false, callBack);
        }
    }


    private void DelAdress(String id) {
        // TODO Auto-generated method stub
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("id", id);
        finalHttp.post(GetServerUrl.getUrl() + "admin/nursery/delete", params,
                new AjaxCallBack<Object>() {

                    @Override
                    public void onSuccess(Object t) {
                        // TODO Auto-generated method stub
                        try {
                            JSONObject jsonObject = new JSONObject(t.toString());
                            String code = JsonGetInfo.getJsonString(jsonObject, "code");
                            String msg = JsonGetInfo.getJsonString(jsonObject,
                                    "msg");

                            if ("1".equals(code)) {
                                setResult(ConstantState.DELETE_SUCCEED);
                                finish();
                            } else {
                                ToastUtil.showShortToast(msg);
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        // TODO Auto-generated method stub
                        Toast.makeText(AddAdressActivity.this,
                                R.string.error_net, Toast.LENGTH_SHORT).show();
                        super.onFailure(t, errorNo, strMsg);
                    }

                });

    }
}

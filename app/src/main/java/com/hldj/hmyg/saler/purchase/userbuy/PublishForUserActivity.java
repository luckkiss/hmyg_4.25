package com.hldj.hmyg.saler.purchase.userbuy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.MainActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.bean.SeedlingType;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.TypeListBean;
import com.hldj.hmyg.bean.UnitTypeBean;
import com.hldj.hmyg.buyer.Ui.ComonCityWheelDialogF;
import com.hldj.hmyg.saler.M.enums.ValidityEnum;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.bean.UserPurchase;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.AutoAddRelative;
import com.hy.utils.ToastUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.zzy.common.widget.wheelview.popwin.CustomDaysPickPopwin;
import com.zzy.common.widget.wheelview.popwin.CustomUnitsPickPopwin;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户发布界面
 */
@SuppressLint({"NewApi", "ResourceAsColor"})
public class PublishForUserActivity extends BaseMVPActivity implements OnClickListener {


    @ViewInject(id = R.id.qxz_pz)
    TextView qxz_pz;
    @ViewInject(id = R.id.save_bottom)
    Button save_bottom;


    @ViewInject(id = R.id.ll_auto_add_layout)
    LinearLayout ll_auto_add_layout;//自动显示的头部

    public SimpleGsonBean mGsonBean;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_publish_for_user;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);
        qxz_pz.setOnClickListener(v -> 请选择品种());

        initRequest(getId());


    }

    private void 请选择品种() {
        SelectPlantActivity.start2Activity(mActivity, qxz_pz.getText().equals("请选择") ? "" : qxz_pz.getText().toString());
    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start2Activity(Activity mActivity) {
        Intent intent = new Intent(mActivity, PublishForUserActivity.class);
        mActivity.startActivityForResult(intent, 100);
    }

    public String getId() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            return bundle.getString("id");
        } else {
            return "";
        }
    }

    public static void start2Activity(Activity mActivity, String purchaseId) {
        Intent intent = new Intent(mActivity, PublishForUserActivity.class);
        intent.putExtra("id", purchaseId);
        mActivity.startActivityForResult(intent, 100);
    }

    @Override
    public String setTitle() {
        return "发布求购";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {
            SeedlingType seedlingType = (SeedlingType) data.getSerializableExtra("item");
            qxz_pz.setText(seedlingType.name);

            try {
                initAutoAddView(mGsonBean, seedlingType, null);
            } catch (Exception e) {
                ToastUtil.showLongToast("初始化失败");
                CrashReport.postCatchedException(e);
                e.printStackTrace();
            }


        }
    }


    private static final String TAG = "PublishForUserActivity";

    public void initRequest(String purchaseId) {
        String host;
        if (TextUtils.isEmpty(purchaseId)) {
            host = "admin/userPurchase/initPublish";
        } else {
            host = "admin/userPurchase/edit";
        }
        new BasePresenter()
                .putParams("id", purchaseId)
                .doRequest(host, new HandlerAjaxCallBack() {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {

                        mGsonBean = gsonBean;

                        Log.i(TAG, "onRealSuccess: " + gsonBean.isSucceed());
                        显示单位(gsonBean.getData().unitTypeList);

                        设置求购期限(gsonBean.getData().validityList, getView(R.id.select_time));

                        设置用苗地(getView(R.id.select_city));

                        设置是否需要添加图片(getView(R.id.radio_left), getView(R.id.radio_right));


                        if (gsonBean.getData().userPurchase != null) {
                            还原(gsonBean.getData().userPurchase);
                            SeedlingType seedlingType = new SeedlingType();
                            seedlingType.id = gsonBean.getData().userPurchase.secondSeedlingTypeId;
                            seedlingType.parentId = gsonBean.getData().userPurchase.firstSeedlingTypeId;
                            seedlingType.parentName =
                                    getParentNameByFirstId(gsonBean.getData().userPurchase.firstSeedlingTypeId, gsonBean.getData().typeList);
//                            new ty userPurchase.secondSeedlingTypeId = seedlingType.id;
//                            userPurchase.firstSeedlingTypeId = seedlingType.parentId;

                            initAutoAddView(gsonBean, seedlingType, gsonBean.getData().userPurchase);

                            还原动态布局数据(gsonBean.getData().userPurchase);
                        }


                    }
                });
    }

    private String getParentNameByFirstId(String firstSeedlingTypeId, List<TypeListBean> typeList) {

        if (typeList == null || typeList.size() == 0) {
            return "";
        }
        for (TypeListBean typeListBean : typeList) {
            if (typeListBean.id.equals(firstSeedlingTypeId)) {
                return typeListBean.name;
            }
        }


        return "";
    }

    private void 设置是否需要添加图片(RadioButton radioButton, RadioButton radioButton1) {
        radioButton.setChecked(true);
    }

    private void 设置用苗地(TextView select_city) {
        select_city.setOnClickListener(v -> {
            ComonCityWheelDialogF.instance().addSelectListener(new ComonCityWheelDialogF.OnCitySelectListener() {
                @Override
                public void onCitySelect(String province, String city, String distrect, String cityCode) {
                    select_city.setText(province + "  " + city);
                    select_city.setTag(cityCode.substring(0, 4));
                }
            }).show(getSupportFragmentManager(), "设置用苗地");
        });

        // 自动定位
        select_city.setText("未选择");
        select_city.setTag("");

        if (MainActivity.aMapLocation != null) {
            if (!TextUtils.isEmpty(MainActivity.cityCode)) {
                select_city.setText(MainActivity.province_loc + " " + MainActivity.city_loc);
                select_city.setTag(MainActivity.cityCode);

//                cityBeans = new CityGsonBean.ChildBeans();
//                cityBeans.cityCode = MainActivity.cityCode;
//                city.setRightText(MainActivity.province_loc + " " + MainActivity.city_loc);
            }
        }


    }

    private void 设置求购期限(List<UnitTypeBean> validityList, TextView select_time) {
        select_time.setOnClickListener(v -> {
            //有效期
            CustomDaysPickPopwin.DayChangeListener dayChangeListener = new CustomDaysPickPopwin.DayChangeListener() {
                @Override
                public void onDayChange(int dayType, int pos) {
                    Log.i(TAG, "onDayChange: dayType= " + dayType);
                    Log.i(TAG, "onDayChange: pos= " + pos);
                    select_time.setText(validityList.get(pos).text);
                    select_time.setTag(validityList.get(pos).value);
                }
            };
            /* "text": "1天","value": "day1"  */
            String[] string = new String[validityList.size()];
            for (int i = 0; i < validityList.size(); i++) {
                string[i] = validityList.get(i).text;
            }

//            String[] ars = (String[]) validityList.toArray();

            CustomDaysPickPopwin daysPopwin = new CustomDaysPickPopwin(mActivity, dayChangeListener, string, string, 0);
            daysPopwin.hidenCustomBtn();
            daysPopwin.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM
                    | Gravity.CENTER, 0, 0);
        });

        select_time.setText(validityList.get(2).text);
        select_time.setTag(validityList.get(2).value);

    }


    private void 显示单位(List<UnitTypeBean> unitTypeList) {

        //单位点击时间
        getView(R.id.select_unit).setOnClickListener(v -> {
            CustomUnitsPickPopwin pickPopwin = new CustomUnitsPickPopwin(mActivity, unitTypeBean -> {
                D.e("======unitTypeBean=====" + unitTypeBean.text);
                D.e("======unitTypeBean=====" + unitTypeBean.value);

                setText(getView(R.id.select_unit), unitTypeBean.text);
                setText(getView(R.id.select_unit), unitTypeBean.text);
                getView(R.id.select_unit).setTag(unitTypeBean.value);
//              holder.tv_save_seeding_unit.setTag(unitTypeBean.value);
//              upLoadDatas.setUnit(unitTypeBean);

            }, unitTypeList);

            pickPopwin.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM
                    | Gravity.CENTER, 0, 0);


        });

        setText(getView(R.id.select_unit), unitTypeList.get(0).text);
        getView(R.id.select_unit).setTag(unitTypeList.get(0).value);


    }


    private AutoAddRelative.ViewHolder_rd viewHolder_rd;
    private AutoAddRelative autoAddRelative_rd;
    ArrayList<AutoAddRelative> arrayList_holders = new ArrayList();//共同的 holder 集合


    private void resetAutoView() {
        viewHolder_rd = null;
        autoAddRelative_rd = null;
        arrayList_holders.clear();
        ll_auto_add_layout.removeAllViews();
    }


    public void 还原动态布局数据(UserPurchase userPurchase) {
        if (viewHolder_rd != null) {
            //根据种类选择 0.3  1.0  1.3  哪个被选中
            if (autoAddRelative_rd.getMTag().equals("dbh")) {
                viewHolder_rd.et_auto_add_min.setText(userPurchase.minDbh);
                viewHolder_rd.et_auto_add_max.setText(userPurchase.maxDbh);
                autoAddRelative_rd.setDiameterTypeWithSize(userPurchase.dbhType);
            } else {
                viewHolder_rd.et_auto_add_min.setText(userPurchase.minDiameter);
                viewHolder_rd.et_auto_add_max.setText((userPurchase.maxDiameter));
                autoAddRelative_rd.setDiameterTypeWithSize(userPurchase.diameterType);
            }
        }
        for (int i = 0; i < arrayList_holders.size(); i++) {
            if (arrayList_holders.get(i).getTag().equals("高度")) {
                //有高度 参数
                setMinByTag("高度", userPurchase.minHeight);
                setMaxByTag("高度", userPurchase.maxHeight);
            }
            if (arrayList_holders.get(i).getTag().equals("冠幅")) {
                setMinByTag("冠幅", userPurchase.minCrown);
                setMaxByTag("冠幅", userPurchase.maxCrown);
            }
            if (arrayList_holders.get(i).getTag().equals("脱杆高")) {
                setMinByTag("脱杆高", userPurchase.minOffbarHeight);
                setMaxByTag("脱杆高", userPurchase.maxOffbarHeight);
            }
            if (arrayList_holders.get(i).getTag().equals("长度")) {
                setMinByTag("长度", userPurchase.minLength);
                setMaxByTag("长度", userPurchase.maxLength);
            }
        }
    }

    public void setMinByTag(String tag, String min) {
        for (int i = 0; i < arrayList_holders.size(); i++) {
            if (arrayList_holders.get(i).getTag().equals(tag)) {
                //有高度 参数
                arrayList_holders.get(i).getViewHolder().et_auto_add_min.setText(min);
            }
        }
    }

    public void setMaxByTag(String tag, String min) {
        for (int i = 0; i < arrayList_holders.size(); i++) {
            if (arrayList_holders.get(i).getTag().equals(tag)) {
                //有高度 参数
                arrayList_holders.get(i).getViewHolder().et_auto_add_max.setText(min);
            }
        }
    }


    //     选择完 品种之后  自动添加 头部 显示
    private void initAutoAddView(SimpleGsonBean mGsonBean, SeedlingType parentName, UserPurchase userPurchase) {

        resetAutoView();

        save_bottom.setOnClickListener(v -> {
            发布(parentName);
        });

//        AutoAddRelative autoAddRelative_top = new AutoAddRelative(this)
//                .initView(R.layout.save_seeding_auto_add);
//        AutoAddRelative autoAddRelative_rd = new AutoAddRelative(this);

        List<SaveSeedingGsonBean.DataBean.TypeListBean.ParamsListBean> paramsListBean = null;
        for (int i = 0; i < mGsonBean.getData().typeList.size(); i++) {
            if (mGsonBean.getData().typeList.get(i).name.equals(parentName.parentName)) {
                paramsListBean = mGsonBean.getData().typeList.get(i).paramsList;
            }
        }


        if (mGsonBean.getData().typeList != null && paramsListBean != null) {

            for (int i = 0; i < paramsListBean.size(); i++) {

                String name = paramsListBean.get(i).getName();
                if (null == name) {
                    return;
                }
                if (name.equals("地径") || name.equals("米径") || name.equals("胸径")) {//第一个添加带有radio button 选项的   地被不添加
                    autoAddRelative_rd = new AutoAddRelative(this)
                            .initView(R.layout.save_seeding_auto_add_radio)
                            .setDatas_rd(paramsListBean.get(i), mGsonBean.getData().dbhTypeList, mGsonBean.getData().diameterTypeList)

                    ;


                    autoAddRelative_rd.setSizeWithTag(paramsListBean.get(i).getValue());
                    autoAddRelative_rd.setTag(paramsListBean.get(i).getValue());
                    ll_auto_add_layout.addView(autoAddRelative_rd);
                    viewHolder_rd = autoAddRelative_rd.getViewHolder_rd();

//                    arrayList_holders.add(autoAddRelative_rd);

                } else {

                    AutoAddRelative autoAddRelative = new AutoAddRelative(this).initView(R.layout.save_seeding_auto_add);
                    autoAddRelative.setTag(name);
                    autoAddRelative.setDatas(paramsListBean.get(i));
                    ll_auto_add_layout.addView(autoAddRelative);
                    arrayList_holders.add(autoAddRelative);

                }

            }

        }
//        ll_auto_add_layout.addView(autoAddRelative_top);
//        ll_auto_add_layout.addView(autoAddRelative_rd);
//        ll_auto_add_layout.addView(autoAddRelative_bottom);


    }

    private SaveSeedingGsonBean.DataBean.TypeListBean.ParamsListBean getParamarsListBean(String parentName) {
        mGsonBean.getData().typeList.get(0).paramsList.get(0);
        List<TypeListBean> typeList = mGsonBean.getData().typeList;
        for (int i = 0; i < typeList.size(); i++) {
            if (typeList.get(i).name.equals(parentName)) {
                List<SaveSeedingGsonBean.DataBean.TypeListBean.ParamsListBean> paramsListBeans = typeList.get(i).paramsList;
                return paramsListBeans.get(i);
            }
        }


        return null;

    }


    private void 还原(UserPurchase userPurchase) {


        setText(getView(R.id.qxz_pz), userPurchase.name);

//        setText(getView(R.id.qxz_pz), userPurchase.secondSeedlingTypeId);

        setText(getView(R.id.qiu_gou_num), userPurchase.count);

        setText(getView(R.id.select_unit), userPurchase.unitTypeName);
        getView(R.id.select_unit).setTag(userPurchase.unitType);


        ValidityEnum enumItem = Enum.valueOf(ValidityEnum.class, userPurchase.validity);
        setText(getView(R.id.select_time), enumItem.getDays() + "");
        getView(R.id.select_time).setTag(userPurchase.validity);


        setText(getView(R.id.select_city), userPurchase.cityName);
        getView(R.id.select_city).setTag(userPurchase.cityCode);


        RadioButton radioButton = getView(R.id.radio_right);
        radioButton.setChecked(userPurchase.needImage);

        setText(getView(R.id.et_remark), userPurchase.remarks);


    }


    private void 发布(SeedlingType seedlingType) {

        UserPurchase userPurchase = new UserPurchase();
        Log.i(TAG, "品种名称---->" + getText(getView(R.id.qxz_pz)));

        userPurchase.id = getId();
        userPurchase.name = getText(getView(R.id.qxz_pz));
        userPurchase.secondSeedlingTypeId = seedlingType.id;
        userPurchase.firstSeedlingTypeId = seedlingType.parentId;

        Log.i(TAG, "高度min  " + getMinByTag("高度") + " 高度max  " + getMaxByTag("高度"));
        userPurchase.minHeight = getMinByTag("高度");
        userPurchase.maxHeight = getMaxByTag("高度");
        Log.i(TAG, "冠幅min  " + getMinByTag("冠幅") + " 高冠幅max  " + getMaxByTag("冠幅"));
        userPurchase.minCrown = getMinByTag("冠幅");
        userPurchase.maxCrown = getMaxByTag("冠幅");
        Log.i(TAG, "脱杆高min  " + getMinByTag("脱杆高") + " 脱杆高max  " + getMaxByTag("脱杆高"));
        userPurchase.minOffbarHeight = getMinByTag("脱杆高");
        userPurchase.maxOffbarHeight = getMaxByTag("脱杆高");
        Log.i(TAG, "长度min  " + getMinByTag("长度") + " 长度max  " + getMaxByTag("长度"));
        userPurchase.minLength = getMinByTag("长度");
        userPurchase.maxLength = getMaxByTag("长度");
        Log.i(TAG, "杆径min  " + getMinByTag("杆径") + " 杆径max  " + getMaxByTag("杆径"));
        userPurchase.minRod = getMinByTag("杆径");
        userPurchase.maxRod = getMaxByTag("杆径");

        if (viewHolder_rd != null) {
            if (autoAddRelative_rd.getTag().equals("dbh")) {
                Log.i(TAG, "最小 dbh " + viewHolder_rd.et_auto_add_min.getText());
                Log.i(TAG, "最da dbh " + viewHolder_rd.et_auto_add_max.getText());
                userPurchase.minDbh = viewHolder_rd.et_auto_add_min.getText().toString();
                userPurchase.maxDbh = viewHolder_rd.et_auto_add_max.getText().toString();
                userPurchase.dbhType = autoAddRelative_rd.getDiameterType();
            } else {
                Log.i(TAG, "最小 dimater" + viewHolder_rd.et_auto_add_min.getText());
                Log.i(TAG, "最da dimater" + viewHolder_rd.et_auto_add_max.getText());
                userPurchase.minDiameter = viewHolder_rd.et_auto_add_min.getText().toString();
                userPurchase.maxDiameter = viewHolder_rd.et_auto_add_max.getText().toString();
                userPurchase.dbhType = autoAddRelative_rd.getDiameterType();
            }

            Log.i(TAG, "xx size  " + autoAddRelative_rd.getDiameterType());


        }


        Log.i(TAG, "求购数量---->" + getText(getView(R.id.qiu_gou_num)));
        userPurchase.count = getText(getView(R.id.qiu_gou_num));

        Log.i(TAG, "单位---->" + getText(getView(R.id.select_unit)) + "  " + getView(R.id.select_unit).getTag());
        userPurchase.unitType = getView(R.id.select_unit).getTag().toString();

        Log.i(TAG, "求购期限---->" + getText(getView(R.id.select_time)) + "  " + getView(R.id.select_time).getTag());
        userPurchase.validity = getView(R.id.select_time).getTag().toString();

        Log.i(TAG, "用苗地---->" + getText(getView(R.id.select_city)) + "  " + getView(R.id.select_city).getTag());
        userPurchase.cityCode = getView(R.id.select_city).getTag().toString();

        RadioButton radioButton = getView(R.id.radio_left);
        Log.i(TAG, "图片比传嘛?---->" + radioButton.isChecked());
        userPurchase.needImage = radioButton.isChecked();

        Log.i(TAG, "求购描述---->" + getText(getView(R.id.et_remark)));
        userPurchase.remarks = getText(getView(R.id.et_remark));

/**
 *    Log.i(TAG, "高度min  " + getMinByTag("高度") + " 高度max  " + getMaxByTag("高度"));
 Log.i(TAG, "冠幅min  " + getMinByTag("冠幅") + " 高冠幅max  " + getMaxByTag("冠幅"));
 Log.i(TAG, "脱杆高min  " + getMinByTag("脱杆高") + " 脱杆高max  " + getMaxByTag("脱杆高"));
 Log.i(TAG, "长度min  " + getMinByTag("长度") + " 长度max  " + getMaxByTag("长度"));
 */
        new BasePresenter()
                .putParams(userPurchase)
                .doRequest("admin/userPurchase/save", new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        ToastUtil.showLongToast(gsonBean.msg);
                        setResult(ConstantState.PUBLIC_SUCCEED);
                        finish();
                    }
                })
        ;


    }


    /**
     * if (autoAddRelative_rd != null) {
     * if (autoAddRelative_rd.getMTag().equals("dbh")) {
     * seedlingBean.setMaxDbh(MyUtil.formateString2Int(viewHolder_rd.et_auto_add_max.getText().toString()));
     * seedlingBean.setMinDbh(MyUtil.formateString2Int(viewHolder_rd.et_auto_add_min.getText().toString()));
     * seedlingBean.setDbhType(autoAddRelative_rd.getDiameterType());
     * } else {
     * seedlingBean.setMaxDiameter(MyUtil.formateString2Int(viewHolder_rd.et_auto_add_max.getText().toString()));
     * seedlingBean.setMinDiameter(MyUtil.formateString2Int(viewHolder_rd.et_auto_add_min.getText().toString()));
     * seedlingBean.setDiameterType(autoAddRelative_rd.getDiameterType());
     * }
     * }
     *
     * @param tag
     * @return
     */


    public String getMinByTag(String tag) {
        String min = "";
        for (int i = 0; i < arrayList_holders.size(); i++) {
            if (arrayList_holders.get(i).getTag().equals(tag)) {
                //有高度 参数
                min = arrayList_holders.get(i).getViewHolder().et_auto_add_min.getText().toString();
            }
        }
        return min;
    }

    public String getMaxByTag(String tag) {
        String max = "";
        for (int i = 0; i < arrayList_holders.size(); i++) {
            if (arrayList_holders.get(i).getTag().equals(tag)) {
                max = arrayList_holders.get(i).getViewHolder().et_auto_add_max.getText().toString();
            }
        }
        return max;
    }


}

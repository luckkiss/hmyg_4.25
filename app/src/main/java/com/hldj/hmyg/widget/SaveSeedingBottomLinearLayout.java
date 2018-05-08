package com.hldj.hmyg.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hldj.hmyg.M.AddressBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.UnitTypeBean;
import com.hldj.hmyg.saler.AdressActivity;
import com.hldj.hmyg.saler.AdressManagerActivity;
import com.hldj.hmyg.saler.M.StorageSave;
import com.hldj.hmyg.util.D;
import com.zf.iosdialog.widget.ActionSheetDialog_new;
import com.zzy.common.widget.wheelview.popwin.CustomDaysPickPopwin;
import com.zzy.common.widget.wheelview.popwin.CustomUnitsPickPopwin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/15.
 */

public class SaveSeedingBottomLinearLayout extends LinearLayout {


    public static AddressBean addressBean;

    public static AdressActivity.Address convertAddress(AddressBean bean) {
        AdressActivity.Address address = new AdressActivity.Address();
        address.addressId = bean.id;
        address.id = bean.id;
        address.cityName = bean.cityName;
        address.contactName = bean.contactName;
        address.fullAddress = bean.fullAddress;
        address.contactPhone = bean.contactPhone;
        address.name = bean.name;
        address.isDefault = bean.isDefault;
        return address;
    }


    Context context;

    public SaveSeedingBottomLinearLayout(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }


    public SaveSeedingBottomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public SaveSeedingBottomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SaveSeedingBottomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initView(context);
    }


    private void initView(Context context) {

        View rootVoiew = LayoutInflater.from(context).inflate(R.layout.include_seeding_bottom, this);

        holder = new ViewHolder(rootVoiew);

    }

    public void setDatas(StorageSave fromJson) {
        D.e("hellow world");
        holder.et_remark.setText(fromJson.getRemarks());
    }

    public ViewHolder getHolder() {

        return holder;
    }


    static ViewHolder holder;

    private SaveSeedingBottomLinearLayout.upLoadDatas upLoadDatas = new SaveSeedingBottomLinearLayout.upLoadDatas();

    //获取上传的数据
    public SaveSeedingBottomLinearLayout.upLoadDatas getUpLoadDatas() {

        if (upLoadDatas == null) {
            upLoadDatas = new SaveSeedingBottomLinearLayout.upLoadDatas();
        }
        return upLoadDatas;
    }

    //重新赋值----修改数据 重新上传时使用
    public void setUpLoadDatas(SaveSeedingBottomLinearLayout.upLoadDatas upLoadDatas) {
        this.upLoadDatas = upLoadDatas;
        holder.cb_is_meet.setChecked(upLoadDatas.isMeet);
        holder.et_remark.setText(upLoadDatas.remark);
        holder.tv_save_seeding_unit.setText(upLoadDatas.getUnit().text);
        initAddressView(holder.rootView, upLoadDatas.address);


        if (!upLoadDatas.isMeet) {
            if (!upLoadDatas.price_max.equals("0"))
                holder.tv_save_seeding_price_max.setText(upLoadDatas.price_max);

            if (!upLoadDatas.price_min.equals("0"))
                holder.tv_save_seeding_price_min.setText(upLoadDatas.price_min);//最小价格

        }

        if (!upLoadDatas.repertory_num.equals("0")) {
            holder.et_repertory_num.setText(upLoadDatas.repertory_num);//库存数量
        }

        holder.tv_save_seeding_useful.setText(upLoadDatas.validity);//有效期

    }


    ActionSheetDialog_new dialog = null;


    public class ViewHolder {
        public View rootView;
        public EditText tv_save_seeding_price_min;
        public EditText tv_save_seeding_price_max;
        public CheckBox cb_is_meet;
        public EditText et_repertory_num;
        public RelativeLayout rl_save_seeding_unit;
        public RelativeLayout rl_save_seeding_home;
        public RelativeLayout rl_save_seeding_useful;
        public EditText et_remark;
        public TextView tv_save_seeding_unit;
        public TextView tv_save_seeding_useful;
        public TextView tv_save_seeding_home;//废弃
        public LinearLayout list_item_adress;//地址

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_save_seeding_price_min = (EditText) rootView.findViewById(R.id.tv_save_seeding_price_min);
            this.tv_save_seeding_price_max = (EditText) rootView.findViewById(R.id.tv_save_seeding_price_max);
            this.cb_is_meet = (CheckBox) rootView.findViewById(R.id.cb_is_meet);
            this.et_repertory_num = (EditText) rootView.findViewById(R.id.et_repertory_num);

            this.rl_save_seeding_unit = (RelativeLayout) rootView.findViewById(R.id.rl_save_seeding_unit);//单位 点击区域
            this.tv_save_seeding_unit = (TextView) rootView.findViewById(R.id.tv_save_seeding_unit);//单位 显示

            this.rl_save_seeding_useful = (RelativeLayout) rootView.findViewById(R.id.rl_save_seeding_useful);//有效期  点击
            this.tv_save_seeding_useful = (TextView) rootView.findViewById(R.id.tv_save_seeding_useful);//有效期  显示

            this.rl_save_seeding_home = (RelativeLayout) rootView.findViewById(R.id.rl_save_seeding_home);//苗源地  点击区域
            this.tv_save_seeding_home = (TextView) rootView.findViewById(R.id.tv_save_seeding_home);//苗源地  显示（废弃）
            this.list_item_adress = (LinearLayout) rootView.findViewById(R.id.list_item_adress);//苗源地显示

            this.et_remark = (EditText) rootView.findViewById(R.id.et_remark);


            this.cb_is_meet.setOnClickListener(v ->
                    {

                        if (holder.cb_is_meet.isChecked()) {
                            holder.tv_save_seeding_price_max.setText("");
                            holder.tv_save_seeding_price_max.setFocusable(false);
                            holder.tv_save_seeding_price_min.setFocusable(false);
                            holder.tv_save_seeding_price_min.setText("");
                        } else {
                            D.e("=======");
                            holder.tv_save_seeding_price_min.setFocusable(true);
                            holder.tv_save_seeding_price_min.setFocusableInTouchMode(true);
                            holder.tv_save_seeding_price_min.requestFocus();
                            holder.tv_save_seeding_price_min.requestFocusFromTouch();
                            holder.tv_save_seeding_price_max.setFocusable(true);
                            holder.tv_save_seeding_price_max.setFocusableInTouchMode(true);
                            holder.tv_save_seeding_price_max.requestFocus();
                            holder.tv_save_seeding_price_max.requestFocusFromTouch();
                        }


                    }
            );

            upLoadDatas = new upLoadDatas();

            {


                if (addressBean == null) {

                    OnClickListener onClickListener = v -> {
                        D.e("=========苗原地点击===========");
                        AdressActivity.start2AdressListActivity(context, upLoadDatas.address.addressId, "SaveSeedlingActivity", address -> {
                            D.e("===========返回的地址==========" + address);
                            initAddressView(rootView, address);
                            upLoadDatas.address = address;
                        });
                    };


                    this.rl_save_seeding_home.setOnClickListener(onClickListener);
                    this.list_item_adress.setOnClickListener(onClickListener);

                } else {
                    initAddressView(rootView, convertAddress(addressBean));
                    upLoadDatas.address = convertAddress(addressBean);
                }


            }

            //单位点击时间
            this.rl_save_seeding_unit.setOnClickListener(v -> {
                CustomUnitsPickPopwin pickPopwin = new CustomUnitsPickPopwin(context, unitTypeBean -> {
                    D.e("======unitTypeBean=====" + unitTypeBean.text);
                    D.e("======unitTypeBean=====" + unitTypeBean.value);
                    holder.tv_save_seeding_unit.setText(unitTypeBean.text);
                    holder.tv_save_seeding_unit.setTag(unitTypeBean.value);
                    upLoadDatas.setUnit(unitTypeBean);

                }, getUnitTypeList());

                pickPopwin.showAtLocation(holder.rootView, Gravity.BOTTOM
                        | Gravity.CENTER, 0, 0);


            });


            //有效期
            this.rl_save_seeding_useful.setOnClickListener(v -> {

                CustomDaysPickPopwin daysPopwin = new CustomDaysPickPopwin(
                        context, (dayType, pos) -> {
                    if (dayType == 0) {
                        holder.tv_save_seeding_useful.setText(Short.valueOf(days[pos]) + "");
//                        upLoadDatas.validity = days[pos];
                    } else {
                        holder.tv_save_seeding_useful.setText(Short.valueOf(customDays[pos]) + "");
//                        upLoadDatas.validity = customDays[pos];
                    }
                }, days, customDays, 0);
                daysPopwin.showAtLocation(holder.rootView, Gravity.BOTTOM
                        | Gravity.CENTER, 0, 0);

            });

        }

    }

    /**
     * 初始化地址栏
     *
     * @param rootView 根布局
     * @param address  地址对象
     */
    private void initAddressView(View rootView, AdressActivity.Address address) {

        upLoadDatas.address = address;
        if (address.isDefault) {
            rootView.findViewById(R.id.tv_is_defoloat).setVisibility(View.VISIBLE);//默认就显示默认地址
            ((TextView) rootView.findViewById(R.id.tv_address_name)).setText(address.fullAddress); //地址名称
        } else {
            rootView.findViewById(R.id.tv_is_defoloat).setVisibility(View.GONE);//默认就显示默认地址
            ((TextView) rootView.findViewById(R.id.tv_address_name)).setText(address.fullAddress); //地址名称
        }

        ((TextView) rootView.findViewById(R.id.tv_name)).setText("苗圃名称：" + AdressManagerActivity.striFil((Activity) getContext(), address.name, ""));// 苗圃名称
        ((TextView) rootView.findViewById(R.id.tv_con_name_phone)).setText("联  系  人：" + AdressManagerActivity.striFil((Activity) getContext(), address.contactName, address.contactPhone));// 名字
        ((TextView) rootView.findViewById(R.id.tv_con_name_phone)).setText("联  系  人：" + AdressManagerActivity.striFil((Activity) getContext(), address.contactName, address.contactPhone));// 名字


        if (!TextUtils.isEmpty(address.addressId)) {
            // 隐藏   苗源地址按钮
//            holder.rl_save_seeding_home.setVisibility(GONE);

            rootView.findViewById(R.id.rl_save_seeding_home).setVisibility(GONE);
            rootView.findViewById(R.id.list_item_adress).setVisibility(VISIBLE);
//            holder.list_item_adress.setVisibility(VISIBLE);
        } else {
            // 隐藏
//            holder.rl_save_seeding_home.setVisibility(VISIBLE);
//            holder.list_item_adress.setVisibility(GONE);
            rootView.findViewById(R.id.rl_save_seeding_home).setVisibility(VISIBLE);
            rootView.findViewById(R.id.list_item_adress).setVisibility(GONE);

        }


    }

    /**
     * 地址选择后回调接口
     */
    public interface onAddressSelectListener {
        void onAddressSelect(AdressActivity.Address address);
    }

    //用于上传的数据
    public class upLoadDatas {
        private UnitTypeBean unit = new UnitTypeBean("株", "plant");//单位
        public String validity = "";//有效期
        public String price_min = "";//最小价格
        public String price_max = "";//最大价格
        public Boolean isMeet = false; // 是否面议
        public String repertory_num = "";//库存
        public String remark = "";//备注
        public AdressActivity.Address address = new AdressActivity.Address();//苗源地

        public UnitTypeBean getUnit() {
            return unit;
        }

        public void setUnit(UnitTypeBean unit) {
            this.unit = unit;
        }

        public String getUnitName() {

            if (null == holder.tv_save_seeding_unit.getTag()) {
                return "plant";//默认为株
            }

            return holder.tv_save_seeding_unit.getText().toString();
        }

        /**
         * 获取 天数
         *
         * @return
         */
        public String getValidity() {
            String str = holder.tv_save_seeding_useful.getText().toString();

            return str;
        }

        public boolean isMeet() {
            return holder.cb_is_meet.isChecked();
        }

        public String getPrice_min() {
            return holder.tv_save_seeding_price_min.getText().toString();
        }

        public String getPrice_max() {
            return holder.tv_save_seeding_price_max.getText().toString();
        }

        public String getRepertory_num() {
            return holder.et_repertory_num.getText().toString();
        }

        public String checkNoNull(String str) {
            if (!TextUtils.isEmpty(str)) {
                return str;
            } else {
//                ToastUtil.showShortToast("不能为空");
                return "";
            }
        }


        public String getRemark() {
            return checkNoNull(holder.et_remark.getText().toString());
        }


    }

    private String[] days = {"30", "90", "180"};

    private String[] customDays = {"1", "2", "3", "4", "5", "6", "7", "8",
            "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",
            "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52",
            "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63",
            "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74",
            "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85",
            "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96",
            "97", "98", "99", "100", "101", "102", "103", "104", "105", "106",
            "107", "108", "109", "110", "111", "112", "113", "114", "115",
            "116", "117", "118", "119", "120", "121", "122", "123", "124",
            "125", "126", "127", "128", "129", "130", "131", "132", "133",
            "134", "135", "136", "137", "138", "139", "140", "141", "142",
            "143", "144", "145", "146", "147", "148", "149", "150", "151",
            "152", "153", "154", "155", "156", "157", "158", "159", "160",
            "161", "162", "163", "164", "165", "166", "167", "168", "169",
            "170", "171", "172", "173", "174", "175", "176", "177", "178",
            "179", "180"};


    private List<UnitTypeBean> unitTypeList = new ArrayList<>();

    public List<UnitTypeBean> getUnitTypeList() {
        return unitTypeList;
    }

    public void setUnitTypeDatas(List<UnitTypeBean> mUnitTypeList) {

        if (mUnitTypeList == null) {
            mUnitTypeList = new ArrayList<>();
        }
        this.unitTypeList = mUnitTypeList;
    }

    public void setDefaultAddr(AdressActivity.Address address) {


        if (addressBean!=null )
        {
            initAddressView(holder.rootView, convertAddress(addressBean));
        }else {
            initAddressView(holder.rootView, address);
        }

    }


}

package com.hldj.hmyg.buyer.Ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.CityGsonBean;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.PicSerializableMaplist;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.M.ImagesJsonBean;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.P.PurchaseDeatilP;
import com.hldj.hmyg.buyer.weidet.Purchase.PurchaseAutoAddLinearLayout;
import com.hldj.hmyg.presenter.SaveSeedlingPresenter;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.UpdataImageActivity_bak;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zzy.common.widget.MeasureGridView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

import static com.hldj.hmyg.R.id.tv_purchase_add_pic;

/**
 * 弹窗activity
 */

public class DialogActivitySecond extends PurchaseDetailActivityChange {

    private static final String TAG = "DialogActivitySecond";


    @ViewInject(id = R.id.commit)
    TextView commit;

    @ViewInject(id = R.id.price)
    EditText 价格;

    @ViewInject(id = R.id.et_purchase_remark)
    EditText 备注;

    @ViewInject(id = R.id.city)
    TextView city;


    @ViewInject(id = tv_purchase_add_pic)
    TextView 选择图片;

    ArrayList<Pic> listPicsOnline = new ArrayList<>();//上传成功的结果保存在这里 网路路径集合

    View.OnClickListener choosePic = v -> {
        Intent toUpdataImageActivity = new Intent(
                mActivity, UpdataImageActivity_bak.class);
        Bundle bundleObject = new Bundle();
        final PicSerializableMaplist myMap = new PicSerializableMaplist();
        myMap.setMaplist(listPicsOnline);
        bundleObject.putSerializable("urlPaths", myMap);
        toUpdataImageActivity.putExtras(bundleObject);
        startActivityForResult(toUpdataImageActivity, 1);
    };

    public void city(View view) {
        CityWheelDialogF.instance()
                .isShowCity(true)
                .addSelectListener(new CityWheelDialogF.OnCitySelectListener() {
                    @Override
                    public void onCitySelect(CityGsonBean.ChildBeans childBeans) {
                        ToastUtil.showLongToast(childBeans.fullName);
                        cityBeans = childBeans;
                        city.setText(childBeans.fullName);
                    }

                    @Override
                    public void onProvinceSelect(CityGsonBean.ChildBeans childBeans) {

                    }
                })
                .show(getSupportFragmentManager(), TAG);
    }

    private CityGsonBean.ChildBeans cityBeans;


    public static void start2Activity(Activity activity, String tag, PurchaseItemBean_new purchaseItemBeanNew) {
        Intent intent = new Intent(activity, DialogActivitySecond.class);
        intent.putExtra("tag", tag);
        intent.putExtra(TAG, purchaseItemBeanNew);
        ToastUtil.showLongToast(purchaseItemBeanNew.toString());

        activity.startActivityForResult(intent, 100);
    }

    /**
     * PurchaseAutoAddLinearLayout.PlantBean plantBean = (PurchaseAutoAddLinearLayout.PlantBean) autoLayouts.get(i).getTag();
     * String str = autoLayouts.get(i).getViewHolder().et_params_03.getText().toString();
     * if (plantBean.value.equals(ConstantParams.price)) {
     * uploadBean.price = str;
     * } else if (plantBean.value.equals(ConstantParams.count)) {
     * uploadBean.count = str;
     * } else if (plantBean.value.equals(ConstantParams.dbh)) {
     * uploadBean.dbh = str;
     * uploadBean.dbhType = autoLayouts.get(i).getSelect_size();
     *
     * @return
     */

    private String 获取参数(String key) {
        for (PurchaseAutoAddLinearLayout autoLayout : autoLayouts) {
            PurchaseAutoAddLinearLayout.PlantBean plantBean = (PurchaseAutoAddLinearLayout.PlantBean) autoLayout.getTag();
            if (plantBean.value.equals(key)) {
                return autoLayout.getViewHolder().et_params_03.getText().toString();
            }
        }
        return "";
    }

    private String 获取价格(String key) {
        for (PurchaseAutoAddLinearLayout autoLayout : autoLayouts) {
            PurchaseAutoAddLinearLayout.PlantBean plantBean = (PurchaseAutoAddLinearLayout.PlantBean) autoLayout.getTag();
            if (plantBean.value.equals(key)) {
                return autoLayout.getViewHolder().et_params_03.getText().toString();
            }
        }
        return "";
    }

    private String 获取地址code() {
        if (cityBeans == null) {
            return "";
        } else {
            return cityBeans.cityCode;

        }
    }

    private String 获取地址全名() {
        if (cityBeans == null) {
            return "";
        } else {
            return cityBeans.fullName;

        }
    }

    @Override
    protected void initListener() {
        /*关闭*/
        getView(R.id.close_title).setOnClickListener(v -> finish());
        commit.setOnClickListener(v -> {


            报价吧();

            Log.i(TAG, "价格: " + 获取参数(ConstantParams.price));
            Log.i(TAG, "到岸价: " + 获取参数(ConstantParams.prePrice));
            Log.i(TAG, "数量: " + 获取参数(ConstantParams.count));
            Log.i(TAG, "高度: " + 获取参数(ConstantParams.height));
            Log.i(TAG, "冠幅: " + 获取参数(ConstantParams.crown));
            Log.i(TAG, "直购。无效字段: " + 获取参数(ConstantParams.direct));
            Log.i(TAG, "备注: " + 备注.getText().toString());
            Log.i(TAG, "苗源地: " + 获取地址全名() + 获取地址code());
            Log.i(TAG, "种植类型: " + plantType);
            Log.i(TAG, "图片: " + GsonUtil.Bean2Json(listPicsOnline));


        });

        选择图片.setOnClickListener(choosePic);
        city.setOnClickListener(v -> {
            city(v);
        });

    }

    private void 报价吧() {
        new BasePresenter()
                .putParams(ConstantParams.id, getPurchaseType())
                .putParams(ConstantParams.price, 获取参数(ConstantParams.price))
                .putParams(ConstantParams.prePrice, 获取参数(ConstantParams.prePrice))
                .putParams(ConstantParams.count, 获取参数(ConstantParams.count))
                .putParams(ConstantParams.height, 获取参数(ConstantParams.height))
                .putParams(ConstantParams.crown, 获取参数(ConstantParams.crown))
                .putParams(ConstantParams.remarks, 备注.getText().toString())
                .putParams(ConstantParams.cityCode, 获取地址code())
                .putParams(ConstantParams.imagesData, GsonUtil.Bean2Json(listPicsOnline))
                .putParams(ConstantParams.purchaseItemId, getPurchaseType())
                .putParams(ConstantParams.purchaseId, getData().pid2)
                .doRequest("admin/quote/save", true, new HandlerAjaxCallBack(mActivity) {
            @Override
            public void onRealSuccess(SimpleGsonBean gsonBean) {
                ToastUtil.showLongToast(gsonBean.msg);
            }
        });
    }

    @Override
    public String getPurchaseType() {
        return super.getPurchaseType();
    }


    @Override
    public int bindLayoutID() {
        return R.layout.activity_dialog_second;
    }

    @Override
    public void initGvTop(MeasureGridView gridView, List<ImagesJsonBean> imagesJson) {

    }

    @Override
    public void initView() {
        FinalActivity.initInjectedView(mActivity);

        //窗口对齐屏幕宽度
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;//设置对话框置顶显示
        win.setAttributes(lp);

        fillData(getData());

    }

    SaveSeedingGsonBean mSaveSeedingGsonBean;

    @Override
    public void initData() {
//        if (getBean() == null | getBean().getData() == null || getBean().getData().getTypeList() == null) {
//            ToastUtil.showLongToast("初始化失败");
//            return;
//        }
        showLoading();
        new PurchaseDeatilP(new ResultCallBack<SaveSeedingGsonBean>() {
            @Override
            public void onSuccess(SaveSeedingGsonBean saveSeedingGsonBean) {
                //item.purchaseJson.projectType
                if (ConstantParams.direct.equals(saveSeedingGsonBean.getData().getItem().purchaseJson.projectType)) {
                    mSaveSeedingGsonBean = saveSeedingGsonBean;
                    Log.i(TAG, "initData: 初始化直购");
                    initDirect(saveSeedingGsonBean.getData().getTypeList());
                    initAutoLayout2(getView(R.id.tfl_purchase_auto_add_plant), saveSeedingGsonBean.getData().getPlantTypeList());

                } else {
                    mSaveSeedingGsonBean = saveSeedingGsonBean;
                    Log.i(TAG, "initData: 初始化代购");
                    initProtocol(saveSeedingGsonBean.getData().getTypeList());
                    initAutoLayout2(getView(R.id.tfl_purchase_auto_add_plant), saveSeedingGsonBean.getData().getPlantTypeList());
                }
                hindLoading();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                hindLoading();
            }
        }).getDatas(getPurchaseType());//请求数据  进行排版


    }

    //step 1  这一步是一样的
    public void initAutoLayout2(TagFlowLayout tagFlowLayout, List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> bean) {
        tagFlowLayout.setCanCancle(false);
        SaveSeedlingPresenter.initAutoLayout2(tagFlowLayout, bean, index, mActivity, (view, position, parent) -> {
            plantType = bean.get(position).getValue();//上传值
            uploadBean.plantType = plantType;
            return true;
        });
    }

    @Override
    public String setTitle() {
        return "";
    }

    @Override
    public boolean setSwipeBackEnable() {
        return false;
    }


    @Override
    public void initProtocol(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen) {
        LinearLayout ll_purc_auto_add = getView(R.id.ll_purc_auto_add);
        autoLayouts.clear();
//      viewHolder_pur.ll_purc_auto_add.addView(new PurchaseAutoAddLinearLayout(this).setData(new PurchaseAutoAddLinearLayout.PlantBean("价格", "dbh", true)));
        ll_purc_auto_add.removeAllViews();//动态添加前先删除所有
        PurchaseAutoAddLinearLayout layout = (PurchaseAutoAddLinearLayout) new PurchaseAutoAddLinearLayout(this).setData(new PurchaseAutoAddLinearLayout.PlantBean("价格", "price", true));
        autoLayouts.add(layout);
        ll_purc_auto_add.addView(layout);


        layout = (PurchaseAutoAddLinearLayout) new PurchaseAutoAddLinearLayout(this).setData(new PurchaseAutoAddLinearLayout.PlantBean("到货价\n(预估)", "prePrice", false));
        autoLayouts.add(layout);
        ll_purc_auto_add.addView(layout);

        layout = (PurchaseAutoAddLinearLayout) new PurchaseAutoAddLinearLayout(this).setData(new PurchaseAutoAddLinearLayout.PlantBean("数量", "count", false));
        autoLayouts.add(layout);
        ll_purc_auto_add.addView(layout);

        for (int i = 0; i < typeListBeen.size(); i++) {
            if (mSaveSeedingGsonBean.getData().getItem().firstSeedlingTypeId.equals(typeListBeen.get(i).getId())) {
                //胸径   高度  冠幅  通过代码来增加
                for (int j = 0; j < typeListBeen.get(i).getParamsList().size(); j++) {
                    //创建 一个plantBean
                    PurchaseAutoAddLinearLayout.PlantBean plantBean = new PurchaseAutoAddLinearLayout.PlantBean(typeListBeen.get(i).getParamsList().get(j).getName(),
                            typeListBeen.get(i).getParamsList().get(j).getValue(),
                            typeListBeen.get(i).getParamsList().get(j).isRequired()
                    ).setSizeList(mSaveSeedingGsonBean.getData().dbhTypeList, mSaveSeedingGsonBean.getData().diameterTypeList);
                    //给plant 赋值
                    layout = (PurchaseAutoAddLinearLayout) new PurchaseAutoAddLinearLayout(this).setData(plantBean);
                    //保存当前的 viw 到list 列表  上传数据时需要获取其中的 内容
                    autoLayouts.add(layout);
                    ll_purc_auto_add.addView(layout);
                }


            }
            // "name": "地径",
            //"value": "diameter",
            //"required": true
        }

    }


    /**
     * 位界面头部添加几条数据
     */
    private void fillData(PurchaseItemBean_new data) {
        if (data == null) {
            return;
        }
        TextView tv = getView(R.id.tv_title);
        tv.setText(filterColor(data.name + "  " + data.count + data.unitTypeName, data.count + data.unitTypeName));
        setText(getView(R.id.space_text), "种植类型: " + data.plantTypeArrayNames);
        setText(getView(R.id.guige), "规格: " + FUtil.$_zero(data.specText));
    }


    private PurchaseItemBean_new getData() {
        Bundle b = getIntent().getExtras();
//        PurchaseItemBean_new itemBean_new =
        if (b != null && b.get(TAG) instanceof PurchaseItemBean_new) {

            return (PurchaseItemBean_new) b.get(TAG);
        } else {
            return new PurchaseItemBean_new();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 5) {
            listPicsOnline.clear();

            data.getExtras().get("urlPaths");

            PicSerializableMaplist myMap = (PicSerializableMaplist) data.getExtras().get("urlPaths");


            listPicsOnline.addAll(myMap.getMaplist());

            int size = listPicsOnline.size();

            选择图片.setText("已经选择了" + size + "张图片");
            //设置 多少张图片
            uploadBean.imagesData = GsonUtil.Bean2Json(listPicsOnline);

        }


    }
}

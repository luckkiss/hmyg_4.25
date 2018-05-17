package com.hldj.hmyg.saler.purchase.userbuy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackData;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.MainActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.child.HeadDetailActivity;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.Ui.ComonCityWheelDialogF;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.presenter.SaveSeedlingPresenter;
import com.hldj.hmyg.saler.FlowerInfoPhotoChoosePopwin2;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.bean.UserPurchaseGsonBean;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.util.TakePhotoUtil;
import com.hldj.hmyg.widget.MyOptionItemView;
import com.hy.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zf.iosdialog.widget.AlertDialog;
import com.zzy.common.widget.MeasureGridView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户 报价(发布)详情 界面  包含成功  与   失败
 */
public class PublishForUserDetailActivity extends BaseMVPActivity implements OnClickListener {


    @ViewInject(id = R.id.grid)
    MeasureGridView grid;


    public static PublishForUserDetailActivity instance;

    private String flowerInfoPhotoPath;
    private Button submit;
    private Button cancle;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_buy_user_detail;
    }

    @Override
    public void onClick(View v) {


    }


    List<Pic> listOnline = new ArrayList<>();
    int uploadCount = 0;
    int failedCount = 0;

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);
        instance = this;

        requestData(getExtraID());

        initGrid();

        cancle = getView(R.id.bottom_left);

        cancle.setVisibility(isPiPei ? View.VISIBLE : View.GONE);

        cancle.setOnClickListener(v -> {
            不保价(getExtraID());
        });
        submit = getView(R.id.bottom_right);
        submit.setOnClickListener(v -> {


            RadioButton left = getView(R.id.rb_title_left);
            RadioButton rb_title_right = getView(R.id.rb_title_right);
            if (!left.isChecked() && !rb_title_right.isChecked()) {
                ToastUtil.showLongToast("请选择价格类型");
                return;
            }


            List<File> l = SaveSeedlingPresenter.getFileList(grid.getAdapter().getDataList());


            listOnline.clear();

            showLoading();
            setLoadCancle(false);
            new SaveSeedlingPresenter(mActivity)
                    .upLoad(grid.getAdapter().getDataList(), new ResultCallBack<Pic>() {
                        @Override
                        public void onSuccess(Pic pic) {
                            if (!TextUtils.isEmpty(pic.getUrl())) {
                                listOnline.add(pic);
                            } else {
                                if (pic.getSort() > 0)
                                    ToastUtil.showLongToast("有图片损坏，您可以修改后重新上传！");
                            }
//                          urlPaths.replaceAll(,pic);
                            uploadCount = pic.getSort();
                            uploadCount++;

                            if (uploadCount > 0) {
                                UpdateLoading("已上传" + uploadCount + "张图片");
                            }


                            if (grid.getAdapter().getDataList().size() == uploadCount) {


                                if (uploadCount > 0) {
                                    UpdateLoading("上传数据中...");
                                }
//                            listOnline.add(pic);

                                if (pic.getSort() == grid.getAdapter().getDataList().size() - 1)

//                                    ToastUtil.showLongToast("submit");

                                    Log.i(TAG, "报价----> " + getText(getView(R.id.input)));

                                Switch aSwitch = getView(R.id.switch1);

                                RadioButton rb_title_left = getView(R.id.rb_title_left);


                                String str = rb_title_left.isChecked() ? "shangche" : "daoan";
                                Log.i(TAG, "到货价-- 上车价 --> " + str);

                                Log.i(TAG, "用苗地- Code-> " + currentCityCode);

                                Log.i(TAG, "备注- Code-> " + getText(getView(R.id.input_remark)));
/**
 * 	/**
 * 上车价  shangche("shangche","上车价"),
 *   到岸价 daoan("daoan","到岸价");
 */
                                new BasePresenter()
                                        .putParams("purchaseId", getExtraID())
                                        .putParams("price", getText(getView(R.id.input)))
                                        .putParams("priceType", str)
                                        .putParams("imagesData", GsonUtil.Bean2Json(listOnline))
                                        .putParams("cityCode", currentCityCode)
                                        .putParams("remarks", getText(getView(R.id.input_remark)))
                                        .doRequest("admin/userQuote/save", new HandlerAjaxCallBack(mActivity) {
                                            @Override
                                            public void onRealSuccess(SimpleGsonBean gsonBean) {
                                                Log.i(TAG, "onRealSuccess: " + gsonBean.isSucceed());
                                                initView();
                                            }
                                        });
                            }


                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {
                            hindLoading("网络异常", 3000);
                        }
                    });


        });

        getView(R.id.select_city).setOnClickListener(v -> {
            ComonCityWheelDialogF
                    .instance()
                    .addSelectListener((province, city, distrect, cityCode) -> {
                        MyOptionItemView myOptionItemView = getView(R.id.select_city);
//                        setText(getView(R.id.select_city), "用苗地  " + province + city);
                        myOptionItemView.setRightText(province + city);
                        currentCityCode = cityCode.substring(0, 4);

                    }).show(getSupportFragmentManager(), "select_city");
        });

        initLocation(getView(R.id.select_city));
    }

    private void 不保价(String extraID) {

        new BasePresenter()
                .putParams("id", extraID)
                .doRequest("admin/userPurchase/saveExclude", new HandlerAjaxCallBack(mActivity) {

                    public boolean isSucceed = false;

                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        ToastUtil.showShortToast(gsonBean.msg);
                        isSucceed = true;
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (isSucceed) {
                            setResult(ConstantState.CANCLE_SUCCEED);
                            finish();
                        }
                    }
                });


    }

    private void 提交报价() {


    }

    private String currentCityCode = "";


    private void initGrid() {


        grid.init(this, new ArrayList<Pic>(), (ViewGroup) getWindow().getDecorView(), new FlowerInfoPhotoChoosePopwin2.onPhotoStateChangeListener() {
            @Override
            public void onTakePic() {
                D.e("===========onTakePic=============");
                if (TakePhotoUtil.toTakePic(mActivity))//检查 存储空间
                    flowerInfoPhotoPath = TakePhotoUtil.doTakePhoto(mActivity, TakePhotoUtil.TO_TAKE_PIC);//照相
            }

            @Override
            public void onChoosePic() {
                D.e("===========onChoosePic=============");
                //通过本界面 addPicUrls 方法回调
                TakePhotoUtil.toChoosePic(mActivity, grid.getAdapter());
            }

            @Override
            public void onCancle() {
                D.e("===========onCancle=============");
            }
        });
    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start2Activity(Activity mActivity, String id, String owerId) {
        start2Activity(mActivity, id, owerId, false);
    }

    public static boolean isPiPei = false;//是否匹配求购

    public static void start2Activity(Activity mActivity, String id, String owerId, boolean isPiPei) {
        PublishForUserDetailActivity.isPiPei = isPiPei;
//        Intent intent = new Intent(mActivity, PublishForUserListActivity.class);
        if (owerId.equals(MyApplication.getUserBean().id)) {
            Intent intent = new Intent(mActivity, PublishForUserListActivity.class);
            Log.i(TAG, "id is =====  " + id);
            intent.putExtra("ID", id);
            mActivity.startActivityForResult(intent, 100);
        } else {
            Intent intent = new Intent(mActivity, PublishForUserDetailActivity.class);
            Log.i(TAG, "id is =====  " + id);
            intent.putExtra("ID", id);
            mActivity.startActivityForResult(intent, 100);
        }
    }


    public String getExtraID() {
        return getIntent().getExtras().getString("ID", "");
    }

    @Override
    public String setTitle() {
        return "用户求购";
    }


    private static final String TAG = "PublishForUserDetailAct";


    private String mUnitTypeName = "";

    public void requestData(String id) {


        new BasePresenter()
                .putParams("id", id)// 用户求购 用户求购 用户求购
                .doRequest("admin/userPurchase/detail", new HandlerAjaxCallBackData<UserPurchaseGsonBean>(mActivity) {
                    @Override
                    public void onRealSuccess(UserPurchaseGsonBean result) {
                        Log.i(TAG, "onRealSuccess: " + result.data.headerMap.ownerId);
                        getView(R.id.include_head)
                                .setOnClickListener(v -> {
//                                    ToastUtil.showLongToast("head is be click");
                                    HeadDetailActivity.start(mActivity, result.data.headerMap.ownerId);
                                });


                        setText(getView(R.id.head_name), FUtil.$_head("", result.data.headerMap.storeName));
//                    ToastUtil.showLongToast( );
//                        ToastUtil.showLongToast("" + result.data.headerMap.storeName);

                        setText(getView(R.id.head_title_name), result.data.headerMap.displayName);
                        ImageLoader.getInstance().displayImage(result.data.headerMap.headImage, (ImageView) getView(R.id.imageView17));

                        setText(getView(R.id.name), result.data.userPurchase.name);
                        setText(getView(R.id.close_time), result.data.userPurchase.closeDateStr);
                        setText(getView(R.id.quote_num), result.data.userPurchase.count + result.data.userPurchase.unitTypeName);

                        mUnitTypeName = result.data.userPurchase.unitTypeName;
                        setUnitType();

                        setText(getView(R.id.space_text), result.data.userPurchase.specText);
                        setText(getView(R.id.city), result.data.userPurchase.cityName);
                        setText(getView(R.id.marks), result.data.userPurchase.remarks);
                        setText(getView(R.id.yaoqiu), result.data.userPurchase.needImage ? "需要上传图片" : "可以不上传图片");
                        setText(getView(R.id.tv_fr_item_state), result.data.userPurchase.quoteCountJson + "条报价");


//imagesJson
                        if (result.data.userQuote != null) {

                            if (result.data.userQuote.imagesJson != null && result.data.userQuote.imagesJson.size() > 0) {
                                grid.getAdapter().addItems(PurchaseDetailActivity.getPicListOriginal(result.data.userQuote.imagesJson));
                                grid.getAdapter().closeAll(true);
                                grid.setVisibility(View.VISIBLE);
                            } else {
                                grid.setVisibility(View.GONE);
                            }

                            //  说明已经报过价格
                            setText(getView(R.id.input), result.data.userQuote.price + "元/" + mUnitTypeName);
                            EditText input = getView(R.id.input);
                            input.setTextColor(getColorByRes(R.color.price_orige));
                            input.setEnabled(false);

                            /* remark */
                            EditText input_remark = getView(R.id.input_remark);
                            input_remark.setEnabled(false);

                            getView(R.id.select_city).setOnClickListener(null);

                            RadioGroup radioGroup = ((RadioGroup) getView(R.id.rb_title_left).getParent());

                            changeState(getView(R.id.input), getView(R.id.textView67), radioGroup, true, result.data.userQuote.priceTypeName);


                            setText(getView(R.id.textView67), result.data.userQuote.priceTypeName);
                            MyOptionItemView city = getView(R.id.select_city);
//                            setText(city, "用苗地  " + result.data.userQuote.cityName);
                            city.setRightText(result.data.userQuote.cityName);

                            setText(getView(R.id.input_remark), FUtil.$_zero(result.data.userQuote.remarks));

                            submit.setText("删除报价");
                            submit.setOnClickListener(v -> {
                                Log.i(TAG, "onRealSuccess:  删除报价");

                                new AlertDialog(mActivity)
                                        .builder()
                                        .setCancelable(true)
                                        .setTitle("确定删除报价?")
                                        .setNegativeButton("取消", v1 -> {

                                        })
                                        .setPositiveButton("确定", v1 -> {
                                            删除报价(result.data.userQuote.id);
                                        }).show();


                            });


                        } else {
                            submit.setText("立即报价");
//                            setText(getView(R.id.textView67), "元/" + result.data.userPurchase.unitTypeName);

                            RadioGroup radioGroup = ((RadioGroup) getView(R.id.rb_title_left).getParent());

                            changeState(getView(R.id.input), getView(R.id.textView67), radioGroup, false, "元/" + result.data.userPurchase.unitTypeName);

                            EditText input_remark = getView(R.id.input_remark);
                            input_remark.setEnabled(true);
                        }


                    }

                    private void 删除报价(String purchaseId) {
                        new BasePresenter()
                                .putParams("id", purchaseId)
                                .doRequest("admin/userQuote/del", new HandlerAjaxCallBack(mActivity) {
                                    @Override
                                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                                        Log.i(TAG, "onRealSuccess: " + gsonBean.isSucceed());
                                        if (gsonBean.isSucceed()) {
                                            ToastUtil.showLongToast("删除成功");
                                            showLoading();
                                            new android.os.Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    resetBottom();
                                                    initView();
                                                }
                                            }, 1000);
//
                                        }
                                    }
                                });
                    }


                })
        ;


    }


    @ViewInject(id = R.id.select_city)
    MyOptionItemView select_city;

    public void isShowRight(boolean isShow) {


        if (isShow) {
            Drawable drawable = MyApplication.getInstance().getResources().getDrawable(R.drawable.arrow_right_eq);
//            Drawable drawable = MyApplication.getInstance().getDrawable(R.drawable.arrow_right_eq);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//            select_city.setCompoundDrawables(null, null, drawable, null);
        } else {
//            select_city.setCompoundDrawables(null, null, null, null);
        }
        select_city.showRightImg(isShow);


    }

    private void changeState(EditText editText, TextView view1, RadioGroup sw, boolean isQuote, String str) {


        if (isQuote)//报价状态
        {
            editText.setTextColor(getColorByRes(R.color.price_orige));
            view1.setVisibility(View.VISIBLE);
            view1.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.tag_bg2));
            view1.setText(str);
            view1.setTextColor(getColorByRes(R.color.main_color));

            sw.getChildAt(1).setVisibility(View.GONE);
            sw.getChildAt(2).setVisibility(View.GONE);
//            sw.setVisibility(View.GONE);


            submit.setTextColor(getColorByRes(R.color.text_color666));
            submit.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.round_rectangle_gray_bg));

            isShowRight(false);

        } else {
            //非报价状态
            editText.setTextColor(getColorByRes(R.color.text_color666));
            view1.setVisibility(View.VISIBLE);
//            sw.setVisibility(View.VISIBLE);

            sw.getChildAt(1).setVisibility(View.VISIBLE);
            sw.getChildAt(2).setVisibility(View.VISIBLE);

            view1.setText("元/" + mUnitTypeName);
            view1.setTextColor(getColorByRes(R.color.text_color666));
            view1.setBackground(new ColorDrawable());

            submit.setTextColor(getColorByRes(R.color.white));
            submit.setBackground(MyApplication.getInstance().getResources().getDrawable(R.drawable.rb_left_select));

            /**
             *    android:background="@drawable/white_btn_selector"
             rb_left_select
             */
            isShowRight(true);

//            TextView select_city = getView(R.id.select_city);


        }

//        RadioButton radioButton = (RadioButton) sw.getChildAt(1);

//        radioButton.setChecked(isQuote);


    }

    private void resetBottom() {

        EditText input = getView(R.id.input);
        input.setTextColor(getColorByRes(R.color.text_color333));
        input.setEnabled(true);

        EditText input_remark = getView(R.id.input_remark);
        input_remark.setEnabled(true);

        setText(getView(R.id.input), "");
//        setText(getView(R.id.select_city), "苗源地");
        setText(getView(R.id.input_remark), "");
//        setText(getView(R.id.textView67), "元/" + mUnitTypeName);

        Switch aSwitch = getView(R.id.switch1);
        aSwitch.setChecked(false);

        RadioButton radioLeft = getView(R.id.rb_title_left);
        radioLeft.setChecked(true);


        grid.getAdapter().getDataList().clear();
        grid.getAdapter().notifyDataSetChanged();
        isShowRight(true);

        initLocation(getView(R.id.select_city));

    }


    public void initLocation(MyOptionItemView city) {
        if (MainActivity.aMapLocation != null) {
            if (!TextUtils.isEmpty(MainActivity.cityCode)) {
//                CityGsonBean.ChildBeans cityBeans = new CityGsonBean.ChildBeans();
                currentCityCode = MainActivity.cityCode;
                city.setRightText(MainActivity.province_loc + " " + MainActivity.city_loc);
            }
        } else {
            city.setRightText("未选择");
        }


    }

    public void setUnitType() {
        setText(getView(R.id.textView67), "元/" + mUnitTypeName);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }


    /**
     * {@link com.zzy.flowers.activity.photoalbum.PhotoActivity#sendPhotos}
     */
    public void addPicUrls(ArrayList<Pic> resultPathList) {
        grid.getAdapter().addItems(resultPathList);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // TODO Auto-generated method stub
        if (requestCode == TakePhotoUtil.TO_TAKE_PIC && resultCode == RESULT_OK) {
            try {
                grid.addImageItem(flowerInfoPhotoPath);
                grid.getAdapter().Faild2Gone(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(getContentResolver(),
                        flowerInfoPhotoPath, flowerInfoPhotoPath, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            // sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
            // Uri.parse("file://" + flowerInfoPhotoPath)));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}

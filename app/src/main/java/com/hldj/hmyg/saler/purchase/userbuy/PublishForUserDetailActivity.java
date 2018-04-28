package com.hldj.hmyg.saler.purchase.userbuy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackData;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
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
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.util.TakePhotoUtil;
import com.hy.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
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
@SuppressLint({"NewApi", "ResourceAsColor"})
public class PublishForUserDetailActivity extends BaseMVPActivity implements OnClickListener {


    @ViewInject(id = R.id.grid)
    MeasureGridView grid;

    public static PublishForUserDetailActivity instance;

    private String flowerInfoPhotoPath;
    private Button submit;

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
        submit = getView(R.id.submit);
        submit.setOnClickListener(v -> {


            List<File> l = SaveSeedlingPresenter.getFileList(grid.getAdapter().getDataList());


            listOnline.clear();
            new SaveSeedlingPresenter(mActivity)
                    .upLoad(grid.getAdapter().getDataList(), new ResultCallBack<Pic>() {
                        @Override
                        public void onSuccess(Pic pic) {
                            if (!TextUtils.isEmpty(pic.getUrl())) {
                                listOnline.add(pic);
                            } else {
                                ToastUtil.showLongToast("有图片损坏，您可以修改后重新上传！");
                            }
//                          urlPaths.replaceAll(,pic);
                            uploadCount = pic.getSort();
                            uploadCount++;

                            if (grid.getAdapter().getDataList().size() == uploadCount) {


//                            listOnline.add(pic);

                                if (pic.getSort() == grid.getAdapter().getDataList().size() - 1)

                                    ToastUtil.showLongToast("submit");

                                Log.i(TAG, "报价----> " + getText(getView(R.id.input)));

                                Switch aSwitch = getView(R.id.switch1);

                                String str = aSwitch.isChecked() ? "shangche" : "daoan";
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

                        }
                    });


        });

        getView(R.id.select_city).setOnClickListener(v -> {
            ComonCityWheelDialogF
                    .instance()
                    .addSelectListener((province, city, distrect, cityCode) -> {
                        setText(getView(R.id.select_city), "用苗地  " + province + city);
                        currentCityCode = cityCode.substring(0, 4);

                    }).show(getSupportFragmentManager(), "select_city");
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
//        Intent intent = new Intent(mActivity, PublishForUserListActivity.class);

        if (owerId.equals(MyApplication.getUserBean().id)) {
            Intent intent = new Intent(mActivity, PublishForUserListActivity.class);
            Log.i(TAG, "id is =====  " + id);
            intent.putExtra("ID", id);
            mActivity.startActivity(intent);
        } else {
            Intent intent = new Intent(mActivity, PublishForUserDetailActivity.class);
            Log.i(TAG, "id is =====  " + id);
            intent.putExtra("ID", id);
            mActivity.startActivity(intent);
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

    public void requestData(String id) {


        new BasePresenter()
                .putParams("id", id)// 用户求购 用户求购 用户求购
                .doRequest("admin/userPurchase/detail", new HandlerAjaxCallBackData<UserPurchaseGsonBean>(mActivity) {
                    @Override
                    public void onRealSuccess(UserPurchaseGsonBean result) {
                        Log.i(TAG, "onRealSuccess: " + result.data.headerMap.ownerId);
                        setText(getView(R.id.head_name), result.data.headerMap.storeName);
                        setText(getView(R.id.head_title_name), result.data.headerMap.displayName);
                        ImageLoader.getInstance().displayImage(result.data.headerMap.headImage, (ImageView) getView(R.id.imageView17));

                        setText(getView(R.id.name), result.data.userPurchase.name);
                        setText(getView(R.id.close_time), result.data.userPurchase.closeDateStr);
                        setText(getView(R.id.quote_num), result.data.userPurchase.count + result.data.userPurchase.unitTypeName);
                        setText(getView(R.id.space_text), result.data.userPurchase.specText);
                        setText(getView(R.id.city), result.data.userPurchase.cityName);
                        setText(getView(R.id.marks), result.data.userPurchase.remarks);
                        setText(getView(R.id.yaoqiu), result.data.userPurchase.needImage ? "需要上传图片" : "可以不上传图片");
                        setText(getView(R.id.tv_fr_item_state), "1条报价");


//imagesJson
                        if (result.data.userQuote != null) {

                            if (result.data.userQuote.imagesJson != null && result.data.userQuote.imagesJson.size() > 0) {
                                grid.getAdapter().addItems(PurchaseDetailActivity.getPicList(result.data.userQuote.imagesJson));
                                grid.getAdapter().closeAll(true);
                                grid.setVisibility(View.VISIBLE);
                            } else {
                                grid.setVisibility(View.GONE);
                            }

                            //  说明已经报过价格
                            setText(getView(R.id.input), result.data.userQuote.price + "元/株");
                            EditText input = getView(R.id.input);
                            input.setTextColor(getColorByRes(R.color.price_orige));
                            input.setEnabled(false);

                            /* remark */
                            EditText input_remark = getView(R.id.input_remark);
                            input_remark.setEnabled(false);

                            getView(R.id.select_city).setOnClickListener(null);


                            changeState(getView(R.id.input), getView(R.id.textView67), getView(R.id.switch1), true, result.data.userQuote.priceTypeName);


                            setText(getView(R.id.textView67), result.data.userQuote.priceTypeName);
                            TextView city = getView(R.id.select_city);
                            setText(city, "用苗地  " + result.data.userQuote.cityName);
                            setText(getView(R.id.input_remark), result.data.userQuote.remarks);

                            submit.setText("删除报价");
                            submit.setOnClickListener(v -> {
                                Log.i(TAG, "onRealSuccess:  删除报价");


                                删除报价(result.data.userQuote.id);

                            });


                        } else {
                            submit.setText("立即报价");
                            changeState(getView(R.id.input), getView(R.id.textView67), getView(R.id.switch1), false, "元/株");

                            EditText input_remark = getView(R.id.input_remark);
                            input_remark.setEnabled(true);
                        }


                    }

                    private void 删除报价(String purchaseId) {
                        new BasePresenter()
                                .putParams("id", purchaseId)
                                .doRequest("admin/userQuote/del", new HandlerAjaxCallBack() {
                                    @Override
                                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                                        Log.i(TAG, "onRealSuccess: " + gsonBean.isSucceed());
                                        if (gsonBean.isSucceed()) {
                                            initView();
                                            ToastUtil.showLongToast("删除成功,刷新界面");
                                            resetBottom();
                                        }
                                    }
                                });
                    }


                })
        ;


    }

    private void changeState(EditText editText, TextView view1, Switch sw, boolean isQuote, String str) {

        if (isQuote)//报价状态
        {
            editText.setTextColor(getColorByRes(R.color.price_orige));
            view1.setVisibility(View.VISIBLE);
            view1.setBackground(getDrawable(R.drawable.tag_bg2));
            view1.setText(str);
            view1.setTextColor(getColorByRes(R.color.main_color));
            sw.setVisibility(View.GONE);


            submit.setTextColor(getColorByRes(R.color.text_color666));
            submit.setBackground(getDrawable(R.drawable.white_btn_selector));

        } else {
            //非报价状态
            editText.setTextColor(getColorByRes(R.color.text_color666));
            view1.setVisibility(View.VISIBLE);
            sw.setVisibility(View.VISIBLE);
            view1.setText("元/株");
            view1.setTextColor(getColorByRes(R.color.text_color666));
            view1.setBackground(new ColorDrawable());

            submit.setTextColor(getColorByRes(R.color.white));
            submit.setBackground(getDrawable(R.drawable.rb_left_select));

            /**
             *    android:background="@drawable/white_btn_selector"
             rb_left_select
             */


        }

        sw.setChecked(isQuote);


    }

    private void resetBottom() {

        EditText input = getView(R.id.input);
        input.setTextColor(getColorByRes(R.color.text_color333));
        input.setEnabled(true);

        EditText input_remark = getView(R.id.input_remark);
        input_remark.setEnabled(true);

        setText(getView(R.id.input), "");
        setText(getView(R.id.select_city), "苗源地");
        setText(getView(R.id.input_remark), "");
        setText(getView(R.id.textView67), "元/株");

        Switch aSwitch = getView(R.id.switch1);
        aSwitch.setChecked(false);

        grid.getAdapter().getDataList().clear();
        grid.getAdapter().notifyDataSetChanged();

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

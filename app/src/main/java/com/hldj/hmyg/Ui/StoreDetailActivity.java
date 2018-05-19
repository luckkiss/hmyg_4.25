package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.child.CenterActivity;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.StoreGsonBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.hldj.hmyg.widget.MyOptionItemView;
import com.hldj.hmyg.widget.SharePopupWindow;
import com.hy.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.white.utils.FileUtil;
import com.zxing.encoding.EncodingHandler;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

import static com.hldj.hmyg.StoreActivity.getRoundCornerImage;

/**
 * 店铺详情
 */

public class StoreDetailActivity extends BaseMVPActivity {

    public static String storeId = "";

    @Override
    public int bindLayoutID() {
        return R.layout.activity_store_detail;
    }

    @ViewInject(id = R.id.name)
    TextView name;
    @ViewInject(id = R.id.logo)
    ImageView logo;

    @ViewInject(id = R.id.brower_num)
    TextView brower_num;

    @ViewInject(id = R.id.toolbar_right_icon)
    ImageView toolbar_right_icon;


    ComonShareDialogFragment.ShareBean shareBean;

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);

        toolbar_right_icon.setImageResource(R.drawable.fenxiang);
        toolbar_right_icon.setVisibility(View.VISIBLE);
        toolbar_right_icon.setOnClickListener(v -> {
//            ToastUtil.showLongToast("share");
            D.d("分享");
//            ToastUtil.showShortToast("share");
            if (null == shareBean) {
                ToastUtil.showShortToast("获取分享数据失败");
            }
            SharePopupWindow window = new SharePopupWindow(mActivity, shareBean);
            window.showAsDropDown(toolbar_right_icon);


        });

        name.setTextColor(getColorByRes(R.color.text_color666));
        brower_num.setTextColor(getColorByRes(R.color.text_color999));


        requestData();


    }

    private void requestData() {

        D.i("requestData: start");
        Observable
                .create((ObservableOnSubscribe<SimpleGsonBean>) emitter ->
                        new BasePresenter()
                                .putParams("id", storeId)
                                .doRequest("store/index", new HandlerAjaxCallBack(mActivity) {
                                    @Override
                                    public void onRealSuccess(SimpleGsonBean gsonBean) {
//                                        ToastUtil.showLongToast(gsonBean.msg);
                                        emitter.onNext(gsonBean);
                                    }
                                })).map(simpleGsonBean -> {

            initStroeBean(simpleGsonBean.getData().store, simpleGsonBean.getData().owner.headImage);

            return simpleGsonBean;
        }).map(simpleGsonBean -> {
            initOwnerBean(simpleGsonBean.getData().owner);
            return simpleGsonBean;
        }).subscribe(simpleGsonBean -> {
            initOtherCount(simpleGsonBean);
//            ToastUtil.showLongToast("结束");
            D.i("--结束---");
        }, throwable -> {
            D.i("--报错了---" + throwable.getMessage());
        });
//                .map(new Function<String, SimpleGsonBean>() {
//                    @Override
//                    public SimpleGsonBean apply(String id) throws Exception {
//
//
//
//
//
//                        return null;
//                    }
//                });


    }


    @ViewInject(id = R.id.option_phone)
    RelativeLayout option_phone;

    @ViewInject(id = R.id.option_identity)
    MyOptionItemView option_identity;

    @ViewInject(id = R.id.store_identity)
    View store_identity;// 企业认证

    private void initOtherCount(SimpleGsonBean simpleGsonBean) {

        //onShelfCount在售苗木数量
        //momentsCount  苗木圈数量
        //identity  企业认证信息
        brower_num.setText(String.format("浏览量 %d", simpleGsonBean.getData().visitsCount));
        setText(getView(R.id.sps), String.format("%d\n商品数", simpleGsonBean.getData().onShelfCount));
        setText(getView(R.id.mmq), String.format("%d\n苗木圈", simpleGsonBean.getData().momentsCount));


        setText(getView(R.id.tv_phone), simpleGsonBean.getData().phone);
        option_phone.setOnClickListener(v -> {
            FlowerDetailActivity.CallPhone(
                    simpleGsonBean.getData().phone, mActivity, v1 ->
                            D.i("call loag -- - -- - -"));
        });
//        option_phone.setRightText("" + simpleGsonBean.getData().phone);


//        option_phone.setRightTextColor(getColorByRes(R.color.main_color));
//        option_phone.setOnClickListener(v -> {
////            ToastUtil.showLongToast(simpleGsonBean.getData().phone);
//
//        });

        option_identity.setRightText(simpleGsonBean.getData().identity ? "已认证" : "未认证");

        option_identity.showRightImg(true);

        store_identity.setVisibility(simpleGsonBean.getData().identity ? View.VISIBLE : View.GONE);


        if (simpleGsonBean.getData().identity) {
            option_identity.setOnClickListener(v -> {
//            ToastUtil.showLongToast("认证");
                AuthenticationCompanyActivity.start(mActivity, AuthenticationActivity.no_auth, "", false, StoreDetailActivity.storeId);
            });
        } else {
            option_identity.showRightImg(false);
        }


        /**
         android:id="@+id/mmq"
         android:layout_width="0dp"
         android:layout_height="match_parent"
         android:layout_weight="1"
         android:gravity="center"
         android:text="2\n苗木圈"
         */


    }

    private void initOwnerBean(StoreGsonBean.DataBean.OwnerBean owner) {
        D.i("---------initOwnerBean-------" + owner.headImage);

//        if (TextUtils.isEmpty(owner.headImage)) {
//            logo.setImageResource(R.drawable.company_head);
//        } else {
//            ImageLoader.getInstance().displayImage(owner.headImage, logo);
//
//        }

        getView(R.id.mmq).setOnClickListener(v -> {
            CenterActivity.start(mActivity, owner.id);
        });
//        http://image.hmeg.cn/upload/image/201708/c34e8838daee4d8fad4e5d938385d3ae.jpeg


    }


    /**
     * "store":{
     * "id":"738f2ca04a234752808bd04113d0f3d2",
     * "createBy":"43f37d26d0bb409896626d0de5a891ea",
     * "createDate":"2017-08-20 12:18:27",
     * "num":"E0650",
     * "name":"商家店铺",
     * "ownerId":"43f37d26d0bb409896626d0de5a891ea",
     * "shareTitle":"商家店铺",
     * "shareContent":"苗木交易原来可以如此简单，配上花木易购APP，指尖轻点，交易无忧。",
     * "shareUrl":"http://m.hmeg.cn/seedling/store/738f2ca04a234752808bd04113d0f3d2.html"
     */

    @ViewInject(id = R.id.option_qc_code)
    MyOptionItemView option_qc_code;
    @ViewInject(id = R.id.tv_mainType)
    TextView tv_mainType;

    @ViewInject(id = R.id.tv_remarks)
    TextView tv_remarks;

    private void initStroeBean(StoreGsonBean.DataBean.StoreBean store, String head) {
        D.i("---------initStroeBean-------" + store.name);
        name.setText(store.name);

        if (TextUtils.isEmpty(store.logoUrl)) {
            logo.setImageResource(R.drawable.company_head);
        } else {
            ImageLoader.getInstance().displayImage(store.logoUrl, logo);
        }

        option_qc_code.setOnClickListener(v -> {
//            ToastUtil.showLongToast(getOssImagePaths(store.shareUrl).toString());
            getOssImagePaths(store.shareUrl);
            if (ossImagePaths.size() > 0) {
                GalleryImageActivity.startGalleryImageActivity(mActivity, 0, ossImagePaths);
            }
        });

        tv_mainType.setText(store.mainType);
        tv_remarks.setText(store.remarks);

        getView(R.id.sps).setOnClickListener(v -> {
            StoreActivity_new.start2Activity(mActivity, storeId);
        });


        createShareBean(store, head);

    }


    public static void start(Activity mActivity, String storeId) {
        StoreDetailActivity.storeId = storeId;
        mActivity.startActivity(new Intent(mActivity, StoreDetailActivity.class));


    }

    private void createShareBean(StoreGsonBean.DataBean.StoreBean store, String headImage) {
        shareBean = new ComonShareDialogFragment.ShareBean(
                store.shareTitle,
                store.shareContent,
                store.shareUrl,
                headImage,
                store.shareUrl
        );
    }


    private ArrayList<Pic> ossImagePaths = new ArrayList<Pic>();

    public ArrayList<Pic> getOssImagePaths(String shareUrl) {

        if (ossImagePaths.size() > 0) {
            return ossImagePaths;
        }
        if (TextUtils.isEmpty(shareUrl)) {
            ToastUtil.showShortToast("对不起，无分享地址");
        }
        try {
            Bitmap qrCodeBitmap = EncodingHandler.createQRCode(shareUrl, 350);
            qrCodeBitmap = getRoundCornerImage(qrCodeBitmap, 3);
            String img_path = "";
            try {
                img_path = FileUtil.saveMyBitmap("commenQcCode", qrCodeBitmap);
                if (!"".equals(img_path)) {
                    ossImagePaths.add(new Pic("", false, img_path, 0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return ossImagePaths;
    }

    @Override
    public String setTitle() {
        return "店铺详情";
    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

}

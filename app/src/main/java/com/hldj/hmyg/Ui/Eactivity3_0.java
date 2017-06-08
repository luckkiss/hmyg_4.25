package com.hldj.hmyg.Ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.FeedBackActivity;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.ManagerListActivity;
import com.hldj.hmyg.MessageListActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.SafeAcountActivity;
import com.hldj.hmyg.SetProfileActivity;
import com.hldj.hmyg.SettingActivity;
import com.hldj.hmyg.StoreActivity;
import com.hldj.hmyg.application.PermissionUtils;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.presenter.EPrestenter;
import com.hldj.hmyg.saler.AdressListActivity;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.StoreSettingActivity;
import com.hldj.hmyg.saler.Ui.ManagerQuoteListActivity_new;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.RippleAdjuster;
import com.hldj.hmyg.widget.ShareDialogFragment;
import com.hy.utils.GetServerUrl;
import com.lqr.optionitemview.OptionItemView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.soundcloud.android.crop.Crop;
import com.zf.iosdialog.widget.ActionSheetDialog;
import com.zym.selecthead.config.Configs;
import com.zym.selecthead.tools.FileTools;
import com.zym.selecthead.tools.SelectHeadTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * Created by Administrator on 2017/5/18.
 */

public class Eactivity3_0 extends NeedSwipeBackActivity {


    private SuperTextView.Adjuster adjuster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_activity_3_0);
        setSwipeBackEnable(false);
        RxRegi();
        RxEvent();

//        RxBus.$().OnEvent(, new Action1<Object>() {
//            @Override
//            public void call(Object onlineEvent) {
//                D.e("====RxBus====OnEvent=========");
//                ToastUtil.showShortToast("denglu");
//                refresh();
//                loadHeadImage(getSpB("isLogin"));
//            }
//        });


        LinearLayout ll_me_content = (LinearLayout) findViewById(R.id.ll_me_content);

        /**
         * 为所有suptertextivew 设置点击效果
         */
        adjuster = new RippleAdjuster(Color.argb(10, 1, 1, 1));
        addAdjuster(ll_me_content);

        loadHeadImage(getSpB("isLogin"));//加载头像
        setRealName(getSpS("userName"), getSpS("realName"));

        this.getView(R.id.sptv_wd_mmgl).setOnClickListener(v -> ManagerListActivity.start2Activity(mActivity));//苗木管理
        this.getView(R.id.sptv_wd_bjgl).setOnClickListener(v -> ManagerQuoteListActivity_new.start2Activity(mActivity));//报价管理
        this.getView(R.id.sptv_wd_wddp).setOnClickListener(v -> StoreActivity.start2Activity(mActivity, getSpS("code")));//我的店铺
        this.getView(R.id.sptv_wd_dpsz).setOnClickListener(v -> StoreSettingActivity.start2Activity(mActivity));//店铺设置
        this.getView(R.id.sptv_wd_zhaq).setOnClickListener(v -> SafeAcountActivity.start2Activity(mActivity));//账户安全
        this.getView(R.id.sptv_wd_mydz).setOnClickListener(v -> AdressListActivity.start2Activity(mActivity));//苗源地址
        this.getView(R.id.sptv_wd_fxapp).setOnClickListener(v -> ShareDialogFragment.newInstance().show(getSupportFragmentManager(), getClass().getName()));//分享 app
        this.getView(R.id.sptv_wd_kf).setOnClickListener(v -> Call_Phone()); // 客服
        this.getView(R.id.sptv_wd_yhfk).setOnClickListener(v -> FeedBackActivity.start2Activity(mActivity));//反馈
        this.getView(R.id.sptv_wd_bjzl).setOnClickListener(v -> SetProfileActivity.start2ActivitySet(mActivity, 100));//编辑资料
        this.getView(R.id.iv_circle_head).setOnClickListener(v -> {
            if (submit()) setPics();
        });//点击弹窗选择拍照 或者 相册  上传图片


        OptionItemView optionItemView = this.getView(R.id.top_bar_option); // 这是title 左右边的点击事件

        optionItemView.setOnOptionItemClickListener(new OptionItemView.OnOptionItemClickListener() {
            @Override
            public void leftOnClick() {
                SettingActivity.start2Activity(mActivity);
            }

            @Override
            public void centerOnClick() {
            }

            @Override
            public void rightOnClick() {
                MessageListActivity.start2Activity(mActivity);
            }
        });


    }

    private void setRealName(String username, String realName) {

        if (!TextUtils.isEmpty(realName)) {
            //
            ((TextView) this.getView(R.id.tv_usrname_relname)).setText(realName);
        } else if (!TextUtils.isEmpty(username)) {
            ((TextView) this.getView(R.id.tv_usrname_relname)).setText(username);
        } else {
            ((TextView) this.getView(R.id.tv_usrname_relname)).setText("花木易购");
        }

    }

    String headImg;

    public void setPics() {
        new ActionSheetDialog(mActivity).builder().setCancelable(true).setCanceledOnTouchOutside(true)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Red,
                        which -> {
                            if (PermissionUtils.requestCamerPermissions(200))
                                SelectHeadTools.startCamearPicCut(mActivity, photoUri);
                        })
                .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue,
                        which -> {
                            if (PermissionUtils.requestReadSDCardPermissions(200))
                                Crop.pickImage(mActivity);
                        })
                .addSheetItem("查看大图", ActionSheetDialog.SheetItemColor.Blue,
                        which -> {
                            if (!TextUtils.isEmpty(headImg = getSpS("headImage"))) {
                                ArrayList<Pic> ossUrls = new ArrayList<>();
                                ossUrls.add(new Pic("", false, headImg, 0));
                                GalleryImageActivity.startGalleryImageActivity(mActivity, 0, ossUrls);
                            } else {
                                showToast("您还未设置头像，赶紧拍张靓照吧");
                            }
                        }).show();
    }


    private Uri photoUri;

    private boolean submit() {
        if (!FileTools.hasSdcard()) {
            showToast("没有找到SD卡，请检查SD卡是否存在");
            return false;
        }
        try {
            photoUri = FileTools.getUriByFileDirAndFileName(
                    Configs.SystemPicture.SAVE_DIRECTORY,
                    Configs.SystemPicture.SAVE_PIC_NAME);
        } catch (IOException e) {
            showToast("创建文件失败");
            return false;
        }
        return true;
    }

    String str = "http://image.hmeg.cn/upload/image/201705/b114185426b6459180ad47d96f21bd28.png";

    private void loadHeadImage(boolean isLogin) {
        if (isLogin)
            ImageLoader.getInstance().displayImage(getSpS("headImage"), (ImageView) getView(R.id.iv_circle_head), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                    ((ImageView) getView(R.id.iv_circle_head)).setImageResource(R.drawable.icon_persion_pic);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {

                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
//            ImageLoader.getInstance().displayImage(str, (ImageView) getView(R.id.iv_circle_head));
    }

    /**
     * .showAnim(mBasIn)//
     * .dismissAnim(mBasOut)//
     */

    public void Call_Phone() {
        final com.flyco.dialog.widget.MaterialDialog dialog = new com.flyco.dialog.widget.MaterialDialog(
                mActivity);
        dialog.title("客服热线4006-579-888").content("客服热线 周一至周日9:00-18:00")//
                .btnText("确认拨号", "取消")//
                .show();
        dialog.setOnBtnClickL(() -> {
            dialog.dismiss();
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + GetServerUrl.Customer_Care_Phone));
            if (ActivityCompat.checkSelfPermission(mActivity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intent);
        }, () -> dialog.dismiss());
    }


    private void addAdjuster(ViewGroup ll_me_content) {
        for (int i = 0; i < ll_me_content.getChildCount(); i++) {
            if (ll_me_content.getChildAt(i) instanceof ViewGroup) {
                addAdjuster(((ViewGroup) ll_me_content.getChildAt(i)));
            } else if (ll_me_content.getChildAt(i) instanceof SuperTextView) {
                ((SuperTextView) ll_me_content.getChildAt(i)).setAdjuster(adjuster);
            }
        }
    }


    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    public Bitmap getBitmapFromUri(Uri uri, Context mContext) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                    mContext.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Configs.SystemPicture.PHOTO_REQUEST_TAKEPHOTO: // 拍照
                Log.i("onActivityResult", "PHOTO_REQUEST_TAKEPHOTO");
                if (SelectHeadTools.imageUri != null) {
                    beginCrop(SelectHeadTools.imageUri);
                    if (SelectHeadTools.imageUri != null) SelectHeadTools.imageUri = null;
                } else {
                    beginCrop(photoUri);
                }
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_GALLERY:// 相册获取
                if (data != null)
                    photoUri = data.getData();
                SelectHeadTools.startPhotoZoom(this, photoUri, 600);
                break;

        }
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        } else if (requestCode == 100 && resultCode == ConstantState.CHANGE_DATES) {
            refresh();
        }


    }


    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            File file = FileTools.getFileByUri(this, Crop.getOutput(result));
            BasePresenter presenter = new EPrestenter()
                    .addResultCallBack(new ResultCallBack<String>() {
                        @Override
                        public void onSuccess(String str) {
                            putSpS("headImage", str);
                            new Handler().postDelayed(() -> {
                                loadHeadImage(true);
                            }, 200);
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {
                        }
                    });

            ((EPrestenter) presenter).upLoadHeadImg("admin/file/uploadHeadImage", true, file.getAbsolutePath());
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxUnRegi();
    }


    public void RxRegi()
    {
        RxBus.getInstance().register(OnlineEvent.class);
    }
    public void RxUnRegi(){
        RxBus.getInstance().unRegister(OnlineEvent.class);
    }
    public void RxEvent()
    {

        RxBus.getInstance().tObservable();
    }


    public static class OnlineEvent {
        boolean isOnline = false;

        public OnlineEvent(boolean isOnline) {
            this.isOnline = isOnline;
        }
    }

    public void refresh() {
        setRealName(getSpS("userName"), getSpS("realName"));
    }


}

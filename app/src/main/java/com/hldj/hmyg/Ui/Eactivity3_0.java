package com.hldj.hmyg.Ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.FeedBackActivity;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.ManagerListActivity_new;
import com.hldj.hmyg.MessageListActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.SafeAcountActivity;
import com.hldj.hmyg.SetProfileActivity;
import com.hldj.hmyg.SettingActivity;
import com.hldj.hmyg.StoreActivity;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.base.rxbus.annotation.Subscribe;
import com.hldj.hmyg.base.rxbus.event.EventThread;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.presenter.EPrestenter;
import com.hldj.hmyg.saler.AdressManagerActivity;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.StoreSettingActivity;
import com.hldj.hmyg.saler.Ui.ManagerQuoteListActivity_new;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.RippleAdjuster;
import com.hldj.hmyg.util.UploadHeadUtil;
import com.hldj.hmyg.widget.ShareDialogFragment;
import com.hy.utils.GetServerUrl;
import com.lqr.optionitemview.OptionItemView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zf.iosdialog.widget.ActionSheetDialog;
import com.zym.selecthead.tools.FileTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static com.hldj.hmyg.util.UploadHeadUtil.CHOOSE_PHOTO;
import static com.hldj.hmyg.util.UploadHeadUtil.CROP_PHOTO;
import static com.hldj.hmyg.util.UploadHeadUtil.TAKE_PHOTO;
import static com.hldj.hmyg.util.UploadHeadUtil.getDiskCacheDir;

/**
 * Created by Administrator on 2017/5/18.
 */

public class Eactivity3_0 extends NeedSwipeBackActivity {

    private SuperTextView.Adjuster adjuster;

    UploadHeadUtil uploadHeadUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_activity_3_0);

        uploadHeadUtil = new UploadHeadUtil(mActivity);

        cachPath = UploadHeadUtil.getDiskCacheDir(this) + "/handimg.jpg";//图片路径
        cacheFile = uploadHeadUtil.getCacheFile(new File(getDiskCacheDir(this)), "handimg.jpg");

        setSwipeBackEnable(false);
        RxRegi();


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

        this.getView(R.id.sptv_wd_mmgl).setOnClickListener(v -> ManagerListActivity_new.start2Activity(mActivity));//苗木管理
        this.getView(R.id.sptv_wd_bjgl).setOnClickListener(v -> ManagerQuoteListActivity_new.start2Activity(mActivity));//报价管理
        this.getView(R.id.sptv_wd_wddp).setOnClickListener(v -> StoreActivity.start2ActivityForRsl(mActivity, getSpS("code")));//我的店铺
        this.getView(R.id.sptv_wd_dpsz).setOnClickListener(v -> StoreSettingActivity.start2Activity(mActivity));//店铺设置
        this.getView(R.id.sptv_wd_zhaq).setOnClickListener(v -> SafeAcountActivity.start2Activity(mActivity));//账户安全
        this.getView(R.id.sptv_wd_mydz).setOnClickListener(v -> AdressManagerActivity.start2Activity(mActivity));//苗源地址管理
        this.getView(R.id.sptv_wd_fxapp).setOnClickListener(v -> ShareDialogFragment.newInstance().show(getSupportFragmentManager(), getClass().getName()));//分享 app
        this.getView(R.id.sptv_wd_kf).setOnClickListener(v -> Call_Phone()); // 客服
        this.getView(R.id.sptv_wd_yhfk).setOnClickListener(v -> FeedBackActivity.start2Activity(mActivity));//反馈
        this.getView(R.id.sptv_wd_bjzl).setOnClickListener(v -> SetProfileActivity.start2ActivitySet(mActivity, 100));//编辑资料
        this.getView(R.id.sptv_wd_wdxm).setOnClickListener(v ->MyProgramActivity.start(mActivity));//我的项目


        this.getView(R.id.iv_circle_head).setOnClickListener(v -> {
            setPics();
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
                //消息
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


    public interface PermissionListener {
        /**
         * 成功获取权限
         */
        void onGranted();

        /**
         * 为获取权限
         *
         * @param deniedPermission
         */
        void onDenied(List<String> deniedPermission);

    }

    //动态获取权限监听
    private static PermissionListener mListener;

    //andrpoid 6.0 需要写运行时权限
    public void requestRuntimePermission(String[] permissions, PermissionListener listener) {

        mListener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            mListener.onGranted();
        }
    }

    private void takePhotoForAlbum() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        requestRuntimePermission(permissions, new PermissionListener() {
            @Override
            public void onGranted() {
                openAlbum();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                //没有获取到权限，什么也不执行，看你心情
            }
        });
    }

    private void takePhotoForCamera() {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestRuntimePermission(permissions, new PermissionListener() {
            @Override
            public void onGranted() {
                openCamera();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                //有权限被拒绝，什么也不做好了，看你心情
            }
        });
    }

    /**
     *
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }


    public void setPics() {
        new ActionSheetDialog(mActivity).builder().setCancelable(true).setCanceledOnTouchOutside(true)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Red,
                        which -> {

                            takePhotoForCamera();


//                                SelectHeadTools.startCamearPicCut(mActivity, photoUri);
                        })
                .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue,
                        which -> {
                            takePhotoForAlbum();
//                            if (PermissionUtils.requestReadSDCardPermissions(200))
//                                Crop.pickImage(mActivity);
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

    private String cachPath;
    private File cacheFile;
    private File cameraFile;
    private Uri imageUri;

    private void openCamera() {
        cameraFile = uploadHeadUtil.getCacheFile(new File(getDiskCacheDir(this)), "output_image.jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            imageUri = Uri.fromFile(cameraFile);
        } else {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            com.hldj.hmyg.fileprovider
            imageUri = FileProvider.getUriForFile(mActivity, "com.hldj.hmyg.fileprovider", cameraFile);
        }
        // 启动相机程序
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }

        switch (requestCode) {
            case ConstantState.STORE_OPEN_FAILD://店铺打开失败的时候
                StoreSettingActivity.start2Activity(mActivity);
                break;
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        uploadHeadUtil.startPhotoZoom(cameraFile, 1, cacheFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        uploadHeadUtil.handleImageOnKitKat(data, 1, cacheFile);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        uploadHeadUtil.handleImageBeforeKitKat(data, 1, cacheFile);
                    }
                }
                break;

            case CROP_PHOTO://裁剪成功
                try {
                    if (resultCode == RESULT_OK) {
                        Uri uri = Uri.fromFile(new File(cachPath));
                        File file = FileTools.getFileByUri(this, uri);

                        D.e("===========" + file.length() / 1024);
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(cachPath))));
//                        Drawable drawable = new BitmapDrawable(bitmap);
////                     D.e("=========bitmap======="+bitmap.getByteCount()/1024/1024);
//                        RelativeLayout relativeLayout = getView(R.id.e_background);
//                        relativeLayout.setBackgroundDrawable(drawable);
                        showLoading("头像上传中...");
                        BasePresenter presenter = new EPrestenter()
                                .addResultCallBack(new ResultCallBack<String>() {
                                    @Override
                                    public void onSuccess(String str) {
                                        putSpS("headImage", str);
                                        new Handler().postDelayed(() -> {
                                            bitmap.recycle();
                                            D.e("===str=====" + str);
                                            ImageLoader.getInstance().displayImage(getSpS("headImage"), (ImageView) getView(R.id.iv_circle_head));
                                            hindLoading("上传成功", 1500);
                                        }, 200);
                                    }

                                    @Override
                                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                                        hindLoading("网路错误,上传失败", 2000);
                                    }
                                });

                        ((EPrestenter) presenter).upLoadHeadImg("admin/file/uploadHeadImage", true, cachPath, bitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    hindLoading("上传失败" + e.getMessage(), 2000);
                    D.e("====报错==" + e.getMessage());
                }

                break;
        }


    }


    public void RxRegi() {
        RxBus.getInstance().register(this);
    }

    public void RxUnRegi() {
        RxBus.getInstance().unRegister(this);
    }

    //订阅
    @Subscribe(tag = 5, thread = EventThread.MAIN_THREAD)
    private void dataBinding11(OnlineEvent event) {
        D.e("======Rx=======" + event.toString());
        Observable.just(event)
                .filter(event1 -> event.isOnline)
                .map((Function<OnlineEvent, Object>) event12 -> event12.isOnline)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(500, TimeUnit.MILLISECONDS)
                .subscribe((b) -> {
                    loadHeadImage(getSpB("isLogin"));//加载头像
                    setRealName(getSpS("userName"), getSpS("realName"));
                });

//        Observable.timer(1, TimeUnit.SECONDS)
//                .filter(new Observable<OnlineEvent>() {
//                    @Override
//                    protected void subscribeActual(Observer<? super OnlineEvent> observer) {
//
//                    }
//                })
//                .subscribe(aa->{
//
//        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxUnRegi();
    }

    public static class OnlineEvent {
        boolean isOnline = false;

        public OnlineEvent(boolean isOnline) {
            this.isOnline = isOnline;
        }

        @Override
        public String toString() {
            return "OnlineEvent{" +
                    "isOnline=" + isOnline +
                    '}';
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {
        setRealName(getSpS("userName"), getSpS("realName"));
    }


}

package com.hldj.hmyg.Ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Keep;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.FeedBackActivity;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.M.userIdentity.enums.UserIdentityStatus;
import com.hldj.hmyg.MainActivity;
import com.hldj.hmyg.ManagerListActivity_new;
import com.hldj.hmyg.MessageListActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.SafeAcountActivity;
import com.hldj.hmyg.SetProfileActivity;
import com.hldj.hmyg.SettingActivity;
import com.hldj.hmyg.StoreActivity;
import com.hldj.hmyg.Ui.friend.child.CenterActivity;
import com.hldj.hmyg.Ui.jimiao.MiaoNoteListActivity;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.application.StateBarUtil;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.base.rxbus.annotation.Subscribe;
import com.hldj.hmyg.base.rxbus.event.EventThread;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.presenter.AActivityPresenter;
import com.hldj.hmyg.presenter.EPrestenter;
import com.hldj.hmyg.saler.AdressManagerActivity;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.StoreSettingActivity;
import com.hldj.hmyg.saler.Ui.ManagerQuoteListActivity_new;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.util.RippleAdjuster;
import com.hldj.hmyg.util.UploadHeadUtil;
import com.hldj.hmyg.widget.BounceScrollView;
import com.hldj.hmyg.widget.ShareDialogFragment;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;
import com.lqr.optionitemview.OptionItemView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zf.iosdialog.widget.ActionSheetDialog;
import com.zf.iosdialog.widget.AlertDialog;
import com.zym.selecthead.tools.FileTools;

import net.tsz.afinal.http.AjaxCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static com.hldj.hmyg.R.id.sptv_wd_gys;
import static com.hldj.hmyg.R.id.sptv_wd_sfz;
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

    public static boolean showSeedlingNoteShare = false;//是否显示 共享资源
    public static boolean showSeedlingNoteTeam = false;//是否显示 团队共享
    private boolean isQuote;/*是否报价权限  true  ----   不需要读取 网页 http://192.168.1.252:8090/app/protocol/supplier*/
    private String userIdentity = UserIdentityStatus.unaudited.enumValue;//auditing  默认 未认证 状态
    private int authStatue = AuthenticationActivity.no_auth;


    BounceScrollView alfa_scroll;

//    FinalBitmap finalBitmap;


    public float distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_activity_3_0);

        uploadHeadUtil = new UploadHeadUtil(mActivity);

        cachPath = UploadHeadUtil.getDiskCacheDir(this) + "/handimg.jpg";//图片路径
        cacheFile = uploadHeadUtil.getCacheFile(new File(getDiskCacheDir(this)), "handimg.jpg");

//        finalBitmap = FinalBitmap.create(mActivity);
//        finalBitmap.configLoadfailImage(R.drawable.icon_persion_pic);
//        finalBitmap.configLoadingImage(R.drawable.icon_persion_pic);

//        StatusBarUtil.setColor(MainActivity.instance, Color.GREEN);

//        StateBarUtil.setStatusBarIconDark(MainActivity.instance, true);
//        StateBarUtil.setStatusTranslaterNoFullStatus(MainActivity.instance, false);

//        if (isDark) {
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//
//            } else {
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//
//            }


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
        BounceScrollView alfa_scroll = (BounceScrollView) findViewById(R.id.alfa_scroll);
        TextView tip = (TextView) findViewById(R.id.tip);
        alfa_scroll.onMoveListener = new BounceScrollView.OnMoveListener() {
            @Override
            public void onMove(float preY, float deltaY, float y) {


                Log.i("onMove", "y abs  ----->" + (y - preY));


                if (curStar == Math.abs(deltaY) < MyApplication.dp2px(mActivity, 65)) {
                    return;
                }

                setStatues(Math.abs(deltaY) < MyApplication.dp2px(mActivity, 65));

                distance += y - preY;


                // I/onMove: y ----->736.61633
                //  y ----->738.6153

            }

            @Override
            public void onUp() {
                Log.i("onUp", "getHeight y " + tip.getHeight());
                Log.i("onUp", "getMeasuredHeight y " + tip.getMeasuredHeight());
                Log.i("onUp", "distance y " + distance);
                Log.i("onUp", " content padding top  " + ll_me_content.getPaddingTop());
//                Log.i("onUp", " content margin top  " + (G)ll_me_content.getLayoutParams());
                setStatues(false);
            }
        };
        Log.i("onUp", "tip y " + tip.getHeight());

        if (!GetServerUrl.isTest) {
            alfa_scroll.setBackgroundColor(Color.WHITE);
        }
//        ViewGroup viewGroup = (ViewGroup) alfa_scroll.getChildAt(0);
//        viewGroup.addView(new Button(mActivity),0);

        /**
         * 为所有suptertextivew 设置点击效果
         */
        adjuster = new RippleAdjuster(Color.argb(10, 1, 1, 1));
        addAdjuster(ll_me_content);

        loadHeadImage(getSpB("isLogin"));//加载头像
        setRealName(getSpS("userName"), getSpS("realName"));

        this.getView(R.id.sptv_wd_mmgl).setOnClickListener(v -> ManagerListActivity_new.start2Activity(mActivity));//苗木管理
        this.getView(R.id.sptv_wd_bjgl).setOnClickListener(v -> ManagerQuoteListActivity_new.start2Activity(mActivity));//报价管理
        D.e("======商店id======" + MyApplication.getUserBean().storeId);
        this.getView(R.id.sptv_wd_wddp).setOnClickListener(v -> StoreActivity.start2ActivityForRsl(mActivity, MyApplication.getUserBean().storeId));//我的店铺
        this.getView(R.id.sptv_wd_dpsz).setOnClickListener(v -> StoreSettingActivity.start2Activity(mActivity));//店铺设置
        this.getView(R.id.sptv_wd_zhaq).setOnClickListener(v -> SafeAcountActivity.start2Activity(mActivity));//账户安全
        this.getView(R.id.sptv_wd_mydz).setOnClickListener(v -> AdressManagerActivity.start2Activity(mActivity));//苗源地址管理
        this.getView(R.id.sptv_wd_fxapp).setOnClickListener(v -> ShareDialogFragment.newInstance().show(getSupportFragmentManager(), getClass().getName()));//分享 app
        this.getView(R.id.sptv_wd_kf).setOnClickListener(v -> Call_Phone()); // 客服
        this.getView(R.id.sptv_wd_yhfk).setOnClickListener(v -> FeedBackActivity.start2Activity(mActivity));//反馈
        this.getView(R.id.sptv_wd_bjzl).setOnClickListener(v -> SetProfileActivity.start2ActivitySet(mActivity, 100));//编辑资料
        this.getView(R.id.sptv_wd_wdxm).setOnClickListener(v -> MyProgramActivity.start(mActivity));//我的项目
        this.getView(R.id.sptv_wd_exit).setOnClickListener(v -> exit());//退出登录
        this.getView(R.id.sptv_wd_ddzy).setOnClickListener(v -> DispatcherActivity.start(mActivity));// 调度专员
        this.getView(R.id.sptv_wd_mmq).setOnClickListener(v -> CenterActivity.start(mActivity, MyApplication.getUserBean().id));//  我的苗木圈
        this.getView(R.id.sptv_wd_jmb).setOnClickListener(v -> MiaoNoteListActivity.start(mActivity));// 记苗本

        this.getView(R.id.sptv_wd_jf).setOnClickListener(v -> IntegralActivity.start(mActivity));//  积分
        this.getView(sptv_wd_gys).setOnClickListener(v -> ProviderActivity.start(mActivity, isQuote));//  供应商
        this.getView(sptv_wd_sfz).setOnClickListener(v -> AuthenticationActivity.start(mActivity, authStatue, "审核不通过原因：身份证不清晰，请重新上传"));//  身份认证


        this.getView(R.id.iv_circle_head).setOnClickListener(v -> {
            setPics();
        });//点击弹窗选择拍照 或者 相册  上传图片


        OptionItemView optionItemView = this.getView(R.id.top_bar_option); // 这是title 左右边的点击事件

        if (optionItemView.isSelected()) {
            optionItemView.setRightImage(BitmapFactory.decodeResource(getResources(), R.mipmap.wd_xx_small));
            optionItemView.setSelected(!optionItemView.isSelected());
        } else {
            optionItemView.setRightImage(BitmapFactory.decodeResource(getResources(), R.mipmap.wd_xx));
            optionItemView.setSelected(!optionItemView.isSelected());
        }


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


        //第一次 时 显示 显示项目。需要重启引用
//        isShowProject();

    }

    private void exit() {
        new AlertDialog(mActivity).builder()
                .setTitle("确定退出登录?")
                .setPositiveButton("退出登录", v1 -> {
                    SettingActivity.exit2Home
                            (mActivity, MyApplication.Userinfo.edit(), false);
                }).setNegativeButton("取消", v2 -> {
        }).show();
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


        // 地区   福建省 厦门市 思明区

        String city = getSpS("ciCityFullName");
//        String city = getSpS("coCityfullName");

        if (TextUtils.isEmpty(city)) {
            ((TextView) getView(R.id.wd_city)).setText("未设置城市");
        } else {
            ((TextView) getView(R.id.wd_city)).setText(getSpS("ciCityFullName"));
        }


    }

    String headImg;


    private void loadHeadImage(boolean isLogin) {
        if (isLogin)
            if (TextUtils.isEmpty(getSpS("headImage"))) {
                D.e("====空头像，不加载===");
                ((ImageView) getView(R.id.iv_circle_head)).setImageResource(R.drawable.icon_persion_pic);
                return;
            }
//        finalBitmap.display((ImageView) getView(R.id.iv_circle_head),getSpS("headImage"));


        ImageLoader.getInstance().displayImage(getSpS("headImage"), (ImageView) getView(R.id.iv_circle_head), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                D.e("");
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                D.e("");
                ((ImageView) getView(R.id.iv_circle_head)).setImageResource(R.drawable.icon_persion_pic);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                D.e("");
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                D.e("");
            }
        });
//            ImageLoader.getInstance().displayImage(str, (ImageView) getView(R.id.iv_circle_head));
    }

    /**
     * .showAnim(mBasIn)//
     * .dismissAnim(mBasOut)//
     */

    public void Call_Phone() {


        new ActionSheetDialog(mActivity).builder().setCancelable(true).setCanceledOnTouchOutside(true)
                .addSheetItem("服务专线", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Log.i("title", "onClick: ");
                    }
                })
                .addSheetItem("供苗专线: 158-6012-8888 卢经理", ActionSheetDialog.SheetItemColor.Blue,
                        which -> {

                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:15860128888"));
                            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            startActivity(intent);


//                                SelectHeadTools.startCamearPicCut(mActivity, photoUri);
                        })
                .addSheetItem("购苗专线: 158-6013-8888 叶经理", ActionSheetDialog.SheetItemColor.Blue,
                        which -> {

                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel::15860138888"));
                            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            startActivity(intent);

//                            if (PermissionUtils.requestReadSDCardPermissions(200))
//                                Crop.pickImage(mActivity);
                        })


                .show();


//        final com.flyco.dialog.widget.MaterialDialog dialog = new com.flyco.dialog.widget.MaterialDialog(
//                mActivity);
//        dialog.title("客服热线4006-579-888").content("客服热线 周一至周日9:00-18:00")//
//                .btnText("确认拨号", "取消")//
//                .show();
//        dialog.setOnBtnClickL(() -> {
//            dialog.dismiss();
//            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + GetServerUrl.Customer_Care_Phone));
//            if (ActivityCompat.checkSelfPermission(mActivity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            startActivity(intent);
//        }, () -> dialog.dismiss());
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


    public static interface PermissionListener {
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
        cameraFile = uploadHeadUtil.getCacheFile(new File(getDiskCacheDir(this)), "output_image1.jpg");
//        if (cameraFile.exists()) {
//            cameraFile.delete();
//            cameraFile.mkdir();
//        }

        D.e("cameraFile size" + cameraFile.length());
//        cacheFile = uploadHeadUtil.getCacheFile(new File(getDiskCacheDir(this)), "handimg.jpg");
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
        D.e("size " + requestCode + " resultCode " + resultCode + " data=" + data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }

        switch (requestCode) {
            case ConstantState.STORE_OPEN_FAILD://店铺打开失败的时候
                StoreSettingActivity.start2Activity(mActivity);
                break;
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {

                    D.e("cameraFile size" + cameraFile.length());


                    D.e("cameraFile size" + cameraFile.length());
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
//                                            ImageLoader.getInstance().displayImage(getSpS("headImage"), (ImageView) getView(R.id.iv_circle_head));
                                            hindLoading("上传成功", 1500);
                                            loadHeadImage(true);

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
    @Keep
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
        public boolean isOnline = false;

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

        setStatues(false);

        OptionItemView optionItemView = this.getView(R.id.top_bar_option); // 这是title 左右边的点击事件
        if (AActivityPresenter.isShowRead) {
            optionItemView.setRightImage(BitmapFactory.decodeResource(getResources(), R.mipmap.wd_xx_small));
        } else {
            optionItemView.setRightImage(BitmapFactory.decodeResource(getResources(), R.mipmap.wd_xx));
        }
    }


    public void refresh() {
        setRealName(getSpS("userName"), getSpS("realName"));
        loadHeadImage(getSpB("isLogin"));
        isShowProject();
    }


    /**
     * 项目 显示关闭开关
     */
    public void isShowProject() {
        new BasePresenter()
                .doRequest("admin/user/getPermission", true, new AjaxCallBack<String>() {


                    @Override
                    public void onSuccess(String json) {
                        Log.i("=======", "onSuccess: " + json);

                        /**
                         * {"code":"1","msg":"操作成功",
                         * "data":{"agentGrade":"level1","isQuote":true,"userPoint":705,"agentGradeText":
                         * "合作供应商",
                         * "userIdentity":"auditing",
                         * "showSeedlingNoteShare":true,"showSeedlingNote":true,"hasProjectManage":true},"version":"tomcat7.0.53"}
                         */


//                        if (GetServerUrl.isTest)//测试的时候显示
//                            ToastUtil.showShortToast("测试的时候显示\n" + "请求是否显示项目结果：\n" + json);
                        SimpleGsonBean bean = GsonUtil.formateJson2Bean(json, SimpleGsonBean.class);
                        if (bean.isSucceed()) {
//                            GetServerUrl.isTest
                            if (false) {
                                getView(R.id.sptv_wd_wdxm).setVisibility(View.VISIBLE);
                            } else {
                                getView(R.id.sptv_wd_wdxm).setVisibility(bean.getData().hasProjectManage ? View.VISIBLE : View.GONE);
                            }

                            if (false) {
                                getView(R.id.sptv_wd_jmb).setVisibility(View.VISIBLE);
                            } else {
                                getView(R.id.sptv_wd_jmb).setVisibility(bean.getData().showSeedlingNote ? View.VISIBLE : View.GONE);
                            }
                            showSeedlingNoteShare = bean.getData().showSeedlingNoteShare;
                            isQuote = bean.getData().isQuote;
                            userIdentity = bean.getData().userIdentityStatus;
                            ProcessUserIdentity(getView(R.id.sptv_wd_sfz), userIdentity);
//                            showSeedlingNoteTeam = !showSeedlingNoteTeam;
//                            showSeedlingNoteShare = false;
                            D.e("===========showSeedlingNoteShare===========" + showSeedlingNoteShare);

                            /* 积分   与 供应商 等级   */
                            checkGys_Point(bean.getData().agentGrade, bean.getData().userPoint, bean.getData().agentGradeText, isQuote);


                        } else {
                            ToastUtil.showLongToast(bean.msg);

                        }
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        ToastUtil.showLongToast("权限请求接口请求失败：" + strMsg);
                    }
                });
    }


    /* 处理身份 认证 状态 */
    private void ProcessUserIdentity(SuperTextView textView, String identityState) {
//  private int authStatue = AuthenticationActivity.no_auth ;

        if (UserIdentityStatus.unaudited.enumValue.equals(identityState)) {
            textView.setText(UserIdentityStatus.unaudited.enumText);  /* 未认证 */
            authStatue = AuthenticationActivity.no_auth;
            textView.setDrawable(getResources().getDrawable(R.mipmap.wd_smrz));
        } else if (UserIdentityStatus.auditing.enumValue.equals(identityState)) {
            textView.setText(UserIdentityStatus.auditing.enumText);  /* 认证中 */
            authStatue = AuthenticationActivity.authing;
            textView.setDrawable(getResources().getDrawable(R.mipmap.wd_smrz));
        } else if (UserIdentityStatus.pass.enumValue.equals(identityState)) {
            textView.setText(UserIdentityStatus.pass.enumText);  /* 认证通过 */
            textView.setDrawable(getResources().getDrawable(R.mipmap.wd_smrz_tg));
            authStatue = AuthenticationActivity.succeed;
        } else if (UserIdentityStatus.back.enumValue.equals(identityState)) {
            textView.setText(UserIdentityStatus.back.enumText);  /* 认证失败 */
            textView.setDrawable(getResources().getDrawable(R.mipmap.wd_smrz_wtg));
            authStatue = AuthenticationActivity.failed;
        }

    }


    /**
     * agentGrade":"level1","userPoint":14,"agentGradeText":"普通供应商
     *
     * @param agentGrade     供应商等级
     * @param userPoint      积分
     * @param agentGradeText 供应商名称
     * @param quote
     */
    private void checkGys_Point(String agentGrade, String userPoint, String agentGradeText, boolean quote) {

        TextView sptv_wd_jf = getView(R.id.sptv_wd_jf);
        SuperTextView sptv_wd_gys = getView(R.id.sptv_wd_gys);
        sptv_wd_jf.setText("积分" + userPoint);

        if (quote) {
            sptv_wd_gys.setText(agentGradeText);
        }
        Drawable drawable = null;
        /// 这一步必须要做,否则不会显示.
        //:{agentGrade:"level1","isQuote":true,"userPoint":421,"agentGradeText":"普通供应商","showS
        sptv_wd_gys.setTextColor(ContextCompat.getColor(mActivity, R.color.text_color666));
        if (!quote) {
            drawable = getResources().getDrawable(R.mipmap.wd_gys_no);
            sptv_wd_gys.setText("申请成为供应商");
            sptv_wd_gys.setTextColor(ContextCompat.getColor(mActivity, R.color.main_color));
        } else if ("level1".equals(agentGrade)) {
            drawable = getResources().getDrawable(R.mipmap.wd_gys_lv1);

        } else if ("level2".equals(agentGrade)) {
            drawable = getResources().getDrawable(R.mipmap.wd_gys_lv2);
        } else if ("level3".equals(agentGrade)) {
            drawable = getResources().getDrawable(R.mipmap.wd_gys_lv3);
        } else if ("level4".equals(agentGrade)) {
            drawable = getResources().getDrawable(R.mipmap.wd_gys_lv4);
        } else if ("level5".equals(agentGrade)) {
            drawable = getResources().getDrawable(R.mipmap.wd_gys_lv5);
        } else {
            drawable = getResources().getDrawable(R.mipmap.wd_gys_no);
            sptv_wd_gys.setText("申请成为供应商");
        }

        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        sptv_wd_gys.setCompoundDrawables(drawable, null, null, null);
        sptv_wd_gys.setDrawable(drawable);


    }


    public boolean curStar = true;

    private void setStatues(boolean isDark) {
//        StatusBarUtil.setColor(MainActivity.instance, Color.TRANSPARENT);
//        mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//        View deco = mActivity.getWindow().getDecorView();
//        deco.setPadding(0, 50, 0, 0);

//        ToastUtil.showPointAdd("每日登陆", "获得10积分");

//        StatusBarUtil.setColor(MainActivity.instance, Color.TRANSPARENT);
//        StatusBarUtil.setTranslucent(MainActivity.instance, 0);
//        StatusBarUtil.setColor(MainActivity.instance, ContextCompat.getColor(mActivity, R.color.main_color));

//        if (curStar == isDark) {
//            return;
//        }
        curStar = isDark;
        try {
            StateBarUtil.setStatusTranslater(MainActivity.instance, isDark);
//        StateBarUtil.setStatusTranslater(MainActivity.instance, false);
            StateBarUtil.setStatusTranslater(mActivity, isDark);
            StateBarUtil.setMiuiStatusBarDarkMode(MainActivity.instance, isDark);
            StateBarUtil.setMeizuStatusBarDarkIcon(MainActivity.instance, isDark);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

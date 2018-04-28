package com.hldj.hmyg.Ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.M.userIdentity.UserIdentity;
import com.hldj.hmyg.M.userIdentity.enums.UserIdentityStatus;
import com.hldj.hmyg.MyLuban.MyLuban;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.util.UploadHeadUtil;
import com.hldj.hmyg.widget.MyImageViewShowUserCard;
import com.hldj.hmyg.widget.StepView;
import com.hy.utils.ToastUtil;
import com.zf.iosdialog.widget.ActionSheetDialog;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.bitmap.core.BitmapDisplayConfig;
import net.tsz.afinal.bitmap.display.Displayer;
import net.tsz.afinal.bitmap.display.SimpleDisplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.hldj.hmyg.R.id.et_card_num;
import static com.hldj.hmyg.R.id.et_name;
import static com.hldj.hmyg.util.UploadHeadUtil.CHOOSE_PHOTO;
import static com.hldj.hmyg.util.UploadHeadUtil.TAKE_PHOTO;
import static com.hldj.hmyg.util.UploadHeadUtil.getDiskCacheDir;

/**
 * 实名认证界面
 */

public class AuthenticationActivity extends BaseMVPActivity {

    public static final int no_auth = 1;/* 未认证  */
    public static final int succeed = 3; /*认证成功 */
    public static final int authing = 2;/*认证中*/
    public static final int failed = -1;/*认证失败*/


    MyImageViewShowUserCard iv_zheng;
    MyImageViewShowUserCard iv_fan;
    private View btn_save;
    /**/
    private String currentType = "";
    private static final String zheng = "zheng";
    private static final String fan = "fan";
   /**/

    private String cachPath;
    private File cacheFile;
    private File cameraFile;
    private File cameraFile1;
    private Uri imageUri;

    UploadHeadUtil uploadHeadUtil;
    private FinalBitmap finalBitmap;


    @Override
    public String setTitle() {

        return "实名认证";

    }

    private String getRealName() {
        return getText(getView(R.id.et_name));
    }

    private String getIdentityNum() {
        return getText(getView(R.id.et_card_num));
    }


    private int uploadImageCount = 0;
    private UserIdentity userIdentity;

    /* 请求  网络数据     认证的  各种数据 */
    private void requestData() {
        new BasePresenter()
                .putParams("", "")
                .doRequest("admin/userIdentity/getUserIdentity", true, new HandlerAjaxCallBack(mActivity) {


                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {

                        /* 获取 审核数据成功  */

                        D.i("onRealSuccess: " + gsonBean.getData().userIdentity.toString());

                        userIdentity = gsonBean.getData().userIdentity;

                        initExtras();


                    }
                });


    }

    @Override
    public void initView() {
        finalBitmap = FinalBitmap.create(mActivity);

        uploadHeadUtil = new UploadHeadUtil(mActivity);
        cachPath = getDiskCacheDir(this) + "/handimg.jpg";//图片路径
        cacheFile = uploadHeadUtil.getCacheFile(new File(getDiskCacheDir(this)), "handimg.jpg");

        iv_zheng = getView(R.id.iv_zheng);
        iv_zheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentType = zheng;
                choosePics();
            }
        });


        iv_fan = getView(R.id.iv_fan);
        iv_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentType = fan;
                choosePics();
            }
        });

        initExtras();
        requestData();


        btn_save = getView(R.id.btn_save);


        btn_save.setOnClickListener(v -> {
            totalCount = 0;


            if (getAuthingState() == failed) {
                // 上传失败  处理
                // 重一张图片
                // 重传2 张图片
                // 直接再次提交

                showLoading();
                setLoadCancle(false);
                upImageAgain(iv_zheng, iv_fan);


            } else {


                if (iv_zheng.getTag() != null && iv_zheng.getTag() instanceof File) {

//                if (iv_zheng.getTag() instanceof String) {
//                    ossUrls.add(new Pic("", false, iv_zheng.getTag() + "", 0));
//                } else {
//                    ossUrls.add(new Pic("", false, ((File) iv_zheng.getTag()).getAbsolutePath(), 0));
//                }
                    File file = ((File) iv_zheng.getTag());
                    D.i("========zheng path===========" + file.getAbsolutePath());


                } else {
                    ToastUtil.showLongToast("正面图片未提交");
                    return;
                }

                if (iv_fan.getTag() != null && iv_fan.getTag() instanceof File) {
                    File file = ((File) iv_fan.getTag());
                    D.i("========fan path===========" + file.getAbsolutePath());
                } else {
                    ToastUtil.showLongToast("背面图片未提交");
                    return;
                }

                showLoading();
                D.i("========et_name=========" + getText(getView(et_name)));
                D.i("========et_card_num=========" + getText(getView(R.id.et_card_num)));
                upDoubleImages();

            }


        });


    }

    private void upDoubleImages() {

        ansyUploadImage(new UploadListener() {
            @Override
            public void uploadSucceed(Pic pic) {

                totalCount++;

                if (totalCount == 1) {

                    ArrayList<Pic> arrayList = new ArrayList<>();
                    arrayList.add(pic);

                    if (pic.getSort() == 0) {
                        pic_json1 = GsonUtil.Bean2Json(arrayList);
                    } else {
                        pic_json2 = GsonUtil.Bean2Json(arrayList);
                    }

                } else {
                    ArrayList<Pic> arrayList = new ArrayList<>();
                    arrayList.add(pic);

                    if (pic.getSort() == 0) {
                        pic_json1 = GsonUtil.Bean2Json(arrayList);
                    } else {
                        pic_json2 = GsonUtil.Bean2Json(arrayList);
                    }
//                    pic_json2 = GsonUtil.Bean2Json(arrayList);
                }

                if (totalCount == 2) {
                    /* 上传成功  上传数据  */

                    ToastUtil.showLongToast("上传成功");
                    Log.i("ansyUploadImage", "uploadSucceed: pic_json1" + pic_json1);
                    Log.i("ansyUploadImage", "uploadSucceed: pic_json2" + pic_json2);

                        /*  保存操作 */

                    D.i("------------保存操作-----------");


                    new BasePresenter()
                            .putParams(ConstantParams.id, userIdentity.id)
                            .putParams(ConstantParams.realName, getRealName())
                            .putParams(ConstantParams.identityNum, getIdentityNum())
                            .putParams(ConstantParams.frontData, pic_json1)
                            .putParams(ConstantParams.backData, pic_json2)
                            .doRequest("admin/userIdentity/save", true, new HandlerAjaxCallBack(mActivity) {
                                @Override
                                public void onRealSuccess(SimpleGsonBean gsonBean) {

                                    ToastUtil.showLongToast(gsonBean.msg);
                                    finish();

                                }
                            });
                }


            }

            @Override
            public void uploadFaild(String msg) {

                ToastUtil.showLongToast("上传失败" + msg);
                totalCount = 0;
            }
        }, ((File) iv_zheng.getTag()), ((File) iv_fan.getTag()));

    }

    private void upOneImage(File f, int type, UploadListener listener) {

        if (f == null) {
            listener.uploadSucceed(null);
            return;
        }

        ArrayList<File> arrayList = new ArrayList<>();
        arrayList.add(f);
            /* zheng */
        MyLuban.compress(mActivity, arrayList)
                .setMaxSize(256)
                .setMaxHeight((int) (1920))
                .setMaxWidth((int) (1080))
                .putGear(MyLuban.CUSTOM_GEAR)
                .asListObservable() // 压缩代码，返回  List<File>
                .flatMap(new Function<List<File>, ObservableSource<File>>() {
                    @Override
                    public ObservableSource<File> apply(@NonNull List<File> files) throws Exception {
                        return Observable.fromIterable(files);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(@NonNull File file) throws Exception {

                        new BasePresenter()
                                .putFile("file", file)
                                .doRequest("admin/file/image", true, new HandlerAjaxCallBack(mActivity) {
                                    @Override
                                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                                        if (gsonBean.getData().image == null) {
                                        } else {
                                            if (gsonBean.isSucceed()) {
                                                listener.uploadSucceed(new Pic(gsonBean.getData().image.id, true, gsonBean.getData().image.url, type));
                                            }
                                        }

                                    }

                                    @Override
                                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                                        super.onFailure(t, errorNo, strMsg);
                                        listener.uploadFaild(strMsg);
                                    }
                                });


                    }
                }, throwable -> {
                    listener.uploadFaild("上传失败，请重新提交");
                });

    }

    private void upImageAgain(MyImageViewShowUserCard iv_zheng, MyImageViewShowUserCard iv_fan) {

        if (iv_zheng.getTag() != null && iv_zheng.getTag() instanceof File && iv_fan.getTag() != null && iv_fan.getTag() instanceof File) {
            /* 表示  正面 反面 重新上传了 照片 */
            /* 走双面上传流程 */
            upDoubleImages();

        } else if (iv_zheng.getTag() != null && iv_zheng.getTag() instanceof File) {
            /* 表示  正面重新上传了 照片 */

            upOne(((File) iv_zheng.getTag()), 0);

        } else if (iv_fan.getTag() != null && iv_fan.getTag() instanceof File) {
            /* 表示  背面重新上传了 照片 */
            upOne(((File) iv_fan.getTag()), 1);

        } else {
//            ToastUtil.showLongToast("请重新提交认证照片");
            upOne(null, -1);

        }

    }

    private void upOne(File upFile, int type) {


        upOneImage(upFile, type, new UploadListener() {
            @Override
            public void uploadSucceed(Pic pic) {

                if (type == 0) {
                    ArrayList<Pic> arrayList = new ArrayList<>();
                    arrayList.add(pic);
                    pic_json1 = GsonUtil.Bean2Json(arrayList);
                    arrayList.clear();
                    arrayList.add(new Pic(userIdentity.backImageJson.id, true, userIdentity.backImageJson.url, 1));
                    pic_json2 = GsonUtil.Bean2Json(arrayList);
                } else if (type == 1) {
                    ArrayList<Pic> arrayList = new ArrayList<>();
                    arrayList.add(pic);
                    pic_json2 = GsonUtil.Bean2Json(arrayList);
                    arrayList.clear();
                    arrayList.add(new Pic(userIdentity.backImageJson.id, true, userIdentity.frontImageJson.url, 1));
                    pic_json1 = GsonUtil.Bean2Json(arrayList);
                } else {
                    ArrayList<Pic> arrayList = new ArrayList<>();
                    arrayList.add(new Pic(userIdentity.frontImageJson.id, true, userIdentity.frontImageJson.url, 0));
                    pic_json1 = GsonUtil.Bean2Json(arrayList);
                    arrayList.clear();
                    arrayList.add(new Pic(userIdentity.backImageJson.id, true, userIdentity.backImageJson.url, 1));
                    pic_json2 = GsonUtil.Bean2Json(arrayList);
                }

                new BasePresenter()
                        .putParams(ConstantParams.id, userIdentity.id)
                        .putParams(ConstantParams.realName, getRealName())
                        .putParams(ConstantParams.identityNum, getIdentityNum())
                        .putParams(ConstantParams.frontData, pic_json1)
                        .putParams(ConstantParams.backData, pic_json2)
                        .doRequest("admin/userIdentity/save", true, new HandlerAjaxCallBack(mActivity) {
                            @Override
                            public void onRealSuccess(SimpleGsonBean gsonBean) {

                                ToastUtil.showLongToast(gsonBean.msg);
                                hindLoading();
                                finish();

                            }
                        });
            }

            @Override
            public void uploadFaild(String msg) {

            }
        });


    }


    private String pic_json1;
    private String pic_json2;
    private int totalCount = 0;


    /* 失败重新上传 问题   */

    private void ansyUploadImage(UploadListener uploadListener, File file1, File file2) {

//        MyLuban.compress(mActivity, file1)
//                .setMaxSize(256)
//                .setMaxHeight((int) (1920))
//                .setMaxWidth((int) (1080))
//                .putGear(MyLuban.CUSTOM_GEAR)
//                .asObservable() // 压缩代码，返回  List<File>
//                .subscribe(file -> {
//                    new BasePresenter()
//                            .putFile("file", file)
//                            .doRequest("admin/file/image", true, new HandlerAjaxCallBack() {
//                                @Override
//                                public void onRealSuccess(SimpleGsonBean gsonBean) {
//                                    if (gsonBean.getData().image == null) {
//                                    } else {
//                                        if (gsonBean.isSucceed()) {
//                                            uploadListener.uploadSucceed(new Pic(gsonBean.getData().image.id, true, gsonBean.getData().image.url, 0));
//                                        }
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Throwable t, int errorNo, String strMsg) {
//                                    super.onFailure(t, errorNo, strMsg);
//                                    uploadListener.uploadFaild(strMsg);
//                                }
//                            });
//
//
//                }, throwable -> {
//                    uploadListener.uploadFaild(throwable.getMessage());
//                });

        List<File> list = new ArrayList();
        list.add(file1);
        list.add(file2);
        compress2Upload2Lists(uploadListener, list);
//        compress2Upload(uploadListener, file1, 0);
//        compress2Upload(uploadListener, file2, 1);


//        MyLuban.compress(mActivity, file1)
//                .setMaxSize(256)
//                .setMaxHeight((int) (1920))
//                .setMaxWidth((int) (1080))
//                .putGear(MyLuban.CUSTOM_GEAR)
//                .asObservable() // 压缩代码，返回  List<File>
//                .subscribe(file -> {
//                    new BasePresenter()
//                            .putFile("file", file)
//                            .doRequest("admin/file/image", true, new HandlerAjaxCallBack() {
//                                @Override
//                                public void onRealSuccess(SimpleGsonBean gsonBean) {
//                                    if (gsonBean.getData().image == null) {
//                                    } else {
//                                        if (gsonBean.isSucceed()) {
//                                            uploadListener.uploadSucceed(new Pic(gsonBean.getData().image.id, true, gsonBean.getData().image.url, 1));
//                                        }
//                                    }
//
//                                }
//
//                                @Override
//                                public void onFailure(Throwable t, int errorNo, String strMsg) {
//                                    super.onFailure(t, errorNo, strMsg);
//                                    uploadListener.uploadFaild(strMsg);
//                                }
//                            });
//                }, throwable -> {
//                    uploadListener.uploadFaild(throwable.getMessage());
//                });


    }


    public int curSort = 0;

    private void compress2Upload2Lists(UploadListener uploadListener, List<File> lists) {
        Log.i("开始上传", "compress2Upload: " + lists.get(0).getAbsolutePath() + "  sort = " + 0);
        Log.i("开始上传", "compress2Upload: " + lists.get(1).getAbsolutePath() + "  sort = " + 1);
        curSort = 0;

//        List<File> files = new ArrayList<>();
//        files.add(fileBig,);
        MyLuban.compress(mActivity, lists)
                .setMaxSize(256)
                .setMaxHeight((int) (1920))
                .setMaxWidth((int) (1080))
                .putGear(MyLuban.CUSTOM_GEAR)
                .asListObservable() // 压缩代码，返回  List<File>
                .flatMap(new Function<List<File>, ObservableSource<File>>() {
                    @Override
                    public ObservableSource<File> apply(@NonNull List<File> files) throws Exception {
                        return Observable.fromIterable(files);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(@NonNull File file) throws Exception {
                        upload2Online(uploadListener, file, curSort++);
                    }
                }, throwable -> {
                    uploadListener.uploadFaild(throwable.getMessage());
                });


    }


    private void upload2Online(UploadListener uploadListener, File compressFile, int sort) {

        new BasePresenter()
                .putFile("file", compressFile)
                .doRequest("admin/file/image", true, new HandlerAjaxCallBack() {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        if (gsonBean.getData().image == null) {
                        } else {
                            if (gsonBean.isSucceed()) {
                                uploadListener.uploadSucceed(new Pic(gsonBean.getData().image.id, true, gsonBean.getData().image.url, sort));
                            }
                        }
                    }

                    @Override
                    public void onLoading(long count, long current) {
                        Log.i("onLoading", "onLoading: \n" + current + "/" + count);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                        uploadListener.uploadFaild(strMsg);
                    }
                });
    }


    private void compress2Upload(UploadListener uploadListener, File fileBig, int sort) {
        Log.i("开始上传", "compress2Upload: " + fileBig.getAbsolutePath() + "  sort = " + sort);

//        List<File> files = new ArrayList<>();
//        files.add(fileBig,);
        MyLuban.compress(mActivity, fileBig)
                .setMaxSize(256)
                .setMaxHeight((int) (1920))
                .setMaxWidth((int) (1080))
                .putGear(MyLuban.CUSTOM_GEAR)
                .asObservable() // 压缩代码，返回  List<File>
                .subscribe(file -> new BasePresenter()
                        .putFile("file", file)
                        .doRequest("admin/file/image", true, new HandlerAjaxCallBack() {
                            @Override
                            public void onRealSuccess(SimpleGsonBean gsonBean) {


                                if (gsonBean.getData().image == null) {
                                } else {
                                    if (gsonBean.isSucceed()) {
                                        uploadListener.uploadSucceed(new Pic(gsonBean.getData().image.id, true, gsonBean.getData().image.url, sort));
                                    }
                                }


                            }

                            @Override
                            public void onFailure(Throwable t, int errorNo, String strMsg) {
                                super.onFailure(t, errorNo, strMsg);
                                uploadListener.uploadFaild(strMsg);
                            }
                        }), throwable -> {
                    uploadListener.uploadFaild(throwable.getMessage());
                });


    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_authentication;
    }


    /* 对状态进行处理 */
    private void initExtras() {
        if (userIdentity == null) {
            D.w("初始化失败");
            return;
        }

        http_zheng = userIdentity.frontImageUrl;
        http_fan = userIdentity.backImageUrl;


        TextView tv_top_tip = getView(R.id.tv_top_tip);

        StepView stepView = getView(R.id.step_view);

        if (!TextUtils.isEmpty(getFailedMsg()) && getAuthingState() == failed) {
            tv_top_tip.setVisibility(View.VISIBLE);
        } else {
            tv_top_tip.setVisibility(View.GONE);
        }
        tv_top_tip.setText(getFailedMsg());
        ImageView imageView = getView(R.id.iv_auth_states);


        switch (getAuthingState()) {
            case no_auth:/* 未认证 --  发起认证 */

                doNoAuth();
                break;

            case succeed:/*认证成功 */
                imageView.setImageResource(R.mipmap.auth_shtg);
                imageView.setVisibility(View.VISIBLE);
                doAuthing();
                break;

            case authing:/*认证中*/
                imageView.setImageResource(R.mipmap.auth_shz);
                imageView.setVisibility(View.VISIBLE);
            /*审核中 */

                doAuthing();

                break;

            case failed:
                 /*认证失败*/

                doAuthFailed();


                break;

        }

        stepView.setStep(getAuthingState());

        if (getAuthingState() == failed) {
            stepView.setVisibility(View.GONE);
        }


    }

    private void doNoAuth() {

        /* 展示 初始状态   显示 一个拍照  外宽 田    消除  背景   消除  半透明边框  消除  重新上传   图标 */
        iv_zheng.showUp();

//        iv_zheng.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (iv_zheng.isAgain) {
//                    iv_zheng.showUp();
//                } else {
//                    iv_zheng.showUpAgain();
//                }
//            }
//        });
        iv_fan.showUp();


    }

    String http_zheng = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521200445415&di=0227ffbe405e51fbae862b1a4d132792&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20130530%2FImg377522814.jpg";
    String http_fan = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521200426297&di=70c4b374bd72b41baadf3f7a0ed1b6d3&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dpixel_huitu%252C0%252C0%252C294%252C40%2Fsign%3Db05d0b3c38fa828bc52e95a394672458%2Fd788d43f8794a4c2717d681205f41bd5ad6e39a8.jpg";

    private void doAuthFailed() {

//        setText(getView(et_name), "大傻么么哒");
//        setText(getView(et_card_num), "350435465476416549647");

        setText(getView(et_name), userIdentity.realName);
        setText(getView(et_card_num), userIdentity.identityNum);

        iv_fan.showUpAgain();
        iv_zheng.showUpAgain();

        Log.i("loadCompletedisplay", "start: ");

        Displayer displayer = new Displayer() {
            @Override
            public void loadCompletedisplay(View view, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig) {


                if (view instanceof MyImageViewShowUserCard) {
                    ImageView aa = ((ImageView) view);

                    aa.setBackground(new BitmapDrawable(bitmap));
                    aa.setImageResource(R.drawable.chongxinshangchuan);

                    if (aa == iv_fan) {
                        aa.setTag(http_fan);
                    } else {
                        aa.setTag(http_zheng);
                    }

                    Log.i("loadCompletedisplay", "loadCompletedisplay: ");

//                ToastUtil.showLongToast("-----加载结束----");
                }


            }

            @Override
            public void loadFailDisplay(View view, Bitmap bitmap) {

            }
        };


        finalBitmap.configDisplayer(displayer);

//        finalBitmap.clearCache(http_zheng);
        finalBitmap.display(iv_zheng, http_zheng);
        Log.i("loadCompletedisplay", "end: ");

        if (finalBitmap.getBitmapFromCache(http_zheng) != null) {


            iv_zheng.setBackground(new BitmapDrawable(finalBitmap.getBitmapFromCache(http_zheng)));
            iv_zheng.setImageResource(R.drawable.chongxinshangchuan);
            iv_zheng.setTag(http_zheng);
            Log.i("loadCompletedisplay", "loadCompletedisplay: ");

        }

        finalBitmap.display(iv_fan, http_fan);


        if (finalBitmap.getBitmapFromCache(http_fan) != null) {


            iv_fan.setBackground(new BitmapDrawable(finalBitmap.getBitmapFromCache(http_fan)));
            iv_fan.setImageResource(R.drawable.chongxinshangchuan);
            iv_fan.setTag(http_fan);
            Log.i("loadCompletedisplay", "loadCompletedisplay: ");

        }

//            }
//        }, 1200);


    }


    /*审核中*/
    private void doAuthing() {
        getView(R.id.btn_save).setVisibility(View.GONE);
        getView(R.id.iv_zheng).setVisibility(View.GONE);
        getView(R.id.iv_fan).setVisibility(View.GONE);
        getView(R.id.tv_zheng).setVisibility(View.GONE);
        getView(R.id.tv_fan).setVisibility(View.GONE);

        getView(R.id.iv_zheng_shili).setVisibility(View.VISIBLE);
        getView(R.id.iv_fan_shili).setVisibility(View.VISIBLE);

        View.OnClickListener listener = v -> {
            ArrayList<Pic> ossUrls = new ArrayList<>();
            ossUrls.add(new Pic("", false, http_zheng, 0));
            ossUrls.add(new Pic("", false, http_fan, 1));
            GalleryImageActivity.startGalleryImageActivity(mActivity, 0, ossUrls);
        };
        View.OnClickListener listener1 = v -> {
            ArrayList<Pic> ossUrls = new ArrayList<>();
            ossUrls.add(new Pic("", false, http_zheng, 0));
            ossUrls.add(new Pic("", false, http_fan, 1));
            GalleryImageActivity.startGalleryImageActivity(mActivity, 1, ossUrls);
        };


        getView(R.id.iv_zheng_shili).setOnClickListener(listener);
        getView(R.id.iv_fan_shili).setOnClickListener(listener1);


        finalBitmap.display(getView(R.id.iv_zheng_shili), http_zheng);
        getView(R.id.iv_zheng_shili).setPadding(0, 0, 0, 0);


        finalBitmap.display(getView(R.id.iv_fan_shili), http_fan);
        getView(R.id.iv_fan_shili).setPadding(0, 0, 0, 0);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finalBitmap.display(getView(R.id.iv_zheng_shili), http_zheng);
                finalBitmap.display(getView(R.id.iv_fan_shili), http_fan);
            }
        }, 1000);


        setText(getView(et_name), userIdentity.realName);
        setText(getView(et_card_num), userIdentity.identityNum);
        EditText editText = ((EditText) getView(et_name));
        editText.setFocusable(false);
        EditText et_card_num = ((EditText) getView(R.id.et_card_num));
        et_card_num.setFocusable(false);

    }


    /* 获取失败信息 */
    public String getFailedMsg() {
        return userIdentity.auditLog;
//        return getIntent().getStringExtra("failedMsg");
    }


    /* 获取 当前认证状态 */
    public int getAuthingState() {

        if (userIdentity == null || userIdentity.status == null) {
            return getIntent().getIntExtra("state", no_auth);
        } else {
            if (userIdentity.status.compareTo(UserIdentityStatus.unaudited) == 0) {
                return no_auth;
            } else if (userIdentity.status.compareTo(UserIdentityStatus.auditing) == 0) {
                return authing;
            } else if (userIdentity.status.compareTo(UserIdentityStatus.pass) == 0) {
                return succeed;
            } else if (userIdentity.status.compareTo(UserIdentityStatus.back) == 0) {
                return failed;
            } else {
                return getIntent().getIntExtra("state", no_auth);
            }
        }

//        return getIntent().getIntExtra("state", no_auth);
//        return getIntent().getIntExtra("state", no_auth);
    }


    public static void start(Activity mActivity, int state, String failedMsg) {
        Intent intent = new Intent(mActivity, AuthenticationActivity.class);
        intent.putExtra("state", state);
        intent.putExtra("failedMsg", failedMsg);
        mActivity.startActivity(intent);
    }


    public void choosePics() {
        new ActionSheetDialog(mActivity).builder().setCancelable(true).setCanceledOnTouchOutside(true)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Red,
                        which -> {
                            takePhotoForCamera();//
                        })
                .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue,
                        which -> {
                            takePhotoForAlbum();//相册获取
//                            boolean requestReadSDCardPermissions = new PermissionUtils(StoreSettingActivity.this).requestReadSDCardPermissions(200);
//                            if (requestReadSDCardPermissions) {
//                                Uri.fromFile(getTempCropFile());
//                                // 打开选择图片界面
//                                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                                openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                                startActivityForResult(openAlbumIntent, REQUEST_CODE_ALBUM);
//                            }

                        })
                .addSheetItem("查看大图", ActionSheetDialog.SheetItemColor.Blue,
                        which -> {
                            ArrayList<Pic> ossUrls = new ArrayList<>();
                            if (iv_zheng.getTag() != null) {
                                if (iv_zheng.getTag() instanceof String) {
                                    ossUrls.add(new Pic("", false, iv_zheng.getTag() + "", 0));
                                } else {
                                    ossUrls.add(new Pic("", false, ((File) iv_zheng.getTag()).getAbsolutePath(), 0));
                                }

                            }
                            if (iv_fan.getTag() != null) {
                                if (iv_fan.getTag() instanceof String) {
                                    ossUrls.add(new Pic("", false, iv_fan.getTag() + "", 1));
                                } else {
                                    ossUrls.add(new Pic("", false, ((File) iv_fan.getTag()).getAbsolutePath(), 1));
                                }
                            }

                            if (ossUrls.size() == 0) {
                                showToast("您还未拍摄照片");
                            } else {


                                if (ossUrls.size() == 1) {
                                    /* 1 张的时候 */
                                    GalleryImageActivity.startGalleryImageActivity(mActivity, 0, ossUrls);
                                }
                                if (ossUrls.size() == 2) {
                                    if (currentType.equals(zheng)) {
                                        GalleryImageActivity.startGalleryImageActivity(mActivity, 0, ossUrls);
                                    } else {
                                        GalleryImageActivity.startGalleryImageActivity(mActivity, 1, ossUrls);
                                    }
                                }


                            }
                        })
                .show();
    }


    private void takePhotoForCamera() {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestRuntimePermission(permissions, new Eactivity3_0.PermissionListener() {
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

    private void takePhotoForAlbum() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        requestRuntimePermission(permissions, new Eactivity3_0.PermissionListener() {
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


    //andrpoid 6.0 需要写运行时权限
    public void requestRuntimePermission(String[] permissions, Eactivity3_0.PermissionListener listener) {

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

    //动态获取权限监听
    private static Eactivity3_0.PermissionListener mListener;


    private void openCamera() {
        cameraFile = uploadHeadUtil.getCacheFile(new File(getDiskCacheDir(this)), "output_image1" + System.currentTimeMillis() + ".jpg");


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


    /**
     *
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    private String imagType_tag = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        if (currentType.equals(zheng)) {
                            displayIv(iv_zheng, cameraFile);
                        } else {
//                            FinalBitmap.create(mActivity)
//                                    .display(iv_fan, cameraFile.getAbsolutePath());
//                            iv_fan.setTag(cameraFile);
                            displayIv(iv_fan, cameraFile);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {


                        Uri uri = data.getData();
                        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
                        String imagePath = uploadHeadUtil.uriToPath(uri);
//        displayImage(imagePath); // 根据图片路径显示图片
                        if (currentType.equals(zheng)) {
//                            FinalBitmap.create(mActivity)
//                                    .display(iv_zheng, imagePath);
//                            iv_zheng.setTag(new File(imagePath));
                            displayIv(iv_zheng, new File(imagePath));

                        } else {
//                            FinalBitmap.create(mActivity)
//                                    .display(iv_fan, imagePath);
//                            iv_fan.setTag(new File(imagePath));

                            displayIv(iv_fan, new File(imagePath));
                        }


                    } else {

                        // 4.4以下系统使用这个方法处理图片
//                            uploadHeadUtil.handleImageBeforeKitKat(data, 2, cacheFile);

                        Uri uri = data.getData();
                        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
                        String imagePath = uploadHeadUtil.getImagePath(uri, null);
//        displayImage(imagePath); // 根据图片路径显示图片
                        if (currentType.equals(zheng)) {
//                            FinalBitmap.create(mActivity)
//                                    .display(iv_zheng, imagePath);
//                            iv_zheng.setTag(new File(imagePath));

                            displayIv(iv_zheng, new File(imagePath));

                        } else {
//                            FinalBitmap.create(mActivity)
//                                    .display(iv_fan, imagePath);
//                            iv_fan.setTag(new File(imagePath));

                            displayIv(iv_fan, new File(imagePath));


                        }

                    }
                }
                break;

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void displayIv(MyImageViewShowUserCard iv, File file) {


        iv.setTag(file);

        iv.showUpAgain();

        FinalBitmap finalBitmap = FinalBitmap.create(mActivity)
                .configDisplayer(new Displayer() {
                    @Override
                    public void loadCompletedisplay(View view, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig) {

                        if (view instanceof MyImageViewShowUserCard) {
                            iv.setBackground(new BitmapDrawable(bitmap));
                            iv.setImageResource(R.drawable.chongxinshangchuan);
//                        this.setImageResource(R.drawable.chongxinshangchuan);
                        }


                    }

                    @Override
                    public void loadFailDisplay(View view, Bitmap bitmap) {

                    }
                });
        finalBitmap.display(iv, file.getAbsolutePath());

        if (finalBitmap.getBitmapFromCache(file.getAbsolutePath()) != null) {
            iv.setBackground(new BitmapDrawable(finalBitmap.getBitmapFromCache(file.getAbsolutePath())));
            iv.setImageResource(R.drawable.chongxinshangchuan);
        }


//        iv.setBackground(new ColorDrawable(Color.YELLOW));


    }


    private interface UploadListener {
        void uploadSucceed(Pic pic);

        void uploadFaild(String msg);

    }

    @Override
    protected void onDestroy() {
        hindLoading();
        if (finalBitmap != null) {
            finalBitmap.configDisplayer(new SimpleDisplayer());
//            finalBitmap.configDisplayer(new Displayer() {
//                @Override
//                public void loadCompletedisplay(View view, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig) {
//                    if (view instanceof ImageView) {
//                        ((ImageView) view).setImageBitmap(bitmap);
//                    }
//                }
//
//                @Override
//                public void loadFailDisplay(View view, Bitmap bitmap) {
//
//                }
//            });
        }
        super.onDestroy();
    }
}

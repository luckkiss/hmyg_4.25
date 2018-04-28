package com.hldj.hmyg.saler;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hldj.hmyg.R;
import com.hldj.hmyg.StoreActivity;
import com.hldj.hmyg.Ui.AuthenticationActivity;
import com.hldj.hmyg.Ui.AuthenticationCompanyActivity;
import com.hldj.hmyg.Ui.Eactivity3_0;
import com.hldj.hmyg.Ui.QcCodeActivity;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.UploadHeadUtil;
import com.hy.utils.GetServerUrl;
import com.hy.utils.JsonGetInfo;
import com.hy.utils.Loading;
import com.hy.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.soundcloud.android.crop.Crop;
import com.zf.iosdialog.widget.ActionSheetDialog;
import com.zym.selecthead.config.Configs;
import com.zym.selecthead.tools.FileTools;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static com.hldj.hmyg.util.UploadHeadUtil.CHOOSE_PHOTO;
import static com.hldj.hmyg.util.UploadHeadUtil.CROP_PHOTO;
import static com.hldj.hmyg.util.UploadHeadUtil.TAKE_PHOTO;
import static com.hldj.hmyg.util.UploadHeadUtil.getDiskCacheDir;

public class StoreSettingActivity extends NeedSwipeBackActivity {

    StoreSettingActivity context;
    //动态获取权限监听
    private static Eactivity3_0.PermissionListener mListener;
    private EditText et_domian;
    private EditText et_store_name;
    private TextView tv_open_close;
    private ImageView iv_logo;
    private TextView change_logo;
    private ImageView iv_banner;
    private static final int REQUEST_CODE_ALBUM = 6;
    private static final int REQUEST_CODE_CAMERA = 2;
    private static final int CROP_REQUEST_CODE = 4;
    private static final String ROOT_NAME = "UPLOAD_CACHE";
    String dateTime;
    String targeturl = null;
    private static final int SCALE = 5;// 照片缩小比例
    private Bitmap bitmap;
    private String imagType_tag = "";
    private String str_logo_json = "";
    private String str_banner_json = "";
    private Loading loading;
    private EditText et_detail;
    private EditText et_type;
    private EditText et_introduction;
    private Gson gson;
    private String store_id = "";
    private String name = "";
    private String code = "";
    private String num = "";
    private String remarks = "";
    private String mainType = "";
    private String logoId = "";
    private String logoUrl = "";
    private String bannerId = "";
    private String bannerUrl = "";
    private FinalBitmap fb;
    private LinearLayout ll_ed;
    private MultipleClickProcess multipleClickProcess;
    private File mTempCameraFile;
    private File mTempCropFile;
    UploadHeadUtil uploadHeadUtil;


    private String cachPath;
    private File cacheFile;
    private File cameraFile;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_setting);
        uploadHeadUtil = new UploadHeadUtil(mActivity);
        cachPath = getDiskCacheDir(this) + "/handimg.jpg";//图片路径
        cacheFile = uploadHeadUtil.getCacheFile(new File(getDiskCacheDir(this)), "handimg.jpg");
        context = this;
        gson = new Gson();
        fb = FinalBitmap.create(this);
        fb.configLoadingImage(R.drawable.no_image_show);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        multipleClickProcess = new MultipleClickProcess();
        ImageView btn_back = (ImageView) findViewById(R.id.toolbar_left_icon);

        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText("店铺设置");

        iv_logo = (ImageView) findViewById(R.id.imageView17);
        change_logo = (TextView) findViewById(R.id.head_name);
        tv_open_close = (TextView) findViewById(R.id.tv_open_close);
        iv_banner = (ImageView) findViewById(R.id.iv_banner);
        ll_ed = (LinearLayout) findViewById(R.id.ll_ed);
        et_domian = (EditText) findViewById(R.id.et_domain);
        et_store_name = (EditText) findViewById(R.id.et_store_name);
        et_detail = (EditText) findViewById(R.id.et_detail);

        et_type = (EditText) findViewById(R.id.et_type);
        et_introduction = (EditText) findViewById(R.id.et_introduction);
        TextView sure = (TextView) findViewById(R.id.sure);
        getStore();
        btn_back.setOnClickListener(multipleClickProcess);
        iv_logo.setOnClickListener(multipleClickProcess);
        change_logo.setOnClickListener(multipleClickProcess);
        iv_banner.setOnClickListener(multipleClickProcess);
        et_domian.setOnClickListener(multipleClickProcess);
        sure.setOnClickListener(multipleClickProcess);
        tv_open_close.setOnClickListener(multipleClickProcess);
        getView(R.id.qyrz).setOnClickListener(multipleClickProcess);
        getView(R.id.qyewm).setOnClickListener(multipleClickProcess);


    }

    private void getStore() {
        showLoading();
        // TODO Auto-generated method stub
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        finalHttp.post(GetServerUrl.getUrl() + "admin/store/getStore", params,
                new AjaxCallBack<Object>() {

                    @Override
                    public void onSuccess(Object t) {
                        // TODO Auto-generated method stub
                        try {
                            JSONObject jsonObject = new JSONObject(t.toString());
                            String codes = JsonGetInfo.getJsonString(
                                    jsonObject, "code");
                            String msg = JsonGetInfo.getJsonString(jsonObject,
                                    "msg");
                            if (!"".equals(msg)) {
                                // Toast.makeText(StoreSettingActivity.this,
                                // msg,
                                // Toast.LENGTH_SHORT).show();
                            }
                            if ("1".equals(codes)) {
                                JSONObject jsonObject2 = JsonGetInfo
                                        .getJSONObject(jsonObject, "data");
                                JSONObject jsonObject3 = JsonGetInfo
                                        .getJSONObject(jsonObject2, "store");
                                store_id = JsonGetInfo.getJsonString(
                                        jsonObject3, "id");
                                name = JsonGetInfo.getJsonString(jsonObject3,
                                        "name");
                                code = JsonGetInfo.getJsonString(jsonObject3,
                                        "code");
                                remarks = JsonGetInfo.getJsonString(
                                        jsonObject3, "remarks");
                                num = JsonGetInfo.getJsonString(jsonObject3,
                                        "num");
                                mainType = JsonGetInfo.getJsonString(
                                        jsonObject3, "mainType");
                                logoId = JsonGetInfo.getJsonString(
                                        JsonGetInfo.getJSONObject(jsonObject3,
                                                "logoJson"), "id");
                                logoUrl = JsonGetInfo.getJsonString(
                                        JsonGetInfo.getJSONObject(jsonObject3,
                                                "logoJson"), "url");
                                bannerId = JsonGetInfo.getJsonString(
                                        JsonGetInfo.getJSONObject(jsonObject3,
                                                "appBannerJson"), "id");
                                bannerUrl = JsonGetInfo.getJsonString(
                                        JsonGetInfo.getJSONObject(jsonObject3,
                                                "appBannerJson"), "url");
                                if (code.length() > 0) {
                                    et_domian.setText(code + ".hmeg.cn");
                                    et_domian.setEnabled(false);
                                    ll_ed.setOnClickListener(new OnClickListener() {

                                        @Override
                                        public void onClick(View arg0) {
                                            // TODO Auto-generated method stub
                                            Intent toStoreActivity = new Intent(
                                                    StoreSettingActivity.this,
                                                    StoreActivity.class);
                                            toStoreActivity.putExtra("code",
                                                    code);
                                            startActivity(toStoreActivity);
                                        }
                                    });

                                } else {
                                    ll_ed.setBackgroundResource(R.drawable.store_edit_selector);
                                }
                                et_store_name.setText(name);
                                et_detail.setText(remarks);
                                et_type.setText(mainType);
                                if (!"".equals(logoId) && !"".equals(logoUrl)) {
                                    Pic pic = new Pic(logoId, false, logoUrl, 0);
                                    ArrayList<Pic> pics = new ArrayList<Pic>();
                                    pics.add(pic);
                                    str_logo_json = gson.toJson(pics);
                                    fb.display(iv_logo, logoUrl);
                                }
                                if (!"".equals(bannerId)
                                        && !"".equals(bannerUrl)) {
                                    Pic pic = new Pic(bannerId, false,
                                            bannerUrl, 0);
                                    ArrayList<Pic> pics = new ArrayList<Pic>();
                                    pics.add(pic);
                                    str_banner_json = gson.toJson(pics);
                                    fb.display(iv_banner, bannerUrl);
                                }

                            } else {

                                ToastUtil.showShortToast(msg);
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();

                        }
                        hindLoading();
                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        // TODO Auto-generated method stub
                        Toast.makeText(StoreSettingActivity.this,
                                R.string.error_net, Toast.LENGTH_SHORT).show();
                        hindLoading();
                        super.onFailure(t, errorNo, strMsg);
                    }

                });
    }


    private void save() {
        if (TextUtils.isEmpty(et_store_name.getText())) {
            ToastUtil.showLongToast("请填写店铺名称");
            return;
        }
        if (TextUtils.isEmpty(et_type.getText())) {
            ToastUtil.showLongToast("请填写品种名称");
            return;
        }

        showLoading();


        // TODO Auto-generated method stub
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("id", store_id);
        if ("".equals(code)) {
            params.put("code", et_domian.getText().toString());
        } else {
            params.put("code", code);
        }
        params.put("name", et_store_name.getText().toString());
        params.put("remarks", et_detail.getText().toString());
        params.put("mainType", et_type.getText().toString());
        params.put("logoData", str_logo_json);
        params.put("bannerData", str_banner_json);
        params.put("num", num);
        finalHttp.post(GetServerUrl.getUrl() + "admin/store/save", params,
                new AjaxCallBack<Object>() {

                    @Override
                    public void onSuccess(Object t) {
                        // TODO Auto-generated method stub
                        try {
                            JSONObject jsonObject = new JSONObject(t.toString());
                            String code = JsonGetInfo.getJsonString(jsonObject,
                                    "code");
                            String msg = JsonGetInfo.getJsonString(jsonObject,
                                    "msg");
                            if (!"".equals(msg) && !"1".equals(code)) {
                                Toast.makeText(StoreSettingActivity.this,
                                        msg, Toast.LENGTH_SHORT).show();
                            }
                            if ("1".equals(code)) {
                                Toast.makeText(StoreSettingActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                RxBus.getInstance().post(5, new Eactivity3_0.OnlineEvent(true));
                                finish();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block

                            e.printStackTrace();
                        }
                        hindLoading();
                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        // TODO Auto-generated method stub
                        hindLoading();
                        Toast.makeText(StoreSettingActivity.this, R.string.error_net, Toast.LENGTH_SHORT).show();
                        super.onFailure(t, errorNo, strMsg);
                    }

                });
    }

    public static void start2Activity(Context mActivity) {
        mActivity.startActivity(new Intent(mActivity, StoreSettingActivity.class));
    }

    public class MultipleClickProcess implements OnClickListener {
        private boolean flag = true;

        private synchronized void setFlag() {
            flag = false;
        }

        public void onClick(View view) {
            if (flag) {
                switch (view.getId()) {
                    case R.id.qyrz:
                        D.i("========企业认证==========");

                        AuthenticationCompanyActivity.start(mActivity, AuthenticationActivity.no_auth, "");

                        break;
                    case R.id.qyewm:
                        D.i("========企业二维码==========");

                        QcCodeActivity.start(mActivity, 1, "hello world");


                        break;
                    case R.id.toolbar_left_icon:
                        onBackPressed();
                        break;
                    case R.id.tv_open_close:
                        //显示。。隐藏
                        View view1 = getView(R.id.ll_other);

                        if (view1.getVisibility() == View.VISIBLE) {
                            view1.setVisibility(View.GONE);
                        } else {
                            view1.setVisibility(View.VISIBLE);
                        }
                        view.setSelected(!view.isSelected());

                        break;
                    case R.id.et_domain:
                        if (!"".equals(code)) {
                            Intent toStoreActivity = new Intent(
                                    StoreSettingActivity.this, StoreActivity.class);
                            toStoreActivity.putExtra("code", store_id);
                            startActivity(toStoreActivity);
                        }
                        break;
                    case R.id.imageView17:
                    case R.id.head_name:
                        imagType_tag = "storeLogo";
                        choosePics();
                        break;
                    case R.id.iv_banner:
                        imagType_tag = "storeBanner";
                        choosePics();
                        break;
                    case R.id.sure:

//                        if ("".equals(et_domian.getText().toString()) || "".equals(et_store_name.getText().toString())) {
//                            Toast.makeText(StoreSettingActivity.this, "请将店铺域名和店铺名称填写完整", Toast.LENGTH_SHORT).show();
//                            hindLoading();
//                            return;
//                        }
//                         validate();
                        save();

                        break;

                    default:
                        break;
                }
                setFlag();
                // do some things
                new TimeThread().start();
            }
        }


        /**
         * 计时线程（防止在一定时间段内重复点击按钮）
         */
        private class TimeThread extends Thread {
            public void run() {
                try {
                    Thread.sleep(Data.loading_time);
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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

    /**
     *
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
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

    private void openCamera() {
        cameraFile = uploadHeadUtil.getCacheFile(new File(getDiskCacheDir(this)), "output_image1.jpg");
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
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        if (imagType_tag.equals("storeLogo")) {
                            // 将拍摄的照片显示出来
                            uploadHeadUtil.startPhotoZoom(cameraFile, 1, cacheFile);
                        } else {
                            // 将拍摄的照片显示出来
                            uploadHeadUtil.startPhotoZoom(cameraFile, 2, cacheFile);
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
                        if (imagType_tag.equals("storeLogo")) {
                            // 4.4及以上系统使用这个方法处理图片
                            uploadHeadUtil.handleImageOnKitKat(data, 1, cacheFile);
                        } else {

                            // 4.4及以上系统使用这个方法处理图片
                            uploadHeadUtil.handleImageOnKitKat(data, 2, cacheFile);
                        }


                    } else {

                        if (imagType_tag.equals("storeLogo")) {
                            // 4.4以下系统使用这个方法处理图片
                            uploadHeadUtil.handleImageBeforeKitKat(data, 1, cacheFile);
                        } else {


                            // 4.4以下系统使用这个方法处理图片
                            uploadHeadUtil.handleImageBeforeKitKat(data, 2, cacheFile);
                        }


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

                        updateImage("", imagType_tag, "", bitmap);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    D.e("====报错==" + e.getMessage());
                }

                break;
        }


        // TODO Auto-generated method stub
//        if (resultCode != RESULT_OK)
//            return;
//        switch (requestCode) {
//            case REQUEST_CODE_ALBUM://相册选择返回的图片.
//                ContentResolver resolver = getContentResolver();
//                // 照片的原始资源地址
//                Uri originalUri = data.getData();
////                Uri originalUri = Uri.fromFile(getTempCropFile());
//                try {
//                    // 使用ContentProvider通过URI获取原始图片
//                    Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
//                    if (photo != null) {
//                        // 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
//                        Bitmap smallBitmap = ImageTools
//                                .zoomBitmap(photo, photo.getWidth() / SCALE,
//                                        photo.getHeight() / SCALE);
//                        // 释放原始图片占用的内存，防止out of memory异常发生
//                        photo.recycle();
//                        targeturl = saveToSdCard(smallBitmap);
//                        if ("storeLogo".equals(imagType_tag)) {
//                            iv_logo.setImageDrawable(new BitmapDrawable(smallBitmap));
//
//                        } else if ("storeBanner".equals(imagType_tag)) {
//                            iv_banner.setImageDrawable(new BitmapDrawable(
//                                    smallBitmap));
//                        }
//                        updateImage(targeturl, imagType_tag, "");
//
//                    }
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                break;
//            case 1://拍照返回
//
//                File file = null ;
////                if (SelectHeadTools.imageUri != null) {
////                      file = FileTools.getFileByUri(this, SelectHeadTools.imageUri);
////                    if (SelectHeadTools.imageUri != null) SelectHeadTools.imageUri = null;
////
////                } else {
//////
////                      file = FileTools.getFileByUri(this, photoUri );
////                }
//
//                file = FileTools.getFileByUri(this, photoUri );
////                mTempCameraFile
//
////              startCrop(Uri.fromFile(getTempCropFile()), 2, 1);
//                sendImage(file);
//                if (file.exists()) {
//                    bitmap = compressImageFromFile(file.getAbsolutePath());
//                    targeturl = saveToSdCard(bitmap);
//                    if ("storeLogo".equals(imagType_tag)) {
//                        iv_logo.setImageDrawable(new BitmapDrawable(bitmap));
//                    } else if ("storeBanner".equals(imagType_tag)) {
//                        iv_banner.setImageDrawable(new BitmapDrawable(bitmap));
//                    }
//                    updateImage(targeturl, imagType_tag, "");
//                } else {
//
//                }
//
//                break;
//
//            default:
//                break;
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
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

    public void updateImage(String url, String imagType, String sourceId, Bitmap upBmp) {
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
//        int rotate = PictureManageUtil.getCameraPhotoOrientation(url);
        // 把压缩的图片进行旋转
//        params.put(  "file",  new ByteArrayInputStream(Bitmap2Bytes(PictureManageUtil  .rotateBitmap(PictureManageUtil.getCompressBm(url),  rotate))), System.currentTimeMillis() + ".png");
        params.put("file", new ByteArrayInputStream(Bitmap2Bytes(upBmp)), System.currentTimeMillis() + ".png");
        params.put("imagType", imagType);
        params.put("sourceId", sourceId);
        finalHttp.post(GetServerUrl.getUrl() + "admin/file/image", params,
                new AjaxCallBack<Object>() {

                    @Override
                    public void onStart() {

                        if (loading != null
                                && !StoreSettingActivity.this.isFinishing()) {
                            loading.showToastAlong();
                        } else if (loading == null
                                && !StoreSettingActivity.this.isFinishing()) {
                            loading = new Loading(StoreSettingActivity.this, "店铺资料修改中.....");
                            loading.showToastAlong();
                        }
                        super.onStart();
                    }

                    @Override
                    public void onSuccess(Object t) {
                        try {
                            JSONObject jsonObject = new JSONObject(t.toString());
                            String msg = jsonObject.getString("msg");
                            int code = jsonObject.getInt("code");
                            if (code == 1) {
                                Toast.makeText(StoreSettingActivity.this,
                                        "图片上传成功", Toast.LENGTH_SHORT).show();
                                JSONObject jsonObject2 = jsonObject.getJSONObject("data").getJSONObject("image");
                                String image = jsonObject2.getString("ossSmallImagePath");

                                if (imagType_tag.equals("storeLogo")) {

                                    ImageLoader.getInstance().displayImage(image, (ImageView) getView(R.id.imageView17));
                                } else {

                                    ImageLoader.getInstance().displayImage(image, (ImageView) getView(R.id.iv_banner));
                                }


                                Pic pic = new Pic(JsonGetInfo.getJsonString(jsonObject2, "id"), false, JsonGetInfo.getJsonString(jsonObject2, "url"), 0);
                                ArrayList<Pic> pics = new ArrayList<Pic>();
                                pics.add(pic);
                                if ("storeLogo".equals(imagType_tag)) {
                                    str_logo_json = gson.toJson(pics);
                                } else if ("storeBanner".equals(imagType_tag)) {
                                    str_banner_json = gson.toJson(pics);
                                }
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        if (loading != null
                                && !StoreSettingActivity.this.isFinishing()) {
                            loading.cancel();
                        }
                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        // TODO Auto-generated method stub
                        if (loading != null
                                && !StoreSettingActivity.this.isFinishing()) {
                            loading.cancel();
                        }
                        super.onFailure(t, errorNo, strMsg);
                    }
                });
    }


    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


}

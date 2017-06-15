package com.hldj.hmyg.saler;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import com.hldj.hmyg.Ui.Eactivity3_0;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.UploadHeadUtil;
import com.hy.utils.BitmapHelper;
import com.hy.utils.GetServerUrl;
import com.hy.utils.JsonGetInfo;
import com.hy.utils.Loading;
import com.hy.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.soundcloud.android.crop.Crop;
import com.xingguo.huang.mabiwang.util.CacheUtils;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private ImageView iv_logo;
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

        iv_logo = (ImageView) findViewById(R.id.iv_logo);
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
        iv_banner.setOnClickListener(multipleClickProcess);
        et_domian.setOnClickListener(multipleClickProcess);
        sure.setOnClickListener(multipleClickProcess);

        // et_domian.setOnEditorActionListener(new OnEditorActionListener() {
        //
        // public boolean onEditorAction(TextView v, int actionId, KeyEvent
        // event) {
        // // TODO Auto-generated method stub
        //
        // return false;
        // }
        // });
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

    private void validate() {
        // TODO Auto-generated method stub
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("code", et_domian.getText().toString());
        finalHttp.post(GetServerUrl.getUrl() + "admin/store/validate", params,
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
                            if (!"".equals(msg)) {
                                // Toast.makeText(StoreSettingActivity.this,
                                // msg,
                                // Toast.LENGTH_SHORT).show();
                            }
                            if ("1".equals(code)) {
                                save();
                            } else if ("4003".equals(code)) {
                                // 存在
                                et_domian.requestFocus();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        // TODO Auto-generated method stub
                        Toast.makeText(StoreSettingActivity.this,
                                R.string.error_net, Toast.LENGTH_SHORT).show();
                        super.onFailure(t, errorNo, strMsg);
                    }

                });
    }

    private void save() {
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
                                RxBus.getInstance().post(5,new Eactivity3_0.OnlineEvent(true));
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
                    case R.id.toolbar_left_icon:
                        onBackPressed();
                        break;
                    case R.id.et_domain:
                        if (!"".equals(code)) {
                            Intent toStoreActivity = new Intent(
                                    StoreSettingActivity.this, StoreActivity.class);
                            toStoreActivity.putExtra("code", store_id);
                            startActivity(toStoreActivity);
                        }
                        break;
                    case R.id.iv_logo:
                        imagType_tag = "storeLogo";
                        choosePics();
                        break;
                    case R.id.iv_banner:
                        imagType_tag = "storeBanner";
                        choosePics();
                        break;
                    case R.id.sure:
                        showLoading();
                        if ("".equals(et_domian.getText().toString()) || "".equals(et_store_name.getText().toString())) {
                            Toast.makeText(StoreSettingActivity.this, "请将店铺域名和店铺名称填写完整", Toast.LENGTH_SHORT).show();
                            hindLoading();
                            return;
                        }
                        // validate();
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

        /**
         *
         */
        private void openAlbum() {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
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
                            uploadHeadUtil.handleImageOnKitKat(data,1, cacheFile);
                        } else {

                            // 4.4及以上系统使用这个方法处理图片
                            uploadHeadUtil.handleImageOnKitKat(data,2, cacheFile);
                        }


                    } else {

                        if (imagType_tag.equals("storeLogo")) {
                            // 4.4以下系统使用这个方法处理图片
                            uploadHeadUtil.handleImageBeforeKitKat(data,1, cacheFile);
                        } else {


                            // 4.4以下系统使用这个方法处理图片
                            uploadHeadUtil.handleImageBeforeKitKat(data,2, cacheFile);
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
//                        Drawable drawable = new BitmapDrawable(bitmap);
////                     D.e("=========bitmap======="+bitmap.getByteCount()/1024/1024);
//                        RelativeLayout relativeLayout = getView(R.id.e_background);
//                        relativeLayout.setBackgroundDrawable(drawable);
//                        BasePresenter presenter = new EPrestenter()
//                                .addResultCallBack(new ResultCallBack<String>() {
//                                    @Override
//                                    public void onSuccess(String str) {
//
//                                        new Handler().postDelayed(() -> {
//                                            bitmap.recycle();
//                                            D.e("===str=====" + str);
//                                            ImageLoader.getInstance().displayImage(str, (ImageView) getView(R.id.iv_logo));
//                                            ImageLoader.getInstance().displayImage(str, (ImageView) getView(R.id.iv_banner));
//                                        }, 500);
//                                    }
//
//                                    @Override
//                                    public void onFailure(Throwable t, int errorNo, String strMsg) {
//                                    }
//                                });
//
//                        ((EPrestenter) presenter).upLoadHeadImg("admin/file/uploadHeadImage", true, cachPath, bitmap);
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

                                    ImageLoader.getInstance().displayImage(image, (ImageView) getView(R.id.iv_logo));
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

    private Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// 只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;//
        float ww = 480f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置采样率

        newOpts.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        // return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        // 其实是无效的,大家尽管尝试
        return bitmap;
    }

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public String saveToSdCard(Bitmap bitmap) {
        String files = CacheUtils.getCacheDirectory(StoreSettingActivity.this,
                true, "pic") + dateTime + "_11";
        File file = new File(files);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    /**
     * 开始裁剪 附加选项 数据类型 描述 crop String 发送裁剪信号 aspectX int X方向上的比例 aspectY int
     * Y方向上的比例 outputX int 裁剪区的宽 outputY int 裁剪区的高 scale boolean 是否保留比例
     * return-data boolean 是否将数据保留在Bitmap中返回 data Parcelable 相应的Bitmap数据
     * circleCrop String 圆形裁剪区域？ MediaStore.EXTRA_OUTPUT ("output") URI
     * 将URI指向相应的file:///...
     *
     * @param uri uri
     */
    private void startCrop(Uri uri, int outputX, int outputY) {
        Bitmap bitmap = null;
        String str = "";

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),
                    uri);
            str = MediaStore.Images.Media.insertImage(getContentResolver(),
                    bitmap, "", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bitmap == null || TextUtils.isEmpty(str)) {
            Toast.makeText(StoreSettingActivity.this, "找不到此图片",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        uri = Uri.parse(str);

        Intent intent = new Intent("com.android.camera.action.CROP"); // 调用Android系统自带的一个图片剪裁页面
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");// 进行修剪
        // aspectX aspectY 是宽高的比例
        if (outputX == outputY) {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        } else {
            intent.putExtra("scale", true);
        }
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(getTempCropFile()));
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    private File getTempCropFile() {
        if (mTempCropFile == null) {
            mTempCropFile = getTempMediaFile();
        }
        return mTempCropFile;
    }

    private File getTempCameraFile() {
        if (mTempCameraFile == null)
            mTempCameraFile = getTempMediaFile();
        return mTempCameraFile;
    }

    private File composBitmap(File file) {
        Bitmap bitmap = BitmapHelper.revisionImageSize(file);
        return BitmapHelper.saveBitmap2file(StoreSettingActivity.this, bitmap);
    }

    private void sendImage(final File file) {
        if (file == null) {
            Toast.makeText(StoreSettingActivity.this, "找不到此图片",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // 可以在这里统一进行联网上传操作，把上传结果传回，也可以直接传file交给原页面处理
    }

    /**
     * 原项目用的RxJava的Action2
     */
    public interface Action2 {
        void call(File file, int mode);
    }

    /**
     * 获取相机的file
     */
    public File getTempMediaFile() {
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            String fileName = getTempMediaFileName();
            file = new File(fileName);
        }
        return file;
    }

    public String getTempMediaFileName() {
        return getParentPath() + "image" + System.currentTimeMillis() + ".jpg";
    }

    private String getParentPath() {
        String parent = Environment.getExternalStorageDirectory()
                + File.separator + ROOT_NAME + File.separator;
        mkdirs(parent);
        return parent;
    }

    public boolean mkdirs(String path) {
        File file = new File(path);
        return !file.exists() && file.mkdirs();
    }

}

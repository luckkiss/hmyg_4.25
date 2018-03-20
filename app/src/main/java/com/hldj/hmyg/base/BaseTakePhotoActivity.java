package com.hldj.hmyg.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.MyLuban.MyLuban;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.Eactivity3_0;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.UploadHeadUtil;
import com.zf.iosdialog.widget.ActionSheetDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.hldj.hmyg.Ui.AuthenticationActivity.no_auth;
import static com.hldj.hmyg.util.UploadHeadUtil.CHOOSE_PHOTO;
import static com.hldj.hmyg.util.UploadHeadUtil.TAKE_PHOTO;
import static com.hldj.hmyg.util.UploadHeadUtil.getDiskCacheDir;

/**
 * 积分详情界面
 */

public abstract class BaseTakePhotoActivity extends BaseMVPActivity {


    private String cachPath;
    private File cacheFile;
    private File cameraFile;
    private File cameraFile1;
    private Uri imageUri;

    UploadHeadUtil uploadHeadUtil;


    @Override
    public String setTitle() {

        return "实名认证";

    }


    private int uploadImageCount = 0;



    private String pic_json1;
    private String pic_json2;
    private int totalCount = 0;


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



    /* 获取失败信息 */
    public String getFailedMsg() {
        return getIntent().getStringExtra("failedMsg");
    }


    /* 获取 当前认证状态 */
    public int getAuthingState() {
        return getIntent().getIntExtra("state", no_auth);
    }


    public static void start(Activity mActivity, int state, String failedMsg) {
        Intent intent = new Intent(mActivity, BaseTakePhotoActivity.class);
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

                            initShowBigImages(ossUrls);


                            if (ossUrls.size() == 0) {
                                showToast("您还未拍摄照片");
                            } else {
                                GalleryImageActivity.startGalleryImageActivity(mActivity, 0, ossUrls);
                            }
                        })
                .show();
    }

    public abstract void initShowBigImages(ArrayList<Pic> ossUrls);


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {

                        displayIv(cameraFile);


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

                        displayIv(new File(imagePath));


                    } else {

                        // 4.4以下系统使用这个方法处理图片
//                            uploadHeadUtil.handleImageBeforeKitKat(data, 2, cacheFile);

                        Uri uri = data.getData();
                        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
                        String imagePath = uploadHeadUtil.getImagePath(uri, null);

                        displayIv(new File(imagePath));


                    }
                }
                break;

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void displayIv(File file) {

    }


    public static interface UploadListener {
        void uploadSucceed(Pic pic);

        void uploadFaild(String msg);

    }

}

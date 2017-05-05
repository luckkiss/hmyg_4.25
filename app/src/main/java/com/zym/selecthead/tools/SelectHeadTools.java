package com.zym.selecthead.tools;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.white.utils.FileUtil;
import com.zym.selecthead.config.Configs;

import java.io.File;
import java.util.UUID;

/**
 * Created by ZYMAppOne on 2015/12/16.
 */
public class SelectHeadTools {



    /*
      public File createIconFile() {
        String fileName = "";
        if (iconDir != null) {
            fileName = UUID.randomUUID().toString() + ".png";
        }
        return new File(iconDir, fileName);
    }

     */

    public static File createIconFile(String iconDir, String name) {
        if (iconDir != null) {
            name = UUID.randomUUID().toString() + name;
        }


        return new File(iconDir, name);
    }

    public static Uri imageUri;


    /****
     * 调用系统的拍照功能
     * @param context Activity上下文对象
     * @param uri  Uri
     */
    public static void startCamearPicCut(Activity context, Uri uri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        File file = createIconFile(Configs.SystemPicture.SAVE_DIRECTORY, Configs.SystemPicture.SAVE_PIC_NAME); // 定义文件

        try {
            //适配7.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(context, "com.hldj.hmyg.fileprovider", file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件   intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
//              ContentValues contentValues = new ContentValues(1);
//              contentValues.put(MediaStore.Images.Media.DATA, flowerInfoPhotoPath);
//              Uri uri_new = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//              intent.putExtra(MediaStore.EXTRA_OUTPUT, uri_new);

                /**
                 *  ContentValues contentValues = new ContentValues(1);
                 contentValues.put(MediaStore.Images.Media.DATA, flowerInfoPhotoPath);
                 Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                 intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                 context.startActivityForResult(intent, request_code);
                 */

            } else {
                // 调用系统的拍照功能
                intent.putExtra("camerasensortype", 2);// 调用前置摄像头
                intent.putExtra("autofocus", true);// 自动对焦
                intent.putExtra("fullScreen", true);// 全屏
                intent.putExtra("showActionIcons", false);
                // 指定调用相机拍照后照片的储存路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
            context.startActivityForResult(intent, Configs.SystemPicture.PHOTO_REQUEST_TAKEPHOTO);
        } catch (Exception e) {
            ContentValues contentValues = new ContentValues(1);
            long str = System.currentTimeMillis();
            String filename = "head_" + str + ".png";
            File photoFile = new File(FileUtil.getFlowerPicPath(""), filename);
            contentValues.put(MediaStore.Images.Media.DATA, FileUtil.getFlowerPicPath(filename));
            imageUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            context.startActivityForResult(intent, Configs.SystemPicture.PHOTO_REQUEST_TAKEPHOTO);
//            ToastUtil.showShortToast("没有权限");
        }
    }

    public static String uriToRealPath(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri,
                new String[]{MediaStore.Images.Media.DATA},
                null,
                null,
                null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        return path;
    }


    /***
     * 调用系统的图库
     * @param context Activity上下文对象
     */
    public static void startImageCaptrue(Activity context) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        context.startActivityForResult(intent, Configs.SystemPicture.PHOTO_REQUEST_GALLERY);
    }


    /*****
     * 进行截图
     * @param context Activity上下文对象
     * @param uri  Uri
     * @param size  大小
     */
    public static void startPhotoZoom(Activity context, Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);//设置为不返回数据

        context.startActivityForResult(intent, Configs.SystemPicture.PHOTO_REQUEST_CUT);
    }
}

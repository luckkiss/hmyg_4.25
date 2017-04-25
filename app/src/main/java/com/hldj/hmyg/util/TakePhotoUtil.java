package com.hldj.hmyg.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.hldj.hmyg.BuildConfig;
import com.hldj.hmyg.R;
import com.hldj.hmyg.adapter.PublishFlowerInfoPhotoAdapter;
import com.hldj.hmyg.bean.Pic;
import com.white.utils.FileUtil;
import com.white.utils.GifImgHelperUtil;
import com.white.utils.ImageTools;
import com.white.utils.SystemSetting;
import com.white.utils.ZzyUtil;
import com.zzy.flowers.activity.photoalbum.PhotoActivity;
import com.zzy.flowers.activity.photoalbum.PhotoAlbumActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/15.
 */

public class TakePhotoUtil {


    private static String flowerInfoPhotoPath = "";
    public static int TO_TAKE_PIC = 100;
    public static final int LOAD_PIC_FAILURE = 4;
    public static final int ADD_NEW_PIC = 5;


    /**
     * 拍照 检测
     */
    public static boolean toTakePic(Activity context) {
        Log.e("toTakePic", "photostatus1");
        String photostatus = Environment.getExternalStorageState();
        Log.e("toTakePic", photostatus);
        if (photostatus.equals(Environment.MEDIA_MOUNTED)) {
            if (!ZzyUtil.ToastForSdcardSpaceEnough(context,
                    true)) {
                // SD卡空间不足
                Toast.makeText(context,
                        R.string.sdcard_is_full, Toast.LENGTH_SHORT).show();
                return false;
            }
            Log.e("toTakePic", "photostatus2");
            return true;
        } else {
            Toast.makeText(context,
                    R.string.sdcard_is_unmount, Toast.LENGTH_SHORT).show();
            Log.e("toTakePic", "photostatus3");
            return false;
        }
    }

    /**
     * 进入拍照
     */
    public static String doTakePhoto(Activity context, int request_code) {

        long str = System.currentTimeMillis();
        String filename = "flower_info_" + str + ".png";
        File photoFile = new File(FileUtil.getFlowerPicPath(""), filename);
        flowerInfoPhotoPath = FileUtil.getFlowerPicPath(filename);
        Intent intent = null;
        try {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            //适配7.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //申请权限
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                //getUriForFile的第二个参数就是Manifest中的authorities
//                videoUri = FileProvider.getUriForFile(context, "com.myself.suifeng.qqredpoint.FileProvider", file);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            }
            context.startActivityForResult(intent, request_code);


        } catch (Exception e) {
            e.printStackTrace();
            D.e(e.getMessage());


            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, flowerInfoPhotoPath);
            Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            context.startActivityForResult(intent, request_code);

//            ToastUtil.showShortToast(R.string.cannot_select_pic);
        }
        Log.e("toTakePic", "doTakePhoto");

        return flowerInfoPhotoPath;
    }


    /**
     * 图片压缩至960像素以内
     */
    public static final int COMPRESS_IMAGE_HEIGHT_PX = 960;
    public static final int COMPRESS_IMAGE_WIDTH_PX = 960;
    /**
     * 新增图片的宽
     */
    public static int newWidth;
    /**
     * 新增图片的高
     */
    public static int newHeight;


    /**
     * 如果是静态图片，则进行压缩处理 压缩并存储临时文件至Image目录
     *
     * @param
     */
    public static String CompressAndSaveImg(File file, int degree,
                                            String sourceImgPath) throws IOException {
        /** 用于压缩的原图Image */
        Bitmap bitmapSourceImg;
        if (degree == 0) {
            bitmapSourceImg = ImageTools.converBitmap(file,
                    COMPRESS_IMAGE_HEIGHT_PX, COMPRESS_IMAGE_WIDTH_PX);
        } else {
            bitmapSourceImg = ImageTools.converBitmap(file,
                    COMPRESS_IMAGE_HEIGHT_PX / 2, COMPRESS_IMAGE_WIDTH_PX / 2);
            bitmapSourceImg = ImageTools.rotate(file.getAbsolutePath(),
                    bitmapSourceImg, degree, COMPRESS_IMAGE_HEIGHT_PX / 2,
                    COMPRESS_IMAGE_WIDTH_PX / 2);
        }
        String img_path = "";
        img_path = FileUtil.getFlowerPicPath("") + "/" + "flower_image_"
                + System.currentTimeMillis() + ".png";
        File filetemp = new File(img_path);
        // 存储临时文件
        if (bitmapSourceImg != null) {
            FileOutputStream out = new FileOutputStream(filetemp);
            bitmapSourceImg.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
        } else {
            return sourceImgPath;
        }
        newWidth = bitmapSourceImg.getWidth();
        newHeight = bitmapSourceImg.getHeight();
        bitmapSourceImg = null;
        return img_path;
    }

    /**
     * 图片太大
     */
    public static final int PIC_IS_TOO_BIG = 3;

    /**
     * 显示图片压缩至160像素以内
     */
    public static final int GRID_COMPRESS_IMAGE_HEIGHT_PX = 160;
    public static final int GRID_COMPRESS_IMAGE_WIDTH_PX = 160;

    public static int checkGif(String sourchImagePath, Context context) throws IOException {

// TODO 还需要做动态图片的预览处理和大小限制
        String image_path = "";
        long imageSize = 0;
        File file = new File(sourchImagePath);
        // 获取原图的宽高
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        Bitmap bm = ImageTools.decodeFile(sourchImagePath, opts,
                TakePhotoUtil.COMPRESS_IMAGE_WIDTH_PX, TakePhotoUtil.COMPRESS_IMAGE_HEIGHT_PX);
        if (bm != null) {
            TakePhotoUtil.newWidth = bm.getWidth();
            TakePhotoUtil.newHeight = bm.getHeight();
        }
        ExifInterface exifInterface = new ExifInterface(sourchImagePath);
        int orc = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                -1);
        // 然后旋转
        int degree = 0;
        if (orc == ExifInterface.ORIENTATION_ROTATE_90) {
            degree = 90;
        } else if (orc == ExifInterface.ORIENTATION_ROTATE_180) {
            degree = 180;
        } else if (orc == ExifInterface.ORIENTATION_ROTATE_270) {
            degree = 270;
        }
        long sourceImgSize = file.length();
        imageSize = sourceImgSize;
        boolean isGif = GifImgHelperUtil.isGif(sourchImagePath);

        /** 如果不是GIF图片 */
        if (!isGif) {
            // SD卡空间足够才压缩
            if (ZzyUtil.ToastForSdcardSpaceEnough(context,
                    false)) {
                image_path = TakePhotoUtil.CompressAndSaveImg(file, degree, sourchImagePath);
                file = new File(image_path);
                imageSize = file.length();
                if (imageSize > sourceImgSize) {
                    image_path = sourchImagePath;
                    file = new File(sourchImagePath);
                    imageSize = sourceImgSize;
                }
            }
        } else {
            image_path = sourchImagePath;
        }
        // 图片不可超过5M，如果压缩成功，则用压缩后图片
        if (imageSize > ImageTools.MAX_IMAGE_SIZE) {
//            handler.sendEmptyMessage(TakePhotoUtil.PIC_IS_TOO_BIG);
            return PIC_IS_TOO_BIG;
        }
        Bitmap showbm = null;
        if (degree == 0) {
            showbm = ImageTools
                    .converBitmap(file, GRID_COMPRESS_IMAGE_HEIGHT_PX,
                            GRID_COMPRESS_IMAGE_WIDTH_PX);
        } else {
            showbm = ImageTools
                    .converBitmap(file, GRID_COMPRESS_IMAGE_HEIGHT_PX,
                            GRID_COMPRESS_IMAGE_WIDTH_PX);
            showbm = ImageTools
                    .rotate(file.getAbsolutePath(), showbm, degree,
                            GRID_COMPRESS_IMAGE_HEIGHT_PX,
                            GRID_COMPRESS_IMAGE_WIDTH_PX);
        }
        if (showbm != null) {
            flowerInfoPhotoPath = image_path;
            Pic pic = new Pic("", false, flowerInfoPhotoPath, 0);
//            adapter.addItem(pic);
//            handler.sendEmptyMessage(ADD_NEW_PIC);

            return ADD_NEW_PIC;
        } else {
//            handler.sendEmptyMessage(LOAD_PIC_FAILURE);
            return LOAD_PIC_FAILURE;
        }


    }


    /**
     * 选择相片
     */
    public static void toChoosePic(Context context, PublishFlowerInfoPhotoAdapter adapter) {
        String picstatus = Environment.getExternalStorageState();
        if (picstatus.equals(Environment.MEDIA_MOUNTED)) {
            if (SystemSetting.getInstance(context).choosePhotoDirId.length() > 0
                    && SystemSetting.isAlbumHasPhoto(
                    context.getContentResolver(),
                    context)) {
                // UpdataImageActivity
                // .startPhotoActivity(
                // UpdataImageActivity.this,
                // PhotoActivity.START_TYPE_JUMP_IN_NOT_FROM_ALBUM,
                // SystemSetting
                // .getInstance(UpdataImageActivity.this).choosePhotoDirId,
                // PhotoActivity.PHOTO_TYPE_PUBLISH_SEED_ATTACH,
                // adapter.getUrlPathsCount(),
                // PhotoActivity.INTENT_NOT_NEED_FOR_RESULT);
                PhotoAlbumActivity.startPhotoAlbumActivity(
                        context,
                        PhotoActivity.PHOTO_TYPE_PUBLISH_SEED_ATTACH,
                        adapter.getUrlPathsCount()
                );
            } else {
                PhotoAlbumActivity.startPhotoAlbumActivity(
                        context,
                        PhotoActivity.PHOTO_TYPE_PUBLISH_SEED_ATTACH,
                        adapter.getUrlPathsCount()
                );
            }
        } else {
            Toast.makeText(context, R.string.sdcard_is_unmount, Toast.LENGTH_SHORT).show();
        }
    }


}

package com.zzy.flowers.activity.photoalbum;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Video;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.child.PublishActivity;
import com.hldj.hmyg.Ui.jimiao.SaveMiaoActivity;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivityChange;
import com.hldj.hmyg.saler.ChoosePhotoGalleryActivity;
import com.hldj.hmyg.saler.CoreActivity;
import com.hldj.hmyg.saler.GlobalConstant;
import com.hldj.hmyg.saler.SaveSeedlingActivity;
import com.hldj.hmyg.saler.UpdataImageActivity;
import com.hldj.hmyg.saler.UpdataImageActivity_bak;
import com.hldj.hmyg.saler.purchase.userbuy.PublishForUserDetailActivity;
import com.hldj.hmyg.util.D;
import com.hy.utils.ToastUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.white.utils.ExpandViewTouchUtil;
import com.white.utils.FileTypeUtil;
import com.white.utils.FileUtil;
import com.white.utils.GifImgHelperUtil;
import com.white.utils.GlobalData;
import com.white.utils.ImageTools;
import com.white.utils.ZzyUtil;
import com.zzy.flowers.iupdate.image.IThumbnailUpdate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import static android.provider.MediaStore.Images.Media.query;


public class PhotoActivity extends CoreActivity implements IThumbnailUpdate {

    public static final String INTENT_CHOOSE_PHOTOS = "intent_choose_photos";
    public static final String INTENT_CHOOSE_VIDEOS = "intent_choose_videos";
    public static final String INTENT_CHOOSE_TYPE = "intent_choose_type";

    public static final String INTENT_START_TYPE_KEY = "intent_start_type";
    public static final String INTENT_DIR_ID_KEY = "intent_dir_id";
    public static final String INTENT_PHOTO_TYPE_KEY = "intent_photo_type";
    public static final String INTENT_HAD_CHOOSE_PHOTO_KEY = "intent_had_choose_photo";
    public static final String INTENT_HAD_CHOOSE_VIDEO_KEY = "intent_had_choose_video";

    public static final int INTENT_NOT_NEED_FOR_RESULT = -1;

    public static final int START_TYPE_JUMP_IN_NOT_FROM_ALBUM = 0;
    public static final int START_TYPE_JUMP_IN_FROM_ALBUM = 1;

    public static final int PHOTO_TYPE_PUBLISH_SEED_ATTACH = 0;
    public static final int PHOTO_TYPE_PRICE_ATTACH = 1;

    public static final int TO_LOAD_IMAGE_OVER = 10;
    public static final int REFRESH_PHOTO_VIEW = 11;
    public static final int ADD_PIC_FINISH = 12;
    public static final int COMPRESS_PHOTO_OVER = 15;
    public static final int COMPRESS_PHOTO_OVER_BY_GALLERY = 16;

    public static final int TO_CHOOSE_NEW_PIC = 20;

    public static final int MAX_IMAGE_COUNT = 9;

    public static PhotoActivity instance = null;

    private TextView titleTv;
    private String titleStr = "";
    private Button backBtn;
    private Button cancelBtn;
    /**
     * 预览按钮
     */
    private Button previewBtn;
    /**
     * 已选中图片
     */
    private TextView numTv;
    /**
     * 发送按钮
     */
    private Button sendBtn;

    /**
     * 进入界面的方式类型 0: 直接进入 1: 由相册进入
     */
    private int startType;
    /**
     * 相片类型 0:发布苗木资源图片 1:报价图片
     */
    private int photoType;
    /**
     * 当前已选中的图片个数
     */
    private int hadChoosePicCount = 0;
    /**
     * 当前相册路径ID
     */
    private String dirId = "";

    private GridView photoGv;
    private PhotoAdapter adapter;
    private List<PhotoItem> dataList = new ArrayList<PhotoItem>();

    /**
     * 添加选中的图片
     */
    private List<PhotoItem> checkList = new ArrayList<PhotoItem>();

    /**
     * 确定发送的图片
     */
    private ArrayList<Pic> resultPathList = new ArrayList<Pic>();

    private LoadHandler mHandler;

    /**
     * log4j对象
     */
    private static final Logger logger = Logger.getLogger("");

    private boolean isSendSourcePic = false;
    private String chooseType;


    public static void startPhotoActivity(Activity context, int startType,
                                          String dirId, int photoType, int hadChoosePicCount, int requestCode, String chooseType) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra(INTENT_START_TYPE_KEY, startType);
        intent.putExtra(INTENT_DIR_ID_KEY, dirId);
        intent.putExtra(INTENT_PHOTO_TYPE_KEY, photoType);
        intent.putExtra(INTENT_HAD_CHOOSE_PHOTO_KEY, hadChoosePicCount);
        intent.putExtra(INTENT_CHOOSE_TYPE, chooseType);

        if (requestCode != INTENT_NOT_NEED_FOR_RESULT) {
            context.startActivityForResult(intent, requestCode);
        } else {
            context.startActivity(intent);
        }

    }

    @Override
    protected void doOnCreate(Bundle savedInstanceState) {
        // super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_choose_layout);

        startType = getIntent().getIntExtra(INTENT_START_TYPE_KEY,
                START_TYPE_JUMP_IN_NOT_FROM_ALBUM);
        dirId = getIntent().getStringExtra(INTENT_DIR_ID_KEY);
        photoType = getIntent().getIntExtra(INTENT_PHOTO_TYPE_KEY,
                PHOTO_TYPE_PUBLISH_SEED_ATTACH);
        hadChoosePicCount = getIntent().getIntExtra(
                INTENT_HAD_CHOOSE_PHOTO_KEY, 0);

        /* 选择 标志   选择视频 还是选择图片   */
        chooseType = getIntent().getStringExtra(INTENT_CHOOSE_TYPE);

        instance = this;

        initView();
        initListener();
        initContent();
    }

    private void initView() {
        titleTv = (TextView) findViewById(R.id.common_titlebar_titleName);
        backBtn = (Button) findViewById(R.id.common_titlebar_leftBtn);
        cancelBtn = (Button) findViewById(R.id.common_titlebar_rightBtn);

        photoGv = (GridView) findViewById(R.id.choose_photo_gridview);
        previewBtn = (Button) findViewById(R.id.choose_photo_preview_btn);
        numTv = (TextView) findViewById(R.id.choose_photo_num_tv);
        sendBtn = (Button) findViewById(R.id.choose_photo_send_btn);

        backBtn.setText("返回");
        cancelBtn.setText(R.string.cancel);
        numTv.setVisibility(View.GONE);
    }

    private void initListener() {
        mHandler = new LoadHandler(getMainLooper());

        BtnOnClickListnener listener = new BtnOnClickListnener();
        backBtn.setOnClickListener(listener);
        cancelBtn.setOnClickListener(listener);
        previewBtn.setOnClickListener(listener);
        sendBtn.setOnClickListener(listener);
    }

    private void initContent() {
        refreshView();
        showDialog(false);
        ThumbnailAddManage.getThumbnailInstance().setiThumbnailCallback(this);
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (chooseType.equals(INTENT_CHOOSE_VIDEOS)) {
                    setVideoData();
                } else {
                    setPhotoData();
                }
                mHandler.sendEmptyMessage(TO_LOAD_IMAGE_OVER);
            }
        }).start();

        ExpandViewTouchUtil.expandViewTouchDelegate(backBtn, 30, 30, 20, 30);
    }

    /**
     * 获取相册信息
     */
    private void setPhotoData() {
        Cursor cursor = query(getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                FileTypeUtil.STORE_IMAGES, MediaStore.Images.Media.BUCKET_ID
                        + " = " + dirId, null);
        PhotoItem photoitem = null;
        while (cursor.moveToNext()) {
            long id = Integer.valueOf(cursor.getString(1));
            titleStr = cursor.getString(3);
            String path = cursor.getString(4);
            photoitem = new PhotoItem(id, path);
            photoitem.picSize = cursor.getLong(5);
            dataList.add(photoitem);
        }
        cursor.close();
        Collections.sort(dataList);
    }


    /**
     * 获取 video 视频信息
     */
    private void setVideoData() {
//        queryAllVideo(this);
        // 直接查询全部  无法 根据 条件查找
//        Cursor cursor = MediaStore.Video.query(
//                getContentResolver(),
//                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null);

//        Cursor cursor = query(
//                getContentResolver(),
//                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                FileTypeUtil.STORE_VIDEOS,
//                MediaStore.Video.VideoColumns .MIME_TYPE   + " = " + dirId  ,
//                null);

        Cursor cursor = getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                FileTypeUtil.STORE_VIDEOS,
                Video.VideoColumns.MIME_TYPE + "=? " + " and " + Video.VideoColumns.DURATION + "<=? ", new String[]{"video/mp4", "300000"},
                Video.DEFAULT_SORT_ORDER
        );

//  MediaStore.Video.Media.BUCKET_ID + " =? ",
//        new String[]{dirId}
        //查询数据库，参数分别为（路径，要查询的列名，条件语句，条件参数，排序）
//        cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);


        PhotoItem videoitem = null;


        while (cursor.moveToNext()) {
//            String path = cursor.getString(cursor
//                    .getColumnIndex(MediaStore.Video.Media.DATA));
//            long id = Integer.valueOf(cursor.getString(1));
            int idColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media._ID);
            long id = Integer.valueOf(cursor.getString(idColumnIndex));


/**
 * MediaStore.Images.Media.DISPLAY_NAME, // 显示的名称
 MediaStore.Images.Media._ID, // ID
 MediaStore.Images.Media.BUCKET_ID, // dir id 目录ID
 MediaStore.Images.Media.BUCKET_DISPLAY_NAME, // dir name 目录名字
 MediaStore.Images.Media.DATA, // 目录的路径
 MediaStore.Images.Media.SIZE // 文件的大小
 */
            int titleStrColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME);
            titleStr = cursor.getString(titleStrColumnIndex);

            int pathStrColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media.DATA);
            String path = cursor.getString(pathStrColumnIndex);//video/mp4

            if (!new File(path).exists()) {
                Log.i(TAG, "setVideoData: 视频不存在 或者已经损坏");
                continue;
            }

            videoitem = new PhotoItem(id, path);


            videoitem.video_image_path = getImagePath(id, instance);
            if (TextUtils.isEmpty(videoitem.video_image_path)) {
                refreshPhotoo(id, instance);
                videoitem.video_image_path = getImagePath(id, instance);
                Log.i(TAG, "setVideoData:相册刷新 ");
            }


            Log.i(TAG, "setVideoData:video_image_path " + videoitem.video_image_path);


            int duration_index = cursor.getColumnIndex(Video.Media.DURATION);
            long duration = Integer.valueOf(cursor.getString(duration_index));

            int picSizeColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media.SIZE);
            videoitem.picSize = cursor.getLong(picSizeColumnIndex);
            videoitem.type = "video";
            videoitem.duration = duration;
            dataList.add(videoitem);
//            dataList.add(path);
        }
        cursor.close();
        Collections.sort(dataList);
    }


    public static void refreshPhotoo(long videoId, Context mContext) {
        MediaStore.Video.Thumbnails.getThumbnail(mContext.getContentResolver(), videoId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
    }

    public static String getImagePath(long videoId, Context mContext) {


        //提前生成缩略图，再获取：http://stackoverflow.com/questions/27903264/how-to-get-the-video-thumbnail-path-and-not-the-bitmap
//       MediaStore.Video.Thumbnails.getThumbnail(getContentResolver(), videoId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
        String[] projection = {MediaStore.Video.Thumbnails._ID, MediaStore.Video.Thumbnails.DATA};
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI
                , projection
                , MediaStore.Video.Thumbnails.VIDEO_ID + "=?"
                , new String[]{videoId + ""}
                , null);
        String thumbPath = "";

        assert cursor != null;
        while (cursor.moveToNext()) {
            thumbPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
        }
        cursor.close();


        return thumbPath;

    }


    /**
     * 增加选中图片
     */
    public void addCheckItem(PhotoItem item) {
        checkList.add(item);
    }

    public void refreshView() {
        if (checkList.size() < 1) {
            previewBtn.setEnabled(false);
            sendBtn.setEnabled(false);
            numTv.setVisibility(View.GONE);
        } else {
            previewBtn.setEnabled(true);
            sendBtn.setEnabled(true);
            numTv.setVisibility(View.VISIBLE);
            numTv.setText(checkList.size() + "");
        }
    }

    public void refreshAll() {
        if (adapter != null)
            adapter.refreshView();
    }

    /**
     * 删除选中图片
     */
    public void delCheckItem(PhotoItem photoitem) {
        for (PhotoItem item : checkList) {
            if (item.getPhotoId() == photoitem.getPhotoId()) {
                checkList.remove(item);
                break;
            }
        }
    }

    public int getCheckCount() {
        return checkList.size();
    }

    public boolean isCheckByPhotoId(long photoId) {
        boolean result = false;
        for (PhotoItem item : checkList) {
            if (item.getPhotoId() == photoId) {
                result = true;
                break;
            }
        }
        return result;
    }

    private class BtnOnClickListnener implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.common_titlebar_leftBtn:
                    if (startType == START_TYPE_JUMP_IN_NOT_FROM_ALBUM) {
                        PhotoAlbumActivity.startPhotoAlbumActivity(
                                PhotoActivity.this, photoType, hadChoosePicCount);
                    }
                    PhotoActivity.this.finish();
                    break;
                case R.id.common_titlebar_rightBtn:
                    Intent cancelIntent = new Intent();
                    setResult(RESULT_OK, cancelIntent);
                    PhotoActivity.this.finish();
                    break;
                case R.id.choose_photo_preview_btn:
                    GlobalData.galleryImageData = new ArrayList<PhotoItem>();
                    for (PhotoItem item : checkList) {
                        GlobalData.galleryImageData.add(item);
                    }
                    ChoosePhotoGalleryActivity.startChoosePhotoGalleryActivity(
                            PhotoActivity.this, 0, isSendSourcePic,
                            TO_CHOOSE_NEW_PIC);
                    break;
                case R.id.choose_photo_send_btn:
                    // TODO 发送
                    showDialog(false);
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            compressPhotos();
                            mHandler.sendEmptyMessage(COMPRESS_PHOTO_OVER);
                        }
                    }).start();
                    break;
                default:
                    break;
            }
        }
    }

    public boolean checkIsPicsSizeMax() {
        boolean result = false;
        for (PhotoItem item : checkList) {
            if (item.picSize > ImageTools.MAX_IMAGE_SIZE) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TO_CHOOSE_NEW_PIC && resultCode == RESULT_OK) {
            mHandler.sendEmptyMessage(COMPRESS_PHOTO_OVER_BY_GALLERY);
        }
    }


    /* 发送一个 视频 地址出来 */
//    public void sendVideo(String path) {
//        if (PublishActivity.instance != null) {
//            PublishActivity.instance.addVideo(path);
//        }
//    }

    public void sendPhotos() {

        if (UpdataImageActivity.instance != null) {
            UpdataImageActivity.instance.addPicUrls(resultPathList);
        }
        if (SaveSeedlingActivity.instance != null) {
            SaveSeedlingActivity.instance.addPicUrls(resultPathList);
        }
        if (PurchaseDetailActivityChange.instance != null) {
            PurchaseDetailActivityChange.instance.addPicUrls(resultPathList);
        }

        if (SaveMiaoActivity.instance != null) {
            SaveMiaoActivity.instance.addPicUrls(resultPathList);
        }
//        if (SaveMiaoActivity.instance != null) {
//            SaveMiaoActivity.instance.addPicUrls(resultPathList);
//        }

        if (UpdataImageActivity_bak.instance != null) {
            UpdataImageActivity_bak.instance.addPicUrls(resultPathList);
        }

        if (PublishActivity.instance != null) {
            PublishActivity.instance.addPicUrls(resultPathList);
        }


        if (PublishForUserDetailActivity.instance != null) {
            PublishForUserDetailActivity.instance.addPicUrls(resultPathList);
        }


//        else if (PurchaseDetailActivityBase.instance != null) {
//            PurchaseDetailActivityBase.instance.addPicUrls(resultPathList);
//        }

        // if (photoType == PHOTO_TYPE_PUBLISH_SEED_ATTACH) {
        // TODO 发表图片的添加
        // if (PublishFlowerInfoFragment.instance != null) {
        // PublishFlowerInfoFragment.instance.addPicUrls(resultPathList);
        // }
        // } else {
        // TODO 报价图片的添加
        // if (PriceImageActivity.instance != null)
        // PriceImageActivity.instance.addPicUrls(resultPathList);
        // }
    }

    public void compressPhotos() {
        resultPathList = new ArrayList<Pic>();
        for (int i = 0; i < checkList.size(); i++) {
            PhotoItem item = checkList.get(i);
            if (!isSendSourcePic) {
                String path = null;
                try {
                    path = getNewImagePath(item.getPath(), i);
                } catch (IOException e) {
                    e.printStackTrace();
                    path = null;
                }
                if (path != null) {
                    resultPathList.add(new Pic("", false, path, 0));
                } else {
                    resultPathList.add(new Pic("", false, item.getPath(), 0));
                }
            } else {
                resultPathList.add(new Pic("", false, item.getPath(), 0));
            }
        }
    }

    public boolean isSendSourcePic() {
        return isSendSourcePic;
    }

    public void setSendSourcePic(boolean isSendSourcePic) {
        this.isSendSourcePic = isSendSourcePic;
    }

    /**
     * 添加图片
     *
     * @param sourchImagePath
     */
    private String getNewImagePath(String sourchImagePath, int pos)
            throws IOException {
        // 还需要做图片的预览处理和大小限制
        String image_path = "";
        long imageSize = 0;
        File file = new File(sourchImagePath);
        // 获取原图的宽高
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
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
            if (ZzyUtil.ToastForSdcardSpaceEnough(PhotoActivity.this, false)) {
                image_path = CompressAndSaveImg(file, degree, pos);
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    ToastUtil.showShortToast(PhotoActivity.this,
                            R.string.image_size_is_too_big_to_send);
                }
            });
            return null;
        }
        return image_path;
    }

    /**
     * 如果是静态图片，则进行压缩处理 压缩并存储临时文件至Image目录
     *
     * @param file
     */
    private String CompressAndSaveImg(File file, int degree, int pos)
            throws IOException {
        /** 用于压缩的原图Image */
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = format.format(date) + "_" + pos;
        Bitmap bitmapSourceImg;
        if (degree == 0) {
            bitmapSourceImg = ImageTools.converBitmap(file,
                    ImageTools.COMPRESS_IMAGE_HEIGHT_PX,
                    ImageTools.COMPRESS_IMAGE_WIDTH_PX);
        } else {
            bitmapSourceImg = ImageTools.converBitmap(file,
                    ImageTools.COMPRESS_IMAGE_HEIGHT_PX / 2,
                    ImageTools.COMPRESS_IMAGE_WIDTH_PX / 2);
            bitmapSourceImg = ImageTools.rotate(file.getAbsolutePath(),
                    bitmapSourceImg, degree,
                    ImageTools.COMPRESS_IMAGE_HEIGHT_PX / 2,
                    ImageTools.COMPRESS_IMAGE_WIDTH_PX / 2);
        }
        String img_path = "";
        img_path = FileUtil.mkDirs(GlobalConstant.IMAGE_DIR) + "/" + "image_"
                + str + ".jpg";
        File filetemp = new File(img_path);
        // 存储临时文件
        if (bitmapSourceImg != null) {
            FileOutputStream out = new FileOutputStream(filetemp);
            bitmapSourceImg.compress(Bitmap.CompressFormat.JPEG, 82, out);
            out.close();
        } else {
            return file.getAbsolutePath();
        }
        return img_path;
    }

    private class LoadHandler extends Handler {

        public LoadHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TO_LOAD_IMAGE_OVER:
                    hideDialog();
                    titleTv.setText(titleStr);
                    adapter = new PhotoAdapter(PhotoActivity.this, dataList, isSendSourcePic);
                    photoGv.setAdapter(adapter);
                    break;
                case REFRESH_PHOTO_VIEW:
                    adapter.notifyDataSetChanged();
                    break;
                case ADD_PIC_FINISH:
                    logger.info("add pic finish");
                    hideDialog();
                    logger.info("hideDialog finish");
                    Intent completeIntent = new Intent();
                    setResult(RESULT_OK, completeIntent);
                    PhotoActivity.this.finish();
                    break;
                case COMPRESS_PHOTO_OVER:
                    sendPhotos();
                    hideDialog();
                    Intent overIntent = new Intent();
                    setResult(RESULT_OK, overIntent);
                    PhotoActivity.this.finish();
                    break;
                case COMPRESS_PHOTO_OVER_BY_GALLERY:
                    hideDialog();
                    Intent galleryOverIntent = new Intent();
                    setResult(RESULT_OK, galleryOverIntent);
                    PhotoActivity.this.finish();
                    break;
                default:
                    break;
            }
        }

    }

    public boolean isNotOutOfLimitCount() {
        return (getCheckCount() + hadChoosePicCount) < MAX_IMAGE_COUNT;
    }

    @Override
    public void finish() {
        instance = null;
        super.finish();
    }

    @Override
    public void updateThumbnailView(long photoId) {

        D.i("updateThumbnailView");
        Iterator<PhotoItem> photoItemIterator = dataList.iterator();

        try {
            while (photoItemIterator.hasNext()) {
                PhotoItem item = photoItemIterator.next();
                if (photoId == item.getPhotoId()) {
                    item.setIsHadThumbnail(true);
                    item.isLoadingThumbnailBm = false;
                    mHandler.sendEmptyMessage(REFRESH_PHOTO_VIEW);
                    break;
                }
            }
        } catch (Exception e) {
            CrashReport.postCatchedException(e);
            Log.w(TAG, "updateThumbnailView: " + e.getMessage());
        }


//        for (PhotoItem item : dataList) {
//            if (photoId == item.getPhotoId()) {
//                item.setIsHadThumbnail(true);
//                item.isLoadingThumbnailBm = false;
//                mHandler.sendEmptyMessage(REFRESH_PHOTO_VIEW);
//                break;
//            }
//        }


    }


    private static final String TAG = "PhotoActivity";

    public ArrayList<PhotoItem> queryAllVideo(final Context context) {
        if (context == null) { //判断传入的参数的有效性
            return null;
        }
        ArrayList<PhotoItem> videos = new ArrayList<PhotoItem>();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = null;
        try {
            //查询数据库，参数分别为（路径，要查询的列名，条件语句，条件参数，排序）
            cursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    PhotoItem video = new PhotoItem(100, "bb");
                    video.setPhotoId(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID))); //获取唯一id
                    video.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))); //文件路径
                    video.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))); //文件名
                    //...   还有很多属性可以设置
                    //可以通过下一行查看属性名，然后在Video.Media.里寻找对应常量名
                    Log.i(TAG, "queryAllImage --- all column name --- " + cursor.getColumnName(cursor.getPosition()));

                    //获取缩略图（如果数据量大的话，会很耗时——需要考虑如何开辟子线程加载）
                /*
                 * 可以访问android.provider.MediaStore.Video.Thumbnails查询图片缩略图
                 * Thumbnails下的getThumbnail方法可以获得图片缩略图，其中第三个参数类型还可以选择MINI_KIND
                 */
                    Bitmap thumbnail = MediaStore.Video.Thumbnails.getThumbnail(resolver, video.getPhotoId(), Video.Thumbnails.MICRO_KIND, null);
//                    video.setThumbnail(thumbnail);
                    videos.add(video);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return videos;
    }


}

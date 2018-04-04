package com.zzy.flowers.activity.photoalbum;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.saler.CoreActivity;
import com.hy.utils.ToastUtil;
import com.white.utils.ExpandViewTouchUtil;
import com.white.utils.FileTypeUtil;
import com.white.utils.SystemSetting;
import com.zzy.flowers.iupdate.image.IThumbnailUpdate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotoAlbumActivity extends CoreActivity implements
        IThumbnailUpdate {

    private static final int TO_CHOOSE_PHOTO = 1;
//    private static final int TO_CHOOSE_VIDEO = 2;

    public static final int TO_LOAD_IMAGE_OVER = 10;
    public static final int TO_LOAD_IMAGE_ERROR = 11;
    public static final int REFRESH_PHOTOALBUM_VIEW = 12;

    private TextView titleTv;
    private Button backBtn;
    private Button rightBtn;

    /**
     * 相片类型 0:发布苗木资源图片 1:报价图片
     */
    private int photoType;
    /**
     * 当前已选中的图片个数
     */
    private int hadChoosePicCount = 0;

    private boolean isChooseVideos = false;

    private GridView photoAlbumGv;
    private PhotoAlbumAdapter adapter;
    private List<PhotoAlbumItem> dataList = new ArrayList<PhotoAlbumItem>();

    private LoadHandler mHandler;


    public static void startPhotoAlbumActivity(Context context, int photoType,
                                               int hadChoosePicCount) {
        Intent intent = new Intent(context, PhotoAlbumActivity.class);
        intent.putExtra(PhotoActivity.INTENT_PHOTO_TYPE_KEY, photoType);
        intent.putExtra(PhotoActivity.INTENT_HAD_CHOOSE_PHOTO_KEY,
                hadChoosePicCount);
        context.startActivity(intent);
    }


    public static void startPhotoAlbumActivityVideo(Context context, int photoType,
                                                    int hadChoosePicCount, boolean isChooseVideos) {
        Intent intent = new Intent(context, PhotoAlbumActivity.class);
        intent.putExtra(PhotoActivity.INTENT_PHOTO_TYPE_KEY, photoType);
        intent.putExtra(PhotoActivity.INTENT_HAD_CHOOSE_PHOTO_KEY,
                hadChoosePicCount);

        intent.putExtra(PhotoActivity.INTENT_HAD_CHOOSE_VIDEO_KEY, isChooseVideos);

        context.startActivity(intent);


    }

    @Override
    protected void doOnCreate(Bundle savedInstanceState) {
        // super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_album_layout);
        photoType = getIntent().getIntExtra(
                PhotoActivity.INTENT_PHOTO_TYPE_KEY,
                PhotoActivity.PHOTO_TYPE_PUBLISH_SEED_ATTACH);
        hadChoosePicCount = getIntent().getIntExtra(
                PhotoActivity.INTENT_HAD_CHOOSE_PHOTO_KEY, 0);

        isChooseVideos = getIntent().getBooleanExtra(
                PhotoActivity.INTENT_HAD_CHOOSE_VIDEO_KEY, false);


        initView();
        initListener();
        initContent();
    }

    private void initView() {
        titleTv = (TextView) findViewById(R.id.common_titlebar_titleName);
        backBtn = (Button) findViewById(R.id.common_titlebar_leftBtn);
        rightBtn = (Button) findViewById(R.id.common_titlebar_rightBtn);

        titleTv.setText(R.string.photo_album_title);
        backBtn.setText("返回");
        rightBtn.setVisibility(View.INVISIBLE);

        photoAlbumGv = (GridView) findViewById(R.id.album_gridview);
    }

    private void initListener() {
        mHandler = new LoadHandler(getMainLooper());

        backBtn.setOnClickListener(new BtnOnClickListener());
        photoAlbumGv.setOnItemClickListener(new AlbumOnItemClickListener());
    }

    private void initContent() {
        showDialog(false);
        ThumbnailAddManage.getThumbnailInstance().setiThumbnailCallback(this);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    setPhotoAlbum();
                    mHandler.sendEmptyMessage(TO_LOAD_IMAGE_OVER);
                } catch (Exception e) {
                    mHandler.sendEmptyMessage(TO_LOAD_IMAGE_ERROR);
                    e.printStackTrace();
                }
            }
        }).start();

        ExpandViewTouchUtil.expandViewTouchDelegate(backBtn, 30, 30, 20, 30);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == TO_CHOOSE_PHOTO) {
            PhotoAlbumActivity.this.finish();
        }
    }

    private class BtnOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.common_titlebar_leftBtn:
                    PhotoAlbumActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    }

    private class AlbumOnItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SystemSetting.getInstance(PhotoAlbumActivity.this).setChoosePhotoDirId(
                    dataList.get(position).getDirId());
//            int request ;
//            if (position == 0) {
//                request = TO_CHOOSE_VIDEO;
//            } else {
//                request = TO_CHOOSE_PHOTO;
//            }

            if (hadChoosePicCount == 0 && isChooseVideos) {
            PhotoActivity.startPhotoActivity(PhotoAlbumActivity.this,
                    PhotoActivity.START_TYPE_JUMP_IN_FROM_ALBUM,
                    dataList.get(position).getDirId(), photoType,
                    hadChoosePicCount, TO_CHOOSE_PHOTO, position == 0 ? PhotoActivity.INTENT_CHOOSE_VIDEOS : PhotoActivity.INTENT_CHOOSE_PHOTOS);
        } else {
            PhotoActivity.startPhotoActivity(PhotoAlbumActivity.this,
                    PhotoActivity.START_TYPE_JUMP_IN_FROM_ALBUM,
                    dataList.get(position).getDirId(), photoType,
                    hadChoosePicCount, TO_CHOOSE_PHOTO, PhotoActivity.INTENT_CHOOSE_PHOTOS);
        }


    }
    }

    /**
     * 获取相册信息
     */
    private void setPhotoAlbum() {
        Cursor cursor = MediaStore.Images.Media.query(getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                FileTypeUtil.STORE_IMAGES);
        Map<String, PhotoAlbumItem> countMap = new HashMap<String, PhotoAlbumItem>();
        PhotoAlbumItem pa = null;
        while (cursor.moveToNext()) {
            long id = Integer.parseInt(cursor.getString(1));
            String dir_id = cursor.getString(2);
            String dir = cursor.getString(3);
            String path = cursor.getString(4);
            if (!countMap.containsKey(dir_id)) {
                pa = new PhotoAlbumItem();
                pa.setName(dir);
                pa.setDirId(dir_id);
                pa.setPhotoId(id);
                pa.setCount(1);
                pa.setAlbumPath(path);
                countMap.put(dir_id, pa);
            } else {
                pa = countMap.get(dir_id);
                pa.setCount(pa.getCount() + 1);
                if (pa.getPhotoId() < id) {
                    pa.setPhotoId(id);
                    pa.setAlbumPath(path);
                }
            }
        }
        cursor.close();
        Iterable<String> it = countMap.keySet();
        for (String key : it) {
            dataList.add(countMap.get(key));
        }
        Collections.sort(dataList);


        /* 如果没有选择 图片。显示视频  如果有图片了。就不显示 视频    */
        if (hadChoosePicCount == 0 && isChooseVideos) {
            //        PhotoAlbumItem albumItem = dataList.get(0);
            PhotoAlbumItem albumItem = new PhotoAlbumItem("", "所有视频", 10, 100, "");
            albumItem.setName("所有视频");
            dataList.add(0, albumItem);
        }


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
                    adapter = new PhotoAlbumAdapter(PhotoAlbumActivity.this,
                            dataList);
                    adapter.setSelectCount(hadChoosePicCount);
                    adapter.setChooseVideos(isChooseVideos);
                    photoAlbumGv.setAdapter(adapter);
                    break;
                case REFRESH_PHOTOALBUM_VIEW:
                    adapter.notifyDataSetChanged();
                    break;
                case TO_LOAD_IMAGE_ERROR:
                    hideDialog();
                    ToastUtil.showShortToast(PhotoAlbumActivity.this,
                            R.string.get_photo_album_failure);
                    adapter = new PhotoAlbumAdapter(PhotoAlbumActivity.this,
                            dataList);
                    photoAlbumGv.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void updateThumbnailView(long photoId) {
        for (PhotoAlbumItem item : dataList) {
            if (photoId == item.getPhotoId()) {
                item.setIsHadThumbnail(true);
                item.isLoadingThumbnailBm = false;
                mHandler.sendEmptyMessage(REFRESH_PHOTOALBUM_VIEW);
                break;
            }
        }
    }
}

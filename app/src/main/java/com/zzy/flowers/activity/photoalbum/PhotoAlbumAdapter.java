package com.zzy.flowers.activity.photoalbum;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.white.utils.FileTypeUtil;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

public class PhotoAlbumAdapter extends BaseAdapter {

    private List<PhotoAlbumItem> dataList = new ArrayList<PhotoAlbumItem>();
    private ViewHolder holder;
    private LayoutInflater inflater;
    private Context context;

    private int selectCount = 0;

    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }

    /* 判断是否选择视频 */
    private boolean isChooseVideos = false;

    public void setChooseVideos(boolean chooseVideos) {
        isChooseVideos = chooseVideos;
    }

    public PhotoAlbumAdapter(Context context, List<PhotoAlbumItem> dataList) {
        super();
        this.dataList = dataList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PhotoAlbumItem item = dataList.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.photo_album_item, null);
            holder = new ViewHolder();
            holder.photoAlbumIv = (ImageView) convertView
                    .findViewById(R.id.photo_album_iamge_iv);
            holder.photoAlbumNumTv = (TextView) convertView
                    .findViewById(R.id.photo_album_num_tv);
            holder.photoAlbumNameTv = (TextView) convertView
                    .findViewById(R.id.photo_album_path_name_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


//        if (isChooseVideos)
//        {



            if ((position != 0 || selectCount != 0) ||  !isChooseVideos) {
//            显示相册
                // holder.photoAlbumIv.setImageBitmap(item.getThumbnailBm());
                /** 通过ID 获取缩略图 */
                Bitmap thumbnail = item.getThumbnailBm();
                /** 通过ID 获取缩略图 */
                if (thumbnail != null) {
                    item.setIsHadThumbnail(true);
                    holder.photoAlbumIv.setImageBitmap(thumbnail);
                } else {
                    item.setIsHadThumbnail(false);
                    holder.photoAlbumIv
                            .setImageResource(R.drawable.un_down_load_pic_icon);
                    if (!item.isLoadingThumbnailBm) {
                        item.isLoadingThumbnailBm = true;
                        ThumbnailAddManage.getThumbnailInstance()
                                .addNewThumbnailToCache(context, item.getAlbumPath(),
                                        item.getPhotoId());
                    }
                }
            } else {
// 显示  视频
                Cursor cursor = context.getContentResolver().query(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        FileTypeUtil.STORE_VIDEOS,
                        MediaStore.Video.VideoColumns.MIME_TYPE + "=? " + " and " + MediaStore.Video.VideoColumns.DURATION + "<=? ", new String[]{"video/mp4", "300000"},
                        MediaStore.Video.DEFAULT_SORT_ORDER
                );

                if (cursor != null) {
                    item.setCount(cursor.getCount());
                    if (cursor.moveToLast()) {

                        int idColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media._ID);
                        long id = Integer.valueOf(cursor.getString(idColumnIndex));

                        int pathStrColumnIndex = cursor.getColumnIndex(MediaStore.Video.Media._ID);
                        ;

//                    String path = cursor.getString(pathStrColumnIndex);//video/mp4
                        FinalBitmap.create(context)
                                .display(holder.photoAlbumIv, PhotoActivity.getImagePath(id, context));
                    }
                }


            }


//        }



        if (item.getCount() < 999) {
            holder.photoAlbumNumTv.setText("" + item.getCount());
        } else {
            holder.photoAlbumNumTv.setText("999+");
        }
        holder.photoAlbumNameTv.setText(item.getName());
        return convertView;
    }


    private class ViewHolder {
        public ImageView photoAlbumIv;
        public TextView photoAlbumNumTv;
        public TextView photoAlbumNameTv;
    }

}

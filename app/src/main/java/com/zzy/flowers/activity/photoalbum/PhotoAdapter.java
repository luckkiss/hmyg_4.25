package com.zzy.flowers.activity.photoalbum;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hldj.hmyg.R;
import com.hldj.hmyg.saler.ChoosePhotoGalleryActivity;
import com.white.utils.AndroidUtil;
import com.white.utils.GlobalData;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends BaseAdapter {

    private List<PhotoItem> dataList = new ArrayList<PhotoItem>();
    private LayoutInflater inflater;
    private ViewHolder holder;
    private int itemWidth;
    private Context context;

    public PhotoAdapter(Context context, List<PhotoItem> dataList) {
        super();
        this.dataList = dataList;
        this.context = context;
        itemWidth = (GlobalData.getScreenWidth(false, context) - AndroidUtil.dip2px(context, 51)) / 4;
        inflater = LayoutInflater.from(context);
    }

    public void refreshView() {
        PhotoActivity.instance.refreshView();
        notifyDataSetChanged();
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
        holder = new ViewHolder();
        PhotoItem item = dataList.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.photo_choose_item, null);
            holder.imageIv = (ImageView) convertView
                    .findViewById(R.id.photo_choose_iv);

//            holder.isvideo = (TextView) convertView
//                    .findViewById(R.id.isvideo);

            holder.checkIv = (CheckBox) convertView
                    .findViewById(R.id.photo_choose_check_iv);
            LayoutParams params = holder.imageIv.getLayoutParams();
            params.height = itemWidth;
            params.width = itemWidth;
            holder.imageIv.setLayoutParams(params);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        holder.isvideo.setText("是否视频：-> " + item.type);


//        if (item.type.equals("video")) {

//            FinalBitmap.create(context).display(holder.imageIv, item.video_image_path);

//        }else {
            /** 通过ID 获取缩略图 */
            Bitmap thumbnail = item.getThumbnailBm();
            /** 通过ID 获取缩略图 */
            if (thumbnail != null) {
                item.setIsHadThumbnail(true);
                holder.imageIv.setImageBitmap(thumbnail);
            } else {
                item.setIsHadThumbnail(false);
                holder.imageIv.setImageResource(R.drawable.un_down_load_pic_icon);
                if (!item.isLoadingThumbnailBm) {
                    item.isLoadingThumbnailBm = true;
                    ThumbnailAddManage.getThumbnailInstance()
                            .addNewThumbnailToCache(PhotoActivity.instance,
                                    item.picPath, item.getPhotoId());
                }
            }
//        }


        holder.checkIv.setChecked(item.isSelect());

        BtnOnClickListener listener = new BtnOnClickListener(position);
        holder.checkIv.setOnClickListener(listener);
        holder.imageIv.setOnClickListener(listener);
        return convertView;
    }

    private class BtnOnClickListener implements OnClickListener {

        private int position;

        public BtnOnClickListener(int position) {
            super();
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.photo_choose_check_iv:
                    PhotoItem item = dataList.get(position);
                    if (PhotoActivity.instance.isNotOutOfLimitCount()
                            && ((CheckBox) v).isChecked()) {
                        item.setSelect(((CheckBox) v).isChecked());
                        PhotoActivity.instance.addCheckItem(item);
                    } else if (!((CheckBox) v).isChecked()) {
                        item.setSelect(((CheckBox) v).isChecked());
                        PhotoActivity.instance.delCheckItem(item);
                    } else {
                        Toast.makeText(context, R.string.choose_pic_is_full, Toast.LENGTH_SHORT).show();
                    }
                    refreshView();
                    break;
                case R.id.photo_choose_iv:
                    toPreviewImages();
                    break;
                default:
                    break;
            }
        }

        private void toPreviewImages() {
            GlobalData.galleryImageData = dataList;
            ChoosePhotoGalleryActivity.startChoosePhotoGalleryActivity(
                    PhotoActivity.instance, position,
                    PhotoActivity.instance.isSendSourcePic(),
                    PhotoActivity.TO_CHOOSE_NEW_PIC);
        }

    }

    private class ViewHolder {
        public ImageView imageIv;
        public CheckBox checkIv;
        public TextView isvideo;
    }

}

package com.zzy.flowers.activity.photoalbum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
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

import com.example.maventest.EsayVideoEditActivity;
import com.example.maventest.OnProcessListener;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.child.PublishActivity;
import com.hldj.hmyg.saler.ChoosePhotoGalleryActivity;
import com.hldj.hmyg.util.VideoHempler;
import com.white.utils.AndroidUtil;
import com.white.utils.GlobalData;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

public class PhotoAdapter extends BaseAdapter {

    private List<PhotoItem> dataList = new ArrayList<PhotoItem>();
    private LayoutInflater inflater;
    private ViewHolder holder;
    private int itemWidth;
    private Context context;

    private boolean isSourceImage = false;

    public PhotoAdapter(Context context, List<PhotoItem> dataList) {
        super();
        this.dataList = dataList;
        this.context = context;
        itemWidth = (GlobalData.getScreenWidth(false, context) - AndroidUtil.dip2px(context, 51)) / 4;
        inflater = LayoutInflater.from(context);
    }

    public PhotoAdapter(Context context, List<PhotoItem> dataList, boolean isSource) {
        super();
        this.dataList = dataList;
        this.context = context;
        itemWidth = (GlobalData.getScreenWidth(false, context) - AndroidUtil.dip2px(context, 51)) / 4;
        inflater = LayoutInflater.from(context);
        this.isSourceImage = isSource;
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

            holder.isvideo = (TextView) convertView
                    .findViewById(R.id.isvideo);
            holder.forund_bg = (ImageView) convertView
                    .findViewById(R.id.forund_bg);

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


        if (item.type.equals("video")) {

            FinalBitmap.create(context).display(holder.imageIv, item.video_image_path);
//            holder.isvideo.setText("是否视频：-> " + item.type);
            holder.isvideo.setText(item.duration / 1000 + " s");


            holder.forund_bg.setVisibility(View.VISIBLE);

            holder.checkIv.setVisibility(View.GONE);


        } else {
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

            holder.isvideo.setVisibility(View.GONE);
            holder.forund_bg.setVisibility(View.GONE);
            holder.checkIv.setVisibility(View.VISIBLE);


        }


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

            if (dataList.get(position).type.equals("video")) {


                /* 判断合法的视频  直接通过 */

                Log.i("toPreviewImages", "duration: " + dataList.get(position).duration / 1000);
                Log.i("toPreviewImages", "videoSize: " + dataList.get(position).picSize / 1024);

                if (dataList.get(position).duration / 1000 <= 15 && dataList.get(position).picSize / 1024 < 4000) {
                    Log.i("toPreviewImages---> ", "");
                    PublishActivity.instance.addVideo(dataList.get(position).getPath(), dataList.get(position).video_image_path);
                    Intent completeIntent = new Intent();
                    ((NeedSwipeBackActivity) context).setResult(Activity.RESULT_OK, completeIntent);
                    ((NeedSwipeBackActivity) context).hindLoading();
                    ((NeedSwipeBackActivity) context).finish();
                    return;
                }


                VideoHempler.startProcessVideo(context, dataList.get(position).getPath());
                EsayVideoEditActivity.onProcessListener = new OnProcessListener() {
                    @Override
                    public void onStart() {
                        ((NeedSwipeBackActivity) context).showLoading();
                        ((NeedSwipeBackActivity) context).UpdateLoading("开始裁剪....");
                    }

                    @Override
                    public void onClipSuccess(String s) {
                        ((NeedSwipeBackActivity) context).UpdateLoading("裁剪成功,开始压缩...");

                    }

                    @Override
                    public void onCompressSuccess(String s) {
                        ((NeedSwipeBackActivity) context).UpdateLoading("压缩成功");
                        PublishActivity.instance.addVideo(s, dataList.get(position).video_image_path);
                        Intent completeIntent = new Intent();
                        ((NeedSwipeBackActivity) context).setResult(Activity.RESULT_OK, completeIntent);
                        ((NeedSwipeBackActivity) context).hindLoading();
                        ((NeedSwipeBackActivity) context).finish();

                    }

                    @Override
                    public void onFailed(String s) {
                        ((NeedSwipeBackActivity) context).UpdateLoading("失败");
                    }

                    @Override
                    public void onFinish() {
                        ((NeedSwipeBackActivity) context).hindLoading();
                    }
                };



                /* 判断   10 s  内  并小于 3 M  直接选择上传。。。 */


            } else {
                GlobalData.galleryImageData = dataList;


                if (PhotoActivity.instance != null) {
                    ChoosePhotoGalleryActivity.startChoosePhotoGalleryActivity(
                            PhotoActivity.instance, position,
                            PhotoActivity.instance.isSendSourcePic(),
                            PhotoActivity.TO_CHOOSE_NEW_PIC);
                } else {
                    ChoosePhotoGalleryActivity.startChoosePhotoGalleryActivity(
                            (Activity) context, position,
                            isSourceImage,
                            PhotoActivity.TO_CHOOSE_NEW_PIC);
                }


            }


        }

    }

    private class ViewHolder {
        public ImageView imageIv;
        public CheckBox checkIv;
        public TextView isvideo;
        public ImageView forund_bg;
    }

}

package com.hldj.hmyg.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.Pic;
import com.white.utils.AndroidUtil;
import com.white.utils.ImageTools;
import com.white.utils.StringUtil;

import net.tsz.afinal.FinalBitmap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PublishFlowerInfoPhotoAdapter extends BaseAdapter {

    protected ArrayList<Pic> urlPaths = new ArrayList<Pic>();

    public boolean isEquleWidth = false;

    protected LayoutInflater inflater;

    private Context context;

    protected ViewHolder holder;

    private int dip30px;
    public static final int TO_CHOOSE_NEW_PIC = 20;

    public static final int MAX_IMAGE_COUNT = 9;

    private int columeNum = 4;

    public int getColumeNum() {
        return columeNum;
    }

    public void setColumeNum(int columeNum) {
        this.columeNum = columeNum;
    }

    private int width;
    private boolean isUpdataNoti;

    private boolean isShowSucceesOrOrrer;

    @SuppressWarnings("deprecation")
    public PublishFlowerInfoPhotoAdapter(Context context,
                                         ArrayList<Pic> urlPaths) {
        super();
        this.urlPaths = urlPaths;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        dip30px = AndroidUtil.dip2px(context, 30);
        WindowManager wm = ((Activity) context).getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        if (urlPaths.size() < MAX_IMAGE_COUNT) {
            if (ismIsCloseAll()) {
                return urlPaths.size();
            }
            return urlPaths.size() + 1;
        }
        return MAX_IMAGE_COUNT;
    }

    @Override
    public Object getItem(int position) {
        if (position < urlPaths.size())
            return urlPaths.get(position);
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getUrlPathsCount() {
        return urlPaths.size();
    }

    public void addItem(Pic item) {
        urlPaths.add(item);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<Pic> items) {
        urlPaths.addAll(items);
        notifyDataSetChanged();
    }

    public void removeItem(int pos) {
        urlPaths.remove(pos);
        notifyDataSetChanged();

    }

    public ArrayList<Pic> getDataList() {
        return urlPaths;
    }

    public void notify(ArrayList<Pic> data) {
//        this.urlPaths = data;
        urlPaths.clear();
        urlPaths.addAll(data);
        notifyDataSetChanged();
    }

    public void notify(ArrayList<Pic> data, boolean isShowSucceesOrOrrer) {
//        this.urlPaths = data;
        urlPaths.clear();
        urlPaths.addAll(data);
        this.isShowSucceesOrOrrer = isShowSucceesOrOrrer;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.publish_flower_info_photo_list_item_view, null);
            holder = new ViewHolder();
            holder.photoIv = (ImageView) convertView
                    .findViewById(R.id.publish_flower_info_photo_item_iv);
            holder.iv_img2 = (ImageView) convertView.findViewById(R.id.iv_img2);
            holder.iv_img1 = (ImageView) convertView.findViewById(R.id.iv_img1);
            LayoutParams para = holder.photoIv.getLayoutParams();

            if (isEquleWidth) {
                para.width = (width - dip30px) / 4;
                para.height =  (width - dip30px) / 4;
                                ViewGroup pr = (ViewGroup) holder.photoIv.getParent();
                pr.setLayoutParams(new LinearLayout.LayoutParams( para.width, para.width));
//                pr.setLayoutParams(new LinearLayout.LayoutParams( para.width, para.width));
//
            } else {
                if (getColumeNum() == 4) {
                    para.width = (width - dip30px) / 4;
                    para.height = para.width * 4 / 3;
                } else {

                    para.width = (width) / 3;
                    para.height = (width) / 3;
//                ViewGroup pr = (ViewGroup) holder.photoIv.getParent();
//                pr.setLayoutParams(new LinearLayout.LayoutParams(MyApplication.dp2px(context, 100),MyApplication.dp2px(context, 100)));
//
//                para.width = MyApplication.dp2px(context, 100);
//                para.height = MyApplication.dp2px(context, 100) ;
                }


            }
            holder.photoIv.setLayoutParams(para);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == urlPaths.size()) {
            if (position == MAX_IMAGE_COUNT) {
                holder.photoIv.setVisibility(View.GONE);
            } else {
                holder.photoIv.setVisibility(View.VISIBLE);
            }
            holder.photoIv.setScaleType(ImageView.ScaleType.FIT_XY);

            if (isEquleWidth)
            {

                holder.photoIv.setImageResource(R.drawable.add_image_icon_big_eq);
            }else {

                holder.photoIv.setImageResource(R.drawable.add_image_icon);
            }
//            holder.photoIv.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.add_image_icon_big));
            holder.photoIv.requestLayout();
            holder.iv_img2.setVisibility(View.INVISIBLE);
            holder.iv_img1.setVisibility(View.INVISIBLE);
        } else {
            holder.photoIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.iv_img2.setVisibility(View.VISIBLE);
            holder.iv_img1.setVisibility(View.VISIBLE);
            holder.iv_img2.setImageResource(R.drawable.shanchu);
            if (StringUtil.isHttpUrlPicPath(urlPaths.get(position).getUrl())) {
                // ImageLoader.getInstance().displayImage(urlPaths.get(position),
                // holder.photoIv);
                FinalBitmap.create(context).display(holder.photoIv,
                        urlPaths.get(position).getUrl());
                holder.iv_img1.setVisibility(View.VISIBLE);
                holder.iv_img1.setImageResource(R.drawable.tupian_chenggou);
            } else {
                if (isGone) {
                    holder.iv_img1.setVisibility(View.GONE);
                    holder.iv_img1.setImageResource(R.drawable.tupian_shibai);
                } else {
                    holder.iv_img1.setVisibility(View.VISIBLE);
                    holder.iv_img1.setImageResource(R.drawable.tupian_shibai);
                    isGone = false;
                }


                // holder.iv_img1.setVisibility(View.INVISIBLE);
                File file = new File(urlPaths.get(position).getUrl());
                if (file.exists()) {
                    try {
                        Bitmap bm = ImageTools.CompressAndSaveImg(file);
                        if (bm != null) {
                            holder.photoIv.setImageBitmap(bm);

                        } else {
                            holder.photoIv
                                    .setImageResource(R.drawable.un_down_load_pic_icon);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        holder.photoIv
                                .setImageResource(R.drawable.un_down_load_pic_icon);
                    }
                } else {
                    holder.photoIv
                            .setImageResource(R.drawable.un_down_load_pic_icon);
                }

            }
            holder.iv_img2.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    removeItem(position);
                }
            });
        }


        if (ismIsCloseAll()) {
            closeDelIco(holder.iv_img2);
            closeAddIco(holder.iv_img1);
//            closeSuccIco(holder.photoIv);
        }

        return convertView;
    }

    boolean isGone = false;//默认是显示失败的

    public void Faild2Gone(boolean isGone) {
        this.isGone = isGone;
        this.notifyDataSetChanged();
    }

    class ViewHolder {
        public ImageView photoIv;
        private ImageView iv_img2;
        private ImageView iv_img1;
    }


    /*关闭删除按钮*/
    public void closeDelIco(ImageView ic_del) {

        ic_del.setVisibility(View.GONE);
    }

    /*关闭添加图标*/
    public void closeAddIco(ImageView ic_add) {

        ic_add.setVisibility(View.GONE);
    }

    /*关闭成功图标*/
    public void closeSuccIco(ImageView ic_succ) {

        ic_succ.setVisibility(View.GONE);
    }

    private boolean mIsCloseAll = false;

    public void closeAll(boolean isCloseAll) {

        this.mIsCloseAll = isCloseAll;

    }

    private boolean ismIsCloseAll() {
        return mIsCloseAll;
    }

}

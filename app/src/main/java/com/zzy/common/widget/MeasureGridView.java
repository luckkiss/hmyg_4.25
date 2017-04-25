package com.zzy.common.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hldj.hmyg.adapter.PublishFlowerInfoPhotoAdapter;
import com.hldj.hmyg.application.PermissionUtils;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.saler.FlowerInfoPhotoChoosePopwin2;
import com.hldj.hmyg.saler.SaveSeedlingActivityBase;
import com.hldj.hmyg.util.TakePhotoUtil;
import com.hy.utils.ToastUtil;
import com.zzy.flowers.activity.photoalbum.EditGalleryImageActivity;

import java.io.IOException;
import java.util.ArrayList;

public class MeasureGridView extends GridView {
    public static Context context;
    /**
     * 照片适配器
     */
    private PublishFlowerInfoPhotoAdapter adapter;

    public MeasureGridView(Context context, AttributeSet attrs,
                           int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;


    }

    public MeasureGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;


    }

    public MeasureGridView(Context context) {
        super(context);
        this.context = context;

    }

//    public static final String usrl = "https://gss0.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D450%2C600/sign=dce89ee56a81800a6eb0810a84051fcc/5243fbf2b2119313bdce98da63380cd790238dce.jpg";
    public static final String usrl = "/storage/emulated/0/tencent/MicroMsg/WeiXin/wx_camera_1492170878634.jpg";
    public static final String usrl1 = "/storage/emulated/0/Flowers/image/flower_info_1492490890982.png";

    static PhotoGvOnItemClickListener gvOnItemClickListener;

    public void init(Context context, ArrayList<Pic> urlPaths, SaveSeedlingActivityBase.ViewHolder viewHolder, FlowerInfoPhotoChoosePopwin2.onPhotoStateChangeListener listener) {


        adapter = new PublishFlowerInfoPhotoAdapter(context, urlPaths);
        this.setAdapter(adapter);


        this.setOnItemClickListener(new PhotoGvOnItemClickListener(viewHolder.ll_mainView, listener));


    }


    public void setImageNumColumns(int num) {
        setNumColumns(num);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


    private static FlowerInfoPhotoChoosePopwin2 popwin;

    //自定义点击事件
    public class PhotoGvOnItemClickListener implements OnItemClickListener {
        View mainView;
        FlowerInfoPhotoChoosePopwin2.onPhotoStateChangeListener listener;

        public PhotoGvOnItemClickListener(View mainView, FlowerInfoPhotoChoosePopwin2.onPhotoStateChangeListener listener) {
            this.mainView = mainView;
            this.listener = listener;
        }

        @Override
        public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            if (position == adapter.getUrlPathsCount()) {
                boolean requestCamerPermissions = new PermissionUtils((Activity) context).requestCamerPermissions(200);
                if (!requestCamerPermissions) {
                    ToastUtil.showShortToast("您未同意拍照权限");
                    return;
                }
                boolean requestReadSDCardPermissions = new PermissionUtils((Activity) context).requestReadSDCardPermissions(200);
                if (!requestReadSDCardPermissions) {
                    ToastUtil.showShortToast("您未同意应用读取SD卡权限");
                    return;
                }
                popwin = new FlowerInfoPhotoChoosePopwin2((Activity) context, listener);
                popwin.showAtLocation(mainView, Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
            } else {
                EditGalleryImageActivity.startEditGalleryImageActivity(
                        (Activity) context,
                        EditGalleryImageActivity.TO_EDIT_PUBLISH_IMAGE,
                        position, adapter.getDataList());
            }

        }
    }

    public PublishFlowerInfoPhotoAdapter getAdapter() {

        return adapter;
    }


    /**
     * 添加图片
     *
     * @param sourchImagePath
     */
    public void addImageItem(String sourchImagePath) throws IOException {

        int return_code = TakePhotoUtil.checkGif(sourchImagePath, context);

        if (return_code == TakePhotoUtil.ADD_NEW_PIC) {

            Pic pic = new Pic("", false, sourchImagePath, 0);
            adapter.addItem(pic);

        } else {
            ToastUtil.showShortToast("error");
        }


    }


}

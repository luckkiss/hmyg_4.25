package com.hldj.hmyg.saler;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.hldj.hmyg.R;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zzy.flowers.widget.popwin.BottomPopwin;

public class FlowerInfoPhotoChoosePopwin2 extends BottomPopwin {

    onPhotoStateChangeListener onPhotoState;

    public FlowerInfoPhotoChoosePopwin2(Context context, onPhotoStateChangeListener onPhotoState) {
        super(new int[]{R.string.photo_take_pic, R.string.photo_album_title, R.string.cancel}, context);
        this.onPhotoState = onPhotoState;

        RxPermissions rxPermissions = new RxPermissions((Activity) context);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .doOnSubscribe(disposable -> {
                }).subscribe(grand -> {


        });

    }

    public FlowerInfoPhotoChoosePopwin2(Context context, onPhotoStateChangeListener onPhotoState, boolean isOpenVideo) {


        super(new int[]{R.string.photo_take_pic, R.string.photo_album_title, R.string.small_video, R.string.cancel}, context);
        this.onPhotoState = onPhotoState;

        RxPermissions rxPermissions = new RxPermissions((Activity) context);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .doOnSubscribe(disposable -> {
                }).subscribe(grand -> {
        });
    }


    @Override
    protected void handleClickListener(int pos) {
        switch (pos) {
            case 0:
                onPhotoState.onTakePic();
                break;
            case 1:
                onPhotoState.onChoosePic();
                break;
            case 2:
                if (onPhotoState instanceof onVideoStateChangeListener) {
                    ((onVideoStateChangeListener) onPhotoState).onVideoing();
                }
                break;
            default:
                onPhotoState.onCancle();
                break;
        }
    }

    public interface onPhotoStateChangeListener {
        void onTakePic();

        void onChoosePic();

        void onCancle();


    }


    public abstract static class onVideoStateChangeListener implements onPhotoStateChangeListener {

        public abstract void onVideoing();


    }

}

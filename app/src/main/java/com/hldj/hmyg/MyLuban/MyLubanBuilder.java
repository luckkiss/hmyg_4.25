package com.hldj.hmyg.MyLuban;

import android.graphics.Bitmap;

import java.io.File;

import me.shaohui.advancedluban.Luban;

/**
 * Created by Administrator on 2017/11/6 0006.
 */

public class MyLubanBuilder {

    int maxSize;

    int maxWidth;

    int maxHeight;

    File cacheDir;

    Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;

    int gear = Luban.THIRD_GEAR;

    MyLubanBuilder(File cacheDir) {
        this.cacheDir = cacheDir;
    }

}

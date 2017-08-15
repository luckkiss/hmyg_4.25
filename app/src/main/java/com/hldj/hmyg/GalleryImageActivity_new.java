package com.hldj.hmyg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

/**
 */
public class GalleryImageActivity_new extends FragmentActivity {


    public static Bitmap bt;
    private static ImageView imageView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.qc_code_image);

        imageView = findViewById(R.id.iv_show_qc);
    }

    public static void setBit(Bitmap bt) {
        imageView.setImageBitmap(bt);
    }

    public static void startGalleryImageActivity(Context context) {
        Intent intent = new Intent(context, GalleryImageActivity_new.class);

        context.startActivity(intent);
    }

}

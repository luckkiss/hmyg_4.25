package com.hldj.hmyg.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.mabeijianxi.smallvideo2.VideoActivity;
import com.mabeijianxi.smallvideorecord2.DeviceUtils;
import com.mabeijianxi.smallvideorecord2.JianXiCamera;

import java.io.File;
import java.util.HashMap;

/**
 *
 */

public class VideoHempler {


    public static void initSmallVideo() {
        if (true) return;
        // Set the cache path for video
        File dcim = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                JianXiCamera.setVideoCachePath(dcim + "/mabeijianxi/");
            } else {
                JianXiCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
                        "/sdcard-ext/")
                        + "/mabeijianxi/");
            }
        } else {
            JianXiCamera.setVideoCachePath(dcim + "/mabeijianxi/");
        }
        // Initialize the shooting, encounter problems can choose to open this tag to facilitate the generation of logs
        JianXiCamera.initialize(false, null);
    }


    public static void start(Context mContext) {
        // recording
//        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
//                .fullScreen(true)
//                .smallVideoWidth(480)
//                .smallVideoHeight(846)
//                .recordTimeMax(10000)
//                .recordTimeMin(1500)
//                .maxFrameRate(25)
//                .videoBitrate(750000)
//                .captureThumbnailsTime(1)
//                .build();
//        MediaRecorderActivity.goSmallVideoRecorder((Activity) mContext, PublishActivity.class.getName(), config);

        VideoActivity.start((Activity) mContext);

//        MediaRecorderActivity.goSmallVideoRecorder((Activity) mContext, MediaRecorderActivity.class.getName(), config);
//  Select local video compression
//        LocalMediaConfig.Buidler buidler = new LocalMediaConfig.Buidler();
//        final LocalMediaConfig config = buidler
//                .setVideoPath(path)
//                .captureThumbnailsTime(1)
//                .doH264Compress(new AutoVBRMode())
//                .setFramerate(15)
//                .setScale(1.0f)
//                .build();
//        OnlyCompressOverBean onlyCompressOverBean = new LocalMediaCompress(config).startCompress();
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

}

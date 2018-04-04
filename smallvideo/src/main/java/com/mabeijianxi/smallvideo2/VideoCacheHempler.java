package com.mabeijianxi.smallvideo2;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 视频缓存代理
 */

public class VideoCacheHempler {

    private Activity mActivity;

    private OnDisplayListener mListener;

    private File videoFile;

    private boolean interceptFlag = false;
    private MyAsyncTask myAsyncTask;

    public interface OnDisplayListener {

        void onDisplay(String url);

        void onLoadFailed(String msg);

        void onProcess(int progress);

        void onStart();

        void onFinish();

    }

    public VideoCacheHempler(Activity mActivity, OnDisplayListener listener) {
        this.mActivity = mActivity;
        mListener = listener;
    }


    public VideoCacheHempler prepareStart(String videoPath) {
        String localUrl = "";


        if (videoPath.startsWith("http")) {
            /* thread load video to display   */

            String cacheDirPath = mActivity.getApplicationContext().getExternalCacheDir() + File.separator + "video_cache/";


            File file = new File(cacheDirPath);
            if (!file.exists() && !file.mkdirs()) {
                throw new NullPointerException("创建 rootPath 失败，注意 6.0+ 的动态申请权限");
            }
            String[] temp = videoPath.split("/");
            this.videoFile = new File(cacheDirPath + temp[temp.length - 1]);
//            this.videoFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "myvideos/" + temp[temp.length - 1]);
            if (this.videoFile.exists()) {
                mListener.onDisplay(this.videoFile.getAbsolutePath());
            } else {
                try {
                    this.videoFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                openThread2Donload(videoPath);
            }


//            return localUrl;

        } else {
//            return videoUrl;
            mListener.onDisplay(videoPath);
        }

        return this;

    }

    private void openThread2Donload(String videoOnlineUrl) {
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(videoOnlineUrl);

    }


    private class MyAsyncTask extends AsyncTask<String, Integer, String> {
        private MyAsyncTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            mListener.onStart();

        }

        protected String doInBackground(String[] params) {
            try {
                String urlPath = params[0];
                URL url = new URL(urlPath);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(videoFile);
                int count = 0;
                byte[] buf = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;
                    int progress = (int) ((float) count / (float) length * 100.0F);
                    Log.d("zzzzz", "更新进度 " + progress);
                    if (numread <= 0) {
                        this.publishProgress(new Integer[]{Integer.valueOf(100)});
                        break;
                    }

                    this.publishProgress(new Integer[]{Integer.valueOf(progress)});
                    fos.write(buf, 0, numread);
                } while (!interceptFlag);

                Log.d("zzzzz", "下载结束 ");
                fos.close();
                is.close();
            } catch (MalformedURLException var12) {
                Log.d("zzzzz", var12.toString());
            } catch (IOException var13) {
                Log.d("zzzzz", var13.toString());
            }

            return null;
        }

        protected void onProgressUpdate(Integer[] values) {
//            if (SurfaceVideoViewCreator.this.progressBar != null) {
            int progress = values[0].intValue();
//                SurfaceVideoViewCreator.this.progressBar.setProgerss(progress, true);

            mListener.onProcess(progress);
            if (progress >= 100) {
                Log.d("zzzzz", "开始播放 ");

                mListener.onDisplay(videoFile.getAbsolutePath());
                mListener.onFinish();

//                    SurfaceVideoViewCreator.this.play(SurfaceVideoViewCreator.this.videoFile.getAbsolutePath());
            }
        }


    }

    public void cancle() {
        myAsyncTask.cancel(true);
    }


    private void upload2XiangCe(String fileUrl) {
        updateVideo(fileUrl);
    }

    /**
     * 将视频插入图库
     *
     *
     *  https://blog.csdn.net/chendong_/article/details/52290329
     *
     * @param url 视频路径地址
     */
    public void updateVideo(String url) {
        File file = new File(url);


        //获取ContentResolve对象，来操作插入视频
        ContentResolver localContentResolver = mActivity.getContentResolver();
        //ContentValues：用于储存一些基本类型的键值对
        ContentValues localContentValues = getVideoContentValues(mActivity, file, System.currentTimeMillis());
        //insert语句负责插入一条新的纪录，如果插入成功则会返回这条记录的id，如果插入失败会返回-1。
        Uri localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues);

        Uri localUr1i = Uri.fromFile(file);

        Log.i("uri = ", "localUri: " + localUri);
        Log.i("uri = ", "localUr1i: " + localUr1i);

        Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUr1i);
        Intent localIntent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);

        mActivity.sendBroadcast(localIntent);
        mActivity.sendBroadcast(localIntent1);


//      MediaScannerConnection.scanFile(mActivity, new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/" + "myvideos"}, null, null);
        MediaScannerConnection.scanFile(mActivity, new String[]{Environment.getExternalStorageDirectory().getPath() + "/" + "myvideos"}, new String[]{"video/mp4"}, null);


        AlbumNotifyHelper. insertVideoToMediaStore(mActivity,url,localContentValues.getAsLong("datetaken"),66);


        AlbumNotifyHelper.scanFile(mActivity,Environment.getExternalStorageDirectory().getPath() + "/" + "myvideos");

    }


    //再往数据库中插入数据的时候将，将要插入的值都放到一个ContentValues的实例当中
    public static ContentValues getVideoContentValues(Context paramContext, File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "video/mp4");
        localContentValues.put("datetaken", Long.valueOf(paramLong));
        localContentValues.put("date_modified", Long.valueOf(paramLong));
        localContentValues.put("date_added", Long.valueOf(paramLong));
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", Long.valueOf(paramFile.length()));
        return localContentValues;
    }


//    //android把  文件添加到相册
//    public void udpateMedia(String url){
//        //图片路径
//        File file=new File(url);
//        ContentResolver localContentResolver = getContentResolver();
//        ContentValues localContentValues = getImageContentValues(UnityPlayerNativeActivity.this, file,      System.currentTimeMillis());
//        localContentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, localContentValues);
//        Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
//        final Uri localUri = Uri.fromFile(file);
//        localIntent.setData(localUri);
//        //发送广播即时更新图库
//        sendBroadcast(localIntent);
//    }


}

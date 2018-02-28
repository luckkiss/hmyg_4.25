package com.mabeijianxi.smallvideo2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.cjt2325.cameralibrary.util.FileUtil;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class VideoActivity extends AppCompatActivity {


    private JCameraView jCameraView;

    /**
     * 视屏地址
     */
    public final static String VIDEO_URI = "video_uri";

    /**
     * 视屏截图地址
     */
    public final static String VIDEO_SCREENSHOT = "video_screenshot";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }


        init();
    }

    private void init() {
        //1.1.1
        jCameraView = (JCameraView) findViewById(R.id.jcameraview);

//设置视频保存路径
        jCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera");

//设置只能录像或只能拍照或两种都可以（默认两种都可以）
        jCameraView.setFeatures(JCameraView.BUTTON_STATE_BOTH);

//设置视频质量
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);

//JCameraView监听
        jCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //打开Camera失败回调
                Log.i("CJT", "open camera error");
            }

            @Override
            public void AudioPermissionError() {
                //没有录取权限回调
                Log.i("CJT", "AudioPermissionError");
            }
        });
        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取图片bitmap
                Log.i("JCameraView", "bitmap = " + bitmap.getWidth());


                Intent intent = null;
                try {
//            intent = new Intent(this, Class.forName(getIntent().getStringExtra(OVER_ACTIVITY_NAME)));
                    intent = new Intent();
//                    intent.putExtra(MediaRecorderActivity.OUTPUT_DIRECTORY,url);
                    String shot = FileUtil.saveBitmap("JCamera", bitmap);
                    intent.putExtra(MediaRecorderActivity.VIDEO_SCREENSHOT, shot);
//                    intent.putExtra(MediaRecorderActivity.VIDEO_SCREENSHOT, );
//            startActivity(intent);
                } catch (Exception e) {
                    throw new IllegalArgumentException("需要传入录制完成后跳转的Activity的全类名");
                }

                setResult(101, intent);
                finish();


            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
//获取视频路径
                Log.i("CJT", "url = " + url);


                Intent intent = null;
                try {
//            intent = new Intent(this, Class.forName(getIntent().getStringExtra(OVER_ACTIVITY_NAME)));
                    intent = new Intent();
//                    intent.putExtra(MediaRecorderActivity.OUTPUT_DIRECTORY,url);
                    intent.putExtra(MediaRecorderActivity.VIDEO_URI, url);
                    File parent = new File(url);
                    String shot = FileUtil.saveBitmap("JCamera", firstFrame);
                    intent.putExtra(MediaRecorderActivity.VIDEO_SCREENSHOT, shot);
//                    intent.putExtra(MediaRecorderActivity.VIDEO_SCREENSHOT, );
//            startActivity(intent);
                } catch (Exception e) {
                    throw new IllegalArgumentException("需要传入录制完成后跳转的Activity的全类名");
                }

                setResult(100, intent);
                finish();


            }
        });

//左边按钮点击事件
        jCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                VideoActivity.this.finish();
            }
        });

        jCameraView.postDelayed(new Runnable() {
            @Override
            public void run() {
//                jCameraView.startPreviewCallback();

                try {
                    Method method = JCameraView.class.getDeclaredMethod("setFocusViewWidthAnimation", float.class, float.class);
                    method.setAccessible(true);
                    method.invoke(jCameraView, (float) (jCameraView.getMeasuredWidth() / 2), (float) (jCameraView.getMeasuredHeight() / 2));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            }
        }, 400);


    }

////右边按钮点击事件
//        jCameraView.setRightClickListener(new ClickListener() {
//            @Override
//            public void onClick() {
//
//            }
//        });


    public static void start(Activity mActivity) {
        mActivity.startActivityForResult(new Intent(mActivity, VideoActivity.class), 100);
    }


    @Override
    protected void onResume() {
        super.onResume();
        jCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }
}

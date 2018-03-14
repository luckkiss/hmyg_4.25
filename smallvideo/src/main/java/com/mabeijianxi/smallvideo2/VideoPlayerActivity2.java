package com.mabeijianxi.smallvideo2;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.mabeijianxi.smallvideorecord2.DeviceUtils;
import com.mabeijianxi.smallvideorecord2.StringUtils;
import com.mabeijianxi.smallvideorecord2.SurfaceVideoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;


/**
 * 通用单独播放界面
 *
 * @author tangjun
 */
public class VideoPlayerActivity2 extends FragmentActivity implements
        SurfaceVideoView.OnPlayStateListener, OnErrorListener,
        OnPreparedListener, OnClickListener, OnCompletionListener,
        OnInfoListener {
    /**
     * 播放控件
     */
//    private SurfaceVideoView mVideoView;
    private VideoView mVideoView;

    private ImageView iv_load_file;

    /**
     * 暂停按钮
     */
    private View mPlayerStatus;
    private View mLoading;

    /**
     * 播放路径
     */
    private String mPath;
    /**
     * 是否需要回复播放
     */
    private boolean mNeedResume;


    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    private VideoCacheHempler videoCacheHempler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mPath = getIntent().getStringExtra("path");
        if (StringUtils.isEmpty(mPath)) {
            finish();
            return;
        }

        setContentView(R.layout.activity_video_player);
        mVideoView = (VideoView) findViewById(R.id.auto_video);


//        int screenWidth = getScreenWidth(this);
//        int screenHeight = getScreenHeight(this);
//        int videoHight = getScreenHeight(this);
////        int videoHight = (int) (screenWidth / (MediaRecorderBase.SMALL_VIDEO_HEIGHT / (MediaRecorderBase.SMALL_VIDEO_WIDTH  * 1.0f)));
//
////        mVideoView.getLayoutParams().height = videoHight + dp(25 + 25);
//        mVideoView.getLayoutParams().height =getDpi();
//
//        mVideoView.getLayoutParams().width = screenWidth;
//        mVideoView.requestLayout();
//
        mPlayerStatus = findViewById(R.id.play_status);
        mLoading = findViewById(R.id.loading);
//
//        mVideoView.setOnPreparedListener(this);
//        mVideoView.setOnPlayStateListener(this);
        mVideoView.setOnErrorListener(this);
        mVideoView.setOnClickListener(this);
        mVideoView.setOnTouchListener((v, event) -> {
            float downX = event.getX();
            float downY = event.getY();
            Log.i("OnTouch", "downX=" + downX + " downY");

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                onStateChanged(mVideoView.isPlaying());
                //当手指按下的时候
                x1 = event.getX();
                y1 = event.getY();
//                finish();
            }


            if (event.getAction() == MotionEvent.ACTION_UP) {

                //当手指离开的时候
                x2 = event.getX();
                y2 = event.getY();
                if (y1 - y2 > 50) {
//                    Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
                    onStateChanged(mVideoView.isPlaying());
                } else if (y2 - y1 > 50) {
//                    Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();

                    finish();
                } else if (x1 - x2 > 50) {
                    onStateChanged(mVideoView.isPlaying());
//                    Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
                } else if (x2 - x1 > 50) {
//                    Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    onStateChanged(mVideoView.isPlaying());
                }


//                finish();
            }
            return true;
        });
//        mVideoView.setOnInfoListener(this);
        mVideoView.setOnCompletionListener(this);
//
////		mVideoView.getLayoutParams().height = DeviceUtils.getScreenWidth(this);
//
//        findViewById(R.id.root).setOnClickListener(this);


        videoCacheHempler = new VideoCacheHempler(this, new VideoCacheHempler.OnDisplayListener() {
            @Override
            public void onDisplay(String url) {
                if (!VideoPlayerActivity2.this.isFinishing()) {
                    mVideoView.setVideoPath(url);
                    mVideoView.setOnPreparedListener(VideoPlayerActivity2.this);
                    mVideoView.start();

                    findViewById(R.id.iv_load_file).setVisibility(View.VISIBLE);
                    findViewById(R.id.iv_load_file).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            String videoDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "myvideos/";

                            if (!new File(videoDirPath).exists()) {
                                new File(videoDirPath).mkdirs();
                            }

                            File cacheFile = new File(url);

                            File newFile = new File(videoDirPath + cacheFile.getName());

                            if (newFile.exists()) {
                                newFile.delete();
                            }


                            InputStream inputStream;
                            FileOutputStream outputStream;
                            try {
                                /* 根据缓存文件 获取输入留 */
                                inputStream = new FileInputStream(cacheFile);
                                byte[] data = new byte[1024];

                                /* 根据新地址 创建新文件  并且获取输出流，能够写文件 */
                                outputStream = new FileOutputStream(newFile);


                                while (inputStream.read(data) != -1) {
                                    outputStream.write(data);
                                }

                                inputStream.close();
                                outputStream.close();

                                Toast.makeText(VideoPlayerActivity2.this, "已经保存到相册:" + videoDirPath, Toast.LENGTH_LONG).show();
                                videoCacheHempler.updateVideo(newFile.getAbsolutePath());
                            } catch (IOException e) {
                                Toast.makeText(VideoPlayerActivity2.this, "保存失败\n" + e.getMessage() + videoDirPath, Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }


                        }

                    });


                }

            }

            @Override
            public void onLoadFailed(String msg) {
                Toast.makeText(VideoPlayerActivity2.this, msg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProcess(int progress) {
                Log.i("onProcess  ---  ", progress + "");


            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }
        }).prepareStart(mPath);


    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mVideoView != null && mNeedResume) {
//            mNeedResume = false;
//            if (!mVideoView.isPlaying())
//                mVideoView.resta();
//            else
//                mVideoView.start();
//        }
        mVideoView.start();


    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null) {
            if (mVideoView.isPlaying()) {
                mNeedResume = true;
                mVideoView.pause();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mVideoView != null) {
//            mVideoView.rel();
            mVideoView = null;
        }
        super.onDestroy();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
//     mVideoView.setVolume(SurfaceVideoView.getSystemVolumn(this));
        mVideoView.start();
        mp.setLooping(true);
        // new Handler().postDelayed(new Runnable() {
        //
        // @SuppressWarnings("deprecation")
        // @Override
        // public void run() {
        // if (DeviceUtils.hasJellyBean()) {
        // mVideoView.setBackground(null);
        // } else {
        // mVideoView.setBackgroundDrawable(null);
        // }
        // }
        // }, 300);
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {// 跟随系统音量走
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_VOLUME_UP:
//                mVideoView.dispatchKeyEvent(this, event);
                onStateChanged(mVideoView.isPlaying());
                break;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onStateChanged(boolean isPlaying) {
        mPlayerStatus.setVisibility(isPlaying ? View.VISIBLE : View.GONE);
        if (isPlaying) {
            mVideoView.pause();
        } else {
            mVideoView.start();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (!isFinishing()) {
            // 播放失败
        }
        finish();
        return false;

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.videoview:
//            case R.id.root:
//                finish();
//                break;
//        }
//        finish();


    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (!isFinishing()) ;

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                // 音频和视频数据不正确
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (!isFinishing())
                    mVideoView.pause();
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                if (!isFinishing())
                    mVideoView.start();
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                if (DeviceUtils.hasJellyBean()) {
                    mVideoView.setBackground(null);
                } else {
                    mVideoView.setBackgroundDrawable(null);
                }
                break;
        }
        return false;
    }


    public int dp(int dp) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


    private int getDpi() {
        int dpi = 0;
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }


}

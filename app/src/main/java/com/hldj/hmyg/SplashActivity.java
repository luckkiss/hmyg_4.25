package com.hldj.hmyg;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;

import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.application.PermissionUtils;
import com.hy.utils.GetServerUrl;
import com.white.utils.AndroidUtil;

import cn.jpush.android.api.JPushInterface;


public class SplashActivity extends FragmentActivity {

    private ImageView img_sp;
    //    private FinalBitmap fb;
    private long delayMillis = 0;
    public Editor e;

    private static final String TAG = "SplashActivity";
    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTime = System.currentTimeMillis();
        Log.i(TAG, "onCreate time  " + startTime);
//        fb = FinalBitmap.create(this);
//        setContentView(R.layout.activity_splash);

//        ToastUtil.showShortToast("hellow world");
//        boolean test = false;


//        if (test) {
//            CustomDialog dialog = new CustomDialog(SplashActivity.this, R.style.Dialog);
//            dialog.show();
//
//            return;
//        }


        boolean requestREAD_PHONE_STATE = false;
        if (Build.VERSION.SDK_INT >= 23) {//大于6.0
//            requestREAD_PHONE_STATE = new PermissionUtils(SplashActivity.this).requestREAD_PHONE_STATE(200);
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(SplashActivity.this, android.Manifest.permission.READ_PHONE_STATE);
            if (checkCallPhonePermission == PackageManager.PERMISSION_GRANTED) {
                putSpInfo_Before_6();
                return;
            }
        } else {//小于6.0
            putSpInfo_Before_6();
        }
        try {
            requestREAD_PHONE_STATE = new PermissionUtils(SplashActivity.this).requestREAD_PHONE_STATE(200);
        } catch (Exception e1) {
            e1.printStackTrace();
        }


//        boolean flag = (PackageManager.PERMISSION_GRANTED ==
//                pm.checkPermission("android.permission.RECORD_AUDIO", "包名"));
//        boolean flag = PermissionChecker.checkSelfPermission(this, Manifest.permission.)== PermissionChecker.PERMISSION_GRANTED;
//        if (flag){
//            ToastUtil.showMessage("有权限");
//        }else {
//            ToastUtil.showMessage("无权限");
//            return;
//        }


    }

    public void putSpInfo_Before_6() {


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        Log.i(TAG, "putSpInfo_Before_6 start" + (System.currentTimeMillis() - startTime));
        e = MyApplication.Deviceinfo.edit();
        e.putString("version", AndroidUtil.getVersion(SplashActivity.this));
        e.putString("deviceId", AndroidUtil.getDeviceIMEI(SplashActivity.this));
        e.commit();
        GetServerUrl.version = AndroidUtil.getVersion(SplashActivity.this);
        GetServerUrl.deviceId = AndroidUtil.getDeviceIMEI(SplashActivity.this);
//            }
//        }).start();

        Log.i(TAG, "putSpInfo_Before_6 end" + (System.currentTimeMillis() - startTime));
        start2Main();
    }


//    KProgressHUD hud_numHud; // 上传时显示的等待框

    int i = 0;

    private void start2Main() {
        Log.i(TAG, "start2Main start" + (System.currentTimeMillis() - startTime));
        Intent toMainActivity = new Intent(SplashActivity.this, MainActivity.class);
        toMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toMainActivity);
        Log.i(TAG, "start2Main center" + (System.currentTimeMillis() - startTime));
        finish();
        Log.i(TAG, "start2Main end" + (System.currentTimeMillis() - startTime));
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 200:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    putSpInfo_Before_6();
                } else {
                    e = MyApplication.Deviceinfo.edit();
                    e.putString("version", AndroidUtil.getVersion(this));
                    e.commit();
                    start2Main();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

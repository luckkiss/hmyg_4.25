package com.hldj.hmyg;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.application.PermissionUtils;
import com.hy.utils.GetServerUrl;
import com.white.utils.AndroidUtil;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;


public class SplashActivity extends FragmentActivity {

    private ImageView img_sp;
    //    private FinalBitmap fb;
    private long delayMillis = 1500;
    public Editor e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        fb = FinalBitmap.create(this);
        setContentView(R.layout.activity_splash);

//        ToastUtil.showShortToast("hellow world");


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


    }

    public void putSpInfo_Before_6() {
        e = MyApplication.Deviceinfo.edit();
        e.putString("version", AndroidUtil.getVersion(this));
        e.putString("deviceId", AndroidUtil.getDeviceIMEI(this));
        e.commit();
        GetServerUrl.version = AndroidUtil.getVersion(this);
        GetServerUrl.deviceId = AndroidUtil.getDeviceIMEI(this);
        start2Main();
    }


//    KProgressHUD hud_numHud; // 上传时显示的等待框

    int i = 0;

    private void start2Main() {
        Observable.timer(delayMillis, TimeUnit.MICROSECONDS)
                .subscribe(delay -> {
                    Intent toMainActivity = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(toMainActivity);
                    finish();
                });
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

package com.hldj.hmyg.CallBack;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hldj.hmyg.application.MyApplication;
import com.hy.utils.ToastUtil;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;

import java.util.Locale;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public interface MyBetaPatchListener extends BetaPatchListener {
    String TAG = "MyBetaPatchListener";


    MyBetaPatchListener MY_BETA_PATCH_LISTENER = new MyBetaPatchListener() {
        @Override
        public void onPatchReceived(String patchFile) {
            if (MyApplication.Flag) {
                Log.i(TAG, "onPatchReceived:补丁下载地址 " + patchFile);
                ToastUtil.showLongToast("检测到应用补丁，正在后台为您下载^_^");
            }
        }

        @Override
        public void onDownloadReceived(long savedLength, long totalLength) {
            if (MyApplication.Flag)
                Log.i(TAG, "onPatchReceived: " +
                        String.format(Locale.getDefault(), "%s %d%%",
                                Beta.strNotificationDownloading,
                                (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)));
        }

        @Override
        public void onDownloadSuccess(String msg) {
            if (MyApplication.Flag)
                Log.i(TAG, "onDownloadSuccess: " + msg);
        }

        @Override
        public void onDownloadFailure(String msg) {
            if (MyApplication.Flag)
                Log.i(TAG, "补丁下载失败: " + msg);

        }

        @Override
        public void onApplySuccess(String msg) {
            if (MyApplication.Flag) {
                Log.i(TAG, "补丁应用成功: " + msg);
//              ToastUtil.showLongToast("补丁应用成功，3s后为你自动重启");
//                Flowable.timer(3000, TimeUnit.MILLISECONDS)
//                        .subscribe(new Consumer<Long>() {
//                            @Override
//                            public void accept(@NonNull Long aLong) throws Exception {
//                                Beta.canNotifyUserRestart = true;
//                                restartApplication(MyApplication.getInstance());
//                            }
//                        });
            }
        }

        private void restartApplication(Context context) {
            final Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }

        @Override
        public void onApplyFailure(String msg) {
            if (MyApplication.Flag) {

                Log.i(TAG, "补丁应用失败: " + msg);
                ToastUtil.showLongToast("补丁应用失败");
            }
        }

        @Override
        public void onPatchRollback() {
            if (MyApplication.Flag) {
                Log.i(TAG, "补丁回滚: ");
                ToastUtil.showLongToast("补丁回滚");
            }

        }
    };


}

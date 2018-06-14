package com.hldj.hmyg.util;

import android.view.View;

import com.zf.iosdialog.widget.AlertDialog;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 提示工具类
 */

public class AlertUtil {
    public static void showAlert(CharSequence title, NeedSwipeBackActivity mActivity, View.OnClickListener onClickListener) {
        new AlertDialog(mActivity)
                .builder()
                .setTitle(title)
                .setPositiveButton("确定", onClickListener)
                .setNegativeButton("取消", v -> D.i("-------取消------"))
                .show();
        ;
    }



    public static void showAlert(CharSequence title,String right, NeedSwipeBackActivity mActivity, View.OnClickListener onClickListener , View.OnClickListener onCancle) {
        new AlertDialog(mActivity)
                .builder()
                .setTitle(title)
                .setPositiveButton(right, onClickListener)
                .setNegativeButton("取消",onCancle)
                .show();
        ;
    }

}

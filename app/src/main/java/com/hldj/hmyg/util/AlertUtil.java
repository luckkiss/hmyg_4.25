package com.hldj.hmyg.util;

import android.app.Dialog;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Window;

import com.hldj.hmyg.R;
import com.hldj.hmyg.buyer.weidet.DialogFragment.CommonDialogFragment1;
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


    public static void showAlert(CharSequence title, String right, NeedSwipeBackActivity mActivity, View.OnClickListener onClickListener, View.OnClickListener onCancle) {
        new AlertDialog(mActivity)
                .builder()
                .setTitle(title)
                .setPositiveButton(right, onClickListener)
                .setNegativeButton("取消", onCancle)
                .show();
        ;
    }


    public static void showCommonDialog(int layoutId, FragmentManager fragmentTransaction, DoConvertView doConvertView, boolean cancleAble) {
        CommonDialogFragment1.newInstance(context -> {
            Dialog dialog1 = new Dialog(context);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.setContentView(layoutId);
//            dialog1.setContentView(R.layout.item_show_seeding_tip);
            doConvertView.onConvert(dialog1);

            return dialog1;
        }, cancleAble).show(fragmentTransaction, "hello-world");
    }

    public static interface DoConvertView {
        void onConvert(Dialog viewRoot);
    }


    public static void showCommonEditDialog(FragmentManager fragmentTransaction, DoConvertView doConvertView) {

        int layout_id = R.layout.common_edit;

        CommonDialogFragment1.newInstance(context -> {
            Dialog dialog1 = new Dialog(context);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.setContentView(layout_id);
//            dialog1.setContentView(R.layout.item_show_seeding_tip);
            doConvertView.onConvert(dialog1);

            return dialog1;
        }, true).show(fragmentTransaction, "hello-world");
    }


}

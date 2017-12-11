package com.hldj.hmyg.buyer.weidet.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/4/27.
 */

public class CommonDialogFragment1 extends DialogFragment {

    /**
     * 监听弹出窗是否被取消
     */
    private OnDialogCancelListener mCancelListener;

    /**
     * 回调获得需要显示的dialog
     */
    private OnCallDialog mOnCallDialog;

    public interface OnDialogCancelListener {
        void onCancel();
    }

    public interface OnCallDialog {
        Dialog getDialog(Context context);
    }

    public static CommonDialogFragment1 newInstance(OnCallDialog call, boolean cancelable) {
        return newInstance(call, cancelable, null);
    }

    public static CommonDialogFragment1 newInstance(OnCallDialog call, boolean cancelable, OnDialogCancelListener cancelListener) {
        CommonDialogFragment1 instance = new CommonDialogFragment1();
        instance.setCancelable(cancelable);
        instance.mCancelListener = cancelListener;
        instance.mOnCallDialog = call;
        return instance;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (null == mOnCallDialog) {
            super.onCreateDialog(savedInstanceState);
        }
        return mOnCallDialog.getDialog(getActivity());
    }




    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();


        if (dialog != null) {
            //在5.0以下的版本会出现白色背景边框，若在5.0以上设置则会造成文字部分的背景也变成透明
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                //目前只有这两个dialog会出现边框
                if (dialog instanceof ProgressDialog || dialog instanceof DatePickerDialog) {
                    getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            Window window = getDialog().getWindow();
            WindowManager m =getActivity(). getWindowManager();
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.5f;

            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            windowParams.width = (int) (d.getWidth() * 0.90); // 宽度设置为屏幕的0.65
//            windowParams.width = (int) (d.getWidth() * 0.85); // 宽度设置为屏幕的0.65
            window.setAttributes(windowParams);


              /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
//        WindowManager m = getWindowManager();
//        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
//        p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65
//        dialogWindow.setAttributes(p);


        }
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mCancelListener != null) {
            mCancelListener.onCancel();
        }
    }

}
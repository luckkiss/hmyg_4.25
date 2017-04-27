package com.hldj.hmyg.buyer.weidet.DialogFragment;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentManager;

import com.hldj.hmyg.R;

/**
 * Created by Administrator on 2017/4/27.
 */

public class DialogFragmentHelper {

    private static final String TAG_HEAD = DialogFragmentHelper.class.getSimpleName();

    /**
     * 加载中的弹出窗
     */
    private static final int PROGRESS_THEME = R.style.Base_AlertDialog;
    private static final String PROGRESS_TAG = TAG_HEAD + ":progress";


    public static CommonDialogFragment showProgress(FragmentManager fragmentManager, String message) {
        return showProgress(fragmentManager, message, true, null);
    }

    public static CommonDialogFragment showProgress(FragmentManager fragmentManager, String message, boolean cancelable) {
        return showProgress(fragmentManager, message, cancelable, null);
    }

    public static CommonDialogFragment showProgress(FragmentManager fragmentManager, final String message, boolean cancelable
            , CommonDialogFragment.OnDialogCancelListener cancelListener) {

        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(context -> {
            ProgressDialog progressDialog = new ProgressDialog(context, PROGRESS_THEME);
            progressDialog.setMessage(message);
            return progressDialog;
        }, cancelable, cancelListener);
        dialogFragment.show(fragmentManager, PROGRESS_TAG);
        return dialogFragment;
    }

//    /**
//     * 带输入框的弹出窗
//     */
//    private static final int INSERT_THEME = R.style.Base_AlertDialog;
//    private static final String INSERT_TAG = TAG_HEAD + ":insert";
//
//    public static void showInsertDialog(FragmentManager manager, final String title, final IDialogResultListener<String> resultListener, final boolean cancelable) {
//
//        CommonDialogFragment dialogFragment = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
//            @Override
//            public Dialog getDialog(Context context) {
//                // ...
//                AlertDialog.Builder builder = new AlertDialog.Builder(context, INSERT_THEME);
//                builder.setPositiveButton(DIALOG_POSITIVE, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (resultListener != null) {
//                            resultListener.onDataResult(editText.getText().toString());
//                        }
//                    }
//                });
//                builder.setNegativeButton(DIALOG_NEGATIVE, null);
//                return builder.create();
//            }
//        }, cancelable, null);
//        dialogFragment.show(manager, INSERT_TAG);
//    }


}

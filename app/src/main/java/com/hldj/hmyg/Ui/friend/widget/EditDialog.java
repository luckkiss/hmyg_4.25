package com.hldj.hmyg.Ui.friend.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseDialogFragment;
import com.hy.utils.ToastUtil;

/**
 * 底部评论弹框
 */

public class EditDialog extends BaseDialogFragment {


    private EditText editText;
    private SuperTextView reply;
    public static OnReplyListener replyListener;


    public static EditDialog instance(String string) {
        EditDialog detailDialogFragment = new EditDialog();
        Bundle bundle = new Bundle();
        bundle.putString("str", string);
        detailDialogFragment.setArguments(bundle);
        return detailDialogFragment;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.edit;
    }

    @Override
    public void initData() {

    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {
        editText = (EditText) inflateView.findViewById(R.id.et_recive);
        reply = (SuperTextView) inflateView.findViewById(R.id.superTextView);
        Bundle bundle = getArguments();
        String str = bundle.getString("str");
        editText.setHint(str);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String replyMsg = editText.getText().toString();
                if (TextUtils.isEmpty(replyMsg)) {
                    ToastUtil.showLongToast("说点什么呗...^_^");
                    return;
                }
                if (replyListener != null) {
                    replyListener.OnReply(editText.getText().toString());
                }
                EditDialog.this.dismiss();
            }
        });

    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {

    }


    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                InputMethodManager inputManager =
                        (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        }, 300);
    }

    @Override
    public void initListener() {

    }

    public interface OnReplyListener {
        void OnReply(String reply);
    }

}

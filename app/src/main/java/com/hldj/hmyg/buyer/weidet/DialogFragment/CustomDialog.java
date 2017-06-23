package com.hldj.hmyg.buyer.weidet.DialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.hldj.hmyg.R;


/**
 * Created by Administrator on 2017/5/6 0006.
 */

public class CustomDialog extends Dialog {

    private AnimationDrawable mAnimationDrawable;

    public CustomDialog(Context context) {
        super(context, R.style.CustomDialog);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        setContentView(R.layout.load_dialog);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;
        window.setAttributes(windowParams);

    }


    @Override
    public void show() {
        super.show();
        ImageView img = (ImageView) this.findViewById(R.id.iv_amin_flowar);

        // 加载动画
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        // 默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        // 默认进入页面就开启动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
    }
}

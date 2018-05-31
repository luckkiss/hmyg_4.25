package com.hldj.hmyg.buyer.weidet.DialogFragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.util.D;

import java.io.Serializable;


/**
 *
 */

public class CustomDialog extends Dialog implements Serializable {

    private AnimationDrawable mAnimationDrawable;
    public TextView tv_loading_text;


    private Activity mActivity;

    public CustomDialog(Context context) {

        super(context, R.style.CustomDialog);
        mActivity = ((Activity) context);

    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(getContext());
    }


    public void setCanceledOutside(boolean flag) {
        setCanceledOnTouchOutside(flag);
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        setContentView(R.layout.load_dialog);

        tv_loading_text = (TextView) this.findViewById(R.id.tv_loading_text);


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

        if (!mActivity.isFinishing()) {
            try {
                super.show();
            } catch (Exception e) {
                D.w("-----------------" + e.getMessage());
            }
        }




        ImageView img = (ImageView) this.findViewById(R.id.iv_amin_flowar);

        // 加载动画
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        // 默认进入页面就开启动画
        if (mAnimationDrawable != null && !mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
//        }

    }

    @Override
    public void dismiss() {
//        if ( !((Activity) getContext()).isFinishing()) {
        super.dismiss();
        // 默认进入页面就开启动画
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
//        }
    }
}

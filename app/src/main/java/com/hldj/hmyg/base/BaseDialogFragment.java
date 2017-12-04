package com.hldj.hmyg.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hldj.hmyg.util.D;

/**
 * Created by  罗擦擦
 */

public abstract class BaseDialogFragment extends DialogFragment implements IBaseDialogFragment {

    private static final String TAG = "BaseDialogFragment";
    protected Activity mActivity;

    public View mRootView;

    public boolean viewCreated = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (null == mRootView) {
            //强制 竖屏 显示
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            this.mRootView = inflater.inflate(bindLayoutID(), null);
            //设置触摸挟持，，解决点击穿透问题
            mRootView.setOnTouchListener((view, motionEvent) -> true);
        }
        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        D.e("======当前Fragment===位置=====" + this.getClass().getName());
        return mRootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated: ");
        if (!viewCreated) {
            findView(view, savedInstanceState);
            initView(view, savedInstanceState);
            initListener();
            initData();
            viewCreated = false;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager m = getActivity().getWindowManager();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;
/**
 * <style name="dialog" parent="@android:style/Theme.Dialog">
 <item name="android:backgroundDimEnabled">false</item><!--activity不变暗-->
 </style>
 */
        windowParams.gravity = Gravity.BOTTOM;

        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//        windowParams.width = (int) (d.getWidth()); // 宽度设置为屏幕的0.65
        windowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setAttributes(windowParams);
    }

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        super.onAttach(context);
    }


    protected void setText(TextView tv, String str) {
        tv.setText(str);
    }

    /**
     * Views indexed with their IDs
     */
    private SparseArray<View> views = new SparseArray<>();

    protected <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = mRootView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }


}

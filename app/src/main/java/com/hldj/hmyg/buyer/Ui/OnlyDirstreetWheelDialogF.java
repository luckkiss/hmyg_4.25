package com.hldj.hmyg.buyer.Ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.CityGsonBean;

import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter_new;

/**
 * Created by Administrator on 2017/4/27.
 */

public class OnlyDirstreetWheelDialogF extends DialogFragment implements OnWheelChangedListener {


    OnDirSelectListener selectListener;
    List<CityGsonBean.ChildBeans> childBeans;
    CityGsonBean.ChildBeans beans;


    public OnlyDirstreetWheelDialogF addSelectListener(OnDirSelectListener selectListener) {
        this.selectListener = selectListener;
        return this;
    }


    public static OnlyDirstreetWheelDialogF instance(OnDirSelectListener selectListener, List<CityGsonBean.ChildBeans> childBeans) {
        OnlyDirstreetWheelDialogF wheelDialogF = new OnlyDirstreetWheelDialogF();
        wheelDialogF.addSelectListener(selectListener);
        wheelDialogF.childBeans = childBeans;
        return wheelDialogF;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialog);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);// 无标题
        getDialog().setCanceledOnTouchOutside(true);// 点击边际可消失
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dia_choose_area, null);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = getDialog().getWindow().getWindowManager().getDefaultDisplay().getWidth();
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }


    private void initView(View dia_choose_share) {
        WheelView wheelView = (WheelView) dia_choose_share.findViewById(R.id.id_Childs);
        wheelView.setViewAdapter(new ArrayWheelAdapter_new(getActivity(), childBeans));

        dia_choose_share.setOnClickListener(v -> {
            if (selectListener != null) {
                selectListener.onDirSelect(childBeans.get(wheelView.getCurrentItem()));
            }
            dismiss();
        });
    }


    protected void initProvinceDatas() {


    }


    private void setUpData() {


    }


    int cCurrent = 0;

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        cCurrent = wheel.getCurrentItem();
        beans = childBeans.get(cCurrent);

    }


    public interface OnDirSelectListener {
        void onDirSelect(CityGsonBean.ChildBeans childBeans);

    }

    @Override
    public void dismiss() {
        super.dismiss();

    }
}

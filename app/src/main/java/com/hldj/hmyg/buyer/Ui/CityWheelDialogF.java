package com.hldj.hmyg.buyer.Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hldj.hmyg.buyer.weidet.DialogFragment.CommonDialogFragment;

/**
 * Created by Administrator on 2017/4/27.
 */

public class CityWheelDialogF extends CommonDialogFragment {

    public static CityWheelDialogF instance() {
        CityWheelDialogF wheelDialogF = new CityWheelDialogF();

        return wheelDialogF;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


//        View view = inflater.inflate(R.layout.whell_city,null);


//        return view;
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}

package com.hldj.hmyg.Ui.myProgramChild;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.model.ProgramPurchaseModel;
import com.hldj.hmyg.presenter.ProgramPurchasePresenter;

/**
 * Created by 罗擦擦 on 2017/6/29 0029.
 */

public class ProgramPurchaseActivityOnly extends ProgramPurchaseActivity {


    public static String title = " - ";
    public static String totlePrice = "";

    @Override
    protected void initAndRequestGysData() {
    }


    @Override
    public void initView() {


        mPresenter = new ProgramPurchasePresenter();
        mModel = new ProgramPurchaseModel();
        mPresenter.setVM(this, mModel);

        searchKey = getIntent().getExtras().getString("sellerId");

        super.initView();
        getView(R.id.appbar).setVisibility(View.GONE);


        View head = LayoutInflater.from(mActivity).inflate(R.layout.toolbar_3_0, null);

        TextView textView = (TextView) head.findViewById(R.id.toolbar_title);
        textView.setText(title);
        head.findViewById(R.id.toolbar_left_icon).setOnClickListener(v -> {
            finish();
        });


        coreRecyclerView.addHeaderView(head);

        TextView textView1 = new TextView(mActivity);
        textView1.setText(TextUtils.isEmpty(totlePrice) ? " 暂未开标" : totlePrice);
        textView1.setTextColor(getColorByRes(R.color.main_color));
        textView1.setBackgroundColor(getColorByRes(R.color.bg_cgd));
        textView1.setPadding(35, 35, 35, 35);
        coreRecyclerView.addHeaderView(textView1);


    }

    @Override
    protected void switchGys_pz(int tag) {
        super.switchGys_pz(1);
    }

    //传值开启界面
    public static void start(Activity mAct, String ext, String sellerId) {
        Intent intent = new Intent(mAct, ProgramPurchaseActivityOnly.class);
        intent.putExtra("ProgramPurchaseActivity", ext);
        intent.putExtra("sellerId", sellerId);//将  sellerId  赋值给 searchKey
        mAct.startActivity(intent);
    }


//    @Override
//    public String setTitle() {
//        return title;
//    }
}

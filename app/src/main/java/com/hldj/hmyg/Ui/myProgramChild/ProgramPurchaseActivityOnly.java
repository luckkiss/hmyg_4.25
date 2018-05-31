package com.hldj.hmyg.Ui.myProgramChild;

import android.app.Activity;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.model.ProgramPurchaseModel;
import com.hldj.hmyg.presenter.ProgramPurchasePresenter;
import com.hldj.hmyg.util.D;

/**
 * Created by 罗擦擦 on 2017/6/29 0029.
 */

public class ProgramPurchaseActivityOnly extends ProgramPurchaseActivity {


    public static String title = " - ";
    public static SpannableStringBuilder totlePrice;

    @Override
    protected void initAndRequestGysData() {
    }


    @Override
    public void initView() {
        initExtralPermision();

        mPresenter = new ProgramPurchasePresenter();
        mModel = new ProgramPurchaseModel();
        mPresenter.setVM(this, mModel);

        searchKey = getIntent().getExtras().getString("sellerId", "");

        super.initView();
        getView(R.id.appbar).setVisibility(View.GONE);


        View head = LayoutInflater.from(mActivity).inflate(R.layout.toolbar_3_0, null);

        TextView textView = (TextView) head.findViewById(R.id.toolbar_title);
        textView.setText(title);
        head.findViewById(R.id.toolbar_left_icon).setOnClickListener(v -> {
            finish();
        });


        coreRecyclerView.addHeaderView(head);

//        TextView textView1 = new TextView(mActivity);

//        if (!TextUtils.isEmpty(getExtralState()) && getExtralState().equals(PurchaseStatus.expired.enumValue)) {
//            //已开标
////            textView1.setText("供应商名称：花木易购供应商1\n联系电话：17074990702" + "");
//            textView1.setText(totlePrice);
//        } else {
//            textView1.setText(totlePrice);
//        }

        View headView = LayoutInflater.from(mActivity).inflate(R.layout.item_head_program_purchase, null);

     TextView   textView1 = headView.findViewById(R.id.tv_content_head);
        textView1.setText(totlePrice);

        textView1.setLineSpacing(0.0f, 1.4f);
//        textView1.setBackground(getResources().getDrawable(R.drawable.bg_bottom_line_gray_20));
//        textView1.setTextSize(15);
        textView1.setTextColor(getColorByRes(R.color.text_color666));
        Linkify.addLinks(textView1, Linkify.PHONE_NUMBERS);
//        textView1.setBackgroundColor(getColorByRes(R.color.white));
//        textView1.setPadding(40, MyApplication.dp2px(mActivity, 2), 35, MyApplication.dp2px(mActivity, 14));
        textView1.setVisibility(View.VISIBLE);

        D.w("-------------这里写一个 隐藏头部 。怕出现bug 特此记录----------");

        coreRecyclerView.addHeaderView(headView);


    }

    @Override
    protected void switchGys_pz(int tag) {
        super.switchGys_pz(0);
    }


    //传值开启界面
    //trueQutoe  是否 拥有权限
    public static void start(Activity mAct, String ext, String sellerId, String states, boolean trueQutoe) {
        Intent intent = new Intent(mAct, ProgramPurchaseActivityOnly.class);
        intent.putExtra("ProgramPurchaseActivity", ext);
        intent.putExtra("sellerId", sellerId);//将  sellerId  赋值给 searchKey
        intent.putExtra(ProgramPurchaseActivity.STATE, states);//将  sellerId  赋值给 searchKey
        intent.putExtra(ProgramPurchaseActivity.HAS_PERMISION, trueQutoe);
        mAct.startActivity(intent);
    }

    private void initExtralPermision() {

        setTrueQutoe(getIntent().getBooleanExtra(ProgramPurchaseActivity.HAS_PERMISION, false));

        D.i("======initExtralPermision=========" + getTrueQutoe());

    }


//    @Override
//    public String setTitle() {
//        return title;
//    }
}

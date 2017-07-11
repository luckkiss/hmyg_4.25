package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.myProgramChild.ProgramDirctActivity;
import com.hldj.hmyg.Ui.myProgramChild.ProgramProtocolActivity;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.contract.MyProgramContract;
import com.hldj.hmyg.model.MyProgramGsonBean;
import com.hldj.hmyg.model.MyProgramModel;
import com.hldj.hmyg.presenter.MyProgramPresenter;
import com.hldj.hmyg.util.D;

import java.util.List;


/**
 * Created by luocaca on 2017/6/27 0027.
 * 我的项目  ---- 项目列表
 */

public class MyProgramActivity extends BaseMVPActivity<MyProgramPresenter, MyProgramModel> implements MyProgramContract.View {
    private static final String TAG = "MyProgramActivity";
    private CoreRecyclerView recyclerView;
    private String search_key = "";

    @Override
    public int bindLayoutID() {//step 1
        return R.layout.activity_my_program;
    }

    @Override
    public void initView() {

        recyclerView = new CoreRecyclerView(mActivity);
        //初始化recycleview
        recyclerView.init(new BaseQuickAdapter<MyProgramGsonBean.DataBeanX.PageBean.DataBean, BaseViewHolder>(R.layout.item_program_list) {
            @Override
            protected void convert(BaseViewHolder helper, MyProgramGsonBean.DataBeanX.PageBean.DataBean item) {
                helper.convertView.setOnClickListener(view -> {

                    if (!item.typeName.equals("直购")) {
                        ProgramDirctActivity.start(mActivity, item.id);
                    } else {
                        ProgramProtocolActivity.start(mActivity, item.id);
                    }
                });
                helper.setText(R.id.tv_program_pos, (helper.getAdapterPosition() + 1) + "");//获取位置 pos
                helper.setText(R.id.tv_program_name, item.projectName);//获取 项目的 名称
                helper.setText(R.id.tv_program_service_price, item.servicePoint + "%")
                        .setVisible(R.id.tv_program_text, item.servicePoint != 0.0)
                        .setVisible(R.id.tv_program_service_price, item.servicePoint != 0.0)
                ;//

                helper.setText(R.id.tv_program_load_car_count, filterColor(item.loadCarCount + "车", item.loadCarCount + "", R.color.main_color));//
                helper.setText(R.id.tv_program_alreay_order, "￥" + item.totalAmount);//
                helper.setText(R.id.tv_program_state, strFilter(item.typeName));//
//                helper.setText(R.id.tv_program_state, item.typeName);//
                int colorId = item.typeName.equals("直购") ? ContextCompat.getColor(mActivity, R.color.price_orige) : ContextCompat.getColor(mActivity, R.color.main_color);
                helper.setTextColor(R.id.tv_program_state, colorId);
            }
        }, false)
                .openLoadMore(10, page -> {
//                    mPresenter.getData(page + "", search_key, "params2");
//                    showLoading();
                    mPresenter.getData(page + "", search_key);
                })
                .openRefresh();
        getContentView().addView(recyclerView);
        recyclerView.onRefresh();
    }

    @Override
    public void showErrir(String erMst) {
        recyclerView.selfRefresh(false, erMst);
        hindLoading();
    }

    @Override
    public void initVH() {
        getView(R.id.sptv_program_do_search).setOnClickListener(view -> {
            D.e("==========根据条件搜索===============");
            search_key = getSearchText();
            recyclerView.onRefresh();
        });
    }

    @Override
    public void initXRecycle(List<MyProgramGsonBean.DataBeanX.PageBean.DataBean> gsonBean) {
        recyclerView.getAdapter().addData(gsonBean);
        recyclerView.selfRefresh(false);
        hindLoading();
    }

    @Override
    public void initCounts(CountTypeGsonBean gsonBean) {

    }


    @Override
    public void onDeled(boolean bo) {

    }

    @Override
    public String getSearchText() {

        return ((EditText) getView(R.id.et_program_serach_text)).getText() + "";
    }

    @Override
    public ViewGroup getContentView() {
        return getView(R.id.my_program_content);
    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, MyProgramActivity.class));
    }

    @Override
    public String setTitle() {
        return "项目列表";
    }


}

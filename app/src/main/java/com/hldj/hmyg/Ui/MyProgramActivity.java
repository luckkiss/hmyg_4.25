package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;
import android.view.ViewGroup;

import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.myProgramChild.ProgramDirctActivity;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.contract.MyProgramContract;
import com.hldj.hmyg.model.MyProgramModel;
import com.hldj.hmyg.presenter.MyProgramPresenter;
import com.hldj.hmyg.util.D;
import com.neopixl.pixlui.components.edittext.EditText;


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
        recyclerView.init(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_home_cjgg) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

                helper.convertView.setOnClickListener(view->{
                    ProgramDirctActivity.start(mActivity);
                });

            }
        }, false)
                .openLoadMore(10, page -> {
//                    mPresenter.getData(page + "", search_key, "params2");
                    initXRecycle(null);

                });
        getContentView().addView(recyclerView);
        initXRecycle(null);
        initXRecycle(null);
        initXRecycle(null);
        initXRecycle(null);
        initXRecycle(null);
        initXRecycle(null);
        initXRecycle(null);
    }

    @Override
    public void initVH() {
        getView(R.id.sptv_do_search).setOnClickListener(view -> {
            D.e("==========根据条件搜索===============");
            search_key = getSearchText();
            recyclerView.onRefresh();
        });
    }

    @Override
    public void initXRecycle(BPageGsonBean gsonBean) {
        recyclerView.getAdapter().addData("1346");
        recyclerView.getAdapter().addData("1346");
        recyclerView.getAdapter().addData("1346");
        recyclerView.getAdapter().addData("1346");
        recyclerView.getAdapter().addData("1346");
        recyclerView.getAdapter().addData("1346");
        recyclerView.getAdapter().addData("1346");
        recyclerView.getAdapter().addData("1346");
        recyclerView.getAdapter().addData("1346");
        recyclerView.getAdapter().addData("1346");

    }

    @Override
    public void initCounts(CountTypeGsonBean gsonBean) {

    }

    @Override
    public void onDeled(boolean bo) {

    }

    @Override
    public String getSearchText() {
        return ((EditText) getView(R.id.et_addr_search)).getText() + "";
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

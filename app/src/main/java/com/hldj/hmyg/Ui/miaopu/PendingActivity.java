package com.hldj.hmyg.Ui.miaopu;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.ManagerSplitListActivity_new;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.enums.SeedlingStatus;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.model.ManagerListModel;
import com.hldj.hmyg.presenter.ManagerListPresenter;
import com.hldj.hmyg.util.D;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 待处理
 */

public class PendingActivity extends ManagerSplitListActivity_new<ManagerListPresenter, ManagerListModel> {

    private static final String TAG = "PendingActivity";

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, PendingActivity.class);
        intent.putExtra("id", id);
        Log.i(TAG, "start2Activity: 苗圃的  ---  >  id");
        context.startActivity(intent);
    }


    @Override
    public void initView() {
        setStoreId(MyApplication.getUserBean().storeId);
        super.initView();


        getView(R.id.ll_bottom_layout).setVisibility(View.GONE);

        ((ViewGroup) xgkc.getParent()).setVisibility(View.GONE);

        findViewById(R.id.toolbar2).setVisibility(View.GONE);
        findViewById(R.id.toolbar1).setVisibility(View.VISIBLE);
        Log.i("PendingActivity", "强行隐藏toolbar 栏目。禁止搜索");


    }


    @Override
    protected void onStart() {
        super.onStart();
        tvs[2].setText("到期下架");
        tvs[5].setText("被退回");


    }

    @Override
    public void requestData(int page) {

        /**
         * 	String storeId = request.getParameter("storeId");
         String statusStr = request.getParameter("status");
         */
        mPresenter.getToDoListData(new ResultCallBack() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

            }
        }, MyApplication.getUserBean().storeId, getStatus());//被退回
        requestCounts();
    }

    @Override
    public void requestCounts() {

        mPresenter.getTodoStatusCount(null, MyApplication.getUserBean().storeId);


    }

    // 3 过期
    // 4 退回

    @Override
    public void switch2Refresh(String stat, int index) {
        if (index == 0) {
            switch2Refresh("published", 2);
            return;
        }
        if (tvs == null) {
            tvs = new TextView[]{getView(R.id.tv_01), getView(R.id.tv_02), getView(R.id.tv_03), getView(R.id.tv_04), getView(R.id.tv_05), getView(R.id.tv_06)};
        }
        if (ivs == null) {
            ivs = new ImageView[]{getView(R.id.botton_lines_1), getView(R.id.botton_lines_2), getView(R.id.botton_lines_3), getView(R.id.botton_lines_4), getView(R.id.botton_lines_5), getView(R.id.botton_lines_6)};
        }
        if (rls == null) {
            //rl_01
            rls = new RelativeLayout[]{getView(R.id.rl_01), getView(R.id.rl_02), getView(R.id.rl_03), getView(R.id.rl_04), getView(R.id.rl_05), getView(R.id.rl_06),};
        }
        if (lines == null) {
            //rl_01
            lines = new View[]{getView(R.id.botton_lines_1), getView(R.id.botton_lines_2), getView(R.id.botton_lines_3), getView(R.id.botton_lines_4), getView(R.id.botton_lines_5), getView(R.id.botton_lines_6),};
        }


        // index == 2 或者   ==  5


        if (index == 2) {   // 已上架

            setType("");
            setStatus(SeedlingStatus. outline.enumValue);



            tvs[2].setTextColor(ContextCompat.getColor(mActivity, R.color.main_color));
            ivs[2].setVisibility(View.VISIBLE);

            tvs[5].setTextColor(ContextCompat.getColor(mActivity, R.color.text_color333));
            ivs[5].setVisibility(View.INVISIBLE);


        } else if (index == 5) // 带操作
        {
            setType("");
            setStatus(SeedlingStatus.backed.enumValue);
            tvs[2].setTextColor(ContextCompat.getColor(mActivity, R.color.text_color333));
            ivs[2].setVisibility(View.INVISIBLE);

            tvs[5].setTextColor(ContextCompat.getColor(mActivity, R.color.main_color));
            ivs[5].setVisibility(View.VISIBLE);

        }


//        resetStateList();


//        for (int i1 = 0; i1 < tvs.length; i1++) {
//            if (index == i1) {
//                tvs[i1].setTextColor(ContextCompat.getColor(mActivity, R.color.main_color));
//                ivs[i1].setVisibility(View.VISIBLE);
//            } else {
//                tvs[i1].setTextColor(ContextCompat.getColor(mActivity, R.color.text_color333));
//                ivs[i1].setVisibility(View.INVISIBLE);
//            }
//        }
        xRecyclerView.onRefresh();
    }


    @Override
    public void invadeDoConvert(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item, NeedSwipeBackActivity mActivity) {
        super.invadeDoConvert(helper, item, mActivity);


//        helper.setText(R.id.tv_06,"hello wolrldl");


//        helper
//                .setVisible(R.id.bottom_layout, true)
//                .addOnClickListener(R.id.bottom_left, null)
//                .addOnClickListener(R.id.bottom_right, null)
//
//        ;


        helper.addOnClickListener(R.id.swipe_manager1, 编辑(helper, item, mActivity));

    }


    @Override
    public String setTitle() {

        return "待处理";
    }

    @Override
    public void initTodoStatusCount(SimpleGsonBean gsonBean) {
//        ToastUtil.showLongToast(gsonBean.msg);
        list_counts.clear();
        LinearLayout linearLayout = getView(R.id.ll_counts_content);
        findTagTextView(linearLayout);
        for (int i = 0; i < list_counts.size(); i++) {
            switch (i) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    list_counts.get(i).setText("(" + gsonBean.getData(). outlineCount + ")");

                    D.w(" storeId is =====>  " + gsonBean.getData().storeId);
                    break;
                case 3:
//                    list_counts.get(i).setText("(" + gsonBean.data.pendingCount + ")");
                    break;
                case 4:
//                    list_counts.get(i).setText("(" + gsonBean.data.countMap.backed + ")");
                    break;
                case 5:
                    list_counts.get(i).setText("(" + gsonBean.getData().backedCount + ")");

//                    list_counts.get(i).setText("(" + gsonBean.data.pendingCount + ")");
                    break;

            }
        }
    }
}

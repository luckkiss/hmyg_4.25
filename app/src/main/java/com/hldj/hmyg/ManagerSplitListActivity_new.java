package com.hldj.hmyg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.contract.ManagerListContract;
import com.hldj.hmyg.model.ManagerListModel;
import com.hldj.hmyg.presenter.ManagerListPresenter;
import com.hldj.hmyg.widget.CommonListSpinner1;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 苗木管理界面  苗木 管理 界面。。根据苗圃进行拆分
 */
@SuppressLint("ClickableViewAccessibility")
public class ManagerSplitListActivity_new extends ManagerListActivity_new<ManagerListPresenter, ManagerListModel> implements ManagerListContract.View {


    int id = R.layout.activity_manager_list_new;
    private CommonListSpinner1 commonListSpinner1;
    private static final String TAG = "ManagerSplitListActivit";

    public static void start2Activity(Context context) {
        context.startActivity(new Intent(context, ManagerSplitListActivity_new.class));
    }

    @ViewInject(id = R.id.muzt)
    TextView muzt;


    @ViewInject(id = R.id.xgkc)
    TextView xgkc;


    @Override
    public void initView() {
        super.initView();
        FinalActivity.initInjectedView(mActivity);

        muzt.setOnClickListener(v -> 苗木状态弹框(v));
        xgkc.setOnClickListener(v -> 修改库存(v));

        // 隐藏之后  重新调用一次  切换  默认显示 已上架 状态
        隐藏3对控件1245();
        switch2Refresh("published", 2);

        /* 本界面隐藏  头部 tab  3个 */

//        getView(R.id.rl_01).setVisibility(View.GONE);
//        getView(R.id.rl_02).setVisibility(View.GONE);
//
//        getView(R.id.rl_04).setVisibility(View.GONE);
//        getView(R.id.rl_05).setVisibility(View.GONE);


        /* 本界面隐藏  头部 tab  3个 */


    }


    private void 修改库存(View v) {


        if (isOpenEdit)// 开启状态
        {
            if (xRecyclerView.getAdapter().getData() != null && xRecyclerView.getAdapter().getData().size() != 0) {

                for (int i = 0; i < xRecyclerView.getAdapter().getData().size(); i++) {

                    if (xRecyclerView.getAdapter().getData().get(i) instanceof BPageGsonBean.DatabeanX.Pagebean.Databean) {
                        BPageGsonBean.DatabeanX.Pagebean.Databean databean = ((BPageGsonBean.DatabeanX.Pagebean.Databean) xRecyclerView.getAdapter().getData().get(i));


                        Log.i(TAG, "修改库存:  pos = " + i + "  chang num = " + databean.inputNum + "  修改的id = " + databean.id + "  是否选中 --> " + databean.isChecked);


                    }
                }

            }
            isOpenCheck = !isOpenCheck;
            isOpenEdit = !isOpenEdit;

            xRecyclerView.getAdapter().notifyDataSetChanged();


        } else {
            isOpenCheck = !isOpenCheck;
            isOpenEdit = !isOpenEdit;
            xRecyclerView.getAdapter().notifyDataSetChanged();

        }


    }


    private void 苗木状态弹框(View archor ) {
        ToastUtil.showLongToast("弹框");

        List<String> dataLeft = new ArrayList<>();
        dataLeft.add("已上架");
        dataLeft.add("在售中");
        dataLeft.add("审核中");


        List<String> dataRight = new ArrayList<>();
        dataRight.add("苗木状态");
        dataRight.add("被撤回");
        dataRight.add("未提交");
        dataRight.add("过期下架");


        //                        View view = LayoutInflater.from(mActivity).inflate(R.layout.list_item_sort, null);
//                        TextView tv_item = view.findViewById(R.id.tv_item);
//                        tv_item.setText("213465");
//                        tv_item.setTextColor(Color.BLACK);

        if (commonListSpinner1 == null)
            commonListSpinner1 = new CommonListSpinner1.Builder<String>()
                    .setDatasList(dataLeft)
                    .setMApplyParentView((View) archor.getParent())
                    .setContentView(new CommonListSpinner1.CallContentView<String>() {
                        @Override
                        public void convert(int position, String str, CommonListSpinner1.ViewHolder viewHolder, String codeStrings) {
//                        View view = LayoutInflater.from(mActivity).inflate(R.layout.list_item_sort, null);
//                        TextView tv_item = view.findViewById(R.id.tv_item);
//                        tv_item.setText("213465");
//                        tv_item.setTextColor(Color.BLACK);
                            TextView tv_item = viewHolder.getView(R.id.tv_item);
                            if (position == currentPos) {
                                tv_item.setText(str);
                                tv_item.setTextColor(getColorByRes(R.color.main_color));
                            } else {
                                tv_item.setText(str);
                                tv_item.setTextColor(getColorByRes(R.color.text_color666));
                            }
                            View view = viewHolder.getView(R.id.is_check);
                            view.setVisibility(position == currentPos ? View.VISIBLE : View.GONE);

                            tv_item.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    currentPos = position;
                                    commonListSpinner1.dismiss();
                                }
                            });

                        }

                        @Override
                        public int getLayoutId() {
                            return R.layout.list_item_sort;
                        }

                        @Override
                        public void onHistory(String historyString) {

                        }
                    })
                    .build(mActivity);

        commonListSpinner1.ShowWithPos(currentPos);

    }

    int currentPos = 0;

    private void 隐藏3对控件1245() {
        rls[0].setVisibility(View.GONE);
        rls[1].setVisibility(View.GONE);
        rls[3].setVisibility(View.GONE);
        rls[4].setVisibility(View.GONE);

        lines[0].setVisibility(View.GONE);
        lines[1].setVisibility(View.GONE);
        lines[3].setVisibility(View.GONE);
        lines[4].setVisibility(View.GONE);
    }


    @Override
    public void switch2Refresh(String stat, int index) {

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

        setStatus(stat);
        // index == 2 或者   ==  5


        if (index == 2) {   // 已上架
            tvs[2].setTextColor(ContextCompat.getColor(mActivity, R.color.main_color));
            ivs[2].setVisibility(View.VISIBLE);

            tvs[5].setTextColor(ContextCompat.getColor(mActivity, R.color.text_color333));
            ivs[5].setVisibility(View.INVISIBLE);
        } else if (index == 5) // 带操作
        {
            tvs[2].setTextColor(ContextCompat.getColor(mActivity, R.color.text_color333));
            ivs[2].setVisibility(View.INVISIBLE);

            tvs[5].setTextColor(ContextCompat.getColor(mActivity, R.color.main_color));
            ivs[5].setVisibility(View.VISIBLE);
        }


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
    protected void onResume() {
        super.onResume();
        Button button = getView(R.id.btn_manager_storege);
        button.setText("更新 12-20-10");
        button.setOnClickListener(updata);
    }


    View.OnClickListener updata = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtil.showLongToast("更新");
        }
    };

}

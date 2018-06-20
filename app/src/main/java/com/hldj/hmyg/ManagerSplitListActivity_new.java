package com.hldj.hmyg;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.M.AddressBean;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.base.MyFinalActivity;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBeanData;
import com.hldj.hmyg.bean.enums.SeedlingStatus;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.DialogFragment.CommonDialogFragment;
import com.hldj.hmyg.contract.ManagerListContract;
import com.hldj.hmyg.model.ManagerListModel;
import com.hldj.hmyg.presenter.ManagerListPresenter;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.SaveSeedlingActivity_change_data;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.widget.CommonListSpinner1;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 苗木管理界面  苗木 管理 界面。。根据苗圃进行拆分
 */
@SuppressLint("ClickableViewAccessibility")
public class ManagerSplitListActivity_new<P extends ManagerListPresenter, M extends ManagerListModel> extends ManagerListActivity_new<ManagerListPresenter, ManagerListModel> implements ManagerListContract.View {


    int id = R.layout.activity_manager_list_new;
    private CommonListSpinner1 commonListSpinner1;
    private static final String TAG = "ManagerSplitListActivit";

    public static void start2Activity(Context context, String id) {
        Intent intent = new Intent(context, ManagerSplitListActivity_new.class);
        intent.putExtra("id", id);
        Log.i(TAG, "start2Activity: 苗圃的  ---  >  id");
        context.startActivity(intent);
    }


    /*苗圃  id   -- -  地址id */
    private String getExtraNurseryId() {
        Bundle bundle = getIntent().getExtras();
        if (TextUtils.isEmpty(bundle != null ? bundle.getString("id") : null)) {
            findViewById(R.id.ll_bottom_layout).setVisibility(View.GONE);

            findViewById(R.id.toolbar1).setVisibility(View.GONE);
            findViewById(R.id.toolbar2).setVisibility(View.VISIBLE);


            View view = findViewById(R.id.toolbar2);
            view.findViewById(R.id.toolbar_left_icon).setOnClickListener(v -> {
                finish();
            });
            EditText editText = (EditText) findViewById(R.id.search_content);
            view.findViewById(R.id.iv_view_type).setOnClickListener(v -> {
//              ToastUtil.showShortToast("搜索-->" +editText.getText());
                setSearchKey(editText.getText().toString());
                xRecyclerView.onRefresh();
            });

            return "";
        } else {
            findViewById(R.id.toolbar1).setVisibility(View.VISIBLE);
            findViewById(R.id.toolbar2).setVisibility(View.GONE);


            return bundle.getString("id", "");
        }
    }

    @ViewInject(id = R.id.muzt)
    public TextView muzt;


    @ViewInject(id = R.id.xgkc)
    public TextView xgkc;


    public void injectView() {
        MyFinalActivity.initInjectedView(mActivity);
    }


    @Override
    public void initView() {

        setNurseryId(getExtraNurseryId());

        super.initView();
        injectView();


        muzt.setOnClickListener(v -> 苗木状态弹框(v));
//        xgkc.setOnClickListener(v -> 修改库存(v));

        // 隐藏之后  重新调用一次  切换  默认显示 已上架 状态
        隐藏3对控件1245();


//        switch2Refresh("published", 2);



        /* 本界面隐藏  头部 tab  3个 */

//        getView(R.id.rl_01).setVisibility(View.GONE);
//        getView(R.id.rl_02).setVisibility(View.GONE);
//
//        getView(R.id.rl_04).setVisibility(View.GONE);
//        getView(R.id.rl_05).setVisibility(View.GONE);


        /* 本界面隐藏  头部 tab  3个 */


    }


    /**
     * 入侵
     * 父类  渲染  recycleview
     * 进行动态更替 内容
     *
     * @param helper
     * @param item
     * @param mActivity
     */
    @Override
    public void invadeDoConvert(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item, NeedSwipeBackActivity mActivity) {
        helper
                .setVisible(R.id.bottom_layout, true)
                .addOnClickListener(R.id.bottom_left, doLeft(helper, item, mActivity))
                .addOnClickListener(R.id.bottom_right, doRight(helper, item, mActivity))
        ;

    }


    /*
     *  左右的动作  随时根据 内容来变换
     *  已上架   下架   修改库存
     *  审核中      撤回
     *  已下架   删除   编辑
     *  被退回：删除，编辑
     *
     * */
    private View.OnClickListener doLeft(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item, NeedSwipeBackActivity mActivity) {
        TextView tv_left = helper.getView(R.id.bottom_left);

        View line = helper.getView(R.id.line);
        // 审核中
        if (SeedlingStatus.unaudit.enumValue.equals(item.status)) {
            tv_left.setText("撤回");

            line.setVisibility(View.GONE);
            return 撤回(helper, item, mActivity);
            // 已上架
        } else if (SeedlingStatus.published.enumValue.equals(item.status)) {//
            tv_left.setText("下架");

            line.setVisibility(View.VISIBLE);
            return 下架(helper, item, mActivity);
            //已下架 --
        } else if (
                SeedlingStatus.outline.enumValue.equals(item.status) ||
                        SeedlingStatus.backed.enumValue.equals(item.status) ||
                        SeedlingStatus.unsubmit.enumValue.equals(item.status)
                ) {
            tv_left.setText("删除");
            line.setVisibility(View.VISIBLE);
            return 删除(helper, item, mActivity);
            //被撤回
        }
//        else if (SeedlingStatus.backed.enumValue.equals(item.status)) {
//            tv_left.setText("删除");
//            line.setVisibility(View.VISIBLE);
//            return 删除(helper, item, mActivity);
//        } else if (SeedlingStatus.unsubmit.enumValue.equals(item.status)) {
//            tv_left.setText("删除");
//            line.setVisibility(View.VISIBLE);
//            return 删除(helper, item, mActivity);
//        }
        /**
         * 审核中：撤回
         已上架：下架、修改库存
         已下架，被退回，未提交：删除，编辑
         */
        else {
            Log.i(TAG, "doLeft: 未知状态");
            return null;
        }


        /**
         * 已下架   删除   编辑
         *  被退回：删除，编辑
         */

    }

    private View.OnClickListener 删除(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item, NeedSwipeBackActivity mActivity) {
        return v -> {
//            ToastUtil.showLongToast("do left   删除");

            MaterialDialog mMaterialDialog = new MaterialDialog(mActivity);
            mMaterialDialog.setMessage("确定删除该商品？")
                    // mMaterialDialog.setBackgroundResource(R.drawable.background);
                    .setPositiveButton(getString(R.string.ok),
                            v1 -> {
                                mMaterialDialog.dismiss();
                                new BasePresenter()
                                        .putParams("id", item.id)
                                        .doRequest("admin/seedling/del", new HandlerAjaxCallBack(mActivity) {
                                            @Override
                                            public void onRealSuccess(SimpleGsonBean gsonBean) {
                                                ToastUtil.showLongToast(gsonBean.msg);
                                                xRecyclerView.onRefresh();
                                            }
                                        });
                            })
                    .setNegativeButton(getString(R.string.cancle), v12 -> mMaterialDialog.dismiss()).setCanceledOnTouchOutside(true)
                    // You can change the message anytime.
                    // mMaterialDialog.setTitle("提示");
                    .setOnDismissListener(
                            dialog -> {
                            }).show();


        };
    }

    private View.OnClickListener 下架(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item, NeedSwipeBackActivity mActivity) {
        return v -> {
//            ToastUtil.showLongToast("do left   下架");
            MaterialDialog mMaterialDialog = new MaterialDialog(mActivity);
            mMaterialDialog.setMessage("确定是否下架该商品？")
                    // mMaterialDialog.setBackgroundResource(R.drawable.background);
                    .setPositiveButton(getString(R.string.ok),
                            v1 -> {
                                mMaterialDialog.dismiss();
                                new BasePresenter()
                                        .putParams("id", item.id)
                                        .doRequest("admin/seedling/doOutline", new HandlerAjaxCallBack(mActivity) {
                                            @Override
                                            public void onRealSuccess(SimpleGsonBean gsonBean) {
                                                ToastUtil.showLongToast(gsonBean.msg);
                                                xRecyclerView.onRefresh();
                                            }
                                        });
                            })
                    .setNegativeButton(getString(R.string.cancle), v12 -> mMaterialDialog.dismiss()).setCanceledOnTouchOutside(true)
                    // You can change the message anytime.
                    // mMaterialDialog.setTitle("提示");
                    .setOnDismissListener(
                            dialog -> {
                            }).show();
        };
    }

    private View.OnClickListener 撤回(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item, NeedSwipeBackActivity mActivity) {
        return v -> {
//            ToastUtil.showLongToast("do left   撤回");


            new BasePresenter()
                    .putParams("id", item.id)
                    .doRequest("admin/seedling/doBack", new HandlerAjaxCallBack(mActivity) {
                        @Override
                        public void onRealSuccess(SimpleGsonBean gsonBean) {
                            ToastUtil.showLongToast(gsonBean.msg);
                            xRecyclerView.onRefresh();
                        }
                    });
//            FinalHttp finalHttp = new FinalHttp();
//            GetServerUrl.addHeaders(finalHttp, true);
//            AjaxParams params = new AjaxParams();
//            params.put("id", id);
//            finalHttp.post(GetServerUrl.getUrl() + "admin/seedling/doBack",
        };
    }

    private View.OnClickListener doRight(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item, NeedSwipeBackActivity mActivity) {
        TextView tv_right = helper.getView(R.id.bottom_right);

        // 审核中
        if (SeedlingStatus.unaudit.enumValue.equals(item.status)) {

            tv_right.setVisibility(View.GONE);

            return null;
            // 已上架
        } else if (SeedlingStatus.published.enumValue.equals(item.status)) {//

            tv_right.setVisibility(View.VISIBLE);

            tv_right.setText("修改库存");
            tv_right.setTextColor(getColorByRes(R.color.main_color));
            return 修改库存(helper, item, mActivity);
            //已下架 -- 被撤回   、删除  上架
        } else if (
                SeedlingStatus.outline.enumValue.equals(item.status) ||
                        SeedlingStatus.backed.enumValue.equals(item.status) ||
                        SeedlingStatus.unsubmit.enumValue.equals(item.status)
                ) {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setText("编辑");
            return 编辑(helper, item, mActivity);
        }


//        else if (SeedlingStatus.backed.enumValue.equals(item.status)) {
//            tv_right.setVisibility(View.VISIBLE);
//            tv_right.setText("编辑");
//            return 编辑(helper, item, mActivity);
//        } else if (SeedlingStatus.unsubmit.enumValue.equals(item.status)) {
//            tv_right.setVisibility(View.VISIBLE);
//            tv_right.setText("编辑");
//            return 编辑(helper, item, mActivity);
//        }
        else {
            Log.i(TAG, "doLeft: 未知状态");
            return null;
        }

    }


    private View.OnClickListener 修改库存(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item, NeedSwipeBackActivity mActivity) {
//        if (isOpenEdit)// 开启状态
//        {
//            if (xRecyclerView.getAdapter().getData() != null && xRecyclerView.getAdapter().getData().size() != 0) {
//
//                for (int i = 0; i < xRecyclerView.getAdapter().getData().size(); i++) {
//
//                    if (xRecyclerView.getAdapter().getData().get(i) instanceof BPageGsonBean.DatabeanX.Pagebean.Databean) {
//                        BPageGsonBean.DatabeanX.Pagebean.Databean databean = ((BPageGsonBean.DatabeanX.Pagebean.Databean) xRecyclerView.getAdapter().getData().get(i));
//
//
//                        Log.i(TAG, "修改库存:  pos = " + i + "  chang num = " + databean.inputNum + "  修改的id = " + databean.id + "  是否选中 --> " + databean.isChecked);
//
//
//                    }
//                }
//
//            }
//            isOpenCheck = !isOpenCheck;
//            isOpenEdit = !isOpenEdit;
//
//            xRecyclerView.getAdapter().notifyDataSetChanged();
//
//
//        } else {
//            isOpenCheck = !isOpenCheck;
//            isOpenEdit = !isOpenEdit;
//            xRecyclerView.getAdapter().notifyDataSetChanged();
//
//        }


        return v -> {
//            ToastUtil.showLongToast("修改库存");
            CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
                @Override
                public Dialog getDialog(Context context) {
                    Dialog dialog1 = new Dialog(context, R.style.DialogTheme);
                    dialog1.setContentView(R.layout.count_edit);
                    TextView textView = dialog1.findViewById(R.id.tv_title);
                    EditText descript = (EditText) dialog1.findViewById(R.id.descript);
                    textView.setText(item.standardName);

                    descript.setText(item.count);

                    dialog1.findViewById(R.id.close_title).setOnClickListener(v -> {
                        dialog1.dismiss();
                    });


                    EditText editText = (EditText) dialog1.findViewById(R.id.descript);
                    ;
                    dialog1.findViewById(R.id.commit).setOnClickListener(v -> {
                        dialog1.dismiss();

//                        ToastUtil.showLongToast(editText.getText().toString());
                        new BasePresenter()
                                .putParams("id", item.id)
                                .putParams("count", editText.getText().toString())
                                .doRequest("admin/seedling/saveCount", new HandlerAjaxCallBack() {
                                    @Override
                                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                                        if (gsonBean.isSucceed()) {
                                            ToastUtil.showLongToast(gsonBean.msg);
                                            xRecyclerView.onRefresh();
                                        }
                                    }
                                });


                    });


//                    dialog1.findViewById(R.id.ll_feed_content).setBackgroundColor(Color.WHITE);
//                    dialog1.findViewById(R.id.tv_feed_ok).setOnClickListener(view1 -> onBackPressed());
                    return dialog1;
                }
            }, true).show(getSupportFragmentManager(), "修改库存");


        };


    }

    public View.OnClickListener 编辑(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item, NeedSwipeBackActivity mActivity) {

        return v -> {
//            ToastUtil.showLongToast("编辑");
            showLoading();
            new BasePresenter()
                    .putParams("id", item.id)
                    .doRequest("admin/seedling/detail", new AjaxCallBack<String>() {
                        @Override
                        public void onSuccess(String t) {
                            SaveSeedingGsonBean saveSeedingGsonBean = GsonUtil.formateJson2Bean(t, SaveSeedingGsonBean.class);
                            if (saveSeedingGsonBean.getCode().equals(ConstantState.SUCCEED_CODE)) {
                                if (!"".equals(item.id)) {
                                    SaveSeedlingActivity_change_data.start2Activity(mActivity, saveSeedingGsonBean, 1, item.id);
                                }
                            } else {
                                ToastUtil.showLongToast(saveSeedingGsonBean.getMsg());
                            }

                            hindLoading();

                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {
                            super.onFailure(t, errorNo, strMsg);
                            hindLoading(strMsg, 3000);
                        }
                    });
        };


    }

    private View.OnClickListener 上架(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item, NeedSwipeBackActivity mActivity) {

        return 编辑(helper, item, mActivity);


    }


    private void resetStateList() {
        if (muzt != null) {
            muzt.setText("全部");
        }
        currentPos = 0;
        commonListSpinner1 = null;

    }

    private void 苗木状态弹框(View archor) {
//        ToastUtil.showLongToast("弹框");

        List<SeedlingStatus> datas = new ArrayList<>();

        // 左右切换   清空  commonListSpinner1
        if (getType().equals("pending")) {
            // 右边
            datas.add(SeedlingStatus.nullstatu);

            datas.add((SeedlingStatus.unsubmit));//未提交
            datas.add((SeedlingStatus.outline));//已下架
            datas.add((SeedlingStatus.backed));//已退回


        } else {
            //左边
            datas.add((SeedlingStatus.nullstatu));
            datas.add((SeedlingStatus.unaudit));//审核中
            datas.add((SeedlingStatus.published));//已上架
        }


        //                        View view = LayoutInflater.from(mActivity).inflate(R.layout.list_item_sort, null);
//                        TextView tv_item = view.findViewById(R.id.tv_item);
//                        tv_item.setText("213465");
//                        tv_item.setTextColor(Color.BLACK);

        if (commonListSpinner1 == null)
            commonListSpinner1 = new CommonListSpinner1.Builder<SeedlingStatus>()
                    .setDatasList(datas)
                    .setMApplyParentView((View) archor.getParent())
                    .setContentView(new CommonListSpinner1.CallContentView<SeedlingStatus>() {
                        @Override
                        public void convert(int position, SeedlingStatus status, CommonListSpinner1.ViewHolder viewHolder, String codeStrings) {
//                        View view = LayoutInflater.from(mActivity).inflate(R.layout.list_item_sort, null);
//                        TextView tv_item = view.findViewById(R.id.tv_item);
//                        tv_item.setText("213465");
//                        tv_item.setTextColor(Color.BLACK);
                            TextView tv_item = viewHolder.getView(R.id.tv_item);
                            if (position == currentPos) {
                                tv_item.setText(status.enumText);
                                tv_item.setTextColor(getColorByRes(R.color.main_color));
                            } else {
                                tv_item.setText(status.enumText);
                                tv_item.setTextColor(getColorByRes(R.color.text_color666));
                            }
                            View view = viewHolder.getView(R.id.is_check);
                            view.setVisibility(position == currentPos ? View.VISIBLE : View.GONE);

                            tv_item.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    currentPos = position;
                                    commonListSpinner1.dismiss();
                                    muzt.setText(status.enumText);
//                                    ToastUtil.showLongToast(status.enumValue);
                                    setStatus(status.enumValue);

                                    xRecyclerView.onRefresh();
//                                    ToastUtil.showLongToast(datas.get(currentPos));
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
        setStatus("");


        if (index == 2) {   // 已上架
            tvs[2].setTextColor(ContextCompat.getColor(mActivity, R.color.main_color));

            ivs[2].setVisibility(View.VISIBLE);

            tvs[5].setTextColor(ContextCompat.getColor(mActivity, R.color.text_color333));
            ivs[5].setVisibility(View.INVISIBLE);

            setType("other");

            getView(R.id.btn_manager_storege).setVisibility(View.VISIBLE);


        } else if (index == 5) // 带操作
        {
            tvs[2].setTextColor(ContextCompat.getColor(mActivity, R.color.text_color333));
            ivs[2].setVisibility(View.INVISIBLE);

            tvs[5].setTextColor(ContextCompat.getColor(mActivity, R.color.main_color));
            ivs[5].setVisibility(View.VISIBLE);

            setType("pending");

            /**
             *
             <Button
             android:id="@+id/btn_manager_storege"
             style="@style/bottom_button_green"
             android:background="@color/simple_blue"
             android:text="草稿箱(50)" />

             <Button
             android:id="@+id/btn_manager_publish"
             style="@style/bottom_button_green"
             android:text="发布苗木" />
             */
            getView(R.id.btn_manager_storege).setVisibility(View.GONE);

        }
        tvs[5].setText("待处理");


        resetStateList();


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
        button.setText("更新");
        button.setOnClickListener(updata);
    }


    View.OnClickListener updata = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            ToastUtil.showLongToast("更新");

//            Type type = new TypeToken<AddressBean>().getType();

            Type beanType = new TypeToken<SimpleGsonBeanData<AddressBean>>() {
            }.getType();

            new BasePresenter()
                    .putParams("nurseryId", getExtraNurseryId())
                    .doRequest("admin/seedling/updateDate", new HandlerAjaxCallBack(mActivity) {
                        @Override
                        public void onRealSuccess(SimpleGsonBean gsonBean) {

//                            ToastUtil.showLongToast(gsonBean.getData().nursery.toString());
                            Button button = getView(R.id.btn_manager_storege);
                            button.setText("更新");
                            button.append("  " + gsonBean.getData().nursery.rePublishDate);


                        }

//                        @Override
//                        public void onRealSuccess(AddressBean result) {
//                            ToastUtil.showLongToast("更新成功...时间");
//                        }
                    });
        }
    };


    public void requestCounts() {
        mPresenter.getCounts2(getExtraNurseryId());
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (arg1 == ConstantState.PUBLIC_SUCCEED) {
//            xRecyclerView.onRefresh();
        }
    }

    @Override
    public void initTodoStatusCount(SimpleGsonBean gsonBean) {




    }


    //    @Override
//    public void initTodoStatusCount(SimpleGsonBeanData<StatusCountBean> gsonBean) {
//        // 子类实现
//    }
}

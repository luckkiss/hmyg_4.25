package com.hldj.hmyg.me.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.BActivity_new_test;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.CallBack.IEditable;
import com.hldj.hmyg.CallBack.IFootMarkEmpty;
import com.hldj.hmyg.CallBack.impl.DeleteAbleImpl;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseRecycleViewFragment;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.decoration.SectionDecoration;
import com.hldj.hmyg.me.HistoryActivity;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.bean.enums.FootMarkSourceType;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.D;
import com.mabeijianxi.smallvideorecord2.Log;

import net.tsz.afinal.FinalBitmap;

import java.lang.reflect.Type;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 商城资源 历史记录
 */

public class SeedlingHistoryFragment extends BaseRecycleViewFragment<BPageGsonBean.DatabeanX.Pagebean.Databean> implements IFootMarkEmpty {

    @Override
    protected void onFragmentVisibleChange(boolean b) {
        super.onFragmentVisibleChange(b);
        if (b) {
            ((HistoryActivity) mActivity).toggleBottomParent(isEditAble);
        }
    }

    @Override
    protected void doRefreshRecycle(String page) {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<BPageGsonBean.DatabeanX.Pagebean.Databean>>>>() {
        }.getType();

        new BasePresenter()
                .putParams(ConstantParams.pageIndex, page)
                .doRequest("admin/footmark/seedling/list", new HandlerAjaxCallBackPage<BPageGsonBean.DatabeanX.Pagebean.Databean>(mActivity, type) {
                    @Override
                    public void onRealSuccess(List<BPageGsonBean.DatabeanX.Pagebean.Databean> e) {


//                        ToastUtil.showLongToast("sort");
//                        System.out.println(e.toString());
//
//                        prictList(e);
//
//
//                        Collections.sort(e, new Comparator<BPageGsonBean.DatabeanX.Pagebean.Databean>() {
//
//                            @Override
//                            public int compare(BPageGsonBean.DatabeanX.Pagebean.Databean o1, BPageGsonBean.DatabeanX.Pagebean.Databean o2) {
//                                if (o1.attrData.dateStr.compareTo(o2.attrData.dateStr) == 0) {
//                                    return 0;
//                                } else if (o1.attrData.dateStr.compareTo(o2.attrData.dateStr) > 0) {
//                                    return -1;
//                                } else {
//                                    return 1;
//                                }
//                            }
//
//                        });
//
//                        prictList(e);
                        mCoreRecyclerView.getAdapter().addData(e);


                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mCoreRecyclerView.selfRefresh(false);
                    }
                });

    }

    @Override
    protected void onRecycleViewInited(CoreRecyclerView corecyclerView) {

        corecyclerView.getRecyclerView().addItemDecoration(
                SectionDecoration.Builder.init(new SectionDecoration.PowerGroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        try {
                            // 19   suze = 20-1
                            if (position > mCoreRecyclerView.getAdapter().getData().size() - 1) {
                                return null;
                            }

                            if (mCoreRecyclerView.getAdapter().getData().size() == 0) {
                                return null;
                            } else {
                                //                            dateStr
                                BPageGsonBean.DatabeanX.Pagebean.Databean databean = (BPageGsonBean.DatabeanX.Pagebean.Databean) mCoreRecyclerView.getAdapter().getItem(position);
                                return databean.attrData.dateStr;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }

                    }

                    @Override
                    public View getGroupView(int position) {
//                        View view = LayoutInflater.from(HistoryActivity.this).inflate( R.layout.item_list_simple, null);
//                        view.setBackgroundColor(getColorByRes(R.color.gray_bg_ed));
//                        TextView tv = view.findViewById(android.R.id.text1);
//                        tv.setText(recycler.getAdapter().getItem(position) + "");
                        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_tag, null);
                        TextView textView = view.findViewById(R.id.text1);
                        textView.setHeight((int) getResources().getDimension(R.dimen.px74));
                        BPageGsonBean.DatabeanX.Pagebean.Databean databean = (BPageGsonBean.DatabeanX.Pagebean.Databean) mCoreRecyclerView.getAdapter().getItem(position);
                        textView.setText(databean.attrData.dateStr);
                        return view;
                    }
                }).setGroupHeight((int) getResources().getDimension(R.dimen.px74)).build());


    }

    @Override
//    protected void doConvert(BaseViewHolder helper, SaveSeedingGsonBean.DataBean.SeedlingBean item, NeedSwipeBackActivity mActivity) {
    protected void doConvert(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item, NeedSwipeBackActivity mActivity) {


        helper.setChecked(R.id.checkBox, item.isChecked())
                .setVisible(R.id.checkBox, isEditAble)
//                .setVisible(R.id.checkBoxParent, isEditAble)
//                .addOnClickListener(R.id.checkBoxParent, v -> {
//                    item.toggle();
//                    helper.setChecked(R.id.checkBox, item.isChecked());
//                })
                .addOnClickListener(R.id.checkBox, v -> {
                    item.toggle();
                });


//        D.i("=============doConvert==============" + item.getName());
        BActivity_new_test.initListType(helper, item, FinalBitmap.create(mActivity), "BActivity_new");

        // helper.getView(R.id.iv_right_top);
//        helper.setVisible(R.id.iv_right_top, false);


//        helper.getConvertView().setOnClickListener(v -> {
//            if (isEditAble) {
//                item.toggle();
//                helper.setChecked(R.id.checkBox, item.isChecked());
//            } else {
//                FlowerDetailActivity.start2Activity(mActivity, "seedling_list", item.id);
//            }

//        });

        if (!item.status.equals("published")) {
            helper.setVisible(R.id.fr_goods_time_out, true);
            View ll_info_content = helper.getView(R.id.ll_info_content);
            ll_info_content.setAlpha(0.6f);
            ll_info_content.setOnClickListener(null);
        } else {
            helper.setVisible(R.id.fr_goods_time_out, false);
            View ll_info_content = helper.getView(R.id.ll_info_content);
            ll_info_content.setAlpha(1.0f);

//            view.findViewById(R.id.fr_goods_time_out).setVisibility(View.GONE);
//            view.findViewById(R.id.ll_info_content).setAlpha(1.0f);
            ll_info_content.setOnClickListener(v -> {
                //点击布局
                D.e("==点击布局==");
//                FlowerDetailActivity.start2Activity(mActivity, "show_type", item.id);

                if (isEditAble) {
                    item.toggle();
                    helper.setChecked(R.id.checkBox, item.isChecked());
                } else {
                    FlowerDetailActivity.start2Activity(mActivity, "seedling_list", item.id);
                }


            });


        }


//        helper
//                .setVisible(R.id.tv_right_top, false)
//                .addOnClickListener(R.id.tv_right_top, v -> {
//                    doUserDelDelete(helper, item, mActivity);
//                })
//        ;


        SwipeLayout swipeLayout = helper.getView(R.id.sl_content);
        swipeLayout.setSwipeEnabled(false);

    }


    @Override
    public int bindRecycleItemId() {
        return R.layout.list_view_seedling_new_shoucan;
    }

    @Override
    public void doEmpty() {
//        ToastUtil.showLongToast("清空  苗木资源");
           /* 判断是否为空 */
        if (null2Tip(this.getResourceId(), "请选择删除项")) return;
        doUserDelDelete(null, this, mActivity);
    }

    private boolean isEditAble = false;


    /* core recycle view 代理 类 */
    private IEditable iEditable;

    public IEditable getiEditable() {
        if (iEditable == null) {
            iEditable = new DeleteAbleImpl(mCoreRecyclerView);
        }
        return iEditable;
    }

    @Override
    public void doEdit() {
        //编辑
        this.isEditAble = !isEditAble;
        mCoreRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void toggleSelect() {
        getiEditable().toggleSelectAll();

    }

    @Override
    public String getEmptyTip() {
        return "确定删除所选？";
    }


    @Override
    public String getResourceId() {
        return getiEditable().getDeleteIds();
    }

    @Override
    public String getDomain() {
        return "admin/footmark/userDel";
    }

    @Override
    public FootMarkSourceType sourceType() {
        return FootMarkSourceType.seedling;
    }


    public void prictList(List<BPageGsonBean.DatabeanX.Pagebean.Databean> list) {


        for (int i = 0; i < list.size(); i++) {
//            System.out.printf("" + list.get(i).attrData.dateStr);
            Log.i("------------", list.get(i).attrData.dateStr);
            D.i("------------------------" + list.get(i).attrData.dateStr);
        }

        D.i("--------------分割线----------");


    }
}

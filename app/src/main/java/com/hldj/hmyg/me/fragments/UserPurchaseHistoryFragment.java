package com.hldj.hmyg.me.fragments;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.CallBack.IEditable;
import com.hldj.hmyg.CallBack.IFootMarkEmpty;
import com.hldj.hmyg.CallBack.impl.DeleteAbleImpl;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseRecycleViewFragment;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.decoration.SectionDecoration;
import com.hldj.hmyg.me.HistoryActivity;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.bean.UserPurchase;
import com.hldj.hmyg.saler.bean.enums.FootMarkSourceType;
import com.hldj.hmyg.saler.purchase.userbuy.BuyForUserActivity;
import com.hldj.hmyg.saler.purchase.userbuy.PublishForUserDetailActivity;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.D;

import java.lang.reflect.Type;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 用户求购足迹列表
 */

public class UserPurchaseHistoryFragment extends BaseRecycleViewFragment<UserPurchase> implements IFootMarkEmpty {

    @Override
    protected void onFragmentVisibleChange(boolean b) {
        super.onFragmentVisibleChange(b);
        if (b) {
            ((HistoryActivity) mActivity).toggleBottomParent(isEditAble);
        }
    }

    @Override
    protected void doRefreshRecycle(String page) {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<UserPurchase>>>>() {
        }.getType();

        new BasePresenter()

                .putParams(ConstantParams.pageIndex, page)
                .doRequest("admin/footmark/userPurchase/list", new HandlerAjaxCallBackPage<UserPurchase>(mActivity, type) {
                    @Override
                    public void onRealSuccess(List<UserPurchase> e) {

//                        Collections.sort(e, new Comparator<UserPurchase>() {
//
//                            @Override
//                            public int compare(UserPurchase o1, UserPurchase o2) {
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

                        mCoreRecyclerView.getAdapter().addData(e);
//                        UserPurchase userPurchase = e.get(0);
//                        userPurchase.attrData.dateStr = "2018-05-09";

//                        mCoreRecyclerView.getAdapter().addData(userPurchase);
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

//                        if (mCoreRecyclerView.getAdapter().getData().size() == 0) {
//                            return null;
//                        } else {
//
//                            UserPurchase databean = (UserPurchase) mCoreRecyclerView.getAdapter().getItem(position);
//                            return databean.attrData.dateStr;
//                        }

                        try {
                            // 19   suze = 20-1
                            if (position > mCoreRecyclerView.getAdapter().getData().size() - 1) {
                                return null;
                            }

                            if (mCoreRecyclerView.getAdapter().getData().size() == 0) {
                                return null;
                            } else {

                                UserPurchase databean = (UserPurchase) mCoreRecyclerView.getAdapter().getItem(position);
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
                        textView.setText(((UserPurchase) mCoreRecyclerView.getAdapter().getItem(position)).attrData.dateStr);
                        return view;
                    }
                }).setGroupHeight((int) getResources().getDimension(R.dimen.px74)).build());


    }

    @Override
    protected void doConvert(BaseViewHolder helper, UserPurchase item, NeedSwipeBackActivity mActivity) {

        D.i("=============doConvert==============" + item.name);
        BuyForUserActivity.doConvert(helper, item, mActivity);

//        if (!isEditAble)
//        {
//            item.setChecked(false);
//        }

        helper.setChecked(R.id.checkBox, item.isChecked())
                .setVisible(R.id.checkBox, isEditAble)
                .addOnClickListener(R.id.checkBox, v -> {
                    item.toggle();
                });


        helper.convertView.setOnClickListener(v -> {
            if (isEditAble) {
                item.toggle();
                helper.setChecked(R.id.checkBox, item.isChecked());
            } else {
                PublishForUserDetailActivity.start2Activity(mActivity, item.id, item.ownerId);
            }
        });

        helper
                .setText(R.id.qubaojia, "删除")
                .setVisible(R.id.qubaojia,false)
                .setBackgroundRes(R.id.qubaojia, R.drawable.round_rectangle_bg_red_gray)
                .addOnClickListener(R.id.qubaojia, v -> {
                    doUserDelDelete(helper, item, mActivity);
                })
        ;


        TextView textView = helper.getView(R.id.qubaojia);
        textView.setWidth(MyApplication.dp2px(mActivity, 64));
        textView.setPadding(
                MyApplication.dp2px(mActivity, 5),
                MyApplication.dp2px(mActivity, 5),
                MyApplication.dp2px(mActivity, 5),
                MyApplication.dp2px(mActivity, 5)
        );
        textView.setGravity(Gravity.CENTER);


    }

    @Override
    public int bindRecycleItemId() {
        return R.layout.item_buy_for_user;
    }

    @Override
    public void doEmpty() {
//        ToastUtil.showLongToast("清空  " + FootMarkSourceType.userPurchase.getEnumText());
           /* 判断是否为空 */
        if (null2Tip(this.getResourceId(), "请选择删除项")) return;
        doUserDelDelete(null, this, mActivity);

    }


    private boolean isEditAble = false;

    @Override
    public void doEdit() {
        //编辑
        this.isEditAble = !isEditAble;
        if (!isEditAble) {
            List<Checkable> checkables = mCoreRecyclerView.getAdapter().getData();
            for (Checkable checkable : checkables) {
                checkable.setChecked(false);
            }
        }

        mCoreRecyclerView.getAdapter().notifyDataSetChanged();
    }


    /* core recycle view 代理 类 */
    private IEditable iEditable;

    public IEditable getiEditable() {
        if (iEditable == null) {
            iEditable = new DeleteAbleImpl(mCoreRecyclerView);
        }
        return iEditable;
    }

    @Override
    public void toggleSelect() {


        if (getiEditable().isSelectAll()) {
            // dis select all  取消全选
            getiEditable().toggleSelectAll();
        } else {
            // select all  全选
            getiEditable().toggleSelectAll();
        }

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
        return FootMarkSourceType.userPurchase;
    }


//    @Override
//    public FootMarkSourceType getType() {
//        return FootMarkSourceType.userPurchase;
//    }
//
//    @Override
//    public String getFootMarkId() {
//        return getiEditable().getDeleteIds();
//    }
}

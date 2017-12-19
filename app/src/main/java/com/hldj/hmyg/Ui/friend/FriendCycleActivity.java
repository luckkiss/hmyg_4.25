package com.hldj.hmyg.Ui.friend;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Keep;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.flyco.dialog.widget.MaterialDialog;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.LoginActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.bean.MomentsReply;
import com.hldj.hmyg.Ui.friend.bean.enums.MomentsType;
import com.hldj.hmyg.Ui.friend.child.FriendBaseFragment;
import com.hldj.hmyg.Ui.friend.child.PublishActivity;
import com.hldj.hmyg.Ui.friend.child.SearchActivity;
import com.hldj.hmyg.Ui.friend.presenter.FriendPresenter;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.base.MyFinalActivity;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.weidet.DialogFragment.CommonDialogFragment1;
import com.hldj.hmyg.saler.Adapter.FragmentPagerAdapter_TabLayout;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hy.utils.ToastUtil;
import com.zzy.common.widget.MeasureListView;

import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static com.hldj.hmyg.util.ConstantState.REFRESH;

/**
 * FinalActivity 来进行    数据绑定
 */
@Keep
public class FriendCycleActivity extends BaseMVPActivity implements View.OnClickListener {

    private static final String TAG = "FriendCycleActivity";

    /*列表 recycle*/
    @ViewInject(id = R.id.viewpager)
    public ViewPager viewpager;

    /*右上角  点击按钮。发布采购 发布供应*/
    @ViewInject(id = R.id.toolbar_right_icon, click = "onClick")
    public ImageView toolbar_right_icon;

    /*左边返回键*/
    @ViewInject(id = R.id.toolbar_left_icon, click = "onClick")
    public ImageView toolbar_left_icon;

    /*中间2个按钮*/
    @ViewInject(id = R.id.rb_title_left, click = "onClick")
    public RadioButton rb_title_left;
    @ViewInject(id = R.id.rb_title_center)
    public RadioButton rb_title_center;
    @ViewInject(id = R.id.rb_title_right, click = "onClick")
    public RadioButton rb_title_right;


    public ArrayList<String> list_title = new ArrayList<String>() {{
        add("供应");
        add("求购");
    }};

    public ArrayList<Fragment> list_fragment = new ArrayList<Fragment>() {{
        add(FriendBaseFragment.newInstance(MomentsType.supply.getEnumValue()));
        add(FriendBaseFragment.newInstance(MomentsType.purchase.getEnumValue()));
    }};

    public void initChild() {

    }


    public void initFiled(ArrayList<String> list_title, ArrayList<Fragment> list_fragment) {
    }

    @Override
    public void initView() {
        MyFinalActivity.initInjectedView(this);
        initFiled(list_title, list_fragment);//子类继承，用于修改数据
        rb_title_left.setText(list_title.get(0));
        rb_title_right.setText(list_title.get(1));
        rb_title_center.setVisibility(View.GONE);
        toolbar_right_icon.setVisibility(View.VISIBLE);
        toolbar_right_icon.setImageResource(R.mipmap.friend_publish_edit);
        toolbar_left_icon.setImageResource(R.mipmap.friend_search);

        initChild();


        FragmentPagerAdapter_TabLayout mFragmentPagerAdapter_tabLayout = new FragmentPagerAdapter_TabLayout(getSupportFragmentManager(), list_title, list_fragment);


        viewpager.setAdapter(mFragmentPagerAdapter_tabLayout);

        initViewPager(viewpager, (RadioGroup) getView(R.id.radio_group_title));
        mFragmentPagerAdapter_tabLayout.notifyDataSetChanged();
    }

    public void initViewPager(ViewPager viewpager, RadioGroup view) {
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    rb_title_left.setChecked(true);
                } else {
                    rb_title_right.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setOffscreenPageLimit(2);
        view.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_title_left) {
                viewpager.setCurrentItem(0);
            } else {
                viewpager.setCurrentItem(1);
            }
        });
    }

    @Keep
    @Override
    public void onClick(View v) {

//        ToastUtil.showLongToast(v.getId() + "");
        switch (v.getId()) {
            /*搜索*/
            case R.id.toolbar_left_icon:
                SearchActivity.start(mActivity, searchContent);
                break;

            case R.id.rb_title_left:
//                ToastUtil.showLongToast("刷新供应");
                /*当前的展示类型*/
                currentType = MomentsType.supply.getEnumValue();
                FriendBaseFragment fragment = (FriendBaseFragment) list_fragment.get(0);
                Log.i(TAG, "onClick: rb_title_right" + rb_title_right.isChecked());
                Log.i(TAG, "onClick: rb_title_left" + viewpager.getCurrentItem());
                Log.i(TAG, "onClick: fragment is visiable" + fragment.mIsVisible);
                viewpager.setCurrentItem(0);

                Log.w(TAG, "---------------------------------");
                break;
            case R.id.rb_title_right:
                /*当前的展示类型*/
//                ToastUtil.showLongToast("刷新采购");
                Log.i(TAG, "onClick: rb_title_right" + rb_title_right.isChecked());
                currentType = MomentsType.purchase.getEnumValue();
                Log.i(TAG, "onClick: rb_title_left" + viewpager.getCurrentItem());

                FriendBaseFragment fragment1 = (FriendBaseFragment) list_fragment.get(1);
                Log.i(TAG, "onClick: fragment is visiable" + fragment1.mIsVisible);
                viewpager.setCurrentItem(1);
                Log.w(TAG, "---------------------------------");
                break;


            /*选择发布类型*/
            case R.id.toolbar_right_icon:
                if (!commitLogin()) return;
                CommonDialogFragment1.newInstance(context -> {
                    Dialog dialog1 = new Dialog(context);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog1.setContentView(R.layout.item_friend_type);
                    dialog1.findViewById(R.id.iv_left).setOnClickListener(v1 -> {
//                            ToastUtil.showLongToast("left");
                        dialog1.dismiss();
                        PublishActivity.start(mActivity, PublishActivity.PUBLISH);

                    });
                    dialog1.findViewById(R.id.iv_right).setOnClickListener(v1 -> {
//                            ToastUtil.showLongToast("right");
                        dialog1.dismiss();
                        PublishActivity.start(mActivity, PublishActivity.PURCHASE);
                    });

                    return dialog1;
                }, true).show(getSupportFragmentManager(), TAG);
                break;
        }

    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_friend_cycle;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        D.i("requestCode=" + requestCode + "  resultCode=" + resultCode);

        if (resultCode == REFRESH) {

        } else if (resultCode == ConstantState.PUBLISH_SUCCEED) {
//        } else if (resultCode == ConstantState.PUBLIC_SUCCEED) {
            //发布成功，当发布的时候刷新
            if (currentType.equals(MomentsType.supply.getEnumValue()))
                list_fragment.get(0).onActivityResult(requestCode, resultCode, data);
            else //切换到个别列表
            {
                rb_title_left.setChecked(true);
                currentType = MomentsType.supply.getEnumValue();
                list_fragment.get(0).onActivityResult(requestCode, resultCode, data);
            }

            D.e("currentType" + currentType);

        } else if (resultCode == ConstantState.PURCHASE_SUCCEED) {
            //求购成功，当求购的时候刷新
            if (currentType.equals(MomentsType.purchase.getEnumValue())) {
                list_fragment.get(1).onActivityResult(requestCode, resultCode, data);
                FriendBaseFragment fragment = ((FriendBaseFragment) list_fragment.get(1));
                fragment.onRefresh();
            } else {
                {
                    rb_title_right.setChecked(true);
                    currentType = MomentsType.purchase.getEnumValue();
                    list_fragment.get(1).onActivityResult(requestCode, resultCode, data);
                    FriendBaseFragment fragment = ((FriendBaseFragment) list_fragment.get(1));
                    fragment.onRefresh();
                }
            }
            D.e("currentType" + currentType);
        }

    }

    public String currentType = MomentsType.supply.getEnumValue();
    public String searchContent = "";


    @Override
    public boolean setSwipeBackEnable() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    /**
     * 判断是否自己 的评论
     *
     * @param myViewHolder
     * @param item
     * @param position
     * @param
     * @return
     */
    public static boolean isSelf(MeasureListView myViewHolder, MomentsReply item, int position, Activity m) {
        boolean isSelf = item.isOwner;
        if (isSelf) {
            final MaterialDialog dialog = new MaterialDialog(
                    m);
            dialog.title("提示").content("确认删除评论?")//
                    .btnText("取消", "确认")//
                    .show();
            dialog.setOnBtnClickL(() -> {
                dialog.dismiss();
//               ToastUtil.showLongToast("确认删除");
            }, () -> {
                dialog.dismiss();
                GlobBaseAdapter globBaseAdapter = (GlobBaseAdapter) myViewHolder.getAdapter();
                doDeleteReply(item.id, item.fromId, position, globBaseAdapter, m);
            });

            Log.i(TAG, "isSelf: \n" + "是自己");
            return true;
        } else {
            Log.i(TAG, "isSelf: \n" + "不是自己");
            return false;
        }

//        myItemClickLister_content.OnItemDel(position, data.get(position).get("id").toString(),);
    }

    private static void doDeleteReply(String id, String fromId, int position, GlobBaseAdapter globBaseAdapter, Activity m) {
        FriendPresenter.doDeleteReply(id, fromId, new HandlerAjaxCallBack((NeedSwipeBackActivity) m) {
            @Override
            public void onRealSuccess(SimpleGsonBean gsonBean) {
                ToastUtil.showLongToast(gsonBean.msg);

                globBaseAdapter.getDatas().remove(position);
                globBaseAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 判断是否需要登录
     *
     * @return
     */
    public boolean commitLogin() {
        if (isLogin()) {
            return true;
        } else {
            LoginActivity.start2Activity(mActivity);
            ToastUtil.showLongToast("请先登录 ^_^ ");
            return false;
        }
    }


//    private void doDelete(Moments moments, int pos) {
//        Log.i(TAG, "doDelete: host\n" + "admin/moments/doDel");
//        FriendPresenter.doDelete(moments.id, new HandlerAjaxCallBack(mActivity) {
//            @Override
//            public void onRealSuccess(SimpleGsonBean gsonBean) {
//                ToastUtil.showLongToast(gsonBean.msg);
//
//            }
//        });
//    }
}

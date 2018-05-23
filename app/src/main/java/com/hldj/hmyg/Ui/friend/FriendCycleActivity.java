package com.hldj.hmyg.Ui.friend;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Keep;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.flyco.dialog.widget.MaterialDialog;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.LoginActivity;
import com.hldj.hmyg.MainActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.bean.Message;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.Ui.friend.bean.MomentsReply;
import com.hldj.hmyg.Ui.friend.bean.enums.MomentsType;
import com.hldj.hmyg.Ui.friend.child.FriendBaseFragment;
import com.hldj.hmyg.Ui.friend.child.PublishActivity;
import com.hldj.hmyg.Ui.friend.child.PushListActivity;
import com.hldj.hmyg.Ui.friend.presenter.FriendPresenter;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.application.StateBarUtil;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.base.MyFinalActivity;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.base.rxbus.annotation.Subscribe;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.saler.Adapter.FragmentPagerAdapter_TabLayout;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hy.utils.ToastUtil;
import com.zzy.common.widget.MeasureListView;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static cn.jpush.android.api.JPushInterface.clearAllNotifications;
import static com.hldj.hmyg.base.rxbus.RxBus.TAG_UPDATE;
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

    @ViewInject(id = R.id.rb_title_center, click = "onClick")
    public RadioButton rb_title_center;

    @ViewInject(id = R.id.rb_title_right, click = "onClick")
    public RadioButton rb_title_right;


    @ViewInject(id = R.id.message, click = "onClick")
    public Button message;

    @ViewInject(id = R.id.fl_search, click = "onClick")
    public View fl_search;

    @ViewInject(id = R.id.et_program_serach_text)
    public EditText et_search;

    @ViewInject(id = R.id.iv_cycle_publish, click = "onClick")
    public ImageView iv_cycle_publish;


    public ArrayList<String> list_title = new ArrayList<String>() {{
        add("供应");
        add("求购");
    }};

    public ArrayList<Fragment> list_fragment = new ArrayList<Fragment>() {{
        add(FriendBaseFragment.newInstance(MomentsType.supply.getEnumValue()));
        add(FriendBaseFragment.newInstance(MomentsType.purchase.getEnumValue()));
    }};

    public void initChild() {

        RxBus.getInstance().register(this);
    }


    //订阅  更新
    @Keep
    @Subscribe(tag = TAG_UPDATE)
    public void postUpdata(Moments momentsNew) {
        onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unRegister(this);
    }

    public void initFiled(ArrayList<String> list_title, ArrayList<Fragment> list_fragment) {
        try {
            list_fragment.add(0, FriendBaseFragment.newInstance(MomentsType.all.getEnumValue()));
            list_title.add(0, "全部");
        } catch (Exception e) {
            Log.i(TAG, "initFiled: ");
            e.printStackTrace();
        }

    }

    @Override
    public void initView() {
        MyFinalActivity.initInjectedView(this);
        initFiled(list_title, list_fragment);//子类继承，用于修改数据
        rb_title_left.setText(list_title.get(0));
        rb_title_center.setText(list_title.get(1));
        rb_title_right.setText(list_title.get(2));
        rb_title_center.setVisibility(View.VISIBLE);
        toolbar_right_icon.setVisibility(View.GONE);
        toolbar_right_icon.setImageResource(R.mipmap.friend_publish_edit);
        toolbar_left_icon.setImageResource(R.mipmap.friend_filter);
        toolbar_left_icon.setVisibility(View.GONE);

        if (et_search != null) {
            et_search.setCursorVisible(false);
            et_search.clearFocus();
            et_search.setHint("请输入内容进行搜索");
        }

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
                } else if (position == 1) {
                    rb_title_center.setChecked(true);
                } else {
                    rb_title_right.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setOffscreenPageLimit(3);
        view.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_title_left) {
                viewpager.setCurrentItem(0);
            } else if (checkedId == R.id.rb_title_center) {
                viewpager.setCurrentItem(1);
            } else {
                viewpager.setCurrentItem(2);
            }
        });
    }

    @Keep
    @Override
    public void onClick(View v) {

//        ToastUtil.showLongToast(v.getId() + "");
        switch (v.getId()) {
            /*搜索*/
//            case R.id.toolbar_left_icon:
            case R.id.iv_cycle_publish:
                /* 发布苗木圈 */

                if (!MyApplication.Userinfo.getBoolean("isLogin", false)) {
                    Intent intent = new Intent(mActivity, LoginActivity.class);
                    startActivityForResult(intent, 4);
                    ToastUtil.showLongToast("请先登录^_^哦");
                    Log.i(TAG, "是否登录");
                    return;
                }
                Log.i(TAG, "是否登录" + MyApplication.Userinfo.getBoolean("isLogin", false));

                PublishActivity.start(mActivity,PublishActivity.ALL);
                break;


            case R.id.fl_search:
//                SearchActivity.start(mActivity, searchContent);
                FriendCycleSearchActivity.start(mActivity, "");
                break;  /*搜索*/

            case R.id.message:
                if (message != null) {
//                  message.setVisibility(View.GONE);
//                  message.setText("没有新消息");
//                  FinalDb.create(mActivity).deleteAll(Message.class);
                    PushListActivity.start(mActivity);
                    /*跳转到 push 推送列表界面*/
                    clearAllNotifications(mActivity);
                }
                ToastUtil.showLongToast(message.getText() + "");
                break;

            case R.id.rb_title_left:
//              ToastUtil.showLongToast("刷新供应");
                /*当前的展示类型*/
                currentType = MomentsType.all.getEnumValue();
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

                FriendBaseFragment fragment1 = (FriendBaseFragment) list_fragment.get(2);
                Log.i(TAG, "onClick: fragment is visiable" + fragment1.mIsVisible);
                viewpager.setCurrentItem(2);
                Log.w(TAG, "---------------------------------");
                break;


            /*选择发布类型*/
            case R.id.toolbar_right_icon:
                if (!commitLogin()) return;
                PublishActivity.start(mActivity, PublishActivity.ALL);
//                CommonDialogFragment1.newInstance(context -> {
//                    Dialog dialog1 = new Dialog(context);
//                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog1.setContentView(R.layout.item_friend_type);
//                    dialog1.findViewById(R.id.iv_left).setOnClickListener(v1 -> {
////                            ToastUtil.showLongToast("left");
//                        dialog1.dismiss();
//                        PublishActivity.start(mActivity, PublishActivity.PUBLISH);
//
//                    });
//                    dialog1.findViewById(R.id.iv_right).setOnClickListener(v1 -> {
////                            ToastUtil.showLongToast("right");
//                        dialog1.dismiss();
//                        PublishActivity.start(mActivity, PublishActivity.PURCHASE);
//                    });
//
//                    return dialog1;
//                }, true).show(getSupportFragmentManager(), TAG);


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
//            if (currentType.equals(MomentsType.supply.getEnumValue()))
//                list_fragment.get(0).onActivityResult(requestCode, resultCode, data);
//            else //切换到个别列表
//            {
//
//
            rb_title_center.setChecked(true);
            currentType = MomentsType.supply.getEnumValue();
            list_fragment.get(0).onActivityResult(requestCode, resultCode, data);
//            ((FriendBaseFragment) list_fragment.get(1)).onrefresh();
//            ((FriendBaseFragment) list_fragment.get(2)).onrefresh();
//            ((FriendBaseFragment) list_fragment.get(3)).onrefresh();
            list_fragment.get(1).onActivityResult(requestCode, resultCode, data);
            list_fragment.get(2).onActivityResult(requestCode, resultCode, data);
            D.e("currentType" + currentType);

        } else if (resultCode == ConstantState.PURCHASE_SUCCEED) {
            //求购成功，当求购的时候刷新
//            if (currentType.equals(MomentsType.purchase.getEnumValue())) {
//                list_fragment.get(1).onActivityResult(requestCode, resultCode, data);
//                FriendBaseFragment fragment = ((FriendBaseFragment) list_fragment.get(1));
//                fragment.onRefresh();
//            } else {
//                {
//
//                    list_fragment.get(1).onActivityResult(requestCode, resultCode, data);
//                    FriendBaseFragment fragment = ((FriendBaseFragment) list_fragment.get(1));
//                    fragment.onRefresh();
//                }
//            }
            rb_title_right.setChecked(true);
            currentType = MomentsType.purchase.getEnumValue();
            list_fragment.get(0).onActivityResult(requestCode, resultCode, data);
            list_fragment.get(1).onActivityResult(requestCode, resultCode, data);
            list_fragment.get(2).onActivityResult(requestCode, resultCode, data);
//            ((FriendBaseFragment) list_fragment.get(1)).onrefresh();
//            ((FriendBaseFragment) list_fragment.get(2)).onrefresh();
//            ((FriendBaseFragment) list_fragment.get(3)).onrefresh();
            D.e("currentType" + currentType);
        }
        else {
//            rb_title_left.setChecked(true);
//            currentType = MomentsType.all.getEnumValue();
//            list_fragment.get(0).onActivityResult(requestCode, resultCode, data);
////            ((FriendBaseFragment) list_fragment.get(1)).onrefresh();
////            ((FriendBaseFragment) list_fragment.get(2)).onrefresh();
////            ((FriendBaseFragment) list_fragment.get(3)).onrefresh();
//            list_fragment.get(1).onActivityResult(requestCode, resultCode, data);
//            list_fragment.get(2).onActivityResult(requestCode, resultCode, data);
//            D.e("currentType" + currentType);
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
        Log.i(TAG, "onResume: 开始");
        setStatusBars();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "执行: 删除代码");
                if (message != null) {
                    FinalDb db = FinalDb.create(mActivity);
                    List<Message> list = db.findAll(Message.class);
                    if (list.size() == 0) {
                        message.setText("没有新消息");
                        message.setVisibility(View.GONE);
                    } else {
                        message.setText(list.size() + "条新消息");
                        message.setVisibility(View.VISIBLE);
                    }
                }
            }
        }, 650);
        Log.i(TAG, "onResume: 结束");
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


    private void setStatusBars() {

        try {
            StateBarUtil.setStatusTranslater(MainActivity.instance, true);
            StateBarUtil.setMiuiStatusBarDarkMode(MainActivity.instance, true);
            StateBarUtil.setMeizuStatusBarDarkIcon(MainActivity.instance, true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}

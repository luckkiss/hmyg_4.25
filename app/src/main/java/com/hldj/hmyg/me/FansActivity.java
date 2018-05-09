package com.hldj.hmyg.me;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.M.UserFollow;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.child.HeadDetailActivity;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hy.utils.SpanUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.lang.reflect.Type;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 我的关注 界面
 */

public class FansActivity extends BaseMVPActivity implements View.OnClickListener {

    int item_layout_id = R.layout.item_invite_friend_list;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_fans;
    }

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycler;

    @ViewInject(id = R.id.tijia, click = "onClick")
    TextView tijia;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tijia:
                AddContactActivity.start(mActivity);
                break;
        }

    }

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);

        //RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity instance


        recycler.init(new BaseQuickAdapter<UserFollow, BaseViewHolder>(item_layout_id) {
            @Override
            protected void convert(BaseViewHolder helper, UserFollow item) {

                doConvert(helper, item, mActivity);
            }
        })

                .openRefresh()
                .openLoadMore(20, page -> {

                    requestDatas(page);
                })
        ;


        recycler
                .onRefresh();


    }


    /* init recycle */
    private void doConvert(BaseViewHolder helper, UserFollow item, NeedSwipeBackActivity mActivity) {

        int item_id = R.layout.item_invite_friend_list;

        ImageLoader.getInstance().displayImage(item.attrData.headImage, (ImageView) helper.getView(R.id.circleImageView));

        helper
                .setText(R.id.title,
                        new SpanUtils()
                                .append(item.attrData.displayName)
//                                .append("   " + item.attrData.cityName).setFontSize(13, true).setForegroundColor(getColorByRes(R.color.text_color999)).setAlign(Layout.Alignment.ALIGN_CENTER)
                                .create())
                .setText(R.id.content, item.attrData.timeStampStr + "  " + item.attrData.cityName)
                .setText(R.id.fensi, item.attrData.isFollowed ? "已关注" : "+关注")
                .setChecked(R.id.fensi, item.attrData.isFollowed)
                .addOnClickListener(R.id.fensi, v -> {
//                    ToastUtil.showLongToast("是否关注" + item.attrData.isFollowed);
//                    if () {
                    取消关注或加关注(item.userId, item, helper.getAdapterPosition(), !item.attrData.isFollowed);


//                    } else {
//                        取消关注或加关注(item.beFollowUserId);
//                    }
                })
//                .setTextColorRes(R.id.fensi, R.color.main_color);
        ;
        helper.getView(R.id.fensi).setMinimumWidth((int) getResources().getDimension(R.dimen.px133));




        helper.convertView.setOnClickListener(v -> {
            HeadDetailActivity.start(mActivity, item.userId);
        });


    }


    public void 取消关注或加关注(String beFollowUserId, UserFollow item, int pos, boolean flag) {

        Log.i("位置---->", item.attrData.displayName + " " + pos);
        String host = "";
        if (flag) {// true
            host = "admin/userFollow/save";
        } else {
            host = "admin/userFollow/del";
        }
        new BasePresenter()
                .putParams("beFollowUserId", beFollowUserId)
                .doRequest(host, new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        item.attrData.isFollowed = !item.attrData.isFollowed ;
                        recycler.getAdapter().notifyItemChanged(pos, item);
                    }
                });


    }

    private void requestDatas(int page) {

        Type beanType = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<UserFollow>>>>() {
        }.getType();


        new BasePresenter()
                .putParams("", "")
                .doRequest("admin/userFollow/beFollowList", new HandlerAjaxCallBackPage<UserFollow>(mActivity, beanType) {
                    @Override
                    public void onRealSuccess(List<UserFollow> userFollows) {
                        recycler.getAdapter().addData(userFollows);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        recycler.selfRefresh(false);
                    }
                });

    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, FansActivity.class));
    }

    @Override
    public String setTitle() {
        return "我的粉丝";
    }

}

package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import com.coorchice.library.SuperTextView;
import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackSimpaleData;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.StoreActivity_new;
import com.hldj.hmyg.Ui.friend.bean.HeadDetail;
import com.hldj.hmyg.Ui.friend.bean.enums.AgentGrade;
import com.hldj.hmyg.Ui.friend.util.CallLogUtil;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBeanData;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Type;
import java.util.ArrayList;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * Created by luocaca on 2017/11/27 0027.
 * <p>
 * <p>
 * 头像点击  进入 详细资料界面
 */

public class HeadDetailActivity extends BaseMVPActivity {

    private static final String TAG = "HeadDetailActivity";


    public int bindLayoutID() {
        return R.layout.activity_friend_head_detail;
    }


    @Override
    public void initView() {


        requestData();


    }

    public String getUserId() {
        Log.i(TAG, " ownerId 个人id: \n" + getIntent().getExtras().getString(TAG));
        return getIntent().getExtras().getString(TAG);
    }

    /**
     * 网络请求，本界面数据
     */
    private void requestData() {
        Log.i(TAG, "结束请求");

        Type type = new TypeToken<SimpleGsonBeanData<HeadDetail>>() {
        }.getType();


        /**
         *    /**
         * phone : 17074990702
         * storeName : 最软0
         * agentScoreRank : {"id":"86581331376844d4a44e828bee1a4622",
         * "createDate":"2017-12-11 08:53:33","sellerId":"2876f7e0f51c4153aadc603b661fedfa",
         * "quoteCount":180,"quoteUsedCount":20,"starsScore":6,
         * "agentGrade":"level1","orderBy":"agentGrade DESC"}
         * cityName : 福建 厦门
         * mainType : 花木墨家
         * momentsCount : 193
         * identity : false
         * level : level1
         * headImage : http://image.hmeg.cn/upload/image/201803/c21c9c443d1c45478c4ce7cba553181e.png
         * userId : 2876f7e0f51c4153aadc603b661fedfa
         * hasMoments : true
         * levelName : 合作供应商
         * isFollowed : false
         * displayName : 大傻么么哒
         * storeId : 789d30acffd74349ab816ece9bb312c6

         */

        new BasePresenter()
                .putParams("id", getUserId())
                .doRequest("user/information", new HandlerAjaxCallBackSimpaleData<HeadDetail>(mActivity, type) {
                    @Override
                    public void onRealSuccess(HeadDetail result) {

//                        ToastUtil.showLongToast("" + result.displayName);

                        processHeadDetail(result);


                    }


                });
    }


    public static void 取消关注或加关注(String beFollowUserId, CheckedTextView attention, boolean flag, NeedSwipeBackActivity mActivity) {
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
                        attention.setChecked(!attention.isChecked());
                        attention.setText(!attention.isChecked() ? "+关注" : "已关注");
                    }
                });


    }

    private void processHeadDetail(HeadDetail result) {

        ImageLoader.getInstance().displayImage(result.headImage, (ImageView) getView(R.id.head));

        getView(R.id.head).setOnClickListener(v -> {
            if (!TextUtils.isEmpty(result.headImage)) {
                ArrayList<Pic> ossUrls = new ArrayList<>();
                ossUrls.add(new Pic("", false, result.headImage, 0));
                GalleryImageActivity.startGalleryImageActivity(mActivity, 0, ossUrls);
            } else {
                showToast("未设置头像");
            }
        });


        /* 名称 */
        setText(getView(R.id.name), result.displayName);


        /* 实名认证  */
        SuperTextView identity = getView(R.id.identity);
        identity.setShowState(result.identity);
        identity.setText(result.identity ? "已实名认证" : "未实名认证");
        if (!result.identity)
            identity.setPadding(0, 0, 0, 0);


        /* 是否关注 */
        CheckedTextView attention = getView(R.id.attention);
        attention.setChecked(result.isFollowed);
        attention.setText(!attention.isChecked() ? "+关注" : "已关注");
        if (MyApplication.getUserBean().id.equals(getUserId())) {
            attention.setVisibility(View.GONE);
        }


        attention.setOnClickListener(v -> {
//            attention.setChecked(!attention.isChecked());


            取消关注或加关注(result.userId, attention, !result.isFollowed, mActivity);

        });


        setStateAndText(result.level, getView(R.id.gys));

        /* 店铺名 */
        setText(getView(R.id.store_name), result.storeName);
         /* 他的店铺  点击事件   有 ** 条动态 >  */
        getView(R.id.store_name).setOnClickListener(v -> {


            StoreActivity_new.start2Activity(mActivity, result.storeId);
//            CenterActivity.start(mActivity, item.ownerId);
        });


        /* 主营品种 */
        setText(getView(R.id.zypz_content), result.mainType);

        /* 苗木圈 显示 几条  */
        setText(getView(R.id.moment_cycle), String.format("有%d条动态", result.momentsCount));
        getView(R.id.moment_cycle).setOnClickListener(v -> {
            CenterActivity.start(mActivity, getUserId());
        });





        /* 所在地 */
        setText(getView(R.id.city_name), result.cityName);


        getView(R.id.button_ddh).setOnClickListener(v -> {
            FlowerDetailActivity.CallPhone(result.phone, mActivity, succeed -> {
                Log.i(TAG, " 记录日志");
                CallLogUtil.log_head_detail();
            });
        });

    }

    private void setStateAndText(String level, SuperTextView superTextView) {

        AgentGrade agentGrade = Enum.valueOf(AgentGrade.class, level);

        superTextView.setText(agentGrade.getEnumText());
        superTextView.setDrawable(getResources().getDrawable(agentGrade.getUpScore()));


    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity activity, String id) {
        Intent intent = new Intent(activity, HeadDetailActivity.class);
        intent.putExtra(TAG, id);
        activity.startActivityForResult(intent, 110);
    }


    @Override
    public String setTitle() {
        return "详细资料";
    }


}

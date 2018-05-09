package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.M.CallLog;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantParams;
import com.hy.utils.SpanUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.lang.reflect.Type;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * Created by luocaca on 2017/11/27 0027.
 * <p>
 * <p>
 * 举报界面
 */

public class PhoneLogActivity extends BaseMVPActivity {

    int item_layout_id = R.layout.item_invite_friend_list;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_be_attention;
    }

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycler;

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);

        recycler.init(new BaseQuickAdapter<CallLog, BaseViewHolder>(item_layout_id) {
            @Override
            protected void convert(BaseViewHolder helper, CallLog item) {


                doConvert(helper, item, mActivity);


            }
        }).openRefresh()
                .openLoadMore(20, this::requestData)
        ;

        recycler.onRefresh();
//        UserFollow userFollow = new UserFollow();
//        userFollow.createDate = "2018-5-06";
//        userFollow.attrData.followCount = "100";
//        userFollow.attrData.mainType = "花木";
//        userFollow.attrData.displayName = "赖皮购花木";
//        userFollow.attrData.headImage = "http://image.hmeg.cn/upload/image/201805/04838e439ed94b23875bfd2d1c3d31e5.jpeg";

//        recycler.getAdapter().addData(userFollow);
//        recycler.getAdapter().addData(userFollow);
//        recycler.getAdapter().addData(userFollow);
//        recycler.getAdapter().addData(userFollow);
//        recycler.getAdapter().addData(userFollow);


    }

    private void requestData(int page) {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<CallLog>>>>() {
        }.getType();

        new BasePresenter()
                .putParams(ConstantParams.pageIndex, page + "")

                .doRequest("admin/callLog/list", new HandlerAjaxCallBackPage<CallLog>(mActivity, type) {
                    @Override
                    public void onRealSuccess(List<CallLog> callLogs) {

                        recycler.getAdapter().addData(callLogs);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        recycler.selfRefresh(false);
                    }
                });


    }


    /* init recycle */
    private void doConvert(BaseViewHolder helper, CallLog item, NeedSwipeBackActivity mActivity) {

        int item_id = R.layout.item_invite_friend_list;

        ImageLoader.getInstance().displayImage(item.attrData.headImage, (ImageView) helper.getView(R.id.circleImageView));

        helper
                .setText(R.id.title,
                        new SpanUtils()
                                .append(item.attrData.displayName)
//                                .append("   " + item.attrData.cityName).setFontSize(13, true).setForegroundColor(getColorByRes(R.color.text_color999)).setAlign(Layout.Alignment.ALIGN_CENTER)
                                .create())
                .setText(R.id.content, item.attrData.timeStampStr + "  " + item.attrData.cityName)
                .setText(R.id.fensi, "来源：" + item.callSourceTypeName)
                .setBackgroundColor(R.id.fensi, Color.TRANSPARENT)
                .setTextColorRes(R.id.fensi, R.color.text_color999)

        ;


        helper.convertView.setOnClickListener(v -> {
            HeadDetailActivity.start(mActivity, item.userId);
        });


    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, PhoneLogActivity.class));
    }

    @Override
    public String setTitle() {
        return "来电记录";
    }

}

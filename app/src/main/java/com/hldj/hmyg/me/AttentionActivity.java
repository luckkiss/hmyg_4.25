package com.hldj.hmyg.me;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.Layout;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.lang.reflect.Type;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 我的关注 界面
 */

public class AttentionActivity extends BaseMVPActivity implements View.OnClickListener {

    int item_layout_id = R.layout.item_invite_friend_list;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_attention;
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
        tijia.setOnClickListener(this);

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

        ImageLoader.getInstance().displayImage(item.attrData.headImage, (ImageView) helper.getView(R.id.circleImageView),getOption());

        helper
                .setText(R.id.title,
                        new SpanUtils()
                                .append(item.attrData.displayName)
                                .append("   " + item.attrData.cityName).setFontSize(13, true).setForegroundColor(getColorByRes(R.color.text_color999)).setAlign(Layout.Alignment.ALIGN_CENTER)
                                .create())
                .setText(R.id.content, "主营品种： " + item.attrData.mainType)
                .setText(R.id.fensi, "粉丝：" + item.attrData.followCount)
                .setBackgroundColor(R.id.fensi, Color.TRANSPARENT)
                .setTextColorRes(R.id.fensi, R.color.text_color999)
        ;

        helper.getView(R.id.fensi).setPadding(0,0,0,0);

        helper.convertView.setOnClickListener(v -> {
            HeadDetailActivity.start(mActivity, item.beFollowUserId);
        });


    }

    private void requestDatas(int page) {

        Type beanType = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<UserFollow>>>>() {
        }.getType();


        new BasePresenter()
                .putParams("", "")
                .doRequest("admin/userFollow/followList", new HandlerAjaxCallBackPage<UserFollow>(mActivity, beanType) {
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
        mActivity.startActivity(new Intent(mActivity, AttentionActivity.class));
    }

    @Override
    public String setTitle() {
        return "我的关注";
    }


    @Override
    protected void onStart() {
        super.onStart();
        doCallLogIsRead();
    }

    public void doCallLogIsRead() {
        new BasePresenter()
                .doRequest("admin/callLog/callLogIsRead", new HandlerAjaxCallBack() {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {

//                        ToastUtil.showShortToast("清除成功");
//                        ToastUtil.showShortToast(gsonBean.msg);
                    }
                });


    }

    public static DisplayImageOptions getOption(){
        DisplayImageOptions option=new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.no_image_show)//设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_persion_pic)//设置图片uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.no_image_to_show)//设置图片加载或解码过程中发生错误显示的图片
                .resetViewBeforeLoading(false)//设置图片在加载前是否重置、复位
//.delayBeforeLoading(1000)//下载前的延迟时间
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在sd卡中
                .considerExifParams(false)//思考可交换的参数
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)//设置图片的显示比例
//                .bitmapConfig(Config.RGB_565)//设置图片的解码类型
                .displayer(new RoundedBitmapDisplayer(40))//设置图片的圆角半径
                .displayer(new FadeInBitmapDisplayer(3000))//设置图片显示的透明度过程的时间
                .build();

        return option;
    }


}

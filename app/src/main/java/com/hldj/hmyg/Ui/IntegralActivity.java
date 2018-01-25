package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;

import com.hldj.hmyg.M.IntegralBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.StateBarUtil;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.widget.IntegralItemRelative;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分详情界面
 */

public class IntegralActivity extends BaseMVPActivity {


    CoreRecyclerView recycle;

    List<IntegralBean> integralBeens;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_integral;
    }


    @Override
    public void initView() {
        StateBarUtil.setStatusTranslater(this, false);
        recycle = getView(R.id.recycle);
//        StateBarUtil.setColorPrimaryDark(getColorByRes(R.color.main_color), this.getWindow());

        recycle.init(new BaseQuickAdapter<IntegralBean, BaseViewHolder>(R.layout.activity_integral_item_layout) {

            @Override
            protected void convert(BaseViewHolder helper, IntegralBean item) {
                IntegralItemRelative relative = helper.getView(R.id.content);
                relative.setBottomText(item.bottomText);
                relative.setTopText(item.topText);
                relative.setLeftIcon(item.leftRes);
                relative.setRightTextStates(item.rightState, "20积分");
                //red_btn_bg_color
            }
        })
                .openRefresh()
                .addRefreshListener(new CoreRecyclerView.RefreshListener() {
                    @Override
                    public void refresh() {
                        recycle.selfRefresh(false);
                    }
                })
        ;
        recycle.getAdapter().addData(getIntegralBeens());
    }

    private List<IntegralBean> getIntegralBeens() {
        if (integralBeens == null) {
            integralBeens = new ArrayList<>();
            integralBeens.add(new IntegralBean(R.mipmap.jf_fbmmq, "发布苗木圈", "每条获得5分，每天上限20分", true));
            integralBeens.add(new IntegralBean(R.mipmap.jf_fbsp, "发布商品", "发布或更新商品5分", false));
            integralBeens.add(new IntegralBean(R.mipmap.jf_cgbj, "采购报价", "每日报价获得5分，不设上限", false));
            integralBeens.add(new IntegralBean(R.mipmap.jf_mrdl, "每日登陆", "每日打开花木易购获得2分，需要登录", true));
            integralBeens.add(new IntegralBean(R.mipmap.jf_nrfx, "内容分享", "分享店铺，商品，苗木圈获得10分", true));
            integralBeens.add(new IntegralBean(R.mipmap.jf_llsp, "浏览商品", "浏览店铺,商品，苗木圈获得10分", true));
        }
        return integralBeens;
    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, IntegralActivity.class));
    }


}

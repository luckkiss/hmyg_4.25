package com.hldj.hmyg.Ui.friend.bean.tipNum;


import android.view.View;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.bean.tipNum.impl.BrowseTipNum;
import com.hldj.hmyg.Ui.friend.bean.tipNum.impl.CallLogTipNum;
import com.hldj.hmyg.Ui.friend.bean.tipNum.impl.CollectTipNum;
import com.hldj.hmyg.Ui.friend.bean.tipNum.impl.DownShelfTipNum;
import com.hldj.hmyg.Ui.friend.bean.tipNum.impl.FansTipNum;
import com.hldj.hmyg.Ui.friend.bean.tipNum.impl.FollowTipNum;
import com.hldj.hmyg.Ui.friend.bean.tipNum.impl.FootMarkTipNum;
import com.hldj.hmyg.Ui.friend.bean.tipNum.impl.MatchPurchaseTipNum;
import com.hldj.hmyg.Ui.friend.bean.tipNum.impl.NewQuoteTipNum;
import com.hldj.hmyg.Ui.friend.bean.tipNum.impl.VisitorTipNum;
import com.hldj.hmyg.application.MyApplication;
import com.mabeijianxi.smallvideorecord2.StringUtils;

import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeLinearLayout;
import cn.bingoogolapple.badgeview.BGABadgeViewHelper;
import cn.bingoogolapple.badgeview.BGABadgeViewUtil;

/**
 * 需要显示红点提示的菜单枚举
 *
 * @author linzj
 */
public enum TipNumType {

    /**
     * 苗圃菜单
     */
    store("store", "苗圃", null, null),

    /**
     * 店铺浏览量
     */
    storeBrowse("storeBrowse", "店铺浏览量", store, new BrowseTipNum()),
    /**
     * 访客菜单
     */
    visitor("visitor", "访客", store, new VisitorTipNum()),
    /**
     * 来电记录菜单
     */
    callLog("callLog", "来电记录", store, new CallLogTipNum()),
    /**
     * 已下架待处理菜单
     */
    downShelf("downShelf", "已下架待处理", store, new DownShelfTipNum()),
    /**
     * 用户求购匹配菜单
     */
    matchPurchase("matchPurchase", "匹配用户求购", store, new MatchPurchaseTipNum()),

    /**
     * 用户个人中心菜单
     */
    personal("personal", "用户个人中心", null, null),

    /**
     * 我的收藏
     */
    collect("collect", "我的收藏", personal, new CollectTipNum()),

    /**
     * 粉丝数
     */
    follow("follow", "我的关注", personal, new FollowTipNum()),

    /**
     * 粉丝数
     */
    fans("fans", "粉丝数", personal, new FansTipNum()),

    /**
     * 我的足迹
     */
    footMark("footMark", "我的足迹", personal, new FootMarkTipNum()),

    /**
     * 我的求购显示未读报价数量
     */
    userPurchase("userPurchase", "我的求购", personal, new NewQuoteTipNum());

    public String value;
    public String text;
    public TipNumType parent;
    public ITipNum tipNumUtils;

    TipNumType(String value, String text, TipNumType parent, ITipNum tipNumUtils) {
        this.value = value;
        this.text = text;
        this.parent = parent;
        this.tipNumUtils = tipNumUtils;
    }

    /**
     * 将字符串转换成枚举，相当于valueOf,增加了异常判断
     *
     * @param typeStr
     * @return
     */
    public static TipNumType str2Enum(String typeStr) {
        try {
            if (StringUtils.isBlank(typeStr)) {
                return null;
            }
            TipNumType type = TipNumType.valueOf(typeStr);
            return type;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据父菜单获取子菜单集合
     *
     * @param
     * @return
     */


    public static void isShowRightTop(List<TipNum> tipNums, SuperTextView... views) {
        for (TipNum tipNum : tipNums) {
            TipNumType tipNumType = TipNumType.str2Enum(tipNum.type.value);
            if (tipNumType == null) {
                continue;
            }

            if (tipNumType.compareTo(TipNumType.collect) == 0) {
                views[0].setShowState(tipNum.showPoint);
                views[0].setText(String.format("%d\n我的收藏", tipNum.count));

            } else if (tipNumType.compareTo(TipNumType.follow) == 0) {
                views[1].setShowState(tipNum.showPoint);
                views[1].setText(String.format("%d\n我的关注", tipNum.count));
            } else if (tipNumType.compareTo(TipNumType.fans) == 0) {
                views[2].setShowState(tipNum.showPoint);
                views[2].setText(String.format("%d\n我的粉丝", tipNum.count));
            } else if (tipNumType.compareTo(TipNumType.footMark) == 0) {
                views[3].setShowState(tipNum.showPoint);
                views[3].setText(String.format("%d\n我的足迹", tipNum.count));
            }


            /**
             *   setText(getView(R.id.tv_wd_sc), String.format("%d\n我的收藏", gsonBean.getData().collectCount));
             setText(getView(R.id.tv_wd_gz), String.format("%d\n我的关注", gsonBean.getData().followCount));
             setText(getView(R.id.tv_wd_fs), String.format("%d\n我的粉丝", gsonBean.getData().beFollowCount));
             setText(getView(R.id.tv_wd_zj), String.format("%d\n我的足迹", gsonBean.getData().footMarkCount));
             */
        }


        //0 tv_wd_sc 收藏
        //1 tv_wd_gz  关注
        //2 tv_wd_fs  粉丝
        //3  tv_wd_zj  足迹


    }


    /**
     * 设置 数量
     *
     * @param
     * @return
     */
    public static void setCount(List<TipNum> tipNums, View headView) {
        for (TipNum tipNum : tipNums) {
//            Enum<TipNumType> tipNumType = TipNumType.valueOf();
            TipNumType tipNumType = TipNumType.str2Enum(tipNum.type.value);
            //setBadgeHorizontalMarginDp


            if (tipNumType == null) {
                continue;
            }

            if (tipNumType.compareTo(TipNumType.storeBrowse) == 0) {
                TextView brower_num = (TextView) headView.findViewById(R.id.brower_num);
                brower_num.setText("浏览量 " + tipNum.count);

            } else if (tipNumType.compareTo(TipNumType.callLog) == 0) {
                setStyleAndText(headView.findViewById(R.id.ll_show_num2), tipNum.count + "");

            } else if (tipNumType.compareTo(TipNumType.callLog) == 0) {
                setStyleAndText(headView.findViewById(R.id.ll_show_num2), tipNum.count + "");

            } else if (tipNumType.compareTo(TipNumType.downShelf) == 0) {
                setStyleAndText(headView.findViewById(R.id.ll_show_num4), tipNum.count + "");
            }
        }

    }





    public static void setTipNum(BGABadgeLinearLayout tip_wd_qg, String str) {
        tip_wd_qg.showTextBadge(str);
        tip_wd_qg.getBadgeViewHelper().setBadgeGravity(BGABadgeViewHelper.BadgeGravity.RightTop);
        tip_wd_qg.getBadgeViewHelper().setBadgeVerticalMarginDp(8);
        tip_wd_qg.getBadgeViewHelper().setBadgeHorizontalMarginDp(16);
    }

    public static void setStyleAndText(BGABadgeLinearLayout ll_show_num, String s) {
        ll_show_num.getBadgeViewHelper().setBadgeGravity(BGABadgeViewHelper.BadgeGravity.RightTop);
        ll_show_num.showTextBadge(s);


        ll_show_num.getBadgeViewHelper().setBadgeHorizontalMarginDp(22);
        ll_show_num.getBadgeViewHelper().setBadgeVerticalMarginDp(12);

//        ll_show_num4.getBadgeViewHelper().setBadgeTextSizeSp( BGABadgeViewUtil.sp2px(mActivity,4 ));
        ll_show_num.getBadgeViewHelper().setBadgeTextSizeSp(BGABadgeViewUtil.sp2px(MyApplication.getInstance(), 4));

//        ll_show_num.hiddenBadge();


//        ll_show_num.getBadgeViewHelper().hiddenBadge();

    }


//    public List<TipNumType> getChilds(){
//    	List<TipNumType> childs = Lists.newArrayList();
//    	for(TipNumType type : TipNumType.values()){
//    		TipNumType typeParent = type.getParent();
//    		if(null == typeParent){
//    			continue;
//    		}
//    		if(this.compareTo(typeParent) == 0){
//    			childs.add(type);
//    		}
//    	}
//    	return childs;
//    }
}
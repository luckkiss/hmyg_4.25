package com.hldj.hmyg.bean;

import com.hldj.hmyg.M.userIdentity.UserIdentity;
import com.hldj.hmyg.M.userIdentity.enums.UserIdentityStatus;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.Ui.friend.bean.MomentsReply;
import com.hldj.hmyg.Ui.friend.bean.UnRead;
import com.hldj.hmyg.buyer.M.ImagesJsonBean;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.PurchaseListPageGsonBean;
import com.hldj.hmyg.buyer.M.SellerQuoteJsonBean;
import com.hldj.hmyg.util.ConstantState;

import java.io.Serializable;
import java.util.List;

/**
 * 用于接收  普通的 成功失败返回  的gson 笨啊、===
 */

public class SimpleGsonBean implements Serializable {

    /**
     * =============json=========={"code":"1","msg":"操作成功"}
     */
    public String code = "";
    public String msg = "失败";
    private DataBean data = new DataBean();

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {

        public int unReadCount;

        public PurchaseListPageGsonBean.DataBeanX.HeadPurchaseBean headPurchase;

        public PurchaseItemBean_new purchaseItem;

        /*临时报价*/
        public SellerQuoteJsonBean quote;


        /*未读消息列表   */
        public List<UnRead> optionList;


        //        "thumbUpCount":2,"isThumUp":true
        public int thumbUpCount;
        public boolean isThumUp;
        public String headImage = "";
        public String displayName = "";
        public String reply = "";
        public MomentsReply.AttrDataBean attrData;


        //        CenterActivity
        /*供应数量*/
        public int supplyCount = 0;
        /*采购数量*/
        public int purchaseCount = 0;
        /*收藏数量*/
        public int collectCount = 0;
        //        CenterActivity
        public int seedlingCount = 0;/*商城资源*/
        public int momentsCount = 0;/* 苗木圈资源数量 */


        public MomentsReply momentsReply;
        public Moments moments;


        private boolean isCollect;

        public boolean isCollect() {
            return isCollect;
        }

        public void setCollect(boolean collect) {
            isCollect = collect;
        }

        public int quoteUsedCount;

        public boolean showSeedlingNote = false;
        public boolean hasProjectManage = false;
        public boolean showSeedlingNoteShare = false;
        public boolean isQuote = false;/*是否需要申请成为供应商*/

        public String userIdentityStatus = UserIdentityStatus.unaudited.enumValue; // 未认证
        public UserIdentity userIdentity ; // 未认证


        public String agentGrade = " - ";
        public String userPoint = " - ";
        public String agentGradeText = " - ";


        //"agentGrade":"level1","userPoint":14,"agentGradeText":
        // "普通供应商",

        public List<SpecTypeBean> specTypeList;


        public SaveSeedingGsonBean.DataBean.SeedlingBean.NurseryJsonBean nursery;


        public ImagesJsonBean image;
//        public String name ="-";
//        public String detailAddress ="-";
//        public String contactName ="-";
//        public String contactPhone ="-";
//        public boolean isDefault ;
//        public String id ="";


    }


    public boolean isSucceed() {
        return code.equals(ConstantState.SUCCEED_CODE);
    }





}

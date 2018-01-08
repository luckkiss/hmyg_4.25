package com.hldj.hmyg.bean;

import com.hldj.hmyg.Ui.friend.bean.MomentsReply;
import com.hldj.hmyg.Ui.friend.bean.UnRead;
import com.hldj.hmyg.buyer.M.PurchaseListPageGsonBean;
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

        public PurchaseListPageGsonBean.DataBeanX.HeadPurchaseBean headPurchase ;


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


        public MomentsReply momentsReply;

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


        public List<SpecTypeBean> specTypeList;


        public SaveSeedingGsonBean.DataBean.SeedlingBean.NurseryJsonBean nursery;


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

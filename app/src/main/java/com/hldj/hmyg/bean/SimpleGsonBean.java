package com.hldj.hmyg.bean;

import com.hldj.hmyg.M.userIdentity.CompanyIdentity;
import com.hldj.hmyg.M.userIdentity.UserIdentity;
import com.hldj.hmyg.M.userIdentity.enums.UserIdentityStatus;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.Ui.friend.bean.MomentsReply;
import com.hldj.hmyg.Ui.friend.bean.UnRead;
import com.hldj.hmyg.Ui.friend.bean.tipNum.TipNum;
import com.hldj.hmyg.buyer.M.ImagesJsonBean;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.M.PurchaseListPageGsonBean;
import com.hldj.hmyg.buyer.M.SellerQuoteJsonBean;
import com.hldj.hmyg.saler.bean.UserPurchase;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.ContactInfoParser;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

        /* 待处理列表 start  */
        public int backedCount;
        /* 审核中 数量 */
        public int unauditCount ;

        public String storeId;
        public int outlineCount;


        public int quoteUsedCountJson;
        public int quotePreUsedCountJson;

        public String name;

        public String shareUrl;
        public String shareDetail;
        public String shareTitle;


        /* 匿名访客用 是否显示小 红点 */
        public boolean hasNewGuest;

        /* 待处理列表 end */
        public CompanyIdentity companyIdentity;// 企业认证实体
//        public ImagesJsonBean licenceImageJson;// 企业认证图片
//        public ImagesJsonBean legalPersonImageJson;// 法人照片


        /* 企业认证 临时属性 */


        /* 联系人 匹配列表 */
        public List<ContactInfoParser.ContactInfo> userList;
        /* 联系人 匹配列表 */


        public Map<String, String> statusCount;



        /* 企业认证 临时属性 */

        /* 苗圃  临时属性 */

        public StoreGsonBean.DataBean.StoreBean store;
        public StoreGsonBean.DataBean.OwnerBean owner;
        public List<TipNum> tipList;

        /* 苗圃  临时属性 */


        public PurchaseListPageGsonBean.DataBeanX.HeadPurchaseBean headPurchase;

        public PurchaseItemBean_new purchaseItem;

        public UserPurchase userPurchase;


        /*临时报价*/
        public SellerQuoteJsonBean quote;


        /*未读消息列表   */
        public List<UnRead> optionList;


        public List<SeedlingType> seedlingTypeList;

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

        /* 苗圃数量 */
        public int nurseryCount = 0;
        /*采购数量*/
        public int purchaseCount = 0;
        /*收藏数量*/
        public int collectCount = 0;


        public int uncoveredCount = 0;
        public int preCount = 0;
        public int quoteCount = 0;
        public int useCount = 0;
        public int itemQuoteCount = 0;
        public int itemCount = 0;
        //        public int openCount = 0;//已开标数
        public int unOpenCount = 0;//unOpenCount


        /*粉丝数量*/
        public int beFollowCount = 0;

        /*收藏数量*/
        public int followCount = 0;

        /*足迹数量*/
        public int footMarkCount = 0;


        /* 关闭数量 */

        public int closeCount;
        public int openCount;

        /* 开启数量 */


        //        CenterActivity
        public int seedlingCount = 0;/*商城资源*/
        public int momentsCount = 0;/* 苗木圈资源数量 */
        public int visitsCount = 0;/* 店铺浏览量 */
        public int onShelfCount = 0; //商品数量
        public String phone = ""; // 电话号码


        public boolean identity;


        public MomentsReply momentsReply;
        public Moments moments;


        private boolean isCollect;
        public String timeStampStr;
        public String levelName = "";


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
        public UserIdentity userIdentity; // 未认证


        public String agentGrade = " - ";
        public String userPoint = " - ";
        public String agentGradeText = " - ";


        //"agentGrade":"level1","userPoint":14,"agentGradeText":
        // "普通供应商",

        public List<SpecTypeBean> specTypeList;


        public SaveSeedingGsonBean.DataBean.SeedlingBean.NurseryJsonBean nursery = new SaveSeedingGsonBean.DataBean.SeedlingBean.NurseryJsonBean();


        public ImagesJsonBean image;
//        public String name ="-";
//        public String detailAddress ="-";
//        public String contactName ="-";
//        public String contactPhone ="-";
//        public boolean isDefault ;
//        public String id ="";


        /* 用户报价 使用  临时属性 */
        public List<SpecTypeBean> diameterTypeList;
        public List<UnitTypeBean> unitTypeList;
        public List<SpecTypeBean> dbhTypeList;
        public List<UnitTypeBean> validityList;
        public List<TypeListBean> typeList;


        /* 用户报价 使用  临时属性 */


//        public List<FootMarkSourceType> typeList ;
    }


    public boolean isSucceed() {
        return code.equals(ConstantState.SUCCEED_CODE);
    }


}

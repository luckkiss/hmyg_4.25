package com.hldj.hmyg.saler.purchase.userbuy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.ExpandableItemAdapter;
import com.hldj.hmyg.buyer.weidet.entity.MultiItemEntity;
import com.hldj.hmyg.saler.M.enums.ValidityEnum;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.bean.UserPurchase;
import com.hldj.hmyg.saler.bean.UserQuote;
import com.hldj.hmyg.util.AlertUtil;
import com.hldj.hmyg.util.ConstantState;
import com.hy.utils.SpanUtils;
import com.hy.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户报价  之后 显示  报价列表  内容  与 头部
 */
@SuppressLint({"NewApi", "ResourceAsColor"})
public class PublishForUserListActivity extends BaseMVPActivity {


    //${adminPath}/userPurchase" detailForMe
//${adminPath}/userQuote listForUserPurchase

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycle;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_buy_user_list;
    }

    @Override
    protected void initListener() {


    }

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);
        initRecycleView(recycle);


        requestHead(getExtraID());


    }

    private void requestList(String extraID) {
//{adminPath}/userQuote listForUserPurchase
        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<UserQuote>>>>() {
        }.getType();
        new BasePresenter()
                .putParams("purchaseId", extraID)
                .doRequest("admin/userQuote/listForUserPurchase", new HandlerAjaxCallBackPage<UserQuote>(mActivity, type, UserQuote.class) {
                    @Override
                    public void onRealSuccess(List<UserQuote> e) {

                        Log.i(TAG, "onRealSuccess: ");
                        recycle.getAdapter().addData(e);


                        if (e == null || e.size() == 0) {
                            View foot = LayoutInflater.from(mActivity).inflate(R.layout.empty_view_top, null);
                            TextView view = (TextView) foot.findViewById(R.id.t_emptyTextView);
                            view.setText("暂无用户报价");
                            recycle.addFooterView(foot);
                            setResult(ConstantState.REFRESH);
                        } else {
                            setResult(ConstantState.REFRESH);
                        }


                    }
                });
    }

    private void initRecycleView(CoreRecyclerView recycle) {


        recycle.init(new ExpandableItemAdapter(R.layout.item_purche_for_user, R.layout.item_purche_for_user_list) {
            @Override
            protected void convert(BaseViewHolder helper, MultiItemEntity item) {
                if (item.getItemType() == TYPE_LEVEL_0)//子布局
                {

                    if (item instanceof UserPurchase) {
                        doHeadInitView(helper, (UserPurchase) item);
                    }
                } else {
                    if (item instanceof UserQuote) {
                        doConvert(helper, ((UserQuote) item));

                    }

                }


            }
        });

        recycle.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 15);
                parent.setBackgroundResource(R.color.gray_bg);
            }
        });


    }

    private void doConvert(BaseViewHolder helper, UserQuote item) {
//String.format("共%s条报价(%d条未读)", count ,unreadQuoteCountJson )

        String str = "";

        if (unreadQuoteCountJson == 0) {
            str = " (全部已读)";
        } else {
            str = " (" + unreadQuoteCountJson + "条未读)";
        }

        SpannableStringBuilder unreadHead = new SpanUtils()
                .append("共")
                .append(count).setForegroundColor(getColorByRes(R.color.main_color))
                .append("条报价")
                .append(str).setForegroundColor(getColorByRes(R.color.text_color999))
                .create();

//        SpannableStringBuilder title = new SpanUtils()
//                .append("共" + count + "条报价")
//                .append("(" + unreadQuoteCountJson + "条未读)").setForegroundColor(getColorByRes(R.color.price_orige))
//                .create();


        helper
                .setVisible(R.id.title, helper.getAdapterPosition() == 1)
                .setText(R.id.title, unreadHead)
                .setText(R.id.dhj, "[" + item.priceTypeName + "] ")
                .setText(R.id.price, item.price)
                .setText(R.id.unit, "/" + item.attrData.unitTypeName)
                .setText(R.id.tupian, "有" + item.imagesJson.size() + "张图片")
//                .addOnClickListener(R.id.tupian,   )
                .setText(R.id.myd_content, item.cityName)
                .setText(R.id.remarks_content, item.remarks)
                .setText(R.id.textView68, item.attrData.storeName)
                .setText(R.id.textView70, item.attrData.displayName)

                .setVisible(R.id.iv_bhs, item.isExclude)
                .setVisible(R.id.bhs, !item.isExclude)
                .setVisible(R.id.is_new, !item.isRead)

                .addOnClickListener(R.id.ddh, v -> FlowerDetailActivity.CallPhone(item.attrData.phone, mActivity)) // 打电话
                .addOnClickListener(R.id.bhs, v -> 不合适(item.id, helper.getAdapterPosition())) // 不合适
        ;

        SuperTextView superTextView = helper.getView(R.id.tupian);
        PurchaseDetailActivity.setImgCountsSeed(mActivity, superTextView, item.imagesJson);
        ;


        Log.i(TAG, "doConvert: 是否合适---> " + item.isExclude);

        ImageLoader.getInstance().displayImage(item.attrData.headImage, (ImageView) helper.getView(R.id.imageView20));


    }

    private void 不合适(String id, int pos) {

        AlertUtil.showAlert("确定设置这条报价不合适?", mActivity, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BasePresenter()
                        .putParams("id", id)
                        .doRequest("admin/userQuote/saveExclude", new HandlerAjaxCallBack() {
                            @Override
                            public void onRealSuccess(SimpleGsonBean gsonBean) {
                                ToastUtil.showLongToast(gsonBean.msg);

                                refresh(pos);
                            }
                        });
            }
        });


    }

    private void refresh(int po) {

        UserQuote userQuote = (UserQuote) recycle.getAdapter().getData().get(po);
        userQuote.isExclude = true;
        recycle.getAdapter().notifyItemChanged(po);
//        recycle.getAdapter().notifyItemChanged(po, userQuote);
        Log.i(TAG, "refresh: possition -- > " + po);

    }


    private void doHeadInitView(BaseViewHolder helper, UserPurchase item) {


        try {
            setText(helper.getView(R.id.name), item.name);

            setText(helper.getView(R.id.close_time), item.closeDateStr);
            setText(helper.getView(R.id.quote_num), item.count + item.unitTypeName);
            setText(helper.getView(R.id.space_text), item.specText);
            setText(helper.getView(R.id.city), item.cityName);
            setText(helper.getView(R.id.marks), item.remarks);
            setText(helper.getView(R.id.yaoqiu), item.needImage ? "需要上传图片" : "可以不上传图片");

            ValidityEnum enumItem = Enum.valueOf(ValidityEnum.class, item.validity);


            setText(helper.getView(R.id.tv_fr_item_state), String.format("求购期限：%s天", enumItem.getDays() + ""));
            helper.setTextColorRes(R.id.tv_fr_item_state, R.color.price_orige);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private String currentCityCode = "";


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start2Activity(Activity mActivity, String id) {
        Intent intent = new Intent(mActivity, PublishForUserListActivity.class);
        Log.i(TAG, "id is =====  " + id);
        intent.putExtra("ID", id);
        mActivity.startActivityForResult(intent, 100);

    }

    public String getExtraID() {
        return getIntent().getExtras().getString("ID", "");
    }

    @Override
    public String setTitle() {
        return "个人求购";
    }


    private static final String TAG = "PublishForUserDetailAct";

    public void requestHead(String id) {
        new BasePresenter()
                .putParams("id", id)// 用户求购 用户求购 用户求购
                .doRequest("admin/userPurchase/detailForMe", new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {

                        //${adminPath}/userPurchase" detailForMe

                        Log.i(TAG, "onRealSuccess: " + gsonBean.isSucceed());

                        count = gsonBean.getData().userPurchase.quoteCountJson;
                        unreadQuoteCountJson = gsonBean.getData().userPurchase.unreadQuoteCountJson;

                        List<UserPurchase> userPurchases = new ArrayList<>();
                        userPurchases.add(gsonBean.getData().userPurchase);
//                        userPurchases
                        recycle.getAdapter().addData(gsonBean.getData().userPurchase);


                        requestList(id);


                    }
                });


    }


    String count;
    int unreadQuoteCountJson = 0;
}

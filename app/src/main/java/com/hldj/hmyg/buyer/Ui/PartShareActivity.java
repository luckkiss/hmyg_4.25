package com.hldj.hmyg.buyer.Ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.text.SpannableStringBuilder;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.M.PurchaseItemBean_new;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.hy.utils.GetServerUrl;
import com.hy.utils.SpanUtils;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 *
 */

public class PartShareActivity extends BaseMVPActivity {


    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycle;
    @ViewInject(id = R.id.bottom_left)
    TextView bottom_left;
    @ViewInject(id = R.id.bottom_right)
    TextView bottom_right;

    public static ComonShareDialogFragment.ShareBean shareBean;

    public static String parentId = "";

    public static List<PurchaseItemBean_new> shareList;

    @Override
    public void initView() {

        FinalActivity.initInjectedView(this);

        bottom_left.setText("取消");
        bottom_right.setText("分享");
        bottom_left.setOnClickListener(v -> finish());
        bottom_right.setOnClickListener(v -> {

            StringBuffer buffer = new StringBuffer();
            for (PurchaseItemBean_new purchaseItemBean_new : shareList) {
                if (purchaseItemBean_new.isChecked())
                    buffer.append(purchaseItemBean_new.id).append(",");
            }


            getShareUrl(buffer);


        });

        recycle.init(new BaseQuickAdapter<PurchaseItemBean_new, BaseViewHolder>(R.layout.item_part_share) {

            @Override
            protected void convert(BaseViewHolder helper, PurchaseItemBean_new item) {
                CheckedTextView checkedTextView = helper.getView(R.id.check_text);
                checkedTextView.setChecked(item.isChecked());

                SpannableStringBuilder spannableStringBuilder =
                        new SpanUtils()
                                .append((helper.getAdapterPosition() + 1) + ". " + item.name)
                                .append(String.format("   %s/%s", item.count+"", item.unitTypeName)).setForegroundColor(getColorByRes(R.color.price_orige))
                                .create();


                checkedTextView.setText(spannableStringBuilder);

                checkedTextView.setOnClickListener(v -> {
                    item.toggle();
                    checkedTextView.toggle();
                });
            }
        })

                .closeDefaultEmptyView();


        recycle.getRecyclerView().addItemDecoration(new DividerItemDecoration(mActivity, VERTICAL));

        recycle.getAdapter().addData(shareList);


    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_part_share;
    }

    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, PartShareActivity.class));
    }

    @Override
    public String setTitle() {
        return "部分商品分享";
    }


    public void getShareUrl(StringBuffer buffer)

    {
/**
 * 	result.setData("shareUrl", url);
 result.setData("shareDetail", itemNames);
 result.setData("shareTitle",shareTitleStr);
 */

        new BasePresenter()
                .putParams("purchaseId", parentId)
                .putParams("purchaseItemIds", buffer.toString())
                .doRequest("admin/purchaseQr/getUserPurchaseQr", new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {


                        if (shareBean == null) {
                            shareBean = new ComonShareDialogFragment.ShareBean();
                        }
                        shareBean.title = gsonBean.getData().shareTitle;
                        shareBean.text = gsonBean.getData().shareDetail;
                        shareBean.pageUrl = gsonBean.getData().shareUrl;
                        shareBean.imgUrl = GetServerUrl.ICON_PAHT;


//                      shareBean.text = buffer.toString();

//                        ToastUtil.showShortToast("已选品种位置 -- > \n" + shareBean.toString());

                        ComonShareDialogFragment.newInstance().setShareBean(shareBean).show(getSupportFragmentManager(), "PartShareActivity");

                    }
                });


//            ShareDialogFragment.newInstance()
//
//                    .show(getSupportFragmentManager(),"PartShareActivity");


    }

}

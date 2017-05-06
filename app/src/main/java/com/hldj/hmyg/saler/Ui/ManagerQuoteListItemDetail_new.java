package com.hldj.hmyg.saler.Ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.buyer.P.PurchaseDeatilP;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.saler.bean.UsedQuoteListBean;
import com.hy.utils.ToastUtil;

import java.util.List;


/**
 * 修改成 快速报价里面的详情界面一样，增加报价结束功能
 * Created by Administrator on 2017/5/3.
 */

public class ManagerQuoteListItemDetail_new extends PurchaseDetailActivity {


    @Override
    public void setContentView() {
        setContentView(R.layout.purchase_detail_activity);
    }


    List<UsedQuoteListBean> usedQuoteList;

    @Override
    public void getDatas() {
        new PurchaseDeatilP(new ResultCallBack<SaveSeedingGsonBean>() {
            @Override
            public void onSuccess(SaveSeedingGsonBean saveSeedingGsonBean) {
                usedQuoteList = saveSeedingGsonBean.getData().usedQuoteList;

                initDatas(saveSeedingGsonBean);

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

            }
        }).getDatasMgLst(getIntent().getExtras().get(GOOD_ID).toString());//请求数据  进行排版
    }


    @Override
    public void initRceycle(boolean direce) {
        overClick();
        super.initRceycle(direce);
        if (usedQuoteList != null) {
            for (int i = 0; i < usedQuoteList.size(); i++) {
                CardView cardView = new CardView(this);
                View view = LayoutInflater.from(this).inflate(R.layout.item_manager_bottom, cardView);
                if (i != 0)
                    view.findViewById(R.id.tv_recycle_detail_bottom_title).setVisibility(View.GONE);
                ((TextView) view.findViewById(R.id.tv_recycle_detail_bottom)).setText(strFilter("价格：￥" + usedQuoteList.get(i).price + "  " + "[" + usedQuoteList.get(i).specText + "]"));

                recyclerView.addFooterView(cardView);
            }
        }
    }


    /**
     * 重写删除的点击事件
     */
    private void overClick() {

        clic2Del = v -> new PurchaseDeatilP(new ResultCallBack<SaveSeedingGsonBean>() {
            @Override
            public void onSuccess(SaveSeedingGsonBean saveSeedingGsonBean) {

                ToastUtil.showShortToast("删除成功");
                ManagerQuoteListItemDetail_new.this.finish();
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

            }
        })
                .quoteDdel(item.id);

    }


//    private void initBottomItems(List<UsedQuoteListBean> usedQuoteList) {
//        CoreRecyclerView recyclerView = (CoreRecyclerView) findViewById(R.id.recycle_detail_bottom);
//        recyclerView.initView(this).init(new BaseQuickAdapter<UsedQuoteListBean, BaseViewHolder>(R.layout.item_manager_bottom) {
//            @Override
//            protected void convert(BaseViewHolder helper, UsedQuoteListBean item) {
//                D.e("==========item=============" + item.toString());
//                helper.setText(R.id.tv_recycle_detail_bottom, strFilter("价格：￥" + item.price + ""));
//
//                if (helper.getAdapterPosition() != 0) {
//                    helper.setVisible(R.id.tv_recycle_detail_bottom_title, false);
//                }
//            }
//        });
//        recyclerView.getAdapter().addData(usedQuoteList);
//    }

    public static void start2Activity(Activity activity, String good_id) {
        Intent intent = new Intent(activity, ManagerQuoteListItemDetail_new.class);
        intent.putExtra(GOOD_ID, good_id);
        activity.startActivityForResult(intent, 100);
    }


}

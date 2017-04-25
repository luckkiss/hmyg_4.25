package com.hldj.hmyg.buyer.Ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.buyer.M.QuoteGsonBean;
import com.hldj.hmyg.buyer.P.QuoteListP;
import com.hldj.hmyg.buyer.V.PurchaseDeatilV;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hy.utils.ToastUtil;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

public class QuoteListActivity_bak extends NeedSwipeBackActivity implements PurchaseDeatilV {


    QuoteGsonBean saveSeedingGsonBean;
    private CoreRecyclerView recycle_quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_puchase_detail_bak);
        InitViews();


    }

    private static int pageNum = 50;

    @Override
    public void InitViews() {
        recycle_quit = (CoreRecyclerView) findViewById(R.id.recycle_quit);

        recycle_quit = new CoreRecyclerView(this).init(new BaseQuickAdapter<QuoteGsonBean, BaseViewHolder>(R.layout.item_quote) {
            @Override
            protected void convert(BaseViewHolder helper, QuoteGsonBean item) {

            }

        }).openLoadMore(pageNum, page ->
        {

            //刷新后调用本接口
            getDatas();


        })
                .openRefresh();
        ;


    }


    @Override
    public void InitHolder() {

    }

    @Override
    public void InitOnclick() {

    }


    private static final String GOOD_ID = "good_id";

    @Override
    public void getDatas() {


        if (null == getIntent().getExtras().get(GOOD_ID)) {
            ToastUtil.showShortToast("请用start2Activity方法来跳转到本届面");
            return;
        }

        new QuoteListP(new ResultCallBack<QuoteGsonBean>() {
            @Override
            public void onSuccess(QuoteGsonBean saveSeedingGsonBean) {

                QuoteListActivity_bak.this.saveSeedingGsonBean = saveSeedingGsonBean;
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

            }
        })
                .getDatas(getIntent().getExtras().get(GOOD_ID).toString())
        ;


    }


    public static void start2Activity(Activity activity, String good_id) {
        Intent intent = new Intent(activity, QuoteListActivity_bak.class);
        intent.putExtra(GOOD_ID, good_id);
        activity.startActivityForResult(intent, 100);
    }


}

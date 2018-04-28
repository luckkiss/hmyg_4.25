package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Intent;

import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by luocaca on 2017/11/27 0027.
 * <p>
 * <p>
 * 举报界面
 */

public class BeReportActivity extends BaseMVPActivity {

    int item_layout_id = R.layout.list_view_seedling_new;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_be_attention;
    }

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycler;

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);

        recycler.init(new BaseQuickAdapter<String, BaseViewHolder>(item_layout_id) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {

            }
        });
        recycler.getAdapter().addData("dafda");
        recycler.getAdapter().addData("dafda");
        recycler.getAdapter().addData("dafda");
        recycler.getAdapter().addData("dafda");


    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, BeReportActivity.class));
    }

    @Override
    public String setTitle() {
        return "被举报";
    }

}

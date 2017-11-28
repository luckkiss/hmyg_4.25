package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luocaca on 2017/11/27 0027.
 * <p>
 * <p>
 * 苗友圈详情界面
 */

public class DetailActivity extends BaseMVPActivity {

    private static final String TAG = "DetailActivity";

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView mCoreRecyclerView;

    @ViewInject(id = R.id.tv_activity_purchase_back)
    TextView tv_activity_purchase_back;


    public int bindLayoutID() {
        return R.layout.activity_friend_detail;
    }


    @Override
    public void initView() {
        if (bindLayoutID() > 0) {
            FinalActivity.initInjectedView(this);
        }

        tv_activity_purchase_back.setOnClickListener(v -> finish());

        mCoreRecyclerView.init(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_home_cjgg) {

            @Override
            protected void convert(BaseViewHolder helper, String item) {

            }
        });

        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList.add("数据" + i);
        }
        mCoreRecyclerView.getAdapter().addData(stringList);
    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    //    @Override
//    public int bindLayoutID() {
//        return 0;
//    }


    public static void start(Activity activity) {
        activity.startActivityForResult(new Intent(activity, DetailActivity.class), 110);
    }

    @Override
    public String setTitle() {
        return "供应详情";
    }
}

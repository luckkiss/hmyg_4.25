package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hy.utils.ToastUtil;
import com.lqr.optionitemview.OptionItemView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by luocaca on 2017/11/27 0027.
 * <p>
 * <p>
 * 我的苗友圈
 */

public class CenterActivity extends BaseMVPActivity {

    private static final String TAG = "CenterActivity";


    /*底部 未知选择[*/
    @ViewInject(id = R.id.location)
    OptionItemView location;


    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycle;


    public int bindLayoutID() {
        return R.layout.activity_friend_center;
    }


    @Override
    public void initView() {
        if (bindLayoutID() > 0) {
            FinalActivity.initInjectedView(this);
        }

        recycle.init(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_friend_cicle) {

            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setVisible(R.id.imageView7, false)
                        .setVisible(R.id.tv_right_top, true)
                        .addOnClickListener(R.id.tv_right_top, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtil.showLongToast("删除");
                            }
                        });

            }
        });

        recycle.getAdapter().addData("123");
        recycle.getAdapter().addData("123");
        recycle.getAdapter().addData("123");
        recycle.getAdapter().addData("123");
        recycle.getAdapter().addData("123");
        recycle.getAdapter().addData("123");


    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    //    @Override
//    public int bindLayoutID() {
//        return 0;
//    }


    public static void start(Activity activity, String tag) {

        Intent intent = new Intent(activity, CenterActivity.class);
        intent.putExtra(TAG, tag);
        Log.i(TAG, "start: " + tag);
        activity.startActivityForResult(intent, 110);
    }

    @Override
    public String setTitle() {
        return "我的苗木圈";
    }


}

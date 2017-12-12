package com.hldj.hmyg.Ui.friend.child;

import android.util.Log;
import android.view.View;

import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseFragment;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;

import net.tsz.afinal.annotation.view.ViewInject;

/**
 * 基础的  朋友圈首页  用于显示 发布的内容。。懒加载。不刷新模式
 */

public class FriendBaseFragment extends BaseFragment {

    private static final String TAG = "FriendBaseFragment";

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView mCoreRecyclerView;

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void loadData() {
        super.loadData();
        Log.i(TAG, "loadData: ");
    }

    @Override
    protected int bindLayoutID() {
        return R.layout.fragment_friend_recycle;
    }
}

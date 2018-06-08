package com.hldj.hmyg;

import android.support.annotation.Keep;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.hldj.hmyg.application.StateBarUtil;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.HomeStore;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.ExpandableItemAdapter;
import com.hldj.hmyg.buyer.weidet.entity.MultiItemEntity;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

import aom.xingguo.huang.banner.MyFragment;


/**
 * 首页卡 怎么办  (づ￣3￣)づ╭❤～  首页加速。。。重做呗。擦
 */
@Keep
public class AActivity_3_0_fast extends BaseMVPActivity implements OnClickListener {

    @ViewInject(id = R.id.recycle)
    private CoreRecyclerView recycle;

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);
        ExpandableItemAdapter itemAdapter = new ExpandableItemAdapter(R.layout.list_item_purchase_list_new, R.layout.item_buy_for_user, R.layout.list_view_seedling_new, R.layout.item_list_simple) {
            @Override
            protected void convert(BaseViewHolder helper, MultiItemEntity mItem) {
                if (mItem.getItemType() == TYPE_LEVEL_3)//子布局
                {

                }
                if (mItem.getItemType() == TYPE_LEVEL_2)//子布局
                {

                } else if (mItem.getItemType() == TYPE_LEVEL_1) {

                } else if (mItem.getItemType() == TYPE_LEVEL_0) {

                }
            }
        };

        recycle.init(itemAdapter);


        List<MultiItemEntity> multiItemEntities = new ArrayList<>();
        multiItemEntities.add(() -> 3);
        multiItemEntities.add(() -> 0);
        multiItemEntities.add(() -> 0);
        multiItemEntities.add(() -> 0);
        multiItemEntities.add(() -> 3);
        multiItemEntities.add(() -> 1);
        multiItemEntities.add(() -> 1);
        multiItemEntities.add(() -> 1);
        multiItemEntities.add(() -> 3);
        multiItemEntities.add(() -> 2);
        multiItemEntities.add(() -> 2);
        multiItemEntities.add(() -> 2);


        recycle.getAdapter().addData(multiItemEntities);


        View view = LayoutInflater.from(mActivity).inflate(R.layout.footer_a_fast, null);
        recycle.addFooterView(view);


        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();


        ArrayList<HomeStore> url0s = new ArrayList<HomeStore>();

        url0s.add(new HomeStore("aa", "20", "http://images.hmeg.cn/upload/image/201805/f640784bdb7f4708a67863f692ee9ea8.jpeg", "", "hello-world"));
        url0s.add(new HomeStore("aa", "20", "http://images.hmeg.cn/upload/image/201805/f640784bdb7f4708a67863f692ee9ea8.jpeg", "", "hello-world"));
        url0s.add(new HomeStore("aa", "20", "http://images.hmeg.cn/upload/image/201805/f640784bdb7f4708a67863f692ee9ea8.jpeg", "", "hello-world"));
        url0s.add(new HomeStore("aa", "20", "http://images.hmeg.cn/upload/image/201805/f640784bdb7f4708a67863f692ee9ea8.jpeg", "", "hello-world"));
        url0s.add(new HomeStore("aa", "20", "http://images.hmeg.cn/upload/image/201805/f640784bdb7f4708a67863f692ee9ea8.jpeg", "", "hello-world"));
        url0s.add(new HomeStore("aa", "20", "http://images.hmeg.cn/upload/image/201805/f640784bdb7f4708a67863f692ee9ea8.jpeg", "", "hello-world"));
        url0s.add(new HomeStore("aa", "20", "http://images.hmeg.cn/upload/image/201805/f640784bdb7f4708a67863f692ee9ea8.jpeg", "", "hello-world"));
        url0s.add(new HomeStore("aa", "20", "http://images.hmeg.cn/upload/image/201805/f640784bdb7f4708a67863f692ee9ea8.jpeg", "", "hello-world"));
        url0s.add(new HomeStore("aa", "20", "http://images.hmeg.cn/upload/image/201805/f640784bdb7f4708a67863f692ee9ea8.jpeg", "", "hello-world"));
        url0s.add(new HomeStore("aa", "20", "http://images.hmeg.cn/upload/image/201805/f640784bdb7f4708a67863f692ee9ea8.jpeg", "", "hello-world"));
        url0s.add(new HomeStore("aa", "20", "http://images.hmeg.cn/upload/image/201805/f640784bdb7f4708a67863f692ee9ea8.jpeg", "", "hello-world"));
        url0s.add(new HomeStore("aa", "20", "http://images.hmeg.cn/upload/image/201805/f640784bdb7f4708a67863f692ee9ea8.jpeg", "", "hello-world"));
        url0s.add(new HomeStore("aa", "20", "http://images.hmeg.cn/upload/image/201805/f640784bdb7f4708a67863f692ee9ea8.jpeg", "", "hello-world"));

        MyFragment myFragment = (MyFragment) getSupportFragmentManager().findFragmentById(R.id.con0);
        if (myFragment != null)

//        MyFragment myFragment0 = new MyFragment();
        {
            myFragment.setUrls(url0s);
            myFragment.refreshGrids(url0s);
        } else
            ToastUtil.showLongToast("foot fragment is empty");
//        ft.add(R.id.con0, myFragment0);
//                    myFragment0.setAutoChange(true);
//                                    iv_home_merchants  .setVisibility(View.VISIBLE);
//        ft.commitAllowingStateLoss();


    }

    @Override
    public boolean setSwipeBackEnable() {
        return false;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_a_3_0_fast;
    }


    @Override
    protected void onResume() {
        super.onResume();
        setStatusBars();
    }

    private void setStatusBars() {
        try {
            StateBarUtil.setStatusTranslater(MainActivity.instance, true);
            StateBarUtil.setMiuiStatusBarDarkMode(MainActivity.instance, true);
            StateBarUtil.setMeizuStatusBarDarkIcon(MainActivity.instance, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

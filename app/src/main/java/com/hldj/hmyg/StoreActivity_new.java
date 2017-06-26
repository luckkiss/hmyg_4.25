package com.hldj.hmyg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.StoreGsonBean;
import com.hldj.hmyg.buyer.weidet.BaseMultAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.contract.StoreContract;
import com.hldj.hmyg.model.StoreModel;
import com.hldj.hmyg.presenter.StorePresenter;
import com.hldj.hmyg.util.D;

import net.tsz.afinal.FinalBitmap;

import java.io.Serializable;
import java.util.List;

@SuppressLint("Override")
public class StoreActivity_new extends BaseMVPActivity<StorePresenter, StoreModel> implements StoreContract.View {

    /**
     * 定义上传搜索对象
     */
    private QueryBean queryBean;
    private CoreRecyclerView store_recycle;
    private static final int LIST_VIEW = 0;//默认是list 列表展示
    int type = LIST_VIEW;//切换类型  GRID_VIEW

    @Override
    public int bindLayoutID() {
        return R.layout.activity_store_mvp;
    }


    FinalBitmap bitmap;

    //step 1   初始化 控件
    @Override
    public void initView() {
        bitmap = FinalBitmap.create(this);
        getView(R.id.btn_back).setOnClickListener(view -> finish());//后退

        store_recycle = (CoreRecyclerView) findViewById(R.id.store_recycle);
        store_recycle.init(new BaseMultAdapter<BPageGsonBean.DatabeanX.Pagebean.Databean, BaseViewHolder>(R.layout.list_view_seedling_new, R.layout.grid_view_seedling) {
            @Override
            protected void convert(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item) {
                if (type == GRID_VIEW) {
                    D.e("=======GRID_VIEW========");
                    BActivity_new_test.initGridType(helper, bitmap, item);
                } else {
                    D.e("=======常规布局========");
                    BActivity_new_test.initListType(helper, item, bitmap, "BActivity_new");
                }
            }
        }, true).openLoadMore(getQueryBean().pageSize, page -> {
            getQueryBean().pageIndex = page + "";
            mPresenter.getData();
        });


    }


    //step 2    网络请求
    @Override
    public void initVH() {
        // 使用rx java  尝试嵌套 请求 网络
        /**
         * {@link StoreActivity_new.QueryBean}
         */
        // 先获取 index  数据   在通过index  中的数据  获取    列表信息
        //step 2.1    网络请求
        mPresenter.getIndexData();

        /**
         *
         params.put("id", code2);
         */


//        Observable observable = mPresenter.getDataRx(getQueryBean());
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String json) throws Exception {
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//
//                    }
//                });


//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//
//
//            }
//        });
    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    @Override
    public QueryBean getQueryBean() {
        if (queryBean == null) {
            queryBean = new QueryBean();
        }
        return queryBean;
    }

    @Override
    public String getStoreID() {
        //这里可以进行跳转判断
        return getIntent().getStringExtra("code");
    }

    @Override
    public ViewPager getViewPager() {
        return getView(R.id.vp_store_content);
    }

    public static class QueryBean implements Serializable {
        public String ownerId = "";
        public String orderBy = "";
        public String plantTypes = "";
        public String firstSeedlingTypeId = "";
        public int pageSize = 10;
        public String pageIndex = "0";

        @Override
        public String toString() {
            return "QueryBean{" +
                    "ownerId='" + ownerId + '\'' +
                    ", orderBy='" + orderBy + '\'' +
                    ", plantTypes='" + plantTypes + '\'' +
                    ", firstSeedlingTypeId='" + firstSeedlingTypeId + '\'' +
                    ", pageSize=" + pageSize +
                    ", pageIndex='" + pageIndex + '\'' +
                    '}';
        }
    }


    /**
     * @param context
     * @param code    商店的  id
     */
    public static void start2Activity(Context context, String code) {
        Intent intent = new Intent(context, StoreActivity_new.class);
        intent.putExtra("code", code);
        context.startActivity(intent);
    }


    @Override
    public void initStoreData(List<BPageGsonBean.DatabeanX.Pagebean.Databean> list) {
        store_recycle.getAdapter().addData(list);
    }

    @Override
    public void initIndexBean(StoreGsonBean.DataBean indexBean) {
        //通过index 中的一些数据  进行 列表请求
//        getQueryBean().firstSeedlingTypeId = indexBean.store.
        getQueryBean().ownerId = indexBean.owner.id;
        D.e("=======QueryBean=========" + getQueryBean().toString());
        mPresenter.getData();
    }

}

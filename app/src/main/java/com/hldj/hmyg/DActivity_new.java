package com.hldj.hmyg;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.Ui.friend.bean.MomentsThumbUp;
import com.hldj.hmyg.Ui.friend.bean.enums.MomentsType;
import com.hldj.hmyg.Ui.friend.child.DetailActivity;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.application.StateBarUtil;
import com.hldj.hmyg.base.MySwipeAdapter;
import com.hldj.hmyg.bean.CollectGsonBean;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.presenter.CollectPresenter;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.util.VideoHempler;
import com.hldj.hmyg.widget.MyCircleImageView;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;
import com.mabeijianxi.smallvideo2.VideoPlayerActivity2;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zf.iosdialog.widget.AlertDialog;
import com.zzy.common.widget.MeasureGridView;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;
import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;


/**
 * 收藏夹  界面
 */
public class DActivity_new extends NeedSwipeBackActivity implements IXListViewListener {
    private XListView xlistView_d_new;
    private static final String TAG = "DActivity_new";

    boolean getdata = true; // 避免刷新多出数据

    private int pageSize = 20;
    private int pageIndex = 0;
    private MySwipeAdapter collectAdapter;//收藏列表的  适配器

    //    public static DActivity_new instance;
    //    private KProgressHUD hud;
    private LocalBroadcastReceiver localReceiver;


    /* 清空收藏夹 需要的属性  */

    private static final String seedling = "seedling";
    private static final String moment = "moment";
    String deleteType = seedling;

    /* 清空收藏夹 需要的属性  */

    /*  新增  苗木圈收藏列表  各种属性*/
    TabItem left;
    TabItem right;
    TabLayout tabLayout;
    CoreRecyclerView mCoreRecyclerView;
    LinearLayout left_content;
    /*  新增  苗木圈收藏列表  */


    /* 初始化苗木圈收藏的  各种 控件 */
    public boolean isSelf(String userId) {
        return MyApplication.Userinfo.getString("id", "").equals(userId);
    }


    private void doDelete(Moments moments, int pos, String currentType) {
        String host;

        host = "admin/collect/save";

        Log.i(TAG, "doDelete: host\n" + host);
        new BasePresenter()
                .putParams("ids", moments.id)
                .putParams("id", moments.id)
                .putParams("sourceId", moments.id)
                .putParams("type", "moment")
                .doRequest(host, true, new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        ToastUtil.showLongToast(gsonBean.msg);
                        if (gsonBean.isSucceed()) {
//                            mRecyclerView.onRefresh();
//                          mRecyclerView.getAdapter().getData().remove(moments);
                            mCoreRecyclerView.remove(pos);
                            /*非 搜藏的情况。发送 删除关播  不需要删除列表的 */
//                          RxBus.getInstance().post(RxBus.TAG_DELETE, moments);
                        }
                    }
                });
    }

    public void requestDatas(String page, String type, String id) {
        showLoading();
        String host = "admin/moments/list";

        host = "admin/collect/listMoment";

        Log.i(TAG, "host url: \n" + host);
//        ToastUtil.showLongToast(host);
//      String host_collect = "admin/collect/listMoment";
        new BasePresenter()
//                .putParams("sourceId", "2876f7e0f51c4153aadc603b661fedfa")
                .putParams("pageSize", "10")
                .putParams("pageIndex", page)
                .putParams("momentsType", type)
                .putParams("type", "moment")
                .putParams("userId", id)
                .putParams("ownerId", id)
                .doRequest(host, true, new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String json) {
                        hindLoading();
                        Log.i(TAG, "onSuccess: " + json);
                        Type beanType = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<Moments>>>>() {
                        }.getType();
                        SimpleGsonBean_new<SimplePageBean<List<Moments>>> bean_new = GsonUtil.formateJson2Bean(json, beanType);

                        if (bean_new.data == null || bean_new.data.page == null) {
                            ToastUtil.showLongToast("暂无数据~_~");
                            mCoreRecyclerView.getAdapter().addData(null);
                            return;
                        }

                        mCoreRecyclerView.getAdapter().addData(bean_new.data.page.data);

//                      ToastUtil.showLongToast(bean_new.data.page.total + "条数据");
                        mCoreRecyclerView.selfRefresh(false);
                        hindLoading();

                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                        mCoreRecyclerView.selfRefresh(false);
                        mCoreRecyclerView.showLoadMoreFailedView();
                        hindLoading();
                    }
                });
    }


    private void initCycleViewAndData() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mCoreRecyclerView = (CoreRecyclerView) findViewById(R.id.coreRecyclerView);
        left_content = (LinearLayout) findViewById(R.id.left_content);

//        mCoreRecyclerView.init(new BaseQuickAdapter<Moments ,BaseViewHolder>(R.layout.item_friend_cicle_simple_center) {
//            @Override
//            protected void convert(BaseViewHolder helper, Moments item) {
//                    doConvert(helper , item);
//            }
//
//        });

        mCoreRecyclerView.init(new BaseQuickAdapter<Moments, BaseViewHolder>(R.layout.item_friend_cicle_simple_center) {
            @Override
            protected void convert(BaseViewHolder helper, Moments item) {


                View.OnClickListener clickListener = v ->
                {
//                  ToastUtil.showLongToast("点击文字--->跳转采购单详情界面");
                    DetailActivity.start(mActivity, item.id);
                };
                helper.addOnClickListener(R.id.title, clickListener);// 发布名称或者标题
                helper.addOnClickListener(R.id.time_city, clickListener);//时间和  发布地址
                helper.addOnClickListener(R.id.descript, clickListener);//描述

                helper.setVisible(R.id.imageView7, false)
                        .setVisible(R.id.tv_right_top, true)
                        .addOnClickListener(R.id.tv_right_top, v ->
                                {


                                    new AlertDialog(mActivity).builder()
                                            .setTitle("确定删除本项?")
                                            .setPositiveButton("确定删除", v1 -> {
                                                //                                    ToastUtil.showLongToast("删除");
                                                doDelete(item, helper.getAdapterPosition(), "");

                                            }).setNegativeButton("取消", v2 -> {
                                    }).show();

                                }
                        );
                helper.setText(R.id.descript, item.content);//描述
                String time_city = FUtil.$_zero(item.timeStampStr);
                if (item.ciCity != null && item.ciCity.fullName != null) {
                    time_city += "   " + item.ciCity.fullName;
                }
                helper.setText(R.id.time_city, time_city);//时间戳_发布点

                Log.i(TAG, "convert: " + item);


//                if (item.attrData != null && !TextUtils.isEmpty(item.ownerUserJson.headImage)) {
//                    //显示图片
//                    finalBitmap.display(helper.getView(R.id.head), item.ownerUserJson.headImage);
//                }

                helper.setVisible(R.id.imageView7, false);


                if (item.attrData != null) {
                    helper.setText(R.id.title, item.attrData.displayName);
                    //显示图片
//                    finalBitmap.display(helper.getView(R.id.head), item.attrData.headImage);
                    MyCircleImageView circleImageView = helper.getView(R.id.head);
                    if (!TextUtils.isEmpty(item.attrData.headImage))
                        ImageLoader.getInstance().displayImage(item.attrData.headImage, circleImageView);
//                    circleImageView.setImageURL(item.attrData.headImage);

                }



                  /* 视频  预览图片   */
                if (item.isVideo) {
                    ImageView video = helper.getView(R.id.video);
                    video.setVisibility(View.VISIBLE);
//                  video.setImageBitmap(VideoHempler.createVideoThumbnail(item.videoUrl, MyApplication.dp2px(mContext, 80), MyApplication.dp2px(mContext, 80)));

                    D.e("============加载地址===========" + item.attrData.videoImageUrl);
                    if (item.attrData != null && !TextUtils.isEmpty(item.attrData.videoImageUrl))
                        ImageLoader.getInstance().displayImage(item.attrData.videoImageUrl, video);
                    else {
                        Observable.just(item.videoUrl)
                                .filter(test -> !TextUtils.isEmpty(item.videoUrl))
                                .subscribeOn(Schedulers.io())
                                .map(s -> VideoHempler.createVideoThumbnail(item.videoUrl, MyApplication.dp2px(mContext, 80), MyApplication.dp2px(mContext, 80)))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Bitmap>() {
                                    @Override
                                    public void accept(@NonNull Bitmap result) throws Exception {
                                        video.setImageBitmap(result);
                                    }
                                });
                    }


//                ImageLoader.getInstance().displayImage(item.videoUrl, video);

                    video.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(mActivity, VideoPlayerActivity2.class).putExtra(
                                    "path", item.videoUrl));
                        }
                    });

                } else {
                    ImageView video = helper.getView(R.id.video);
                    video.setVisibility(View.GONE);
                }


                MeasureGridView gridView = helper.getView(R.id.imageView8);
                gridView.setImageNumColumns(3);
                gridView.setHorizontalSpacing(6);
                gridView.setVerticalSpacing(6);
                gridView.initFriend(mActivity, PurchaseDetailActivity.getPicList(item.imagesJson), (ViewGroup) gridView.getParent(), null);
                gridView.setOnViewImagesListener((mContext, pos, pics) -> {
                    GalleryImageActivity.startGalleryImageActivity(
                            mContext, pos, PurchaseDetailActivity.getPicListOriginal(item.imagesJson));
                });
//                gridView.getAdapter().closeAll(true);
//                gridView.getAdapter().notifyDataSetChanged();


                helper.addOnClickListener(R.id.first, clickListener).setText(R.id.first, " " + item.thumbUpCount);//按钮一 点赞

                if (item.thumbUpListJson == null)
                    item.thumbUpListJson = new ArrayList<MomentsThumbUp>();
                helper.addOnClickListener(R.id.second, clickListener).setText(R.id.second, " " + item.replyCount);//按钮2 评论
            }
        }).openRefresh()
                .openLoadMore(10, page -> {
                    showLoadingCus("刷新数据");
                    requestDatas(page + "", MomentsType.collect.getEnumValue(), MyApplication.getUserBean().id);
                    requestCount(tabLayout.getTabAt(0), tabLayout.getTabAt(1), false);
                })
                .setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        mCoreRecyclerView.onLoadMoreRequested();
                    }
                });
        mCoreRecyclerView.onRefresh();


//        left = (TabItem) findViewById(R.id.left);
//        right = (TabItem) findViewById(R.id.right);
//        left.setOnClickListener(v -> {
//            ToastUtil.showLongToast("left");
//        });
//        right.setOnClickListener(v -> {
//            ToastUtil.showLongToast("right");
//        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                ToastUtil.showLongToast(tab.getText() + "");

                left_content.setVisibility(tab.getPosition() == 0 ? View.VISIBLE : View.GONE);
                mCoreRecyclerView.setVisibility(tab.getPosition() == 0 ? View.GONE : View.VISIBLE);

                if (tab.getPosition() == 0) {
                    deleteType = seedling;
                } else {
                    deleteType = moment;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        requestCount(tabLayout.getTabAt(0), tabLayout.getTabAt(1), true);

    }


    private void requestCount(TabLayout.Tab tabAt, TabLayout.Tab tabAt1, boolean move2) {

        new BasePresenter()
                .putParams("userId", MyApplication.getUserBean().id)
                .doRequest("admin/collect/collectCount", true, new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        tabAt.setText("商城资源 ( " + gsonBean.getData().seedlingCount + " )");
                        tabAt1.setText("苗木圈 ( " + gsonBean.getData().momentsCount + " )");

                        //seedlingCount
//                        momentsCount
                        if (gsonBean.getData().seedlingCount == 0 && gsonBean.getData().momentsCount != 0 && move2) {
                            tabAt1.select();
                        }


                    }
                });

//        new BasePresenter()
//                .putParams("userId", getUserId())
//                .doRequest("moments/momentsCount", true, new HandlerAjaxCallBack() {
//                    @Override
//                    public void onRealSuccess(SimpleGsonBean gsonBean) {
//                        tabAt.setText("供应 ( " + gsonBean.getData().supplyCount + " )");
//                        tabAt1.setText("求购 ( " + gsonBean.getData().purchaseCount + " )");
//                        tabAt2.setText("收藏 ( " + gsonBean.getData().collectCount + " )");
//                    }
//                });
    }


    private void doConvert(BaseViewHolder helper, Moments item) {


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        hud = KProgressHUD.create(DActivity_new.this)
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("数据加载中...").setMaxProgress(100).setCancellable(true)
//                .setDimAmount(0f);


        setContentView(R.layout.activity_d_new);
//        instance = this;

//        StatusBarUtil.setColor(MainActivity.instance, Color.WHITE);
//        StateBarUtil.setStatusBarIconDark(MainActivity.instance, true);
//        mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        StatusBarUtil.setColor(MainActivity.instance, Color.GREEN);
//        StateBarUtil.setStatusBarIconDark(MainActivity.instance, true);
//        StateBarUtil.setStatusTranslaterNoFullStatus(MainActivity.instance, true);

        setSwipeBackEnable(false);
        initView();
        initCycleViewAndData();

//        hud.show();
        initData();

        addLocalBrodcast();


    }


//    public static DActivity_new getInstance() {
//
//        if (instance != null) {
//            return instance;
//        } else {
//            return null;
//        }
//
//    }


    List<SaveSeedingGsonBean.DataBean.SeedlingBean> seedlingBeen = new ArrayList<>();

    private void initView() {


        if (getIntent().getExtras() != null) {
            boolean isShow = (boolean) getIntent().getExtras().get(IS_SHOW);
            if (isShow) {
                getView(R.id.iv_back).setVisibility(View.VISIBLE);
                getView(R.id.iv_back).setOnClickListener(v -> finish());
                setSwipeBackEnable(true);
            }
        } else {

        }


        getView(R.id.tv_clear_all).setOnClickListener(v -> {
                    showLoading();
                    D.e("==============清空收藏夹============");
                    String deleteTitle = deleteType.equals(seedling) ? "确认清空资源收藏?" : "确认清空苗木圈收藏?";
                    new AlertDialog(this).builder()
//                            .setTitle("确定清空所有收藏?")
                            .setTitle(deleteTitle)
                            .setPositiveButton("确定", v1 -> {
                                new CollectPresenter(new ResultCallBack<SimpleGsonBean>() {
                                    @Override
                                    public void onSuccess(SimpleGsonBean simpleGsonBean) {
//                                        pageIndex = 0;
//                                        seedlingBeen.clear();
//                                        collectAdapter.notifyDataSetChanged();

                                        if (deleteType.equals(moment)) {
                                            mCoreRecyclerView.onRefresh();
                                        } else {
                                            onRefresh();
                                        }

                                        hindLoading();
                                    }

                                    @Override
                                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                                        hindLoading();
                                    }
                                }).reqClearCollect(deleteType);
                            }).setNegativeButton("取消", v2 -> {
                    }).show();

                }

        );


        xlistView_d_new = getView(R.id.xlistView_d_new);
//        xlistView_d_new.setDivider(new BitmapDrawable() );
        xlistView_d_new.setPullLoadEnable(true);
        xlistView_d_new.setPullRefreshEnable(true);
        xlistView_d_new.setXListViewListener(this);

        collectAdapter = new MySwipeAdapter(this, seedlingBeen);
        xlistView_d_new.setAdapter(collectAdapter);

    }


//    boolean isRefreshing = false;

    private void initData() {
        /**
         * 2.5.8.1 收藏夹苗木列表
         1，说明
         收藏夹苗木列表返回该用户对应类型的收藏内容
         2，URL
         Post:      /admin/collect/listSeedling
         3，参数
         admin/collect/listSeedling
         */
        // TODO Auto-generated method stub
        showLoading();

        getdata = false;
//        isRefreshing = true;
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("pageSize", pageSize + "");
        params.put("pageIndex", pageIndex + "");
        finalHttp.post(GetServerUrl.getUrl() + "admin/collect/listSeedling", params,
//        finalHttp.post(GetServerUrl.getUrl() + "admin/cart/list", params,
                new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String json) {
                        CollectGsonBean collectGsonBean = GsonUtil.formateJson2Bean(json, CollectGsonBean.class);
//                        {"code":"1","msg":"操作成功","data":{"page":{"pageNo":1,"pageSize":20,"total":2,"firstResult":20,"maxResults":20}}}
                        D.e("========json=========" + json);
                        if (collectGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                            if (collectGsonBean.data.page.total != 0 && collectGsonBean.data.page.data.size() != 0) {
                                if (isFresh) {
                                    seedlingBeen.clear();
                                }
                                seedlingBeen.addAll(collectGsonBean.data.page.data);
                                collectAdapter.notifyDataSetChanged();
                                D.e("=====pageIndex=======" + pageIndex);
                                getView(R.id.xlistView_d_new).setVisibility(View.VISIBLE);
                                getView(R.id.rl_refresh).setVisibility(View.GONE);

                            } else {
                                getView(R.id.xlistView_d_new).setVisibility(View.GONE);
                                getView(R.id.rl_refresh).setVisibility(View.VISIBLE);
                            }


                        } else {
                            getView(R.id.xlistView_d_new).setVisibility(View.GONE);
                            getView(R.id.rl_refresh).setVisibility(View.VISIBLE);

                            D.e("===数据库空空如也====");
                        }

//                        hud.dismiss();
//                        isRefreshing = false;

                        hindLoading();
                        pageIndex = seedlingBeen.size() / 20;
                        getdata = true;
                        getView(R.id.rl_refresh).setOnClickListener(refresh);

                        D.e("===============collectGsonBean================" + collectGsonBean);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        // TODO Auto-generated method stub
                        Toast.makeText(DActivity_new.this, R.string.error_net,
                                Toast.LENGTH_SHORT).show();
                        getView(R.id.xlistView_d_new).setVisibility(View.GONE);
                        getView(R.id.rl_refresh).setVisibility(View.VISIBLE);
                        getView(R.id.rl_refresh).setOnClickListener(refresh);
//                        hud.dismiss();
//                        isRefreshing = false;
                        hindLoading();
                        getdata = true;
                        isFresh = false;
                        super.onFailure(t, errorNo, strMsg);
                        getView(R.id.rl_refresh).setOnClickListener(refresh);
                    }

                });

    }


    public boolean isFresh = false;

    @Override
    public void onRefresh() {
        requestCount(tabLayout.getTabAt(0), tabLayout.getTabAt(1), false);
        // TODO Auto-generated method stub
        xlistView_d_new.setPullLoadEnable(false);
        pageIndex = 0;
        isFresh = true;
//        datas.clear();
//        listAdapter.notifyDataSetChanged();
        if (getdata == true) {
            initData();
        }
        onLoad();

    }


    public void addLocalBrodcast() {
        localReceiver = new LocalBroadcastReceiver();

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);

        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(ConstantState.COLLECT_REFRESH);
        intentFilter.addAction(ConstantState.COLLECT_CENTER_REFRESH);

        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }


    @Override
    public void onLoadMore() {
        xlistView_d_new.setPullRefreshEnable(false);
        //判断是否超过一页。。么有的话刷新
        if (seedlingBeen.size() % 20 == 0) {
            initData();
        }
        onLoad();
    }

    private void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                xlistView_d_new.stopRefresh();
                xlistView_d_new.stopLoadMore();
                xlistView_d_new.setRefreshTime(new Date().toLocaleString());
                xlistView_d_new.setPullLoadEnable(true);
                xlistView_d_new.setPullRefreshEnable(true);

            }
        }, com.hldj.hmyg.application.Data.refresh_time);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localReceiver);
    }


    class LocalBroadcastReceiver extends BroadcastReceiver {

        @Override

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConstantState.COLLECT_CENTER_REFRESH)) {
                mCoreRecyclerView.onRefresh();
            } else {
                onRefresh();
            }
//            seedlingBeen.clear();
//            pageIndex = 0;
//            initData();
            D.e("==refresh===");

        }

    }


    View.OnClickListener refresh = v -> {
        onRefresh();
    };


    protected static final String IS_SHOW = "isShow";

    public static void start2Activity(Activity context, boolean isShowBack) {
        Intent intent = new Intent(context, DActivity_new.class);
        intent.putExtra(IS_SHOW, isShowBack);
        context.startActivity(intent);

    }


    @Override
    protected void onResume() {
        super.onResume();

//        StatusBarUtil.setColor(MainActivity.instance, Color.WHITE);
//        mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        View deco = mActivity.getWindow().getDecorView();
//        deco.setBackgroundColor(Color.WHITE);
//        deco.setPadding(0, 50, 0, 0);

//      StatusBarUtil.setColor(MainActivity.instance, Color.WHITE);
//      StatusBarUtil.setColor(MainActivity.instance, ContextCompat.getColor(mActivity, R.color.main_color));

//        StateBarUtil.setMiuiStatusBarDarkMode(MainActivity.instance, true);
//        StateBarUtil.setMeizuStatusBarDarkIcon(MainActivity.instance, true);
        StateBarUtil.setStatusTranslater(MainActivity.instance, true);
//        StateBarUtil.setStatusTranslater(mActivity, true);
        StateBarUtil.setMiuiStatusBarDarkMode(MainActivity.instance, true);
        StateBarUtil.setMeizuStatusBarDarkIcon(MainActivity.instance, true);

    }

    @Override
    public boolean setSwipeBackEnable() {
        return false;
    }
}


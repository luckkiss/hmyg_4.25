package com.hldj.hmyg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Keep;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.M.AddressBean;
import com.hldj.hmyg.Ui.Eactivity3_0;
import com.hldj.hmyg.Ui.StoreActivity_new;
import com.hldj.hmyg.Ui.friend.bean.tipNum.TipNum;
import com.hldj.hmyg.Ui.friend.bean.tipNum.TipNumType;
import com.hldj.hmyg.Ui.friend.child.MarchingPurchaseActivity;
import com.hldj.hmyg.Ui.friend.child.PhoneLogActivity;
import com.hldj.hmyg.Ui.miaopu.PendingActivity;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.application.StateBarUtil;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.base.CommonPopupWindow;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.base.rxbus.annotation.Subscribe;
import com.hldj.hmyg.base.rxbus.event.EventThread;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.bean.StoreGsonBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.me.FootMarkActivity;
import com.hldj.hmyg.saler.AddAdressActivity;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.saler.StoreSettingActivity;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.hldj.hmyg.widget.SaveSeedingBottomLinearLayout;
import com.hy.utils.SpanUtils;
import com.hy.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bingoogolapple.badgeview.BGABadgeLinearLayout;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static com.hldj.hmyg.me.AttentionActivity.getCompanyOption;


/**
 * 收藏夹  界面
 */
public class DActivity_new_mp extends BaseMVPActivity implements View.OnClickListener {

    private View headView;
    //    private View footerView;
    private ComonShareDialogFragment.ShareBean shareBean;
    private View head_parent;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_d_new_mp;
    }

    @ViewInject(id = R.id.btn_xzmp, click = "onClick")
        /* 动态添加的  view  无法注入 headview  */
            ImageView btn_xzmp;
    @ViewInject(id = R.id.btn_jrdp, click = "onClick")
        /* 动态添加的  view  无法注入 headview  */
            ImageView btn_jrdp;

    //    @ViewInject(id = R.id.logo, click = "onClick")
    /* 动态添加的  view  无法注入 headview  */
    ImageView logo;


//    @ViewInject(id = R.id.djck, click = "onClick")
//    TextView djck;

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycle;

    @ViewInject(id = R.id.toolbar_left_icon)
    ImageView toolbar_left_icon;

    @ViewInject(id = R.id.toolbar_right_icon, click = "onClick")
    ImageView toolbar_right_icon;

    @ViewInject(id = R.id.toolbar_right_text, click = "onClick")
    TextView toolbar_right_text;


    public static final int image_id = 16679496;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_xzmp:
                D.i("========新增圃==========");
                AddAdressActivity.start2Activity(mActivity, null);
                break;
            case R.id.btn_jrdp:
                D.i("========进入店铺==========");
                StoreActivity_new.start2Activity(mActivity, MyApplication.getUserBean().storeId);
                break;
            case R.id.sptv_program_do_search:
                recycle.onRefresh();
                break;
            case R.id.filter:
//                ToastUtil.showShortToast("search1");
                new CommonPopupWindow.Builder(mActivity)
                        .setBgType(CommonPopupWindow.TYPE_WHITE_UP_RIGHT_TOP)
                        .setOutsideTouchable(true)
                        .bindLayoutId(R.layout.item_mp_filter)
                        .setCovertViewListener((viewRoot, popupWindow) -> {

                            viewRoot.findViewById(R.id.search_mm).setOnClickListener(v2 -> {
                                // 搜索苗木
                                ManagerSplitListActivity_new.start2Activity(mActivity, "");

//                                ToastUtil.showShortToast("11" + headView.findViewById(R.id.include_search));
                                getView(R.id.include_search).setVisibility(View.GONE);
//                                    getView().setVisibility(View.GONE);
                                popupWindow.dismiss();


                            });
                            viewRoot.findViewById(R.id.search_mp).setOnClickListener(v2 -> {
                                //搜索苗圃
//                                    getView(R.id.include_search).setVisibility(View.VISIBLE);
//                                ToastUtil.showShortToast("22" + headView.findViewById(R.id.include_search));
                                getView(R.id.include_search).setVisibility(View.VISIBLE);
                                popupWindow.dismiss();

                            });


                        }).build().simpleShow(getView(R.id.filter));
                break;
            case R.id.logo:
            case R.id.head_parent:
                D.i("========店铺设置==========");
                StoreSettingActivity.start2Activity(mActivity);
                break;
            case R.id.toolbar_right_icon:
//                ToastUtil.showLongToast("search");
                ManagerSplitListActivity_new.start2Activity(mActivity, "");
//                ManagerListActivity_new.start2Activity(mActivity);
                break;
            case R.id.toolbar_right_text:
            case image_id:
                if (shareBean == null) {
                    ToastUtil.showLongToast("对不起,分享数据获取失败~_~");

                    return;
                }

//                ComonShareDialogFragment
//                        .newInstance()
//                        .setShareBean(shareBean)
//                        .show(getSupportFragmentManager(), "aaa");
//

                ComonShareDialogFragment.newInstance()
                        .setShareBean(shareBean)
                        .show(mActivity.getSupportFragmentManager(), "分享求购");
                break;
        }
    }


    private void createShareBean(StoreGsonBean.DataBean.StoreBean store, String headImage) {
        shareBean = new ComonShareDialogFragment.ShareBean(
                "qqq",
                store.shareContent,
                "https://blog.csdn.net/dqcfkyqdxym3f8rb0/article/details/80252828",
//                store.shareUrl,
                "https://ss.csdn.net/p?https://mmbiz.qpic.cn/mmbiz_jpg/BnSNEaficFAZHNgJvEn6wU3HjZK9icJfibUPSS6N8FyGM5fyrxVf1S9ufxV9kXIcQ3LojexL3UU2sJlhbXY4bW2dg/640?wx_fmt=jpeg",
                "https://blog.csdn.net/dqcfkyqdxym3f8rb0/article/details/80252828"
        );
    }

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);
        RxRegi();
        /* 由于注入是在 添加头部之前 。可能会找不到。需要手动 添加一个头部。再injection  不然会导致空指针 →_→  猜测*/
        initCoreRecycleView();


        TextView text_text = getView(R.id.text_text);


        SpanUtils spanUtils = new SpanUtils();


        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ToastUtil.showShortToast("事件触发了");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(false);
            }
        };


        text_text.setMovementMethod(LinkMovementMethod.getInstance());

//                .append("测试空格").appendSpace(30, Color.LTGRAY).appendSpace(50, Color.GREEN).appendSpace(100).appendSpace(30, Color.LTGRAY).appendSpace(50, Color.GREEN)
//                .create());


//        spanUtils  .append("from")
//                .setForegroundColor(mActivity.getResources().getColor(R.color.main_color))
//                .append("test clickspan")
//                .setClickSpan(clickableSpan).append(": 134654646" )
//                .append("test clickspan ")
//        ;
        text_text.setVisibility(View.GONE);
//        text_text.setText(spanUtils.create());


//        FinalActivity.initInjectedView(this, recycle);


        configtoolbar_left_icon();


//        toolbar_left_icon.setOnClickListener(v -> {
//            StoreActivity_new.start2Activity(mActivity, MyApplication.getUserBean().storeId);
//        });

        toolbar_right_icon.setVisibility(View.INVISIBLE);
        toolbar_right_icon.setImageResource(R.drawable.sousuo);
        toolbar_right_text.setVisibility(View.VISIBLE);
        toolbar_right_text.setText("");


        toolbar_right_text.setMaxWidth(MyApplication.dp2px(mActivity, 28));
        toolbar_right_text.setMinWidth(MyApplication.dp2px(mActivity, 1));
        toolbar_right_text.setMinHeight(MyApplication.dp2px(mActivity, 1));
        toolbar_right_text.setMaxHeight(MyApplication.dp2px(mActivity, 28));
        toolbar_right_text.setBackground(getResources().getDrawable(R.drawable.ic_share));


        LinearLayout linearLayout = ((LinearLayout) toolbar_right_text.getParent());
        linearLayout.removeView(toolbar_right_text);
        ImageView imageView = new ImageView(mActivity);
        imageView.setMinimumWidth(MyApplication.dp2px(mActivity, 20));
        imageView.setMaxHeight(MyApplication.dp2px(mActivity, 20));
        imageView.setPadding(MyApplication.dp2px(mActivity, 3), MyApplication.dp2px(mActivity, 3), MyApplication.dp2px(mActivity, 3), MyApplication.dp2px(mActivity, 3));
//        ViewGroup.MarginLayoutParams linearLayout1 = new LinearLayout.LayoutParams(mActivity, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MyApplication.dp2px(mActivity, 18), MyApplication.dp2px(mActivity, 18));
        params.setMargins(MyApplication.dp2px(mActivity, 8), MyApplication.dp2px(mActivity, 5), MyApplication.dp2px(mActivity, 3), MyApplication.dp2px(mActivity, 0));


        imageView.setLayoutParams(params);
        imageView.setPadding(19, 19, 19, 19);
        imageView.setBackgroundResource(R.drawable.fenxiang);
        imageView.setId(image_id);
        imageView.setOnClickListener(this);

        linearLayout.addView(imageView);


//        text_text.setOnClickListener(v -> {
//            SpanActivity.start(mActivity);
//        });
//        djck.setOnClickListener(v -> {
//                 /* 苗圃管理 */
//            D.i("========苗圃管理==========");
//            ManagerListActivity_new.start2Activity(mActivity);
//        });

    }

    private void configtoolbar_left_icon() {
        toolbar_left_icon.setVisibility(View.GONE);

    }

    @Override
    public void initData() {

    }


    int count = 0;

    private void initCoreRecycleView() {

        int headViewId = R.layout.item_head_d_new_mp;

        recycle.init(new BaseQuickAdapter<AddressBean, BaseViewHolder>(R.layout.item_d_new_mp) {
            @Override
            public void convert(BaseViewHolder helper, AddressBean item) {
//                helper.convertView.setOnClickListener(v -> {
//                    ToastUtil.showLongToast("aaa");
//                    D.i("========苗圃管理==========");
////                    ManagerListActivity_new.start2Activity(mActivity);
//                    ManagerSplitListActivity_new.start2Activity(mActivity);
//                });

                D.i("========苗圃管理==========" + item.fullAddress);

                doConvert(helper, item, mActivity);


            }
        })

                .openLoadMore(20, page -> {

                    if (没有开通店铺()) {
                        recycle.selfRefresh(false);
                        ToastUtil.showLongToast("未开通企业");
                        return;
                    }

                    if (recycle.getAdapter().getHeaderLayoutCount() == 0) {
                        recycle.addHeaderView(headView);
                    }

                    count = 0;
                    requestData(20, page);
                    requestHead();

                })
                .setDefaultEmptyView(true, "下一步", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StoreSettingActivity.start2Activity(mActivity);
                    }
                })
                .setEmptyText("请先完善企业信息")
                .openRefresh();


        //添加自定义分割线
//        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(this,R.drawable.dot_red));
//        recycle.getRecyclerView().addItemDecoration(divider);

//        recycle.getRecyclerView().addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));


//        recycle.getRecyclerView().addItemDecoration(new RecycleViewDivider(
//                mActivity, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.gray_bg_ed)));
//

//        recycle.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                if (count == 0) {
//                    outRect.set(0, 0, 0, 0);
//                } else {
//                    outRect.set(0, 0, 0, 0);
//                }
//                count++;
//                parent.setBackgroundResource(R.color.gray_bg_ed);
//            }
//        });


//        headView = createHeadView();
//        headView = LayoutInflater.from(mActivity).inflate(R.layout.item_head_d_new_mp, null);
        headView = getLayoutInflater().inflate(R.layout.item_head_d_new_mp, null);

//        footerView = getLayoutInflater().inflate(R.layout.item_footer_d_new_mp, null);
//        footerView = LayoutInflater.from(mActivity).inflate(R.layout.item_footer_d_new_mp,null);
        recycle.addHeaderView(headView);
//        recycle.addFooterView(footerView);
        recycle.onRefresh();


    }

    private boolean 没有开通店铺() {

        String storeId = MyApplication.Userinfo.getString("storeId", "");
        if (TextUtils.isEmpty(storeId)) {
            ToastUtil.showLongToast(storeId);
            D.i("store id  is = : " + storeId);


            recycle.getAdapter().removeAllHeaderView();
            recycle.getAdapter().removeAllFooterView();
            recycle.getAdapter().getData().clear();
            recycle.getAdapter().notifyDataSetChanged();

            btn_xzmp.setVisibility(View.GONE);

            return true;
        }
        btn_xzmp.setVisibility(View.VISIBLE);
        return false;
    }

    /* 请求店铺信息 */
    private void requestHead() {


        new BasePresenter()
                .doRequest("admin/store/getStore", new HandlerAjaxCallBack(mActivity) {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {
                        Log.i("----------", "onRealSuccess11: ");

                        recycle.selfRefresh(false);
//                        public StoreGsonBean.DataBean.StoreBean store ;
//                        public List<TipNum> tipList  ;
                        StoreGsonBean.DataBean.StoreBean storeBean = gsonBean.getData().store;
                        List<TipNum> tipNums = gsonBean.getData().tipList;

                        setText(getView(R.id.title_left), new SpanUtils()
                                .append("我的苗圃").setForegroundColor(getColorByRes(R.color.main_color))
                                .append(String.format("  (共%d个苗圃)", gsonBean.getData().nurseryCount)).setForegroundColor(getColorByRes(R.color.text_color999)).setFontSize(12, true)
                                .create());
                        initHeadView(headView, storeBean, tipNums, gsonBean.getData().identity);


                    }

                    @Override
                    public void onStart() {
                        super.onStart();
//                        recycle.removeAllFooterView();
//                        recycle.addFooterView(footerView);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                        D.i("--------------" + t.getMessage());

                    }
                });


    }

    private void initHeadView(View headView, StoreGsonBean.DataBean.StoreBean storeBean, List<TipNum> tipNums, boolean isPass) {
        //           <cn.bingoogolapple.badgeview.BGABadgeLinearLayout
//        android:id="@+id/ll_show_num1"
        createShareBean(storeBean, storeBean.bannerUrl);

        int head_id = R.layout.item_head_d_new_mp;
        BGABadgeLinearLayout ll_show_num1 = headView.findViewById(R.id.ll_show_num1);
        BGABadgeLinearLayout ll_show_num2 = headView.findViewById(R.id.ll_show_num2);
        BGABadgeLinearLayout ll_show_num3 = headView.findViewById(R.id.ll_show_num3);
        BGABadgeLinearLayout ll_show_num4 = headView.findViewById(R.id.ll_show_num4);

        ll_show_num1.hiddenBadge();
        ll_show_num2.hiddenBadge();
        ll_show_num3.hiddenBadge();
        ll_show_num4.hiddenBadge();


        ll_show_num1.setOnClickListener(v -> FootMarkActivity.start(mActivity));
        ll_show_num2.setOnClickListener(v -> PhoneLogActivity.start(mActivity));
        ll_show_num3.setOnClickListener(v -> MarchingPurchaseActivity.start(mActivity));
        ll_show_num4.setOnClickListener(v -> PendingActivity.start(mActivity, ""));//处理提示


        TextView textView = (TextView) headView.findViewById(R.id.name);
        textView.setText(TextUtils.isEmpty(storeBean.name) ? "未填写" : storeBean.name);

        ImageView logo = (ImageView) headView.findViewById(R.id.logo);
        ImageLoader.getInstance().displayImage(storeBean.logoUrl, logo, getCompanyOption());
        FinalBitmap.create(mActivity).display(logo, storeBean.logoUrl);

//        boolean isPass = storeBean.attrData.store_identity.equals(CompanyIdentityStatus.pass.getEnumValue());
//        boolean isPass = storeBean.identity;

        headView.findViewById(R.id.store_identity).setVisibility(isPass ? View.VISIBLE : View.GONE);
        //通过认证
        TipNumType.setCount(tipNums, headView);
//        setStyleAndText(ll_show_num1, "22");
//        setStyleAndText(ll_show_num3, "");
        // 头部控件初始化   点击事件 由框架注入
        logo = headView.findViewById(R.id.logo);
        head_parent = headView.findViewById(R.id.head_parent);
//        btn_xzmp = footerView.findViewById(R.id.btn_xzmp);
        logo.setOnClickListener(this);
        head_parent.setOnClickListener(this);
        btn_xzmp.setOnClickListener(this);
        btn_jrdp.setOnClickListener(this);


//        headView.findViewById(R.id.include_search).setVisibility(View.GONE);
        headView.findViewById(R.id.sptv_program_do_search).setOnClickListener(this);
        EditText editText = headView.findViewById(R.id.et_program_serach_text);
        editText.setHint("苗圃名称/联系人/联系电话/地址");
        headView.findViewById(R.id.filter).setOnClickListener(this);


    }


    /* recycle view  内容    */
    private void doConvert(BaseViewHolder helper, AddressBean item, NeedSwipeBackActivity mActivity) {
        int layout_id = R.layout.item_d_new_mp;


        helper
                .setText(R.id.title, String.format("%s.%s", (helper.getAdapterPosition()), item.name))
                .setText(R.id.textView64, new SpanUtils()
                        .append("在售  ")
                        .append("(" + item.onShelfJson + ")").setForegroundColor(getColorByRes(R.color.main_color)).setAlign(Layout.Alignment.ALIGN_NORMAL)

                        .append("  审核中  ")
                        .append("(" + item.unauditJson + ")").setForegroundColor(getColorByRes(R.color.main_color)).setAlign(Layout.Alignment.ALIGN_NORMAL)

                        .append("  下架  ")
                        .append("(" + item.downShelfJson + ")").setForegroundColor(getColorByRes(R.color.main_color)).setAlign(Layout.Alignment.ALIGN_NORMAL)
                        .create())
        ;


        helper.addOnClickListener(R.id.bj, v -> {
            AddAdressActivity.start2Activity(mActivity, item);
        });

        helper.addOnClickListener(R.id.mmgl, v -> {
//            ToastUtil.showLongToast("aaa");
            D.i("========苗圃管理==========");
//                    ManagerListActivity_new.start2Activity(mActivity);

            SaveSeedingBottomLinearLayout.addressBean = item;


            ManagerSplitListActivity_new.start2ActivityUpdate(mActivity, item.id, item.rePublishDate);


        });

        helper.getConvertView().setOnClickListener(v -> {
            SaveSeedingBottomLinearLayout.addressBean = item;
            ManagerSplitListActivity_new.start2Activity(mActivity, item.id);
        });


    }

    private void requestData(int pageSize, int page) {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<AddressBean>>>>() {
        }.getType();
        new BasePresenter()
                .putParams(ConstantParams.searchKey, getSearchKey())
                .putParams(ConstantParams.pageIndex, page + "")
                .putParams(ConstantParams.pageSize, "" + pageSize)
                .putParams("storeId", MyApplication.getUserBean().storeId)
                .doRequest("admin/nursery/listByStore", new HandlerAjaxCallBackPage<AddressBean>(mActivity, type) {
                    @Override
                    public void onRealSuccess(List<AddressBean> addressBeans) {
                        recycle.getAdapter().addData(addressBeans);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        recycle.selfRefresh(false);
                    }


                });


    }

    private View createHeadView() {
        View headView = LayoutInflater.from(mActivity).inflate(R.layout.item_head_d_new_mp, null);

        return headView;
    }


    public static void start2Activity(Activity context) {
        Intent intent = new Intent(context, DActivity_new_mp.class);
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


    @Override
    public String setTitle() {
        return "我的苗圃";
    }


    /**
     * 监听登录成功  刷新
     */


    private Disposable subscription;

    //订阅
    @Keep
    @Subscribe(tag = 5, thread = EventThread.MAIN_THREAD)
    private void dataBinding11(Eactivity3_0.OnlineEvent event) {
        D.e("======Rx=======" + event.toString());
        //加载头像
        subscription = Observable.just(event)
                .filter(event1 -> event.isOnline)
                .map((Function<Eactivity3_0.OnlineEvent, Object>) event12 -> event12.isOnline)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(500, TimeUnit.MILLISECONDS)
                .subscribe((b) -> {
                    recycle.onRefresh();

                    D.i("--------苗木界面刷新了--------");
                    D.i("--------苗木界面刷新了--------");
                    D.i("--------苗木界面刷新了--------");
                    D.i("--------苗木界面刷新了--------");
                    D.i("--------苗木界面刷新了--------");
                    D.i("--------苗木界面刷新了--------");
                    D.i("--------苗木界面刷新了--------");

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        D.w("=============Consumer=监听登录成功报错===========" + throwable.getMessage());
                    }
                });

//        Observable.timer(1, TimeUnit.SECONDS)
//                .filter(new Observable<OnlineEvent>() {
//                    @Override
//                    protected void subscribeActual(Observer<? super OnlineEvent> observer) {
//
//                    }
//                })
//                .subscribe(aa->{
//
//        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isDisposed())
            subscription.dispose();

        RxUnRegi();
    }

    public void RxRegi() {
        RxBus.getInstance().register(this);
    }

    public void RxUnRegi() {
        RxBus.getInstance().unRegister(this);
    }


    public static final int refresh = 100;

    //订阅
    @Keep
    @Subscribe(tag = refresh, thread = EventThread.MAIN_THREAD)
    private void refreshCount(Eactivity3_0.OnlineEvent event) {
        D.e("======Rx=======" + event.toString());
        requestHead();
        D.i("--------数量刷新了--------");
        D.i("--------数量刷新了--------");
        D.i("--------数量刷新了--------");

    }


    public String getSearchKey() {
        if (getView(R.id.et_program_serach_text) != null) {
            return getText(getView(R.id.et_program_serach_text));
        } else {
            return "";
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConstantState.ADD_SUCCEED || resultCode == ConstantState.CHANGE_DATES || resultCode == ConstantState.DELETE_SUCCEED) {
            recycle.onRefresh();
        }
    }
}


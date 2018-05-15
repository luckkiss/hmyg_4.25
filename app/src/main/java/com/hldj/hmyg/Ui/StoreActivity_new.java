package com.hldj.hmyg.Ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.google.zxing.WriterException;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.GalleryImageActivity;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.M.QueryBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.Ui.friend.child.CenterActivity;
import com.hldj.hmyg.Ui.friend.child.DetailActivity;
import com.hldj.hmyg.Ui.storeChild.StoreDetailFragment;
import com.hldj.hmyg.Ui.storeChild.StoreHomeFragment;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.application.StateBarUtil;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.StoreGsonBean;
import com.hldj.hmyg.contract.StoreContract;
import com.hldj.hmyg.model.StoreModel;
import com.hldj.hmyg.presenter.StorePresenter;
import com.hldj.hmyg.saler.Adapter.FragmentPagerAdapter_TabLayout;
import com.hldj.hmyg.util.AppBarStateChangeListener;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.widget.ComonShareDialogFragment;
import com.hldj.hmyg.widget.SharePopupWindow;
import com.hldj.hmyg.widget.UPMarqueeView;
import com.hy.utils.ToastUtil;
import com.white.utils.FileUtil;
import com.zxing.encoding.EncodingHandler;

import net.tsz.afinal.FinalBitmap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.hldj.hmyg.StoreActivity.getRoundCornerImage;

public class StoreActivity_new extends BaseMVPActivity<StorePresenter, StoreModel> implements StoreContract.View {

    private Disposable disposable;

    TextView miao;
    private String persiId = "";

    @Override
    public int bindLayoutID() {
        return R.layout.activity_store_mvp;
    }

    private String img_path = "";

    private String shareUrl = "";

    FinalBitmap bitmap;
    private ArrayList<Pic> ossImagePaths = new ArrayList<Pic>();


    private ValueAnimator createDropAnimator(final View view, int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }


    private void animateOpen(View view) {
//        count
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mPresenter.count <= 0) return;
                view.setVisibility(View.VISIBLE);


                miao.setText(filterColor("TA的苗木圈有" + mPresenter.count + "条动态>>>", mPresenter.count + "", R.color.price_orige));
                ValueAnimator animator = createDropAnimator(view, 0, 100);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        miao.setText(filterColor("TA的苗木圈有" + mPresenter.count + "条动态>>>", mPresenter.count + "", R.color.price_orige));
                    }
                });
                animator.start();
            }
        }, 1500);

    }

    //step 1   初始化 控件
    @Override
    public void initView() {


//        StartBarUtils.FlymeSetStatusBarLightMode(getWindow(),false);
//        StartBarUtils.MIUISetStatusBarLightMode(getWindow(),false);

        miao = (TextView) findViewById(R.id.miao);


        miao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CenterActivity.start(mActivity, persiId);
            }
        });

//        (((ViewGroup) getView(R.id.head_parent))).setBackgroundColor(getColorByRes(R.color.trans));

        getView(R.id.head_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreDetailActivity.start(mActivity, getStoreID());
            }
        });
        AppBarLayout layout = getView(R.id.app_bar);
        layout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                if (state == State.EXPANDED) {
                    //展开状态
                    getView(R.id.tv_store_name).setVisibility(View.GONE);
                    StateBarUtil.setStatusTranslater(mActivity, false);//变白

//                     getView(R.id.btn_back).setSelected(false);


                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    getView(R.id.tv_store_name).setVisibility(View.VISIBLE);
//                    StateBarUtil.setStatusTranslater(mActivity, true);//变黑
//                    getView(R.id.btn_back).setSelected(true);
                } else {
                    //中间状态
//                    StateBarUtil.setStatusTranslater(mActivity, true);//变黑
//                    getView(R.id.btn_back).setSelected(true);
                }
            }
        });


        bitmap = FinalBitmap.create(mActivity);
        bitmap.configLoadfailImage(R.drawable.logo);

    }


    @Override
    protected void initListener() {

        /*后退*/
        getView(R.id.btn_back).setOnClickListener(v -> finish());

        /*分享*/
        getView(R.id.iv_fenxiang).setOnClickListener(v -> {
            D.d("分享");
//            ToastUtil.showShortToast("share");
            if (null == shareBean) {
                ToastUtil.showShortToast("获取分享数据失败");
            }
            SharePopupWindow window = new SharePopupWindow(mActivity, shareBean);
            window.showAsDropDown(getView(R.id.iv_fenxiang));
        });

        /*二维码 */
        getView(R.id.iv_erweima).setOnClickListener(v -> {
            setShareUrl(shareUrl);
            if (ossImagePaths.size() > 0) {
                GalleryImageActivity.startGalleryImageActivity(mActivity, 0, ossImagePaths);
            }
        });


        SuperTextView sptv_store_home = getView(R.id.sptv_store_home);
        SuperTextView sptv_store_detail = getView(R.id.sptv_store_detail);
        switchColor(sptv_store_home, sptv_store_detail, 0);

        /*切换 店铺首页*/
        sptv_store_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewPager().setCurrentItem(0, true);
                switchColor(sptv_store_home, sptv_store_detail, 0);
            }
        });

        /*切换 店铺详情*/
        sptv_store_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewPager().setCurrentItem(1, true);
                switchColor(sptv_store_home, sptv_store_detail, 1);
            }
        });


        getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getViewPager().setCurrentItem(position, true);
                switchColor(sptv_store_home, sptv_store_detail, position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


//      <com.hldj.hmyg.widget.UPMarqueeView
//    android:id="@+id/upview1"

    //step 2    网络请求
    @Override
    public void initVH() {

        showLoading();
        // 使用rx java  尝试嵌套 请求 网络
        /**
         * {@link StoreActivity_new.QueryBean}
         */
        // 先获取 index  数据   在通过index  中的数据  获取    列表信息
        //step 2.1    网络请求
     /*请求头部数据*///传入左边  对象   转换  右边对象
        disposable = mPresenter.getIndexData()/*请求头部数据*/
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, String>() {//传入左边  对象   转换  右边对象
                    @Override
                    public String apply(@NonNull String persion_id) throws Exception {
                        Log.i("===4", "subscribe: " + Thread.currentThread().getName());
                        D.e("=======getIndexData=======" + persion_id);
                        return persion_id;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(@NonNull String persion_id) throws Exception {
                                   Log.i("===5", "subscribe: " + Thread.currentThread().getName());

                                   persiId = persion_id;

//                                   animateOpen(miao);
//                                   miao.setVisibility(View.VISIBLE);
//                        miao.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                miao.setVisibility(View.VISIBLE);
//                            }
//                        });


                                   ArrayList<Fragment> fragments = new ArrayList<Fragment>() {
                                       {
                                           add(StoreHomeFragment.Instance(persion_id));
                                           add(StoreDetailFragment.Instance(getStoreID()));
                                       }
                                   };

                                   ArrayList<String> titles = new ArrayList<String>() {
                                       {
                                           add("titile1");
                                           add("titile2");
                                       }
                                   };
                                   getViewPager().setAdapter(new FragmentPagerAdapter_TabLayout(getSupportFragmentManager(), titles, fragments));
                               }
                           }

                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                hindLoading();
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                hindLoading();

                                ToastUtil.showShortToast("complete");
                            }
                        }, new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {

                            }
                        }

                );


        /**
         *
         params.put("id", code2);
         */


    }


    @Override
    public void showErrir(String erMst) {
        if ("参数错误".equals(erMst)) {
            erMst = "参数错误，可能店铺没有开通~_~ ";
        } else {
            super.showErrir(erMst);
        }
        new Handler().postDelayed(() -> {
            setResult(ConstantState.STORE_OPEN_FAILD);//商店打开失败
            finish();
//                                    StoreSettingActivity.start2Activity(mActivity);
        }, 2000);
    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    @Override
    public String getStoreID() {
        //这里可以进行跳转判断
        return getIntent().getStringExtra("code");
    }


    public ViewPager getViewPager() {
        return getView(R.id.vp_store_content);
    }

    @Override
    public void initUpMarqueeView(List<Moments> moments) {
//        moments = null;

//        ToastUtil.showLongToast(moments.size() + "   moment size  is this");
        UPMarqueeView upview1 = (UPMarqueeView) findViewById(R.id.upview1);

        if (moments == null || moments.size() == 0) {
            getView(R.id.line1).setVisibility(View.GONE);
            getView(R.id.pa).setVisibility(View.GONE);
        } else {
            upview1.setViews(getViewsByDatas(mActivity, moments));

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


    public void initStoreData(List<BPageGsonBean.DatabeanX.Pagebean.Databean> list) {
//        store_recycle.getAdapter().addData(list);
    }

    String typeListJson = "";

    public String getTypeListJson() {
        return typeListJson;
    }

    List<StoreGsonBean.DataBean.TypeListBean> typeList;

    @Override
    public void initIndexBean(StoreGsonBean.DataBean indexBean) {

        typeListJson = GsonUtil.Bean2Json(indexBean.typeList);

        getView(R.id.store_identity).setVisibility(indexBean.identity ? View.VISIBLE : View.GONE);


        TextView tvTitle = getView(R.id.tv_store_name);
        tvTitle.setText(indexBean.store.name);
        tvTitle.setVisibility(View.GONE);

        TextView tv_infot1 = getView(R.id.name);
        tv_infot1.setText(indexBean.store.name);
        TextView tv_infot2 = getView(R.id.brower_num);
        tv_infot2.setText("浏览量  " + indexBean.visitsCount + "");
//        TextView tv_infot3 = getView(R.id.tv_infot3);
//        tv_infot3.setText(indexBean.owner.displayPhone);
        //拨打电话
        if (!TextUtils.isEmpty(indexBean.owner.displayPhone)) {
            getView(R.id.fab).setOnClickListener(v -> FlowerDetailActivity.CallPhone(indexBean.owner.displayPhone, mActivity));
            getView(R.id.logo).setOnClickListener(v -> FlowerDetailActivity.CallPhone(indexBean.owner.displayPhone, mActivity));
        }


        ImageView sptv_store_home_head = getView(R.id.logo);


        bitmap.display(sptv_store_home_head, indexBean.owner.headImage);


        ViewGroup.LayoutParams l_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        WindowManager wm = this.getWindowManager();
//        l_params.height = (int) (wm.getDefaultDisplay().getWidth() * 1 / 1.9);
//        getView(R.id.iv_store_banner).setLayoutParams(l_params);
//        bitmap.display(getView(R.id.iv_store_banner), indexBean.store.appBannerUrl);

        shareUrl = indexBean.store.shareUrl;
        /**
         *   SharePopupWindow window = new SharePopupWindow(mActivity, new ComonShareDialogFragment.ShareBean(
         "title",
         "text",
         "desc",
         "https://o1wh05aeh.qnssl.com/image/view/app_icons/9301261c8a35492659faf52d2cb95b63/120",
         "https://www.pgyer.com/wu1a"
         ));
         */
        createShareBean(indexBean);
    }

    private ComonShareDialogFragment.ShareBean shareBean;

    private void createShareBean(StoreGsonBean.DataBean indexBean) {
        shareBean = new ComonShareDialogFragment.ShareBean(
                indexBean.store.shareTitle,
                indexBean.store.shareContent,
                indexBean.store.shareUrl,
                indexBean.owner.headImage,
                indexBean.store.shareUrl
        );
    }

    @Override
    public QueryBean getQueryBean() {
        return null;
    }


    public void switchColor(SuperTextView superTextView_home, SuperTextView superTextView_detail, int state) {
        if (state == 0) {
            superTextView_home.setDrawable(getResources().getDrawable(R.drawable.dpsy));
            superTextView_detail.setDrawable(getResources().getDrawable(R.drawable.dpxq_h));
            superTextView_home.setTextColor(getResources().getColor(R.color.main_color));
            superTextView_detail.setTextColor(getResources().getColor(R.color.text_color));
        } else {
            superTextView_home.setDrawable(getResources().getDrawable(R.drawable.dpsy_h));
            superTextView_detail.setDrawable(getResources().getDrawable(R.drawable.dpxq_lv));
            superTextView_home.setTextColor(getResources().getColor(R.color.text_color));
            superTextView_detail.setTextColor(getResources().getColor(R.color.main_color));
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void setShareUrl(String shareUrl) {

        if (TextUtils.isEmpty(shareUrl)) {
            ToastUtil.showShortToast("对不起，无分享地址");
            return;
        }
        try {
            Bitmap qrCodeBitmap = EncodingHandler.createQRCode(shareUrl, 350);
            qrCodeBitmap = getRoundCornerImage(qrCodeBitmap, 3);
            try {
                img_path = FileUtil.saveMyBitmap("commenQcCode", qrCodeBitmap);
                if (!"".equals(img_path)) {
                    ossImagePaths.add(new Pic("", false, img_path, 0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


    public static List<View> getViewsByDatas(Activity aActivity, List<Moments> data) {

//        if (data == null) {
//            ToastUtil.showLongToast("苗木圈列表为空");
//            return new ArrayList<>();
//        }

        List<View> views = new ArrayList<>();
        for (int i = 0; i < data.size(); i = i + 1) {
            final int position = i;
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(aActivity).inflate(R.layout.item_home_mmgg, null);
            //初始化布局的控件
            SuperTextView tv1 = (SuperTextView) moreView.findViewById(R.id.tv_taggle1);
            TextView right_text1 = (TextView) moreView.findViewById(R.id.right_text1);
            TextView right_text2 = (TextView) moreView.findViewById(R.id.right_text2);
            SuperTextView tv2 = (SuperTextView) moreView.findViewById(R.id.tv_taggle2);
            /**
             * 设置监听
             */
            moreView.findViewById(R.id.tv_taggle1).setOnClickListener(view -> {
//                String url = Data.getNotices_and_news_url_only_by_id(data.get(position).id);
//                D.e("url=" + url);
//                NoticeActivity_detail.start2Activity(aActivity, url);
//                ToastUtil.showLongToast("点击事件");
                DetailActivity.start(aActivity, data.get(position).id);
            });
            /**
             * 设置监听
             */
            moreView.findViewById(R.id.tv_taggle2).setOnClickListener(view -> {
//              String url = Data.getNotices_and_news_url_only_by_id(data.get(position + 1).id);
//              D.e("url=" + url);
//                NoticeActivity_detail.start2Activity(aActivity, url);
//                ToastUtil.showLongToast("点击事件");
                DetailActivity.start(aActivity, data.get(position + 1).id);
            });
            //进行对控件赋值
            tv1.setText(data.get(i).content);
            right_text1.setText(data.get(i).timeStampStr);


            if (data.get(i).type.equals("supply")) {//供应
                tv1.setShowState(true);
                tv1.setDrawable(aActivity.getResources().getDrawable(R.drawable.home_cj_gg_gy));
                tv1.setPadding(MyApplication.dp2px(aActivity, 40), 0, 0, 0);


            } else {
                tv1.setDrawable(aActivity.getResources().getDrawable(R.drawable.home_cj_gg_qg));
                tv2.setPadding(MyApplication.dp2px(aActivity, 40), 0, 0, 0);
                tv1.setShowState(true);
            }

            if (data.size() > i + 1) {
                //因为淘宝那儿是两条数据，但是当数据是奇数时就不需要赋值第二个，所以加了一个判断，还应该把第二个布局给隐藏掉
                tv2.setText(data.get(i + 1).content);
                right_text2.setText(data.get(i + 1).timeStampStr);

                if (data.get(i + 1).type.equals("supply")) {
                    tv2.setShowState(true);
                    tv2.setDrawable(aActivity.getResources().getDrawable(R.drawable.home_cj_gg_gy));
                    tv2.setPadding(MyApplication.dp2px(aActivity, 40), 0, 0, 0);
                } else {
                    tv2.setDrawable(aActivity.getResources().getDrawable(R.drawable.home_cj_gg_qg));
                    tv2.setPadding(MyApplication.dp2px(aActivity, 40), 0, 0, 0);
                    tv2.setShowState(true);
                }
            } else {
                moreView.findViewById(R.id.tv_taggle2).setVisibility(View.GONE);
                moreView.findViewById(R.id.right_text2).setVisibility(View.GONE);
            }


            //添加到循环滚动数组里面去
            views.add(moreView);
        }

        return views;
    }

}

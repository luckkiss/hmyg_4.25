package com.hldj.hmyg;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Keep;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBackPage;
import com.hldj.hmyg.CallBack.search.IBrandShopMoreSearch;
import com.hldj.hmyg.M.AddressBean;
import com.hldj.hmyg.Ui.StoreActivity_new;
import com.hldj.hmyg.Ui.friend.bean.enums.MomentsType;
import com.hldj.hmyg.Ui.friend.util.FriendUtil;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.CommonListSpinner;
import com.hldj.hmyg.widget.CommonListSpinner1;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;

import java.lang.reflect.Type;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static com.hldj.hmyg.Ui.friend.FriendCycleSearchActivity.setStatusDrable;


/**
 * 品牌店铺 更多 （包含搜索）
 */
@Keep
public class BrandShopMoreActivity extends BaseMVPActivity implements View.OnClickListener, IBrandShopMoreSearch {


    @Override
    public int bindLayoutID() {
        return R.layout.activity_brand_shop_more;
    }


    @ViewInject(id = R.id.iv_view_type, click = "onClick")
    TextView iv_view_type;


    @ViewInject(id = R.id.tv_sort, click = "onClick") //
            SuperTextView tv_filter;
    @ViewInject(id = R.id. tv_filter, click = "onClick")//
            SuperTextView tv_sort;

    @ViewInject(id = R.id.toolbar_left_icon, click = "onClick")
    View toolbar_left_icon;

//    @ViewInject(id = R.id.djck, click = "onClick")
//    TextView djck;

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycle;


    @ViewInject(id = R.id.search_content)
    EditText search_content;

    @Keep
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_icon:
                finish();
                break;
            case R.id.tv_filter:
                doSearchByCity();
                break;
            case R.id.tv_sort:
               doSearchBySort();
                break;
            case R.id.iv_view_type:
                doSearch();
                break;
        }
    }


    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);

        tv_filter.setText("排序");
        tv_sort.setText("地区");

        /* 由于注入是在 添加头部之前 。可能会找不到。需要手动 添加一个头部。再injection  不然会导致空指针 →_→  猜测*/
        initCoreRecycleView();


    }


    @Override
    public void initData() {

    }


    int count = 0;

    private void initCoreRecycleView() {

        int headViewId = R.layout.item_head_d_new_mp;

        recycle.init(new BaseQuickAdapter<AddressBean, BaseViewHolder>(R.layout.item_brand_shop) {
            @Override
            public void convert(BaseViewHolder helper, AddressBean item) {
//                helper.convertView.setOnClickListener(v -> {
//                    ToastUtil.showLongToast("aaa");
//                    D.i("========苗圃管理==========");
////                    ManagerListActivity_new.start2Activity(mActivity);
//                    ManagerSplitListActivity_new.start2Activity(mActivity);
//                });

                D.i("========品牌店铺 渲染==========" + item.fullAddress);

                doConvert(helper, item, mActivity);


            }
        })

                .openLoadMore(20, page -> {

                    requestData(20, page);


                })


                .openRefresh();


//        recycle.addFooterView(footerView);
        recycle.onRefresh();


    }


    /* recycle view  内容    */
    private void doConvert(BaseViewHolder helper, AddressBean item, NeedSwipeBackActivity mActivity) {
        int layout_id = R.layout.item_brand_shop;

        FinalBitmap.create(mActivity)
                .display(helper.getView(R.id.iv_left), "https://avatar.csdn.net/9/7/A/3_zhangphil.jpg");

        FinalBitmap.create(mActivity)
                .display(helper.getView(R.id.iv_center), "https://avatar.csdn.net/9/7/A/3_zhangphil.jpg");

        FinalBitmap.create(mActivity)
                .display(helper.getView(R.id.iv_right), "https://avatar.csdn.net/9/7/A/3_zhangphil.jpg");

        ImageLoader
                .getInstance()
                .displayImage("https://avatar.csdn.net/9/7/A/3_zhangphil.jpg", (ImageView) helper.getView(R.id.head));

        helper.addOnClickListener(R.id.tv_right_top, v -> {
            StoreActivity_new.start2Activity(mActivity, MyApplication.getUserBean().storeId);
        });


    }

    private void requestData(int pageSize, int page) {

        Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<AddressBean>>>>() {
        }.getType();
        new BasePresenter()
                .putParams(ConstantParams.searchKey,  getSearchKey())
//                .putParams(ConstantParams.searchKey, currentType)
                .putParams("content", getSearchKey())
                .putParams(ConstantParams.pageIndex, page + "")
                .putParams(ConstantParams.pageSize, "" + pageSize)
                .putParams("cityCodeList", cityCodeString)// 填写地区    str 【】
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


    public static void start2Activity(Activity context) {
        Intent intent = new Intent(context, BrandShopMoreActivity.class);
        context.startActivity(intent);

    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    @Override
    public String getSearchKey() {
        return search_content.getText().toString();
    }

    @Override
    public void doSearch() {
        recycle.onRefresh();
    }




    private CommonListSpinner1 commonListSpinner1;
    public String cityCodeString = "";
    private static final String TAG = "BrandShopMoreActivity";

    @ViewInject(id = R.id.line)
    public View line;

    @Override
    public void doSearchByCity() {

              /*分类*/
        tv_filter.setTextColor(getColorByRes(R.color.text_color333 ));
                /*地区*/
        tv_sort.setTextColor(getColorByRes(R.color.main_color));


        setStatusDrable(mActivity, tv_sort, tv_filter, 0);

        if (commonListSpinner1 != null) {
            commonListSpinner1.ShowWithHistorys(cityCodeString);
            return;
        }
        commonListSpinner1 = FriendUtil.CreateSortCitySpinner(
                mActivity,
                new FriendUtil.OnSortSelectListener() {
                    @Override
                    public void onSelect(int pos, String key, String value) {
//                                ToastUtil.showLongToast("==============pos=" + pos + "  key=" + key + "  value=" + value);
                        Log.i(TAG, "onSelect: key \n" + key);
                        Log.i(TAG, "onSelect: sub \n" + key.substring(1, key.length() - 1));
                        Log.i(TAG, "onSelect: value \n" + value);
                        Log.i(TAG, "onSelect: sub \n" + value.substring(1, value.length() - 1));

                        if (TextUtils.isEmpty(value.substring(1, value.length() - 1).trim())) {
                            tv_sort.setText("地区");
                            tv_sort.setShowState(true);
                            cityCodeString = "";
                        } else {
                            tv_sort.setText(value.substring(1, value.length() - 1));
                            tv_sort.setShowState(false);
                            cityCodeString = key.substring(1, key.length() - 1);
                        }

                        commonListSpinner1.dismiss();

                    }
                }, new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu menu) {
                        tv_sort.setDrawable(mActivity.getResources().getDrawable(R.drawable.ic_arrow_down_select));
                    }
                },
                line).Show();


    }


    private CommonListSpinner commonListSpinner;
    int possition = 0;
    public String currentType = MomentsType.supply.getEnumValue();
    @Override
    public void doSearchBySort() {

           /*分类*/
        tv_filter.setTextColor(getColorByRes(R.color.main_color));
                /*地区*/
        tv_sort.setTextColor(getColorByRes(R.color.text_color333));

        setStatusDrable(mActivity, tv_filter, tv_sort, 0);

        if (commonListSpinner != null) {
            commonListSpinner.ShowWithPos(possition);
        } else {
            commonListSpinner = FriendUtil.CreateSortSpinner(mActivity, new FriendUtil.OnSortSelectListener() {
                @Override
                public void onSelect(int pos, String key, String value) {
                    possition = pos;
//                            ToastUtil.showLongToast("-----------pos=" + pos + "  key = " + key + "  value = " + value);
//                          if (checkedId == R.id.rb_left) {
//                            viewpager.setCurrentItem(pos);
                    currentType = key;
//                   ("searchKey", currentType, "");
                    tv_filter.setText(value);
                    commonListSpinner.dismiss();
//                            } else if (checkedId == R.id.rb_center) {
//                            viewpager.setCurrentItem(1);
//                            } else if (checkedId == R.id.rb_right) {
//                            viewpager.setCurrentItem(2);
//                            }
                }
            }, new PopupMenu.OnDismissListener() {
                @Override
                public void onDismiss(PopupMenu menu) {
                    tv_filter.setDrawable(mActivity.getResources().getDrawable(R.drawable.ic_arrow_down_select));
                }
            }, line).ShowWithPos(possition);
        }
    }
}


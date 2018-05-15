package com.hldj.hmyg.Ui.storeChild;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hldj.hmyg.BActivity_new_test;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.M.QueryBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.StoreActivity;
import com.hldj.hmyg.Ui.StoreActivity_new;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.base.BaseFragment;
import com.hldj.hmyg.bean.StoreGsonBean;
import com.hldj.hmyg.buyer.weidet.BaseMultAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.contract.StoreContract;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.store.StoreTypeActivity;
import com.hldj.hmyg.store.TypeEx;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.widget.DrawableCenterText;
import com.weavey.loading.lib.LoadingLayout;
import com.zzy.flowers.widget.popwin.EditP5;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONArray;

import java.util.List;

import static com.hldj.hmyg.buyer.weidet.BaseMultAdapter.GRID_VIEW;

/**
 * 我的店铺     ---------   店铺首页
 */

public class StoreHomeFragment extends BaseFragment implements StoreContract.View, View.OnClickListener {

    private static final String TAG = "StoreHomeFragment";
    FinalBitmap bitmap;
    private CoreRecyclerView store_recycle;
    /**
     * 定义上传搜索对象
     */
    private QueryBean queryBean;

    private static final int LIST_VIEW = 0;//默认是list 列表展示
    int type = LIST_VIEW;//切换类型  GRID_VIEW
    private TypeEx typeEx;


    public static StoreHomeFragment Instance(String persionId) {
        StoreHomeFragment fragment = new StoreHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG, persionId);
        fragment.setArguments(bundle);
        return fragment;
    }


    public String getTypeListJson() {
        if (mActivity instanceof StoreActivity_new) {
            Log.e(TAG, "getTypeListJson: " + "\n" + ((StoreActivity_new) mActivity).getTypeListJson());
            return ((StoreActivity_new) mActivity).getTypeListJson();
        } else {
            return "";
        }
    }

//    @Override
//    public int bindLoadingLayout() {
//        return R.id.store_loading;
//    }

    @Override
    protected void initView(View rootView) {
        initExt();
        MyPresenter myPresenter = new MyPresenter();
        bitmap = FinalBitmap.create(mActivity);
//        store_recycle = (CoreRecyclerView) getView(R.id.store_recycle);
        store_recycle = (CoreRecyclerView) rootView.findViewById(R.id.store_recycle);
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
                helper.getConvertView().setOnClickListener(v -> {
                    FlowerDetailActivity.start2Activity(mActivity, "seedling_list", item.id);
                });
            }
        }, true)
                .closeDefaultEmptyView()
                .closeRefresh()
                .openLoadMore(getQueryBean().pageSize, page -> {
                    showLoading();
                    showActivityLoading();
                    getQueryBean().pageIndex = page + "";
                    getQueryBean().ownerId = getStoreID();
                    myPresenter.getData();
//            mPresenter.getData();
                });
        store_recycle.onRefresh();


    }


    @Override
    protected void initListener() {

        getView(R.id.dctv_left).setOnClickListener(this);
        getView(R.id.dctv_center).setOnClickListener(this);
        getView(R.id.dctv_right).setOnClickListener(this);

        ImageView imageView = getView(R.id.iv_store_l_g);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStyle(imageView.isSelected());
                imageView.setSelected(!imageView.isSelected());

            }
        });

    }

    @Override
    protected int bindLayoutID() {
        return R.layout.fragment_store_home;
    }

    @Override
    public void showErrir(String erMst) {
        hideLoading(LoadingLayout.Error, erMst);
    }

    @Override
    public void hindLoading() {

    }


    @Override
    public void initStoreData(List<BPageGsonBean.DatabeanX.Pagebean.Databean> bPageGsonBean) {

    }

    @Override
    public void initIndexBean(StoreGsonBean.DataBean indexBean) {

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
        return getArguments().getString(TAG);
    }

    @Override
    public ViewPager getViewPager() {
        return null;
    }

    @Override
    public void initUpMarqueeView(List<Moments> moments) {

    }


    private void changeStyle(boolean selected) {

        if (!selected)//grid
        {
            RecyclerView.LayoutManager layoutManager = store_recycle.getRecyclerView().getLayoutManager();
            //判断是当前layoutManager是否为LinearLayoutManager
            // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                //获取第一个可见view的位置
                int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                D.e("=======firstItemPosition========" + firstItemPosition);
                now_position = firstItemPosition;
            } else if (layoutManager instanceof GridLayoutManager) {
                D.e("=======GridLayoutManager========");
            }
            ((BaseMultAdapter) store_recycle.getAdapter()).setDefaultType(GRID_VIEW);

            type = GRID_VIEW;
            store_recycle.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.left = 0;
                    outRect.right = 0;
                    outRect.top = 0;
                    outRect.bottom = 0;
                }
            });
            store_recycle.getRecyclerView().setLayoutManager(new GridLayoutManager(mActivity, 2));

            store_recycle.getAdapter().notifyDataSetChanged();
            startAnimation(store_recycle.getRecyclerView(), R.anim.zoom_in);
            store_recycle.getRecyclerView().scrollToPosition(now_position);
        } else {//list
            RecyclerView.LayoutManager layoutManager = store_recycle.getRecyclerView().getLayoutManager();
            //判断是当前layoutManager是否为LinearLayoutManager
            // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法

            if (layoutManager instanceof LinearLayoutManager) {

            } else if (layoutManager instanceof GridLayoutManager) {
                D.e("=======GridLayoutManager========");
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;

                now_position = gridLayoutManager.findFirstVisibleItemPosition();


                //获取第一个可见view的位置
                D.e("=======firstItemPosition========");

            }

            ((BaseMultAdapter) store_recycle.getAdapter()).setDefaultType(0);
            type = 0;
            store_recycle.getRecyclerView().setLayoutManager(new LinearLayoutManager(mActivity));
            store_recycle.getAdapter().notifyDataSetChanged();

            store_recycle.getRecyclerView().scrollToPosition(now_position);

            startAnimation(store_recycle.getRecyclerView(), R.anim.zoom_in);


        }
        store_recycle.getAdapter().onAttachedToRecyclerView(store_recycle.getRecyclerView());
    }

    int now_position = 0;

    /**
     * 开启动画
     */
    private void startAnimation(RecyclerView rv, int anim) {
        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(mActivity, anim));
        lac.setOrder(LayoutAnimationController.ORDER_RANDOM);
        rv.setLayoutAnimation(lac);
        rv.startLayoutAnimation();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        ToastUtil.showShortToast("fragment       onActivityResult" + resultCode);

        if (requestCode == 13 && resultCode == 1) {
            Bundle bundle = data.getExtras();
            if (bundle.get("TypeEx") != null) {
                typeEx = (TypeEx) bundle.get("TypeEx");
                getQueryBean().secondSeedlingTypeId = typeEx.getFirstSeedlingTypeId();
                store_recycle.onRefresh();
                if (typeEx.getName().equals("苗木分类")) {
                    ((TextView) getView(R.id.dctv_left)).setText(typeEx.getName());
                    ((TextView) getView(R.id.dctv_left)).setTextColor(getResources().getColor(R.color.text_color666));
                } else {
                    ((TextView) getView(R.id.dctv_left)).setText(typeEx.getName());
                    ((TextView) getView(R.id.dctv_left)).setTextColor(getResources().getColor(R.color.main_color));
                }
            }
        }


    }

    public String secondSeedlingTypeId = "";

    public void initExt() {
        typeEx = new TypeEx(-1, -1, "", secondSeedlingTypeId);
    }

    private JSONArray typeList;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dctv_left:
                if (getTypeListJson() != null && getTypeListJson().length() != 0) {
                    Intent intent = new Intent(mActivity, StoreTypeActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("TypeEx", typeEx);
                    mBundle.putString("typeList", getTypeListJson());
                    intent.putExtras(mBundle);
                    startActivityForResult(intent, 13);
//                    Log.e("typeList", typeList.toString());
                } else {
                    Toast.makeText(mActivity, "该店铺暂无分类", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.dctv_center:
                EditP5 popwin = new EditP5(mActivity, getQueryBean().plantTypes, new EditP5.OnPlantTypeSelect() {
                    @Override
                    public void onSelect(String plantType) {
                        TextView tv_plant_type = getView(R.id.dctv_center);
                        if (TextUtils.isEmpty(plantType)) {
                            tv_plant_type.setTextColor(getResources().getColor(R.color.text_color666));
                            tv_plant_type.setText("种植类型");
                        } else {
                            tv_plant_type.setTextColor(getResources().getColor(R.color.main_color));
                            StoreActivity.setTextByType(tv_plant_type, plantType);
                        }

                        if (plantType.length() > 0 && plantType.endsWith(",")) {
                            getQueryBean().plantTypes = plantType.substring(0, plantType.length() - 1);
                        } else {
                            getQueryBean().plantTypes = plantType;
                        }
                        store_recycle.onRefresh();
                    }
                });
                popwin.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                popwin.showAtLocation(getView(R.id.ll_store_home_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.dctv_right:
                DrawableCenterText drawableCenterText = getView(R.id.dctv_right);
                int drawableId = 0;
                if ("".equals(getQueryBean().orderBy)) {
                    getQueryBean().orderBy = "price_asc";
                    drawableId = R.drawable.icon_seller_arrow2;
//                    drawableCenterText.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.icon_seller_arrow2), null);
                } else if ("price_asc".equals(getQueryBean().orderBy)) {
                    getQueryBean().orderBy = "price_desc";
                    drawableId = R.drawable.icon_seller_arrow3;
//                    drawableCenterText.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.icon_seller_arrow3), null);
                } else if ("price_desc".equals(getQueryBean().orderBy)) {
                    getQueryBean().orderBy = "";
                    drawableId = R.drawable.icon_seller_arrow1;
//                    drawableCenterText.setCompoundDrawablesWithIntrinsicBounds(null, null,drawable ,null);
                }
                Drawable drawable = getResources().getDrawable(drawableId);
                drawable.setBounds(0, 0, drawable.getMinimumWidth() / 2 - 3, drawable.getMinimumHeight() / 2 - 3);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    drawableCenterText.setCompoundDrawables(null, null, drawable, null);
                }
                /**
                 *  this.drawableBounds[0] = (float)this.width - this.drawableWidth + this.drawablePaddingLeft;
                 this.drawableBounds[1] = (float)(this.height / 2) - this.drawableHeight / 2.0F + this.drawablePaddingTop;
                 this.drawableBounds[2] = this.drawableBounds[0] + this.drawableWidth;
                 this.drawableBounds[3] = this.drawableBounds[1] + this.drawableHeight;
                 */
                store_recycle.onRefresh();
                break;
        }
    }


//    getQueryBean().ownerId = indexBean.owner.id;
//        D.e("=======QueryBean=========" + getQueryBean().toString());
//        mPresenter.getData();


    private class MyPresenter extends BasePresenter {

        public void getData() {
            putParams(getQueryBean());
            AjaxCallBack ajaxCallBack = new AjaxCallBack<String>() {
                @Override
                public void onSuccess(String json) {

                    BPageGsonBean bPageGsonBean = GsonUtil.formateJson2Bean(json, BPageGsonBean.class);
                    if (bPageGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        store_recycle.getAdapter().addData(bPageGsonBean.data.page.data);

                        if (store_recycle.isDataNull()) store_recycle.setNoData("");
                        hideLoading(store_recycle);
                        hideActivityLoading();
                    } else {

                        hideLoading(LoadingLayout.Error, bPageGsonBean.msg);
                        hideActivityLoading();
                    }
                    store_recycle.selfRefresh(false);
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    store_recycle.selfRefresh(false);
                    store_recycle.setNoData("网络错误");
                    hideActivityLoading();
                    hideLoading(LoadingLayout.No_Network);
                }
            };
            doRequest("seedling/list", false, ajaxCallBack);
        }

    }


}

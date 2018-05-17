package com.hldj.hmyg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hldj.hmyg.DaoBean.SaveJson.DaoSession;
import com.hldj.hmyg.DaoBean.SaveJson.SavaBean;
import com.hldj.hmyg.DaoBean.SaveJson.SavaBeanDao;
import com.hldj.hmyg.M.BPageGsonBean;
import com.hldj.hmyg.M.CountTypeGsonBean;
import com.hldj.hmyg.M.StatusCountBean;
import com.hldj.hmyg.Ui.Eactivity3_0;
import com.hldj.hmyg.adapter.ProductListAdapterForManager;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.base.rxbus.RxBus;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBeanData;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.contract.ManagerListContract;
import com.hldj.hmyg.model.ManagerListModel;
import com.hldj.hmyg.presenter.ManagerListPresenter;
import com.hldj.hmyg.saler.SaveSeedlingActivity;
import com.hldj.hmyg.saler.SearchActivity;
import com.hldj.hmyg.saler.StorageSaveActivity;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hy.utils.GetServerUrl;
import com.hy.utils.JsonGetInfo;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fanrunqi.swipelayoutlibrary.SwipeLayout;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;
import me.kaede.tagview.TagView;

/**
 * 苗木管理界面
 */
@SuppressLint("ClickableViewAccessibility")
public abstract class ManagerListActivity_new<P extends ManagerListPresenter, M extends ManagerListModel> extends BaseMVPActivity<ManagerListPresenter, ManagerListModel> implements ManagerListContract.View {

    private static final String TAG = "ManagerListActivity_new";
    private String status = "";
    private String mType = "";
    private String nurseryId = "";
    private String storeId = "";
    private String searchKey = "";
    public CoreRecyclerView xRecyclerView;


    public boolean isOpenEdit = false;
    public boolean isOpenCheck = false;

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }

    public void setNurseryId(String id) {
        nurseryId = id;
    }

    public List<TextView> list_counts = new ArrayList<>();//显示count 的几个textview

    @Override
    public int bindLayoutID() {
        return R.layout.activity_manager_list_new;
    }

    @Override
    public String setTitle() {
        return "苗木管理";
    }


    @Override
    public void initView() {
        initToolbar();
        xRecyclerView = getView(R.id.xrecycle);
//        list_view_seedling_new
        xRecyclerView.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, 14);
            }
        });
        xRecyclerView.init(new BaseQuickAdapter<BPageGsonBean.DatabeanX.Pagebean.Databean, BaseViewHolder>(R.layout.list_view_seedling_new_swipe) {

//            @Override
//            public int getSwipeLayoutResourceId(int position) {
//                return R.id.swipe_manager;
//            }


//            @Override
//            public void onViewRecycled(@NonNull BaseViewHolder holder) {
//
////                if (holder != null && holder.getView(R.id.et) != null) {
////                    Log.i(TAG, "" + holder.getAdapterPosition() + "  - >" + ((EditText) holder.getView(R.id.et)).getText());
////
////                }
//
//                super.onViewRecycled(holder);
//
//
//            }

            @Override
            protected void convert(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item) {


                /**
                 * add this swipeview
                 */
//                SwipeLayout.addSwipeView(helper.getView(R.id.swipe_manager));

                helper.setIsRecyclable(false);
                BActivity_new.initListType(helper, item, FinalBitmap.create(mContext), "Manager");

                EditText editText = helper.getView(R.id.et);
                CheckBox checkBox = helper.getView(R.id.cb);


                if (isOpenEdit) {
                    editText.setVisibility(View.VISIBLE);
                    Log.i(TAG, helper.getAdapterPosition() + "---> " + item.inputNum);

                    editText.setTag(helper.getAdapterPosition());
                    editText.clearFocus();
                    editText.setText(getData().get(Integer.valueOf(editText.getTag().toString())).inputNum + "");


                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

//                        if (((int) editText.getTag()) == helper.getAdapterPosition()) {
                            try {
                                item.inputNum = Integer.valueOf(s.toString());
                                Log.i(TAG, "afterTextChanged: succeed " + item.inputNum);
                            } catch (Exception e) {
                                item.inputNum = -1;
                                Log.i(TAG, "afterTextChanged: failed" + item.inputNum);
                            }

//                        }
                        }
                    });

                } else {
                    editText.setVisibility(View.GONE);
                }

                if (isOpenCheck) {
                    checkBox.setChecked(item.isChecked);
                    checkBox.setVisibility(View.VISIBLE);
                    checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        item.isChecked = isChecked;
                        Log.i(TAG, "pos = " + helper.getAdapterPosition() + " -- > " + item.isChecked);
                    });
                } else {
                    checkBox.setVisibility(View.GONE);
                }


//                editText.setText(item.inputNum == -1 ? "" : item.inputNum + "");


//                helper.getView(R.id.et).setTag(helper.getAdapterPosition());

                ProductListAdapterForManager.setStateColor(helper.getView(R.id.tv_right_top), item.status, item.statusName);

//                ((SwipeLayout) helper.getView(R.id.swipe_manager)).computeScroll();

                helper.addOnClickListener(R.id.swipe_manager1, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent toFlowerDetailActivity = new Intent(ManagerListActivity_new.this, FlowerDetailActivity.class);
                        toFlowerDetailActivity.putExtra("id", item.id);
                        toFlowerDetailActivity.putExtra("show_type", "manage_list");
                        startActivityForResult(toFlowerDetailActivity, 1);
                    }
                });

                helper.addOnClickListener(R.id.btn_delete_manager, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((SwipeLayout) helper.getView(R.id.swipe_manager)).SimulateScroll(SwipeLayout.SHRINK);
                        mPresenter.doDelete(item.id);
                    }
                });


                invadeDoConvert(helper, item, mActivity);

            }
        }).openLoadMore(10, page -> {
//            xRecyclerView.selfRefresh(true);
            showLoading();
            requestData(page);
        }).openLoadAnimation(BaseQuickAdapter.ALPHAIN)
                .openRefresh();

        switch2Refresh("", 0);

    }

    public abstract void invadeDoConvert(BaseViewHolder helper, BPageGsonBean.DatabeanX.Pagebean.Databean item, NeedSwipeBackActivity mActivity);


    public void requestData(int page) {
        mPresenter.getData(page + "", storeId, nurseryId, mType, status, searchKey);
        requestCounts();
    }

    public void requestCounts() {
        mPresenter.getCounts();
    }


    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    private void seedlingDoDel(String id, final int pos) {
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("ids", id);
        finalHttp.post(GetServerUrl.getUrl() + "admin/seedling/doDel", params, new AjaxCallBack<Object>() {

            @Override
            public void onSuccess(Object t) {
                try {
                    JSONObject jsonObject = new JSONObject(t.toString());
                    String code = JsonGetInfo.getJsonString(jsonObject,
                            "code");
                    String msg = JsonGetInfo.getJsonString(jsonObject,
                            "msg");
                    if (!"".equals(msg)) {
                        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
                    }
                    if ("1".equals(code)) {
                    } else {

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                super.onSuccess(t);
            }

            @Override
            public void onFailure(Throwable t, int errorNo,
                                  String strMsg) {
                // TODO Auto-generated method stub
                super.onFailure(t, errorNo, strMsg);
            }

        });

    }


    @Override
    public void initVH() {


        getView(R.id.btn_manager_storege).setOnClickListener(view -> {//草稿箱
            StorageSaveActivity.start2Activity(mActivity);
        });


        getView(R.id.btn_manager_publish).setOnClickListener(view -> {//发布
            SaveSeedlingActivity.start2Activity(mActivity);
        });
        getView(R.id.rl_01).setOnClickListener(view -> {//所有
            switch2Refresh("", 0);
        });
        getView(R.id.rl_02).setOnClickListener(view -> {//所有
            switch2Refresh("unaudit", 1);
        });
        getView(R.id.rl_03).setOnClickListener(view -> {//所有

            switch2Refresh("published", 2);

        });
        getView(R.id.rl_04).setOnClickListener(view -> {//所有

            switch2Refresh("outline", 3);

        });
        getView(R.id.rl_05).setOnClickListener(view -> {//所有

            switch2Refresh("backed", 4);

        });
        getView(R.id.rl_06).setOnClickListener(view -> {//所有
            switch2Refresh("unsubmit", 5);

        });


    }


    public TextView[] tvs = null;
    public ImageView[] ivs = null;
    public ViewGroup[] rls = null;
    public View[] lines = null;

    public void switch2Refresh(String stat, int index) {

        if (tvs == null) {
            tvs = new TextView[]{getView(R.id.tv_01), getView(R.id.tv_02), getView(R.id.tv_03), getView(R.id.tv_04), getView(R.id.tv_05), getView(R.id.tv_06)};
        }
        if (ivs == null) {
            ivs = new ImageView[]{getView(R.id.botton_lines_1), getView(R.id.botton_lines_2), getView(R.id.botton_lines_3), getView(R.id.botton_lines_4), getView(R.id.botton_lines_5), getView(R.id.botton_lines_6)};
        }
        if (rls == null) {
            //rl_01
            rls = new RelativeLayout[]{getView(R.id.rl_01), getView(R.id.rl_02), getView(R.id.rl_03), getView(R.id.rl_04), getView(R.id.rl_05), getView(R.id.rl_06),};
        }
        if (lines == null) {
            //rl_01
            lines = new View[]{getView(R.id.botton_lines_1), getView(R.id.botton_lines_2), getView(R.id.botton_lines_3), getView(R.id.botton_lines_4), getView(R.id.botton_lines_5), getView(R.id.botton_lines_6),};
        }

        status = stat;


        for (int i1 = 0; i1 < tvs.length; i1++) {
            if (index == i1) {
                tvs[i1].setTextColor(ContextCompat.getColor(mActivity, R.color.main_color));
                ivs[i1].setVisibility(View.VISIBLE);
            } else {
                tvs[i1].setTextColor(ContextCompat.getColor(mActivity, R.color.text_color333));
                ivs[i1].setVisibility(View.INVISIBLE);
            }
        }
        xRecyclerView.openRefresh();
    }


//    public void initRequest() {
//        switch2Refresh("", 0);
//    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    private void initToolbar() {
        ImageView iv_search = getView(R.id.toolbar_right_icon);
        iv_search.setVisibility(View.VISIBLE);
//        setTitle("苗木管理");
        iv_search.setOnClickListener(view -> {
            Intent toSearchActivity = new Intent(ManagerListActivity_new.this, SearchActivity.class);
            toSearchActivity.putExtra("searchKey", searchKey);
            startActivityForResult(toSearchActivity, 1);
        });
    }

//    private void setCounts() {
//        BasePresenter listPresenter = new ManagerListPresenter()
//                .addResultCallBack(new ResultCallBack<CountTypeGsonBean.PurchaseItemBean_new.CountMapBean>() {
//                    @Override
//                    public void onSuccess(CountTypeGsonBean.PurchaseItemBean_new.CountMapBean countMapBean) {
//                        list_counts.clear();
//                        LinearLayout linearLayout = getView(R.id.ll_counts_content);
//                        findTagTextView(linearLayout);
//                        for (int i = 0; i < list_counts.size(); i++) {
//                            switch (i) {
//                                case 0:
//                                    ((TextView) list_counts.get(i)).setText("(" + countMapBean.all + ")");
//                                    break;
//                                case 1:
//                                    ((TextView) list_counts.get(i)).setText("(" + countMapBean.unaudit + ")");
//                                    break;
//                                case 2:
//                                    ((TextView) list_counts.get(i)).setText("(" + countMapBean.published + ")");
//                                    break;
//                                case 3:
//                                    ((TextView) list_counts.get(i)).setText("(" + countMapBean.outline + ")");
//                                    break;
//                                case 4:
//                                    ((TextView) list_counts.get(i)).setText("(" + countMapBean.backed + ")");
//                                    break;
//                                case 5:
//                                    ((TextView) list_counts.get(i)).setText("(" + countMapBean.unsubmit + ")");
//                                    break;
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t, int errorNo, String strMsg) {
//
//                    }
//                });
//        ((ManagerListPresenter) listPresenter).getStatusCount();
//
//
////        findTagTextView(linearLayout);
//
//
//    }


    public void findTagTextView(ViewGroup viewGroup) {

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                findTagTextView(((ViewGroup) viewGroup.getChildAt(i)));
            } else {
                if (viewGroup.getChildAt(i).getTag() != null && viewGroup.getChildAt(i).getTag().toString().equals("tag")) {
                    list_counts.add((TextView) viewGroup.getChildAt(i));
                }
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Button button = getView(R.id.btn_manager_storege);
        DaoSession daoSession = MyApplication.getInstance().getDaoSession();
        SavaBeanDao savaBeanDao = daoSession.getSavaBeanDao();
        List<SavaBean> list = savaBeanDao.queryBuilder().list();
        button.setText("草稿箱" + "(" + list.size() + ")");
    }


    public static void start2Activity(Context context) {
        context.startActivity(new Intent(context, ManagerListActivity_new.class));
    }


    @Override
    public void initXRecycle(BPageGsonBean gsonBean) {

        xRecyclerView.getAdapter().addData(gsonBean.data.page.data);
//        xRecyclerView.getAdapter().notifyDataSetChanged();

        xRecyclerView.selfRefresh(false);
        hindLoading();

        if (xRecyclerView.isDataNull()) {
            xRecyclerView.setNoData("");
        }
    }


    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        if (arg2 != null && arg1 == 6) {

            searchKey = arg2.getStringExtra("searchKey");
            TagView tagView = getView(R.id.tagview);
            tagView.removeAllTags();

            if (!"".equals(searchKey)) {
                me.kaede.tagview.Tag tag = new me.kaede.tagview.Tag(searchKey);
                tag.layoutColor = ContextCompat.getColor(mActivity, R.color.main_color);
                tag.isDeletable = true;
                tag.id = 1; // 关键字
                tagView.addTag(tag);
                tagView.setOnTagDeleteListener((position, tag1) -> {
                    searchKey = "";
                    xRecyclerView.onRefresh();
                });
            }

            switch2Refresh("", 0);

        } else if (arg1 == ConstantState.PUBLIC_SUCCEED) {
            xRecyclerView.onRefresh();
            RxBus.getInstance().post(DActivity_new_mp.refresh, new Eactivity3_0.OnlineEvent(true));
        } else if (arg1 == ConstantState.FLOW_BACK) {
            xRecyclerView.onRefresh();
        }

        super.onActivityResult(arg0, arg1, arg2);
    }


    @Override
    public void initCounts(CountTypeGsonBean gsonBean) {
        list_counts.clear();
        LinearLayout linearLayout = getView(R.id.ll_counts_content);
        findTagTextView(linearLayout);
        for (int i = 0; i < list_counts.size(); i++) {
            switch (i) {
                case 0:
                    list_counts.get(i).setText("(" + gsonBean.data.countMap.all + ")");
                    break;
                case 1:
                    list_counts.get(i).setText("(" + gsonBean.data.countMap.unaudit + ")");
                    break;
                case 2:
                    list_counts.get(i).setText("(" + gsonBean.data.countMap.published + ")");
                    break;
                case 3:
                    list_counts.get(i).setText("(" + gsonBean.data.countMap.outline + ")");
                    break;
                case 4:
                    list_counts.get(i).setText("(" + gsonBean.data.countMap.backed + ")");
                    break;
                case 5:
                    list_counts.get(i).setText("(" + gsonBean.data.countMap.unsubmit + ")");
                    break;

            }
        }

    }

    @Override
    public void initCounts2(SimpleGsonBeanData<StatusCountBean> gsonBean) {

//,"pendingCount":1,"statusList":["unaudit","published"],"sellingCount"
//        ToastUtil.showLongToast(gsonBean.msg);
        list_counts.clear();
        LinearLayout linearLayout = getView(R.id.ll_counts_content);
        findTagTextView(linearLayout);
        for (int i = 0; i < list_counts.size(); i++) {
            switch (i) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    list_counts.get(i).setText("(" + gsonBean.data.sellingCount + ")");
                    break;
                case 3:
//                    list_counts.get(i).setText("(" + gsonBean.data.pendingCount + ")");
                    break;
                case 4:
//                    list_counts.get(i).setText("(" + gsonBean.data.countMap.backed + ")");
                    break;
                case 5:
                    list_counts.get(i).setText("(" + gsonBean.data.pendingCount + ")");
                    break;

            }
        }

    }


    @Override
    public void initTodoStatusCount(SimpleGsonBean gsonBean) {
//        ToastUtil.showLongToast(gsonBean.msg);
        list_counts.clear();
        LinearLayout linearLayout = getView(R.id.ll_counts_content);
        findTagTextView(linearLayout);
        for (int i = 0; i < list_counts.size(); i++) {
            switch (i) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    list_counts.get(i).setText("(" + gsonBean.getData().backedCount + ")");

                    D.w(" storeId is =====>  " + gsonBean.getData().storeId);
                    break;
                case 3:
//                    list_counts.get(i).setText("(" + gsonBean.data.pendingCount + ")");
                    break;
                case 4:
//                    list_counts.get(i).setText("(" + gsonBean.data.countMap.backed + ")");
                    break;
                case 5:
                    list_counts.get(i).setText("(" + gsonBean.getData().outlineCount + ")");

//                    list_counts.get(i).setText("(" + gsonBean.data.pendingCount + ")");
                    break;

            }
        }
    }

    @Override
    public void onDeled(boolean bo) {
        if (bo) {
            xRecyclerView.onRefresh();
        }
    }


    @Override
    public void showErrir(String erMst) {
        //此处处理错误 样式
        if (xRecyclerView != null) {
            xRecyclerView.selfRefresh(false);
        }
        hindLoading();
    }


    FinalBitmap bitmap;

}

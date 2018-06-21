package com.hldj.hmyg.buyer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.BActivity_new_test;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.StoreActivity_new;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.M.SearchBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

import java.util.ArrayList;
import java.util.List;

import static com.hldj.hmyg.R.color.main_color;
import static com.hldj.hmyg.buyer.weidet.CoreRecyclerView.REFRESH;

/**
 * 首页 商城  关键字搜索
 */
public class PurchaseSearchListActivity extends BaseMVPActivity {

    @ViewInject(id = R.id.recycle)
    CoreRecyclerView mCoreRecyclerView;

    @ViewInject(id = R.id.search_content)
    EditText search_content;

    @ViewInject(id = R.id.toolbar_left_icon)
    ImageView toolbar_left_icon;

    @ViewInject(id = R.id.iv_view_type)
    TextView iv_view_type;


    @ViewInject(id = R.id.left)
    TextView left;


    @ViewInject(id = R.id.top)
    ViewGroup top;

    @ViewInject(id = R.id.right)
    TextView right;


    public String currentType = LEFT;
    public static final String LEFT = "left";
    public static final String RIGHT = "right";


    private List<SearchBean.DataBean.StoreListBean> storeListBeen = new ArrayList<>();
    private List<SearchBean.DataBean.SeedlingListBean> seedlingListBeen = new ArrayList<>();

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);
        toolbar_left_icon.setOnClickListener(v -> finish());
        search_content.addTextChangedListener(watcher);
        iv_view_type.setOnClickListener(v -> processByFrom(search_content.getText().toString(), ""));
//        iv_view_type.setOnClickListener(v -> request(str));


        initByFrom(search_content, top);


        left.setOnClickListener(v -> {
            currentType = LEFT;
            left.setTextColor(getColorByRes(main_color));
            right.setTextColor(getColorByRes(R.color.text_color333));
//            request(search_content.getText());
            refresh();
        });

        right.setOnClickListener(v -> {
            currentType = RIGHT;
            left.setTextColor(getColorByRes(R.color.text_color333));
            right.setTextColor(getColorByRes(R.color.main_color));
//            request(search_content.getText());
            refresh();
        });


        mCoreRecyclerView.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
                //设定底部边距为1px
                outRect.set(0, 0, 0, 1);
            }
        });

        mCoreRecyclerView.init(new BaseQuickAdapter<Object, BaseViewHolder>(R.layout.list_item_sort) {

            @Override
            protected void convert(BaseViewHolder helper, Object item) {

                String sourceId = "";
                if (item instanceof SearchBean.DataBean.StoreListBean) {
                    SearchBean.DataBean.StoreListBean storeBean = ((SearchBean.DataBean.StoreListBean) item);
                    helper.setText(R.id.tv_item, storeBean.name);
                    sourceId = storeBean.id;
                } else if (item instanceof SearchBean.DataBean.SeedlingListBean) {
                    SearchBean.DataBean.SeedlingListBean seedlingBean = ((SearchBean.DataBean.SeedlingListBean) item);
                    helper.setText(R.id.tv_item, seedlingBean.name);
                }
                helper.setBackgroundRes(R.id.tv_item, R.drawable.bg_bottom_line);
                String finalSourceId = sourceId;
                helper.setVisible(R.id.is_check, false)
                        .addOnClickListener(R.id.tv_item, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                ToastUtil.showLongToast(((TextView) v).getText().toString());
                                processByFrom(((TextView) v).getText().toString(), finalSourceId);

                            }
                        })

                ;

            }
        }).lazyShowEmptyViewEnable(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethod.showSoftInput(search_content, 0);
                inputMethod.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);


//                ToastUtil.showLongToast("自动显示软键盘");
            }
        }, 1300);


    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_purchase_search_list;
    }


    public long lastTime = 0;

    Handler handler = new Handler() {

    };
    Runnable removeCallbacks = new Runnable() {
        @Override
        public void run() {
            request(str);
        }
    };

    String str = "";

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            if (System.currentTimeMillis() - lastTime > 1000) {
//                D.i("-------间隔500mm-------搜索ing----");
//                lastTime = System.currentTimeMillis();

            } else {
//                D.i("-------间隔小于 500mm----不搜索-------");
            }
            str = s.toString();
            handler.removeCallbacks(removeCallbacks);
            handler.postDelayed(removeCallbacks, 400);

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }
    };

    private void request(CharSequence s) {

        if (TextUtils.isEmpty(s)) {
            D.i("======关键字为空，不请求======");
            return;
        }


        new BasePresenter()
                .putParams("searchKey", s.toString())
                .doRequest("seedling/search", true, new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String json) {

                        SearchBean searchBean = null;
                        try {
                            searchBean = GsonUtil.formateJson2Bean(json, SearchBean.class);
                            storeListBeen.clear();
                            storeListBeen.addAll(searchBean.data.storeList);
                            seedlingListBeen.clear();
                            seedlingListBeen.addAll(searchBean.data.seedlingList);
                            if (searchBean.code == "1") {
//                                ToastUtil.showLongToast(searchBean.msg);
                            } else {
//                                ToastUtil.showLongToast(searchBean.msg);
                            }
//                            mCoreRecyclerView.onRefresh();


                            refresh();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        mCoreRecyclerView.setDefaultEmptyView();

                    }
                });
        D.i("==========请求数据====keyword0====" + s);
    }


    private void refresh() {
        if (mCoreRecyclerView == null) {
            return;
        }
        mCoreRecyclerView.getAdapter().setDatasState(REFRESH);

        if (currentType.equals(LEFT)) {
            mCoreRecyclerView.getAdapter().addData(seedlingListBeen);
        } else {
            mCoreRecyclerView.getAdapter().addData(storeListBeen);
        }
        left.setText("苗木资源 （" + seedlingListBeen.size() + "）");
        right.setText("店铺 （" + storeListBeen.size() + "）");

    }


    public static final String FROM_HOME = "home";/*从首页过来*/
    public static final String FROM_STORE = "store";/*从商店过来*/
    public static final String FROM_SEARCH = "search";/*从搜索界面过来*/
    public static final String FROM = "from";


    public String getFrom() {

        return getIntent().getStringExtra(FROM);
    }


    public void initByFrom(EditText hint, ViewGroup parent) {
        switch (getFrom()) {
            case FROM_HOME:
                hint.setHint("品名/别名/店铺");
                break;
            case FROM_STORE:
                hint.setHint("请输入关键字");
                parent.setVisibility(View.GONE);
                break;
            case FROM_SEARCH:
                hint.setHint("请输入关键字");
                parent.setVisibility(View.GONE);
                break;
        }
    }


    public void processByFrom(String search_key, String source) {
        Intent intent = new Intent();
        switch (getFrom()) {
            case FROM_HOME:
                //首页来的 -- -- -- --  直接跳到各大 位置

                if (!TextUtils.isEmpty(source)) {
                    StoreActivity_new.start2Activity(mActivity, source);
                } else {
                    BActivity_new_test.start2Activity(mActivity, search_key, 0);
                }


                break;
            case FROM_STORE:
                //首页来的 -- -- -- --  直接跳到各大 位置

                intent.putExtra("searchKey", search_key);
                setResult(ConstantState.SEARCH_OK, intent);
                finish();
                //商店来的 来的 -- -- -- --   setrestult
                break;
            case FROM_SEARCH:
                //首页来的 -- -- -- --  直接跳到各大 位置
                intent.putExtra("searchKey", search_key);
                setResult(ConstantState.SEARCH_OK, intent);
                finish();
                // 从搜索界面过来 ------   setrestult
                break;
        }


    }


    public static void start(Activity activity, String from) {
        Intent intent = new Intent(activity, PurchaseSearchListActivity.class);
        intent.putExtra(FROM, from);
        activity.startActivityForResult(intent, 1);
    }


}

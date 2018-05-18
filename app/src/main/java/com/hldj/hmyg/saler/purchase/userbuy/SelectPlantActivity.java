package com.hldj.hmyg.saler.purchase.userbuy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sortlistview.SideBar;
import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SeedlingType;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;
import com.weavey.loading.lib.LoadingLayout;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import static com.hldj.hmyg.buyer.weidet.CoreRecyclerView.REFRESH;

/**
 * 用户发布界面
 */
@SuppressLint({"NewApi", "ResourceAsColor"})
public class SelectPlantActivity extends BaseMVPActivity implements OnClickListener {


    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycle;

    @ViewInject(id = R.id.slide_bar)
    SideBar slide_bar;

    @ViewInject(id = R.id.center)
    TextView centerTip;


    @ViewInject(id = R.id.sptv_program_do_search, click = "onClick")
    View sptv_program_do_search;
    @ViewInject(id = R.id.et_program_serach_text)
    EditText et_search_content;

    @ViewInject(id = R.id.loading_layout)
    LoadingLayout loading_layout;

    Handler handler = new Handler();

    @Override
    public int bindLayoutID() {
        return R.layout.activity_select_plant;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sptv_program_do_search:
                搜索(getSearchKey());
                break;
        }
    }

    public String getSearchKey() {
        return getText(getView(R.id.et_program_serach_text));
    }

    private void 搜索(String key) {

        new BasePresenter()
                .putParams("key", key)
                .doRequest("seedling/autocomplete", new HandlerAjaxCallBack() {
                    @Override
                    public void onRealSuccess(SimpleGsonBean gsonBean) {

                        Log.i("搜索", "onRealSuccess: " + gsonBean.isSucceed());
                        recycle.getAdapter().setDatasState(REFRESH);
                        recycle.getAdapter().addData(gsonBean.getData().seedlingTypeList);
                    }
                });


    }


    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            D.i("延时执行");
        }
    };

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);


        et_search_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 800);
                搜索(s.toString());
            }
        });
        et_search_content.setText(getHistoryKey());


        loading_layout.setStatus(LoadingLayout.Success);

        loading_layout.setEmptyText("品种未收录,点击提交(花花木)");


        initRecycleView(recycle);

        slide_bar.setTextView(centerTip);

        slide_bar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                centerTip.setText(s);

                int pos = findPosByChar(s);
                if (pos != -1) {
                    recycle.getRecyclerView().scrollToPosition(pos);
                }
            }

            private int findPosByChar(String s) {
                for (int i = 0; i < recycle.getAdapter().getData().size(); i++) {

                    if (recycle.getAdapter().getData().get(i).toString().contains(s)) {
                        return i;
                    }
                }
                return -1;
            }
        });


        if (TextUtils.isEmpty(getSearchKey())) {
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


    }

    private void initRecycleView(CoreRecyclerView recycle) {


//        View empty_view = LayoutInflater.from(mActivity).inflate(R.layout.empty_view_select, null);

        slide_bar.setVisibility(View.GONE);


        recycle.init(new BaseQuickAdapter<SeedlingType, BaseViewHolder>(R.layout.item_list_simple) {
            @Override
            protected void convert(BaseViewHolder helper, SeedlingType item) {
                helper.setText(android.R.id.text1, item.name + " ( " + item.parentName + " )");

                helper.getView(android.R.id.text1).setPadding(helper.getView(android.R.id.text1).getPaddingLeft(), 20, 0, 20);


                helper.convertView.setOnClickListener(v -> {
                    Intent intent = new Intent();
                    intent.putExtra("item", item);
                    setResult(100, intent);
                    finish();
                });
            }
        }).setDefaultEmptyView();

//        recycle.getRecyclerView().addItemDecoration(SectionDecoration.Builder.init(new SectionDecoration.PowerGroupListener() {
//            @Override
//            public String getGroupName(int position) {
//
//                if (recycle.getAdapter().getData().size() == 0) {
//                    return null;
//                }
//
//                SeedlingType seedlingType = (SeedlingType) recycle.getAdapter().getData().get(position);
//                return seedlingType.parentName;
//            }
//
//            @Override
//            public View getGroupView(int position) {
//                if (recycle.getAdapter().getData().size() == 0)
//                    return null;
//                View view = LayoutInflater.from(mActivity).inflate(R.layout.item_tag, null);
//                TextView textView = view.findViewById(R.id.text1);
//                textView.setHeight((int) getResources().getDimension(R.dimen.px50));
//                SeedlingType seedlingType = (SeedlingType) recycle.getAdapter().getData().get(position);
//                textView.setText(seedlingType.parentName);
//                return view;
//
//            }
//        }).setGroupHeight((int) getResources().getDimension(R.dimen.px50)).build());

//        recycle.getAdapter().addData("AA");
//        recycle.getAdapter().addData("aaa");
//        recycle.getAdapter().addData("aaa");
//        recycle.getAdapter().addData("b");
//        recycle.getAdapter().addData("c");
//        recycle.getAdapter().addData("C");
//        recycle.getAdapter().addData("c");
//        recycle.getAdapter().addData("d");
//        recycle.getAdapter().addData("e");
//        recycle.getAdapter().addData("o");
//        recycle.getAdapter().addData("p");
//        recycle.getAdapter().addData("p");
//        recycle.getAdapter().addData("q");
//        recycle.getAdapter().addData("Z");


    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public String getHistoryKey() {
        String str = getIntent().getStringExtra("searchKey");
        D.i("------searchkey -is ---" + str);
        return str;
    }

    public static void start2Activity(Activity mActivity, String searchKey) {
        Intent intent = new Intent(mActivity, SelectPlantActivity.class);
        intent.putExtra("searchKey", searchKey);
        mActivity.startActivityForResult(intent, 100);
    }

    @Override
    public String setTitle() {
        return "品种搜索";
    }
}

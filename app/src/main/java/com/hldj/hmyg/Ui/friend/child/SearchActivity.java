package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hldj.hmyg.BActivity_new_test;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.bean.History;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.PurchaseSearchListActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.util.ConstantState;
import com.hy.utils.ToastUtil;
import com.zf.iosdialog.widget.AlertDialog;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.Collections;
import java.util.List;

import static com.hldj.hmyg.buyer.weidet.CoreRecyclerView.REFRESH;

/**
 * 搜索界面  包含历史记录
 */

public class SearchActivity extends BaseMVPActivity {

    /**
     * 搜索内容 标签
     */
    public static final String SEARCH_CONTENT = "searchKey";


    @ViewInject(id = R.id.recycle)
    CoreRecyclerView mCoreRecyclerView;

    @ViewInject(id = R.id.iv_view_type)
    TextView iv_view_type;//搜索按钮


    @ViewInject(id = R.id.toolbar_left_icon)
    View toolbar_left_icon;//后退按钮
    private FinalDb db;

    @ViewInject(id = R.id.search_content)
    EditText search_content;//输入搜索框


    public int bindLayoutID() {
        return R.layout.activity_friend_search;
    }


    /**
     * 获取输入框文本内容
     */
    private String getContent() {
        return search_content.getText().toString();
    }

    /*赋值文本框内容，将历史记录快速输入*/
    private void setContent(String msg) {
        search_content.setText(msg);
        if (!TextUtils.isEmpty(msg))
            search_content.setSelection(msg.length());
    }

    public void jump(Intent intent) {
        intent.putExtra(SEARCH_CONTENT, getContent());
        if (getExtraFrom().equals(PurchaseSearchListActivity.FROM_HOME)) {//首页过来
            hideSoftWare();
            BActivity_new_test.start2Activity(mActivity, getContent(), 0);
            finish();
        } else if (getExtraFrom().equals(PurchaseSearchListActivity.FROM_STORE)) {//  商店过来
            //调到新界面。，筛选显示
            hideSoftWare();
            setResult(ConstantState.SEARCH_OK, intent);
            finish();
        } else {//其他
            //后退到上个界面。以删除按钮样式显示

            hideSoftWare();
            BActivity_new_test.start2Activity(mActivity, getContent(), 0);
            finish();
        }
    }

    @Override
    public void initView() {
        if (bindLayoutID() > 0) {
            /*注入ui */
            FinalActivity.initInjectedView(this);
        }
        /*创建数据库*/
        db = FinalDb.create(this);

        setContent(getKeyWord());

        toolbar_left_icon.setOnClickListener(v -> finish());


         /*跳转搜索界面*/
        search_content.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                toSearch();
            }
            return false;
        });
        iv_view_type.setOnClickListener(v -> toSearch());
        /*跳转搜索界面*/


        mCoreRecyclerView.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
                //设定底部边距为1px
                outRect.set(0, 0, 0, 1);
            }
        });

        mCoreRecyclerView.init(new BaseQuickAdapter<History, BaseViewHolder>(R.layout.list_item_sort) {

            @Override
            protected void convert(BaseViewHolder helper, History item) {
                helper
                        .setImageResource(R.id.is_check, R.mipmap.search_delete)
                        .setBackgroundRes(R.id.tv_item, R.drawable.bg_bottom_line)
                        .setVisible(R.id.is_check, true)
                        .setText(R.id.tv_item, item.getContent())
                        .addOnClickListener(R.id.tv_item, new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                setContent(item.getContent());
                                jump(new Intent());
                            }
                        })
                        .addOnClickListener(R.id.is_check, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 删除db数据
                                List<History> userList = db.findAll(History.class);//查询所有的用户
                                if (userList == null || userList.size() == 0) {
                                    ToastUtil.showLongToast("删光了");
                                }
                                db.delete(item);
//                                ToastUtil.showLongToast(userList.size() + "一共这么多条数据");
//                                userList.remove(helper.getAdapterPosition());
//                                mCoreRecyclerView.getAdapter().notifyItemRemoved(helper.getAdapterPosition());
                                refresh();

                            }
                        })
                ;

            }
        })
                .setEmptyText("暂无历史记录...")
//        .lazyShowEmptyViewEnable(true)
        ;
        refresh();
        View footView = LayoutInflater.from(mActivity).inflate(R.layout.item_friend_delete_all, null);
        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog(mActivity).builder()
                        .setTitle("确定清空历史记录?")
                        .setPositiveButton("确定删除", v1 -> {
//                            ToastUtil.showLongToast("删除所有1");
                            db.deleteAll(History.class);
                            refresh();
                        }).setNegativeButton("取消", v2 -> {
                }).show();


            }
        });
        mCoreRecyclerView.addFooterView(footView);

    }

    private void toSearch() {
        if (TextUtils.isEmpty(getContent().trim())) {
//                    ToastUtil.showLongToast("请输入需要查找的内容");
            jump(new Intent());
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(SEARCH_CONTENT, getContent());

        History history = new History();
        history.setContent(getContent());
        history.setTime(System.currentTimeMillis());


        //保存前先判断 是覆盖还是刷新
        List<History> userList = db.findAll(History.class);//查询所有的用户

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getContent().equals(history.getContent())) {
                        /*历史记录内容相等，更新数据*/
                History history1 = userList.get(i);
                history1.setContent(history.getContent());
                history1.setTime(history.getTime());
                db.update(history1);
//                        ToastUtil.showLongToast("数据库有次数据，更新");
                jump(intent);
                return;
            }
        }
//                ToastUtil.showLongToast("数据库没有相同数据，插入新数据");
        db.save(history);
//                ToastUtil.showLongToast(userList.size() + "一共这么多条数据");
        refresh();
        jump(intent);
//                hideSoftWare();
//                setResult(ConstantState.SEARCH_OK, intent);
//                finish();
    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    //    @Override
//    public int bindLayoutID() {
//        return 0;
//    }


    @Override
    public String setTitle() {
        return "搜索";
    }


    public void refresh() {
        //查询所有的用户
        List<History> userList = db.findAll(History.class);
        mCoreRecyclerView.getAdapter().setDatasState(REFRESH);
        Collections.reverse(userList);
        mCoreRecyclerView.getAdapter().addData(userList);
    }

    /*隐藏软键盘*/
    public void hideSoftWare() {
        InputMethodManager inputMethod = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        if (inputMethod != null && this.getCurrentFocus() != null) {
            inputMethod.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public String getKeyWord() {
        return getIntent().getStringExtra(TAG);
    }

    public static void start(Activity activity, String keyWord, String from) {
        Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(TAG, keyWord);
        intent.putExtra(PurchaseSearchListActivity.FROM, from);
        activity.startActivityForResult(intent, 110);
    }

    private static final String TAG = "SearchActivity";


    private String getExtraFrom() {

        return TextUtils.isEmpty(getIntent().getStringExtra(PurchaseSearchListActivity.FROM)) ? "" : getIntent().getStringExtra(PurchaseSearchListActivity.FROM);
    }

}

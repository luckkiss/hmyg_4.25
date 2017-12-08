package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.FriendCycleSearchActivity;
import com.hldj.hmyg.Ui.friend.bean.History;
import com.hldj.hmyg.base.BaseMVPActivity;
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
 * 搜索界面
 */

public class SearchActivity extends BaseMVPActivity {

    /**
     * 搜索内容 标签
     */
    public static final String SEARCH_CONTENT = "search_content";

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
    }

    public void jump(int tag, Intent intent) {
        if (tag == 1) {
            //后退到上个界面。以删除按钮样式显示
            hideSoftWare();
            setResult(ConstantState.SEARCH_OK, intent);
            finish();
        } else {
            //调到新界面。，筛选显示
            hideSoftWare();
            FriendCycleSearchActivity.start(mActivity, getContent());
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

        iv_view_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(getContent().trim())) {
//                    ToastUtil.showLongToast("请输入需要查找的内容");
                    jump(0, new Intent());
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
                        jump(0, intent);
                        return;
                    }
                }
//                ToastUtil.showLongToast("数据库没有相同数据，插入新数据");
                db.save(history);
//                ToastUtil.showLongToast(userList.size() + "一共这么多条数据");
                refresh();
                jump(0, intent);
//                hideSoftWare();
//                setResult(ConstantState.SEARCH_OK, intent);
//                finish();
            }
        });


        mCoreRecyclerView.init(new BaseQuickAdapter<History, BaseViewHolder>(R.layout.list_item_sort) {

            @Override
            protected void convert(BaseViewHolder helper, History item) {
                helper
                        .setImageResource(R.id.is_check, R.mipmap.search_delete)
                        .setVisible(R.id.is_check, true)
                        .setText(R.id.tv_item, item.getContent())
                        .addOnClickListener(R.id.tv_item, new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                setContent(item.getContent());
                                jump(0, new Intent());
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
        });
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
        return "供应详情";
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
        inputMethod.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public String getKeyWord() {
        return getIntent().getStringExtra(TAG);
    }

    public static void start(Activity activity, String keyWord) {
        Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(TAG, keyWord);
        activity.startActivityForResult(intent, 110);
    }

    private static final String TAG = "SearchActivity";

}

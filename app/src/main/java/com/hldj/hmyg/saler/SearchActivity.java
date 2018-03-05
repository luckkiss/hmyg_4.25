package com.hldj.hmyg.saler;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.hldj.hmyg.R;
import com.hldj.hmyg.application.Data;
import com.zzy.flowers.widget.popwin.EditP2;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;
import me.maxwin.view.XListView;

public class SearchActivity extends NeedSwipeBackActivity {
    private XListView xListView;

    boolean getdata; // 避免刷新多出数据

    private MultipleClickProcess multipleClickProcess;
    private String id = "";
    private EditText et_search;
    private Button edit_btn;
    private String searchKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchKey = getIntent().getStringExtra("searchKey");
        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        edit_btn = (Button) findViewById(R.id.edit_btn);
        et_search = (EditText) findViewById(R.id.et_search);
        xListView = (XListView) findViewById(R.id.xlistView);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(false);
        multipleClickProcess = new MultipleClickProcess();
        btn_back.setOnClickListener(multipleClickProcess);
        edit_btn.setOnClickListener(multipleClickProcess);
        et_search.setText(searchKey);
        et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // initData(et_search.getText().toString());
                    searchKey = et_search.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("searchKey", searchKey);
                    setResult(6, intent);
                    finish();
                }
                return false;
            }
        });

    }


    public class MultipleClickProcess implements OnClickListener {
        private boolean flag = true;
        private EditP2 popwin;

        private synchronized void setFlag() {
            flag = false;
        }

        public void onClick(View view) {
            if (flag) {
                switch (view.getId()) {
                    case R.id.btn_back:
                        onBackPressed();
                        break;
                    case R.id.edit_btn:
                        // initData(et_search.getText().toString());
                        searchKey = et_search.getText().toString();
                        Intent intent = new Intent();
                        intent.putExtra("searchKey", searchKey);
                        setResult(6, intent);
                        finish();
                        break;
                    default:
                        break;
                }
                setFlag();
                // do some things
                new TimeThread().start();
            }
        }

        /**
         * 计时线程（防止在一定时间段内重复点击按钮）
         */
        private class TimeThread extends Thread {
            public void run() {
                try {
                    Thread.sleep(Data.loading_time);
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }








}

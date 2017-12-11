package com.hldj.hmyg.Ui.friend;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Keep;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.bean.enums.MomentsType;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.annotation.view.ViewInject;

/**
 * FinalActivity 来进行    数据绑定
 */
@Keep
public class FriendCycleSearchActivity extends FriendCycleActivity {

    private static final String TAG = "FriendCycleSearch";

    public String name = "haha";


    //    /*中间2个按钮*/
//    @ViewInject(id = R.id.rb_title_left)
//    RadioButton rb_title_left;
    @ViewInject(id = R.id.search_bar, click = "onClick")
    public View search_bar;

    @ViewInject(id = R.id.iv_view_type, click = "onClick")
    public TextView iv_view_type;

    @ViewInject(id = R.id.rb_left, click = "onClick")
    public RadioButton rb_left;
    @ViewInject(id = R.id.rb_center, click = "onClick")
    public RadioButton rb_center;
    @ViewInject(id = R.id.rb_right, click = "onClick")
    public RadioButton rb_right;
    /*搜索框*/
    @ViewInject(id = R.id.search_content)
    public EditText search_content;


    @Override
    public int bindLayoutID() {
        return R.layout.activity_friend_cycle_search;
    }

    @Keep
    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.search_bar:
                ToastUtil.showLongToast("top");
                break;
            case R.id.rb_left:
//                ToastUtil.showLongToast("rb_left");
                currentType = MomentsType.all.getEnumValue();
                mRecyclerView.onRefresh();
                break;
            case R.id.rb_center:
//                ToastUtil.showLongToast("rb_center");
                currentType = MomentsType.supply.getEnumValue();
                mRecyclerView.onRefresh();
                break;
            case R.id.rb_right:
//                ToastUtil.showLongToast("rb_right");
                currentType = MomentsType.purchase.getEnumValue();
                mRecyclerView.onRefresh();
                break;
            case R.id.iv_view_type:
                searchContent = search_content.getText().toString().trim();
                mRecyclerView.onRefresh();
//                ToastUtil.showLongToast("iv_view_type");
                break;
        }

    }


    //    @Override
//    public void setView() {
//        if (bindLayoutID() > 0) {
//            FinalActivity.initInjectedView(this);
//        }
//    }


    @Override
    public void initChild() {
        toolbar_left_icon.setImageResource(R.drawable.ic_arrow_left_green);
        toolbar_left_icon.setOnClickListener(v -> finish());

        searchContent = getSearchContent();
        search_content.setText(searchContent);
        search_content.setSelection(searchContent.length());//将光标移至文字末尾
        currentType = MomentsType.all.getEnumValue();
//        search_content.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                try {
//                    Thread.sleep(100);
//                    Log.i("onTouch", "x" + event.getX() + "   RawX" + event.getRawX());
//                    Log.i("onTouch", "y" + event.getY() + "   RawY" + event.getRawY());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return true;
//            }
//        });

    }

//    @Override
//    public void inject() {
//        FinalActivity.initInjectedView(this);
//    }

//    @Override
//    public void initView() {
//        super.initView();
//        String keyWorld = getSearchContent();
//        D.e(keyWorld);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }


    public String getSearchContent() {
//        ToastUtil.showLongToast(getIntent().getStringExtra(TAG));
        return getIntent().getStringExtra(TAG);
    }

    public static void start(Activity activity, String content) {
        Intent intent = new Intent(activity, FriendCycleSearchActivity.class);
        intent.putExtra(TAG, content);
        activity.startActivity(intent);
    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


}

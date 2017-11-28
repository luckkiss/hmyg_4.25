package com.hldj.hmyg.Ui.friend.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hldj.hmyg.R;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class FriendBottomView extends LinearLayout {
    public FriendBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();


    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_friend_other,this);

    }


}

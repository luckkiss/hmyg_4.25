package com.hldj.hmyg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.mrwujay.cascade.activity.BaseSecondActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * 商城界面
 */
@SuppressLint("NewApi")
public class BActivity_new extends BaseSecondActivity   {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_b_to_toolbar);
        final ListView recyclerView = (ListView) findViewById(R.id.xlistView);
        ArrayAdapter<String> myAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getDatas());//适配器
        recyclerView.setAdapter(myAdapter);
        CoreRecyclerView recyclerView1 = (CoreRecyclerView) findViewById(R.id.core_rv_b);
    }

    public List<String> getDatas() {
        List list_datas = new ArrayList();
        for (int i = 0; i < 50; i++) {
            list_datas.add("data" + i);
        }
        return list_datas;
    }


}

package com.hldj.hmyg.saler.Ui;

import android.os.Bundle;

import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.buyer.Ui.PurchaseDetailActivityBase;

import java.util.ArrayList;
import java.util.List;


/**
 * 修改成 快速报价里面的详情界面一样，增加报价结束功能
 * Created by Administrator on 2017/5/3.
 */

public class ManagerQuoteListItemDetail_new extends PurchaseDetailActivityBase {


//    int oldlayout = R.id.manager_quote_list_detail ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initDirect(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen) {

    }

    @Override
    protected void initProtocol(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen) {

    }

    @Override
    public void addPicUrls(ArrayList<Pic> resultPathList) {

    }

}

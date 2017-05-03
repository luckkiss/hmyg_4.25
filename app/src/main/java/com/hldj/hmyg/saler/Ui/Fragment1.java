package com.hldj.hmyg.saler.Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hldj.hmyg.R;
import com.hldj.hmyg.buyer.M.QuoteListBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.util.D;
import com.hy.utils.GetServerUrl;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * Created by Administrator on 2017/5/2.
 */

public class Fragment1 extends Fragment {


    private CoreRecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.activity_a_top_toolbar_new,null);

        View view = getContentView() ;

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getData();


    }

    private void getData() {
        initData();

        // TODO: 2017/5/2 0002
    }

    public View getContentView() {

        recyclerView = new CoreRecyclerView(getActivity());
        recyclerView.initView(getActivity()).init(new BaseQuickAdapter<QuoteListBean, BaseViewHolder>(R.layout.item_quote_dir_po) {
            @Override
            protected void convert(BaseViewHolder helper, QuoteListBean item) {

            }
        });

        return recyclerView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

    }


    private void initData() {
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("status", "");
        params.put("pageSize", 10 + "");
        params.put("pageIndex", 0 + "");
        finalHttp.post(GetServerUrl.getUrl() + "admin/quote/list", params, new AjaxCallBack<String>() {

            @Override
            public void onSuccess(String json) {
                D.e("======json=========" + json);
                super.onSuccess(json);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }
}
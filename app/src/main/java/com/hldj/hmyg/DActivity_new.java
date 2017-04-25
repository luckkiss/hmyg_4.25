package com.hldj.hmyg;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.adapter.CartListAdapter;
import com.hldj.hmyg.base.MySwipeAdapter;
import com.hldj.hmyg.bean.CollectGsonBean;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.presenter.CollectPresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

/**
 * 收藏夹  界面
 */
public class DActivity_new extends Activity implements IXListViewListener {
    private XListView xlistView_d_new;
    private ArrayList<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();

    boolean getdata = true; // 避免刷新多出数据
    private CartListAdapter listAdapter;

    private int pageSize = 20;
    private int pageIndex = 0;
    private MySwipeAdapter collectAdapter;//收藏列表的  适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_d_new);


        initView();

        initData();
    }


    List<SaveSeedingGsonBean.DataBean.SeedlingBean> seedlingBeen = new ArrayList<>();

    private void initView() {


        findViewById(R.id.tv_clear_all).setOnClickListener(v -> {
                    D.e("==============清空收藏夹============");



                    new CollectPresenter(new ResultCallBack<SimpleGsonBean>() {
                        @Override
                        public void onSuccess(SimpleGsonBean simpleGsonBean) {
                            pageIndex = 0;
                            initData();
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {

                        }
                    }).reqClearCollect("seedling");
                }
//            FinalHttp finalHttp = new FinalHttp();
//            GetServerUrl.addHeaders(finalHttp, true);
//            AjaxParams params = new AjaxParams();
//            params.put("type", "seedling");
//            finalHttp.post(GetServerUrl.getUrl() + "admin/collect/empty", params, new AjaxCallBack<String>() {
//                @Override
//                public void onSuccess(String json) {
//
//                    SimpleGsonBean simpleGsonBean = GsonUtil.formateJson2Bean(json, SimpleGsonBean.class);
//
//                    if (simpleGsonBean.code.equals("1")) {
//
//                        ToastUtil.showShortToast("清空成功");
//                        pageIndex = 0;
//                        initData();
//                    } else {
//                        ToastUtil.showShortToast(simpleGsonBean.msg);
//                    }
//                }
//
//                @Override
//                public void onFailure(Throwable t, int errorNo, String strMsg) {
//                    super.onFailure(t, errorNo, strMsg);
//                }
//            });
//
//
//        }
        );


            xlistView_d_new = (XListView) findViewById(R.id.xlistView_d_new);
            xlistView_d_new.setDivider(null);
            xlistView_d_new.setPullLoadEnable(true);
            xlistView_d_new.setPullRefreshEnable(true);
            xlistView_d_new.setXListViewListener(this);

            collectAdapter = new MySwipeAdapter(this, seedlingBeen);
            xlistView_d_new.setAdapter(collectAdapter);

        }

    private void initData() {
        /**
         * 2.5.8.1 收藏夹苗木列表
         1，说明
         收藏夹苗木列表返回该用户对应类型的收藏内容
         2，URL
         Post:      /admin/collect/listSeedling
         3，参数
         admin/collect/listSeedling
         */
        // TODO Auto-generated method stub

        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("pageSize", pageSize + "");
        params.put("pageIndex", pageIndex + "");
        finalHttp.post(GetServerUrl.getUrl() + "admin/collect/listSeedling", params,
//        finalHttp.post(GetServerUrl.getUrl() + "admin/cart/list", params,
                new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String json) {
                        CollectGsonBean collectGsonBean = GsonUtil.formateJson2Bean(json, CollectGsonBean.class);
//                        {"code":"1","msg":"操作成功","data":{"page":{"pageNo":1,"pageSize":20,"total":2,"firstResult":20,"maxResults":20}}}
                        D.e("========json=========" + json);
                        if (collectGsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                            if (collectGsonBean.data.page.total != 0 && collectGsonBean.data.page.data.size() != 0) {
                                seedlingBeen.addAll(collectGsonBean.data.page.data);
                                collectAdapter.notifyDataSetChanged();
                                pageIndex++;
                                D.e("=====pageIndex=======" + pageIndex);
                            }
                        } else {

                            D.e("===数据库空空如也====");
                        }
                        D.e("===============collectGsonBean================" + collectGsonBean);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        // TODO Auto-generated method stub
                        Toast.makeText(DActivity_new.this, R.string.error_net,
                                Toast.LENGTH_SHORT).show();
                        super.onFailure(t, errorNo, strMsg);
                    }

                });
        getdata = true;
    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        xlistView_d_new.setPullLoadEnable(false);
        seedlingBeen.clear();
        pageIndex = 0;
//        datas.clear();
//        listAdapter.notifyDataSetChanged();
        if (getdata == true) {
            initData();
        }
        onLoad();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onLoadMore() {
        xlistView_d_new.setPullRefreshEnable(false);
        //判断是否超过一页。。么有的话刷新
        initData();
        onLoad();
    }

    private void onLoad() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                xlistView_d_new.stopRefresh();
                xlistView_d_new.stopLoadMore();
                xlistView_d_new.setRefreshTime(new Date().toLocaleString());
                xlistView_d_new.setPullLoadEnable(true);
                xlistView_d_new.setPullRefreshEnable(true);

            }
        }, com.hldj.hmyg.application.Data.refresh_time);

    }




}

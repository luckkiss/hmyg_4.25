package com.hldj.hmyg.saler.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.hldj.hmyg.R;
import com.hldj.hmyg.saler.Adapter.FragmentPagerAdapter_TabLayout;
import com.hldj.hmyg.saler.ManagerQuoteListAdapter;
import com.hy.utils.GetServerUrl;
import com.hy.utils.JsonGetInfo;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;
import me.maxwin.view.XListView;


public class ManagerQuoteListActivity_new extends NeedSwipeBackActivity {
    private XListView xListView;
    private ArrayList<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();

    boolean getdata; // 避免刷新多出数据
    private ManagerQuoteListAdapter listAdapter;

    private int pageSize = 20;
    private int pageIndex = 0;
    private String status = "";
    private View mainView;

    // 报价管理列表

    TabLayout mTabLayout;
    ViewPager mViewPager;
    FragmentPagerAdapter_TabLayout mFragmentPagerAdapter_tabLayout;
    private ArrayList<String> list_title = new ArrayList<String>() {{
        add("全部");
        add("已中标");
        add("未中标");
    }};
    private ArrayList<Fragment> list_fragment = new ArrayList<Fragment>() {{
        add(new Fragment1());
        add(new Fragment2());
        add(new Fragment3());
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_quote_list_new);


//        LoadingLayout   loadingLayout = (LoadingLayout) findViewById(R.id.loading);
//        loadingLayout.show
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);


        mFragmentPagerAdapter_tabLayout = new FragmentPagerAdapter_TabLayout(getSupportFragmentManager(), list_title, list_fragment);
        mViewPager.setAdapter(mFragmentPagerAdapter_tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);


    }

    private void initData() {
        // TODO Auto-generated method stub
        getdata = false;
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("status", status);
        params.put("pageSize", pageSize + "");
        params.put("pageIndex", pageIndex + "");
        finalHttp.post(GetServerUrl.getUrl() + "admin/quote/list", params,
                new AjaxCallBack<Object>() {

                    @Override
                    public void onSuccess(Object t) {
                        // TODO Auto-generated method stub
                        try {
                            JSONObject jsonObject = new JSONObject(t.toString());
                            String code = JsonGetInfo.getJsonString(jsonObject,
                                    "code");
                            String msg = JsonGetInfo.getJsonString(jsonObject,
                                    "msg");
                            if (!"".equals(msg)) {
                                // Toast.makeText(ManagerListActivity.this, msg,
                                // Toast.LENGTH_SHORT).show();
                            }
                            if ("1".equals(code)) {
                                JSONObject jsonObject2 = jsonObject
                                        .getJSONObject("data").getJSONObject(
                                                "page");
                                if (JsonGetInfo.getJsonArray(jsonObject2,
                                        "data").length() > 0) {
                                    JSONArray jsonArray = JsonGetInfo
                                            .getJsonArray(jsonObject2, "data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject4 = jsonArray
                                                .getJSONObject(i);
                                        JSONObject jsonObject3 = JsonGetInfo
                                                .getJSONObject(jsonArray
                                                                .getJSONObject(i),
                                                        "purchaseItemJson");
                                        JSONObject purchaseJson = JsonGetInfo
                                                .getJSONObject(jsonArray
                                                                .getJSONObject(i),
                                                        "purchaseJson");
                                        JSONObject buyer = JsonGetInfo
                                                .getJSONObject(purchaseJson,
                                                        "buyer");
                                        HashMap<String, Object> hMap = new HashMap<String, Object>();
                                        hMap.put("id", JsonGetInfo
                                                .getJsonString(jsonObject4,
                                                        "id"));
                                        hMap.put("remarks", JsonGetInfo
                                                .getJsonString(jsonObject4,
                                                        "remarks"));
                                        hMap.put("status", JsonGetInfo
                                                .getJsonString(jsonObject4,
                                                        "status"));
                                        hMap.put("statusName", JsonGetInfo
                                                .getJsonString(jsonObject4,
                                                        "statusName"));
                                        hMap.put("createDate", JsonGetInfo
                                                .getJsonString(jsonObject4,
                                                        "createDate"));
                                        hMap.put("closeDate", JsonGetInfo
                                                .getJsonString(jsonObject4,
                                                        "closeDate"));
                                        hMap.put("count", JsonGetInfo
                                                .getJsonInt(jsonObject4,
                                                        "count"));
                                        hMap.put("price", JsonGetInfo
                                                .getJsonDouble(jsonObject4,
                                                        "price"));
                                        hMap.put("name", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "name"));
                                        hMap.put("seedlingParams", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "seedlingParams"));
                                        hMap.put("firstSeedlingTypeId",
                                                JsonGetInfo.getJsonString(
                                                        jsonObject3,
                                                        "firstSeedlingTypeId"));
                                        hMap.put("firstTypeName", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "firstTypeName"));
                                        hMap.put("plantType", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "plantType"));
                                        hMap.put("unitType", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "unitType"));
                                        hMap.put("unitTypeName", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "unitTypeName"));
                                        hMap.put("plantTypeName", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "plantTypeName"));
                                        hMap.put("diameterType", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "diameterType"));
                                        hMap.put("dbhType", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "dbhType"));
                                        hMap.put("purchaseId", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "purchaseId"));
                                        hMap.put("prCode", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "prCode"));
                                        hMap.put("ciCode", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "ciCode"));
                                        hMap.put("coCode", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "coCode"));
                                        hMap.put("twCode", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "twCode"));
                                        hMap.put("diameter", JsonGetInfo
                                                .getJsonInt(jsonObject3,
                                                        "diameter"));
                                        hMap.put("dbh", JsonGetInfo.getJsonInt(
                                                jsonObject3, "dbh"));
                                        hMap.put("height", JsonGetInfo
                                                .getJsonInt(jsonObject3,
                                                        "height"));
                                        hMap.put("crown", JsonGetInfo
                                                .getJsonInt(jsonObject3,
                                                        "crown"));
                                        hMap.put("offbarHeight", JsonGetInfo
                                                .getJsonInt(jsonObject3,
                                                        "offbarHeight"));
                                        hMap.put("length", JsonGetInfo
                                                .getJsonInt(jsonObject3,
                                                        "length"));
                                        hMap.put(
                                                "count_purchaseItemJson",
                                                JsonGetInfo
                                                        .getJsonInt(
                                                                jsonObject3,
                                                                "count_purchaseItemJson"));
                                        JSONObject ciCity = JsonGetInfo
                                                .getJSONObject(purchaseJson,
                                                        "ciCity");
                                        hMap.put("fullName", JsonGetInfo
                                                .getJsonString(ciCity,
                                                        "fullName"));
                                        hMap.put("num", JsonGetInfo
                                                .getJsonString(purchaseJson,
                                                        "num"));
                                        hMap.put("receiptDate", JsonGetInfo
                                                .getJsonString(purchaseJson,
                                                        "receiptDate"));
                                        hMap.put("closeDate", JsonGetInfo
                                                .getJsonString(purchaseJson,
                                                        "closeDate"));

                                        String publicName = JsonGetInfo
                                                .getJsonString(buyer,
                                                        "publicName");
                                        String realName = JsonGetInfo
                                                .getJsonString(buyer,
                                                        "realName");
                                        String companyName = JsonGetInfo
                                                .getJsonString(buyer,
                                                        "companyName");
                                        String publicPhone = JsonGetInfo
                                                .getJsonString(buyer,
                                                        "publicPhone");
                                        String address = JsonGetInfo
                                                .getJsonString(purchaseJson,
                                                        "cityName");
                                        hMap.put("publicName", publicName);
                                        hMap.put("realName", realName);
                                        hMap.put("companyName", companyName);
                                        hMap.put("publicPhone", publicPhone);
                                        hMap.put("address", address);
                                        hMap.put("quoteCountJson", JsonGetInfo
                                                .getJsonInt(jsonObject3,
                                                        "quoteCountJson"));
                                        datas.add(hMap);
                                        if (listAdapter != null) {
                                            listAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    if (listAdapter == null) {
                                        listAdapter = new ManagerQuoteListAdapter(
                                                ManagerQuoteListActivity_new.this,
                                                datas, mainView);
                                        xListView.setAdapter(listAdapter);
                                    }

                                    pageIndex++;

                                }

                            } else {

                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        super.onSuccess(t);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo,
                                          String strMsg) {
                        // TODO Auto-generated method stub
                        Toast.makeText(ManagerQuoteListActivity_new.this,
                                R.string.error_net, Toast.LENGTH_SHORT).show();
                        super.onFailure(t, errorNo, strMsg);
                    }

                });
        getdata = true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == 1) {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

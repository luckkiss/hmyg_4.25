package com.hldj.hmyg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hldj.hmyg.bean.CityGsonBean;
import com.hldj.hmyg.bean.QueryBean;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.buyer.PurchaseSearchListActivity;
import com.hldj.hmyg.buyer.Ui.CityWheelDialogF;
import com.hldj.hmyg.presenter.SaveSeedlingPresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.widget.SearchScropView;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;
import com.yangfuhai.asimplecachedemo.lib.ACache;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

import static com.hldj.hmyg.R.id.search_view2;
import static com.hldj.hmyg.util.ConstantState.FILTER_OK;

public class SellectActivity2 extends NeedSwipeBackActivity {
    //    MaterialDialog mMaterialDialog;
    private static String type01 = ""; // planted,


    private LinearLayout ll_area;
//    private LinearLayout ll_price;

    private TextView tv_area;


    private ArrayList<String> danwei_names = new ArrayList<String>();
    private ArrayList<String> danwei_ids = new ArrayList<String>();
    private ArrayList<String> planttype_names = new ArrayList<String>();
    private ArrayList<String> planttype_ids = new ArrayList<String>();

    private TagFlowLayout mFlowLayout2;
    private TagFlowLayout mFlowLayout3;

    private ACache mCache;

    private EditText et_min_guige;//最大规格
    private EditText et_max_guige;//最小规格

    //    private EditText et_pinming;
    private String searchSpec = "";
    private String plantTypes = "";
    private ArrayList<String> planttype_has_ids = new ArrayList<String>();
    private String searchKey = "";
    private QueryBean queryBean;
    private SearchScropView scropView_rod;
    private SearchScropView scropView_height;
    private SearchScropView scropView_crown;
    //    private EditText et_minHeight;
//    private EditText et_maxHeight;
//    private EditText et_minCrown;
//    private EditText et_maxCrown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellect2);

        scropView_rod = getView(R.id.search_view1);
        scropView_height = getView(search_view2);
        scropView_crown = getView(R.id.search_view3);

        getIntentExtral();
        mCache = ACache.get(this);
//        mMaterialDialog = new MaterialDialog(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        MultipleClickProcess multipleClickProcess = new MultipleClickProcess();
        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        TextView iv_reset = (TextView) findViewById(R.id.iv_reset);
        ll_area = (LinearLayout) findViewById(R.id.ll_area);
//        ll_price = (LinearLayout) findViewById(R.id.ll_price);
//        et_pinming = (EditText) findViewById(et_pinming);

        et_min_guige = (EditText) findViewById(R.id.et_min_guige);
        et_max_guige = (EditText) findViewById(R.id.et_max_guige);

//        et_minHeight = (EditText) findViewById(R.id.et_minHeight);
//        et_maxHeight = (EditText) findViewById(R.id.et_maxHeight);
//        et_minCrown = (EditText) findViewById(R.id.et_minCrown);
//        et_maxCrown = (EditText) findViewById(R.id.et_maxCrown);


        tv_area = (TextView) findViewById(R.id.tv_area);

        mFlowLayout2 = (TagFlowLayout) findViewById(R.id.id_flowlayout2);
        mFlowLayout2.setMaxSelectCount(1);
        mFlowLayout3 = (TagFlowLayout) findViewById(R.id.id_flowlayout3);
        TextView sure = (TextView) findViewById(R.id.sure);
        initData();
        initImportWorlds();

//        if (mCache.getAsString("initSearch") != null && !"".equals(mCache.getAsString("initSearch"))) {
//            LoadCache(mCache.getAsString("initSearch"));
//        } else {
        initSearch();
//        }

        btn_back.setOnClickListener(multipleClickProcess);
        iv_reset.setOnClickListener(multipleClickProcess);
        ll_area.setOnClickListener(multipleClickProcess);

        sure.setOnClickListener(multipleClickProcess);
    }


    private void initSearch() {
        // TODO Auto-generated method stub
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("searchKey", queryBean.searchKey);
        finalHttp.post(GetServerUrl.getUrl() + "seedling/initSearch", params, new AjaxCallBack<Object>() {

            @Override
            public void onSuccess(Object t) {
                // TODO Auto-generated method stub
                mCache.remove("initSearch");
                mCache.put("initSearch", t.toString());
                LoadCache(t.toString());
                super.onSuccess(t);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                if (mCache.getAsString("initSearch") != null && !"".equals(mCache.getAsString("initSearch"))) {
                    LoadCache(mCache.getAsString("initSearch"));
                }
                super.onFailure(t, errorNo, strMsg);
            }

        });
    }

    List<TypesBean.DataBean.MainSpecBean> mainSpecBean;

    private void LoadCache(String t) {
        TypesBean typesBean = GsonUtil.formateJson2Bean(t, TypesBean.class);

        if (!typesBean.code.equals(ConstantState.SUCCEED_CODE)) {
            ToastUtil.showShortToast(typesBean.msg);
            return;
        }
        if (typesBean.data.plantTypeList != null) {
            initPlantTypeList(typesBean.data.plantTypeList);
        }
        if (typesBean.data.specList != null) {
            initSpecList(typesBean.data.specList);
        }
        mainSpecBean = typesBean.data.mainSpec;

//        if (GetServerUrl.isTest)
        initSearchView(scropView_rod, mainSpecBean.get(0), "10", "20");

        initSearchView(scropView_height, mainSpecBean.get(1), "30", "450");
        initSearchView(scropView_crown, mainSpecBean.get(2), "50", "666");


        //必填 写 *  号
//        AutoAddRelative.isShowLeft(mainSpecBean.required, getView(R.id.tv_sa_type), R.drawable.seller_redstar);//是否必填

        /**
         "mainSpec": {
         "name": "地径",
         "value": "diameter",
         "required": true
         },
         */


    }


    public void initSearchView(SearchScropView view, TypesBean.DataBean.MainSpecBean model, String min, String max) {
        view.setDatas(model);
        if (view.type.equals("rod")) {
            view.setMin(queryBean.minRod);
            view.setMax(queryBean.maxRod);
        } else if (view.type.equals("height")) {
            view.setMin(queryBean.minHeight);
            view.setMax(queryBean.maxHeight);
        } else if (view.type.equals("crown")) {
            view.setMin(queryBean.minCrown);
            view.setMax(queryBean.maxCrown);
        }
    }

    private void initSpecList(List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> specList) {
        {
            for (int i = 0; i < specList.size(); i++) {
                danwei_names.add(specList.get(i).getText());
                danwei_ids.add(specList.get(i).getValue());
            }
            if (danwei_names.size() > 0) {
                SaveSeedlingPresenter.initAutoLayout2(mFlowLayout2, specList, -1, SellectActivity2.this, (view, position, parent) ->
                        {
//                            D.e("==view被点击了===" + view.isSelected());
//                            D.e("==parent被点击了===" + parent.isSelected());
//                            searchSpec = danwei_ids.get(position);
                            return false;
                        }
                );

                mFlowLayout2.setOnSelectListener(selectPosSet -> {
                    searchSpec = "";
                    for (Integer setItem : selectPosSet) {
                        D.e("===================" + setItem);
                        searchSpec = danwei_ids.get(setItem);

                    }
                    queryBean.searchSpec = searchSpec;
                    D.e("====buffer======" + queryBean.toString());
                });


                for (int i = 0; i < danwei_ids.size(); i++) {
                    if (searchSpec.equals(danwei_ids.get(i))) {
                        mFlowLayout2.getAdapter().setSelectedList(i);
                    }
                }
            }
        }
    }

    /**
     * 初始化  植物选项
     *
     * @param plantTypeList 种植类型  集合
     */
    private void initPlantTypeList(List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> plantTypeList) {
        if (plantTypeList.size() > 0) {
            planttype_names.clear();
            planttype_ids.clear();
            for (int i = 0; i < plantTypeList.size(); i++) {
                planttype_names.add(plantTypeList.get(i).getText());
                planttype_ids.add(plantTypeList.get(i).getValue());
            }

            if (planttype_names.size() > 0) {
                SaveSeedlingPresenter.initAutoLayout2(mFlowLayout3, plantTypeList, -1, SellectActivity2.this, (view, position, parent) -> false);
                mFlowLayout3.setMaxSelectCount(1);

                int[] pos = new int[planttype_ids.size()];
                for (int i = 0; i < planttype_ids.size(); i++) {
                    pos[i] = -1;
                    if (buffer.toString().contains(planttype_ids.get(i))) {
                        pos[i] = i;
                    }
                }
                if (buffer.length() != 0) {//没值时  退出循环
                    mFlowLayout3.getAdapter().setSelectedList(pos);
                }

                mFlowLayout3.setOnSelectListener(selectPosSet -> {
                    buffer = new StringBuffer();
                    planttype_has_ids.clear();
                    for (Integer setItem : selectPosSet) {
                        D.e("===================" + setItem);

//                        buffer = buffer.append(planttype_ids.get(setItem) + ",");
                        buffer = new StringBuffer(planttype_ids.get(setItem) + ",");
                        planttype_has_ids.clear();
                        planttype_has_ids.add(planttype_ids.get(setItem));

                    }
                    D.e("====buffer======" + buffer);
                });


            }


        }
    }

    StringBuffer buffer = new StringBuffer();


    public void initData() {
//        String from = getIntent().getStringExtra("from");
//        if ("StorePurchaseListActivity".equals(from)) {
//            ll_price.setVisibility(View.GONE);
//        }

//        cityName = getIntent().getStringExtra("cityName");

//        planttype_has_ids = getIntent().getStringArrayListExtra( "planttype_has_ids");
//        searchSpec = getIntent().getStringExtra("searchSpec");
        searchSpec = queryBean.searchSpec;
//        String specMinValue = getIntent().getStringExtra("specMinValue");
        String specMinValue = queryBean.specMinValue;
        String specMaxValue = queryBean.specMaxValue;
//        String specMaxValue = getIntent().getStringExtra("specMaxValue");

//        searchKey = getIntent().getStringExtra("searchKey");
        searchKey = queryBean.searchKey;
//        buffer = buffer.append(getIntent().getStringExtra("plantTypes"));
        buffer = buffer.append(queryBean.plantTypes);


        et_max_guige.setText(queryBean.specMaxValue);// 最大厘米
        et_min_guige.setText(queryBean.specMinValue);// 最小厘米

//        et_minHeight.setText(queryBean.minHeight);//最小高度
//        et_maxHeight.setText(queryBean.maxHeight);//最大高度

//        et_minCrown.setText(queryBean.minCrown);//最小冠幅
//        et_maxCrown.setText(queryBean.maxCrown);//最大冠幅


//        if (et_min_guige.getText().toString().length() > 0
//                || et_max_guige.getText().toString().length() > 0) {
//            mFlowLayout2.setVisibility(View.VISIBLE);
//        } else {
//            mFlowLayout2.setVisibility(View.VISIBLE);
//        }


//        et_pinming.setText(searchKey);

        if (!TextUtils.isEmpty(childBeans.name)) {
            tv_area.setText(childBeans.name);
            queryBean.cityCode = childBeans.cityCode;
        } else {
            tv_area.setText("全国");
        }


//        queryBean.specMinValue, queryBean.specMaxValue, queryBean.searchSpec


    }

    public static CityGsonBean.ChildBeans childBeans = new CityGsonBean.ChildBeans();

    public class MultipleClickProcess implements OnClickListener {
        private boolean flag = true;

        private synchronized void setFlag() {
            flag = false;
        }

        public void onClick(View view) {
            if (flag) {
                switch (view.getId()) {
                    case R.id.btn_back:
                        onBackPressed();
                        break;
                    case R.id.ll_area:

                        CityWheelDialogF.instance()
                                .setNoShowCity()
                                .addSelectListener(new CityWheelDialogF.OnCitySelectListener() {
                                    @Override
                                    public void onCitySelect(CityGsonBean.ChildBeans childBeans) {
                                    }

                                    @Override
                                    public void onProvinceSelect(CityGsonBean.ChildBeans childBeans) {
                                        SellectActivity2.childBeans = childBeans;
                                        D.e("=选择  地区==" + childBeans.toString());
                                        tv_area.setText(SellectActivity2.childBeans.name);
                                    }
                                }).show(getSupportFragmentManager(), "SellectActivity2");


                        break;
                    case R.id.iv_reset:
                        searchSpec = "";
//                        guige_has_click_names.clear();
//                        et_pinming.setText("");
                        et_min_guige.setText("");
                        et_max_guige.setText("");

//                        setText(getView(R.id.et_sa_type), "");
                        setText(getView(R.id.tv_import_word), "关键字:");
                        queryBean.searchKey = "";
                        scropView_rod.resetValue();
                        scropView_height.resetValue();
                        scropView_crown.resetValue();

                        buffer = new StringBuffer();
                        initSearch();
//                        et_minHeight.setText("");//最小高度
//                        et_maxHeight.setText("");//最大高度

//                        et_minCrown.setText("");//最小冠幅
//                        et_maxCrown.setText("");//最大冠幅


                        planttype_has_ids.clear();
//                        mFlowLayout1.setAdapter(adapter1);
                        mFlowLayout2.getAdapter().resetList();
                        mFlowLayout3.getAdapter().resetList();
                        childBeans = new CityGsonBean.ChildBeans();
                        tv_area.setText("全国");
                        type01 = "";

                        queryBean = new QueryBean();

                        break;

                    case R.id.sure:
                        Intent intent = new Intent();
//                        String str = GetCodeByName.initProvinceDatas(SellectActivity2.this, mCurrentProviceName, mCurrentCityName);
//                        if (TextUtils.isEmpty(str)) {
//                            intent.putExtra("cityCode", "");
//                        } else {
//                            intent.putExtra("cityCode", str);
//                        }
//

                        queryBean.cityCode = childBeans.cityCode;


                        String stra = "";
                        if (buffer.length() != 0) {
                            if (buffer.toString().endsWith(",")) {
                                stra = buffer.toString().substring(0, buffer.length() - 1);
                            } else {
                                stra = buffer.toString();
                            }

                        }
                        queryBean.plantTypes = stra;

//                        searchSpec :"diameter"     specMinValue  : 10

//                        queryBean.specMaxValue


//                        if (mainSpecBean.required) {
//                        if (!TextUtils.isEmpty(getText(getView(R.id.et_sa_type)))) {
//                            queryBean.searchSpec = mainSpecBean.get(0).value;
//                            queryBean.specMinValue = getText(getView(R.id.et_sa_type));
////                                ToastUtil.showShortToast(mainSpecBean.name + " 必须填写");
//                        } else {
//                            queryBean.searchSpec = "";
//                            queryBean.specMinValue = "";
//                        }
//                        }
//                        queryBean.searchSpec = getText(getView(R.id.et_sa_type));
//                        queryBean.specMinValue = et_min_guige.getText().toString();
//                        queryBean.specMaxValue = et_max_guige.getText().toString();


                        queryBean.minHeight = scropView_height.getMin();
                        queryBean.maxHeight = scropView_height.getMax();
                        queryBean.minCrown = scropView_crown.getMin();
                        queryBean.maxCrown = scropView_crown.getMax();
                        queryBean.minRod = scropView_rod.getMin();
                        queryBean.maxRod = scropView_rod.getMax();


//                        if (!TextUtils.isEmpty(queryBean.specMinValue) || !TextUtils.isEmpty(queryBean.specMaxValue)) {
//                            if (TextUtils.isEmpty(queryBean.searchSpec)) {
//                                ToastUtil.showShortToast("确定规格后必须选择规格");
//
//                                return;
//                            }
//                        }

//                        if (!TextUtils.isEmpty(queryBean.searchSpec)) {
//                            if (TextUtils.isEmpty(queryBean.specMinValue) && TextUtils.isEmpty(queryBean.specMaxValue)) {
//                                ToastUtil.showShortToast("选择类型后必须填写规格");
//                                return;
//                            }
//                        }
//                        queryBean.searchKey = searchSpec;
//                        intent.putExtra("cityName", cityName);
//                        intent.putExtra("plantTypes", buffer.toString());

//                        intent.putStringArrayListExtra("planttype_has_ids", planttype_has_ids);
//                        intent.putExtra("searchSpec", searchSpec);
//                        intent.putExtra("specMinValue", et_min_guige.getText().toString());
//                        intent.putExtra("specMaxValue", et_max_guige.getText().toString());
//                        intent.putExtra("searchKey", et_pinming.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("hellow", queryBean);
                        intent.putExtras(bundle);
                        setResult(FILTER_OK, intent);
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
                    Thread.sleep(200);
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private int getItemByName(String cityName, String[] mProvinceDatas) {
        int count = 0;
        for (String iten : mProvinceDatas) {
            if (cityName.contains(iten)) {
                return count;
            }
            count++;
        }
        return 0;
    }


    public static class TypesBean {

        public String code = "";
        public String msg = "";//测试gson 为空时 会不会报错


        public DataBean data;

        public static class DataBean {

            public List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> plantTypeList;

            public List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> specList;

            List<MainSpecBean> mainSpec;
//            MainSpecBean mainSpec = new MainSpecBean();

            public class MainSpecBean implements Serializable {
                public String title;
                public String value;
                /**
                 * "title": "杆径",
                 * "value": "rod",
                 */
                public List<ListBean> list;

                public boolean required = false;

                @Override
                public String toString() {
                    return "MainSpecBean{" +
                            "title='" + title + '\'' +
                            ", value='" + value + '\'' +
                            ", list=" + list +
                            ", required=" + required +
                            '}';
                }
            }

            public class ListBean implements Serializable {
                public String text = "";
                public String min = "";
                public String max = "";

                @Override
                public String toString() {
                    return "ListBean{" +
                            "text='" + text + '\'' +
                            ", min='" + min + '\'' +
                            ", max='" + max + '\'' +
                            '}';
                }
            }

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConstantState.SEARCH_OK && requestCode == 1) {
            queryBean.searchKey = data.getStringExtra("searchKey");
            setText(getView(R.id.tv_import_word), "关键字:" + queryBean.searchKey);
            initSearch();
//            ToastUtil.showShortToast("搜索 关键字 -- -- 刷新- -- " + data.getStringExtra("searchKey"));
        }
    }


    //初始化关键字
    public void initImportWorlds() {
        setText(getView(R.id.tv_import_word), TextUtils.isEmpty(queryBean.searchKey) ? "关键字:" : "关键字:" + queryBean.searchKey);
//        setText(getView(R.id.et_sa_type), TextUtils.isEmpty(queryBean.specMinValue) ? "" : queryBean.specMinValue);
    }

    // 关键字
    public void importWords(View view) {
//        ToastUtil.showShortToast("关键字");
//        Intent intent = new Intent(mActivity, PurchaseSearchListActivity.class);
//        intent.putExtra("from", "SellectActivity2");
//        startActivityForResult(intent, 1);
        PurchaseSearchListActivity.start(mActivity, PurchaseSearchListActivity.FROM_SEARCH);

    }

    //复原  城市名字
    public void resetCity(View view) {
//        ToastUtil.showShortToast("delete");
        tv_area.setText("全国");
        queryBean.cityCode = "";
        childBeans.cityCode = "";
        childBeans.name = "";
    }


    private void getIntentExtral() {

        if (getIntent().getExtras() != null) {
            queryBean = (QueryBean) getIntent().getExtras().getSerializable("queryBean");
            Log.d("SellectActivity2", queryBean.toString());
        }
    }

    public static void start2Activity(Activity context, QueryBean queryBean) {
        Intent intent = new Intent(context, SellectActivity2.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("queryBean", queryBean);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, 110);
    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }
}

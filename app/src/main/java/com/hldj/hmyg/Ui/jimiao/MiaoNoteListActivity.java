package com.hldj.hmyg.Ui.jimiao;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.BActivity_new_test;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.Eactivity3_0;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.PicSerializableMaplist;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.widget.SegmentedGroup;
import com.hldj.hmyg.widget.SortSpinner;
import com.hy.utils.GetServerUrl;
import com.hy.utils.JsonGetInfo;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import me.drakeet.materialdialog.MaterialDialog;
import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;
import me.kaede.tagview.OnTagDeleteListener;
import me.kaede.tagview.Tag;
import me.kaede.tagview.TagView;
import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import static com.hldj.hmyg.R.id.rl_choose_type;

@SuppressLint("NewApi")
public class MiaoNoteListActivity extends NeedSwipeBackActivity implements IXListViewListener, OnCheckedChangeListener {

//    public boolean mIsSelf = false;

    //    private RelativeLayout rl_choose_type;//选择地区。改为 在筛选中   一起选择
//    private ImageView iv_seller_arrow2;
//    private ImageView iv_seller_arrow3;
    private XListView xListView;
    private String orderBy = "";
    private String priceSort = "";
    private String publishDateSort = "";
    private ArrayList<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
    private int pageSize = 10;
    private int pageIndex = 0;
    private MiaoNoteListAdapter listAdapter;
    boolean getdata; // 避免刷新多出数据
    private String noteType = "1";
    FinalHttp finalHttp = new FinalHttp();
    private String minSpec = "";
    private String maxSpec = "";
    private String minHeight = "";
    private String maxHeight = "";
    private String minCrown = "";
    private String maxCrown = "";
    private String name = "";


    private String cityCode = "";
    private String cityName = "全国";


    private TagView tagView;
    public int i = 0;
    MaterialDialog mMaterialDialog;
    private SuperTextView rl_choose_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miao_note_list);


        findViewById(R.id.publish).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMiaoActivity.start(mActivity);
            }
        });


        mMaterialDialog = new MaterialDialog(this);
        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        View tv_b_sort =  findViewById(R.id.tv_b_sort);

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        SegmentedGroup segmented3 = (SegmentedGroup) findViewById(R.id.segmented3);
        RadioButton button31 = (RadioButton) findViewById(R.id.button31);
        RadioButton button32 = (RadioButton) findViewById(R.id.button32);
        button31.setChecked(true);
        segmented3.setOnCheckedChangeListener(this);


        if (Eactivity3_0.showSeedlingNoteShare) {
            segmented3.setVisibility(View.VISIBLE);
            tv_title.setVisibility(View.GONE);
        } else {
            segmented3.setVisibility(View.GONE);
            tv_title.setVisibility(View.VISIBLE);
        }
//        rl_choose_type = (RelativeLayout) findViewById(R.id.rl_choose_type);
//        RelativeLayout rl_choose_price = (RelativeLayout) findViewById(R.id.rl_choose_price);
//        RelativeLayout rl_choose_time = (RelativeLayout) findViewById(R.id.rl_choose_time);
        rl_choose_screen = (SuperTextView) findViewById(R.id.rl_choose_screen);
//        iv_seller_arrow2 = (ImageView) findViewById(R.id.iv_seller_arrow2);
//        iv_seller_arrow3 = (ImageView) findViewById(R.id.iv_seller_arrow3);
        xListView = (XListView) findViewById(R.id.xlistView);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
//        xListView.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1,   int position, long arg3) {
//               intent2SaveMiao();
//
//            }
//        });
        tagView = (TagView) this.findViewById(R.id.tagview);
        tagView.setOnTagDeleteListener(new OnTagDeleteListener() {

            @Override
            public void onTagDeleted(int position, Tag tag) {
                // TODO Auto-generated method stub
                if (tag.id == 1) {
                    cityCode = "";
                    cityName = "";
                    onRefresh();
                } else if (tag.id == 2) {
                    name = "";
                    onRefresh();
                } else if (tag.id == 3) {
                    minSpec = "";
                    maxSpec = "";
                    onRefresh();
                } else if (tag.id == 4) {
                    minHeight = "";
                    maxHeight = "";
                    onRefresh();
                } else if (tag.id == 5) {
                    minCrown = "";
                    maxCrown = "";
                    onRefresh();
                }

            }
        });
        initData();

        MultipleClickProcess multipleClickProcess = new MultipleClickProcess();
//        rl_choose_type.setOnClickListener(multipleClickProcess);
//        rl_choose_price.setOnClickListener(multipleClickProcess);
//        rl_choose_time.setOnClickListener(multipleClickProcess);
        rl_choose_screen.setOnClickListener(multipleClickProcess);
        btn_back.setOnClickListener(multipleClickProcess);
        tv_b_sort.setOnClickListener(multipleClickProcess);

    }

    private void intent2SaveMiao(int position, boolean is) {
        // method stub
        position = position + 1;
        Intent toMiaoNoteListActivity = new Intent(
                MiaoNoteListActivity.this, SaveMiaoActivity.class);
        if ("1".equals(noteType)) {
            toMiaoNoteListActivity = new Intent(
                    MiaoNoteListActivity.this, SaveMiaoActivity.class);
        } else if ("2".equals(noteType)) {

            if (is) {//假如是自己的资源
                toMiaoNoteListActivity = new Intent(
                        MiaoNoteListActivity.this, SaveMiaoActivity.class);
            } else {
                toMiaoNoteListActivity = new Intent(
                        MiaoNoteListActivity.this, MiaoDetailActivity.class);
            }


        }
        Bundle bundleObject = new Bundle();
        final PicSerializableMaplist myMap = new PicSerializableMaplist();
        ArrayList<Pic> ossImagePaths = new ArrayList<Pic>();

//        String  imagesJson =  datas.get(position - 1) .get("imagesJson")+"";

//        D.e("=======imgJson===========" + imagesJson);
//        List<ImagesJsonBean> jsonBeen = new ArrayList<>();
//        if (!TextUtils.isEmpty(imagesJson)) {
//            jsonBeen = GsonUtil.formateJson2Bean(imagesJson, new TypeToken<List<ImagesJsonBean>>() {
//            }.getType());
//        }
//        ossImagePaths.clear();
//        for (int i1 = 0; i1 < jsonBeen.size(); i1++) {
//            ossImagePaths.add(new Pic(jsonBeen.get(i).id,true,
//                    jsonBeen.get(i).ossMediumImagePath,i
//                    ));
//        }

        myMap.setMaplist(ossImagePaths);
//        myMap.setMaplist(ossImagePaths);
        bundleObject.putSerializable("urlPaths", myMap);
        toMiaoNoteListActivity.putExtras(bundleObject);
        toMiaoNoteListActivity.putExtra("id", datas.get(position - 1)
                .get("id").toString());
        toMiaoNoteListActivity.putExtra("addressId",
                datas.get(position - 1).get("addressId").toString());
        toMiaoNoteListActivity.putExtra("count", datas
                .get(position - 1).get("count").toString());
        toMiaoNoteListActivity.putExtra("height",
                datas.get(position - 1).get("height").toString());
        toMiaoNoteListActivity.putExtra("maxHeight",
                datas.get(position - 1).get("maxHeight").toString());
        toMiaoNoteListActivity.putExtra("crown",
                datas.get(position - 1).get("crown").toString());
        toMiaoNoteListActivity.putExtra("maxCrown",
                datas.get(position - 1).get("maxCrown").toString());
        toMiaoNoteListActivity.putExtra("contactName",
                datas.get(position - 1).get("contactName").toString());
        toMiaoNoteListActivity.putExtra("contactPhone",
                datas.get(position - 1).get("contactPhone").toString());
        toMiaoNoteListActivity.putExtra("address",
                datas.get(position - 1).get("address").toString());
        toMiaoNoteListActivity.putExtra("name", datas.get(position - 1)
                .get("name").toString());
        toMiaoNoteListActivity.putExtra("price", datas
                .get(position - 1).get("price").toString());
        toMiaoNoteListActivity.putExtra("minSpec",
                datas.get(position - 1).get("minSpec").toString());
        toMiaoNoteListActivity.putExtra("maxSpec",
                datas.get(position - 1).get("maxSpec").toString());
        toMiaoNoteListActivity.putExtra("isDefault", (Boolean) datas
                .get(position - 1).get("isDefault"));
        startActivityForResult(toMiaoNoteListActivity, 1);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

//    private boolean isSelfRes() {
//        ToastUtil.showShortToast(" mIsSelf= " + mIsSelf);
//        return mIsSelf;
//    }

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
                    case rl_choose_type:
//                        showCitys();
                        break;

                    case R.id.tv_b_sort:

                        ChoiceSortList();
                        BActivity_new_test.setColor(getView(R.id.rl_choose_screen), getView(R.id.tv_b_sort), "1", mActivity);
                        //排序
                        /*    //筛选
        getView(R.id.tv_b_filter).setOnClickListener(v -> {
            SellectActivity2.start2Activity(this, queryBean);
            setColor(getView(R.id.tv_b_filter), getView(R.id.tv_b_sort), "0",mActivity);
        });

        //排序
        getView(R.id.tv_b_sort).setOnClickListener(v -> {
            ChoiceSortList();
            setColor(getView(R.id.tv_b_filter), getView(R.id.tv_b_sort), "1",mActivity);
        });*/

                        //ll_fil_content


                        break;

//                    case R.id.rl_choose_price:
//                        if ("".equals(priceSort)) {
//                            priceSort = "price_asc";
//                            iv_seller_arrow2
//                                    .setImageResource(R.drawable.icon_seller_arrow2);
//                        } else if ("price_asc".equals(priceSort)) {
//                            priceSort = "price_desc";
//                            iv_seller_arrow2
//                                    .setImageResource(R.drawable.icon_seller_arrow3);
//                        } else if ("price_desc".equals(priceSort)) {
//                            priceSort = "";
//                            iv_seller_arrow2
//                                    .setImageResource(R.drawable.icon_seller_arrow1);
//                        }
//                        onRefresh();
//                        break;
//                    case R.id.rl_choose_time:
//                        if ("".equals(publishDateSort)) {
//                            publishDateSort = "createDate_asc";
//                            iv_seller_arrow3
//                                    .setImageResource(R.drawable.icon_seller_arrow2);
//                        } else if ("createDate_asc".equals(publishDateSort)) {
//                            publishDateSort = "createDate_desc";
//                            iv_seller_arrow3
//                                    .setImageResource(R.drawable.icon_seller_arrow3);
//                        } else if ("createDate_desc".equals(publishDateSort)) {
//                            publishDateSort = "";
//                            iv_seller_arrow3
//                                    .setImageResource(R.drawable.icon_seller_arrow1);
//                        }
//                        onRefresh();
//                        break;
                    case R.id.rl_choose_screen:
                        //筛选
                        Intent toSellectActivity = new Intent(
                                MiaoNoteListActivity.this,
                                SellectMiaoActivity.class);
                        toSellectActivity.putExtra("minHeight", minHeight);
                        toSellectActivity.putExtra("maxHeight", maxHeight);
                        toSellectActivity.putExtra("minCrown", minCrown);
                        toSellectActivity.putExtra("maxCrown", maxCrown);
                        toSellectActivity.putExtra("minSpec", minSpec);
                        toSellectActivity.putExtra("maxSpec", maxSpec);
                        toSellectActivity.putExtra("name", name);


                        toSellectActivity.putExtra("cityCode", cityCode);
                        toSellectActivity.putExtra("cityName", cityName);

                        BActivity_new_test.setColor(rl_choose_screen, getView(R.id.tv_b_sort), "0", mActivity);

                        startActivityForResult(toSellectActivity, 1);
                        overridePendingTransition(R.anim.slide_in_left,
                                R.anim.slide_out_right);

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


    SortSpinner sortSpinner;
    int pos = 0;

    private void ChoiceSortList() {

        if (sortSpinner == null) {
            sortSpinner = SortSpinner.getInstance(mActivity, getView(R.id.ll_fil_content))
                    .addOnItemClickListener((parent, view1, position, id) -> {
                        D.e("addOnItemClickListener" + position);
                        switch (position) {
                            case 0:
                                orderBy = "default_asc";//综合排序
                                break;
                            case 1:
                                orderBy = "publishDate_desc";//最新发布
                                break;
                            case 2:
                                orderBy = "distance_asc";//最近距离
                                break;
                            case 3:
                                orderBy = "price_asc";//价格从低到高
                                break;
                            case 4:
                                orderBy = "price_desc";//综合排序
                                break;
                        }
                        pos = position;
                        sortSpinner.dismiss();
                        onRefresh();
                    });

            sortSpinner.ShowWithPos(pos);
        } else {
            try {
                sortSpinner.ShowWithPos(pos);
            } catch (Exception e) {
                D.e("==baocuo==" + e.getMessage());
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 9) {
            minHeight = data.getStringExtra("minHeight");
            maxHeight = data.getStringExtra("maxHeight");
            minCrown = data.getStringExtra("minCrown");
            maxCrown = data.getStringExtra("maxCrown");
            minSpec = data.getStringExtra("minSpec");
            maxSpec = data.getStringExtra("maxSpec");
            name = data.getStringExtra("name");

            cityCode = data.getStringExtra("cityCode");
            cityName = data.getStringExtra("cityName");

            // TODO:   需要多2个 city code  city name

//            List<Tag> tags = tagView.getTags();

            tagView.removeAllTags();
//            for (int i = 0; i < tags.size(); i++) {
//                if (tags.get(i).id == 1 ||
//                        tags.get(i).id == 2 || tags.get(i).id == 3
//                        || tags.get(i).id == 4 || tags.get(i).id == 5) {
//                    tagView.remove(i);
//                }
//            }

            if (!"".equals(cityCode)) {
                Tag tag = new Tag(cityName);
                tag.layoutColor = getResources().getColor(R.color.main_color);
                tag.isDeletable = true;
                tag.id = 1; // 1 城市 2.品名 3.规格 4.高度 5.冠幅
                tagView.addTag(tag);
            }

            if (!"".equals(name)) {
                Tag tag = new Tag(name);
                tag.layoutColor = getResources().getColor(R.color.main_color);
                tag.isDeletable = true;
                tag.id = 2; // 1 城市 2.品名 3.规格 4.高度 5.冠幅
                tagView.addTag(tag);
            }
            if (!"".equals(minSpec) || !"".equals(maxSpec)) {
                Tag tag = new Tag("规格：" + FUtil.$("-", minSpec, maxSpec));
//                        + minSpec + "-" + maxSpec);
                tag.layoutColor = getResources().getColor(R.color.main_color);
                tag.isDeletable = true;
                tag.id = 3; // 1 城市 2.品名 3.规格 4.高度 5.冠幅
                tagView.addTag(tag);
            }
            if (!"".equals(minHeight) || !"".equals(maxHeight)) {
                Tag tag = new Tag("高度：" + FUtil.$("-", minHeight, maxHeight));
//                        + minHeight + "-" + maxHeight);
                tag.layoutColor = getResources().getColor(R.color.main_color);
                tag.isDeletable = true;
                tag.id = 4; // 1 城市 2.品名 3.规格 4.高度 5.冠幅
                tagView.addTag(tag);
            }
            if (!"".equals(minCrown) || !"".equals(maxCrown)) {


                Tag tag = new Tag("冠幅：" + FUtil.$("-", minCrown, maxCrown));
//                        + minCrown + "-" + maxCrown);


                tag.layoutColor = getResources().getColor(R.color.main_color);
                tag.isDeletable = true;
                tag.id = 5; // 1 城市 2.品名 3.规格 4.高度 5.冠幅
                tagView.addTag(tag);
            }

            onRefresh();
        } else if (resultCode == 8) {
            onRefresh();
        } else if (resultCode == 10) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    startActivity(new Intent(mActivity, SaveMiaoActivity.class));
//                }
//            }, 300);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        xListView.setPullLoadEnable(false);
        pageIndex = 0;
        datas.clear();
        if (listAdapter == null) {
            listAdapter = new MiaoNoteListAdapter(MiaoNoteListActivity.this, datas);
            xListView.setAdapter(listAdapter);
            listAdapter.setMyItemLisContent(new MiaoNoteListAdapter.MyItemClickLister() {
                @Override
                public void OnItemDel(int pos, String id, boolean isSelf) {
                    intent2SaveMiao(pos, isSelf);
                }
            });

            initAptLis(listAdapter);

        } else {
            listAdapter.notifyDataSetChanged();
        }
        if (getdata == true) {
            initData();
        }
        onLoad();
    }

    private void initAptLis(MiaoNoteListAdapter mlistAdapter) {

        if (mlistAdapter != null && mlistAdapter.myItemClickLister == null) {


            mlistAdapter.setMyItemLis(new MiaoNoteListAdapter.MyItemClickLister() {
                @Override
                public void OnItemDel(int position, String mIid, boolean isSelf) {

                    if ("1".equals(noteType) || isSelf) {
                        i = position;
                        Log.i("setMyItemLis", "OnItemDel: " + mIid);
                        myid = mIid;
                        Log.i("setMyItemLis", "OnItemDel: " + myid);
                        if (mMaterialDialog != null) {
                            mMaterialDialog.setMessage("确定删除这条资源？")
                                    // mMaterialDialog.setBackgroundResource(R.drawable.background);
                                    .setPositiveButton(getString(R.string.ok), new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            doDel(myid);
                                            Log.i("doDel", "OnItemDel: " + mIid);
                                            mMaterialDialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton(getString(R.string.cancle), new OnClickListener() {
                                        public void onClick(View v) {
                                            mMaterialDialog.dismiss();
                                        }
                                    })
                                    .setCanceledOnTouchOutside(
                                            true)
                                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(
                                                DialogInterface dialog) {
                                        }
                                    })
                                    .show();
                        } else {
                        }
                    } else {
                        Toast.makeText(MiaoNoteListActivity.this,
                                "共享资源您没有权限删除哦",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }

//                                            @Override
//                                            public void OnSelf(boolean isSelf) {
//
//                                            }
            });
        }
    }

    private void initData() {
        // TODO Auto-generated method stub
        getdata = false;
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("pageSize", pageSize + "");
        params.put("pageIndex", pageIndex + "");
        if ("".equals(priceSort) && !"".equals(publishDateSort)) {
            orderBy = publishDateSort;
        } else if (!"".equals(priceSort) && "".equals(publishDateSort)) {
            orderBy = priceSort;
        } else if ("".equals(priceSort) && "".equals(publishDateSort)) {
            orderBy = "";
        } else {
            orderBy = priceSort + "," + publishDateSort;
        }

        if (orderBy.endsWith(",")) {
            orderBy = orderBy.substring(0, orderBy.length() - 1);
        }
        params.put("orderBy", orderBy);
        params.put("noteType", noteType);
        params.put("minSpec", minSpec);
        params.put("maxSpec", maxSpec);
        params.put("minHeight", minHeight);
        params.put("maxHeight", maxHeight);
        params.put("minCrown", minCrown);
        params.put("maxCrown", maxCrown);
        params.put("name", name);
        params.put("cityCode", cityCode);
        finalHttp.post(
                GetServerUrl.getUrl() + "admin/seedlingNote/manage/list",
                params, new AjaxCallBack<Object>() {

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
                            }
                            if ("1".equals(code)) {
                                JSONObject jsonObject2 = jsonObject
                                        .getJSONObject("data").getJSONObject(
                                                "page");
                                int total = JsonGetInfo.getJsonInt(jsonObject2,
                                        "total");
                                if (JsonGetInfo.getJsonArray(jsonObject2,
                                        "data").length() > 0) {
                                    JSONArray jsonArray = JsonGetInfo
                                            .getJsonArray(jsonObject2, "data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject3 = jsonArray
                                                .getJSONObject(i);
                                        HashMap<String, Object> hMap = new HashMap<String, Object>();
                                        hMap.put("noteType", noteType);
                                        hMap.put("name", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "name"));
                                        hMap.put("id", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "id"));


                                        hMap.put("ownerId", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "ownerId"));


                                        hMap.put("cityCode", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "cityCode"));
                                        hMap.put("cityName", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "cityName"));
                                        hMap.put("price", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "price"));
                                        hMap.put("count", JsonGetInfo
                                                .getJsonInt(jsonObject3,
                                                        "count"));
                                        hMap.put("minSpec", JsonGetInfo
                                                .getJsonInt(jsonObject3,
                                                        "minSpec"));
                                        hMap.put("maxSpec", JsonGetInfo
                                                .getJsonInt(jsonObject3,
                                                        "maxSpec"));
                                        hMap.put("crown", JsonGetInfo
                                                .getJsonInt(jsonObject3,
                                                        "minCrown"));
                                        hMap.put("maxCrown", JsonGetInfo
                                                .getJsonInt(jsonObject3,
                                                        "maxCrown"));
                                        hMap.put("height", JsonGetInfo
                                                .getJsonInt(jsonObject3,
                                                        "minHeight"));
                                        hMap.put("maxHeight", JsonGetInfo
                                                .getJsonInt(jsonObject3,
                                                        "maxHeight"));
                                        /*图片*/
                                        hMap.put("imagesJson", JsonGetInfo
                                                .getJsonString(jsonObject3,
                                                        "imagesJson"));


                                        JSONObject nurseryJson = JsonGetInfo
                                                .getJSONObject(jsonObject3,
                                                        "nurseryJson");
                                        JSONObject ciCity = JsonGetInfo
                                                .getJSONObject(jsonObject3,
                                                        "ciCity");

                                        /*日期  */
                                        hMap.put("nurseryJson_createDate", JsonGetInfo
                                                .getJsonString(nurseryJson,
                                                        "createDate"));

                                        /*苗圃名称*/
                                        hMap.put("nurseryJson_name", JsonGetInfo
                                                .getJsonString(nurseryJson,
                                                        "name"));


                                        /*联系人*/
                                        hMap.put("contactName", JsonGetInfo
                                                .getJsonString(nurseryJson,
                                                        "contactName"));
                                        /*联系电话*/
                                        hMap.put("contactPhone", JsonGetInfo
                                                .getJsonString(nurseryJson,
                                                        "contactPhone"));

                                        /*发布人*/
                                        hMap.put("ownerName", JsonGetInfo
                                                .getJsonString(nurseryJson,
                                                        "ownerName"));


                                        /*发布电话*/
                                        hMap.put("ownerPhone", JsonGetInfo
                                                .getJsonString(nurseryJson,
                                                        "ownerPhone"));





                                        hMap.put("isDefault", JsonGetInfo
                                                .getJsonBoolean(nurseryJson,
                                                        "isDefault"));
                                        hMap.put("addressId", JsonGetInfo
                                                .getJsonString(nurseryJson,
                                                        "id"));
                                        hMap.put("fullName", JsonGetInfo
                                                .getJsonString(ciCity,
                                                        "fullName"));


                                        hMap.put(
                                                "address",
                                                JsonGetInfo
                                                        .getJsonString(
                                                                nurseryJson,
                                                                "cityName")
                                                        + JsonGetInfo
                                                        .getJsonString(
                                                                nurseryJson,
                                                                "detailAddress"));
                                        datas.add(hMap);
                                        if (listAdapter != null) {
                                            listAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    if (listAdapter == null) {
                                        listAdapter = new MiaoNoteListAdapter(MiaoNoteListActivity.this, datas);
                                        xListView.setAdapter(listAdapter);

                                        listAdapter.setMyItemLisContent(new MiaoNoteListAdapter.MyItemClickLister() {
                                            @Override
                                            public void OnItemDel(int pos, String id, boolean isSelf) {
                                                intent2SaveMiao(pos, isSelf);
                                            }
                                        });


                                        initAptLis(listAdapter);
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
                        super.onFailure(t, errorNo, strMsg);
                    }

                });
        getdata = true;
    }

    String myid = "";


    public int newPos;

    //    private void doDel(String id, int pos) {
    private void doDel(String id) {
        D.e("=======doDel======" + " id = " + id + "\n " + "pos---i=" + i);
        // TODO Auto-generated method stub
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("ids", id);
        finalHttp.post(GetServerUrl.getUrl() + "admin/seedlingNote/doDel",
                params, new AjaxCallBack<Object>() {

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
                            }
                            if ("1".equals(code)) {
                                datas.remove(i);
                                listAdapter.notifyDataSetChanged();
//                                listAdapter.notifyDel(pos);
                            } else {
                                Toast.makeText(MiaoNoteListActivity.this,
                                        "删除失败", Toast.LENGTH_SHORT).show();
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
                        super.onFailure(t, errorNo, strMsg);
                    }

                });

    }

    @Override
    public void onLoadMore() {
        xListView.setPullRefreshEnable(false);
        initData();
        onLoad();
    }

    private void onLoad() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                xListView.stopRefresh();
                xListView.stopLoadMore();
                xListView.setRefreshTime(new Date().toLocaleString());
                xListView.setPullLoadEnable(true);
                xListView.setPullRefreshEnable(true);
            }
        }, com.hldj.hmyg.application.Data.refresh_time);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.button31:
                noteType = "1";
                // 我的资源
                onRefresh();
                break;
            case R.id.button32:
                noteType = "2";
                // 共享资源
                onRefresh();
                break;
            default:
                // Nothing to do
        }
    }


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MiaoNoteListActivity.class);
        activity.startActivityForResult(intent, 110);
    }

}

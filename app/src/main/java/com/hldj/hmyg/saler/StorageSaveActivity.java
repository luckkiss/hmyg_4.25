package com.hldj.hmyg.saler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.DaoBean.SaveJson.DaoSession;
import com.hldj.hmyg.DaoBean.SaveJson.SavaBean;
import com.hldj.hmyg.DaoBean.SaveJson.SavaBeanDao;
import com.hldj.hmyg.M.BProduceAdapt;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.base.MySwipeAdapter;
import com.hldj.hmyg.base.ViewHolders;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.M.ImagesJsonBean;
import com.hldj.hmyg.presenter.SaveSeedlingPresenter;
import com.hldj.hmyg.util.ConstantParams;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zf.iosdialog.widget.AlertDialog;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

//草稿界面 草稿箱
public class StorageSaveActivity extends NeedSwipeBackActivity implements OnClickListener {
    private ListView GroupManList;
    private BaseAdapter baseAdapter;

    private static String DB_NAME = "flower.db";
    private static final String DB_TABLE = "savetable";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    private boolean flag = true; // 全选或全取消
    private FinalBitmap fb;
    private Gson gson;
    private ImageView btn_back;
    private TextView tv_all;
    private TextView tv_begin;
    private int progress = 0;
    KProgressHUD hud_numHud; // 上传时显示的等待框

    /**
     * Called when the activity is first created.
     */
    @SuppressLint("SdCardPath")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_save);
        gson = new Gson();
        fb = FinalBitmap.create(this);
        fb.configLoadingImage(R.drawable.no_image_show);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        tv_all = (TextView) findViewById(R.id.tv_all);
        tv_begin = (TextView) findViewById(R.id.tv_begin);
        id_tv_edit_all = (TextView) findViewById(R.id.id_tv_edit_all);
        btn_back.setOnClickListener(this);

        hud_numHud = KProgressHUD.create(StorageSaveActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("上传中，请等待...")
                .setCancellable(true);

        initView();

        initDao();
        initData();


//        TViewUtil.EmptyViewBuilder.getInstance(StorageSaveActivity.this)
//                .setEmptyText(getResources().getString(R.string.nodata))
//                .setEmptyTextSize(12).setEmptyTextColor(Color.GRAY)
//                .setShowButton(false)
//                .setActionText(getResources().getString(R.string.reload))
//                .setAction(new OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // Toast.makeText(getApplicationContext(),
//                        // "Yes, clicked~",Toast.LENGTH_SHORT).show();
//                        onRefresh();
//                    }
//                }).setShowIcon(false).setShowText(false).bindView(GroupManList);
        btn_back.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        tv_begin.setOnClickListener(this);
        id_tv_edit_all.setOnClickListener(this);
    }


    DaoSession daoSession;
    SavaBeanDao savaBeanDao;
    QueryBuilder<SavaBean> qb;//qb  数据库列表

    private void initDao() {

        daoSession = MyApplication.getInstance().getDaoSession();

        savaBeanDao = daoSession.getSavaBeanDao();

        qb = savaBeanDao.queryBuilder();
        qb.orderAsc(SavaBeanDao.Properties.Id);


    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private MyAdapter_now myadapter;
    private TextView id_tv_edit_all;


    private List<SaveSeedingGsonBean.DataBean.SeedlingBean> seedlingBeens = new ArrayList<>();
    private List<SaveSeedingGsonBean> list_saveSeedingGsonBeans = new ArrayList<>();

    /**
     * 从数据库中获取数据 dao
     */
    private void initData() {

        List<SavaBean> list = qb.list();
        D.e("============" + list.toString());
        list_saveSeedingGsonBeans.clear();
        seedlingBeens.clear();
        for (int i = 0; i < list.size(); i++) {
            SaveSeedingGsonBean saveSeedingGsonBeans = GsonUtil.formateJson2Bean(list.get(i).getJson(), SaveSeedingGsonBean.class);
            list_saveSeedingGsonBeans.add(saveSeedingGsonBeans);
            seedlingBeens.add(saveSeedingGsonBeans.getData().getSeedling());
        }

        myadapter.setDatas(seedlingBeens);

    }


    /*
     * 初始化View
     */
    private void initView() {

//        SaveSeedingGsonBean saveSeedingGsonBean = formateJson2Bean(SPUtil.get(this, "save_sp", "").toString(), SaveSeedingGsonBean.class);
//        SaveSeedingGsonBean.PurchaseItemBean_new.SeedlingBean seedlingBean = saveSeedingGsonBean.getData().getSeedling();
//        List<SaveSeedingGsonBean.PurchaseItemBean_new.SeedlingBean> seedlingBeen = new ArrayList<>();
//        seedlingBeen.add(seedlingBean);
        myadapter = new MyAdapter_now(this, seedlingBeens, R.layout.list_view_seedling_new_shoucan);
//        myadapter = new MyAdapter_now(this, seedlingBeens, R.layout.manager_group_list_item_new);
        GroupManList = (ListView) findViewById(R.id.GroupManList);
        GroupManList.setAdapter(myadapter);
//        GroupManList.setOnItemClickListener((parent, view, position, id) -> {
//            SaveSeedlingActivity_show_history.start2Activity(StorageSaveActivity.this, seedlingBeens.get(position));
//        });
    }


    public void RemoveItems() {
        D.e("========delete=========");

        QueryBuilder<SavaBean> qb = savaBeanDao.queryBuilder();
        qb.orderAsc(SavaBeanDao.Properties.Id);
        List<SavaBean> list = qb.list();
        for (int i = 0; i < myadapter.checks.length; i++) {
            if (myadapter.checks[i]) {
                D.e("========删除选项============" + i);
                savaBeanDao.delete(list.get(i));
            }
        }
        D.e("========重新初始化============");
    }

    public void RemoveOne(int pos) {

        for (int i = 0; i < qb.list().size(); i++) {

            if (pos == i) {
                savaBeanDao.delete(qb.list().get(i));
                initData();
            }

        }

    }

    public List<SavaBean> QueryAll() {

        D.e("========delete=========");
        QueryBuilder<SavaBean> qb = savaBeanDao.queryBuilder();
        qb.orderAsc(SavaBeanDao.Properties.Id);
        return qb.list();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;

            case R.id.id_tv_edit_all:
                new AlertDialog(this).builder()
                        .setTitle("确定删除所选草稿?")
                        .setPositiveButton("确定删除", v1 -> {
                            dele();//删除
                        }).setNegativeButton("取消", v2 -> {
                }).show();
                break;
            case R.id.tv_all:
                selectedAll();

                break;
            case R.id.tv_begin:
                AjaxParams params = new AjaxParams();
                if (!submit(params)) {
                    return;
                }
                fabu(params);

                break;

            default:
                break;
        }
    }

    /**
     * 删除代码
     */
    public void dele() {

        initDao();

        List<SavaBean> list = qb.list();


        for (int i = 0; i < myadapter.checks.length; i++) {
            if (myadapter.checks[i]) {
                D.e("========删除选项============" + i);
                savaBeanDao.delete(list.get(i));
            }

        }
        D.e("========重新初始化============");
        initData();
    }

    ArrayList arrayList;

    List<Pic> onlinePics = new ArrayList<>();

    private void fabu(AjaxParams params) {
        errorNum = 0;
        onlinePics.clear();

        //执行上传图片接口
        if (list_imagejsons == null) {
            return;
        }

        arrayList = new ArrayList<Pic>();
        for (int i = 0; i < list_imagejsons.size(); i++) {
            arrayList.add(new Pic(list_imagejsons.get(i).getId(), false, list_imagejsons.get(i).getLocal_url(), i));
        }

        if (arrayList.size() != 0) {
            hud_numHud.show();
            hud_numHud.updateLable("正在上传第" + (1) + "/" + arrayList.size() + "张图片");
            //   上传图片  可能多图片
            new SaveSeedlingPresenter(mActivity).upLoad(arrayList, new ResultCallBack<Pic>() {
                @Override
//                        public void onSuccess(UpImageBackGsonBean imageBackGsonBean) {//
                public void onSuccess(Pic pic) {//
                    hud_numHud.updateLable("已上传第" + (pic.getSort() + 1) + "/" + arrayList.size() + "张图片");
                    if (!TextUtils.isEmpty(pic.getUrl())) {
                        onlinePics.add(pic);
//                        listPicsOnline.add(pic);
                    } else {
                        errorNum++;
                        ToastUtil.showLongToast("有图片损坏，您可以修改后重新上传！");
                    }


                    if ((onlinePics.size() + errorNum) == arrayList.size()) {
                        if (onlinePics.size() == 0) {
                            ToastUtil.showLongToast("请上传图片");
                            hindLoading();
                            return;
                        }
                        hud_numHud.updateLable("图片上传成功");

                        putParams(params, "imagesData", GsonUtil.Bean2Json(onlinePics));
                        seedlingSave(params);
                    }
//                urlPaths.add(pic);
//                a = pic.getSort();
//                a++;
//                hudProgress();


                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    hud_numHud.dismiss();
                    ToastUtil.showShortToast("上传图片失败");
                }
            });
        } else {
            seedlingSave(params);
        }


        //执行发布接口


        D.e("===没有问题可以发布==");
    }

    public int errorNum = 0;


    private void seedlingSave(AjaxParams ajaxParams) {
        hud_numHud.dismiss();
        showLoading();
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);

        finalHttp.post(GetServerUrl.getUrl() + "admin/seedling/save", ajaxParams, new AjaxCallBack<String>() {
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                // TODO Auto-generated method stub
                Toast.makeText(StorageSaveActivity.this,
                        R.string.error_net, Toast.LENGTH_SHORT).show();
                hindLoading();
                if (hud_numHud != null) {
                    hud_numHud.dismiss();
                }
            }

            @Override
            public void onSuccess(String json) {
                D.e("=============json==========" + json);


                SimpleGsonBean simpleGsonBean = GsonUtil.formateJson2Bean(json, SimpleGsonBean.class);

                if (simpleGsonBean.code.equals("1")) {
                    //成功
                    ToastUtil.showShortToast("提交完毕");
                    RemoveOne(myadapter.change_item);
                    setResult(ConstantState.PUBLIC_SUCCEED);
                    finish();
//                    ManagerListActivity_new.start2Activity(StorageSaveActivity.this);
                } else {
                    ToastUtil.showShortToast(simpleGsonBean.msg);
                }
                hud_numHud.dismiss();
                hindLoading();

            }
        });

    }

    public void putParams(AjaxParams ajaxParams, String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            D.e("===参数===" + key + "-------->" + "" + value);
            ajaxParams.put(key, value);
        } else {
            D.e("===参数===" + key + "==的值是空的==");
        }

    }


    List<ImagesJsonBean> list_imagejsons = null;


    public SaveSeedingGsonBean getGsonBean() {
        int count = 0;
        for (int i = 0; i < myadapter.checks.length; i++) {

            if (myadapter.checks[i]) {
                D.e("========已选选项============" + i);
                count++;
                return list_saveSeedingGsonBeans.get(i);
            }
        }

        if (count == 0) {
            ToastUtil.showShortToast("请选择要发布的一个项目!");
            return null;
        }
        return null;
    }

    private boolean submit(AjaxParams params) {
//        list_saveSeedingGsonBeans.get(0);

        //判断是否可以上传
        SaveSeedingGsonBean saveSeedingGsonBean = getGsonBean();
        if (saveSeedingGsonBean == null) {
            return false;
        }
        SaveSeedingGsonBean.DataBean.SeedlingBean seedlingBean = saveSeedingGsonBean.getData().getSeedling();
        List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeanlist = saveSeedingGsonBean.getData().getTypeList();
        List<SaveSeedingGsonBean.DataBean.TypeListBean.ParamsListBean> paramsListBeen = null;


        List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> plantTypeListBeen = saveSeedingGsonBean.getData().getPlantTypeList();

        //step 1    判断图片  2个大的分类tag
        list_imagejsons = seedlingBean.getImagesJson();
        if (list_imagejsons.size() == 0) {
            ToastUtil.showShortToast("上传图片不能为空");
            return false;
        }
//        String imagejson = GsonUtil.Bean2Json(seedlingBean.getImagesJson());

//        if (!checkNoNull("上传图片", "imagesData", imagejson, params)) return false;

        if (!checkNoNull("品种分类", "firstSeedlingTypeId", seedlingBean.getFirstSeedlingTypeId(), params))
            return false;

        if (!checkNoNull("苗木分类", "secondSeedlingTypeId", seedlingBean.getSecondSeedlingTypeId(), params))
            return false;


        for (int i = 0; i < typeListBeanlist.size(); i++) {
            if (seedlingBean.getFirstSeedlingTypeId().equals(typeListBeanlist.get(i).getId())) {
                D.e("==i==" + i);
                paramsListBeen = typeListBeanlist.get(i).getParamsList();
            }
        }
        if (paramsListBeen == null) {
            checkNoNull("苗木分类", "firstSeedlingTypeId", "", params);
            return false;
        }


        //step2  动态添加的数据判断
        for (SaveSeedingGsonBean.DataBean.TypeListBean.ParamsListBean item : paramsListBeen)

        {

            if (!submitParamsLis(item, seedlingBean, params)) {
                return false;
            }

        }


        if (!checkNoNull("种植类型", "plantType", seedlingBean.getPlantType(), params)) return false;


        //step3  bottom 上传数据判断


        if (!checkNoNull("品名", "name", seedlingBean.getName() + "", params))
            return false;

        if (seedlingBean.isNego())//面议
        {
            params.put("isNego", true + "");//是否面议

        } else {
//            if (!checkNoNull("最低价格", "minPrice", seedlingBean.getMinPrice() + "", params))
//                return false;
            if (!checkNoNullDouble("最低价格或最高价格", "maxPrice", seedlingBean.getMaxPrice(), "minPrice", seedlingBean.getMinPrice() + "", params))
                return false;
        }


        if (!checkNoNull("库存", "count", seedlingBean.getCount() + "", params)) return false;

        if (!checkNoNull("单位", "unitType", seedlingBean.getUnitType(), params))
            return false;

        if (!checkNoNull("地址", "nurseryId", seedlingBean.getNurseryId(), params)) return false;

        return checkNoNull("有效期", "validity", seedlingBean.getValidity() + "", params);

    }

    private boolean submitParamsLis(SaveSeedingGsonBean.DataBean.TypeListBean.ParamsListBean item, SaveSeedingGsonBean.DataBean.SeedlingBean seedlingBean, AjaxParams params) {
//        if (item.isRequired()) {
        item.getName();
        String value = item.getValue();

        if (value.equals(ConstantParams.height))//高度
        {
            return checkNoNullDouble("高度的最大值或最小值", "maxHeight", seedlingBean.getMaxHeight() + "",
                    "minHeight", seedlingBean.getMinHeight() + "", params, item.isRequired());

//            if (item.isRequired())
//            {
//                return !checkNoNullDouble("高度的最大值或最小值", "maxHeight", seedlingBean.getMaxHeight() + "", "minHeight", seedlingBean.getMinHeight() + "", params) ;
//            }else {
//                return true;
//            }
//
//            if (!checkNoNullDouble("高度的最大值或最小值", "maxHeight", seedlingBean.getMaxHeight() + "", "minHeight", seedlingBean.getMinHeight() + "", params))
////                     && !checkNoNull("高度的最大值或最小值", "minHeight", seedlingBean.getMinHeight() + "", params)
//                return !item.isRequired();
////                if (!checkNoNull("高度的最小值", "minHeight", seedlingBean.getMinHeight() + "", params))
////                    return false;
        }
        if (value.equals(ConstantParams.crown))//冠幅
        {

            return checkNoNullDouble("冠幅的最大值或最小值", "maxCrown", seedlingBean.getMaxCrown() + "",
                    "minCrown", seedlingBean.getMinCrown() + "", params, item.isRequired());
//            if (!checkNoNullDouble("冠幅的最大值或最小值", "maxCrown", seedlingBean.getMaxCrown() + "", "minCrown", seedlingBean.getMinCrown() + "", params))
////                if (!checkNoNull("冠幅的最大值或最小值", "maxCrown", seedlingBean.getMaxCrown() + "", params) && !checkNoNull("冠幅的最大值或最小值", "minCrown", seedlingBean.getMinCrown() + "", params))
//
//
//                return !item.isRequired();
//                if (!checkNoNull("冠幅的最小值", "minCrown", seedlingBean.getMinCrown() + "", params))
//                    return false;
        }
        if (value.equals(ConstantParams.dbh))//胸径
        {
            putParams(params, "dbhType", seedlingBean.getDbhType());
            return
                    checkNoNullDouble("胸径或米径的最大值或最小值", "maxDbh", seedlingBean.getMaxDbh() + "", "minDbh", seedlingBean.getMinDbh() + "", params, item.isRequired());

//                    checkNoNull("胸径的最大值或最小值", "maxDbh", seedlingBean.getMaxDbh() + "", params)
//                    && !checkNoNull("胸径的最大值或最小值", "minDbh", seedlingBean.getMinDbh() + "", params)
//                    || item.isRequired();

//            if (!checkNoNullDouble("冠幅的最大值或最小值", "maxDbh", seedlingBean.getMaxDbh() + "", "minDbh", seedlingBean.getMinDbh() + "", params))
////                    if (!checkNoNull("胸径的最大值或最小值", "maxDbh", seedlingBean.getMaxDbh() + "", params) && !checkNoNull("胸径的最大值或最小值", "minDbh", seedlingBean.getMinDbh() + "", params))
//
//                putParams(params, "dbhType", seedlingBean.getDbhType());
//
//            return !item.isRequired();
//                if (!checkNoNull("胸径的最小值", "minDbh", seedlingBean.getMinDbh() + "", params))
//                    return false;
        }
        if (value.equals(ConstantParams.diameter))//地径
        {
            putParams(params, "diameterType", seedlingBean.getDiameterType());

            return checkNoNullDouble("地径的最大值或最小值", "maxDiameter", seedlingBean.getMaxDiameter() + "",
                    "minDiameter", seedlingBean.getMinDiameter() + "", params, item.isRequired())
                    ;


//            if (!checkNoNullDouble("地径的最大值或最小值", "maxDiameter", seedlingBean.getMaxDiameter() + "", "minDiameter", seedlingBean.getMinDiameter() + "", params))
////               if (!checkNoNull("地径的最大值或最小值", "maxDiameter", seedlingBean.getMaxDiameter() + "", params) && !checkNoNull("地径的最大值或最小值", "minDiameter", seedlingBean.getMinDiameter() + "", params))
//                return false;
//                if (!checkNoNull("地径的最小值", "minDiameter", seedlingBean.getMinDiameter() + "", params))
//                    return false;
        }
        if (value.equals(ConstantParams.offbarHeight))//脱杆高
        {
            return checkNoNullDouble("脱杆高的最大值或最小值", "maxOffbarHeight", seedlingBean.getMaxOffbarHeight() + "",
                    "minOffbarHeight", seedlingBean.getMinOffbarHeight() + "", params, item.isRequired())
                    ;

//            if (!checkNoNullDouble("脱杆高的最大值或最小值", "maxOffbarHeight", seedlingBean.getMaxOffbarHeight() + "", "minOffbarHeight", seedlingBean.getMinOffbarHeight() + "", params))
////                if (!checkNoNull("脱杆高的最大值或最小值", "maxOffbarHeight", seedlingBean.getMaxOffbarHeight() + "", params) && !checkNoNull("脱杆高的最大值或最小值", "minOffbarHeight", seedlingBean.getMinOffbarHeight() + "", params))
//                return !item.isRequired();
//                if (!checkNoNull("脱杆高的最小值", "minOffbarHeight", seedlingBean.getMinOffbarHeight() + "", params))
//                    return false;
        }
        if (value.equals(ConstantParams.length))//长度
        {

            return checkNoNullDouble("长度的最大值或最小值", "maxLength", seedlingBean.getMaxLength() + "",
                    "minLength", seedlingBean.getMinLength() + "", params, item.isRequired());

//            if (!checkNoNullDouble("长度的最大值或最小值", "maxLength", seedlingBean.getMaxLength() + "", "minLength", seedlingBean.getMinLength() + "", params))
////                    if (!checkNoNull("长度的最大值或最小值", "maxLength", seedlingBean.getMaxLength() + "", params) && !checkNoNull("长度的最小值", "minLength", seedlingBean.getMinLength() + "", params))
//                return !item.isRequired();
//                if (!checkNoNull("长度的最小值", "minLength", seedlingBean.getMinLength() + "", params))
//                    return false;

        }


//        }
        return true;
    }


//    public boolean checkNoNull(String reson, String key, int value, AjaxParams ajaxParams) {
//        if (value == 0) {
//            ToastUtil.showShortToast(reson + "不能为空");
//            return false;
//        }
//        putParams(ajaxParams, key, value + "");
//        return true;
//    }

    /**
     * @param faildReason 失败原因
     * @param key         上传参数的 key
     * @param value
     * @param ajaxParams
     * @return
     */
    public boolean checkNoNull(String faildReason, String key, String value, AjaxParams ajaxParams) {
        if (TextUtils.isEmpty(value) || value.equals("0")) {
            ToastUtil.showShortToast(faildReason + "不能为空");
            return false;
        }
        putParams(ajaxParams, key, value);
        return true;
    }

    /**
     * @param faildReason 失败原因
     * @param key         上传参数的 key
     * @param value
     * @param ajaxParams
     * @return
     */
    public boolean checkNoNullDouble(String faildReason, String key, String value, String key1, String value1, AjaxParams ajaxParams) {

        if (TextUtils.isEmpty(value) && TextUtils.isEmpty(value1)) {
            ToastUtil.showShortToast(faildReason + "不能为空");
            return false;
        }
        if (value.equals("0") && value1.equals("0")) {
            ToastUtil.showShortToast(faildReason + "不能为空");
            return false;
        }

        if (!TextUtils.isEmpty(value)) {
            putParams(ajaxParams, key, value);
        }
        if (!TextUtils.isEmpty(value1)) {
            putParams(ajaxParams, key1, value1);
        }

        return true;
    }

    public boolean checkNoNullDouble(String faildReason, String key, String value, String key1, String value1, AjaxParams ajaxParams, boolean isRequired) {

        if (isRequired) {
            if (TextUtils.isEmpty(value) && TextUtils.isEmpty(value1)) {
                ToastUtil.showShortToast(faildReason + "不能为空");
                return false;
            }
            if (value.equals("0") && value1.equals("0")) {
                ToastUtil.showShortToast(faildReason + "不能为空");
                return false;
            }
        }

        if (!TextUtils.isEmpty(value)) {
            putParams(ajaxParams, key, value);
        }
        if (!TextUtils.isEmpty(value1)) {
            putParams(ajaxParams, key1, value1);
        }

        return true;
    }

    // 全选或全取消
    private void selectedAll() {

        if (myadapter.isSelectAll) {
            myadapter.selectAll();
        } else {
            myadapter.noSelectAll();
        }
        myadapter.isSelectAll = !myadapter.isSelectAll;

//        if (userList.size() > 0) {
//            for (int i = 0; i < userList.size(); i++) {
//                Myadapter.getIsSelected().put(i, flag);
//            }
////            myadapter.notify(userList);
//        }

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ConstantState.SAVE_SUCCEED) {

            D.e("=======SAVE_SUCCEED===========");
            RemoveOne(myadapter.change_item);
            //先删除 在添加
        }
        if (resultCode == ConstantState.PUBLIC_SUCCEED) {
            //发布的 删除
            RemoveOne(myadapter.change_item);
            D.e("=======PUBLIC_SUCCEED===========");
        }
    }

    public void onRefresh() {

    }


    class MyAdapter_now extends GlobBaseAdapter<SaveSeedingGsonBean.DataBean.SeedlingBean> {

        public boolean isSelectAll = true;
        private boolean[] checks; //用于保存checkBox的选择状态

        public MyAdapter_now(Context context, List<SaveSeedingGsonBean.DataBean.SeedlingBean> data, int layoutId) {
            super(context, data, layoutId);

        }


        public void setDatas(List<SaveSeedingGsonBean.DataBean.SeedlingBean> data) {
            checks = new boolean[data.size()];
            super.notifyDataSetChanged();
        }


        public void selectAll() {
            for (int i = 0; i < checks.length; i++) {
                checks[i] = true;
            }
            super.notifyDataSetChanged();
        }

        public void noSelectAll() {
            for (int i = 0; i < checks.length; i++) {
                checks[i] = false;
            }
            super.notifyDataSetChanged();
        }


        int change_item = 0;//点击去编辑的对象 。。。。

        @Override
        public void setConverView(ViewHolders myViewHolder, SaveSeedingGsonBean.DataBean.SeedlingBean seedlingBean, final int position) {

//            int id = R.layout.manager_group_list_item_new;
            int id = R.layout.list_view_seedling_new_shoucan;


            D.e("========================================" + seedlingBean.toString());

            CheckBox checkBox = myViewHolder.getView(R.id.checkBox);
            checkBox.setVisibility(View.VISIBLE);
            final int pos = position; //pos必须声明为final
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checks[pos] = isChecked;
                }
            });
            checkBox.setChecked(checks[pos]);

//            if (isSelectAll) {
//                checkBox.setChecked(true);
//            }
//            myViewHolder.getView(R.id.tv_01).setBackground();
            TextView tv_01 = myViewHolder.getView(R.id.tv_01);

            if (!TextUtils.isEmpty(seedlingBean.getPlantType()))
                MySwipeAdapter.setSrcByType(tv_01, seedlingBean.getPlantType());
            tv_01.setVisibility(TextUtils.isEmpty(seedlingBean.getPlantType()) ? View.GONE : View.VISIBLE);


            TextView tv_02 = myViewHolder.getView(R.id.tv_02);
            tv_02.setText(seedlingBean.getName());
            TextView tv_03 = myViewHolder.getView(R.id.tv_03);

            tv_03.setText("规格：" + getSpaceText(seedlingBean));

            TextView tv_04 = myViewHolder.getView(R.id.tv_04);
            tv_04.setText(seedlingBean.getNurseryJson().getCityName());


            TextView tv_06 = myViewHolder.getView(R.id.tv_06);


            BProduceAdapt.setPublishName(tv_06,
                    seedlingBean.getNurseryJson().getCompanyName(),
                    seedlingBean.getNurseryJson().getPublicName(),
                    seedlingBean.getNurseryJson().getRealName(),
                    seedlingBean.getNurseryJson().contactName);

//            tv_03.setText(seedlingBean.getCityName());


            TextView tv_07 = myViewHolder.getView(R.id.tv_07);//价格

            if (seedlingBean.isNego()) {
                tv_07.setText("面议");
            } else {
                tv_07.setText(FUtil.$("-", seedlingBean.getMinPrice(), seedlingBean.getMaxPrice()));

            }
//            ProductListAdapter.setPrice(tv_07, seedlingBean.getPriceStr(), seedlingBean.getMinPrice(), seedlingBean.isNego(), null);


            TextView tv_num_res = myViewHolder.getView(R.id.tv_09);//库存
            tv_num_res.setText("库存：" + seedlingBean.getCount());

            ImageView iv_img = myViewHolder.getView(R.id.iv_img);//

            if (null != seedlingBean.getImagesJson() && seedlingBean.getImagesJson().size() != 0) {
                String url = seedlingBean.getImagesJson().get(0).getLocal_url();
                fb.display(iv_img, url);
            }


            myViewHolder.getConvertView().setOnClickListener(v -> {

                change_item = position;
                SaveSeedlingActivity_change_data.start2Activity(StorageSaveActivity.this, list_saveSeedingGsonBeans.get(position), ConstantState.SAVE_REQUEST);
            });


        }

        private String getSpaceText(SaveSeedingGsonBean.DataBean.SeedlingBean seedlingBean) {
            StringBuilder spaceText = new StringBuilder();
            spaceText.append(checkApend("胸径：", seedlingBean.getMinDbh(), seedlingBean.getMaxDbh()));
            spaceText.append(checkApend("地径：", seedlingBean.getMinDiameter(), seedlingBean.getMaxDiameter()));
            spaceText.append(checkApend("高度：", seedlingBean.getMinHeight(), seedlingBean.getMaxHeight()));
            spaceText.append(checkApend("冠幅：", seedlingBean.getMinCrown(), seedlingBean.getMaxCrown()));
            spaceText.append(checkApend("长度：", seedlingBean.getMinLength(), seedlingBean.getMaxLength()));
            spaceText.append(checkApend("脱杆高：", seedlingBean.getMinOffbarHeight(), seedlingBean.getMaxOffbarHeight()));
            return spaceText.toString();
        }

        public String checkApend(String name, int min, int max) {

            if (min == 0 && max == 0) {
                return "";
            }

            if (min == 0) {
                return name + FUtil.$("-", "", max + "");
            }

            if (max == 0) {
                return name + FUtil.$("-", min + "", "");
            }

            return name + FUtil.$("-", min + "", max + "");
        }


        public void setAllSelect(boolean b) {
            if (b) {
                isSelectAll = true;
                this.notifyDataSetChanged();
            } else {
                isSelectAll = false;
                this.notifyDataSetChanged();
            }
        }


    }


    public static void start2Activity(Context context) {
        context.startActivity(new Intent(context, StorageSaveActivity.class));
    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }
}

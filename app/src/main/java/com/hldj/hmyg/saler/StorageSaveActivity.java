package com.hldj.hmyg.saler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.barryzhang.temptyview.TViewUtil;
import com.cn2che.androids.swipe.OnlyListViewSwipeGesture;
import com.google.gson.Gson;
import com.hldj.hmyg.DaoBean.SaveJson.DaoSession;
import com.hldj.hmyg.DaoBean.SaveJson.SavaBean;
import com.hldj.hmyg.DaoBean.SaveJson.SavaBeanDao;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.GlobBaseAdapter;
import com.hldj.hmyg.base.ViewHolders;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.buy.bean.CollectCar;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;

import net.tsz.afinal.FinalBitmap;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

//草稿界面
public class StorageSaveActivity extends NeedSwipeBackActivity implements OnClickListener {
    private ListView GroupManList;
    private BaseAdapter baseAdapter;

    private static String DB_NAME = "flower.db";
    private static final String DB_TABLE = "savetable";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    private boolean flag = true; // 全选或全取消
    private ArrayList<CollectCar> userList = new ArrayList<CollectCar>();
    private FinalBitmap fb;
    private Gson gson;
    private ImageView btn_back;
    private TextView tv_all;
    private TextView tv_begin;
    private int progress = 0;


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

        initView();

        initDao();
        initData();

        final OnlyListViewSwipeGesture touchListener = new OnlyListViewSwipeGesture(
                GroupManList, swipeListener, this);
        touchListener.SwipeType = OnlyListViewSwipeGesture.Double; // 设置两个选项列表项的背景
        GroupManList.setOnTouchListener(touchListener);
        TViewUtil.EmptyViewBuilder.getInstance(StorageSaveActivity.this)
                .setEmptyText(getResources().getString(R.string.nodata))
                .setEmptyTextSize(12).setEmptyTextColor(Color.GRAY)
                .setShowButton(false)
                .setActionText(getResources().getString(R.string.reload))
                .setAction(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Toast.makeText(getApplicationContext(),
                        // "Yes, clicked~",Toast.LENGTH_SHORT).show();
                        onRefresh();
                    }
                }).setShowIcon(false).setShowText(false).bindView(GroupManList);
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


    OnlyListViewSwipeGesture.TouchCallbacks swipeListener = new OnlyListViewSwipeGesture.TouchCallbacks() {

        @Override
        public void FullSwipeListView(int position) {
            // TODO Auto-generated method stub
            Toast.makeText(StorageSaveActivity.this, "Action_2",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void HalfSwipeListView(int position) {
            // TODO Auto-generated method stub
            db.delete(DB_TABLE, "storage_save_id="
                    + userList.get(position).getStorage_save_id(), null);
            userList.remove(position);
            baseAdapter.notifyDataSetChanged();
        }

        @Override
        public void LoadDataForScroll(int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onDismiss(ListView listView, int[] reverseSortedPositions) {
            // TODO Auto-generated method stub
            // Toast.makeText(StorageSaveActivity.this, "Delete",
            // Toast.LENGTH_SHORT).show();
            // for(int i:reverseSortedPositions){
            // data.remove(i);
            // new MyAdapter().notifyDataSetChanged();
            // }
        }

        @Override
        public void OnClickListView(int position) {
            // TODO Auto-generated method stub

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
//        SaveSeedingGsonBean.DataBean.SeedlingBean seedlingBean = saveSeedingGsonBean.getData().getSeedling();
//        List<SaveSeedingGsonBean.DataBean.SeedlingBean> seedlingBeen = new ArrayList<>();
//        seedlingBeen.add(seedlingBean);
        myadapter = new MyAdapter_now(this, seedlingBeens, R.layout.manager_group_list_item_new);
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

                break;
            case R.id.tv_all:
                selectedAll();

                break;
            case R.id.tv_begin:


                break;

            default:
                break;
        }
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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

            int id = R.layout.manager_group_list_item_new;


            D.e("========================================" + seedlingBean.toString());

            CheckBox checkBox = myViewHolder.getView(R.id.remmber);
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
            TextView tv_02 = myViewHolder.getView(R.id.tv_02);
            tv_02.setText(seedlingBean.getName());
            TextView tv_03 = myViewHolder.getView(R.id.tv_03);

            TextView tv_05 = myViewHolder.getView(R.id.tv_05);
            tv_05.setText(seedlingBean.getCityName());
//            tv_03.setText(seedlingBean.getCityName());


            TextView tv_07 = myViewHolder.getView(R.id.tv_07);//价格


            if (seedlingBean.isNego()) {
                tv_07.setText("面议");
            } else {
                tv_07.setText(seedlingBean.getMinPrice() + "-" + seedlingBean.getMaxPrice());
            }


            TextView tv_num_res = myViewHolder.getView(R.id.tv_num_res);//库存
            tv_num_res.setText("库存：" + seedlingBean.getCount());

            ImageView iv_img = myViewHolder.getView(R.id.iv_img);//

            if (null != seedlingBean.getImagesJson()) {
                String url = seedlingBean.getImagesJson().get(0).getLocal_url();
                fb.display(iv_img, url);

            }


            myViewHolder.getConvertView().setOnClickListener(v -> {

                change_item = position;
                SaveSeedlingActivity_change_data.start2Activity(StorageSaveActivity.this, list_saveSeedingGsonBeans.get(position), ConstantState.SAVE_REQUEST);
            });


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


}

package com.hldj.hmyg.saler;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.PicSerializableMaplist;
import com.hldj.hmyg.presenter.SaveSeedlingPresenter;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.TakePhotoUtil;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.white.utils.SystemSetting;
import com.zzy.common.widget.MeasureGridView;

import net.tsz.afinal.FinalHttp;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

public class UpdataImageActivity_bak extends NeedSwipeBackActivity {


    private ArrayList<Pic> urlPaths = new ArrayList<>();

    private ViewGroup mainView;
    public static UpdataImageActivity_bak instance;
    private TextView fabu;
    private KProgressHUD hud_numHud;
    FinalHttp finalHttp = new FinalHttp();
    public int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_img);
        hud_numHud = KProgressHUD.create(UpdataImageActivity_bak.this)
                .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
                .setLabel("上传中，请等待...").setMaxProgress(100)
                .setCancellable(true);
        instance = this;
        SystemSetting.getInstance(UpdataImageActivity_bak.this).choosePhotoDirId = "";
        Bundle bundle = getIntent().getExtras();
        urlPaths = ((PicSerializableMaplist) bundle.get("urlPaths"))
                .getMaplist();


        // 初始化
        mainView = (ViewGroup) findViewById(R.id.ll_mainView);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        initGv();

        findViewById(R.id.btn_back).setOnClickListener(v -> {
            finish();
        });

        findViewById(R.id.fabu).setOnClickListener(v -> {
            hud_numHud.show();
            save();
        });

    }


    private ArrayList<Pic> arrayList = new ArrayList<>();
    private String flowerInfoPhotoPath = "";
    MeasureGridView measureGridView;

    public void initGv() {
        measureGridView = (MeasureGridView) findViewById(R.id.publish_flower_info_gv);
        arrayList.clear();
        arrayList.addAll(urlPaths);
//            arrayList.add(new Pic("hellows", true, MeasureGridView.usrl1, 12));

        measureGridView.init(this, arrayList, mainView, new FlowerInfoPhotoChoosePopwin2.onPhotoStateChangeListener() {
            @Override
            public void onTakePic() {
                D.e("===========onTakePic=============");
                if (TakePhotoUtil.toTakePic(UpdataImageActivity_bak.this))//检查 存储空间
                    flowerInfoPhotoPath = TakePhotoUtil.doTakePhoto(UpdataImageActivity_bak.this, TakePhotoUtil.TO_TAKE_PIC);//照相
            }

            @Override
            public void onChoosePic() {
                D.e("===========onChoosePic=============");
                //通过本界面 addPicUrls 方法回调
                TakePhotoUtil.toChoosePic(UpdataImageActivity_bak.this, measureGridView.getAdapter());
            }

            @Override
            public void onCancle() {
                D.e("===========onCancle=============");
            }
        });


    }


    public void save() {

        int size = measureGridView.getAdapter().getDataList().size();
        if (measureGridView != null || size != 0) {
            D.e("======图片的地址数量====" + size);
            D.e("======图片的地址====" + this.measureGridView.getAdapter().getDataList().get(0).getUrl());
//            uploadBean.imagesData = PurchaseDetailActivity.this.measureGridView.getAdapter().getDataList().get(0).getUrl();
        }

        D.e("============上传报价===================");

        //如果是代购  就必须上传一张图片   参数多的那个
        ArrayList<Pic> piclistLocal = this.measureGridView.getAdapter().getDataList();//本地路径集合
        ArrayList<Pic> listPicsOnline = new ArrayList<>();//上传成功的结果保存在这里 网路路径集合

        if (piclistLocal.size() != 0) {//有图片，则先上传图片
            //接口上传图片
            new SaveSeedlingPresenter().upLoad(this.measureGridView.getAdapter().getDataList(), new ResultCallBack<Pic>() {
                @Override
                public void onSuccess(Pic pic) {
                    listPicsOnline.add(pic);
                    if (listPicsOnline.size() == piclistLocal.size()) {
//                        uploadBean.imagesData = GsonUtil.Bean2Json(listPicsOnline);
//                        save();//如果没有图片，直接上传数据

                        hud_numHud.dismiss();

                        //传回地址。到上一个界面
                        Intent intent = new Intent();
                        Bundle bundleObject = new Bundle();
                        final PicSerializableMaplist myMap = new PicSerializableMaplist();
                        myMap.setMaplist(listPicsOnline);
                        bundleObject.putSerializable("urlPaths", myMap);
                        intent.putExtras(bundleObject);
                        setResult(5, intent);
                        finish();


                    }

                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {

                }
            });
        } else {
            save();//如果没有图片，直接上传数据
        }
    }


    @Override
    public void finish() {
        super.finish();
        instance = null;
    }

    @Override
    public void onBackPressed() {
        finish();
        instance = null;

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void hudProgress() {
        if (hud_numHud != null && !UpdataImageActivity_bak.this.isFinishing()) {
            hud_numHud.setProgress(a * 100 / urlPaths.size());
            hud_numHud.setProgressText("上传中(" + a + "/" + urlPaths.size()
                    + "张)");
        }
        if (a == urlPaths.size()) {
            if (urlPaths.size() > 0) {
                if (hud_numHud != null
                        && !UpdataImageActivity_bak.this.isFinishing()) {
                    hud_numHud.dismiss();
                }

            }
        }

    }


    public void addPicUrls(ArrayList<Pic> resultPathList) {
        measureGridView.getAdapter().addItems(resultPathList);
        measureGridView.getAdapter().Faild2Gone(true);
//        viewHolder.publish_flower_info_gv.getAdapter().getDataList();
        D.e("=========addPicUrls=========");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TakePhotoUtil.TO_TAKE_PIC && resultCode == RESULT_OK) {


            //接受 上传图片界面传过来的list《pic》
            try {
                measureGridView.addImageItem(flowerInfoPhotoPath);
                measureGridView.getAdapter().Faild2Gone(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(getContentResolver(),
                        flowerInfoPhotoPath, flowerInfoPhotoPath, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//             最后通知图库更新
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.parse("file://" + flowerInfoPhotoPath)));
        }

    }
}


package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.MainActivity;
import com.hldj.hmyg.MyLuban.MyLuban;
import com.hldj.hmyg.R;
import com.hldj.hmyg.SellectActivity2;
import com.hldj.hmyg.Ui.friend.bean.Moments;
import com.hldj.hmyg.Ui.friend.bean.enums.MomentsType;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.CityGsonBean;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.Ui.CityWheelDialogF;
import com.hldj.hmyg.presenter.SaveSeedlingPresenter;
import com.hldj.hmyg.saler.FlowerInfoPhotoChoosePopwin2;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.util.TakePhotoUtil;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;
import com.lqr.optionitemview.OptionItemView;
import com.zf.iosdialog.widget.AlertDialog;
import com.zzy.common.widget.MeasureGridView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.hldj.hmyg.presenter.SaveSeedlingPresenter.getFileList;
import static com.hldj.hmyg.util.ConstantState.PUBLISH_SUCCEED;
import static com.hldj.hmyg.util.ConstantState.PURCHASE_SUCCEED;

/**
 * Created by luocaca on 2017/11/27 0027.
 * <p>
 * <p>
 * 苗友圈详情界面
 */

public class PublishActivity extends BaseMVPActivity {

    private static final String TAG = "PublishActivity";

    public static PublishActivity instance;

    /*发布按钮*/
    @ViewInject(id = R.id.toolbar_right_text)
    TextView toolbar_right_text;

    @ViewInject(id = R.id.toolbar_left_icon)
    ImageView toolbar_left_icon;


    /*底部 未知选择[*/
    @ViewInject(id = R.id.location)
    OptionItemView location;

    /*发布窗口*/
    @ViewInject(id = R.id.grid)
    MeasureGridView grid;


    private String cityCode = "";

    /**
     * 采购
     */
    public static String PURCHASE = "purchase";
    /**
     * 发布
     */
    public static String PUBLISH = "publish";


    @ViewInject(id = R.id.et_content)
    EditText et_content;
    private String flowerInfoPhotoPath;


    public int bindLayoutID() {
        return R.layout.activity_friend_publish;
    }


    @Override
    public void initView() {
        if (bindLayoutID() > 0) {
            FinalActivity.initInjectedView(this);
        }
        instance = this;


        initGvBottom();

        switchTypeText();

        initLocation();

        location.setOnClickListener(v -> {
//            ToastUtil.showLongToast("选择地址");
            CityWheelDialogF.instance()
                    .isShowCity(true)
                    .addSelectListener(new CityWheelDialogF.OnCitySelectListener() {
                        @Override
                        public void onCitySelect(CityGsonBean.ChildBeans childBeans) {
                            SellectActivity2.childBeans = childBeans;
                            D.e("=选择  地区==" + childBeans.toString());
                            cityCode = childBeans.cityCode;
                            location.setRightText(SellectActivity2.childBeans.fullName);
                        }

                        @Override
                        public void onProvinceSelect(CityGsonBean.ChildBeans childBeans) {

                        }
                    }).show(getSupportFragmentManager(), TAG);
        });

    }

    /**
     * 初始化 地理位置
     */
    private void initLocation() {

        if (MainActivity.aMapLocation != null) {
            if (!TextUtils.isEmpty(MainActivity.cityCode))
                cityCode = MainActivity.cityCode;
            location.setRightText(MainActivity.province_loc + " " + MainActivity.city_loc);
        }

    }

    private void initGvBottom() {

        ArrayList<Pic> arrayList = new ArrayList<Pic>();
//        arrayList.add(new Pic("q", true, "http://img95.699pic.com/photo/40007/4901.jpg_wh300.jpg", 0));
//        arrayList.add(new Pic("q", true, "http://img95.699pic.com/photo/50045/5922.jpg_wh300.jpg", 1));
//        arrayList.add(new Pic("q", true, "http://img95.699pic.com/photo/00009/3523.jpg_wh300.jpg!/format/webp", 2));
//        arrayList.add(new Pic("q", false, "http://img95.699pic.com/photo/00040/4625.jpg_wh300.jpg!/format/webp", 3));
//        arrayList.add(new Pic("q", false, "http://img95.699pic.com/photo/00040/2066.jpg_wh300.jpg", 4));
//      arrayList.add(new Pic("hellows", true, MeasureGridView.usrl1, 12));

//      grid.setImageNumColumns(3);
//      grid.setNumColumns(3);
//      grid.setHorizontalSpacing(3);
//      grid.setVerticalSpacing(0);
        grid.init(this, arrayList, (ViewGroup) grid.getParent(), new FlowerInfoPhotoChoosePopwin2.onPhotoStateChangeListener() {
            @Override
            public void onTakePic() {
                D.e("===========onTakePic=============");
                if (TakePhotoUtil.toTakePic(mActivity))//检查 存储空间
                    flowerInfoPhotoPath = TakePhotoUtil.doTakePhoto(mActivity, TakePhotoUtil.TO_TAKE_PIC);//照相
            }

            @Override
            public void onChoosePic() {
                D.e("===========onChoosePic=============");
                //通过本界面 addPicUrls 方法回调
                TakePhotoUtil.toChoosePic(mActivity, grid.getAdapter());
            }

            @Override
            public void onCancle() {
                D.e("===========onCancle=============");
            }
        });


    }

    private void switchTypeText() {

        //发布按钮显示，并赋值
        toolbar_right_text.setText("发布");
        toolbar_right_text.setTextColor(getColorByRes(R.color.main_color));
        toolbar_right_text.setVisibility(View.VISIBLE);
        View.OnClickListener clickListener = null;

        if (getTag().equals(PURCHASE)) {

            location.setLeftText("用苗地");
            /*采购*/
            et_content.setHint(R.string.purchase_content);
            setTitle("发布求购");
            clickListener = v -> {
//                ToastUtil.showLongToast("发布求购");
                if (TextUtils.isEmpty(et_content.getText())) {
                    ToastUtil.showLongToast("先写点什么嘛^_^");
                    return;
                }
                if (TextUtils.isEmpty(cityCode)) {
                    ToastUtil.showLongToast("请先选择地址^_^");
                    return;
                }
                requestUpload(MomentsType.purchase.getEnumValue());
            };
            toolbar_right_text.setOnClickListener(clickListener);
        } else if (getTag().equals(PUBLISH)) {
            location.setLeftText("苗源地");
            /*发布*/
            setTitle("发布供应");
            et_content.setHint(R.string.publish_content);
            clickListener = v -> {
                if (TextUtils.isEmpty(et_content.getText())) {
                    ToastUtil.showLongToast("先写点什么嘛^_^");
                    return;
                }
                if (TextUtils.isEmpty(cityCode)) {
                    ToastUtil.showLongToast("请先选择地址^_^");
                    return;
                }
//                ToastUtil.showLongToast("发布供应");
                requestUpload(MomentsType.supply.getEnumValue());
            };
            toolbar_right_text.setOnClickListener(clickListener);
        }

        /*初始化地址*/


    }

    List<Pic> pics = new ArrayList<>();

    private void requestUpload(String tag) {
//        List<Pic> pics = new ArrayList<>();
        this.upLoadImage(grid.getAdapter().getDataList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(pic -> !TextUtils.isEmpty(pic.getUrl()))

                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        String type = "";
                        if (pics != null && pics.size() > 0 && (pics.size() == grid.getAdapter().getDataList().size())) {
                            grid.getAdapter().notify((ArrayList<Pic>) pics);
                            UpdateLoading("图片上传成功，正在上传数据...");
                            type = "succeed";
                        } else {
                            List<Pic> l = addNoUpLoadImg(grid.getAdapter().getDataList());//把未上传的图片。添加到尾部。
                            grid.getAdapter().notify((ArrayList<Pic>) pics);
                            grid.getAdapter().addItems((ArrayList<Pic>) l);
                            pics.clear();
                            UpdateLoading("正在上传数据...");
                            if (grid.getAdapter().getDataList().size() == 0) {
                                type = "succeed";
                            } else {
                                type = "faild";
                            }
                        }
                        if (type.equals("succeed")) {
                            Log.i(TAG, "doFinally: 上传所有数据");
                            //图片上传结束
                            Moments moments = new Moments();
                            moments.content = et_content.getText().toString().trim();
                            moments.cityCode = cityCode;
                            moments.momentsType = tag;
                            moments.images = GsonUtil.Bean2Json(pics);
                            moments.imagesData = GsonUtil.Bean2Json(pics);
                            new BasePresenter().putParams(moments).doRequest("admin/moments/save", true, new HandlerAjaxCallBack(mActivity) {
                                @Override
                                public void onRealSuccess(SimpleGsonBean gsonBean) {
                                    ToastUtil.showLongToast(gsonBean.msg);
                                    Log.i(TAG, "run: 上传结束" + gsonBean.msg);
                                    hindLoading();
                                    if (getTag().equals(PURCHASE)) {
                                        //求购成功
                                        setResult(PURCHASE_SUCCEED);
                                    } else if (getTag().equals(PUBLISH)) {
                                        //发布成功
                                        setResult(PUBLISH_SUCCEED);
                                    }
                                    finish();
                                }
                            });
                        } else {

                            ToastUtil.showLongToast("上传失败，请重新上传~_~");
                            if (!isFinishing())
                                new AlertDialog(mActivity).builder()
                                        .setTitle("有些图片上传失败了?")
                                        .setPositiveButton("继续提交", v1 -> {
                                            Log.i(TAG, "doFinally: 上传所有数据");
                                            //图片上传结束
                                            Moments moments = new Moments();
                                            moments.content = et_content.getText().toString();
                                            moments.cityCode = cityCode;
                                            moments.momentsType = tag;
                                            moments.images = GsonUtil.Bean2Json(pics);
                                            moments.imagesData = GsonUtil.Bean2Json(pics);
                                            new BasePresenter().putParams(moments).doRequest("admin/moments/save", true, new HandlerAjaxCallBack(mActivity) {
                                                @Override
                                                public void onRealSuccess(SimpleGsonBean gsonBean) {
                                                    ToastUtil.showLongToast(gsonBean.msg);
                                                    Log.i(TAG, "run: 上传结束" + gsonBean.msg);
                                                    hindLoading();
                                                    if (getTag().equals(PURCHASE)) {
                                                        //求购成功
                                                        setResult(PURCHASE_SUCCEED);
                                                    } else if (getTag().equals(PUBLISH)) {
                                                        //发布成功
                                                        setResult(PUBLISH_SUCCEED);
                                                    }
                                                    finish();
                                                }
                                            });

                                        }).setNegativeButton("重新上传", v2 -> {
                                    requestUpload(MomentsType.purchase.getEnumValue());
                                }).show();
//                            new AlertDialog(mActivity).builder()
//                                    .setTitle("确定退出登录?")
//                                    .setPositiveButton("退出登录", v1 -> {
//
//                                    }).setNegativeButton("取消", v2 -> {
//                            }).show();

                        }


                    }


                })

                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.i(TAG, "run: doOnComplete");

                    }
                })
                .doOnSubscribe(disposable -> {
                    //开启订阅 用于显示 loading
                    showLoadingCus("数据处理中...");
                })
                .doOnNext(new Consumer<Pic>() {
                    @Override
                    public void accept(@NonNull Pic pic) throws Exception {
                        Log.i(TAG, "图片上传成功accept:  doOnNext " + pic.toString());
                        pics.add(pic);
                        Log.i(TAG, "pics size = : " + pics.size());
                        Log.i(TAG, "getAdapter size = : " + grid.getAdapter().getDataList().size());
                        UpdateLoading("正在上传第 " + pics.size() + "/" + grid.getAdapter().getDataList().size() + "张图片");
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.i(TAG, "doOnError: ");
                        UpdateLoading("图片上传失败");
                    }
                })

                .subscribe(new Consumer<Pic>() {
                    @Override
                    public void accept(@NonNull Pic simpleGsonBean) throws Exception {
//                        ToastUtil.showLongToast("成功" + simpleGsonBean.toString());
                        Log.i(TAG, "next: 上传结束，继续上传");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ToastUtil.showLongToast("失败");
                        Log.i(TAG, "error: 上传失败");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.i(TAG, "run: complete");
                    }
                });
//                .map(new Function<ArrayList<Pic>, ArrayList<ImagesJsonBean>>() {
//
//                    @Override
//                    public ArrayList<ImagesJsonBean> apply(@NonNull ArrayList<Pic> pics) throws Exception {
//
//
//
//                        return null;
//                    }
//                });


    }


    public void addPicUrls(ArrayList<Pic> resultPathList) {
        grid.getAdapter().addItems(resultPathList);
        grid.getAdapter().Faild2Gone(true);
//        viewHolder.publish_flower_info_gv.getAdapter().getDataList();
        D.e("=========addPicUrls=========");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TakePhotoUtil.TO_TAKE_PIC && resultCode == RESULT_OK) {
            //接受 上传图片界面传过来的list《pic》
            try {
                grid.addImageItem(flowerInfoPhotoPath);
                grid.getAdapter().Faild2Gone(true);
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

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    //    @Override
//    public int bindLayoutID() {
//        return 0;
//    }


    private String getTag() {
        String mTag = getIntent().getStringExtra(TAG);


        if (TextUtils.isEmpty(mTag)) {
            ToastUtil.showLongToast("未知类型");
            return "";
        }
        return mTag;
    }

    public static void start(Activity activity, String tag) {
        Intent intent = new Intent(activity, PublishActivity.class);
        intent.putExtra(TAG, tag);
        Log.i(TAG, "start: " + tag);
        activity.startActivityForResult(intent, 110);
    }

    public static void start(Fragment fragment, String tag) {
        Intent intent = new Intent(fragment.getActivity(), PublishActivity.class);
        intent.putExtra(TAG, tag);
        Log.i(TAG, "start: " + tag);
        fragment.startActivityForResult(intent, 110);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    @Override
    public String setTitle() {
        return "我的苗友圈";
    }


    public Observable<Pic> upLoadImage(List<Pic> pics) {
        Log.i("===1", "subscribe: " + Thread.currentThread().getName());

        if (getFileList(pics).size() == 0) {
            return Observable.empty();
        }

        //此处进行上传操作
        return MyLuban.compress(mActivity, getFileList(pics))
                .setMaxSize(512)
                .setMaxHeight((int) (1920))//2560
                .setMaxWidth((int) (1080))//2560
                .putGear(MyLuban.CUSTOM_GEAR)
//              .putGear(MyLuban.THIRD_GEAR)
                .asListObservable()
                .flatMap(new Function<List<File>, ObservableSource<File>>() {
                    @Override
                    public ObservableSource<File> apply(@NonNull List<File> files) throws Exception {
                        Log.i(TAG, "apply: " + files.size());
                        return Observable.fromIterable(files);//将图片分批下发
                    }
                })
                .filter(file -> file != null && file.length() > 0)
                .flatMap(new Function<File, ObservableSource<Pic>>() {
                    @Override
                    public ObservableSource<Pic> apply(@NonNull File file) throws Exception {
                        return doUp(file);
                    }
                });
    }


    int count = 0;

    private Observable<Pic> doUp(File file) {
        return Observable.create(new ObservableOnSubscribe<Pic>() {
            @Override
            public void subscribe(ObservableEmitter<Pic> e) throws Exception {
                FinalHttp finalHttp = new FinalHttp();
                GetServerUrl.addHeaders(finalHttp, true);
                finalHttp.addHeader("Content-Type", "image/jpeg");
                AjaxParams ajaxParams = new AjaxParams();
                new SaveSeedlingPresenter(mActivity).doUpLoad(file, ajaxParams, finalHttp, new ResultCallBack<Pic>() {
                    @Override
                    public void onSuccess(Pic image) {
                        image.setSort(count++);
                        e.onNext(image);
                        e.onComplete();
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
//                      e.onError(t);
//                      e.onNext(new Pic("-1", false, "", -1));
                        e.onComplete();
                        ToastUtil.showLongToast("图片上传失败" + strMsg);
                    }
                });
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());


    }


    private List<Pic> addNoUpLoadImg(ArrayList<Pic> dataList) {
        List<Pic> pic = new ArrayList<>();
        for (Pic localPic : dataList) {
            if (!localPic.getUrl().startsWith("http")) {
                pic.add(localPic);
            }
        }
        return pic;
    }
}

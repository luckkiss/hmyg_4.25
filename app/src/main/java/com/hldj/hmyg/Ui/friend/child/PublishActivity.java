package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VideoView;

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
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.VideoData;
import com.hldj.hmyg.buyer.Ui.CityWheelDialogF;
import com.hldj.hmyg.presenter.SaveSeedlingPresenter;
import com.hldj.hmyg.saler.FlowerInfoPhotoChoosePopwin2;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hldj.hmyg.util.TakePhotoUtil;
import com.hldj.hmyg.util.VideoHempler;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;
import com.lqr.optionitemview.OptionItemView;
import com.mabeijianxi.smallvideo2.VideoPlayerActivity2;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zf.iosdialog.widget.AlertDialog;
import com.zzy.common.widget.MeasureGridView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
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

    @ViewInject(id = R.id.video)
    VideoView video;

    @ViewInject(id = R.id.record)
    Button record;


    /* 删除视频按钮 */
    @ViewInject(id = R.id.iv_close)
    View iv_close;
    /* 视频上传失败按钮 */
    @ViewInject(id = R.id.iv_failed)
    TextView iv_failed;
    /* 视频播放按钮 */
    @ViewInject(id = R.id.play)
    ImageView play;


    /*顶部 类型  显示区域   当 tag 为 all 的时候显示。。用于首页跳转时选择发布类型 */
    @ViewInject(id = R.id.top_radio_group)
    RadioGroup top_radio_group;

    @ViewInject(id = R.id.rb_type_left)/*发布供应*/
            RadioButton rb_type_left;

    @ViewInject(id = R.id.rb_type_right)/*发布求购*/
            RadioButton rb_type_right;


    private String cityCode = "";

    /**
     * 采购
     */
    public static String PURCHASE = "purchase";
    /**
     * 发布
     */
    public static String PUBLISH = "publish";


    /**
     * all  发布与采购
     */
    public static String ALL = "all";

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

        Bitmap bitmap = BitmapFactory.decodeFile("/storage/emulated/0/DCIM/mabeijianxi/1519717970869/1519717970869.jpg");
        play.setImageBitmap(bitmap);
        toggleVideo(true);

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


//        video.setVisibility(View.GONE);
//        video.setOnClickListener(v -> {
//            VideoHempler.start(mActivity);
//        });

        video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (TextUtils.isEmpty(currentVideoPath)) {
                    D.i("=========视频地址已经被删除=========");
                    return true;
                } else return false;
            }
        });

        /*关闭视频控件 */
        toggleVideo(false);
        /*关闭删除按钮*/
        toggleDeleteIcon(false);
        /*关闭失败按钮*/
        toggleFailedIcon(false);


        record.setVisibility(View.GONE);
        record.setOnClickListener(v -> {
//            ToastUtil.showLongToast("跳转录屏");
            VideoHempler.start(mActivity);
        });


    }

    private void checkIntent(Intent data) {

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f, 0f);
            }
        });

//        String str = data.getStringExtra(MediaRecorderActivity.OUTPUT_DIRECTORY);
        String url = data.getStringExtra(MediaRecorderActivity.VIDEO_URI);
        String screen_shot = data.getStringExtra(MediaRecorderActivity.VIDEO_SCREENSHOT);

        Log.i(TAG, "checkIntent: " + screen_shot);

        if (TextUtils.isEmpty(url)) {
            ToastUtil.showLongToast("小视频录制失败~_~");
            D.w("===========录屏为空===========");
        } else {
            currentVideoPath = url;

             /*关闭视频控件 */
            toggleVideo(true);
        /*关闭删除按钮*/
            toggleDeleteIcon(true);
        /*关闭失败按钮*/
            toggleFailedIcon(false);
            grid.setVisibility(View.GONE);
            video.setVisibility(View.VISIBLE);
//            video.setVideoPath(url);
            video.start();
//            video.seekTo(1);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    video.pause();
                }
            }, 500);

            ImageLoader.getInstance().displayImage(screen_shot.trim(), play, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    Log.i(TAG, "onLoadingStarted: ");
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    Log.i(TAG, "onLoadingFailed: ");

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Log.i(TAG, "onLoadingComplete: ");

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    Log.i(TAG, "onLoadingCancelled: ");

                }
            });

            Bitmap bitmap = BitmapFactory.decodeFile(screen_shot.trim());
            play.setImageBitmap(bitmap);

        }


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

        // true  表示开启小视频按钮
//        grid.init(this, arrayList, (ViewGroup) grid.getParent(), true, new FlowerInfoPhotoChoosePopwin2.onPhotoStateChangeListener() {
        grid.init(this, arrayList, (ViewGroup) grid.getParent(), true, new FlowerInfoPhotoChoosePopwin2.onVideoStateChangeListener() {
            @Override
            public void onVideoing() {
//                ToastUtil.showLongToast("跳转录屏");
                VideoHempler.start(mActivity);


            }

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
//        toolbar_right_text.setTextColor(getColorByRes(R.color.text_color111));
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


                if (!currentVideoPath.isEmpty()) {
//                    ToastUtil.showLongToast("开始上传视频");
                    doUpVideo(MomentsType.purchase.getEnumValue());
                } else {
                    requestUpload(MomentsType.purchase.getEnumValue());
                }

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

                if (!currentVideoPath.isEmpty()) {
//                    ToastUtil.showLongToast("开始上传视频");
                    doUpVideo(MomentsType.supply.getEnumValue());
                } else {
                    requestUpload(MomentsType.supply.getEnumValue());
                }
//                ToastUtil.showLongToast("发布供应");
            };
            toolbar_right_text.setOnClickListener(clickListener);
        } else if (getTag().equals(ALL)) {


            top_radio_group.setVisibility(View.VISIBLE);
//            setTitle("我的苗友圈");
            location.setLeftText("用苗地");
            /*采购*/
            et_content.setHint(R.string.purchase_content);
            setTitle("发布求购");
            rb_type_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ToastUtil.showLongToast("发布供应");
                    D.i("发布供应");

                    location.setLeftText("苗源地");
            /*发布*/
                    setTitle("发布供应");

                    et_content.setHint(R.string.publish_content);

                    View.OnClickListener clickListener = v1 -> {
//                ToastUtil.showLongToast("发布求购");
                        if (TextUtils.isEmpty(et_content.getText())) {
                            ToastUtil.showLongToast("先写点什么嘛^_^");
                            return;
                        }
                        if (TextUtils.isEmpty(cityCode)) {
                            ToastUtil.showLongToast("请先选择地址^_^");
                            return;
                        }


                        if (!currentVideoPath.isEmpty()) {
//                    ToastUtil.showLongToast("开始上传视频");
                            doUpVideo(MomentsType.supply.getEnumValue());
                        } else {
                            requestUpload(MomentsType.supply.getEnumValue());
                        }

                    };
                    toolbar_right_text.setOnClickListener(clickListener);


                }
            });
            rb_type_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ToastUtil.showLongToast("发布求购");
                    D.i("发布求购");

                    location.setLeftText("用苗地");
            /*采购*/
                    et_content.setHint(R.string.purchase_content);
                    setTitle("发布求购");


                    et_content.setHint(R.string.publish_content);
                    View.OnClickListener clickListener = v3 -> {
                        if (TextUtils.isEmpty(et_content.getText())) {
                            ToastUtil.showLongToast("先写点什么嘛^_^");
                            return;
                        }
                        if (TextUtils.isEmpty(cityCode)) {
                            ToastUtil.showLongToast("请先选择地址^_^");
                            return;
                        }

                        if (!currentVideoPath.isEmpty()) {
//                    ToastUtil.showLongToast("开始上传视频");
                            doUpVideo(MomentsType.purchase.getEnumValue());
                        } else {
                            requestUpload(MomentsType.purchase.getEnumValue());
                        }
//                ToastUtil.showLongToast("发布供应");
                    };
                    toolbar_right_text.setOnClickListener(clickListener);


                }
            });


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
//                                    ToastUtil.showLongToast(gsonBean.msg);
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


    String currentVideoPath = "";

    public void addPicUrls(ArrayList<Pic> resultPathList) {
        grid.getAdapter().addItems(resultPathList);
        grid.getAdapter().Faild2Gone(true);

//        currentVideoPath = resultPathList.get(0).getUrl();
//        video.setVideoPath(resultPathList.get(0).getUrl());
//        video.start();
////        viewHolder.publish_flower_info_gv.getAdapter().getDataList();
//        D.e("=========addPicUrls=========" + resultPathList.get(0).toString());
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
        } else if (requestCode == 100 && resultCode == 100) {
            checkIntent(data);
        } else if (requestCode == 100 && resultCode == 101) {
            String screen_shot = data.getStringExtra(MediaRecorderActivity.VIDEO_SCREENSHOT);
//            ArrayList<Pic> pic = new ArrayList<>();
//            pic.add(new Pic("1", true, screen_shot, 0));
//            addPicUrls(pic);


            //接受 上传图片界面传过来的list《pic》
            try {
                grid.addImageItem(screen_shot);
                grid.getAdapter().Faild2Gone(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(getContentResolver(),
                        screen_shot, screen_shot, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//             最后通知图库更新
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.parse("file://" + screen_shot)));


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


    private String doUpVideo(String type) {

//        FinalHttp finalHttp = new FinalHttp();
//        GetServerUrl.addHeaders(finalHttp, true);
//        finalHttp.addHeader("Content-Type", "application/octet-stream");
//        AjaxParams ajaxParams = new AjaxParams();

        new BasePresenter()
                .putParams("imagType", "video")
                .putFile("file", new File(currentVideoPath))
                .addHead("Content-Type", "application/octet-stream")
                .doRequest("admin/file/video", true, new AjaxCallBack<String>() {

                    @Override
                    public void onStart() {
                        showLoading();
//                        getLoad().showToastAlong();
                        super.onStart();
                    }

                    @Override
                    public void onSuccess(String json) {
                        ToastUtil.showLongToast(json);
                        D.e("======onSuccess=======" + json);
                        SimpleGsonBean_new<VideoData> gsonBean = GsonUtil.formateJson2Bean(json, SimpleGsonBean_new.class);
                        if (gsonBean.isSucceed()) {
                            Log.i(TAG, "doFinally: 上传所有数据");
                            //图片上传结束
                            Moments moments = new Moments();
                            moments.content = et_content.getText().toString().trim();
                            moments.cityCode = cityCode;
                            moments.momentsType = type;
                            moments.images = GsonUtil.Bean2Json(pics);
                            moments.imagesData = GsonUtil.Bean2Json(pics);
                            moments.videoData = GsonUtil.Bean2Json(gsonBean.data.video);
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
                            UpdateLoading(gsonBean.msg);
                            toggleFailedIcon(true);
                        }

                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                        D.e("======onFailure=======");
                        UpdateLoading("网络错误");
                        toggleFailedIcon(true);
                    }

                });

        return "ok";

    }


    public void toggleVideo(boolean flag) {
        D.i("=========toggleVideo======" + flag);

        grid.setVisibility(!flag ? View.VISIBLE : View.GONE);
        ((ViewGroup) video.getParent()).setVisibility(flag ? View.VISIBLE : View.GONE);

        if (flag && !TextUtils.isEmpty(currentVideoPath))

        {
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mActivity, VideoPlayerActivity2.class).putExtra(
                            "path", currentVideoPath));
                }
            });
        }

        if (!flag) {//如果是关闭的话 。清空当前视频地址，。。。 需要删除视频

            File file = new File(currentVideoPath);
            if (!file.exists()) {
                D.i("===文件不存在==" + currentVideoPath);
                return;
            }
            D.i("==删除 文件夹 操作 start==");
            deleteAllFile(file.getParentFile());
            file.getParentFile().delete();
//            if (file.exists()) {
//                File parent = file.getParentFile();
//                //direct 文件夹，删除操作
//                File[] childs = parent.listFiles();
//                for (File child : childs) {
//                    D.i("===" + child.getName());
//                    if (child.isFile()) {
//                        child.delete();
//                    }
//                }
//                parent.delete();
//            }

            D.i("==删除 文件夹 操作 end==");

            currentVideoPath = "";
        }

    }

    public void deleteAllFile(File file) {
        if (file.isDirectory()) {
            //是文件夹的话 便利删除子文件
            File[] childs = file.listFiles();
            for (File child : childs) {
                D.i("===" + child.getName());
                if (child.isFile()) {
                    child.delete();
                } else {
                    //文件夹
                    deleteAllFile(file);
                }
            }


        } else {
            file.delete();
        }

    }


    public void toggleDeleteIcon(boolean flag) {


        iv_close.setOnClickListener(v -> toggleVideo(false));

        iv_close.setVisibility(flag ? View.VISIBLE : View.GONE);


    }

    public void toggleFailedIcon(boolean flag) {
        iv_failed.setVisibility(flag ? View.VISIBLE : View.GONE);


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


    @Override
    protected void onPause() {
        super.onPause();
//        if (video.isPlaying()) {
//            video.pause();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (!TextUtils.isEmpty(currentVideoPath) && video.getVisibility() == View.VISIBLE) {
//            video.start();
//            D.e("======播放=======");
////            ToastUtil.showLongToast("播放" + currentVideoPath);
//        } else {
//            D.e("======不播放了播放=======");
////            ToastUtil.showLongToast("不播放了播放");
//            video.stopPlayback();
//            video.pause();
//        }
    }
}

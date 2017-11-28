package com.hldj.hmyg.Ui.friend.child;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.SellectActivity2;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.CityGsonBean;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.buyer.Ui.CityWheelDialogF;
import com.hldj.hmyg.saler.FlowerInfoPhotoChoosePopwin2;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.TakePhotoUtil;
import com.hy.utils.ToastUtil;
import com.lqr.optionitemview.OptionItemView;
import com.zzy.common.widget.MeasureGridView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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


    /*采购*/
    public static String PURCHASE = "purchase";
    /*发布*/
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

        location.setOnClickListener(v -> {
            ToastUtil.showLongToast("选择地址");
            CityWheelDialogF.instance()
                    .isShowCity(true)
                    .addSelectListener(new CityWheelDialogF.OnCitySelectListener() {
                        @Override
                        public void onCitySelect(CityGsonBean.ChildBeans childBeans) {
                            SellectActivity2.childBeans = childBeans;
                            D.e("=选择  地区==" + childBeans.toString());
                            location.setRightText(SellectActivity2.childBeans.name);
                        }

                        @Override
                        public void onProvinceSelect(CityGsonBean.ChildBeans childBeans) {

                        }
                    }).show(getSupportFragmentManager(), TAG);
        });

    }

    private void initGvBottom() {

        ArrayList<Pic> arrayList = new ArrayList<Pic>();
        arrayList.add(new Pic("q", true, "http://img95.699pic.com/photo/40007/4901.jpg_wh300.jpg", 0));
        arrayList.add(new Pic("q", true, "http://img95.699pic.com/photo/50045/5922.jpg_wh300.jpg", 1));
        arrayList.add(new Pic("q", true, "http://img95.699pic.com/photo/00009/3523.jpg_wh300.jpg!/format/webp", 2));
        arrayList.add(new Pic("q", false, "http://img95.699pic.com/photo/00040/4625.jpg_wh300.jpg!/format/webp", 3));
        arrayList.add(new Pic("q", false, "http://img95.699pic.com/photo/00040/2066.jpg_wh300.jpg", 4));
//      arrayList.add(new Pic("hellows", true, MeasureGridView.usrl1, 12));

        grid.init(this, arrayList, (ViewGroup) grid.getParent(), new FlowerInfoPhotoChoosePopwin2.onPhotoStateChangeListener() {
            @Override
            public void onTakePic() {

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
            /*采购*/
            et_content.setHint(R.string.purchase_content);
            setTitle("发布求购");
            clickListener = v -> {
                ToastUtil.showLongToast("发布求购");
            };
            toolbar_right_text.setOnClickListener(clickListener);
        } else if (getTag().equals(PUBLISH)) {
            /*发布*/
            setTitle("发布供应");
            et_content.setHint(R.string.publish_content);
            clickListener = v -> {
                ToastUtil.showLongToast("发布供应");

            };
            toolbar_right_text.setOnClickListener(clickListener);
        }

        /*初始化地址*/


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    @Override
    public String setTitle() {
        return "我的苗友圈";
    }
}

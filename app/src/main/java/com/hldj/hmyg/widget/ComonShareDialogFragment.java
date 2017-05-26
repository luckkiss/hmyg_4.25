package com.hldj.hmyg.widget;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.PlatformForShare;
import com.hldj.hmyg.util.D;
import com.hy.utils.GetServerUrl;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;


/**
 * Created by Administrator on 2017/5/18.
 */

public class ComonShareDialogFragment extends DialogFragment implements PlatformActionListener {


    private String img = GetServerUrl.ICON_PAHT;
    private static ComonShareDialogFragment instance;
    Dialog dialog;
    private ArrayList<PlatformForShare> shares = new ArrayList<>();
    private ShareBean shareBean = new ShareBean();

    public static ComonShareDialogFragment newInstance() {
        instance = new ComonShareDialogFragment();
        instance.setCancelable(true);
        return instance;
    }


    public static ComonShareDialogFragment getInstance() {
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dia_choose_share = inflater.inflate(R.layout.dia_choose_share, null);
//        dialog = getDialog();
//        Window window = dialog.getWindow();
//        // 设置显示动画
//        window.setWindowAnimations(R.style.main_menu_animstyle);
        showDialog(dia_choose_share);

        this.setCancelable(true);

        return dia_choose_share;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialog);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);// 无标题
        getDialog().setCanceledOnTouchOutside(true);// 点击边际可消失
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
//        lp.gravity = Gravity.BOTTOM;
//        lp.width = (int) (getDialog().getWindow().getWindowManager().getDefaultDisplay().getWidth());
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }


    @Override
    public void onStart() {
        super.onStart();
        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = (int) (getDialog().getWindow().getWindowManager().getDefaultDisplay().getWidth());
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }

    private String platform = "1,2,3,4,5,6,7,8";

    private void showDialog(View dia_choose_share) {
        shares.clear();
        if (platform.contains("1")) {
            PlatformForShare platformForShare = new PlatformForShare("朋友圈",
                    "WechatMoments", "1", R.drawable.sns_icon_23);
            shares.add(platformForShare);
        }
        if (platform.contains("2")) {
            PlatformForShare platformForShare = new PlatformForShare("微信好友",
                    "Wechat", "2", R.drawable.sns_icon_22);
            shares.add(platformForShare);
        }
        if (platform.contains("3")) {
            PlatformForShare platformForShare = new PlatformForShare("新浪微博",
                    "SinaWeibo", "3", R.drawable.sns_icon_1);
            shares.add(platformForShare);
        }
        if (platform.contains("4")) {
            PlatformForShare platformForShare = new PlatformForShare("QQ好友",
                    "QZone", "4", R.drawable.sns_icon_24);
            shares.add(platformForShare);
        }


        GridView gridView = (GridView) dia_choose_share.findViewById(R.id.gridView);
        Button btn_cancle = (Button) dia_choose_share.findViewById(R.id.btn_cancle);
        gridView.setAdapter(new SharePlatformAdapter());

        btn_cancle.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            if (getDialog().isShowing()) {
                dismiss();
            }
        });
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {

    }


    class SharePlatformAdapter extends BaseAdapter {

        @Override
        public boolean areAllItemsEnabled() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return shares.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub
            View inflate = getActivity().getLayoutInflater().inflate(R.layout.list_item_share_platform, null);
            inflate.setBackgroundColor(Color.WHITE);
            ImageView iv_icon = (ImageView) inflate.findViewById(R.id.iv_icon);
            TextView tv_name = (TextView) inflate.findViewById(R.id.tv_name);
            iv_icon.setImageResource(shares.get(position).getPic());
            tv_name.setText(shares.get(position).getName());
            inflate.setOnClickListener(v -> {
                // TODO Auto-generated method stub
                if (!getActivity().isFinishing() && dialog != null
                        && dialog.isShowing()) {
                    dialog.cancel();
                }

                if ("WechatMoments".equals(shares.get(position).getEname())) {
                    ShareToWechatMoments();
                } else if ("Wechat".equals(shares.get(position).getEname())) {
                    ShareToWechat();
                } else if ("SinaWeibo".equals(shares.get(position)
                        .getEname())) {
                    ShareToSinaWeibo();
                } else if ("QZone".equals(shares.get(position).getEname())) {
                    ShareToQzone();
                }

                D.e("=======分享内容=======" + shareBean.toString());


            });
            return inflate;
        }
    }


    private void ShareToQzone() {
        Platform.ShareParams sp5 = new Platform.ShareParams();
        sp5.setTitle(shareBean.title);
        sp5.setTitleUrl(shareBean.pageUrl); // 标题的超链接
        sp5.setText(shareBean.text);
        sp5.setImageUrl(shareBean.imgUrl);
        sp5.setSite(getString(R.string.app_name));
        sp5.setSiteUrl(shareBean.pageUrl);
        Platform qzone = ShareSDK.getPlatform(QQ.NAME);
        qzone.setPlatformActionListener(this); // 设置分享事件回调
        // 执行图文分享
        qzone.share(sp5);
    }

    private void ShareToSinaWeibo() {
        Platform.ShareParams sp3 = new Platform.ShareParams();
        sp3.setText("苗木交易原来可以如此简单,配上花木易购APP,指尖轻点,交易无忧。");
        // sp3.setImagePath("/mnt/sdcard/share/" + system_time + ".jpg");
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo.setPlatformActionListener(this); // 设置分享事件回调
        // 执行图文分享
        weibo.share(sp3);
    }

    private void ShareToWechat() {
        Platform.ShareParams sp1 = new Platform.ShareParams();
        sp1.setShareType(Platform.SHARE_WEBPAGE);
        sp1.setTitle(shareBean.title);
        sp1.setText(shareBean.text);
//        sp1.setImageUrl(img);
        sp1.setImageUrl(shareBean.imgUrl);
//        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResourcePackageName(R.drawable.图片名称) + "/" + r.getResourceTypeName(R.drawable.图片名称) + "/" + r.getResourceEntryName(R.drawable.图片名称))
        sp1.setUrl(shareBean.pageUrl);
        sp1.setSiteUrl(shareBean.pageUrl);
        Platform Wechat = ShareSDK.getPlatform("Wechat");
        Wechat.setPlatformActionListener(this);
        Wechat.share(sp1);
    }

    private void ShareToWechatMoments() {
        Platform.ShareParams sp2 = new Platform.ShareParams();
        sp2.setShareType(Platform.SHARE_WEBPAGE);
        sp2.setTitle(shareBean.title);
        sp2.setText(shareBean.text);
        sp2.setImageUrl(shareBean.imgUrl);
        sp2.setUrl(shareBean.pageUrl);
        sp2.setTitleUrl(shareBean.pageUrl);
        sp2.setSiteUrl(shareBean.pageUrl);
        Platform Wechat_men = ShareSDK.getPlatform("WechatMoments");
        Wechat_men.setPlatformActionListener(this);
        Wechat_men.share(sp2);
    }


    @Override
    public void dismiss() {
        super.dismiss();
        instance = null;
    }


    public ComonShareDialogFragment setShareBean(ShareBean bean) {
        shareBean = bean;
        return this;
    }

    public static class ShareBean {
        public String title = "";//标题
        public String text = "苗木交易原来可以如此简单,配上花木易购APP,指尖轻点,交易无忧。";//内容
        public String desc = "";//描述
        public String imgUrl = "";//图片链接地址
        public String pageUrl = "";// 

        public ShareBean(String title, String text, String desc, String imgUrl, String pageUrl) {
            this.title = title;
            this.text = text;
            this.desc = desc;
            this.imgUrl = imgUrl;
            this.pageUrl = pageUrl;
        }

        public ShareBean() {
        }

        @Override
        public String toString() {
            return "ShareBean{" +
                    "title='" + title + '\'' +
                    ", text='" + text + '\'' +
                    ", desc='" + desc + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", pageUrl='" + pageUrl + '\'' +
                    '}';
        }
    }


}

package com.hldj.hmyg.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.PlatformForShare;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;
import com.white.utils.AndroidUtil;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class SharePopupWindow extends PopupWindow implements PlatformActionListener {

    Context mContext;
    private ListView lvPopupList;
    private ComonShareDialogFragment.ShareBean shareBean;

    public int bindLoadingLayout() {
        return 0;
    }

    public SharePopupWindow(Context context, ComonShareDialogFragment.ShareBean shareBean) {
        this.mContext = context;
        // 设置弹窗的布局界面
        View view = LayoutInflater.from(context).inflate(R.layout.task_detail_popupwindow, null);
        this.shareBean = shareBean;

        setContentView(view);
        setWidth(AndroidUtil.dip2px(context, 140));
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        initView(view);
    }


    private void initView(View viewRoot) {


        // 设置可以获得焦点
        setFocusable(true);
        // 设置弹窗内可点击
        setTouchable(true);
        // 设置弹窗外可点击
        setOutsideTouchable(true);

        lvPopupList = (ListView) viewRoot.findViewById(R.id.lv_popup_list);
        SharePoPAdapter sharePoPAdapter = new SharePoPAdapter(mContext, getSharesLists());
        lvPopupList.setAdapter(sharePoPAdapter);

        lvPopupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isShowing()) {
                    dismiss();// 关闭
                }
                if ("1".equals(getSharesLists().get(position).getId())) {
                    ShareToWechatMoments();
                } else if ("2".equals(getSharesLists().get(position).getId())) {
                    ShareToWechat();
                } else if ("4".equals(getSharesLists().get(position).getId())) {
                    ShareToQzone();
                }

            }
        });

        setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_popupwindow));// 设置背景图片，不能在布局中设置，要通过代码来设置

    }


    private String platform = "1,2,3,4,5,6,7,8";

    ArrayList<PlatformForShare> shares = new ArrayList<PlatformForShare>();

    public ArrayList<PlatformForShare> getSharesLists() {

        if (shares.size() != 0) {
            return shares;
        }
        if (platform.contains("2")) {
            PlatformForShare platformForShare = new PlatformForShare("分享微信", "Wechat", "2", R.drawable.fenxiang_weixin);
            shares.add(platformForShare);
        }
        if (platform.contains("1")) {
            PlatformForShare platformForShare = new PlatformForShare("分享朋友圈", "WechatMoments", "1", R.drawable.fenxiang_pengyouquan);
            shares.add(platformForShare);
        }
        if (platform.contains("4")) {
            PlatformForShare platformForShare = new PlatformForShare("分享qq", "QZone", "4", R.drawable.fenxiang_qq);
            shares.add(platformForShare);
        }

        return shares;
    }


    private void ShareToQzone() {
        Platform.ShareParams sp5 = new Platform.ShareParams();
        sp5.setShareType(Platform.SHARE_WEBPAGE);
        sp5.setTitle(shareBean.title);
        sp5.setTitleUrl(shareBean.pageUrl);
        sp5.setText(shareBean.text);
//            sp5.setUrl("http://download.csdn.net/detail/liufatao/9844983?web=web");
//            sp5.setImageUrl(TextUtils.isEmpty(img) ? GetServerUrl.ICON_PAHT : img);
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此
        sp5.setSiteUrl(shareBean.pageUrl);
        sp5.setUrl(shareBean.pageUrl);
        sp5.setImageUrl(TextUtils.isEmpty(shareBean.imgUrl) ? GetServerUrl.ICON_PAHT : shareBean.pageUrl);
//            sp5.setImageUrl(url);
//            sp5.setImagePath("http://f11.baidu.com/it/u=1805479168,3437334559&fm=76");
        Platform qzone = ShareSDK.getPlatform(QQ.NAME);
        qzone.setPlatformActionListener(this); // 设置分享事件回调
        // 执行图文分享
        qzone.share(sp5);
    }


    private void ShareToWechat() {
        Platform.ShareParams sp1 = new Platform.ShareParams();
        sp1.setShareType(Platform.SHARE_WEBPAGE);
        sp1.setTitle(shareBean.title);
        sp1.setText(shareBean.text);
        sp1.setImageUrl(TextUtils.isEmpty(shareBean.imgUrl) ? GetServerUrl.ICON_PAHT : shareBean.imgUrl);
//            sp1.setUrl("http://download.csdn.net/detail/liufatao/9844983?web=web");
        sp1.setUrl(shareBean.pageUrl);
//            sp1.setSiteUrl(url);
//            sp1.setImagePath("http://f11.baidu.com/it/u=1805479168,3437334559&fm=76");
        Platform Wechat = ShareSDK.getPlatform("Wechat");
        Wechat.setPlatformActionListener(this);
        Wechat.share(sp1);
    }

    private void ShareToWechatMoments() {
        Platform.ShareParams sp2 = new Platform.ShareParams();
        sp2.setShareType(Platform.SHARE_WEBPAGE);
        sp2.setTitle(shareBean.title);
        sp2.setText(shareBean.text);
//            sp2.setImageUrl(TextUtils.isEmpty(img) ? GetServerUrl.ICON_PAHT : img);
        sp2.setUrl(shareBean.pageUrl);
        sp2.setTitleUrl(shareBean.pageUrl);
        sp2.setSiteUrl(shareBean.pageUrl);
        sp2.setImagePath(TextUtils.isEmpty(shareBean.imgUrl) ? GetServerUrl.ICON_PAHT : shareBean.imgUrl);
        Platform Wechat_men = ShareSDK.getPlatform("WechatMoments");
        Wechat_men.setPlatformActionListener(this);
        Wechat_men.share(sp2);
    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        ToastUtil.showShortToast("分享成功");
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

        ToastUtil.showShortToast("分享失败");
    }

    @Override
    public void onCancel(Platform platform, int i) {
        ToastUtil.showShortToast("分享取消");

    }


    private class SharePoPAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<PlatformForShare> shares;
        private ImageView icon;

        public SharePoPAdapter(Context context, ArrayList<PlatformForShare> shares) {
            this.context = context;
            this.shares = shares;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.list_item_pop_share, null);
            TextView tv_list_item = (TextView) inflate.findViewById(R.id.tv_list_item);
            icon = (ImageView) inflate.findViewById(R.id.icon);
            tv_list_item.setText(shares.get(position).getName());
            icon.setImageResource(shares.get(position).getPic());
            return inflate;
        }
    }
}

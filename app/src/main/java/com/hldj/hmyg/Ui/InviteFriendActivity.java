package com.hldj.hmyg.Ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.hldj.hmyg.M.IntegralBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.application.Data;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.widget.ShareDialogFragment;
import com.hy.utils.ToastUtil;
import com.lqr.optionitemview.OptionItemView;
import com.zxing.encoding.EncodingHandler;

import net.tsz.afinal.http.AjaxCallBack;

import java.util.List;

import cn.sharesdk.framework.Platform;

import static com.hldj.hmyg.StoreActivity.getRoundCornerImage;

/**
 * 邀请好友  界面   分享链接邀请好友
 */

public class InviteFriendActivity extends BaseMVPActivity {


    CoreRecyclerView recycle;

    List<IntegralBean> integralBeens;

    OptionItemView top;


  TextView toolbar_right_text ;

    @Override
    public int bindLayoutID() {
        return R.layout.activity_invite_friend;
    }


    @Override
    public void initView() {

        toolbar_right_text =  getView(R.id.toolbar_right_text);
        toolbar_right_text.setVisibility(View.VISIBLE);

        toolbar_right_text.setText("邀请记录");
        toolbar_right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InviteFriendListActivity.start(mActivity);
            }
        });
//        Platform.ShareParams sp2 = new Platform.ShareParams();
//        sp2.setShareType(Platform.SHARE_WEBPAGE);
//        sp2.setTitle("花木易购APP下载 ");
//        sp2.setText("欢迎使用花木易购代购型苗木交易平台。指尖轻点，交易无忧！ ");
//        sp2.setImageUrl(img);
//        sp2.setUrl(Data.appDoloadUrl);
//        sp2.setTitleUrl(Data.appDoloadUrl);
//        sp2.setSiteUrl(Data.appDoloadUrl);

        ImageView imageView = getView(R.id.qc_code);

        {

            String shareUrl = Data.getAppDoloadUrlForInvite(MyApplication.getUserBean().id);

            if (TextUtils.isEmpty(shareUrl)) {
                ToastUtil.showShortToast("对不起，无分享地址");
                return;
            }
            try {
                Bitmap qrCodeBitmap = EncodingHandler.createQRCode(shareUrl, 550);
                qrCodeBitmap = getRoundCornerImage(qrCodeBitmap, 3);

                imageView.setImageBitmap(qrCodeBitmap);


//                try {
//                  /  img_path = FileUtil.saveMyBitmap("commenQcCode", qrCodeBitmap);
//                    if (!"".equals(img_path)) {
//                        ossImagePaths.add(new Pic("", false, img_path, 0));
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            } catch (WriterException e) {
                e.printStackTrace();
            }


        }


        getView(R.id.share_left).setOnClickListener(v -> {
            ToastUtil.showLongToast("分享到微信");

            Platform.ShareParams sp1 = new Platform.ShareParams();
            sp1.setShareType(Platform.SHARE_WEBPAGE);
            sp1.setTitle(title);
            sp1.setText(text);
//        sp1.setImageUrl(img);
            sp1.setImageUrl("");
//        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResourcePackageName(R.drawable.图片名称) + "/" + r.getResourceTypeName(R.drawable.图片名称) + "/" + r.getResourceEntryName(R.drawable.图片名称))

//            sp1.setUrl(Data.appDoloadUrl);
            sp1.setUrl(Data.getAppDoloadUrlForInvite(MyApplication.getUserBean().id));
            sp1.setSiteUrl(Data.getAppDoloadUrlForInvite(MyApplication.getUserBean().id));
            ShareDialogFragment.ShareToWechat(sp1);

        });

        getView(R.id.share_right).setOnClickListener(v -> {
            ToastUtil.showLongToast("分享到朋友圈");
            Platform.ShareParams sp1 = new Platform.ShareParams();
            sp1.setShareType(Platform.SHARE_WEBPAGE);
            sp1.setTitle(title);
            sp1.setText(text);
//        sp1.setImageUrl(img);
            sp1.setImageUrl("");
//        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResourcePackageName(R.drawable.图片名称) + "/" + r.getResourceTypeName(R.drawable.图片名称) + "/" + r.getResourceEntryName(R.drawable.图片名称))

//            sp1.setUrl(Data.appDoloadUrl);
            sp1.setUrl(Data.getAppDoloadUrlForInvite(MyApplication.getUserBean().id));

            sp1.setSiteUrl(Data.getAppDoloadUrlForInvite(MyApplication.getUserBean().id));

            ShareDialogFragment.ShareToWechatMoments(sp1);
        });
    }

    private String title = "花木易购注册邀请";
    private String text = "卖过那么多苗，用过那么多app，还是这个靠谱!";


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, InviteFriendActivity.class));
    }


    public void requestDatas(AjaxCallBack callBack) {
//        new BasePresenter()
//                .doRequest("userPoint/list", true, callBack);
        new BasePresenter()
                .doRequest("admin/userPoint/index", true, callBack);
    }


    @Override
    public String setTitle() {
        return "分享链接邀请好友";
    }
}

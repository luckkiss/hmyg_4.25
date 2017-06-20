package com.hldj.hmyg.presenter;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.http.AjaxCallBack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2017/5/19.
 */

public class EPrestenter extends BasePresenter {


    public EPrestenter upLoadHeadImg(String path, boolean isAddHead, String imgUrl,Bitmap bmp) {

//        int rotate = PictureManageUtil.getCameraPhotoOrientation(imgUrl);

//        Bitmap bitmap = PictureManageUtil.getCompressBm(imgUrl) ;
//        Bitmap bitmap1 = PictureManageUtil.rotateBitmap(bitmap, rotate);

        getAjaxParams().put("headImage", new ByteArrayInputStream(Bitmap2Bytes(bmp)), System.currentTimeMillis() + ".png");

        AjaxCallBack callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                    SimpleGsonBean simpleGsonBean = GsonUtil.formateJson2Bean(json, SimpleGsonBean.class);

                    if (simpleGsonBean.code.equals(ConstantState.SUCCEED_CODE) & !TextUtils.isEmpty(simpleGsonBean.getData().headImage)) {
                        resultCallBack.onSuccess(simpleGsonBean.getData().headImage);
                    } else {
                        ToastUtil.showShortToast(simpleGsonBean.msg);
                    }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                resultCallBack.onFailure(t, errorNo, strMsg);
            }
        };
        return (EPrestenter) doRequest(path, isAddHead, callBack);
//        return this;
    }


    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 50, baos);//暂时压缩百分50
        return baos.toByteArray();
    }


/**
 *     public void updateImage(String url, String imagType, String sourceId) {
 FinalHttp finalHttp = new FinalHttp();
 GetServerUrl.addHeaders(finalHttp, true);
 AjaxParams params = new AjaxParams();
 int rotate = PictureManageUtil.getCameraPhotoOrientation(url);
 // 把压缩的图片进行旋转
 params.put(
 "headImage",
 new ByteArrayInputStream(Bitmap2Bytes(PictureManageUtil
 .rotateBitmap(PictureManageUtil.getCompressBm(url),
 rotate))), System.currentTimeMillis() + ".png");

 finalHttp.post(GetServerUrl.getUrl() + "admin/file/uploadHeadImage",
 params, new AjaxCallBack<Object>() {

@Override public void onStart() {

if (loading != null && !EActivity.this.isFinishing()) {
loading.showToastAlong();
} else if (loading == null
&& !EActivity.this.isFinishing()) {
loading = new Loading(EActivity.this, "头像修改中.....");
loading.showToastAlong();
}
super.onStart();
}

@Override public void onSuccess(Object t) {
try {
JSONObject jsonObject = new JSONObject(t.toString());
String msg = jsonObject.getString("msg");
int code = jsonObject.getInt("code");
if (code == 1) {
JSONObject data = JsonGetInfo.getJSONObject(
jsonObject, "data");
String headImage = JsonGetInfo.getJsonString(
data, "headImage");
editor.putString("headImage", headImage);
editor.commit();
if (!"".equals(MyApplication.Userinfo
.getString("headImage", ""))) {
//                                    ImageLoader.getInstance().displayImage(
//                                            MyApplication.Userinfo.getString(
//                                                    "headImage", ""),
//                                            iv_icon_persion_pic);
//                                    fb.display(iv_icon_persion_pic, MyApplication.Userinfo.getString("headImage", ""));
//                                    finalBitmapDisplay();
finalBitmapDisplayNo();
}

}
} catch (JSONException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}

if (loading != null && !EActivity.this.isFinishing()) {
loading.cancel();
}
super.onSuccess(t);
}

@Override public void onFailure(Throwable t, int errorNo,
String strMsg) {
// TODO Auto-generated method stub
if (loading != null && !EActivity.this.isFinishing()) {
loading.cancel();
}
super.onFailure(t, errorNo, strMsg);
}
});
 }
 */

//    public BPresenter getDatas(String url, boolean isAddHead) {
//
//        AjaxCallBack callBack = new AjaxCallBack<String>() {
//            @Override
//            public void onSuccess(String json) {
//                try {
//                    BPageGsonBean gsonBean = GsonUtil.formateJson2Bean(json, BPageGsonBean.class);
//                    if (gsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
//                        resultCallBack.onSuccess(gsonBean.data.page.data);
//                    } else {
//                        //失败
//                        resultCallBack.onSuccess(gsonBean.msg);
//                    }
//                } catch (Exception e) {
//                    ToastUtil.showShortToast("获取数据失败" + e.getMessage());
//                    resultCallBack.onFailure(null, -1, e.getMessage());
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                ToastUtil.showShortToast("网络错误，数据请求失败");
//                super.onFailure(t, errorNo, strMsg);
//            }
//        };
//        return (BPresenter) doRequest(url, isAddHead, callBack);
//    }


}

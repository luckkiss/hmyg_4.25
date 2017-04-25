package com.hldj.hmyg.presenter;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * Created by Administrator on 2017/4/24.
 */

public class CollectPresenter {


    ResultCallBack<SimpleGsonBean> simpleGsonBeanResultCallBack;

    public CollectPresenter(ResultCallBack<SimpleGsonBean> simpleGsonBeanResultCallBack) {
        this.simpleGsonBeanResultCallBack = simpleGsonBeanResultCallBack;
    }


    /**
     * 添加删除   收藏夹接口
     *
     * @param id
     */
    public void reqCollect(String id) {
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("sourceId", id);
        params.put("type", "seedling");

        finalHttp.post(GetServerUrl.getUrl() + "admin/collect/save", params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String s) {
//                {"code":"1","msg":"操作成功","data":{"isCollect":false}}
                SimpleGsonBean simpleGsonBean = GsonUtil.formateJson2Bean(s, SimpleGsonBean.class);
                if (simpleGsonBean.code.equals("1")) {
                    simpleGsonBeanResultCallBack.onSuccess(simpleGsonBean);
                } else {
                    ToastUtil.showShortToast(simpleGsonBean.msg);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                ToastUtil.showShortToast("网络超时");
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }


    /**
     * 清空收藏夹
     */
    public void reqClearCollect(String type) {
        D.e("==============清空收藏夹============");
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("type", type);
        finalHttp.post(GetServerUrl.getUrl() + "admin/collect/empty", params, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {

                SimpleGsonBean simpleGsonBean = GsonUtil.formateJson2Bean(json, SimpleGsonBean.class);

                if (simpleGsonBean.code.equals("1")) {

                    simpleGsonBeanResultCallBack.onSuccess(simpleGsonBean);
                    ToastUtil.showShortToast("清空成功");

                } else {
                    ToastUtil.showShortToast(simpleGsonBean.msg);
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }


}

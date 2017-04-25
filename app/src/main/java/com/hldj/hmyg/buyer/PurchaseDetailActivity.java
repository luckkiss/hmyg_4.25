package com.hldj.hmyg.buyer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.buyer.P.PurchaseDeatilP;
import com.hldj.hmyg.buyer.V.PurchaseDeatilV;
import com.hy.utils.ToastUtil;

import java.util.List;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;


@SuppressLint("NewApi")
public class PurchaseDetailActivity extends NeedSwipeBackActivity implements PurchaseDeatilV {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puchase_detail_bak);

        if (null == getIntent()) {
            ToastUtil.showShortToast("用 start2activity来条状本届面");
            return;
        }


    }

    /**
     * 接口请求到数据后 调用
     *
     * @param saveSeedingGsonBean
     */
    private void initDatas(SaveSeedingGsonBean saveSeedingGsonBean) {

        SaveSeedingGsonBean.DataBean.SeedlingBean item = saveSeedingGsonBean.getData().getItem();

        List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen = saveSeedingGsonBean.getData().getTypeList();
        List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> plantTypeListBeen = saveSeedingGsonBean.getData().getPlantTypeList() ;

    }






    ViewHolder viewHolder;

    @Override
    public void InitViews() {
        viewHolder = new ViewHolder(this);
    }

    @Override
    public void InitHolder() {

    }

    @Override
    public void InitOnclick() {

    }

    @Override
    public void getDatas() {
        new PurchaseDeatilP(new ResultCallBack<SaveSeedingGsonBean>() {
            @Override
            public void onSuccess(SaveSeedingGsonBean saveSeedingGsonBean) {
                initDatas(saveSeedingGsonBean);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {

            }
        }).getDatas(getIntent().getExtras().get(GOOD_ID).toString());//请求数据  进行排版

    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_use_addr;
        public SuperTextView tv_close_time;
        public SuperTextView tv_left;
        public SuperTextView tv_right;
        public SuperTextView tv_up_load_img;
        public SuperTextView tv_remark;
        public TextView tv_cli_to_save;

        public ViewHolder(Activity rootView) {

            this.tv_use_addr = (TextView) rootView.findViewById(R.id.tv_use_addr);
            this.tv_close_time = (SuperTextView) rootView.findViewById(R.id.tv_close_time);
            this.tv_left = (SuperTextView) rootView.findViewById(R.id.tv_left);
            this.tv_right = (SuperTextView) rootView.findViewById(R.id.tv_right);
            this.tv_up_load_img = (SuperTextView) rootView.findViewById(R.id.tv_up_load_img);
            this.tv_remark = (SuperTextView) rootView.findViewById(R.id.tv_remark);
            this.tv_cli_to_save = (TextView) rootView.findViewById(R.id.tv_cli_to_save);
        }

    }


    private static final String GOOD_ID = "good_id";//本届面传过来的id


    public static void start2Activity(Activity activity, String good_id) {
        Intent intent = new Intent(activity, PurchaseDetailActivity.class);
        intent.putExtra(GOOD_ID, good_id);
        activity.startActivityForResult(intent, 100);
    }


}

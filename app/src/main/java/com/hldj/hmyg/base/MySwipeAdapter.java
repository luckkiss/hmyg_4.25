package com.hldj.hmyg.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.adapter.ProductListAdapter;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.presenter.CollectPresenter;
import com.hldj.hmyg.util.D;
import com.hy.utils.ToastUtil;

import net.tsz.afinal.FinalBitmap;

import java.util.List;


/**
 * Created by Administrator on 2017/4/24.
 */

public class MySwipeAdapter extends BaseSwipeAdapter {
    List<SaveSeedingGsonBean.DataBean.SeedlingBean> items;
    Context context;
    public FinalBitmap finalBitmap;

    public MySwipeAdapter(Context context, List<SaveSeedingGsonBean.DataBean.SeedlingBean> items) {
        this.context = context;
        this.items = items;

        finalBitmap = FinalBitmap.create(context);
        finalBitmap.configLoadingImage(R.drawable.no_image_show);
    }


    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.sl_content;
    }


    @Override
    public View generateView(final int i, ViewGroup viewGroup) {


        View view = View.inflate(context, R.layout.list_view_seedling_new_shoucan, null);


        return view;
    }


    @Override
    public void fillValues(final int i, View view) {
//        TextView tv = (TextView) view.findViewById(R.id.tv);
//        final CheckBox cb_swipe_tag1 = (CheckBox) view.findViewById(R.id.cb_swipe_tag1);

        SaveSeedingGsonBean.DataBean.SeedlingBean seedlingBean = items.get(i);

        int layoutId = R.layout.list_view_seedling_new_shoucan;

        ImageView iv_img = (ImageView) view.findViewById(R.id.iv_img);
        finalBitmap.display(iv_img, seedlingBean.getImageUrl());

        //设置小图标
        TextView tv_01 = (TextView) view.findViewById(R.id.tv_01);
        setSrcByType(tv_01, seedlingBean.getPlantType());

        //名字
        TextView tv_02 = (TextView) view.findViewById(R.id.tv_02);
        tv_02.setText(seedlingBean.getName());

        //地区：
        TextView tv_03 = (TextView) view.findViewById(R.id.tv_03);
        tv_03.setText(seedlingBean.getCiCity().getFullName());

        //拼接的   地径  长度
        TextView tv_04 = (TextView) view.findViewById(R.id.tv_04);
        tv_04.setText(seedlingBean.getSpecText());

//            发布人
        TextView tv_06 = (TextView) view.findViewById(R.id.tv_06);
        setName(tv_06, seedlingBean);

//           价格
        TextView tv_07 = (TextView) view.findViewById(R.id.tv_07);
        boolean isNeGo = seedlingBean.isNego();
        String maxPrice = seedlingBean.getMinPrice() + "";
        String minPrice = seedlingBean.getMaxPrice() + "";
        ProductListAdapter.setPrice(tv_07, maxPrice, minPrice, isNeGo);

//           库存
        TextView tv_09 = (TextView) view.findViewById(R.id.tv_09);
        tv_09.setText(seedlingBean.getCount() + "");

        view.findViewById(R.id.layoutRoot).setOnClickListener(v -> {
            //点击布局
            D.e("==点击布局==");
            FlowerDetailActivity.start2Activity(context, "show_type", items.get(i).getId());
        });

        view.findViewById(R.id.tv_delete_item).setOnClickListener(v -> {
            ToastUtil.showShortToast("删除");

            new CollectPresenter(new ResultCallBack<SimpleGsonBean>() {
                @Override
                public void onSuccess(SimpleGsonBean simpleGsonBean) {
                    //成功删除某个item
                    items.remove(i);
                    notifyDataSetChanged();
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {

                }
            })
                    .reqCollect(items.get(i).getId());


        });

    }

    void setName(TextView textView, SaveSeedingGsonBean.DataBean.SeedlingBean bean) {

        if (bean.getNurseryJson() == null) {
            textView.setText("");
            return;
        }
        if (!TextUtils.isEmpty(bean.getNurseryJson().getCompanyName())) {
            textView.setText(bean.getNurseryJson().getCompanyName());
        } else if (!TextUtils.isEmpty(bean.getNurseryJson().getPublicName())) {
            textView.setText(bean.getNurseryJson().getPublicName());
        } else if (!TextUtils.isEmpty(bean.getNurseryJson().getRealName())) {
            textView.setText(bean.getNurseryJson().getRealName());
        }
    }

    void setSrcByType(TextView textView, String type) {
        switch (type) {
            case "plantType":
                textView.setBackgroundResource(R.drawable.icon_seller_di);
                break;
            case "transplant":
                textView.setBackgroundResource(R.drawable.icon_seller_yi);

                break;
            case "heelin":
                textView.setBackgroundResource(R.drawable.icon_seller_jia);
                break;
            case "container":
                textView.setBackgroundResource(R.drawable.icon_seller_rong);
                break;
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }


    @Override
    public Object getItem(int position) {
        return items.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


}

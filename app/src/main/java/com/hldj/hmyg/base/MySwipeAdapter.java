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
        closeAllItems();

        int layoutId = R.layout.list_view_seedling_new_shoucan;

        ImageView iv_img = (ImageView) view.findViewById(R.id.iv_img);
        finalBitmap.display(iv_img, seedlingBean.getSmallImageUrl());


        View tv_right_top = view.findViewById(R.id.iv_right_top);

        if (seedlingBean.attrData.ziying) {//自营显示票
            tv_right_top.setVisibility(View.VISIBLE);
        } else {
            tv_right_top.setVisibility(View.GONE);
        }


        //设置小图标
        TextView tv_01 = (TextView) view.findViewById(R.id.tv_01);
        setSrcByType(tv_01, seedlingBean.getPlantType());

        //名字
        TextView tv_02 = (TextView) view.findViewById(R.id.tv_02);
        tv_02.setText(seedlingBean.getName());


        //拼接的   地径  长度
        TextView tv_03 = (TextView) view.findViewById(R.id.tv_03);
        tv_03.setText(seedlingBean.getSpecText());

        //地区：
        TextView tv_04 = (TextView) view.findViewById(R.id.tv_04);
        tv_04.setText("苗源地:" + seedlingBean.getCiCity().getFullName());

//            发布人
        TextView tv_06 = (TextView) view.findViewById(R.id.tv_06);
        setName(tv_06, seedlingBean);

//           价格
        TextView tv_07 = (TextView) view.findViewById(R.id.tv_07);


        boolean isNeGo = seedlingBean.isNego();
        String maxPrice = seedlingBean.getMinPrice() + "";
        String minPrice = seedlingBean.getMaxPrice() + "";

        TextView tv_08 = (TextView) view.findViewById(R.id.tv_08);
        tv_08.setText("/" + seedlingBean.getUnitTypeName());
        ProductListAdapter.setPrice(tv_07, maxPrice, minPrice, isNeGo, tv_08);

//           库存
        TextView tv_09 = (TextView) view.findViewById(R.id.tv_09);
        tv_09.setText("库存: " + seedlingBean.getCount() + "");


        view.findViewById(R.id.tv_delete_item).setOnClickListener(v -> {
            ToastUtil.showShortToast("删除");

            new CollectPresenter(new ResultCallBack<SimpleGsonBean>() {
                @Override
                public void onSuccess(SimpleGsonBean simpleGsonBean) {
                    //成功删除某个item
                    items.remove(i);
                    notifyDataSetChanged();
                    closeItem(i);
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {

                }
            })
                    .reqCollect(items.get(i).getId());


        });

//[model.status isEqualToString:@"published"]
        // 过期
        if (!seedlingBean.getStatus().equals("published")) {
            view.findViewById(R.id.fr_goods_time_out).setVisibility(View.VISIBLE);
            view.findViewById(R.id.ll_info_content).setAlpha(0.6f);
            return;
        }

        view.findViewById(R.id.layoutRoot).setOnClickListener(v -> {
            //点击布局
            D.e("==点击布局==");
            FlowerDetailActivity.start2Activity(context, "show_type", items.get(i).getId());
        });

    }

    /**
     * openItem(int position)：打开某个item的侧滑
     * closeItem(int position)：关闭某个打开的侧滑
     * getOpenItems()：获取所有打开的item
     * isOpen(int position):判断某个位置的item是否打开侧滑
     * getMode()：获取侧滑显示模式
     * setMode(Attributes.Mode mode)：设置侧滑显示模式
     *
     * @param textView
     * @param bean
     */

    void setName(TextView textView, SaveSeedingGsonBean.DataBean.SeedlingBean bean) {

        if (bean.getOwnerJson() == null) {
            textView.setText("发布人:-");
            return;
        }
        if (!TextUtils.isEmpty(bean.getOwnerJson().getCompanyName())) {
            textView.setText("发布人:" + bean.getOwnerJson().getCompanyName());
        } else if (!TextUtils.isEmpty(bean.getOwnerJson().getPublicName())) {
            textView.setText("发布人:" + bean.getOwnerJson().getPublicName());
        } else if (!TextUtils.isEmpty(bean.getOwnerJson().getRealName())) {
            textView.setText("发布人:" + bean.getOwnerJson().getRealName());
        }
    }

    public static void setSrcByType(TextView textView, String type) {
        switch (type) {
            case "plantType":
                textView.setBackgroundResource(R.drawable.icon_seller_di);
                break;
            case "planted":
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

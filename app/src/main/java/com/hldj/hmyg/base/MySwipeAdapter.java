package com.hldj.hmyg.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.hldj.hmyg.CallBack.IEditable;
import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.R;
import com.hldj.hmyg.adapter.ProductListAdapter;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.util.D;

import net.tsz.afinal.FinalBitmap;

import java.util.List;


/**
 * Created by Administrator on 2017/4/24.
 */

public class MySwipeAdapter extends BaseSwipeAdapter implements IEditable {
    List<SaveSeedingGsonBean.DataBean.SeedlingBean> items;
    Context context;
    public FinalBitmap finalBitmap;

    private boolean isEditAble = false;
    private boolean isSelectAll = false;


    public MySwipeAdapter(Context context, List<SaveSeedingGsonBean.DataBean.SeedlingBean> items) {
        this.context = context;
        this.items = items;

        finalBitmap = FinalBitmap.create(context);
        finalBitmap.configLoadingImage(R.drawable.no_image_show);
    }


    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.sl_content;
//        return R.id.sl_content;
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


//        helper.setChecked(R.id.checkBox, item.isChecked())
//                .setVisible(R.id.checkBox, isRightEditable)
////                .setVisible(R.id.checkBoxParent, isEditAble)
////                .addOnClickListener(R.id.checkBoxParent, v -> {
////                    item.toggle();
////                    helper.setChecked(R.id.checkBox, item.isChecked());
////                })
//                .addOnClickListener(R.id.checkBox, v -> {
//                    item.toggle();
//
//                });


        SaveSeedingGsonBean.DataBean.SeedlingBean seedlingBean = items.get(i);

        CheckBox checkBox = view.findViewById(R.id.checkBox);

        checkBox.setVisibility(isEditAble ? View.VISIBLE : View.GONE);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seedlingBean.toggle();
            }
        });

        checkBox.setChecked(seedlingBean.isChecked());


        closeAllItems();

        int layoutId = R.layout.list_view_seedling_new_shoucan;

        SwipeLayout layout = (SwipeLayout) view.findViewById(getSwipeLayoutResourceId(i));
        layout.setSwipeEnabled(false);


        ImageView iv_img = (ImageView) view.findViewById(R.id.iv_img);
        finalBitmap.display(iv_img, seedlingBean.getSmallImageUrl());


//        View iv_right_top = view.findViewById(R.id.iv_right_top);

//        if (seedlingBean.attrData.ziying) {//自营显示票
//            iv_right_top.setVisibility(View.VISIBLE);
//        } else {
//            iv_right_top.setVisibility(View.GONE);
//        }


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
        //苗源地:
        tv_04.setText("" + seedlingBean.getCiCity().getFullName());

//            发布人
        TextView tv_06 = (TextView) view.findViewById(R.id.tv_06);
        setName(tv_06, seedlingBean);

//           价格
        TextView tv_07 = (TextView) view.findViewById(R.id.tv_07);


        boolean isNeGo = seedlingBean.isNego();
        String maxPrice = seedlingBean.getMinPrice() + "";
        String priceStr = seedlingBean.getPriceStr() + "";
        String minPrice = seedlingBean.getMaxPrice() + "";

        TextView tv_08 = (TextView) view.findViewById(R.id.tv_08);
        tv_08.setText("/" + seedlingBean.getUnitTypeName());
        ProductListAdapter.setPrice(tv_07, priceStr, minPrice, isNeGo, tv_08);

//           库存
        TextView tv_09 = (TextView) view.findViewById(R.id.tv_09);
        tv_09.setText("库存: " + seedlingBean.getCount() + "");


//        View tv_right_top = view.findViewById(R.id.tv_right_top);
//        tv_right_top.setVisibility(View.GONE);
//      tv_delete_item   侧滑 删除时使用

//        view.findViewById(R.id.tv_right_top).setOnClickListener(v -> {
//
//            new AlertDialog(context).builder()
////                            .setTitle("确定清空所有收藏?")
//                    .setTitle("确定删除本项?")
//                    .setPositiveButton("确定", v1 -> {
//
//                        {
//                            new CollectPresenter(new ResultCallBack<SimpleGsonBean>() {
//                                @Override
//                                public void onSuccess(SimpleGsonBean simpleGsonBean) {
//                                    ToastUtil.showShortToast("删除成功");
//                                    //成功删除某个item
//                                    try {
//                                        items.remove(i);
//                                        notifyDataSetChanged();
//                                        closeItem(i);
//                                    } catch (Exception e) {
//                                        notifyDataSetChanged();
//                                        Log.i("======删除报错===刷新列表===", "Exception: " + e.getMessage());
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Throwable t, int errorNo, String strMsg) {
//
//                                }
//                            })
//                                    .reqCollect(items.get(i).getId());
//
//                        }
//
//
//                    }).setNegativeButton("取消", v2 -> {
//            }).show();
//
//
//        });

//[model.status isEqualToString:@"published"]
        // 过期
        if (!seedlingBean.getStatus().equals("published")) {
            view.findViewById(R.id.fr_goods_time_out).setVisibility(View.VISIBLE);
            view.findViewById(R.id.fr_goods_time_out).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.toggle();
                    seedlingBean.toggle();
                }
            });
            view.findViewById(R.id.ll_info_content).setAlpha(0.6f);

        } else {
            view.findViewById(R.id.fr_goods_time_out).setVisibility(View.GONE);
            view.findViewById(R.id.ll_info_content).setAlpha(1.0f);
            view.findViewById(R.id.layoutRoot).setOnClickListener(v -> {
                //点击布局
                D.e("==点击布局==");

                if (this.isEditAble) {
                    checkBox.toggle();
                    seedlingBean.toggle();
                } else {
                    FlowerDetailActivity.start2Activity(context, "show_type", items.get(i).getId());

                }

            });
        }


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

    public static void setName(TextView textView, SaveSeedingGsonBean.DataBean.SeedlingBean bean) {
        if (bean.getOwnerJson() == null) {
            textView.setText("发布人:-");
            return;
        }
        textView.setText("发布人:" + bean.getOwnerJson());
        D.i("--------发布人--bean.getOwnerJson()---" + bean.getOwnerJson());
//        if (!TextUtils.isEmpty(bean.getOwnerJson())) {
//            textView.setText("发布人:" + bean.getOwnerJson().getCompanyName());
//        } else if (!TextUtils.isEmpty(bean.getOwnerJson().getPublicName())) {
//            textView.setText("发布人:" + bean.getOwnerJson().getPublicName());
//        } else if (!TextUtils.isEmpty(bean.getOwnerJson().getRealName())) {
//            textView.setText("发布人:" + bean.getOwnerJson().getRealName());
//        } else if (!TextUtils.isEmpty(bean.getOwnerJson().getUserName())) {//用户名
//            textView.setText("发布人:" + bean.getOwnerJson().getUserName());
//        } else {
//            textView.setText("发布人:-");
//        }
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


    @Override
    public IEditable toggleSelectAll() {
        this.isSelectAll = !this.isSelectAll;
        if (items != null && items.size() > 0) {
            for (SaveSeedingGsonBean.DataBean.SeedlingBean item : items) {
                item.setChecked(isSelectAll);
            }
        }
        notifyDataSetChanged();
        return this;
    }

    @Override
    public IEditable toggleEditable() {
        this.isEditAble = !this.isEditAble;
        this.notifyDataSetChanged();
        return this;
    }

    @Override
    public boolean isSelectAll() {


        return isSelectAll;
    }

    @Override
    public boolean isEditable() {
        return isEditAble;
    }

    @Override
    public String getDeleteIds() {

        if (items == null || items.size() == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (SaveSeedingGsonBean.DataBean.SeedlingBean item : items) {
            if (item.isChecked())
                stringBuilder.append(item.getCollectId() + ",");
        }
        D.i("-------------getDeleteIds--------" + stringBuilder.toString());
        return stringBuilder.toString();
    }
}

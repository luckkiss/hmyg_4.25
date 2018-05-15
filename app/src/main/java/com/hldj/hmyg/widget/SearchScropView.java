package com.hldj.hmyg.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.SellectActivity2;
import com.hldj.hmyg.saler.purchase.userbuy.RxSeekBar;
import com.hldj.hmyg.util.D;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * 创建控件来 控制筛选中的  输入{@link SellectActivity2}
 */


public class SearchScropView extends BaseLinearLayout<SellectActivity2.TypesBean.DataBean.MainSpecBean> {

    private RxSeekBar seek_bar;
    private ViewHolder mViewHolder;
    public String type = "";//上传时候用，表示类型   --- 肝经  地径  胸襟  什么的

    public SearchScropView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setContextView() {
        return R.layout.view_search_scrop;
    }

    @Override
    public BaseLinearLayout initViewHolder(View viewRoot) {
        mViewHolder = new ViewHolder(viewRoot);
        return this;
    }

    @Override
    public BaseLinearLayout setDatas(SellectActivity2.TypesBean.DataBean.MainSpecBean mainSpecBean) {
//           String title;
//          String value;
//          List<ListBean> list;
        if (mainSpecBean == null) {
            this.setVisibility(GONE);
            D.e("=========隐藏控件=====mainSpecBean == null====");
        } else {
            setText(mViewHolder.tv_text, mainSpecBean.title + ": ");//设置左边 显示的字    例如   规格   肝经
            type = mainSpecBean.value;
            setText(mViewHolder.et_min, ""); // 最小值
            setText(mViewHolder.et_max, ""); // 最大值
            initList(mainSpecBean.list); // 初始化  动态列表
        }

//     mViewHolder.et_min.setText();


        return this;
    }

    private void initList(List<SellectActivity2.TypesBean.DataBean.ListBean> list) {
        if (list != null) {
            initAutoLayout(mViewHolder.flowlayout, list, -1, ((Activity) getContext()), new TagFlowLayout.OnTagClickWithCancleListener() {
                @Override
                public boolean onCancleClick(View view, int position, FlowLayout parent) {
                    resetValue();
                    return true;
                }

                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    SellectActivity2.TypesBean.DataBean.ListBean model = list.get(position);
//                    ToastUtil.showShortToast("" +model);
                    setMin(model.min);
                    setMax(model.max);
                    setSeek_bar(model.min, model.max);
                    return true;
                }
            });
        }
    }


    private static class ViewHolder {
        View rootView;
        TextView tv_text;
        EditText et_min;
        EditText et_max;
        RxSeekBar seek_bar;
        TagFlowLayout flowlayout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.seek_bar = (RxSeekBar) rootView.findViewById(R.id.seek_bar);
            this.tv_text = (TextView) rootView.findViewById(R.id.tv_text);
            this.et_min = (EditText) rootView.findViewById(R.id.et_min);
            this.et_max = (EditText) rootView.findViewById(R.id.et_max);
            this.flowlayout = (TagFlowLayout) rootView.findViewById(R.id.flowlayout);

            seek_bar.setOnRangeChangedListener(new RxSeekBar.OnRangeChangedListener() {
                @Override
                public void onRangeChanged(RxSeekBar view, float min, float max, boolean isFromUser) {
                    et_min.setText(((int) min) + "");
                    et_max.setText(((int) max) + "");
                }
            });

        }


    }


    //获取最小值
    public String getMin() {
        return mViewHolder.et_min.getText().toString();
    }

    //还原最小值
    public void setMin(String oldValue) {
        setText(mViewHolder.et_min, oldValue);
    }

    public void setMin(int oldValue) {
        setText(mViewHolder.et_min, oldValue + "");
    }

    public void setSeek_bar(String min, String max) {
        int mi = intFormat(min, 0);
        int ma = intFormat(max, 1200);
        mViewHolder.seek_bar.setRules(Float.valueOf(min), Float.valueOf(max),0,1);
        mViewHolder.seek_bar.setValue(mi, ma);
    }// (int) (Float.valueOf( max) - Float.valueOf(min) / 100)

    //还原最大值
    public void setMax(String oldValue) {
        setText(mViewHolder.et_max, oldValue);
    }

    public void setMax(int oldValue) {
        setText(mViewHolder.et_max, oldValue + "");
    }

    //获取最大值
    public String getMax() {
        return mViewHolder.et_max.getText().toString();
    }

    public void resetValue() {
        setMin("");
        setMax("");
    }


    public static void initAutoLayout(final TagFlowLayout mFlowLayout, List<SellectActivity2.TypesBean.DataBean.ListBean> typeListBeen, int index, final Activity Activity, TagFlowLayout.OnTagClickListener onTagClickListener) {

        TagAdapter tagAdapter = new TagAdapter<SellectActivity2.TypesBean.DataBean.ListBean>(typeListBeen) {
            @Override
            public View getView(FlowLayout parent, int position, SellectActivity2.TypesBean.DataBean.ListBean o) {
                TextView tv = (TextView) Activity.getLayoutInflater().inflate(R.layout.tv1, mFlowLayout, false);
                tv.setText(typeListBeen.get(position).text);
                return tv;
            }
        };


        mFlowLayout.setAdapter(tagAdapter);

        mFlowLayout.setMaxSelectCount(1);//最多选择 1 个

        mFlowLayout.setOnTagClickListener(onTagClickListener);

        if (index != -1) tagAdapter.setSelectedList(index);


        D.e("=======选择之后====进行添加数据=========" + typeListBeen.toString());


    }


    public int intFormat(String str, int def) {
        float it = def;
        try {
            it = Float.valueOf(str);
        } catch (NumberFormatException e) {
            it = def;
        }


        return ((int) it);
    }
}

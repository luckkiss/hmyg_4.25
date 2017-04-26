package com.hldj.hmyg.buyer.weidet.Purchase;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.widget.BaseLinearLayout;
import com.neopixl.pixlui.components.edittext.EditText;

/**
 * Created by Administrator on 2017/4/26.
 */

public class PurchaseAutoAddLinearLayout extends BaseLinearLayout {


    public PurchaseAutoAddLinearLayout(Context context) {
        this(context, null);

    }

    public PurchaseAutoAddLinearLayout(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public PurchaseAutoAddLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PurchaseAutoAddLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int setContextView() {
        return R.layout.purchase_auto_add;
    }

    @Override
    public BaseLinearLayout initViewHolder(View viewRoot) {
        viewHolder = new ViewHolder(viewRoot);
        return this;
    }

    @Override
    public <T> BaseLinearLayout setDatas(T t) {
        return null;
    }


    public BaseLinearLayout setData(PlantBean plantBean) {

        if (plantBean.name.equals("价格")) {
            getViewHolder().et_params_03.setHint("元");
        }
        //价格直接写在外面
        getViewHolder().tv_params_01.setText(plantBean.name);

        if (plantBean.required) {//必须
            getViewHolder().tv_params_02.setVisibility(View.INVISIBLE);//隐藏
        } else {
            getViewHolder().tv_params_02.setVisibility(View.VISIBLE);//显示
        }

        if (plantBean.value.equals("dbh"))//为胸径时显示 radiobutton
        {
            getViewHolder().vb_radions.setVisibility(VISIBLE);
        } else {
            getViewHolder().vb_radions.setVisibility(GONE);
        }


        return this;
    }

    public static class PlantBean {
        /**
         * +
         * "name": "地径",
         * "value": "diameter",
         * "required": true
         */
        public String name = "";
        public String value = "";
        public boolean required;

        public PlantBean(String name, String value, boolean required) {
            this.name = name;
            this.value = value;
            this.required = required;
        }
    }

    private ViewHolder viewHolder;

    public ViewHolder getViewHolder() {
        return viewHolder;
    }

    private class ViewHolder {
        private TextView tv_params_01;
        private TextView tv_params_02;
        private EditText et_params_03;
        private ViewStub vb_radions;
//        private RadioGroup vb_radions;

        public ViewHolder(View viewRroot) {
            this.tv_params_01 = (TextView) viewRroot.findViewById(R.id.tv_params_01);
            this.tv_params_02 = (TextView) viewRroot.findViewById(R.id.tv_params_02);
            this.et_params_03 = (EditText) viewRroot.findViewById(R.id.et_params_03);
            this.vb_radions = (ViewStub) viewRroot.findViewById(R.id.vb_radions);
//            this.vb_radions = (RadioGroup) viewRroot.findViewById(R.id.vb_radions);
        }

    }
}

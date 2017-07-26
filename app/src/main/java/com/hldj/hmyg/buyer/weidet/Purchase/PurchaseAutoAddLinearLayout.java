package com.hldj.hmyg.buyer.weidet.Purchase;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.widget.BaseLinearLayout;

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


    String type = " ";
    private boolean required = false;

    public boolean getRequired() {
        return required;
    }

    public BaseLinearLayout setData(PlantBean plantBean) {

        if (plantBean.name.equals("价格")) {
            getViewHolder().et_params_03.setHint("元");
            getViewHolder().et_params_03.setInputType(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
//            getViewHolder().et_params_03.setInputType(EditorInfo.TYPE_CLASS_NUMBER);

        }
        if (plantBean.name.equals("数量")) {
            getViewHolder().et_params_03.setHint("可供数量");
        }

        this.setTag(plantBean);//为这个view 赋值 tag  获取时可以直接 获取当中的tag 判断是哪个上传的值
        //价格直接写在外面
        getViewHolder().tv_params_01.setText(plantBean.name);

        required = plantBean.required;
        if (plantBean.required) {//必须
            //必填情况 添加* 号
            isShowLeft(true, getViewHolder().tv_params_01, R.drawable.seller_redstar);
            getViewHolder().tv_params_02.setVisibility(View.GONE);//隐藏
        } else {
            getViewHolder().tv_params_02.setVisibility(View.GONE);//显示
        }

        if (plantBean.value.equals("dbh"))//为胸径时显示 radiobutton
        {
            select_size = "size30";
            getViewHolder().vb_radions.setVisibility(VISIBLE);
            type = "dbh";
            //显示完以后为radio button 添加获取值
            initStubView(getViewHolder().vb_radions);


        } else if (plantBean.value.equals("diameter")) {
            select_size = "size0";
            getViewHolder().vb_radions.setVisibility(VISIBLE);
        }
//|| plantBean.value.equals("diameter")

        return this;
    }

    //初始化viewstub
    private void initStubView(ViewStub vb_radions) {


        this.findViewById(R.id.rb_auto_add_left).setOnClickListener(clickListener);
        this.findViewById(R.id.rb_auto_add_center).setOnClickListener(clickListener);
        this.findViewById(R.id.rb_auto_add_right).setOnClickListener(clickListener);

        //需要初始化  select_size


    }

    private OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.rb_auto_add_left:
                if (type.equals("dbh")) {
                    select_size = "size30";
                } else {
                    select_size = "size0";
                }
                break;
            case R.id.rb_auto_add_center:
                if (type.equals("dbh")) {
                    select_size = "size100";
                } else {
                    select_size = "size10";
                }
                break;
            case R.id.rb_auto_add_right:
                if (type.equals("dbh")) {
                    select_size = "size130";
                } else {
                    select_size = "size30";
                }
                break;

        }
    };


    private String select_size = "";

    /**
     * 获取返回的  size 值
     *
     * @return
     */
    public String getSelect_size() {
        return select_size;
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

        @Override
        public String toString() {
            return "PlantBean{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    ", required=" + required +
                    '}';
        }

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

    public class ViewHolder {
        public TextView tv_params_01;
        public TextView tv_params_02;
        public EditText et_params_03;
        public ViewStub vb_radions;
        //        private RadioGroup vb_radions;
        public RadioButton rb_auto_add_left;

        public ViewHolder(View viewRroot) {
            this.tv_params_01 = (TextView) viewRroot.findViewById(R.id.tv_params_01);
            this.tv_params_02 = (TextView) viewRroot.findViewById(R.id.tv_params_02);
            this.et_params_03 = (EditText) viewRroot.findViewById(R.id.et_params_03);
            this.vb_radions = (ViewStub) viewRroot.findViewById(R.id.vb_radions);

            rb_auto_add_left = (RadioButton) viewRroot.findViewById(R.id.rb_auto_add_left);


//            vb_radions.findViewById(R.id.rb_auto_add_left).setOnClickListener(clickListener);
//            vb_radions.findViewById(R.id.rb_auto_add_center).setOnClickListener(clickListener);
//            vb_radions.findViewById(R.id.rb_auto_add_right).setOnClickListener(clickListener);


//            this.vb_radions = (RadioGroup) viewRroot.findViewById(R.id.vb_radions);
        }

    }

    public void isShowLeft(boolean flag, TextView textView, int drawableId) {
        if (flag) {
            Drawable drawable = getResources().getDrawable(drawableId);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
        } else {
            //隐藏Drawables
            textView.setCompoundDrawables(null, null, null, null);
        }
    }
}

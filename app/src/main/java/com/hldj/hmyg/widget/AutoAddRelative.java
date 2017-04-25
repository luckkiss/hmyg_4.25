package com.hldj.hmyg.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.neopixl.pixlui.components.relativelayout.RelativeLayout;

/**
 * Created by Administrator on 2017/4/15.
 */

public class AutoAddRelative extends RelativeLayout {

    private ViewHolder viewHolder_add;
    private ViewHolder_rd viewHolder_rd;
    private ViewHolder_top viewHolder_top;
    Context context;
    boolean isChangeName = true; //是否改变 左边的名字

    public AutoAddRelative(Context context) {
        super(context);
        this.context = context;
    }

    public AutoAddRelative(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoAddRelative(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setDatas(SaveSeedingGsonBean.DataBean.TypeListBean.ParamsListBean paramsListBean) {
        viewHolder_add.tv_auto_add_left1.setText(paramsListBean.getName());


        requiredis = paramsListBean.isRequired();
        if (!requiredis)
            viewHolder_add.tv_auto_add_left2.setText("(选填)");//不是必须的话 写选填
    }

    //是否必填
    boolean requiredis = true;

    public boolean isRequiredis() {
        return requiredis;
    }

    public AutoAddRelative setDatas_rd(SaveSeedingGsonBean.DataBean.TypeListBean.ParamsListBean paramsListBean) {
        viewHolder_rd.tv_auto_add_left1.setText(paramsListBean.getName());
        if (paramsListBean.getValue().equals("dbh")) {
            if (isChangeName)
                viewHolder_rd.initListener();
            isChangeName = false;
        }//如果是胸径 就不会自动改变左边的字


        requiredis = paramsListBean.isRequired();
        if (!requiredis)
            viewHolder_rd.tv_auto_add_left2.setText("(选填)");//不是必须的话 写选填
//        if (!paramsListBean.isRequired())
//            viewHolder_rd.tv_auto_add_left2.setText("(选填)");//不是必须的话 写选填

        return this;
    }

//    int layoutId;

    public AutoAddRelative initView(int layoutId) {
//        this.layoutId = layoutId;
        View view = LayoutInflater.from(context).inflate(layoutId, this);

        switch (layoutId) {
            case R.layout.save_seeding_auto_add:
                viewHolder_add = new ViewHolder(view);
                break;
            case R.layout.save_seeding_auto_add_radio:
                viewHolder_rd = new ViewHolder_rd(view);
                break;
            case R.layout.save_seeding_auto_add_top:
                viewHolder_top = new ViewHolder_top(view);
                break;
        }
        return this;
    }


    public ViewHolder getViewHolder() {

        return viewHolder_add;
    }

    public ViewHolder_rd getViewHolder_rd() {

        return viewHolder_rd;
    }

    public ViewHolder_top getViewHolder_top() {

        return viewHolder_top;
    }


    private String mTag = "";

    public void setMTag(String str) {
        this.mTag = str;
    }

    public String getMTag() {
        return mTag;
    }


    /**
     * 获取 上传大小的种类
     *
     * @return
     */
    public String getDiameterType() {
        if (getMTag().equals("dbh")) {
            if (viewHolder_rd.rb_auto_add_left.isChecked()) {
                return "size30";
            }
            if (viewHolder_rd.rb_auto_add_center.isChecked()) {
                return "size100";
            }
            if (viewHolder_rd.rb_auto_add_right.isChecked()) {
                return "size130";
            }
        } else if (getMTag().equals("diameter")) {
            if (viewHolder_rd.rb_auto_add_left.isChecked()) {
                return "size0";
            }
            if (viewHolder_rd.rb_auto_add_center.isChecked()) {
                return "size10";
            }
            if (viewHolder_rd.rb_auto_add_right.isChecked()) {
                return "size30";
            }
        }
        return "";
    }


    /**
     * 根据size 种类  动态选择中间按钮
     *
     * @param size
     */
    public void setDiameterTypeWithSize(String size) {
        if (getMTag().equals("dbh")) {

            switch (size) {
                case "size30":
                    viewHolder_rd.rb_auto_add_left.setChecked(true);
                    break;
                case "size100":
                    viewHolder_rd.rb_auto_add_center.setChecked(true);
                    break;
                case "size130":
                    viewHolder_rd.rb_auto_add_right.setChecked(true);
                    break;
            }
        } else if (getMTag().equals("diameter")) {
            switch (size) {
                case "size0":
                    viewHolder_rd.rb_auto_add_left.setChecked(true);
                    break;
                case "size10":
                    viewHolder_rd.rb_auto_add_center.setChecked(true);
                    break;
                case "size30":
                    viewHolder_rd.rb_auto_add_right.setChecked(true);
                    break;
            }
        }
    }


    public void setSizeWithTag(String tag) {
        switch (tag) {
            case "dbh":
                viewHolder_rd.rb_auto_add_left.setText("0.3M量");
                viewHolder_rd.rb_auto_add_center.setText("1.0M量");
                viewHolder_rd.rb_auto_add_right.setText("1.3M量");
                break;
            case "diameter":
                viewHolder_rd.rb_auto_add_left.setText("出土量");
                viewHolder_rd.rb_auto_add_center.setText("0.1M量");
                viewHolder_rd.rb_auto_add_right.setText("0.3M量");
                break;
        }
        setMTag(tag);
    }


    public class ViewHolder {
        public View rootView;
        public TextView tv_auto_add_left1;
        public TextView tv_auto_add_left2;
        public EditText et_auto_add_min;
        public EditText et_auto_add_max;
        public LinearLayout linearLayout2;
        public TextView et_auto_add_unit;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_auto_add_left1 = (TextView) rootView.findViewById(R.id.tv_auto_add_left1);
            this.tv_auto_add_left2 = (TextView) rootView.findViewById(R.id.tv_auto_add_left2);
            this.et_auto_add_min = (EditText) rootView.findViewById(R.id.et_auto_add_min);
            this.et_auto_add_max = (EditText) rootView.findViewById(R.id.et_auto_add_max);
            this.linearLayout2 = (LinearLayout) rootView.findViewById(R.id.linearLayout2);
            this.et_auto_add_unit = (TextView) rootView.findViewById(R.id.et_auto_add_unit);
        }

    }

    public class ViewHolder_rd {
        public View rootView;
        public TextView tv_auto_add_left1;
        public TextView tv_auto_add_left2;
        public EditText et_auto_add_min;
        public EditText et_auto_add_max;
        public LinearLayout linearLayout2;
        public TextView et_auto_add_unit;
        public RadioButton rb_auto_add_left;
        public RadioButton rb_auto_add_center;
        public RadioButton rb_auto_add_right;

        public ViewHolder_rd(View rootView) {
            this.rootView = rootView;
            this.tv_auto_add_left1 = (TextView) rootView.findViewById(R.id.tv_auto_add_left1);
            this.tv_auto_add_left2 = (TextView) rootView.findViewById(R.id.tv_auto_add_left2);
            this.et_auto_add_min = (EditText) rootView.findViewById(R.id.et_auto_add_min);
            this.et_auto_add_max = (EditText) rootView.findViewById(R.id.et_auto_add_max);
            this.linearLayout2 = (LinearLayout) rootView.findViewById(R.id.linearLayout2);
            this.et_auto_add_unit = (TextView) rootView.findViewById(R.id.et_auto_add_unit);
            this.rb_auto_add_left = (RadioButton) rootView.findViewById(R.id.rb_auto_add_left);
            this.rb_auto_add_center = (RadioButton) rootView.findViewById(R.id.rb_auto_add_center);
            this.rb_auto_add_right = (RadioButton) rootView.findViewById(R.id.rb_auto_add_right);


        }

        private void initListener() {
            this.rb_auto_add_left.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    viewHolder_rd.tv_auto_add_left1.setText("地径");
                }
            });
            this.rb_auto_add_center.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder_rd.tv_auto_add_left1.setText("米径");
                }
            });
            this.rb_auto_add_right.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder_rd.tv_auto_add_left1.setText("胸径");
                }
            });

        }

    }

    public static class ViewHolder_top {
        public View rootView;
        public TextView tv_auto_add_left1;
        public EditText tv_auto_add_name;

        public ViewHolder_top(View rootView) {
            this.rootView = rootView;
            this.tv_auto_add_left1 = (TextView) rootView.findViewById(R.id.tv_auto_add_left1);
            this.tv_auto_add_name = (EditText) rootView.findViewById(R.id.tv_auto_add_name);
        }

    }
}

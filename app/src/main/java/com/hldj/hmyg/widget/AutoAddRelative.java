package com.hldj.hmyg.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.bean.SpecTypeBean;
import com.hldj.hmyg.util.D;

import java.util.List;

/**
 * 这个界面 我写的坑。很难改。以后要是接盘的小哥看到。别 喷。。。。。。。
 */

public class AutoAddRelative extends RelativeLayout {

    private ViewHolder viewHolder_add;
    private ViewHolder_rd viewHolder_rd;
    private ViewHolder_top viewHolder_top;
    Context context;
    boolean isChangeName = true; //是否改变 左边的名字


    public SpecTypeBean currentSpaceType = new SpecTypeBean();

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
            viewHolder_add.tv_auto_add_left2.setText("");//不是必须的话 写选填

        //如果必填  显示*

        isShowLeft(requiredis, viewHolder_add.tv_auto_add_left1, R.drawable.seller_redstar);


    }

    //是否必填
    boolean requiredis = true;

    public boolean isRequiredis() {
        return requiredis;
    }

    public AutoAddRelative setDatas_rd(SaveSeedingGsonBean.DataBean.TypeListBean.ParamsListBean paramsListBean, List<SpecTypeBean> dbh, List<SpecTypeBean> dim) {
        viewHolder_rd.tv_auto_add_left1.setText(paramsListBean.getName());
        if (paramsListBean.getValue().equals("dbh") ) {
            if (isChangeName)
                viewHolder_rd.initListener();


            for (int i = 0; i < dbh.size(); i++) {
                RadioButton button = (RadioButton) LayoutInflater.from(context).inflate(R.layout.radio, null);
                button.setId(1100 + i);
                button.setText(dbh.get(i).text);
                button.setTag(dbh.get(i).value);
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        RadioButton button1 = ((RadioButton) v);
                        currentSpaceType.text = button1.getText().toString();
                        currentSpaceType.value = button1.getTag().toString();
//                        ToastUtil.showLongToast(currentSpaceType.toString());

                    }
                });
                viewHolder_rd.radio_group_auto_add.addView(button);
            }


            viewHolder_rd.rb_auto_add_center.setChecked(true);
            viewHolder_rd.tv_auto_add_left1.setText("米径");
            D.e("dbh=" + dbh.toString());
//            ToastUtil.showLongToast(dbh.toString());
            isChangeName = false;
        }//如果是胸径 就不会自动改变左边的字
        else {
            D.e("dim=" + dim.toString());
//            ToastUtil.showLongToast(dim.toString());


            for (int i = 0; i < dim.size(); i++) {
                RadioButton button = (RadioButton) LayoutInflater.from(context).inflate(R.layout.radio, null);
                button.setId(1100 + i);
                button.setText(dim.get(i).text);
                button.setTag(dim.get(i).value);
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        RadioButton button1 = ((RadioButton) v);
                        currentSpaceType.text = button1.getText().toString();
                        currentSpaceType.value = button1.getTag().toString();

//                        ToastUtil.showLongToast(currentSpaceType.toString());

                    }
                });

                viewHolder_rd.radio_group_auto_add.addView(button);


            }
        }

        requiredis = paramsListBean.isRequired();
        if (!requiredis)
            viewHolder_rd.tv_auto_add_left2.setText("");//不是必须的话 写选填

        isShowLeft(requiredis, viewHolder_rd.tv_auto_add_left1, R.drawable.seller_redstar);


//        if (!paramsListBean.isRequired())
//            viewHolder_rd.tv_auto_add_left2.setText("(选填)");//不是必须的话 写选填

        return this;
    }


    public AutoAddRelative setDefaultSelect(String value) {

        if (viewHolder_rd != null) {
            viewHolder_rd.rb_auto_add_center.setChecked(true);
        }
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
//        ToastUtil.showLongToast("获取上传的size" + currentSpaceType.text);

        if (currentSpaceType != null) {
            return currentSpaceType.value;
        }


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
        if (TextUtils.isEmpty(size))
        {
            return;
        }

        RadioGroup radioGroup = viewHolder_rd.radio_group_auto_add;
        int count = radioGroup.getChildCount();

        if (count > 3) {
            for (int i = 3; i < count; i++) {
                RadioButton button = (RadioButton) radioGroup.getChildAt(i);
                if (size.equals(button.getTag())) {
                    currentSpaceType.text = button.getText().toString();
                    currentSpaceType.value = button.getTag().toString();
                    button.setChecked(true);
                }
            }
            return;
        }


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
        public RadioGroup radio_group_auto_add;

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
            // 自动添加的  RadioGroup
            this.radio_group_auto_add = (RadioGroup) rootView.findViewById(R.id.radio_group_auto_add);


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


    public static void isShowLeft(boolean flag, TextView textView, int drawableId) {
        if (textView == null) return;
        if (flag) {
            Drawable drawable = MyApplication.getInstance().getResources().getDrawable(drawableId);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
        } else {
            //隐藏Drawables
            textView.setCompoundDrawables(null, null, null, null);
        }
    }


    public static void isShowLeftAndRight(boolean flag, TextView textView, Integer... integers) {
        if (textView == null) return;
        if (flag) {
            Drawable drawable = MyApplication.getInstance().getResources().getDrawable(integers[0]);
            Drawable drawable1 = MyApplication.getInstance().getResources().getDrawable(integers[1]);
            /// 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
            textView.setCompoundDrawables(drawable, null, drawable1, null);
        } else {
            Drawable drawable1 = MyApplication.getInstance().getResources().getDrawable(integers[1]);
            /// 这一步必须要做,否则不会显示.
            drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
            //隐藏Drawables
            textView.setCompoundDrawables(null, null, drawable1, null);
        }
    }
}

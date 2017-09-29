package com.hldj.hmyg.Ui.jimiao;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.CityGsonBean;
import com.hldj.hmyg.buyer.Ui.CityWheelDialogF;
import com.hldj.hmyg.util.D;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

public class SellectMiaoActivity extends NeedSwipeBackActivity {
    private EditText et_min_guige;
    private EditText et_max_guige;
    private EditText et_pinming;
    private EditText et_minCrown;
    private EditText et_maxCrown;
    private EditText et_minHeight;
    private EditText et_maxHeight;
    private String minSpec = "";
    private String maxSpec = "";
    private String minHeight = "";
    private String maxHeight = "";
    private String minCrown = "";
    private String maxCrown = "";
    private String name = "";


    private String cityCode = "";
    private String cityName = "全国";


    private TextView tv_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellect_miao);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        MultipleClickProcess multipleClickProcess = new MultipleClickProcess();
        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
         View iv_reset =   findViewById(R.id.iv_reset);
        et_pinming = (EditText) findViewById(R.id.et_pinming);
        et_min_guige = (EditText) findViewById(R.id.et_min_guige);
        et_max_guige = (EditText) findViewById(R.id.et_max_guige);
        et_minCrown = (EditText) findViewById(R.id.et_minCrown);
        et_maxCrown = (EditText) findViewById(R.id.et_maxCrown);
        et_minHeight = (EditText) findViewById(R.id.et_minHeight);
        et_maxHeight = (EditText) findViewById(R.id.et_maxHeight);

        tv_area = (TextView) findViewById(R.id.tv_area);


        initData();
        View sure =   findViewById(R.id.sure);

        tv_area.setOnClickListener(multipleClickProcess);

        btn_back.setOnClickListener(multipleClickProcess);
        iv_reset.setOnClickListener(multipleClickProcess);
        sure.setOnClickListener(multipleClickProcess);
    }

    public void initData() {
        minSpec = getIntent().getStringExtra("minSpec");
        maxSpec = getIntent().getStringExtra("maxSpec");
        minHeight = getIntent().getStringExtra("minHeight");
        maxHeight = getIntent().getStringExtra("maxHeight");
        minCrown = getIntent().getStringExtra("minCrown");
        maxCrown = getIntent().getStringExtra("maxCrown");
        name = getIntent().getStringExtra("name");

        cityCode = getIntent().getStringExtra("cityCode");
        cityName = getIntent().getStringExtra("cityName");
        tv_area.setText(TextUtils.isEmpty(cityName) ? "全国" : cityName);


        et_max_guige.setText(maxSpec);
        et_min_guige.setText(minSpec);
        et_pinming.setText(name);
        et_minHeight.setText(minHeight);
        et_maxHeight.setText(maxHeight);
        et_minCrown.setText(minCrown);
        et_maxCrown.setText(maxCrown);

    }

    public class MultipleClickProcess implements OnClickListener {
        private boolean flag = true;

        private synchronized void setFlag() {
            flag = false;
        }

        public void onClick(View view) {
            if (flag) {
                switch (view.getId()) {
                    case R.id.btn_back:
                        onBackPressed();
                        break;

                    case R.id.tv_area:
                        D.e("=选择区域=");


                        CityWheelDialogF.instance()
                                .setNoShowCity()
                                .addSelectListener(new CityWheelDialogF.OnCitySelectListener() {
                                    @Override
                                    public void onCitySelect(CityGsonBean.ChildBeans childBeans) {
                                    }

                                    @Override
                                    public void onProvinceSelect(CityGsonBean.ChildBeans childBeans) {
//                                        SellectActivity2.childBeans = childBeans;
                                        D.e("=选择  地区==" + childBeans.toString());
                                        cityName = childBeans.name;
                                        tv_area.setText(cityName);
                                        cityCode = childBeans.cityCode;
                                    }
                                }).show(mActivity.getSupportFragmentManager(), "SellectMiaoActivity");


                        break;
                    case R.id.iv_reset:
                        minSpec = "";
                        maxSpec = "";
                        minHeight = "";
                        maxHeight = "";
                        minCrown = "";
                        maxCrown = "";
                        name = "";
                        et_max_guige.setText(maxSpec);
                        et_min_guige.setText(minSpec);
                        et_pinming.setText(name);
                        et_minHeight.setText(minHeight);
                        et_maxHeight.setText(maxHeight);
                        et_minCrown.setText(minCrown);
                        et_maxCrown.setText(maxCrown);


                        tv_area.setText("全国");
                        cityCode = "";
                        cityName = "全国";
                        break;
                    case R.id.sure:
                        Intent intent = new Intent();
                        intent.putExtra("minSpec", et_min_guige.getText()
                                .toString());
                        intent.putExtra("maxSpec", et_max_guige.getText()
                                .toString());
                        intent.putExtra("minHeight", et_minHeight.getText()
                                .toString());
                        intent.putExtra("maxHeight", et_maxHeight.getText()
                                .toString());
                        intent.putExtra("minCrown", et_minCrown.getText()
                                .toString());
                        intent.putExtra("maxCrown", et_maxCrown.getText()
                                .toString());

                        intent.putExtra("cityCode", cityCode);
                        intent.putExtra("cityName", cityName);

                        intent.putExtra("name", et_pinming.getText().toString());
                        setResult(9, intent);
                        finish();
                        break;

                    default:
                        break;
                }
                setFlag();
                // do some things
                new TimeThread().start();
            }
        }

        /**
         * 计时线程（防止在一定时间段内重复点击按钮）
         */
        private class TimeThread extends Thread {
            public void run() {
                try {
                    Thread.sleep(200);
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //复原  城市名字
    public void resetCity(View view) {
        tv_area.setText("全国");
        cityCode = "";
        cityName = "全国";
    }

}

package com.zzy.common.widget.wheelview.popwin;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.UnitTypeBean;
import com.zzy.common.widget.wheelview.WheelAdapter;
import com.zzy.common.widget.wheelview.WheelView;

import java.util.List;

public class CustomUnitsPickPopwin extends PopupWindow {

    private List<UnitTypeBean> dataSource;
    /**
     * 整体view
     */
    private View popView;
    /**
     * 空白区域
     */
    private View araeView;
    /**
     * 取消按钮
     */
    private Button cancelBtn;
    /**
     * 确定按钮
     */
    private Button confirmBtn;
    /**
     * 自定义按钮
     */
    private Button customBtn;
    /**
     * 字符串
     */
    private final WheelView wv_string;

    private UnitSelectListener onUnitSelect;
    private int pos;
    private int dayType = 0;

    /**
     * 实现点击确认后的时间变化
     */
    public interface UnitSelectListener {
        void onUnitSelect(UnitTypeBean unitTypeBean);
    }

    public CustomUnitsPickPopwin(Context context, UnitSelectListener onUnitSelect, List<UnitTypeBean> dataSource) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popView = inflater.inflate(R.layout.custom_dayspicker_layout, null);
        araeView = popView.findViewById(R.id.pop_arae_layout);
        cancelBtn = (Button) popView.findViewById(R.id.cancel_btn);
        confirmBtn = (Button) popView.findViewById(R.id.confirm_btn);
        customBtn = (Button) popView.findViewById(R.id.custom_btn);
        customBtn.setVisibility(View.GONE);
        wv_string = (WheelView) popView.findViewById(R.id.stringwheel);
        this.onUnitSelect = onUnitSelect;
        this.dataSource = dataSource;
        initListener(context);
        Init(context, this.dataSource);
        this.setContentView(popView);
        // 设置弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        this.setInputMethodMode(INPUT_METHOD_NOT_NEEDED);
        this.setFocusable(true);
        // this.setAnimationStyle(R.style.PopupAnimation);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setOutsideTouchable(true);
    }

    private void Init(Context context, List<UnitTypeBean> dataSource) {
        wv_string.setAdapter(new UnitsWheelAdapter(dataSource));
        wv_string.setCyclic(false);
        wv_string.setCurrentItem(pos);
        wv_string.setLabel("");// 添加文字
    }

    private void initListener(Context context) {
        BtnOnClickListener clickListener = new BtnOnClickListener(context);
        araeView.setOnClickListener(clickListener);
        cancelBtn.setOnClickListener(clickListener);
        confirmBtn.setOnClickListener(clickListener);
        customBtn.setOnClickListener(clickListener);
    }

    private class BtnOnClickListener implements OnClickListener {

        private Context context;

        public BtnOnClickListener(Context context) {
            super();
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cancel_btn:
                case R.id.pop_arae_layout:
                    // TODO 取消
                    dismiss();
                    break;
                case R.id.confirm_btn:
                    // TODO 确定
                    if (onUnitSelect != null) {
                        onUnitSelect.onUnitSelect(dataSource.get(wv_string.getCurrentItem()));
                    }
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 字符串选择
     */
    public class UnitsWheelAdapter implements WheelAdapter {

        private List<UnitTypeBean> datas;

        public UnitsWheelAdapter(List<UnitTypeBean> datas) {
            super();
            this.datas = datas;
        }

        @Override
        public int getItemsCount() {
            return datas.size();
        }

        @Override
        public int getItemId(int index) {
            return index;
        }

        @Override
        public String getItem(int index) {
            return datas.get(index).text;
        }


        @Override
        public int getMaximumLength() {
            int maxLen = 1;
            for (int i = 0; i < datas.size(); i++) {
                maxLen = Math.max(maxLen, getItem(i).length());
            }
            maxLen *= 2;
            return maxLen;
        }
    }


}

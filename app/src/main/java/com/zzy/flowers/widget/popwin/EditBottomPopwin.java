package com.zzy.flowers.widget.popwin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hldj.hmyg.R;
import com.white.utils.AndroidUtil;

@SuppressLint("CutPasteId")
public abstract class EditBottomPopwin extends PopupWindow {

	// private BaseAdapter adapter;
	protected static int DISTANCE_3_DIP = 0;
	/** 显示的列表文本 */
	protected EditText texts;

	protected LayoutInflater inflater;

	protected Context context;
	protected String str;

	protected View mainView;
	private TextView pop_add;
	private TextView pop_reduce;
	private EditText pop_num;
	private final int ADDORREDUCE = 1;

	protected abstract void handleClickListener(EditText et);

	public EditBottomPopwin(Context context, String str) {
		super(context);
		setHeight(WindowManager.LayoutParams.MATCH_PARENT);
		setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		this.context = context;
		this.str = str;
		init();
	}

	protected void init() {
		if (DISTANCE_3_DIP == 0) {
			DISTANCE_3_DIP = AndroidUtil.dip2px(context, 3);
		}
		inflater = LayoutInflater.from(context);
		mainView = inflater.inflate(R.layout.bottom_edit, null);
		TextView tv_less = (TextView) mainView.findViewById(R.id.tv_less);
		tv_less.setText("库存：" + str);
		pop_add = (TextView) mainView.findViewById(R.id.pop_add);
		pop_reduce = (TextView) mainView.findViewById(R.id.pop_reduce);
		pop_num = (EditText) mainView.findViewById(R.id.pop_num);
		TextView tv_add_car = (TextView) mainView.findViewById(R.id.tv_add_car);
		pop_add.setOnClickListener(new myOnclick());
		pop_reduce.setOnClickListener(new myOnclick());
		tv_add_car.setOnClickListener(new ClickListener(pop_num));
		setContentView(mainView);
		initPopwin();
	}

	class myOnclick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.pop_add:
				if (!pop_num.getText().toString().equals(str)) {
					String num_add = Integer.valueOf(pop_num.getText()
							.toString()) + ADDORREDUCE + "";
					pop_num.setText(num_add);
				} else {
					Toast.makeText(context, "不能超过最大产品数量", Toast.LENGTH_SHORT)
							.show();
				}

				break;
			case R.id.pop_reduce:
				if (!pop_num.getText().toString().equals("1")) {
					String num_reduce = Integer.valueOf(pop_num.getText()
							.toString()) - ADDORREDUCE + "";
					pop_num.setText(num_reduce);
				} else {
					Toast.makeText(context, "购买数量不能低于1件", Toast.LENGTH_SHORT)
							.show();
				}
				break;

			default:
				break;
			}
		}

	}

	protected void initPopwin() {
		mainView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				EditBottomPopwin.this.dismiss();
				return true;
			}
		});
		setBackgroundDrawable(new BitmapDrawable());
		setFocusable(true);
	}

	private class ClickListener implements OnClickListener {

		private EditText et;

		public ClickListener(EditText et) {
			this.et = et;
		}

		@Override
		public void onClick(View v) {
			if (et.getText().toString().length() == 0
					|| et.getText().toString().startsWith("0")) {

				return;
			}
			handleClickListener(et);
			dismiss();
		}
	}

}

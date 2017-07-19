
//package com.hldj.hmyg.saler;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.google.gson.Gson;
//import com.hldj.hmyg.R;
//import com.hldj.hmyg.bean.PicSerializableMaplist;
//import com.hldj.hmyg.bean.SaveSeedingGsonBean;
//import com.hldj.hmyg.buy.bean.CollectCar;
//import com.hldj.hmyg.buy.bean.StorageSave;
//import com.hldj.hmyg.buyer.ArithUtil;
//import com.hy.utils.ValueGetInfo;
//import com.white.utils.ImageTools;
//import com.white.utils.StringUtil;
//
//import net.tsz.afinal.FinalBitmap;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//class Myadapter extends BaseAdapter {
//
//	private Handler mHandler;
//	private int resourceId; // 适配器视图资源ID
//	private Context context; // 上下午对象
//	private ArrayList<SaveSeedingGsonBean.PurchaseItemBean_new.SeedlingBean> list; // 数据集合List
//	private LayoutInflater inflater; // 布局填充器
//	public static HashMap<Integer, Boolean> isSelected;
//	private Gson gson;
//	private FinalBitmap fb;
//	private StorageSave fromJson;
//	private LinearLayout ll_reason;
//	private TextView reason;
////	List<SaveSeedingGsonBean.PurchaseItemBean_new.SeedlingBean> data
//	@SuppressLint("UseSparseArrays")
//	public Myadapter(Context context, ArrayList<SaveSeedingGsonBean.PurchaseItemBean_new.SeedlingBean> list,
//			Handler mHandler) {
//		this.list = list;
//		fb = FinalBitmap.create(context);
//		fb.configLoadingImage(R.drawable.no_image_show);
//		gson = new Gson();
//		this.context = context;
//		this.mHandler = mHandler;
//		isSelected = new HashMap<Integer, Boolean>();
//		initDate();
//	}
//
//	// 初始化isSelected的数据
//	private void initDate() {
//		for (int i = 0; i < list.size(); i++) {
//			getIsSelected().put(i, false);
//		}
//	}
//
//	public static HashMap<Integer, Boolean> getIsSelected() {
//		return isSelected;
//	}
//
//	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
//		Myadapter.isSelected = isSelected;
//	}
//
//	@Override
//	public int getCount() {
//		return list.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return list.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		if (convertView == null) {
//			LayoutInflater layoutInflater = LayoutInflater.from(context);
//			convertView = layoutInflater.inflate(
//					R.layout.manager_group_list_item_parent, parent, false);
//		}
//		 return convertView ;
//	}
//
//	// CheckBox选择改变监听器
//	private final class CheckBoxChangedListener implements
//			OnCheckedChangeListener {
//		@Override
//		public void onCheckedChanged(CompoundButton cb, boolean flag) {
//			int position = (Integer) cb.getTag();
//			getIsSelected().put(position, flag);
//			   dfa = list.get(position);
//			collectCar.setChoosed(flag);
//			mHandler.sendMessage(mHandler.obtainMessage(10, getTotalPrice()));
//			// 如果所有的物品全部被选中，则全选按钮也默认被选中
//			mHandler.sendMessage(mHandler.obtainMessage(11, isAllSelected()));
//		}
//	}
//
//
//
//	/**
//	 * 判断是否购物车中所有的商品全部被选中
//	 *
//	 * @return true所有条目全部被选中 false还有条目没有被选中
//	 */
//	private boolean isAllSelected() {
//		boolean flag = true;
//		for (int i = 0; i < list.size(); i++) {
//			if (!getIsSelected().get(i)) {
//				flag = false;
//				break;
//			}
//		}
//		return flag;
//	}
//
//	public void notify(ArrayList<CollectCar> list) {
//		this.list = list;
//		notifyDataSetChanged();
//
//	}
//}
package com.hldj.hmyg.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.util.D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用  spinner
 */
public class CommonListSpinner {
    // PopupWindow对象
    private PopupWindow selectPopupWindow = null;
    // 自定义Adapter
    private SortListAdapter optionsAdapter = null;
    // 下拉框依附组件
    private View parent;
    // 下拉框依附组件宽度，也将作为下拉框的宽度
    private int pwidth;
    // 文本框
    private EditText et;
    // 下拉箭头图片组件
    private ImageView image;
    // 展示所有下拉选项的ListView
    private ListView listView = null;

    // 是否初始化完成标志
    private boolean flag = false;
    Activity activity;

    TextView mTextView;

    Object lock = new Object();

    public SortListAdapter getSortListAdapter() {
        return optionsAdapter;
    }

    private static CommonListSpinner commonListSpinner;

    private CommonListSpinner(Activity activity, View parent) {
        this.activity = activity;
        this.parent = parent;
//      initData();//初始化数据
    }


    public CommonListSpinner initViews() {
        initWedget();//初始化控件
        return commonListSpinner;
    }

    public static CommonListSpinner getInstance(Activity activity, View line) {
//        if (commonListSpinner == null) {
        commonListSpinner = new CommonListSpinner(activity, line);
//        }
        return commonListSpinner;
    }

    public static CommonListSpinner getInstance() {

        return commonListSpinner;
    }

    //	AdapterView.OnItemClickListener onItemClickListener;
    public CommonListSpinner addOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
//		this.onItemClickListener = onItemClickListener ;
        listView.setOnItemClickListener(onItemClickListener);

        return this;
    }

    public List getListMaps() {
        return list_map;
    }

    public CommonListSpinner setSortMaps(List<Map<String, String>> maps) {
        list_map = maps;
        return commonListSpinner;
    }


    public String orderBy = "";

    public CommonListSpinner addData(String str) {

        this.orderBy = str;

        return this;
    }

//    public void initData() {
//        this.list_maps = getSortMaps();
//    }

    List<Map<String, String>> list_map = new ArrayList<>();


    public Map createMap(String key, String value) {
        Map map = new HashMap();
        map.put(key, value);
        return map;
    }


    /**
     * 初始化界面控件
     */
    Context context;


    private void initWedget() {


        // 获取下拉框依附的组件宽度
        int width = parent.getWidth();
        pwidth = width;

        // 初始化PopupWindow
        initPopuWindow();

    }


    /**
     * 初始化PopupWindow
     */
    private void initPopuWindow() {
        // PopupWindow浮动下拉框布局
        View loginwindow = activity.getLayoutInflater().inflate(R.layout.popo_shop_type_list, null);


        loginwindow.findViewById(R.id.fr_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
//        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
//        lp.alpha = 0.4f; //0.0-1.0
//        activity.getWindow().setAttributes(lp);


        listView = (ListView) loginwindow.findViewById(R.id.listview);
        // 设置自定义Adapter
        optionsAdapter = new SortListAdapter();
        listView.setAdapter(optionsAdapter);
//		listView.setOnItemClickListener(onItemClickListener);
        selectPopupWindow = new PopupWindow(loginwindow, pwidth, LayoutParams.MATCH_PARENT, true);

        // 设置动画效果
//		selectPopupWindow.setAnimationStyle(R.style.Dialog);


//		activity.getWindow().addFlags(View.dar);

//				window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//				| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//		window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//				| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);


        selectPopupWindow.setOutsideTouchable(true);

        selectPopupWindow.setOnDismissListener(() -> {

        });

        //设置背景变暗
        loginwindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(activity, R.color.popWinBgColor)));

        // 这一句是为了实现弹出PopupWindow后，当点击屏幕其他部分及Back键时PopupWindow会消失，
        // 没有这一句则效果不能出来，但并不会影响背景
        // 本人能力极其有限，不明白其原因，还望高手、知情者指点一下
//        selectPopupWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    /**
     * 显示PopupWindow窗口
     *
     * @param
     */
    public CommonListSpinner Show() {
        // 将selectPopupWindow作为parent的下拉框显示，并指定selectPopupWindow在Y方向上向上偏移3pix，
        // 这是为了防止下拉框与文本框之间产生缝隙，影响界面美化
        // （是否会产生缝隙，及产生缝隙的大小，可能会根据机型、Android系统版本不同而异吧，不太清楚）
//        selectPopupWindow.showAsDropDown(parent, 0, -3);

        if (Build.VERSION.SDK_INT < 24) {
            selectPopupWindow.showAsDropDown(parent, 0, -3);
        } else if (Build.VERSION.SDK_INT < 26){
            // 适配 android 7.0
            int[] location = new int[2];
//             selectPopupWindow(location);
            getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
//            ToastUtil.showShortToast("y="+y);
            Log.e(getClass().getSimpleName(), "x : " + x + ", y : " + y);
            selectPopupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, y + 5);
        }else {
            showAsDropDown(selectPopupWindow, parent, 0, -3);
        }

        optionsAdapter.notifyDataSetChanged();
        return this;
    }


    /**
     * 显示PopupWindow窗口
     *
     * @param
     */
    public CommonListSpinner ShowWithPos(int pos) {
        getSortListAdapter().setSeclection(pos);
        // 将selectPopupWindow作为parent的下拉框显示，并指定selectPopupWindow在Y方向上向上偏移3pix，
        // 这是为了防止下拉框与文本框之间产生缝隙，影响界面美化
        // （是否会产生缝隙，及产生缝隙的大小，可能会根据机型、Android系统版本不同而异吧，不太清楚）
//        selectPopupWindow.showAsDropDown(parent, 0, -3);
        //showAsDropDown


        if (Build.VERSION.SDK_INT < 24) {
            selectPopupWindow.showAsDropDown(parent, 0, -3);
        } else if (Build.VERSION.SDK_INT < 26) {
            // 适配 android 7.0
            int[] location = new int[2];
//             selectPopupWindow(location);
            getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
//            ToastUtil.showShortToast("y="+y);
            Log.e(getClass().getSimpleName(), "x : " + x + ", y : " + y);
            selectPopupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, y - 1 + getStatusBarHeight());
        } else {
            //8.0 以上
            showAsDropDown(selectPopupWindow, parent, 0, -3);
        }

        optionsAdapter.notifyDataSetChanged();
        return this;
    }

    private void getLocationOnScreen(int[] location) {
        location[0] = 0;
//        location[1] = parent.getHeight() + parent.getPaddingTop() + parent.getTop();
//        location[1] = dip2px(activity, parent.getBottom());
        location[1] = parent.getBottom();
    }

    /**
     * PopupWindow消失
     */
    public void dismiss() {
        D.e("========dismiss=========");

        if (onDismissListener != null) {
            onDismissListener.onDismiss(null);
        }
        selectPopupWindow.dismiss();
    }


    PopupMenu.OnDismissListener onDismissListener;

    public CommonListSpinner setOnDismissListener(PopupMenu.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    class SortListAdapter extends BaseAdapter {

        private int clickTemp = 0;

        // 标识选择的Item
        public void setSeclection(int position) {
            clickTemp = position;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list_map.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
//            View view = LayoutInflater.from(context).inflate(R.layout.list_item_sort_new, null);
            View sort_list_item = activity.getLayoutInflater().inflate(R.layout.list_item_sort, null);
            TextView area_tv_item = (TextView) sort_list_item.findViewById(R.id.tv_item);
            for (Map.Entry<String, String> entry : list_map.get(position).entrySet()) {
                area_tv_item.setText(entry.getValue());
            }
            ImageView is_check = (ImageView) sort_list_item.findViewById(R.id.is_check);
            if (clickTemp == position) {
                area_tv_item.setTextColor(ContextCompat.getColor(activity, R.color.main_color));
                is_check.setVisibility(View.VISIBLE);
            } else {
                is_check.setVisibility(View.INVISIBLE);
            }
            return sort_list_item;
        }

    }

    class ViewHolder {
        TextView tv_item;
        ImageView is_check;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static final class Builder {

        private Map<String, String> maps;

        private CallContentView contentView;


    }


    public static interface CallContentView {
        View getView(int position, View convertView, ViewGroup parent);

        @LayoutRes
        int getLayoutId();
    }


    /**
     * @param pw     popupWindow
     * @param anchor v
     * @param xoff   x轴偏移
     * @param yoff   y轴偏移
     */
    public static void showAsDropDown(final PopupWindow pw, final View anchor, final int xoff, final int yoff) {


        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            pw.setHeight(height);
            pw.showAsDropDown(anchor, xoff, yoff);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff);
        }
    }

}

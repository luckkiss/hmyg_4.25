package com.hldj.hmyg.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import com.hldj.hmyg.R;
import com.hldj.hmyg.util.D;

import java.util.List;

import static android.widget.AbsListView.CHOICE_MODE_NONE;
import static com.hldj.hmyg.widget.CommonListSpinner.showAsDropDown;

/**
 * 通用  spinner
 */
public class CommonListSpinner1<T> {
    // PopupWindow对象
    private PopupWindow selectPopupWindow = null;
    // 自定义Adapter
    private SortListAdapter optionsAdapter = null;
    // 下拉框依附组件
//    private View parent;
    // 下拉框依附组件宽度，也将作为下拉框的宽度
    private int pwidth;

    private Builder mBuilder;

    private String historyCityCodes = "";

    private CommonListSpinner1(Builder builder) {
        mBuilder = builder;

        if (mBuilder.choice_mode_multiple == AbsListView.CHOICE_MODE_MULTIPLE) {
            /*多选时  初始化 grid*/
            initPopuWindowGird(mBuilder);
        } else {
            initPopuWindow(mBuilder);
        }
    }

//    private void initBuilder() {
//        //初始化  builder
//        initPopuWindow(mBuilder);
//    }

    public static CommonListSpinner1 newInstance(Builder builder) {
        return new CommonListSpinner1(builder);
    }


    /**
     * 初始化界面控件
     */
    Context context;


    private void initWedget() {
        // 获取下拉框依附的组件宽度
        int width = mBuilder.mApplyParentView.getWidth();
        pwidth = width;

        // 初始化PopupWindow
//        initPopuWindow();

    }


    /**
     * 初始化PopupWindow
     */
    private void initPopuWindow(Builder builder) {
        // PopupWindow浮动下拉框布局
        View loginwindow = ((Activity) builder.mContext).getLayoutInflater().inflate(R.layout.popo_shop_type_list, null);
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


        ListView listView = (ListView) loginwindow.findViewById(R.id.listview);

        listView.setVisibility(View.VISIBLE);
        loginwindow.findViewById(R.id.grid_parent).setVisibility(View.GONE);

        listView.setChoiceMode(mBuilder.choice_mode_multiple);

        // 设置自定义Adapter
        optionsAdapter = new SortListAdapter<T>();
        listView.setAdapter(optionsAdapter);
//		listView.setOnItemClickListener(onItemClickListener);
        selectPopupWindow = new PopupWindow(loginwindow, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);

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
        loginwindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(mBuilder.mContext, R.color.popWinBgColor)));

        // 这一句是为了实现弹出PopupWindow后，当点击屏幕其他部分及Back键时PopupWindow会消失，
        // 没有这一句则效果不能出来，但并不会影响背景
        // 本人能力极其有限，不明白其原因，还望高手、知情者指点一下
//        selectPopupWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    private void initPopuWindowGird(Builder builder) {
        // PopupWindow浮动下拉框布局
        View loginwindow = ((Activity) builder.mContext).getLayoutInflater().inflate(R.layout.popo_shop_type_list, null);
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


        /*重置  按钮点击事件*/
        Button reset = (Button) loginwindow.findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuilder.onResetClickListener.onResult(optionsAdapter, mBuilder.mDatasList);
            }
        });


        Button ok = (Button) loginwindow.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuilder.onOkClickListener.onResult(optionsAdapter, mBuilder.mDatasList);
            }
        });
        /*确定 按钮 点击事件*/

        GridView gridView = (GridView) loginwindow.findViewById(R.id.grid);
        gridView.setVisibility(View.VISIBLE);
        loginwindow.findViewById(R.id.listview).setVisibility(View.GONE);
        loginwindow.findViewById(R.id.grid_parent).setVisibility(View.VISIBLE);

        gridView.setChoiceMode(mBuilder.choice_mode_multiple);

        // 设置自定义Adapter
        optionsAdapter = new SortListAdapter<T>();
        gridView.setAdapter(optionsAdapter);
//		listView.setOnItemClickListener(onItemClickListener);
        selectPopupWindow = new PopupWindow(loginwindow, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);

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
        loginwindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(mBuilder.mContext, R.color.popWinBgColor)));

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
    public CommonListSpinner1 Show() {
        // 将selectPopupWindow作为parent的下拉框显示，并指定selectPopupWindow在Y方向上向上偏移3pix，
        // 这是为了防止下拉框与文本框之间产生缝隙，影响界面美化
        // （是否会产生缝隙，及产生缝隙的大小，可能会根据机型、Android系统版本不同而异吧，不太清楚）
//        selectPopupWindow.showAsDropDown(parent, 0, -3);

        if (Build.VERSION.SDK_INT < 24) {
            selectPopupWindow.showAsDropDown(mBuilder.mApplyParentView, 0, -3);
        } else if (Build.VERSION.SDK_INT < 26) {
            // 适配 android 7.0
            int[] location = new int[2];
//             selectPopupWindow(location);
            getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
//            ToastUtil.showShortToast("y="+y);
            Log.e(getClass().getSimpleName(), "x : " + x + ", y : " + y);
            selectPopupWindow.showAtLocation(mBuilder.mApplyParentView, Gravity.NO_GRAVITY, 0, y + 5);
        } else {
            showAsDropDown(selectPopupWindow, mBuilder.mApplyParentView, 0, -3);

        }

        optionsAdapter.notifyDataSetChanged();
        return this;

    }

    public CommonListSpinner1 ShowWithHistorys(String cityCodes) {
        this.historyCityCodes = cityCodes;
        // 将selectPopupWindow作为parent的下拉框显示，并指定selectPopupWindow在Y方向上向上偏移3pix，
        // 这是为了防止下拉框与文本框之间产生缝隙，影响界面美化
        // （是否会产生缝隙，及产生缝隙的大小，可能会根据机型、Android系统版本不同而异吧，不太清楚）
//        selectPopupWindow.showAsDropDown(parent, 0, -3);

        if (Build.VERSION.SDK_INT < 24) {
            selectPopupWindow.showAsDropDown(mBuilder.mApplyParentView, 0, -3);
        } else if (Build.VERSION.SDK_INT < 26) {
//            // 适配 android 7.0
//            int[] location = new int[2];
////             selectPopupWindow(location);
//            getLocationOnScreen(location);
//            int x = location[0];
//            int y = location[1];
////            ToastUtil.showShortToast("y="+y);
//            Log.e(getClass().getSimpleName(), "x : " + x + ", y : " + y);
//            selectPopupWindow.showAtLocation(mBuilder.mApplyParentView, Gravity.NO_GRAVITY, 0, y + 5);

            showAsDropDown(selectPopupWindow, mBuilder.mApplyParentView, 0, -3);
        } else {
            showAsDropDown(selectPopupWindow, mBuilder.mApplyParentView, 0, -3);
        }


        /*历史记录回调*/
        mBuilder.contentView.onHistory(historyCityCodes);

        optionsAdapter.notifyDataSetChanged();
        return this;
    }


    /**
     * 显示PopupWindow窗口
     *
     * @param
     */
    public CommonListSpinner1 ShowWithPos(int pos) {
//        getSortListAdapter().setSeclection(pos);
        // 将selectPopupWindow作为parent的下拉框显示，并指定selectPopupWindow在Y方向上向上偏移3pix，
        // 这是为了防止下拉框与文本框之间产生缝隙，影响界面美化
        // （是否会产生缝隙，及产生缝隙的大小，可能会根据机型、Android系统版本不同而异吧，不太清楚）
//        selectPopupWindow.showAsDropDown(parent, 0, -3);
        if (Build.VERSION.SDK_INT < 24) {
            selectPopupWindow.showAsDropDown(mBuilder.mApplyParentView, 0, -3);
        } else if (Build.VERSION.SDK_INT < 26) {
//            // 适配 android 7.0
//            int[] location = new int[2];
////             selectPopupWindow(location);
//            getLocationOnScreen(location);
//            int x = location[0];
//            int y = location[1];
////            ToastUtil.showShortToast("y="+y);
//            Log.e(getClass().getSimpleName(), "x : " + x + ", y : " + y);
//            selectPopupWindow.showAtLocation(mBuilder.mApplyParentView, Gravity.NO_GRAVITY, 0, y + 5);

            showAsDropDown(selectPopupWindow, mBuilder.mApplyParentView, 0, -3);
        } else {
            showAsDropDown(selectPopupWindow, mBuilder.mApplyParentView, 0, -3);
        }

        optionsAdapter.notifyDataSetChanged();
        return this;
    }

    private void getLocationOnScreen(int[] location) {
        location[0] = 0;
//        location[1] = parent.getHeight() + parent.getPaddingTop() + parent.getTop();
//        location[1] = dip2px(activity, parent.getBottom());
        location[1] = mBuilder.mApplyParentView.getBottom();
    }

    /**
     * PopupWindow消失
     */
    public void dismiss() {
        D.e("========dismiss=========");

        if (mBuilder.onDismissListener != null) {
            mBuilder.onDismissListener.onDismiss(null);
        }
        selectPopupWindow.dismiss();

    }


    class SortListAdapter<T> extends BaseAdapter {

        private int clickTemp = 0;

        // 标识选择的Item
        public void setSeclection(int position) {
            clickTemp = position;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mBuilder.mDatasList.size();
        }

        @Override
        public T getItem(int position) {
            // TODO Auto-generated method stub
            return (T) mBuilder.mDatasList.get(position);
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
            ViewHolder mViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mBuilder.mContext).inflate(mBuilder.contentView.getLayoutId(), null);
//                convertView = mBuilder.mContext.getLayoutInflater().inflate(builder.contentView.getLayoutId(), null);
                mViewHolder = new ViewHolder(convertView);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            if (mBuilder.contentView != null) {
                mBuilder.contentView.convert(position, getItem(position), mViewHolder, historyCityCodes);
            } else {
                Log.w("getView", "-------接口没有 设置-------");
            }
            return convertView;
        }
    }

    public static class ViewHolder {
        private SparseArray<View> views;
        private View itemView;

        public <T extends View> T getView(int viewId) {
            View view = views.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                views.put(viewId, view);
            }
            return (T) view;
        }

        public ViewHolder(View rootView) {
            if (views == null) {
                views = new SparseArray<>();
            }
            itemView = rootView;
        }
    }


    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = mBuilder.mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mBuilder.mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static final class Builder<T> {

        /*数据源*/
//        private Map<String, String> maps;

        private List<T> mDatasList;


        private CallContentView contentView;

        private Context mContext;

        private View mApplyParentView;//受依附 的  view  也就是  在这个控件的下面位置

        /*     选择模式    单选 多选    -- - --  */
        private int choice_mode_multiple = CHOICE_MODE_NONE;
//        private int CHOICE_MODE_MULTIPLE


        private OnResultListener onResetClickListener;
        private OnResultListener onOkClickListener;
//        private OnResultListener resultListener;

        public Builder setOnResetClickListener(OnResultListener clickListener) {
            onResetClickListener = clickListener;
            return this;
        }

//        public Builder setOnResultListener(OnResultListener clickListener) {
//            resultListener = clickListener;
//            return this;
//        }

        public Builder setOnOkClickListener(OnResultListener clickListener) {
            onOkClickListener = clickListener;
            return this;
        }

        public Builder setMApplyParentView(View view) {
            mApplyParentView = view;
            return this;
        }


        PopupMenu.OnDismissListener onDismissListener;

        public Builder setOnDismissListener(PopupMenu.OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }


        public Builder setChoice_mode_multiple(int model) {
            choice_mode_multiple = model;
            return this;
        }

        public CommonListSpinner1 build(Context context) {
            mContext = context;
            return CommonListSpinner1.<T>newInstance(this);
        }

        public Builder<T> setDatasList(List<T> datas) {
            mDatasList = datas;
            return this;
        }

//        public Builder setMaps(Map<String, String> maps) {
//            this.maps = maps;
//            return this;
//        }


        public Builder setContentView(CallContentView contentView) {
            this.contentView = contentView;
            return this;
        }


    }

    public static interface CallContentView<T> {
        void convert(int position, T obj, ViewHolder viewHolder, String codeStrings);

        @LayoutRes
        int getLayoutId();

        void onHistory(String historyString);


    }


    public static abstract class OnResultListener<T> implements View.OnClickListener {
        public abstract void onResult(BaseAdapter adapter, List<T> list);

        @Override
        public void onClick(View v) {

        }
    }


}

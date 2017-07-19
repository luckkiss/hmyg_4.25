package com.hldj.hmyg.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hldj.hmyg.R;
import com.hldj.hmyg.util.D;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 */
public abstract class GlobBaseAdapter<T> extends BaseAdapter {
    protected Context context;
    protected LayoutInflater layoutInflater;
    protected List<T> data;
    private int layoutId;
    public FinalBitmap finalBitmap;

    public GlobBaseAdapter(Context context, List<T> data, int layoutId) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        if (data == null) {
            data = new ArrayList<T>();
        }
        this.data = data;
        this.layoutId = layoutId;
        finalBitmap = FinalBitmap.create(context);
        finalBitmap.configLoadingImage(R.drawable.no_image_show);
    }

    @Override
    public int getCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolders myViewHolder = new ViewHolders(context, convertView, layoutId, parent, position);
        setConverView(myViewHolder, data.get(position), position);
        return myViewHolder.getConvertView();
    }

    public abstract void setConverView(ViewHolders myViewHolder, T t, int position);


    public List<T> getDatas() {
        if (this.data == null) {
            this.data = new ArrayList<T>();
        }
        return this.data;
    }

    /**
     * additional data;
     *
     * @param newData
     */
    public void addData(List<T> newData) {

        if (state == REFRESH) {
            if (newData == null) {
                D.e("==刷新，增加了0条==");
                this.data.clear();
            } else {
                D.e("==刷新，增加了" + newData.size() + "条数据");
                this.data.clear();
                this.data.addAll(newData);
            }
            resetState();
        } else {

            if (newData != null) {
                D.e("==加载更多" + newData.size() + "条数据");
                this.data.addAll(newData);
            } else {
                D.e("==加载更多" + 0 + "条数据");
            }
        }


        notifyDataSetChanged();
    }

    /**
     * additional data;
     *
     * @param newData
     */
    public void addData(List<T> newData, boolean isRefresh) {

        if (isRefresh) {
            state = REFRESH;
        } else {
            state = LOAD_MORE;
        }
        if (state == REFRESH) {
            if (newData == null) {
                this.data.clear();
            } else {
                this.data.clear();
                this.data.addAll(newData);
            }
            resetState();
        } else {

            if (newData != null) {
                this.data.addAll(newData);
            }
        }


        notifyDataSetChanged();
    }

    private int state = LOAD_MORE;

    public static final int REFRESH = 20;
    public static final int LOAD_MORE = 21;

    public void setState(int newState) {
        state = newState;
    }

    public void resetState() {
        state = LOAD_MORE;
    }

    public void refreshState() {
        state = REFRESH;
    }


//    public void updateView(int itemIndex) {
//        //得到第一个可显示控件的位置，
//        int visiblePosition = mListView.getFirstVisiblePosition();
//        //只有当要更新的view在可见的位置时才更新，不可见时，跳过不更新
//        if (itemIndex - visiblePosition >= 0) {
//            //得到要更新的item的view
//            View view = mListView.getChildAt(itemIndex - visiblePosition);
//            //从view中取得holder
//            ViewHolder holder = (ViewHolder) view.getTag();
//            HashMap<String, Object> item = data.get(itemIndex);
//
//            holder.listItem = (RelativeLayout) view.findViewById(R.id.rl_item);
//            holder.ibPlay = (ImageButton) view.findViewById(R.id.ib_play);
//            holder.ibDelete = (ImageButton) view.findViewById(R.id.ib_delete);
//            holder.tvName = (TextView) view.findViewById(R.id.tv_record_sound_name);
//            holder.tvLastModifyTime = (TextView) view
//                    .findViewById(R.id.tv_record_time);
//            holder.tvCurrentPlayTime = (TextView) view
//                    .findViewById(R.id.tv_current_play_time);
//            holder.tvTotalTime = (TextView) view.findViewById(R.id.tv_total_time);
//            holder.sb = (MySeekBar) view.findViewById(R.id.pb_play);
//            holder.layout = (LinearLayout) view
//                    .findViewById(R.id.play_progress_info);
//
//            updateData(itemIndex, holder, item);
//        }
//    }


}
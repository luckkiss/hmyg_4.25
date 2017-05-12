package com.hldj.hmyg.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hldj.hmyg.R;

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


    /**
     * additional data;
     *
     * @param newData
     */
    public void addData(List<T> newData) {

        if (newData == null) {
            this.data.clear();
        } else {
            this.data.clear();
            this.data.addAll(newData);
        }
        notifyDataSetChanged();
    }


}
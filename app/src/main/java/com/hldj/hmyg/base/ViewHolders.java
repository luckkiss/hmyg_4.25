package com.hldj.hmyg.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 *
 */
public class ViewHolders {
    private SparseArray<View> sparseArray;
    private View convertView;
    private int position;

    public ViewHolders(Context context, View convertView, int layoutId,
                       ViewGroup parent, int position) {
        this.position = position;
        this.sparseArray = new SparseArray<View>();
        this.convertView = LayoutInflater.from(context).inflate(layoutId,
                parent, false);
        this.convertView.setTag(this);

    }

    public static ViewHolders getCommonViewHolder(Context context,
                                                  View convertView, int layoutId, ViewGroup parent, int position) {
        if (convertView == null) {
            return new ViewHolders(context, convertView, layoutId, parent,
                    position);
        } else {
            ViewHolders commonViewHolder = (ViewHolders) convertView.getTag();
            commonViewHolder.position = position;
            return commonViewHolder;
        }
    }

    public <T extends View> T getView(int viewId) {
        View view = sparseArray.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            sparseArray.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

    public void setTag(Object object) {
        convertView.setTag(object);
    }

}
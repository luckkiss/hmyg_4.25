package com.zzy.flowers.widget.popwin;

import android.content.Context;

import com.hldj.hmyg.StoreActivity;

public class EditP5 extends ChooseTypeBottomPopwin {

    private StoreActivity activity;
    OnPlantTypeSelect onPlantTypeSelect;

    public EditP5(Context context, String str, StoreActivity activity) {
        super(context, str);
        // TODO Auto-generated constructor stub
        this.activity = activity;
    }

    public EditP5(Context context, String str, OnPlantTypeSelect onPlantTypeSelect) {
        super(context, str);
        this.onPlantTypeSelect = onPlantTypeSelect;

    }

    @Override
    protected void handleClickListener(String str) {

        if (activity != null) {
            // TODO Auto-generated method stub
            activity.plantTypes = str;
            activity.Refresh();
        }

          if (onPlantTypeSelect != null) {
            onPlantTypeSelect.onSelect(str);
        }


    }


    public interface OnPlantTypeSelect {
        void onSelect(String plantType);
    }

}

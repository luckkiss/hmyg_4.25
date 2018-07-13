package com.hldj.hmyg.CallBack.impl;

import android.widget.Checkable;

import com.hldj.hmyg.CallBack.IEditable;
import com.hldj.hmyg.CallBack.IFootMarkDelete;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.mabeijianxi.smallvideorecord2.Log;

import java.util.List;

/**
 * 写个简介代理?
 */

public class DeleteAbleImpl implements IEditable {


    private boolean isSelectAll = false;
    private boolean isEditable = false;

    private CoreRecyclerView coreRecyclerView;

    private DeleteAbleImpl() {

    }

    public DeleteAbleImpl(CoreRecyclerView coreRecyclerView) {

        this.coreRecyclerView = coreRecyclerView;
    }


    public String getDeleteIds() {

        StringBuilder stringBuilder = new StringBuilder();
        List<Checkable> checkables = coreRecyclerView.getAdapter().getData();


        for (Checkable checkable : checkables) {

            if (checkable.isChecked() && checkable instanceof IFootMarkDelete ) {
                stringBuilder.append(((IFootMarkDelete) checkable).getFootMarkId() + ",");
            }
        }

        Log.i("getDeleteIds", "" + stringBuilder.toString());
        return stringBuilder.toString();
    }


    @Override
    public IEditable toggleSelectAll() {
        this.isSelectAll = !isSelectAll;
        List<Checkable> checkables = coreRecyclerView.getAdapter().getData();
        for (Checkable checkable : checkables) {
            checkable.setChecked(isSelectAll);
        }
        coreRecyclerView.getAdapter().notifyDataSetChanged();
        return this;
    }


    public boolean isSelectAll() {
        return isSelectAll;
    }

    public boolean isEditable() {
        return isEditable;
    }


    @Override
    public IEditable toggleEditable() {
        this.isEditable = !isEditable;
        return this;
    }


}

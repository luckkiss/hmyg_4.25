package com.hldj.hmyg.widget;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.IGroup;
import com.hldj.hmyg.R;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.buyer.weidet.decoration.SectionDecoration;

/**
 * 分组
 */

public class CommonSectionDecoration {

// extends SectionDecoration


    public static SectionDecoration simpleDecoration(CoreRecyclerView coreRecyclerView, Activity mActivity) {

        return createDecoration(coreRecyclerView, mActivity);
    }

    private static SectionDecoration createDecoration(CoreRecyclerView coreRecyclerView, Activity mActivity) {

        coreRecyclerView.getRecyclerView().addItemDecoration(
                SectionDecoration.Builder.init(new SectionDecoration.PowerGroupListener() {
                    @Override
                    public String getGroupName(int position) {

                        if (coreRecyclerView.getAdapter().getData().size() == 0) {
                            return null;
                        } else {
//                            dateStr

                            if (coreRecyclerView.getAdapter().getItem(position) instanceof IGroup) {
                                IGroup iGroup = ((IGroup) coreRecyclerView.getAdapter().getItem(position));
                                return iGroup.getGroupName();
                            }
                            return null;

                        }

                    }

                    @Override
                    public View getGroupView(int position) {
//                        View view = LayoutInflater.from(HistoryActivity.this).inflate( R.layout.item_list_simple, null);
//                        view.setBackgroundColor(getColorByRes(R.color.gray_bg_ed));
//                        TextView tv = view.findViewById(android.R.id.text1);
//                        tv.setText(recycler.getAdapter().getItem(position) + "");
                        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_tag, null);
                        TextView textView = view.findViewById(R.id.text1);
                        textView.setHeight((int) mActivity.getResources().getDimension(R.dimen.px64));
                        if (coreRecyclerView.getAdapter().getItem(position) instanceof IGroup) {
                            IGroup iGroup = ((IGroup) coreRecyclerView.getAdapter().getItem(position));
                            textView.setText(iGroup.getGroupName());
                        }
                        return view;
                    }
                }).setGroupHeight((int) mActivity.getResources().getDimension(R.dimen.px64)).build());


        return null;
    }


}

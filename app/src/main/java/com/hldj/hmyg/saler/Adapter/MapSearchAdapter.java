package com.hldj.hmyg.saler.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.buyer.Ui.StorePurchaseListActivity;
import com.hldj.hmyg.saler.bean.Subscribe;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;

/**
 * 字母分组适配器
 */

public class MapSearchAdapter extends BaseAdapter implements SectionIndexer {
    private static final String TAG = "MapSearchAdapter";

    private ArrayList<Subscribe> data = null;

    private Context context = null;
    private FinalBitmap fb;

    public MapSearchAdapter(Context context, ArrayList<Subscribe> data) {
        this.data = data;
        this.context = context;
        fb = FinalBitmap.create(context);
        fb.configLoadingImage(R.drawable.no_image_show);
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int arg0) {
        return this.data.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        RelativeLayout xiao_rl_popo_list_item = (RelativeLayout) inflate.findViewById(R.id.rl_popo_list_item);
        TextView xiao_quyu_tv_item = (TextView) inflate.findViewById(R.id.tv_item);
        TextView tvLetter = (TextView) inflate.findViewById(R.id.catalog);
        xiao_quyu_tv_item.setText("[" + data.get(position).getParentName()
                + "]" + data.get(position).getName());
        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            tvLetter.setVisibility(View.VISIBLE);
            tvLetter.setText(data.get(position).getSortLetters());
            tvLetter.setBackgroundColor(context.getResources().getColor(R.color.gray_line));
        } else {
            tvLetter.setVisibility(View.GONE);
        }

        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StorePurchaseListActivity.class);
                intent.putExtra("secondSeedlingTypeId", data.get(position)
                        .getId());
                intent.putExtra("title", "[" + data.get(position).getParentName() + "]" + data.get(position).getName());
                context.startActivity(intent);
                // subscribeSave(data.get(position).getId(), position);
            }

        });

        return inflate;
    }

    public void notify(ArrayList<Subscribe> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Object[] getSections() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = data.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        // TODO Auto-generated method stub
        return data.get(position).getSortLetters().charAt(0);
    }

}

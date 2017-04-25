package com.hldj.hmyg.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.util.D;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/19.
 */

public class AutoAdd2DetailLinearLayout extends BaseLinearLayout {

    private ViewHolder viewHolder_derail;

    public AutoAdd2DetailLinearLayout(Context context) {
        super(context);
    }

    public AutoAdd2DetailLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoAdd2DetailLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AutoAdd2DetailLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected int setContextView() {
        return R.layout.activity_flower_detail_test_content_self_view;
}

    @Override
    public BaseLinearLayout initViewHolder(View viewRoot) {

        viewHolder_derail = new ViewHolder(viewRoot);

        return this;
    }

    @Override
    public <T> BaseLinearLayout setDatas(T t) {

        return this;
    }


    public BaseLinearLayout setDatas(UploadDatas t) {

        viewHolder_derail.remarks.setText(t.remarks);
        viewHolder_derail.firstTypeName.setText(t.firstTypeName);
        viewHolder_derail.seedlingNum.setText(t.seedlingNum);

        try {
            D.e("");
            for (int i = 0; i < t.jsonArray.length(); i++) {
                JSONObject object = t.jsonArray.getJSONObject(i);
                // TODO: 2017/4/19  这里有多少个值就动态产生多少个列表

                TableRow tableRow = new TableRow(context);
                View view = inflate(context, R.layout.auto_add_tablerow_view, tableRow);
                ((TextView) view.findViewById(R.id.table_row_left)).setText(object.get("name").toString());
                ((TextView) view.findViewById(R.id.table_row_right)).setText(object.get("value").toString());

                int index = ((ViewGroup) this.getChildAt(0)).getChildCount();//取子布局的  控件数量

                ((ViewGroup) this.getChildAt(0)).addView(tableRow, index - 1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            D.e("");
        }

        /**
         *  <TableRow
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         android:minHeight="25dp">

         <TextView
         style="@style/table_row_left"
         android:layout_weight="2"
         android:text="动态添加" />

         <TextView
         style="@style/table_row_right"
         android:layout_weight="6"
         android:text="中间动态添加" />
         </TableRow>
         */


        /**
         *  for (int i = 0; i<array.length(); i++){
         JSONObject object = array.getJSONObject(i);
         String id = object.getString("id");
         String name = object.getString("name");
         String version = object.getString("version");
         Log.d("testtest","*********id is :  " + id +"\n");
         Log.d("testtest","*********name is :   " + name +"\n");
         Log.d("testtest","*********version is :   " + version +"\n");
         }
         */

        D.e("============hellowworld================" + t);
        D.e("================hellowworld============" + t);
        D.e("==============hellowworld==============" + t);

        return this;
    }

    public static class UploadDatas {
        /*备注*/
        public String remarks = "";
        /*商品编号*/
        public String seedlingNum = "";
        /*分类*/
        public String firstTypeName = "";

        /*json 数组*/
        public JSONArray jsonArray = null;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView seedlingNum;
        public TextView firstTypeName;
        public TextView remarks;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.seedlingNum = (TextView) rootView.findViewById(R.id.seedlingNum);
            this.firstTypeName = (TextView) rootView.findViewById(R.id.firstTypeName);
            this.remarks = (TextView) rootView.findViewById(R.id.remarks);
        }

    }
}

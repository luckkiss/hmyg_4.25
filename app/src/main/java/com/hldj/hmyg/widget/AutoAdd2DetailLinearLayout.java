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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public class AutoAdd2DetailLinearLayout extends BaseLinearLayout {

//    private ViewHolder viewHolder_derail;

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

//        viewHolder_derail = new ViewHolder(viewRoot);

        return this;
    }

    @Override
    public BaseLinearLayout setDatas(Object o) {
        return null;
    }


    List<View> listViews = new ArrayList<>();


//    public AutoAdd2DetailLinearLayout changeLeftText(String string) {
//        viewHolder_derail.top_left.setText(string);
//        return this;
//    }

//    public AutoAdd2DetailLinearLayout changeText(String string) {
//        viewHolder_derail.seedlingNum.setText(string);
//        return this;
//    }

//    public BaseLinearLayout hidenView(@IdRes int viewId ) {
//        viewHolder_derail.top_left.setText();
//
//        return this;
//    }

    public BaseLinearLayout setDatas(UploadDatas t) {

//        viewHolder_derail.remarks.setText(t.remarks);

//        if (TextUtils.isEmpty(t.firstTypeName)) {
//            ((ViewGroup) viewHolder_derail.firstTypeName.getParent()).setVisibility(GONE);
//            changeText("资源编号");
//        }

//        viewHolder_derail.firstTypeName.setText(t.firstTypeName);
//        viewHolder_derail.seedlingNum.setText(t.seedlingNum);

        try {

//            if (listViews.size() != 0) {
//                ((ViewGroup) this.getChildAt(0)).removeViews(3, listViews.size());
//                listViews.clear();
//            }

            D.e("");
            for (int i = 0; i < t.jsonArray.length(); i++) {
                JSONObject object = t.jsonArray.getJSONObject(i);
                // TODO: 2017/4/19  这里有多少个值就动态产生多少个列表

                TableRow tableRow = new TableRow(context);

                View view = inflate(context, R.layout.auto_add_tablerow_view, tableRow);
                ((TextView) view.findViewById(R.id.table_row_left)).setText(object.get("name").toString());
                ((TextView) view.findViewById(R.id.table_row_right)).setText(object.get("value").toString());

                listViews.add(tableRow);

//                int index = ((ViewGroup) this.getChildAt(0)).getChildCount();//取子布局的  控件数量
                ((ViewGroup) this.getChildAt(0)).addView(tableRow);
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


//    public static class ViewHolder {
//        public View mRootView;
//        public TextView seedlingNum;
//        public TextView firstTypeName;
//        public TextView top_left;
//        public TextView remarks;
//
//        public ViewHolder(View mRootView) {
//            this.mRootView = mRootView;
//            this.top_left = (TextView) mRootView.findViewById(R.id.top_left);
//            this.seedlingNum = (TextView) mRootView.findViewById(R.id.seedlingNum);
//            this.firstTypeName = (TextView) mRootView.findViewById(R.id.firstTypeName);
//            this.remarks = (TextView) mRootView.findViewById(R.id.remarks);
//        }

//    }
}

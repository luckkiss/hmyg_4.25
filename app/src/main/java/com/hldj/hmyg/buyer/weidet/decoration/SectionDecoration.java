package com.hldj.hmyg.buyer.weidet.decoration;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

/**
 * https://blog.csdn.net/bydbbb/article/details/78709732
 * 分割线实现 悬停头部  分割线 类
 */


public class SectionDecoration extends RecyclerView.ItemDecoration {
    private PowerGroupListener mGroupListener;
    /**
     * 悬浮栏高度
     */
    private int mGroupHeight = 80;

    private SectionDecoration(PowerGroupListener listener) {
        this.mGroupListener = listener;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();


        // 得出距离左边 和 右边的 padding
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();


        //开始绘制
        String preGroupName;
        String currentGroupName = null;

        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            preGroupName = currentGroupName;
            currentGroupName = getGroupName(position);
            if (currentGroupName == null || TextUtils.equals(currentGroupName, preGroupName)) {
                continue;
            }

            int viewBottom = view.getBottom();
            // top 决定当前顶部第一个悬浮  Group 的位置
            int top = Math.max(mGroupHeight, view.getTop());
            if (position + 1 < itemCount) {
                // 获取下个groupName
                String nextGroupName = getGroupName(position + 1);
                //
                if (!currentGroupName.equals(nextGroupName) && viewBottom < top) {
                    top = viewBottom;
                }
            }


            // 根据 position 获取 view

            View groupView = getGroupView(position);

            if (groupView == null) {
                return;
            }

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mGroupHeight);

            groupView.setLayoutParams(layoutParams);
            /* smgui */
            groupView.setDrawingCacheEnabled(true);
            groupView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
           /* smgui */
            groupView.layout(0, 0, right, mGroupHeight);
//            groupView.buildDrawingCache();
            Bitmap bitmap = groupView.getDrawingCache();
            int marginLeft = 0;

            c.drawBitmap(bitmap, left + marginLeft, top - mGroupHeight, null);


//            TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
//            paint.setTextSize(60);

//            if (groupView instanceof TextView) {
//                TextView textView = ((TextView) groupView);
//                textView.setText("hello world");
//                Log.i("onDrawOver", "textview in : " + groupView);
//                textView.setBackgroundColor(Color.RED);
//            }

//            Log.i("onDrawOver", "onDrawOver: " + groupView);

//            c.drawText(mGroupListener.getGroupName(position), 0, mGroupHeight, paint);


        }


    }

    private View getGroupView(int position) {
        if (mGroupListener != null) {
            return mGroupListener.getGroupView(position);
        } else {
            return null;
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        /* 得到 你添加的分割线的   view 的位置  (原来 分割线也是一个 view  )  */
        int pos = parent.getChildAdapterPosition(view);//----》 根据view  获取 rv 中的   位置  position

        /* 获取组名 */

        String groupName = getGroupName(pos);


        if (groupName == null) {
            return;
        }


        // 只有 是同一组的第一个才显示炫富 栏目

        if (pos == 0 || isFirstInGroup(pos)) {
            outRect.top = mGroupHeight;
//            outRect.top = mGroupListener.getGroupView(pos).getMeasuredHeight();

        }


    }

    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            String prevGroupId = getGroupName(pos - 1);
            String groupId = getGroupName(pos);
            return !TextUtils.equals(prevGroupId, groupId);
        }
    }


    /**
     * 获取组名
     * 通过接口  回调 组名
     *
     * @param pos 位置
     * @return 组名
     */
    private String getGroupName(int pos) {
        if (mGroupListener != null) {
            return mGroupListener.getGroupName(pos);
        } else {
            return null;
        }
    }


    /* 建造模式  来一个 */
    public static class Builder {
        SectionDecoration mDecoration;

        private Builder(PowerGroupListener listener) {
            mDecoration = new SectionDecoration(listener);
        }


        public static Builder init(PowerGroupListener listener) {
            return new Builder(listener);
        }


        public Builder setGroupHeight(int groupHeight) {
            mDecoration.mGroupHeight = groupHeight;
            return this;
        }

        /**
         * 是否靠左边
         * true 靠左边（默认）、false 靠右边
         *
         * @return this
         */
//        public Builder isAlignLeft(boolean b){
//            mDecoration.isAlignLeft = b;
//            return this;
//        }
        public SectionDecoration build() {
            return mDecoration;
        }

    }


    /* 强有力的 监听 →_→ */
    public static interface PowerGroupListener {

        /* 获取每一组的   组名 */
        public String getGroupName(int position);

        /* 头部 view  */
        public View getGroupView(int position);

    }

}

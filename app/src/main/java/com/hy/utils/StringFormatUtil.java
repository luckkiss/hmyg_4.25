package com.hy.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import com.hldj.hmyg.application.MyApplication;

public class StringFormatUtil {
    private SpannableStringBuilder spBuilder;
    private String wholeStr, highlightStr;
    private String highlightStr1;
    private Context mContext;
    private int color;
    private int start = 0, end = 0;
    private int start1 = 0, end1 = 0;


    /**
     * @param context
     * @param wholeStr     全部文字
     * @param highlightStr 改变颜色的文字
     * @param color        颜色
     */
    public StringFormatUtil(Context context, String wholeStr, String highlightStr, int color) {
        this.mContext = context;
        this.wholeStr = wholeStr;
        this.highlightStr = highlightStr;
        this.color = color;
    }

    /**
     * @param context
     * @param wholeStr     全部文字
     * @param highlightStr 改变颜色的文字
     * @param color        颜色
     */
    public StringFormatUtil(Context context, String wholeStr, String highlightStr, String highlightStr1, int color) {
        this.mContext = context;
        this.wholeStr = wholeStr;
        this.highlightStr = highlightStr;
        this.color = color;
        this.highlightStr1 = highlightStr1;

    }


    public StringFormatUtil fillColor() {
        if (!TextUtils.isEmpty(wholeStr) && !TextUtils.isEmpty(highlightStr)) {
            if (wholeStr.contains(highlightStr)) {
                /*
                 *  返回highlightStr字符串wholeStr字符串中第一次出现处的索引。
                 */
                start = wholeStr.indexOf(highlightStr);
                end = start + highlightStr.length();
            } else {
                return null;
            }
            if (!TextUtils.isEmpty(highlightStr1)) {
                if (wholeStr.contains(highlightStr1)) {
                /*
                 *  返回highlightStr字符串wholeStr字符串中第一次出现处的索引。
                 */
                    start1 = wholeStr.indexOf(highlightStr1);
                    end1 = start1 + highlightStr1.length();
                } else {
                    return null;
                }
            }

        } else {
            return null;
        }
        spBuilder = new SpannableStringBuilder(wholeStr);
        color = mContext.getResources().getColor(color);
//        CharacterStyle charaStyle = new ForegroundColorSpan(color);
        ForegroundColorSpan charaStyle = new ForegroundColorSpan(color);
        ForegroundColorSpan whiteSpan = new ForegroundColorSpan(color);
        spBuilder.setSpan(charaStyle, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (!TextUtils.isEmpty(highlightStr1)) {
            spBuilder.setSpan(whiteSpan, start1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return this;
    }


    public SpannableStringBuilder getResult() {
        if (spBuilder != null) {
            return spBuilder;
        }
        return null;
    }

    public static SpannableStringBuilder setTextColor(String string, Object highlightStr, int colorId) {
        String str = highlightStr.toString();
        int len = str.length();
        int co = ContextCompat.getColor(MyApplication.getInstance(), colorId);
        SpannableStringBuilder builder = new SpannableStringBuilder(str, 0, len);
        CharacterStyle charaStyle = new ForegroundColorSpan(co);
        builder.setSpan(charaStyle, 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //Spannable.SPAN_EXCLUSIVE_EXCLUSIVE：前后都不包括，即在指定范围的前面和后面插入新字符都不会应用新样式
        return builder;
    }
}
package me.kaede.tagview;

import android.text.TextUtils;

import com.hldj.hmyg.R;
import com.hldj.hmyg.application.MyApplication;

import static com.hldj.hmyg.util.ConstantParams.container;
import static com.hldj.hmyg.util.ConstantParams.count;
import static com.hldj.hmyg.util.ConstantParams.dbh;
import static com.hldj.hmyg.util.ConstantParams.diameter;
import static com.hldj.hmyg.util.ConstantParams.heelin;
import static com.hldj.hmyg.util.ConstantParams.height;
import static com.hldj.hmyg.util.ConstantParams.length;
import static com.hldj.hmyg.util.ConstantParams.mijing;
import static com.hldj.hmyg.util.ConstantParams.offbarHeight;
import static com.hldj.hmyg.util.ConstantParams.planted;
import static com.hldj.hmyg.util.ConstantParams.transplant;

/**
 * Created by Administrator on 2017/5/27.
 */

public class TagFactory {


    public static Tag createDelTag(String item) {
        return getTagViewByName(item);
    }


    public static Tag createDelTag(String item, String item2) {


        if (!TextUtils.isEmpty(item) && !TextUtils.isEmpty(item2)) {
            return getTagViewByName(item + "-" + item2 + " cm");
        } else if (!TextUtils.isEmpty(item)) {
            return getTagViewByName(item + " cm");
        } else if (!TextUtils.isEmpty(item2)) {
            return getTagViewByName(item2 + " cm");
        }
        return null;

    }

    public static Tag createDelTag(String item, String item2, String searchSpec) {

        boolean isNull = TextUtils.isEmpty(searchSpec);

        if (!TextUtils.isEmpty(item) && !TextUtils.isEmpty(item2)) {
            if (!isNull)
                return getTagViewByName(getTypeName(searchSpec, true) + item + "-" + item2 + " cm");
            return getTagViewByName(item + "-" + item2 + " cm");
        } else if (!TextUtils.isEmpty(item)) {
            if (!isNull) return getTagViewByName(getTypeName(searchSpec, true) + item + " cm");
            return getTagViewByName(item + " cm");
        } else if (!TextUtils.isEmpty(item2)) {
            if (!isNull) return getTagViewByName(getTypeName(searchSpec, true) + item2 + " cm");
            return getTagViewByName(item2 + " cm");
        } else if (TextUtils.isEmpty(item) && TextUtils.isEmpty(item2)) {
            return null;
        } else {
            if (!isNull) return getTagViewByName(getTypeName(searchSpec, true) + item);
            return getTagViewByName(searchSpec + item);
        }

    }


    private static Tag getTagViewByName(String item) {
        if (!"".equals(getTypeName(item))) {
            Tag tag = new Tag(getTypeName(item));
            tag.layoutColor = R.color.main_color;
            tag.layoutColor = MyApplication.getInstance().getResources().getColor(R.color.main_color);
            tag.isDeletable = true;
            return tag;
        } else {
            return null;
        }
    }


    private static String getTypeName(String item, boolean isSeach) {
//        planted container transplant heelin
        switch (item) {
            case planted:
                return "地栽苗";
            case container:
                return "容器苗";
            case heelin:
                return "假植苗";
            case transplant:
                return "移植苗";
            case mijing:
                return "米径";
            case diameter:
            case dbh:
                return "杆径";
            case height:
                return "高度";
            case offbarHeight:
                return "脱杆高";
            case length:
                return "长度";
            case count:
                return "数量";
            default:
                return item;
        }
    }


    private static String getTypeName(String item) {
//        planted container transplant heelin
        switch (item) {
            case planted:
                return "地栽苗";
            case container:
                return "容器苗";
            case heelin:
                return "假植苗";
            case transplant:
                return "移植苗";
            case mijing:
                return "米径";
            case diameter:
                return "地径";
            case dbh:
                return "胸径";
            case height:
                return "高度";
            case offbarHeight:
                return "脱杆高";
            case length:
                return "长度";
            case count:
                return "数量";
            default:
                return item;
        }
    }


}

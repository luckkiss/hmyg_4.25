package com.hldj.hmyg.Ui.friend.util;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.bean.enums.MomentsType;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.FUtil;
import com.hldj.hmyg.widget.CommonListSpinner;
import com.hldj.hmyg.widget.CommonListSpinner1;
import com.mrwujay.cascade.model.ProvinceModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 朋友圈的公共类 ，在这里写
 */

public class FriendUtil {

    int pos = 0;

    /**
     * 排序 显示位置不对. 小米上正确
     */
    public static CommonListSpinner CreateSortSpinner(Activity activity, OnSortSelectListener selectListener, View p) {

        return CommonListSpinner.getInstance(activity, p) // 单利。有点low
                .setSortMaps(getSortMaps()) // 赋值
                .initViews() // 初始化
                .addOnItemClickListener((parent, view1, position, id) -> {
                    D.e("addOnItemClickListener" + position);
                    for (Map.Entry<String, String> entry : getSortMaps().get(position).entrySet()) {
                        if (selectListener != null) {
                            selectListener.onSelect(position, entry.getKey(), entry.getValue());
                        }
                    }
                });

    }

    static String historyString = "";
    static boolean isFirst = true;

    /**
     * 地区选择  多选列表
     */
    public static CommonListSpinner1 CreateSortCitySpinner(Activity activity, OnSortSelectListener selectListener, View p) {


        CommonListSpinner1.Builder builder = new CommonListSpinner1.Builder<ProvinceModel>()
                .setDatasList(createProvincesMap())
                .setChoice_mode_multiple(ListView.CHOICE_MODE_MULTIPLE)
                .setMApplyParentView(p)
                .setOnOkClickListener(new CommonListSpinner1.OnResultListener<ProvinceModel>() {
                    @Override
                    public void onResult(BaseAdapter adapter, List<ProvinceModel> list) {
//                        ToastUtil.showLongToast(Arrays.toString(list.toArray()));
//                        ToastUtil.showLongToast(Arrays.toString(list.toArray()));
//                        getCodeString(list);
//                        getNameString(list);

                        if (selectListener != null) {
                            selectListener.onSelect(-1, getCodeString(list), getNameString(list));
                        }

                        isFirst = false;

//                        for (ProvinceModel provinceModel : list) {
//                            provinceModel.setChecked(true);
//                            adapter.notifyDataSetChanged();
//                        }
                    }
                })
//                .setOnOkClickListener(v -> {
//                    ToastUtil.showLongToast("ok");
//                })
                .setOnResetClickListener(new CommonListSpinner1.OnResultListener<ProvinceModel>() {
                    @Override
                    public void onResult(BaseAdapter adapter, List<ProvinceModel> list) {
//                        ToastUtil.showLongToast(Arrays.toString(list.toArray()));
                        historyString = "";
                        for (ProvinceModel provinceModel : list) {
                            provinceModel.setChecked(false);
                            adapter.notifyDataSetChanged();
                        }
                    }
                })
                /* listview  的  view 的 绑定  绘制  getview（） */
                .setContentView(new CommonListSpinner1.CallContentView<ProvinceModel>() {


                    @Override
                    public void onHistory(String ss) {
                        /*恢复 省份记录的时候  执行   再  getView 方法之前*/
                        historyString = ss;
                    }

                    @Override
                    public void convert(int position, ProvinceModel provinceModel, CommonListSpinner1.ViewHolder viewHolder, String codeStrings) {

//                      int resId =
                        D.i("=========getView=========position =" + provinceModel.toString());
//                        Map<String, String> map = (Map<String, String>) obj;
                        /*返回值 也可以是泛型*/
                        CheckedTextView textView = viewHolder.getView(R.id.text1);
                        textView.setText(provinceModel.getName());


                        if (historyString.contains(provinceModel.getCityCode())) {
//                            textView.setTextColor(activity.getResources().getColor(R.color.main_color));
                        } else {
//                            textView.setTextColor(activity.getResources().getColor(R.color.text_color333));
                        }


                        if (isFirst) {
                            textView.setChecked(false);
                            provinceModel.setChecked(false);
                        } else if (TextUtils.isEmpty(historyString)) {
                            // null
                            textView.setChecked(provinceModel.isChecked());
                        } else {
                            //not null
                            textView.setChecked(historyString.contains(provinceModel.getCityCode()));
                        }


//                        textView.setChecked(provinceModel.isChecked());
//                        textView.setChecked(true);


//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (codeStrings.contains(provinceModel.getCityCode())) {
//                                    textView.setChecked(true);
////                                    textView.setTextColor(activity.getResources().getColor(R.color.main_color));
//                                } else {
//                                    //空的时候 。直接恢复
//                                    textView.setChecked(false);
////                                    textView.setTextColor(activity.getResources().getColor(R.color.text_color333));
//                                }
//                            }
//                        }, 250);
//                        textView.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                textView.setChecked(provinceModel.isChecked());
//                            }
//                        });

//                        if (provinceModel.isChecked()) {
//                            textView.setText("已经被选中");
//                        }

//                        ViewGroup vg = (ViewGroup) textView.getParent();
//                        vg.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                textView.toggle();
//                                textView.setChecked(!textView.isChecked());
                                provinceModel.setChecked(textView.isChecked());
                                historyString = "";
//                                isFirst = false;
//                                if (provinceModel.isChecked()) {
//                                    textView.setTextColor(activity.getResources().getColor(R.color.main_color));
//                                } else {
//                                    textView.setTextColor(activity.getResources().getColor(R.color.text_color333));
//                                }
//                                if (selectListener != null) {
//                                    selectListener.onSelect(position, provinceModel.getCityCode(), provinceModel.getName());
//                                }


                            }
                        });


                    }

                    @Override
                    public int getLayoutId() {
//                        return android.R.layout.simple_list_item_multiple_choice;
                        return R.layout.tv_checked;
                    }
                });
        return builder.build(activity);


//        builder.setMaps();

//        Map<String, String> map = createMap("0001", "北京");


//        return CommonListSpinner1.getInstance(activity, p) // 单利。有点low
//                .setSortMaps(getSortMaps()) // 赋值
//                .initViews() // 初始化
//                .addOnItemClickListener((parent, view1, position, id) -> {
//                    D.e("addOnItemClickListener" + position);
//                    for (Map.Entry<String, String> entry : getSortMaps().get(position).entrySet()) {
//                        if (selectListener != null) {
//                            selectListener.onSelect(position, entry.getKey(), entry.getValue());
//                        }
//                    }
//                });

    }

    private static String getNameString(List<ProvinceModel> list) {
        return FUtil.$(",", Arrays.toString(getListByTag(list, "1").toArray()));
    }

    private static String getCodeString(List<ProvinceModel> list) {
        return FUtil.$(",", Arrays.toString(getListByTag(list, "0").toArray()));
    }

    public static List<String> getListByTag(List<ProvinceModel> list, String tag) {

        List<String> stringList = new ArrayList<>();
        if (tag.equals("0")) {//0  代码
            for (ProvinceModel provinceModel : list) {
                if (provinceModel.isChecked()) {
                    stringList.add(provinceModel.getCityCode());
                }
            }

        } else {//1   城市名称
            for (ProvinceModel provinceModel : list) {
                if (provinceModel.isChecked()) {
                    stringList.add(provinceModel.getName());
                }
            }
        }
        return stringList;


    }


    public static List<Map<String, String>> getSortMaps() {
        List<Map<String, String>> mapList = new ArrayList<>();
        mapList.add(createMap(MomentsType.all.getEnumValue(), MomentsType.all.getEnumText()));
        mapList.add(createMap(MomentsType.supply.getEnumValue(), MomentsType.supply.getEnumText()));
        mapList.add(createMap(MomentsType.purchase.getEnumValue(), MomentsType.purchase.getEnumText()));
        return mapList;
    }

    private static Map<String, String> createMap(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        return map;
    }


    private static List<ProvinceModel> createProvincesMap() {
        List<ProvinceModel> arrayList = new ArrayList<>();
        arrayList.add(new ProvinceModel("北京", "11"));
        arrayList.add(new ProvinceModel("天津", "12"));
        arrayList.add(new ProvinceModel("河北", "13"));
        arrayList.add(new ProvinceModel("山西", "14"));
        arrayList.add(new ProvinceModel("内蒙古", "15"));
        arrayList.add(new ProvinceModel("辽宁", "21"));
        arrayList.add(new ProvinceModel("吉林", "22"));
        arrayList.add(new ProvinceModel("黑龙江", "23"));
        arrayList.add(new ProvinceModel("上海", "31"));
        arrayList.add(new ProvinceModel("江苏", "32"));
        arrayList.add(new ProvinceModel("浙江", "33"));
        arrayList.add(new ProvinceModel("安徽", "34"));
        arrayList.add(new ProvinceModel("福建", "35"));
        arrayList.add(new ProvinceModel("江西", "36"));
        arrayList.add(new ProvinceModel("山东", "37"));
        arrayList.add(new ProvinceModel("河南", "41"));
        arrayList.add(new ProvinceModel("湖北", "42"));
        arrayList.add(new ProvinceModel("湖南", "43"));
        arrayList.add(new ProvinceModel("广东", "44"));
        arrayList.add(new ProvinceModel("广西", "45"));
        arrayList.add(new ProvinceModel("海南", "46"));
        arrayList.add(new ProvinceModel("重庆", "50"));
        arrayList.add(new ProvinceModel("四川", "51"));
        arrayList.add(new ProvinceModel("贵州", "52"));
        arrayList.add(new ProvinceModel("云南", "53"));
        arrayList.add(new ProvinceModel("西藏", "54"));
        arrayList.add(new ProvinceModel("陕西", "61"));
        arrayList.add(new ProvinceModel("甘肃", "62"));
        arrayList.add(new ProvinceModel("青海", "63"));
        arrayList.add(new ProvinceModel("宁夏", "64"));
        arrayList.add(new ProvinceModel("新疆", "65"));
        return arrayList;
    }

    private static List<ProvinceModel> createCityMap() {
        List<ProvinceModel> arrayList = new ArrayList<>();
        arrayList.add(new ProvinceModel("北京市", "1101"));
        arrayList.add(new ProvinceModel("天津市", "1201"));
        arrayList.add(new ProvinceModel("石家庄市", "1301"));
        arrayList.add(new ProvinceModel("太原市", "1401"));
        arrayList.add(new ProvinceModel("呼和浩特市", "1501"));
        arrayList.add(new ProvinceModel("沈阳市", "2101"));
        arrayList.add(new ProvinceModel("长春市", "2201"));
        arrayList.add(new ProvinceModel("上海市", "3101"));
        arrayList.add(new ProvinceModel("南京市", "3201"));
        arrayList.add(new ProvinceModel("杭州市", "3301"));
        arrayList.add(new ProvinceModel("合肥市", "3401"));
        arrayList.add(new ProvinceModel("福州市", "3501"));
        arrayList.add(new ProvinceModel("南昌市", "3601"));
        arrayList.add(new ProvinceModel("济南市", "3701"));
        arrayList.add(new ProvinceModel("郑州市", "4101"));
        arrayList.add(new ProvinceModel("武汉市", "4201"));
        arrayList.add(new ProvinceModel("长沙市", "4301"));
        arrayList.add(new ProvinceModel("南宁市", "4501"));
        arrayList.add(new ProvinceModel("海口市", "4601"));
        arrayList.add(new ProvinceModel("重庆市", "5001"));
        arrayList.add(new ProvinceModel("成都市", "5101"));
        arrayList.add(new ProvinceModel("贵阳市", "5201"));
        arrayList.add(new ProvinceModel("昆明市", "5301"));
        arrayList.add(new ProvinceModel("拉萨市", "5401"));
        arrayList.add(new ProvinceModel("西安市", "6101"));
        arrayList.add(new ProvinceModel("兰州市", "6201"));
        arrayList.add(new ProvinceModel("西宁市", "6301"));
        arrayList.add(new ProvinceModel("银川市", "6401"));
        arrayList.add(new ProvinceModel("乌鲁木齐市", "6501"));
        return arrayList;
    }


    public static interface OnSortSelectListener {
        void onSelect(int pos, String key, String value);
    }


}

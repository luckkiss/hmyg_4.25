package com.hldj.hmyg.Ui.friend;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Keep;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.friend.bean.enums.MomentsType;
import com.hldj.hmyg.Ui.friend.child.FriendBaseFragment;
import com.hldj.hmyg.Ui.friend.util.FriendUtil;
import com.hldj.hmyg.widget.CommonListSpinner;
import com.hldj.hmyg.widget.CommonListSpinner1;

import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;

/**
 * FinalActivity 来进行    数据绑定
 */
@Keep
public class FriendCycleSearchActivity extends FriendCycleActivity {

    /**
     * //排序
     * getView(R.id.tv_b_sort).setOnClickListener(v -> {
     * ChoiceSortList();
     * setColor(getView(R.id.tv_b_filter), getView(R.id.tv_b_sort), "1", mActivity);
     * });
     */

    @Keep
    private static final String TAG = "FriendCycleSearch";

    public String name = "haha";

//     ArrayList<String> list_title = new ArrayList<String>() {{
//        add("所有");
//        add("供应");
//        add("采购");
//    }};
//    private ArrayList<Fragment> list_fragment = new ArrayList<Fragment>() {{
//        add(FriendBaseFragment.newInstance(MomentsType.all.getEnumValue()));
//        add(FriendBaseFragment.newInstance(MomentsType.supply.getEnumValue()));
//        add(FriendBaseFragment.newInstance(MomentsType.purchase.getEnumValue()));
//    }};


    //    /*中间2个按钮*/
//    @ViewInject(id = R.id.rb_title_left)
//    RadioButton rb_title_left;
    @ViewInject(id = R.id.search_bar, click = "onClick")
    public View search_bar;

    @ViewInject(id = R.id.iv_view_type, click = "onClick")
    public TextView iv_view_type;

    /*分类  所有   供应   求购*/
    @ViewInject(id = R.id.tv_filter, click = "onClick")
    public SuperTextView tv_filter;

    /*地区*/
    @ViewInject(id = R.id.tv_sort, click = "onClick")
    public SuperTextView tv_sort;

    @ViewInject(id = R.id.rb_left, click = "onClick")
    public RadioButton rb_left;
    @ViewInject(id = R.id.rb_center, click = "onClick")
    public RadioButton rb_center;
    @ViewInject(id = R.id.rb_right, click = "onClick")
    public RadioButton rb_right;

    @ViewInject(id = R.id.radios)
    public RadioGroup radios;
    /*搜索框*/
    @ViewInject(id = R.id.search_content)
    public EditText search_content;

    @ViewInject(id = R.id.line)
    public View line;
    @ViewInject(id = R.id.line_iv)
    public ImageView line_iv;
    private CommonListSpinner1 commonListSpinner1;


    @Override
    public void initFiled(ArrayList<String> list_title, ArrayList<Fragment> list_fragment) {
//        list_title.clear();
        list_fragment.clear();
//      super.initFiled(list_title, list_fragment);
        try {
            list_fragment.add(0, FriendBaseFragment.newInstance(MomentsType.all.getEnumValue()));
            list_title.add(0, "所有");
        } catch (Exception e) {
            Log.i(TAG, "initFiled: ");
            e.printStackTrace();
        }
    }

    @Override
    public void initViewPager(ViewPager viewpager, RadioGroup view) {
        //不用此处传来的view  直接使用   radios
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    rb_left.setChecked(true);
                } else if (position == 1) {
                    rb_center.setChecked(true);
                } else {
                    rb_right.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setOffscreenPageLimit(1);
        radios.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_left) {
                viewpager.setCurrentItem(0);
            } else if (checkedId == R.id.rb_center) {
                viewpager.setCurrentItem(1);
            } else if (checkedId == R.id.rb_right) {
                viewpager.setCurrentItem(2);
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_friend_cycle_search;
    }


    @Keep
    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.search_bar:
//                ToastUtil.showLongToast("top");
                break;
            case R.id.rb_left:
//              ToastUtil.showLongToast("rb_left");
                currentType = MomentsType.all.getEnumValue();
                break;
            case R.id.rb_center:
//                ToastUtil.showLongToast("rb_center");
                currentType = MomentsType.supply.getEnumValue();
                break;
            case R.id.rb_right:
//                ToastUtil.showLongToast("rb_right");
                currentType = MomentsType.purchase.getEnumValue();
                break;
            case R.id.iv_view_type:
                searchContent = search_content.getText().toString().trim();
                FriendBaseFragment fragment = (FriendBaseFragment) list_fragment.get(viewpager.getCurrentItem());
                fragment.onRefresh(searchContent, currentType, "");
                break;
            case R.id.tv_sort:

                  /*分类*/
                tv_filter.setTextColor(getColorByRes(R.color.text_color333));
                /*地区*/
                tv_sort.setTextColor(getColorByRes(R.color.main_color));


                if (commonListSpinner1 != null) {
                    commonListSpinner1.ShowWithHistorys(cityCodeString);
                    return;
                }
                commonListSpinner1 = FriendUtil.CreateSortCitySpinner(
                        mActivity,
                        new FriendUtil.OnSortSelectListener() {
                            @Override
                            public void onSelect(int pos, String key, String value) {
//                                ToastUtil.showLongToast("==============pos=" + pos + "  key=" + key + "  value=" + value);
                                Log.i(TAG, "onSelect: key \n" + key);
                                Log.i(TAG, "onSelect: sub \n" + key.substring(1, key.length() - 1));
                                Log.i(TAG, "onSelect: value \n" + value);
                                Log.i(TAG, "onSelect: sub \n" + value.substring(1, value.length() - 1));

                                if (TextUtils.isEmpty(value.substring(1, value.length() - 1).trim())) {
                                    tv_sort.setText("地区");
                                    tv_sort.setShowState(true);
                                    cityCodeString = "";
                                } else {
                                    tv_sort.setText(value.substring(1, value.length() - 1));
                                    tv_sort.setShowState(false);
                                    cityCodeString = key.substring(1, key.length() - 1);
                                }

                                getCurrentFragment().onRefresh(searchContent, currentType, key.substring(1, key.length() - 1));


                                commonListSpinner1.dismiss();

                            }
                        },
                        line).Show();

                break;
            case R.id.tv_filter: // 分类
                /**
                 * 排序
                 */
//                searchContent = search_content.getText().toString().trim();
//                FriendBaseFragment fragment1 = (FriendBaseFragment) list_fragment.get(viewpager.getCurrentItem());
//                fragment1.onRefresh("1");


                //排序
//                  getView(R.id.tv_b_sort).setOnClickListener(v -> {
//                  ChoiceSortList();
//                  setColor(getView(R.id.tv_b_filter), getView(R.id.tv_b_sort), "1", mActivity);  });

//                ToastUtil.showLongToast("搜索");

                /*分类*/
                tv_filter.setTextColor(getColorByRes(R.color.main_color));
                /*地区*/
                tv_sort.setTextColor(getColorByRes(R.color.text_color333));


                if (commonListSpinner != null) {
                    commonListSpinner.ShowWithPos(possition);
                } else {
                    commonListSpinner = FriendUtil.CreateSortSpinner(mActivity, new FriendUtil.OnSortSelectListener() {
                        @Override
                        public void onSelect(int pos, String key, String value) {
                            possition = pos;
//                            ToastUtil.showLongToast("-----------pos=" + pos + "  key = " + key + "  value = " + value);
//                          if (checkedId == R.id.rb_left) {
//                            viewpager.setCurrentItem(pos);
                            currentType = key;
                            getCurrentFragment().onRefresh("searchKey", currentType, "");
                            tv_filter.setText(value);
                            commonListSpinner.dismiss();
//                            } else if (checkedId == R.id.rb_center) {
//                            viewpager.setCurrentItem(1);
//                            } else if (checkedId == R.id.rb_right) {
//                            viewpager.setCurrentItem(2);
//                            }
                        }
                    }, line).ShowWithPos(possition);
                }
                break;
        }

    }

    //城市搜索集合  { 11  ，  20  ，  30  ，  40  ... . }
    public String[] cityCodes = {"100", "200", "300"};
    public String cityCodeString = "";
    int possition = 0;
    CommonListSpinner commonListSpinner;
    @ViewInject(id = R.id.out)
    LinearLayout out;

    @Override
    public void initChild() {
        toolbar_left_icon.setImageResource(R.drawable.arrow_left_back);
        toolbar_left_icon.setOnClickListener(v -> finish());
        searchContent = getSearchContent();
        search_content.setText(searchContent);
        if (!TextUtils.isEmpty(searchContent)) // 避免空指针
            search_content.setSelection(searchContent.length());//将光标移至文字末尾
        currentType = MomentsType.all.getEnumValue();
    }


    public String getSearchContent() {
//        ToastUtil.showLongToast(getIntent().getStringExtra(TAG));
        if (TextUtils.isEmpty(getIntent().getStringExtra(TAG))) {
            return "";
        }
        return getIntent().getStringExtra(TAG);
    }

    public static void start(Activity activity, String content) {
        Intent intent = new Intent(activity, FriendCycleSearchActivity.class);
        intent.putExtra(TAG, content);
        activity.startActivity(intent);
    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    public FriendBaseFragment getCurrentFragment() {

        return (FriendBaseFragment) list_fragment.get(viewpager.getCurrentItem());
    }

}

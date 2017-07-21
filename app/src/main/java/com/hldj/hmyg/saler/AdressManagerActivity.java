package com.hldj.hmyg.saler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.hldj.hmyg.M.AddressBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.contract.AdressListContract;
import com.hldj.hmyg.model.AdressListModel;
import com.hldj.hmyg.presenter.AdressListPresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.SaveSeedingBottomLinearLayout;
import com.hy.utils.StringFormatUtil;
import com.hy.utils.ToastUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 苗源地址
 */
public class AdressManagerActivity extends BaseMVPActivity<AdressListPresenter, AdressListModel> implements AdressListContract.View {

    private static final String TAG = "AdressActivity";
    CoreRecyclerView coreRecyclerView;


    @Override
    public void showErrir(String erMst) {
        hindLoading();
        coreRecyclerView.selfRefresh(false);
        ToastUtil.showShortToast(erMst);
    }

    private class AdressQueryBean implements Serializable {
        public String pageIndex = "0";
        public String pageSize = "20";
        public String type = "";
        public String searchKey = "";
    }

    private AdressQueryBean queryBean = null;

    public AdressQueryBean getQueryBean() {
        if (queryBean == null) {
            queryBean = new AdressQueryBean();
        }
        return queryBean;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConstantState.CHANGE_DATES) {//地址 修改成功
            //修改成功
            coreRecyclerView.onRefresh();
            ToastUtil.showShortToast("修改成功 ^_^ ");
        }
        if (resultCode == ConstantState.ADD_SUCCEED) {
            //修改成功
            coreRecyclerView.onRefresh();
            ToastUtil.showShortToast("添加成功 ^_^ ");
        }

    }


    static SaveSeedingBottomLinearLayout.onAddressSelectListener onAddressSelectListener;


    /**
     * @param context
     * @param addressId               地址的 id
     * @param onAddressSelectListener
     */
    public static void start2AdressListActivity(Context context, String addressId, String from, SaveSeedingBottomLinearLayout.onAddressSelectListener onAddressSelectListener) {
        Intent intent = new Intent(context, AdressManagerActivity.class);
        intent.putExtra("addressId", addressId);
        intent.putExtra("from", from);
        AdressManagerActivity.onAddressSelectListener = onAddressSelectListener;
        context.startActivity(intent);
    }


    public static void start2Activity(Context mActivity) {
        Intent intent = new Intent(mActivity, AdressManagerActivity.class);
//        intent.putExtra("from", from);
        mActivity.startActivity(intent);
    }


    @Override
    public int bindLayoutID() {
        return R.layout.activity_address_list_manager;
    }

    /**
     * @link{initRecycle}
     */
    @Override
    public void initView() {

        getView(R.id.btn_back).setOnClickListener(view -> finish());

        //添加地址
        getView(R.id.btn_add_addt).setOnClickListener(view -> {
            AddAdressActivity.start2Activity(mActivity, null);
        });


        //搜索关键字
        getView(R.id.iv_search_adress).setOnClickListener(view -> {

            EditText etSearch = getView(R.id.et_addr_search);
            getQueryBean().searchKey = TextUtils.isEmpty(etSearch.getText()) ? "" : etSearch.getText().toString();
            coreRecyclerView.onRefresh();

//            if (!TextUtils.isEmpty(etSearch.getText())) {
//                getQueryBean().searchKey = etSearch.getText().toString();
//                coreRecyclerView.onRefresh();
//            } else {
//                ToastUtil.showShortToast("请输入搜索关键字");
//            }
        });

        coreRecyclerView = getView(R.id.address_recycle);
        coreRecyclerView.init(new BaseQuickAdapter<AddressBean, BaseViewHolder>(R.layout.recy_item_adress_manager) {

            @Override
            protected void convert(BaseViewHolder helper, AddressBean item) {
                D.e("==item====" + item.toString());
                helper.setText(R.id.tv_recy_item_one, item.fullAddress);
                helper.setText(R.id.tv_recy_item_two, "苗圃名称：" + striFil(mActivity, item.name, ""));
                helper.setText(R.id.tv_recy_item_three, "联  系  人：" + striFil(mActivity, item.contactName, item.contactPhone));

                helper.setChecked(R.id.cb_is_default, item.isDefault);

                helper.addOnClickListener(R.id.cb_is_default, view -> {
//                    ToastUtil.showShortToast("执行修改 默认地址代码");
                    mPresenter.changeAddr(item.id);
                });

                helper.addOnClickListener(R.id.iv_recy_item_left, view -> {
//                    ToastUtil.showShortToast("编辑");
                    AddAdressActivity.start2Activity(mActivity, item);
                });
                helper.addOnClickListener(R.id.tv_recy_item_right, view -> {
//                    ToastUtil.showShortToast("删除");
                    mPresenter.deleteAddr(item.id);
                });
            }


        }).openLoadMore(100, page -> {
            mPresenter.getData(getQueryBean());
        }).openRefresh().openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //initRecycle 下一步执行
        mPresenter.getData(getQueryBean());//初始化  地址数据列表
    }


    public static String striFil(Activity activity, String str1, String str2) {
        if (!TextUtils.isEmpty(str1) && !TextUtils.isEmpty(str2)) {
            return str1 + " " + str2;
        } else if (!TextUtils.isEmpty(str1) && TextUtils.isEmpty(str2)) {
            return str1;
        } else if (TextUtils.isEmpty(str1) && !TextUtils.isEmpty(str2)) {
            return str2;
        } else {
            StringFormatUtil formatUtil = new StringFormatUtil(activity, "未填写", "未填写", R.color.text_color999).fillColor();
            return formatUtil.getResult().toString();
        }
    }

    @Override
    public void initRecycle(List<AddressBean> beens) {
        coreRecyclerView.getAdapter().addData(beens);
        coreRecyclerView.selfRefresh(false);

    }

    @Override
    public void OnDeleteAddr(boolean bo) {
        if (bo) coreRecyclerView.onRefresh();//删除成功。刷新界面
        ToastUtil.showShortToast("地址删除成功");

    }

    @Override
    public void OnChangeAddrAddr(boolean bo) {
        if (bo) {
            coreRecyclerView.onRefresh();//修改地址。刷新界面
            ToastUtil.showShortToast("地址修改成功");
        }
    }


    @Override
    public void initVH() {

    }

    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }


    public static class Address {
        public String addressId = "";
        public String contactPhone = "";
        public String contactName = "";
        public String cityName = "";
        public boolean isDefault = false;
    }


}

package com.hldj.hmyg.saler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.hldj.hmyg.saler.M.AdressQueryBean;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.SaveSeedingBottomLinearLayout;
import com.hy.utils.StringFormatUtil;
import com.hy.utils.ToastUtil;

import java.util.List;

/**
 * 苗源地址
 */
public class AdressActivity extends BaseMVPActivity<AdressListPresenter, AdressListModel> implements AdressListContract.View {

    private static final String TAG = "AdressActivity";
    CoreRecyclerView coreRecyclerView;


    @Override
    public void showErrir(String erMst) {
        hindLoading();
        coreRecyclerView.selfRefresh(false);
        ToastUtil.showShortToast(erMst);
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
        if (resultCode == ConstantState.CHANGE_DATES) {
            //修改成功
            coreRecyclerView.onRefresh();
            ToastUtil.showShortToast("修改成功^_^");
        } else if (resultCode == ConstantState.ADD_SUCCEED) {
            coreRecyclerView.onRefresh();
            ToastUtil.showShortToast("新增地址成功^_^");
        } else if (resultCode == ConstantState.DELETE_SUCCEED){
            coreRecyclerView.onRefresh();
            ToastUtil.showShortToast("删除成功^_^");
        }


    }


    static SaveSeedingBottomLinearLayout.onAddressSelectListener onAddressSelectListener;


    /**
     * @param context
     * @param addressId               地址的 id
     * @param onAddressSelectListener
     */
    public static void start2AdressListActivity(Context context, String addressId, String from, SaveSeedingBottomLinearLayout.onAddressSelectListener onAddressSelectListener) {
        Intent intent = new Intent(context, AdressActivity.class);
        intent.putExtra("addressId", addressId);
        intent.putExtra("from", from);
        AdressActivity.onAddressSelectListener = onAddressSelectListener;
        context.startActivity(intent);
    }


    public String getExtralId() {
        return getIntent().getStringExtra("addressId");
    }


    public static void start2Activity(Context mActivity) {
        Intent intent = new Intent(mActivity, AdressActivity.class);
//        intent.putExtra("from", from);
        mActivity.startActivity(intent);
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_address_list;
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
        EditText etSearch = getView(R.id.et_addr_search);
        //搜索关键字
        getView(R.id.iv_search_adress).setOnClickListener(view -> {

            getQueryBean().searchKey = etSearch.getText().toString();
            coreRecyclerView.onRefresh();

//            if (!TextUtils.isEmpty(etSearch.getText())) {
//                getQueryBean().searchKey = etSearch.getText().toString();
//                coreRecyclerView.onRefresh();
//            } else {
//                ToastUtil.showShortToast("请输入搜索关键字");
//            }
        });

        coreRecyclerView = getView(R.id.address_recycle);
        coreRecyclerView.init(new BaseQuickAdapter<AddressBean, BaseViewHolder>(R.layout.recy_item_adress) {

            @Override
            protected void convert(BaseViewHolder helper, AddressBean item) {
                D.e("==item====" + item.toString());
                boolean isSelect = false;
                isSelect = item.id.equals(getExtralId());
                //显示 绿色  【默认】
                //选中 整体红色
                if (isSelect) {
                    //选中状态，整体是红色
                    helper.setText(R.id.tv_recy_item_one, item.isDefault ? "[ 默认 ]" + item.fullAddress : item.fullAddress);//默认地址
                    helper.setSelected(R.id.tv_recy_item_one, true);//默认地址显示红色
                } else if (item.isDefault) {
                    String str = "[ 默认 ] ";
                    //默认状态 默认 为绿色
                    StringFormatUtil fillColor = new StringFormatUtil(mActivity, str + item.fullAddress, str, R.color.main_color).fillColor();
                    helper.setText(R.id.tv_recy_item_one, fillColor.getResult());//默认地址
                } else {
                    //常规状态  整体显示  黑色
                    helper.setSelected(R.id.tv_recy_item_one, false);//默认地址
                    helper.setText(R.id.tv_recy_item_one, item.fullAddress);//默认地址
                }
                helper.setText(R.id.tv_recy_item_two, "苗圃名称：" + AdressManagerActivity.striFil(mActivity, item.name, ""));
                helper.setText(R.id.tv_recy_item_three, "联  系  人：" + AdressManagerActivity.striFil(mActivity, item.contactName, item.contactPhone));
                /**
                 *    helper.setText(R.id.tv_recy_item_two, "苗圃名称：" + striFil(mActivity, item.name, ""));
                 helper.setText(R.id.tv_recy_item_three, "联  系  人：" + striFil(mActivity, item.contactName, item.contactPhone));
                 */
                helper.convertView.setOnClickListener(view -> {
                    Address address = new Address();
                    address.addressId = item.id;
                    address.cityName = item.cityName;
                    address.contactName = item.contactName;
                    address.contactPhone = item.contactPhone;
                    address.fullAddress = item.fullAddress;
                    address.name = item.name ;
                    address.isDefault = item.isDefault;
                    D.e("====" + item.isDefault);
//                    if (TextUtils.isEmpty(item.twCode)) {
//                        ToastUtil.showShortToast("请编辑完整这条地址的所在街道!");
//                        return;
//                    }

                    onAddressSelectListener.onAddressSelect(address);
                    finish();
                });

                helper.addOnClickListener(R.id.iv_right_edit_bj, view -> {
//                    ToastUtil.showShortToast("编辑");
                    AddAdressActivity.start2Activity(mActivity, item);
                });

            }
        }).openLoadMore(100, page -> {
            mPresenter.getData(getQueryBean());
        }).openRefresh().openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //initRecycle 下一步执行
        mPresenter.getData(getQueryBean());//初始化  地址数据列表
    }

    @Override
    public void initRecycle(List<AddressBean> beens) {
        coreRecyclerView.getAdapter().addData(beens);
        coreRecyclerView.selfRefresh(false);
    }

    @Override
    public void OnDeleteAddr(boolean bo) {
        if (bo) coreRecyclerView.onRefresh();//删除成功。刷新界面

    }

    @Override
    public void OnChangeAddrAddr(boolean bo) {
        if (bo) coreRecyclerView.onRefresh();//修改地址。刷新界面
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
        public String name = "";//苗圃名称
        public String fullAddress = "";// 拼接好的 详细地址地址
        public boolean isDefault = false;

        @Override
        public String toString() {
            return "Address{" +
                    "addressId='" + addressId + '\'' +
                    ", contactPhone='" + contactPhone + '\'' +
                    ", contactName='" + contactName + '\'' +
                    ", cityName='" + cityName + '\'' +
                    ", name='" + name + '\'' +
                    ", fullAddress='" + fullAddress + '\'' +
                    ", isDefault=" + isDefault +
                    '}';
        }
    }


    public static void start2Activity(Activity activity) {

    }

}

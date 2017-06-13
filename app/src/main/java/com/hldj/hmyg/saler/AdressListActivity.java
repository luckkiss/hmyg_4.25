package com.hldj.hmyg.saler;

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
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.widget.SaveSeedingBottomLinearLayout;
import com.hy.utils.ToastUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 苗源地址
 */
public class AdressListActivity extends BaseMVPActivity<AdressListPresenter, AdressListModel> implements AdressListContract.View {

    private static final String TAG = "AdressListActivity";
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
        if (resultCode == ConstantState.CHANGE_DATES) {
            //修改成功
            coreRecyclerView.onRefresh();
        }

    }


    static SaveSeedingBottomLinearLayout.onAddressSelectListener onAddressSelectListener;


    /**
     * @param context
     * @param addressId               地址的 id
     * @param onAddressSelectListener
     */
    public static void start2AdressListActivity(Context context, String addressId, String from, SaveSeedingBottomLinearLayout.onAddressSelectListener onAddressSelectListener) {
        Intent intent = new Intent(context, AdressListActivity.class);
        intent.putExtra("addressId", addressId);
        intent.putExtra("from", from);
        AdressListActivity.onAddressSelectListener = onAddressSelectListener;
        context.startActivity(intent);
    }


    public static void start2Activity(Context mActivity) {
        Intent intent = new Intent(mActivity, AdressListActivity.class);
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
//                    this.tv_recy_item_one = (TextView) rootView.findViewById(R.id.tv_recy_item_one);
//                    this.tv_recy_item_two = (TextView) rootView.findViewById(R.id.tv_recy_item_two);
//                    this.tv_recy_item_three = (TextView) rootView.findViewById(R.id.tv_recy_item_three);
//                    this.cb_is_default = (CheckBox) rootView.findViewById(R.id.cb_is_default);
//                    this.iv_recy_item_left = (ImageView) rootView.findViewById(R.id.iv_recy_item_left);
//                    this.tv_recy_item_right = (ImageView) rootView.findViewById(R.id.tv_recy_item_right);

            @Override
            protected void convert(BaseViewHolder helper, AddressBean item) {
                D.e("==item====" + item.toString());

                /**
                 * if(model.detailAddress.length>0){
                 addressStr=[NSString stringWithFormat:@"%@%@",model.cityName,model.detailAddress];
                 }else{
                 addressStr=[NSString stringWithFormat:@"%@",model.cityName];
                 }
                 self.addressLab.text=addressStr;
                 */

                helper.setText(R.id.tv_recy_item_one, item.fullAddress);
                helper.setText(R.id.tv_recy_item_two, item.detailAddress.length() > 0 ? item.cityName + item.detailAddress : item.detailAddress);
                helper.setText(R.id.tv_recy_item_three, "联  系  人：" + item.contactName + " " + item.contactPhone);
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
        public boolean isDefault = false;
    }


}

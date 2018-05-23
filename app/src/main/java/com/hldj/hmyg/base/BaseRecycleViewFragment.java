package com.hldj.hmyg.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hldj.hmyg.CallBack.HandlerAjaxCallBack;
import com.hldj.hmyg.CallBack.IDelete;
import com.hldj.hmyg.CallBack.IFootMarkDelete;
import com.hldj.hmyg.CallBack.IFootMarkEmpty;
import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.SimpleGsonBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.me.HistoryActivity;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.D;
import com.hy.utils.ToastUtil;
import com.zf.iosdialog.widget.AlertDialog;

import java.io.Serializable;

import me.imid.swipebacklayout.lib.app.NeedSwipeBackActivity;

/**
 * 懒加载  fragment
 */

public abstract class BaseRecycleViewFragment<T> extends BaseLazyFragment {


    public CoreRecyclerView mCoreRecyclerView;

    private int pageSize = 20;

    @Override
    protected void onFragmentVisibleChange(boolean b) {

    }

    @Override
    protected void onFragmentFirstVisible() {

        mCoreRecyclerView = mRootView.findViewById(R.id.recycle);

        mCoreRecyclerView.init(new BaseQuickAdapter<T, BaseViewHolder>(bindRecycleItemId()) {
            @Override
            protected void convert(BaseViewHolder helper, T item) {

                doConvert(helper, item, mActivity);

            }
        })
                .openRefresh()
                .openLoadMore(pageSize, page -> {
                    doRefreshRecycle(page + "");
                });
        onRecycleViewInited(mCoreRecyclerView);

        mCoreRecyclerView.onRefresh();


    }

    @Override
    protected void initView(View rootView) {


    }

    protected abstract void doRefreshRecycle(String page);

    /* 初始化完毕  可以在此处 对  recycle  进行   添加别的属性 */
    protected abstract void onRecycleViewInited(CoreRecyclerView coreRecyclerView);


    /* 执行recycle   绘制 */
    protected abstract void doConvert(BaseViewHolder helper, T item, NeedSwipeBackActivity mActivity);


    /* 绑定 recycle   item  先定制一个  后期可以多个*/
    public abstract int bindRecycleItemId();

    @Override
    protected int bindLayoutID() {
        return R.layout.fragment_base_recycle_view;
    }

    // 删除 与 清空 的
    public <H extends IDelete, A extends NeedSwipeBackActivity> void doUserDelDelete(BaseViewHolder helper, H t, A a, HandlerAjaxCallBack handlerAjaxCallBack) {

        String tip = "确定删除本项?";
        if (t instanceof IFootMarkEmpty) {
            tip = ((IFootMarkEmpty) t).getEmptyTip();
        }

//      String str = "确定删除本项?";
        new AlertDialog(a).builder()
                .setTitle(tip)
                .setPositiveButton("确定", v1 -> {
                    {
                        if (t instanceof IFootMarkDelete) {
                            new BasePresenter()
                                    .putParams("ids", ((IFootMarkDelete) t).getFootMarkId())
                                    .doRequest(t.getDomain(), handlerAjaxCallBack);
                        }
                        if (t instanceof IFootMarkEmpty) {
                            new BasePresenter()
                                    .putParams("sourceType", ((IFootMarkEmpty) t).sourceType().getEnumValue())
                                    .doRequest(t.getDomain(), handlerAjaxCallBack);
                        }

                    }
                }).setNegativeButton("取消", v2 -> {
        }).show();
    }

    public <H extends IDelete, A extends NeedSwipeBackActivity> void doUserDelDelete(BaseViewHolder helper, H t, A a) {
        doUserDelDelete(helper, t, a, new HandlerAjaxCallBack() {
            @Override
            public void onRealSuccess(SimpleGsonBean gsonBean) {
                D.i("============删除成功   位置 在base ================");
                ToastUtil.showLongToast(gsonBean.msg);
                if (a instanceof HistoryActivity) {
                    //重新请求数量
                    ((HistoryActivity) a).requestCounts();
                }
                mCoreRecyclerView.onRefresh();

            }
        });

    }


    // foot marks 清空


    public static void addArgument(String key, Object value, Fragment fragment) {
        if (fragment != null) {
            Bundle bundle = new Bundle();
            if (value instanceof String) {
                bundle.putString(key, (String) value);
            } else if (value instanceof Boolean) {
                bundle.putBoolean(key, (Boolean) value);
            } else if (value instanceof Serializable) {
                bundle.putSerializable(key, ((Serializable) value));
            } else {
                D.i("-------------other---------");
                D.i("-------------other---------");
                D.i("-------------other---------");
                D.i("-------------other-------需要时额外添加--");
            }
            fragment.setArguments(bundle);
        }
    }


    public String getStringArgument(String key) {
        if (getArguments() != null) {
            return getArguments().getString(key, "");
        } else {
            return "";
        }
    }


}

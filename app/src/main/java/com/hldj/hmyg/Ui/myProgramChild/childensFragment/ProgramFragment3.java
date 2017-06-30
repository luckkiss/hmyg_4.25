package com.hldj.hmyg.Ui.myProgramChild.childensFragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hldj.hmyg.FlowerDetailActivity;
import com.hldj.hmyg.M.PorgramDetailGsonBean;
import com.hldj.hmyg.R;
import com.hldj.hmyg.Ui.myProgramChild.ProgramDirctActivity;
import com.hldj.hmyg.base.BaseFragment;
import com.hldj.hmyg.model.MyProgramGsonBean;
import com.hldj.hmyg.saler.P.BasePresenter;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;
import com.weavey.loading.lib.LoadingLayout;

import net.tsz.afinal.http.AjaxCallBack;

import static android.content.ContentValues.TAG;

/**
 * 项目信息
 */

public class ProgramFragment3 extends BaseFragment {
    MyPresenter myPresenter;
    private boolean mIsPrepared = false;
    private LoadingLayout loadingLayout;
    private boolean isFirst = true;


    @Override
    protected int bindLayoutID() {
        return R.layout.fragment_program3;
    }

    @Override
    protected void initView(View rootView) {
        myPresenter = new MyPresenter();
    }

    private void initBean(MyProgramGsonBean.DataBeanX.PageBean.DataBean project) {

        isFirst = false;
        this.setText(getView(R.id.program3_num), project.num);
        this.setText(getView(R.id.program3_projectName), project.projectName);
        this.setText(getView(R.id.program3_projectFullName), project.projectFullName);
        this.setText(getView(R.id.program3_consumerUserName), project.consumerUserName);
        this.setText(getView(R.id.program3_address), project.address);
        this.setText(getView(R.id.program3_consumerName), project.consumerName);
        this.setText(getView(R.id.program3_displayName), project.clerk.displayName);
        ((TextView) getView(R.id.program3_displayName)).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        this.getView(R.id.program3_displayName).setOnClickListener(view -> FlowerDetailActivity.CallPhone(project.clerk.publicPhone, mActivity));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated: ");
        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    public String getProjectId() {
        return ((ProgramDirctActivity) mActivity).getExtralID();
    }

    @Override
    protected void loadData() {
        if (!mIsVisible || !mIsPrepared || !isFirst) {
            Log.e(TAG, "不加载数据 mIsVisible=" + mIsVisible + "  mIsPrepared=" + mIsPrepared + " isFirst = " + isFirst);
            return;
        } else {
            Log.e(TAG, "加载数据" + mIsVisible + "  mIsPrepared=" + mIsPrepared + " isFirst = " + isFirst);
        }

        myPresenter.getDatas();

    }


///admin/project/projectInfo


    private class MyPresenter extends BasePresenter {

        public void getDatas() {
            putParams("projectId", getProjectId());
            AjaxCallBack ajaxCallBack = new AjaxCallBack<String>() {
                @Override
                public void onSuccess(String json) {

                    PorgramDetailGsonBean gsonBean = GsonUtil.formateJson2Bean(json, PorgramDetailGsonBean.class);
                    if (gsonBean.code.equals(ConstantState.SUCCEED_CODE)) {
                        initBean(gsonBean.data.project);
                    } else {
                        ToastUtil.showShortToast(gsonBean.msg);
                    }
                }
            };
            doRequest("admin/project/projectInfo", true, ajaxCallBack);
//

        }


    }


}

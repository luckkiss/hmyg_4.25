package com.hldj.hmyg.me;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.hldj.hmyg.R;
import com.hldj.hmyg.base.BaseMVPActivity;
import com.hldj.hmyg.bean.SimpleGsonBean_new;
import com.hldj.hmyg.bean.SimplePageBean;
import com.hldj.hmyg.buyer.weidet.BaseQuickAdapter;
import com.hldj.hmyg.buyer.weidet.BaseViewHolder;
import com.hldj.hmyg.buyer.weidet.CoreRecyclerView;
import com.hldj.hmyg.saler.P.BaseRxPresenter;
import com.hldj.hmyg.util.ContactInfoParser;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 联系人列表 界面
 */

public class AddContactActivity extends BaseMVPActivity implements View.OnClickListener {

    int item_layout_id = R.layout.item_invite_friend_list;


    @ViewInject(id = R.id.recycle)
    CoreRecyclerView recycler;
    private RxPermissions rxPermissions;


    @ViewInject(id = R.id.sptv_program_do_search, click = "showArrays")
    View view;


//    public void showArrays(View view) {
//
//        List<ContactInfoParser.ContactInfo> list = ContactInfoParser.getContacts(mActivity);
//        ToastUtil.showLongToast(list.toString());
//
//        ToastUtil.showLongToast(GsonUtil.Bean2Json(list));
//
//        D.i(GsonUtil.Bean2Json(list));
//
//    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView() {
        FinalActivity.initInjectedView(this);

        rxPermissions = new RxPermissions(mActivity);

//        rxPermissions
//                .request(Manifest.permission.READ_CONTACTS)
//                .subscribe(granted  -> {
//                    if (granted) {
//                        // All requested permissions are granted
//                    } else {
//                        // At least one permission is denied
//                    }
//                });


//                .subscribe(permission -> { // will emit 2 Permission objects
//                    if (permission.granted) {
//                        ToastUtil.showLongToast("获得权限");
//
//                        ContactInfoParser.findAll(mActivity);
//
//                        // `permission.name` is granted !
//                    } else if (permission.shouldShowRequestPermissionRationale) {
//                        ToastUtil.showLongToast("禁止本次");
//                        // Denied permission without ask never again
//                    } else {
//                        // Denied permission with ask never again
//                        // Need to go to the settings
//                        ToastUtil.showLongToast("永远禁止");
//                    }
//                });


        recycler.init(new BaseQuickAdapter<ContactInfoParser.ContactInfo, BaseViewHolder>(item_layout_id) {
            @Override
            protected void convert(BaseViewHolder helper, ContactInfoParser.ContactInfo item) {
                Log.i(TAG, "convert: " + item.toString());
                helper
                        .setText(R.id.title, item.getName())
                        .setText(R.id.content, item.getPhone())
                        .setText(R.id.fensi, " +关注 ").setTextColorRes(R.id.fensi, R.color.white)
//                        .setBackgroundRes(R.id.fensi, R.drawable.white_or_main_color)

                ;


            }
        })
                .openLoadMore(20, page -> {
                    requestData(page);
                })
                .openRefresh();
        recycler.onRefresh();


    }

    private void requestData(int page) {


        // Must be done during an initialization phase like onCreate
//        rxPermissions
//                .request(Manifest.permission.GET_ACCOUNTS,Manifest.permission.READ_CONTACTS)
//                .subscribe(granted -> {
//                    if (granted) { // Always true pre-M
//                        // I can control the camera now
//                        Log.i("///", "requestData: ");
//                    } else {
//                        // Oups permission denied
//                        Log.i("///", "requestData: ");
//                    }
//                });


        rxPermissions
                .requestEachCombined(Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    showLoading();
                })
                .map(permission -> {
//                    if (permission.granted) {
//                        return ContactInfoParser.findAll(mActivity);
//                    } else {
//                        return null;
//                    }
//                    return ContactInfoParser.findAll(mActivity);
                    if (permission.granted) {
                        ToastUtil.showLongToast("获得权限");
                        return ContactInfoParser.getContacts(mActivity, new ContactInfoParser.OnPhoneUpdateListener() {
                            @Override
                            public void onUpdata(ContactInfoParser.ContactInfo contactInfo) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        UpdateLoading(contactInfo.getName());

                                    }
                                });

                            }
                        });

                        // `permission.name` is granted !
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        ToastUtil.showLongToast("禁止本次");
                        return null;
                        // Denied permission without ask never again
                    } else {
                        // Denied permission with ask never again
                        // Need to go to the settings
                        ToastUtil.showLongToast("永远禁止");
                        return null;
                    }
                })
                .filter(contactInfos -> contactInfos != null)
                .flatMap(new Function<List<ContactInfoParser.ContactInfo>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<List<ContactInfoParser.ContactInfo>> apply(List<ContactInfoParser.ContactInfo> contactInfos) throws Exception {
                        return getContactInfoOnly(contactInfos);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                            recycler.selfRefresh(false);
                            hindLoading();

                        }
                )
                .subscribe(contactInfos -> {
                    recycler.getAdapter().addData((contactInfos));
                }, throwable -> {
                    ToastUtil.showLongToast("请求失败" + throwable.getMessage());
                });
    }

    private ObservableSource<List<ContactInfoParser.ContactInfo>> getContactInfoOnly(List<ContactInfoParser.ContactInfo> contactInfos) {

        return Observable
                .create(new ObservableOnSubscribe<List<ContactInfoParser.ContactInfo>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<ContactInfoParser.ContactInfo>> e) throws Exception {
                        java.lang.reflect.Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<ContactInfoParser.ContactInfo>>>>() {
                        }.getType();
                        new BaseRxPresenter()
                                .putParams("mobileData", GsonUtil.Bean2Json(contactInfos))
                                .doRequest("admin/userFollow/matchMobileUser", true, new AjaxCallBack<String>() {
                                    @Override
                                    public void onSuccess(String json) {


//                                        TongXunGsonBean tongXunGsonBean = GsonUtil.formateJson2Bean(json, TongXunGsonBean.class);

//                                        e.onNext(tongXunGsonBean.data.userList.aaBeans);
//                                        e.onComplete();
                                        Log.i("tongxun", "onSuccess: " + json);

                                    }

                                    @Override
                                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                                        Log.i("tongxun", "onSuccess: " + t.getMessage());
                                    }
                                });


//                                        , new HandlerAjaxCallBack() {
//                                    @Override
//                                    public void onRealSuccess(SimpleGsonBean gsonBean) {
//                                        e.onNext(gsonBean.getData().userList);
//                                        e.onComplete();
//                                    }
//
//                                    @Override
//                                    public void onFailure(Throwable t, int errorNo, String strMsg) {
//                                        super.onFailure(t, errorNo, strMsg);
//                                        e.onError(t);
//                                    }
//
//                                    @Override
//                                    public void onFinish() {
//                                        super.onFinish();
//                                        e.onComplete();
//                                    }
//                                });


                    }
                });


    }


    @Override
    public boolean setSwipeBackEnable() {
        return true;
    }

    @Override
    public int bindLayoutID() {
        return R.layout.activity_add_contact;
    }


    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, AddContactActivity.class));
    }

    @Override
    public String setTitle() {
        return "我的关注";
    }

}

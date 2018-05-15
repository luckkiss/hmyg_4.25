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
import com.hldj.hmyg.bean.TongXunGsonBean;
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


//        recycler.getRecyclerView().addItemDecoration(   corecyclerView.getRecyclerView().addItemDecoration(
//                SectionDecoration.Builder.init(new SectionDecoration.PowerGroupListener() {
//                    @Override
//                    public String getGroupName(int position) {
//
//                        if (mCoreRecyclerView.getAdapter().getData().size() == 0) {
//                            return null;
//                        } else {
////                            dateStr
//                            BPageGsonBean.DatabeanX.Pagebean.Databean databean = (BPageGsonBean.DatabeanX.Pagebean.Databean) mCoreRecyclerView.getAdapter().getItem(position);
//                            return databean.attrData.dateStr;
//                        }
//
//                    }
//
//                    @Override
//                    public View getGroupView(int position) {
////                        View view = LayoutInflater.from(HistoryActivity.this).inflate( R.layout.item_list_simple, null);
////                        view.setBackgroundColor(getColorByRes(R.color.gray_bg_ed));
////                        TextView tv = view.findViewById(android.R.id.text1);
////                        tv.setText(recycler.getAdapter().getItem(position) + "");
//                        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_tag, null);
//                        TextView textView = view.findViewById(R.id.text1);
//                        textView.setHeight((int) getResources().getDimension(R.dimen.px74));
//                        BPageGsonBean.DatabeanX.Pagebean.Databean databean = (BPageGsonBean.DatabeanX.Pagebean.Databean) mCoreRecyclerView.getAdapter().getItem(position);
//                        textView.setText(databean.attrData.dateStr);
//                        return view;
//                    }
//                }).setGroupHeight((int) getResources().getDimension(R.dimen.px74)).build());
//);
//
//        recycler.onRefresh();


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
                .flatMap(new Function<List<ContactInfoParser.ContactInfo>, ObservableSource<List<TongXunGsonBean.DataBean.UserDataBean>>>() {
                    @Override
                    public ObservableSource<List<TongXunGsonBean.DataBean.UserDataBean>> apply(List<ContactInfoParser.ContactInfo> contactInfos) throws Exception {


                        return getContactInfoOnly(contactInfos);
                    }
                })

                .flatMap(new Function<List<TongXunGsonBean.DataBean.UserDataBean>, ObservableSource<TongXunGsonBean.DataBean.UserDataBean>>() {
                    @Override
                    public ObservableSource<TongXunGsonBean.DataBean.UserDataBean> apply(List<TongXunGsonBean.DataBean.UserDataBean> userDataBeans) throws Exception {
//                        return Observable.just(userDataBeans);
//                        TongXunGsonBean.DataBean.UserDataBean[] userDataBeans1 = (TongXunGsonBean.DataBean.UserDataBean[]) userDataBeans.toArray();
//                      return Observable.just(userDataBeans.get(0));
                        return Observable.fromIterable(userDataBeans);
                    }
                })

//                .flatMap(new Function<List<TongXunGsonBean.DataBean.UserDataBean>, List<ContactInfoParser.ContactInfo>>() {
//                    @Override
//                    public  ObservableSource<List<TongXunGsonBean.DataBean.UserDataBean>>  apply(List<TongXunGsonBean.DataBean.UserDataBean> userDataBeans) throws Exception {
//                        List<ContactInfoParser.ContactInfo> contactInfos = new ArrayList<>();
//
//                        return Ob;
//                    }
//                })

                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                            recycler.selfRefresh(false);
                            hindLoading();

                        }
                )
                .subscribe(contactInfos -> {
                    Log.i("resulet ", "  py  is  " + contactInfos.py + "   ---\n list  is = " + contactInfos.userList.toString());
                    recycler.getAdapter().addData(contactInfos.userList);
                }, throwable -> {
                    ToastUtil.showLongToast("请求失败" + throwable.getMessage());
                });
    }

    private ObservableSource<List<TongXunGsonBean.DataBean.UserDataBean>> getContactInfoOnly(List<ContactInfoParser.ContactInfo> contactInfos) {

        return Observable
                .create(new ObservableOnSubscribe<List<TongXunGsonBean.DataBean.UserDataBean>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<TongXunGsonBean.DataBean.UserDataBean>> e) throws Exception {
                        java.lang.reflect.Type type = new TypeToken<SimpleGsonBean_new<SimplePageBean<List<ContactInfoParser.ContactInfo>>>>() {
                        }.getType();
                        new BaseRxPresenter()
                                .putParams("mobileData", GsonUtil.Bean2Json(contactInfos))
                                .doRequest("admin/userFollow/matchMobileUser", true, new AjaxCallBack<String>() {
                                    @Override
                                    public void onSuccess(String json) {
                                        TongXunGsonBean tongXunGsonBean = GsonUtil.formateJson2Bean(json, TongXunGsonBean.class);
                                        e.onNext(tongXunGsonBean.data.userData);
                                        e.onComplete();
                                        Log.i("tongxun", "onSuccess: " + GsonUtil.formatJson2String(json));
                                    }

                                    @Override
                                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                                        Log.i("tongxun", "onSuccess: " + t.getMessage());
                                    }
                                });
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

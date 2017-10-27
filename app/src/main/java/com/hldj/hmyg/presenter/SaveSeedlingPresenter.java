package com.hldj.hmyg.presenter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hldj.hmyg.CallBack.ResultCallBack;
import com.hldj.hmyg.R;
import com.hldj.hmyg.bean.Pic;
import com.hldj.hmyg.bean.SaveSeedingGsonBean;
import com.hldj.hmyg.bean.UpImageBackGsonBean;
import com.hldj.hmyg.util.ConstantState;
import com.hldj.hmyg.util.D;
import com.hldj.hmyg.util.GsonUtil;
import com.hy.utils.GetServerUrl;
import com.hy.utils.TagAdapter;
import com.white.utils.StringUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import me.shaohui.advancedluban.Luban;

import static com.zzy.common.widget.MeasureGridView.context;

/**
 * Created by Administrator on 2017/4/14.
 */

public class SaveSeedlingPresenter {


    public SaveSeedlingPresenter() {

    }


    public static void getAllDatas(ResultCallBack<SaveSeedingGsonBean> resultCallBack) {

        D.e("initAutoLayout");
        initDataGetFirstType(resultCallBack);
    }

    public static void getAllDatas_toSeed(ResultCallBack<SaveSeedingGsonBean> resultCallBack, String id) {
        D.e("initAutoLayout");
        initDataGetFirstType_toSeed(resultCallBack, id);
    }


    private static void initDataGetFirstType_toSeed(final ResultCallBack<SaveSeedingGsonBean> resultCallBack, String id) {
        // 获取json 数据
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        params.put("id", id);
        finalHttp.post(GetServerUrl.getUrl() + "admin/seedlingNote/toSeedling",
                params, new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String json) {
                        SaveSeedingGsonBean gsonBean = GsonUtil.formateJson2Bean(json, SaveSeedingGsonBean.class);
                        D.e("==========" + gsonBean.toString());
                        gsonBean.getData().getTypeList();//有5个  乔木 灌木  观景  棕榈/苏铁  地被
                        if (gsonBean.getCode().equals(ConstantState.SUCCEED_CODE)) {
                            //成功
                            D.e("===成功===");
                            resultCallBack.onSuccess(gsonBean);
                        } else {
                            D.e("===失败===" + gsonBean.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                    }
                });
    }


    private static void initDataGetFirstType(final ResultCallBack<SaveSeedingGsonBean> resultCallBack) {
        // 获取json 数据
        FinalHttp finalHttp = new FinalHttp();
        GetServerUrl.addHeaders(finalHttp, true);
        AjaxParams params = new AjaxParams();
        finalHttp.post(GetServerUrl.getUrl() + "admin/seedling/initPublish",
                params, new AjaxCallBack<String>() {
                    @Override
                    public void onSuccess(String json) {
                        SaveSeedingGsonBean gsonBean = GsonUtil.formateJson2Bean(json, SaveSeedingGsonBean.class);
                        D.e("==========" + gsonBean.toString());
                        gsonBean.getData().getTypeList();//有5个  乔木 灌木  观景  棕榈/苏铁  地被
                        if (gsonBean.getCode().equals(ConstantState.SUCCEED_CODE)) {
                            //成功
                            D.e("===成功===");
                            resultCallBack.onSuccess(gsonBean);
                        } else {
                            D.e("===失败===");
                        }
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {
                        super.onFailure(t, errorNo, strMsg);
                    }
                });
    }


    //适配器
//    static class tagadapter extends com.zhy.view.flowlayout.TagAdapter {
//
//        public tagadapter(List datas) {
//            super(datas);
//        }
//
//        @Override
//        public View getView(FlowLayout parent, int position, Object o) {
//            return null;
//        }
//    }


    //第一行 标签乔木 灌木  庄景   地被  。。。。
    private static TagAdapter<String> mtypeListAdapter;

//    public static void initAutoLayout(final TagFlowLayout mFlowLayout, List<SaveSeedingGsonBean.PurchaseItemBean_new.TypeListBean> typeListBeen, final Activity Activity, TagFlowLayout.OnTagClickListener onTagClickListener) {
//
//        final List str_typeLists = getListNames(typeListBeen);
//
//        com.zhy.view.flowlayout.TagAdapter tagAdapter = new com.zhy.view.flowlayout.TagAdapter(str_typeLists) {
//            @Override
//            public View getView(FlowLayout parent, int position, Object o) {
//                TextView tv = (TextView) Activity.getLayoutInflater().inflate(R.layout.tv, mFlowLayout, false);
//                tv.setText(str_typeLists.get(position).toString());
//                return tv;
//            }
//        };
//
//        mFlowLayout.setAdapter(tagAdapter);
//
//        mFlowLayout.setMaxSelectCount(1);
//
//        mFlowLayout.setOnTagClickListener(onTagClickListener);
//
//        tagAdapter.setSelectedList(1);
//
//        ;
//
//        D.e("=======选择之后====进行添加数据=========" + typeListBeen.get(1).getParamsList().toString());
//
//
//    }


    public static void initAutoLayout(final TagFlowLayout mFlowLayout, List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen, int index, final Activity Activity, TagFlowLayout.OnTagClickListener onTagClickListener) {

        final List str_typeLists = getListNames(typeListBeen);

        com.zhy.view.flowlayout.TagAdapter tagAdapter = new com.zhy.view.flowlayout.TagAdapter(str_typeLists) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tv = (TextView) Activity.getLayoutInflater().inflate(R.layout.tv, mFlowLayout, false);
                tv.setText(str_typeLists.get(position).toString());
                return tv;
            }
        };

        mFlowLayout.setAdapter(tagAdapter);

        mFlowLayout.setMaxSelectCount(1);

        mFlowLayout.setOnTagClickListener(onTagClickListener);

        if (index != -1) tagAdapter.setSelectedList(index);


        D.e("=======选择之后====进行添加数据=========" + typeListBeen.get(1).getParamsList().toString());


    }


    private static List getListWithKey(List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> list_enter) {
        int mSize = list_enter.size();
        List<String> lisWithtKey = new ArrayList();
        if (mSize > 0) {
            for (int i = 0; i < mSize; i++) {
                lisWithtKey.add(list_enter.get(i).getText());//获取所有名称
            }
        }
        return lisWithtKey;

    }

    private static List getListNames(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen) {
        int mSize = typeListBeen.size();
        List list = new ArrayList();
        if (mSize > 0) {
            for (int i = 0; i < mSize; i++) {
                list.add(typeListBeen.get(i).getName());//获取所有名称
            }
        }
        return list;

    }

    //     * plantTypeList : [{"text":"地栽苗","value":"planted"},{"text":"移植苗","value":"transplant"},{"text":"假植苗","value":"heelin"},{"text":"容器苗","value":"container"}]
//    public static void initAutoLayout2(final TagFlowLayout mFlowLayout, List<SaveSeedingGsonBean.PurchaseItemBean_new.TypeListBean.PlantTypeListBean> plantTypeList , final Activity saveSeedlingActivity, TagFlowLayout.OnTagClickListener onTagClickListener) {
//
//        com.zhy.view.flowlayout.TagAdapter tagAdapter = new com.zhy.view.flowlayout.TagAdapter(plantTypeList) {
//            @Override
//            public View getView(FlowLayout parent, int position, Object o) {
//                TextView tv = (TextView) saveSeedlingActivity.getLayoutInflater().inflate(R.layout.tv, mFlowLayout, false);
//                tv.setText(plantTypeList.get(position).getText());
//                return tv;
//            }
//        };
//
//        mFlowLayout.setAdapter(tagAdapter);
//
//        mFlowLayout.setMaxSelectCount(1);
//
//        mFlowLayout.setOnTagClickListener(onTagClickListener);
//
//
//    }


    //     * plantTypeList : [{"text":"地栽苗","value":"planted"},{"text":"移植苗","value":"transplant"},{"text":"假植苗","value":"heelin"},{"text":"容器苗","value":"container"}]
    public static void initAutoLayout2(final TagFlowLayout mFlowLayout, List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> plantTypeList, int index, final Activity saveSeedlingActivity, TagFlowLayout.OnTagClickListener onTagClickListener) {

        com.zhy.view.flowlayout.TagAdapter tagAdapter = new com.zhy.view.flowlayout.TagAdapter(plantTypeList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tv = (TextView) saveSeedlingActivity.getLayoutInflater().inflate(R.layout.tv, mFlowLayout, false);
                tv.setText(plantTypeList.get(position).getText());
                tv.setTag(plantTypeList.get(position).getValue());
                return tv;
            }
        };


        mFlowLayout.setAdapter(tagAdapter);

        mFlowLayout.setMaxSelectCount(1);

        mFlowLayout.setOnTagClickListener(onTagClickListener);


        if (index != -1) tagAdapter.setSelectedList(index); // 不为-1 则 不设置默认


    }


    int a = 0;

    public void upLoad(ArrayList<Pic> dataList, ResultCallBack<Pic> resultCallBack) {
        a = 0;
        int list_size = dataList.size();

        FinalHttp finalHttp = new FinalHttp();
//        Luban
//        Luban.compress(context, file)
//                .setMaxSize(500)                // limit the final image size（unit：Kb）
//                .setMaxHeight(1920)             // limit image height
//                .setMaxWidth(1080)              // limit image width
//                .putGear(Luban.CUSTOM_GEAR)     // use CUSTOM GEAR compression mode
//                .asObservable();


//        Luban.compress(context, fileList)           // 加载多张图片
//                .putGear(Luban.CUSTOM_GEAR)
//                .launch(new OnCompressListener() {
//                    @Override
//                    public void onStart() {
//
//                    }
//
//                    @Override
//                    public void onSuccess(File file) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                }); // 生成Observable<List> 返回压缩成功的所有图片结果


//        Luban.get(this)
//                .putGear(Luban.CUSTOM_GEAR)
//                .load(fileList)                     // load all images
//                .asListObservable()                 // Generates Observable <List<File>. Returns the result of all the images compressed successfully


        if (getFileList(dataList).size() > 0) {


            //上传多张本地图片
            Luban.compress(context, getFileList(dataList))
                    .setMaxSize(1024)
                    .setMaxHeight((int) (2560))
                    .setMaxWidth((int) (1440))
                    .putGear(Luban.CUSTOM_GEAR)
                    .asListObservable() // 压缩代码，返回  List<File>
                    .flatMap(new Function<List<File>, ObservableSource<File>>() {
                        @Override
                        public ObservableSource<File> apply(@NonNull List<File> files) throws Exception {
                            return Observable.fromIterable(files);
                        }
                    })
//                .flatMap(new Function<List<File>, ObservableSource<File>>() {
//                    @Override
//                    public ObservableSource<File> apply(@NonNull List<File> files) throws Exception {
//                        return Observable.fromIterator(list); // ------如何一个一个的发送 List 里面的  数据  ，并且每次延迟一秒
////                        return    Observable.just(files);
//                    }
//                })
//                .delay(1000, TimeUnit.MILLISECONDS)//延迟1秒，进行下一次上传
//                .delaySubscription(1000,TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(@NonNull File file) throws Exception {
                            D.e("========accept========path=" + file.getAbsolutePath() + "   size =" + file.length() / 1024);
                            Thread.sleep(150);
                            GetServerUrl.addHeaders(finalHttp, true);
                            finalHttp.addHeader("Content-Type", "application/octet-stream");
                            AjaxParams params1 = new AjaxParams();
                            params1.put("sourceId", "");
                            doUpLoad(file, params1, finalHttp, resultCallBack);
                        }
                    })
//                .subscribe(new Consumer<File>() {
//                    @Override
//                    public void accept(@NonNull File file) throws Exception {
//                        //执行上传代码
//                    }
//                })
//                .subscribe(new Consumer<List<File>>() {
//                    @Override
//                    public void accept(@NonNull List<File> files) throws Exception {
//
//                    }
//                })
            ;
//                .subscribe(new Consumer<List<File>>() {
//                    @Override
//                    public void accept(@NonNull List<File> files) throws Exception {
//
//                    }
//                });


//        Observable.interval(1000, TimeUnit.MILLISECONDS).


//                .delay(1000,TimeUnit.MILLISECONDS)
//                .timeInterval();
//                .flatMap(new Function<List<File>, ObservableSource<File>>() {
//                    @Override
//                    public ObservableSource<File> apply(@NonNull List<File> files) throws Exception {
//
//                        return Observable.fromArray(files);
//
////                        return Observable.create(new ObservableOnSubscribe<File>() {
////                            @Override
////                            public void subscribe(ObservableEmitter<File> e) throws Exception {
////                                Thread.sleep(1000);
////                                e.onNext();
////                            }
////                        });
//                    }
//                })
//                .delay(100, TimeUnit.MILLISECONDS)


//        GetServerUrl.addHeaders(finalHttp, true);
//        finalHttp.addHeader("Content-Type", "application/octet-stream");
//        AjaxParams params1 = new AjaxParams();
//        params1.put("sourceId", "");
//                PicCopressUtil copressUtil = new PicCopressUtil();
//                File file1 = new File(dataList.get(i).getUrl());


//                        D.e("===========开始上传图片=========\n" + i + "   图片大小：" + file1.length() / 1024 + " k ");
//                        D.e("===========图片地址=========\n" + dataList.get(i).getUrl());

        }
        for (int i = 0; i < list_size; i++) {
            if (!StringUtil.isHttpUrlPicPath(dataList.get(i).getUrl())) {//不是已经上传的图片
            } else {
                dataList.get(i).setSort(a);
                a++;
                resultCallBack.onSuccess(dataList.get(i));//如果已经上传 就直接 传回出去
            }
        }
    }


    private List<File> getFileList(List<Pic> pics) {
        List<File> files = new ArrayList<>();
        for (int i = 0; i < pics.size(); i++) {
            if (!StringUtil.isHttpUrlPicPath(pics.get(i).getUrl())) {
                files.add(new File(pics.get(i).getUrl()));
            }

        }
        return files;

    }


    public void doUpLoad(File file, AjaxParams params1, FinalHttp finalHttp, ResultCallBack resultCallBack) {
        try {
            params1.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        params1.put("imagType", "seedling");
        finalHttp.post(GetServerUrl.getUrl() + "admin/file/image", params1, new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                D.e("===========json=====上传图片成功==========\n" + json);
                UpImageBackGsonBean imageBackGsonBean = GsonUtil.formateJson2Bean(json, UpImageBackGsonBean.class);

                if (imageBackGsonBean.getCode().equals(ConstantState.SUCCEED_CODE)) {
                    D.e("===上传成功==");
                    resultCallBack.onSuccess(new Pic(imageBackGsonBean.getData().getImage().getId(), false, imageBackGsonBean.getData().getImage().getOssMediumImagePath(), a));
                } else {
                    D.e("===上传失败==");
                }

//                        urlPaths.add(a, new Pic(imageBackGsonBean.getData().getImage().getId(), false, imageBackGsonBean.getData().getImage().getOssMediumImagePath(), a));
                D.e("==========暂时使用中等大小图片==============");
                a++;
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                D.e("===========json=====失败==========" + errorNo + "  " + strMsg + " " + t.getMessage());
                resultCallBack.onFailure(t, errorNo, strMsg);
            }
        });
    }

    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }


}


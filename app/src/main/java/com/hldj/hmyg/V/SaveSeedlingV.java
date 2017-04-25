package com.hldj.hmyg.V;

import com.hldj.hmyg.bean.SaveSeedingGsonBean;

import java.util.List;

/**
 *
 * SaveSeedlingIView view
 * 接口
 * Created by Administrator on 2017/4/17.
 */

public interface SaveSeedlingV  {


    //获取本界面  所有数据
    void getAllData(SaveSeedingGsonBean saveSeedingGsonBean);

    void initAutoLayout2(List<SaveSeedingGsonBean.DataBean.TypeListBean.PlantTypeListBean> plantTypeList);

    void initAutoLayout(List<SaveSeedingGsonBean.DataBean.TypeListBean> typeListBeen);







}

package com.hldj.hmyg.util;

/**
 * 定义状态常量  服务器全局
 * Created by luocaca on 2017/4/12.
 */

public interface ConstantState {


    int LOGIN_SUCCEED = 200;

    int CHANGE_DATES = 201; //地址修改成功

    int SAVE_SUCCEED = 202;//保存成功 状态码
    int SAVE_REQUEST = 203;//保存成功 状态码
    int PUBLIC_SUCCEED = 204;//发布成功 状态码


    int DELETE_SUCCEED = 205;//采购详情界面   订单删除成功状态码

    int SEARCH_OK = 206;//搜索完成
    int FILTER_OK = 207;//筛选完成
    int ADD_SUCCEED = 207;//添加地址成功

    int FLOW_BACK = 208;// 详情界面返回 result 代码
    int STORE_OPEN_FAILD = 209;//  商店打开失败


    String ERROR_CODE = "1003";
    String SUCCEED_CODE = "1";
    String NO_NET_CODE = "110";

    String COLLECT_REFRESH = "COLLECT_REFRESH";

    boolean ON_OFF = true;//打开强制更新

    //被呼叫号码类型：
    String TYPE_OWNER = "owner";//owner(发布人)、
    String TYPE_NURSERY = "nursery";//nursery(苗圃)


}

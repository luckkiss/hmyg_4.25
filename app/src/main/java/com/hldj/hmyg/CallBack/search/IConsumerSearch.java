package com.hldj.hmyg.CallBack.search;

/**
 * Consumer  采购报价  搜索
 */

public interface IConsumerSearch extends ISearch {


    void doRefreshCount();

    void doRefreshOneFragment(int pos);//强制刷新某个 fragment  使用次接口 当 结束报价的时候。进行刷新 已结束的  fragment


}

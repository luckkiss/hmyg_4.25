package com.hldj.hmyg.CallBack.search;

/**
 * 更多 热门商家  搜索
 */

public interface IBrandShopMoreSearch extends ISearch {

    // 父类 已有 获取 searchKey 方法

    String getSearchKey();

    void doSearch();


    void doSearchByCity();
    void doSearchBySort();

}

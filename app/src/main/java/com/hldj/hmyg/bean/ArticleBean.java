package com.hldj.hmyg.bean;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class ArticleBean {

    public String id = "";
    public String href = "";
    public String title = "";
    public boolean isNew = false;

    @Override
    public String toString() {
        return "ArticleBean{" +
                "id='" + id + '\'' +
                ", href='" + href + '\'' +
                ", title='" + title + '\'' +
                ", isNew=" + isNew +
                '}';
    }
}

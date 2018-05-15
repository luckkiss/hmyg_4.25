package com.hldj.hmyg.CallBack;

/**
 * 清空  接口 实现本 接口  实现清空   某一类型 功能
 */

public interface IEmpty extends IDelete {

    abstract void doEmpty();


    /* 清空的 时候  使用  暂时弃用 */
    @Override
    public String getResourceId();



    /* 清空的 时候  使用  子类必须实现 */
    public abstract String getDomain();


}

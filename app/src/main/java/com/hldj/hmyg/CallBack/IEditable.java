package com.hldj.hmyg.CallBack;

/**
 * 可编辑的接口
 */

//extends Editable
public interface IEditable {


    /* 开启或者关闭 全选 或 全 不选 */
    public IEditable toggleSelectAll();

    public IEditable toggleEditable();


    public boolean isSelectAll();

    public boolean isEditable();

    public String getDeleteIds();


}

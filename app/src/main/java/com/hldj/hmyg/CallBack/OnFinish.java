package com.hldj.hmyg.CallBack;

/**
 * 一个结束  监听接口  true 表示成功，，false
 * 表示 失败  一般用于 结果回调
 * 关闭界面 或者刷新界面时使用
 */

public interface OnFinish {

    void onFinish(boolean b);
}

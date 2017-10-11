package com.hldj.hmyg.base.rxbus.event;

/**
 * 用于rx bus 的 post  订阅
 */
public class PostObj<T> {

    private T data;
    private int code;

    public PostObj( T t) {
//        this.code = co;
        this.data = t;
    }

    public T getData() {
        return data;
    }


    public int getCode() {
        return code;
    }


}

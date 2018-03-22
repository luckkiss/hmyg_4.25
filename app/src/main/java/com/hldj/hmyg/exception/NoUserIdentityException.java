package com.hldj.hmyg.exception;

/**
 * 没有实名认证 错误
 */

public class NoUserIdentityException extends Exception {

    public NoUserIdentityException(String msg) {
        super(msg);
    }

}

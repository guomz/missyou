package com.guomz.missyou.exception;

public class UnAuthenticatedException extends MyHttpException {
    public UnAuthenticatedException(Integer errCode) {
        super(errCode, 401);
    }
}

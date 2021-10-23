package com.guomz.missyou.exception;

public class ForbiddenException extends MyHttpException{
    public ForbiddenException(Integer errCode) {
        super(errCode, 403);
    }
}

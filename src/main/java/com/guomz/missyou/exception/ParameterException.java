package com.guomz.missyou.exception;

public class ParameterException extends MyHttpException{

    public ParameterException(Integer errCode) {
        super(errCode, 400);
    }
}

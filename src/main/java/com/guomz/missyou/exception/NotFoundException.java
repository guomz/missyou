package com.guomz.missyou.exception;

public class NotFoundException extends MyHttpException{
    public NotFoundException(Integer errCode){
        super(errCode,404);
    }
}

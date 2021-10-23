package com.guomz.missyou.exception;

public class ServerErrorException extends MyHttpException{

    public ServerErrorException(Integer errCode) {
        super(errCode, 500);
    }
}

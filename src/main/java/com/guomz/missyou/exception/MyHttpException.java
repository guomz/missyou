package com.guomz.missyou.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyHttpException extends RuntimeException{
    private Integer errCode;
    private Integer httpCode;
    public MyHttpException(Integer errCode, Integer httpCode){
        this.errCode = errCode;
        this.httpCode = httpCode;
    }
}

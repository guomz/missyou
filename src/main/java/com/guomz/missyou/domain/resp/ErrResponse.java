package com.guomz.missyou.domain.resp;

import lombok.Data;

import java.io.Serializable;
@Data
public class ErrResponse implements Serializable {

    private Integer code;
    private String message;

    public ErrResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

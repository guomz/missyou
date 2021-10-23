package com.guomz.missyou.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginTypeEnums {

    WX(0),EMAIL(1);
    private Integer code;
}

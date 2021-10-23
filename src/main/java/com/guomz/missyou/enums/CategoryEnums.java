package com.guomz.missyou.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryEnums {
    IS_ROOT(1),NOT_ROOT(0);
    private int code;
}

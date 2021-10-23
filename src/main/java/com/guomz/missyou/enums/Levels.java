package com.guomz.missyou.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Levels {

    TOUR(0),NORMAL(1),VIP(2);
    private Integer code;
}

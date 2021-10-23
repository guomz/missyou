package com.guomz.missyou.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CouponStatusEnums {

    USABLE(1), USED(2),EXPIRED(3);
    private Integer code;

    public static CouponStatusEnums getStatusEnums(Integer status){
        return Arrays.stream(CouponStatusEnums.values()).filter(item -> item.getCode().compareTo(status) ==0)
                .findFirst().orElse(null);
    }
}

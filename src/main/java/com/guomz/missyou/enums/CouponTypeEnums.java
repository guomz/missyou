package com.guomz.missyou.enums;

import com.guomz.missyou.exception.ForbiddenException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CouponTypeEnums {

    FULL_MINUS(1, "满减券"),
    NO_THRESHOLD_OFF(2,"无门槛折扣券"),
    NO_THRESHOLD_MINUS(3, "无门槛减除券"),
    FULL_OFF(4, "满金额折扣券");
    private Integer code;
    private String description;

    public static CouponTypeEnums getTypeEnum(Integer type){
        CouponTypeEnums typeEnums = Arrays.stream(CouponTypeEnums.values())
                .filter(couponTypeEnums -> couponTypeEnums.getCode().compareTo(type) == 0)
                .findFirst().orElseThrow(()->new ForbiddenException(40009));
        return typeEnums;
    }
}

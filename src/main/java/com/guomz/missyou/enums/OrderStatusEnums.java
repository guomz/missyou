package com.guomz.missyou.enums;

import com.guomz.missyou.exception.ForbiddenException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum OrderStatusEnums {

    All(0, "全部"),
    UNPAID(1, "待支付"),
    PAID(2, "已支付"),
    DELIVERED(3, "已发货"),
    FINISHED(4, "已完成"),
    CANCELED(5, "已取消"),

    // 预扣除库存不存在以下这两种情况
    PAID_BUT_OUT_OF(21, "已支付，但无货或库存不足"),
    DEAL_OUT_OF(22, "已处理缺货但支付的情况");

    private int code;
    private String description;

    public static OrderStatusEnums getOrderStatus(Integer status){
        return Arrays.stream(OrderStatusEnums.values())
                .filter(orderStatusEnums -> orderStatusEnums.getCode() == status)
                .findAny()
                .orElseThrow(()-> new ForbiddenException(50012));
    }
}

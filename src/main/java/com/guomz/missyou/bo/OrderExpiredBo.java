package com.guomz.missyou.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
public class OrderExpiredBo {

    private Long userId;
    private Long couponId;
    private Long orderId;

    public OrderExpiredBo (String expiredKey){
        String arr[] = expiredKey.split("-");
        this.userId = StringUtils.isNumeric(arr[0])? Long.parseLong(arr[0]):null;
        this.orderId = StringUtils.isNumeric(arr[1])? Long.parseLong(arr[1]):null;
        this.couponId = StringUtils.isNumeric(arr[2])? Long.parseLong(arr[2]):null;
    }
}

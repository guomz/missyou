package com.guomz.missyou.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {

    private Long couponId;
    @NotNull(message = "总价格为空")
    private BigDecimal totalPrice;
    @NotNull(message = "最终价格为空")
    private BigDecimal finalTotalPrice;
    @Valid
    @NotNull(message = "商品信息为空")
    private List<SkuDto> skuInfoList;
    @Valid
    @NotNull(message = "地址信息为空")
    private OrderAddressDto address;
}

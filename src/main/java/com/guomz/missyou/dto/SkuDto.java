package com.guomz.missyou.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SkuDto {
    @NotNull(message = "商品id为空")
    private Long id;
    @NotNull(message = "购买数量为空")
    private Integer count;
}

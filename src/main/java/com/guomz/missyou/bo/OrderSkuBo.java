package com.guomz.missyou.bo;

import com.guomz.missyou.dto.SkuDto;
import com.guomz.missyou.entity.Sku;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderSkuBo {

    private Long skuId;
    private Long categoryId;
    private Integer count;
    private BigDecimal actualPrice;

    public OrderSkuBo(Sku sku, SkuDto orderSkuDto){
        this.skuId = sku.getId();
        this.categoryId = sku.getCategoryId();
        this.count = orderSkuDto.getCount();
        this.actualPrice = sku.getActualPrice();
    }
}

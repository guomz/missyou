package com.guomz.missyou.entity;

import com.guomz.missyou.dto.SkuDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@NoArgsConstructor
@Data
public class OrderSku {

    private Long id;
    private Long spuId;
    private BigDecimal finalPrice;
    private BigDecimal singlePrice;
    private List<String> specValues;
    private Integer count;
    private String img;
    private String title;

    public OrderSku(Sku sku, SkuDto skuDto){
        this.id = sku.getId();
        this.spuId = sku.getSpuId();
        this.finalPrice = sku.getActualPrice().multiply(new BigDecimal(skuDto.getCount())).setScale(2, RoundingMode.HALF_EVEN);
        this.singlePrice = sku.getActualPrice();
        this.count = skuDto.getCount();
        this.img = sku.getImg();
        this.title = sku.getTitle();
        this.specValues = sku.getSpecValues();
    }
}

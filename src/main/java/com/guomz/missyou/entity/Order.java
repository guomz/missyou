package com.guomz.missyou.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.guomz.missyou.dto.OrderAddressDto;
import com.guomz.missyou.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNo;

    private Long userId;

    private BigDecimal totalPrice;

    private Integer totalCount;

    private Date createTime;

    private Date deleteTime;

    private Date updateTime;

    private Date expiredTime;

    private Date placedTime;

    private String snapImg;

    private String snapTitle;

    private String snapItems;

    private String snapAddress;

    private String prepayId;

    private BigDecimal finalTotalPrice;

    private Integer status;

    private Integer period;

    public void setSnapItems(List<OrderSku> orderSkuList){
        this.snapItems = JsonUtil.objectToJson(orderSkuList);
    }

    public List<OrderSku> getSnapItems(){
        return JsonUtil.jsonToObject(this.snapItems, new TypeReference<List<OrderSku>>() {});
    }

    public OrderAddressDto getSnapAddress() {
        return JsonUtil.jsonToObject(this.snapAddress, new TypeReference<OrderAddressDto>() {});
    }

    public void setSnapAddress(OrderAddressDto snapAddress) {
        this.snapAddress = JsonUtil.objectToJson(snapAddress);
    }
}
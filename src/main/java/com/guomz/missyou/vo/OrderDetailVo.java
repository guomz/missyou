package com.guomz.missyou.vo;

import com.guomz.missyou.entity.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class OrderDetailVo extends Order {

    public OrderDetailVo(Order order){
        BeanUtils.copyProperties(order, this, "snapItems", "snapAddress");
        this.setSnapItems(order.getSnapItems());
        this.setSnapAddress(order.getSnapAddress());
    }
}

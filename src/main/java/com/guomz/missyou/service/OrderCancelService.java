package com.guomz.missyou.service;

import com.guomz.missyou.bo.OrderExpiredBo;
import com.guomz.missyou.entity.Order;
import com.guomz.missyou.entity.OrderSku;
import com.guomz.missyou.enums.OrderStatusEnums;
import com.guomz.missyou.exception.NotFoundException;
import com.guomz.missyou.exception.ServerErrorException;
import com.guomz.missyou.repository.OrderRepository;
import com.guomz.missyou.repository.SkuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderCancelService {

    @Resource
    private OrderRepository orderRepository;
    @Resource
    private SkuRepository skuRepository;

    /**
     * 归还订单
     * @param orderExpiredBo
     */
    @Transactional
    public void cancelOrder(OrderExpiredBo orderExpiredBo){
        //查找订单
        Order order = orderRepository.findByIdAndUserId(orderExpiredBo.getOrderId(), orderExpiredBo.getUserId())
                .orElseThrow(()-> new NotFoundException(50009));
        if (order.getStatus().equals(OrderStatusEnums.PAID)){
            log.info("订单已支付");
            return;
        }
        //更改订单状态 未支付-已取消
        int cancelResult = orderRepository.cancelOrder(orderExpiredBo.getOrderId());
        if (cancelResult != 1){
            log.error("订单取消失败");
            throw new ServerErrorException(50000);
        }
        //归还库存
        for (OrderSku orderSku : order.getSnapItems()) {
            skuRepository.backStock(orderSku.getId(), orderSku.getCount());
        }
    }
}

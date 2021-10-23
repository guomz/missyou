package com.guomz.missyou.listener;

import com.guomz.missyou.bo.OrderExpiredBo;
import com.guomz.missyou.service.CouponBackService;
import com.guomz.missyou.service.OrderCancelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisExpiredKeyListener extends KeyExpirationEventMessageListener {

    @Autowired
    private OrderCancelService orderCancelService;
    @Autowired
    private CouponBackService couponBackService;


    public RedisExpiredKeyListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    protected void doRegister(RedisMessageListenerContainer listenerContainer) {
        //确认监听类型，默认为监听所有
        Topic topic = new PatternTopic("__keyevent@0__:expired");
        listenerContainer.addMessageListener(this, topic);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("消息：{}", message.toString());
        OrderExpiredBo orderExpiredBo = new OrderExpiredBo(message.toString());
        if (orderExpiredBo.getUserId() == null || orderExpiredBo.getOrderId() == null){
            log.warn("消息 {} 非订单过期，不处理", message);
            return;
        }
        //取消订单
        orderCancelService.cancelOrder(orderExpiredBo);
        couponBackService.backCoupon(orderExpiredBo);
    }
}

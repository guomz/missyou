package com.guomz.missyou.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
public class OrderRepositoryTest {

    @Resource
    private OrderRepository orderRepository;

    @Test
    @Transactional
    public void testPay(){
        Date now  = new Date();
        Long userId = 34L;
        Long orderId = 275L;
        int result = orderRepository.payOrder(userId,orderId, now);
        System.out.println(result);
    }
}

package com.guomz.missyou.task;

import com.guomz.missyou.scheduled_task.CouponExpiredTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CouponTaskTest {

    @Autowired
    private CouponExpiredTask couponExpiredTask;

    @Test
    public void testExpired(){
        couponExpiredTask.checkExpired();
    }
}

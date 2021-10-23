package com.guomz.missyou.service;

import com.guomz.missyou.bo.OrderExpiredBo;
import com.guomz.missyou.entity.UserCoupon;
import com.guomz.missyou.exception.NotFoundException;
import com.guomz.missyou.exception.ServerErrorException;
import com.guomz.missyou.repository.UserCouponRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class CouponBackService {

    @Resource
    private UserCouponRepository userCouponRepository;

    @Transactional
    public void backCoupon(OrderExpiredBo orderExpiredBo){
        //查找使用记录
        if (orderExpiredBo.getCouponId() == null){
            return;
        }
        UserCoupon userCoupon = userCouponRepository.findByUserIdAndCouponId(orderExpiredBo.getUserId(), orderExpiredBo.getCouponId())
                .orElseThrow(()-> new NotFoundException(40014));
        //更改领取状态 已领取-未领取
        int result = userCouponRepository.backCoupon(orderExpiredBo.getUserId(), orderExpiredBo.getCouponId(), new Date());
        if (result != 1){
            log.error("归还优惠券失败");
            throw new ServerErrorException(40015);
        }
    }
}

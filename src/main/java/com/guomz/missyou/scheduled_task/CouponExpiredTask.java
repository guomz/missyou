package com.guomz.missyou.scheduled_task;

import com.guomz.missyou.entity.Coupon;
import com.guomz.missyou.entity.UserCoupon;
import com.guomz.missyou.enums.CouponStatusEnums;
import com.guomz.missyou.repository.CouponRepository;
import com.guomz.missyou.repository.UserCouponRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CouponExpiredTask {

    @Resource
    private CouponRepository couponRepository;
    @Resource
    private UserCouponRepository userCouponRepository;

    /**
     * 每天零点五分执行
     */
    @Scheduled(cron = "0 5 0 * * ? ")
    public void checkExpired(){
        log.info("优惠券过期定时任务开始");
        Date now = new Date();
        //找出全部状态为可使用的领取记录与优惠券
        List<UserCoupon> userCouponList = userCouponRepository.findByStatus(CouponStatusEnums.USABLE.getCode());
        Map<Long, Coupon> couponMap = findCoupons(userCouponList.stream()
                .map(UserCoupon::getCouponId).distinct().collect(Collectors.toList()));
        for (UserCoupon userCoupon : userCouponList) {
            Coupon coupon = couponMap.get(userCoupon.getCouponId());
            if (coupon.getEndTime() != null && now.after(coupon.getEndTime())){
                log.info("优惠券已过期: {}, {}",userCoupon.getUserId(),userCoupon.getCouponId());
                userCoupon.setStatus(CouponStatusEnums.EXPIRED.getCode());
                userCoupon.setUpdateTime(now);
                userCouponRepository.save(userCoupon);
            }
        }
        log.info("优惠券过期定时任务结束");
    }

    /**
     * 找出对应的优惠券
     * @param idList
     * @return
     */
    private Map<Long, Coupon> findCoupons(List<Long> idList){
        if (idList.isEmpty()){
            return new HashMap<>();
        }
        List<Coupon> couponList = couponRepository.findByIdIn(idList);
        return couponList.stream().collect(Collectors.toMap(Coupon::getId, Function.identity(), (k1,k2) -> k2));
    }
}

package com.guomz.missyou.service;

import com.guomz.missyou.entity.Activity;
import com.guomz.missyou.entity.Coupon;
import com.guomz.missyou.entity.UserCoupon;
import com.guomz.missyou.enums.CouponStatusEnums;
import com.guomz.missyou.exception.ForbiddenException;
import com.guomz.missyou.exception.NotFoundException;
import com.guomz.missyou.repository.ActivityRepository;
import com.guomz.missyou.repository.CouponRepository;
import com.guomz.missyou.repository.UserCouponRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CouponService {

    @Resource
    private CouponRepository couponRepository;
    @Resource
    private ActivityRepository activityRepository;
    @Resource
    private UserCouponRepository userCouponRepository;

    public List<Coupon> findCouponsByCategory(Long cid){
        List<Coupon> couponList = couponRepository.findByCategory(cid, new Date());
        return couponList;
    }

    public List<Coupon> findWholeStoreCoupon(){
        List<Coupon> couponList = couponRepository.findWholeStore(new Date());
        return couponList;
    }

    public List<Coupon> findUsableCoupons(Long userId){
        List<Coupon> couponList = couponRepository.findUsableCoupons(userId, new Date());
        return couponList;
    }

    public List<Coupon> findUsedCoupons(Long userId){
        List<Coupon> couponList = couponRepository.findUsedCoupons(userId);
        return couponList;
    }

    public List<Coupon> findExpiredCoupons(Long userId){
        List<Coupon> couponList = couponRepository.findExpiredCoupons(userId, new Date());
        return couponList;
    }

    /**
     * 领取优惠券
     * @param userId
     * @param couponId
     */
    public void collectCoupon(Long userId, Long couponId){
        Date now = new Date();
        //检查优惠券是否存在且能领取
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(()-> new NotFoundException(40004));
        Activity activity = activityRepository.findByCouponsId(couponId)
                .orElseThrow(()->new NotFoundException(40010));
        if (activity.getStartTime().after(now) || activity.getEndTime().before(now)){
            log.error("优惠券当前不可领取");
            throw new ForbiddenException(40013);
        }
        //检查是否领取过
        Boolean isCollected = userCouponRepository.findByUserIdAndCouponId(userId, couponId)
                .isPresent();
        if (isCollected){
            log.error("优惠券已领取");
            throw new ForbiddenException(40006);
        }
        //插入领取记录
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userId);
        userCoupon.setCouponId(couponId);
        userCoupon.setCreateTime(now);
        userCoupon.setUpdateTime(now);
        userCoupon.setStatus(CouponStatusEnums.USABLE.getCode());
        userCouponRepository.save(userCoupon);
    }
}

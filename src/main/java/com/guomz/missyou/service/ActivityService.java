package com.guomz.missyou.service;

import com.guomz.missyou.entity.Activity;
import com.guomz.missyou.exception.NotFoundException;
import com.guomz.missyou.repository.ActivityRepository;
import com.guomz.missyou.vo.ActivitySimplifyVo;
import com.guomz.missyou.vo.CouponSimplifyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActivityService {

    @Resource
    private ActivityRepository activityRepository;

    public ActivitySimplifyVo getActivityByName(String name){
        Activity activity = activityRepository.findByName(name);
        if (activity == null){
            log.error("活动不存在");
            throw new NotFoundException(10002);
        }
        ActivitySimplifyVo activitySimplifyVo = new ActivitySimplifyVo();
        BeanUtils.copyProperties(activity, activitySimplifyVo);
        return activitySimplifyVo;
    }

    public ActivitySimplifyVo getActivityByNameWithCoupon(String name){
        Activity activity = activityRepository.findByName(name);
        if (activity == null){
            log.error("活动不存在");
            throw new NotFoundException(10002);
        }
        ActivitySimplifyVo activitySimplifyVo = new ActivitySimplifyVo();
        BeanUtils.copyProperties(activity, activitySimplifyVo);
        List<CouponSimplifyVo> couponSimplifyVoList = activity.getCoupons().stream()
                .map(coupon -> new CouponSimplifyVo(coupon)).collect(Collectors.toList());
        activitySimplifyVo.setCoupons(couponSimplifyVoList);
        return activitySimplifyVo;
    }
}

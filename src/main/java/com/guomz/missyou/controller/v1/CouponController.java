package com.guomz.missyou.controller.v1;

import com.guomz.missyou.annotations.ScopeLevel;
import com.guomz.missyou.domain.resp.ErrResponse;
import com.guomz.missyou.entity.Coupon;
import com.guomz.missyou.enums.CouponStatusEnums;
import com.guomz.missyou.exception.ParameterException;
import com.guomz.missyou.service.CouponService;
import com.guomz.missyou.util.LocalUserUtil;
import com.guomz.missyou.vo.CouponCategoryVo;
import com.guomz.missyou.vo.CouponSimplifyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/coupon")
@Api(tags = "优惠券接口")
@Slf4j
public class CouponController {

    @Autowired
    private CouponService couponService;

    @ApiOperation("根据二级分类查找优惠券")
    @GetMapping("/by/category/{cid}")
    public List<CouponSimplifyVo> getByCategory(@PathVariable("cid") @NotNull(message = "分类id为空") Long cid) {
        List<CouponSimplifyVo> couponSimplifyVoList = couponService.findCouponsByCategory(cid).stream()
                .map(coupon -> new CouponSimplifyVo(coupon)).collect(Collectors.toList());
        return couponSimplifyVoList;
    }

    @ApiOperation("查找全部全场券")
    @GetMapping("/whole_store")
    public List<CouponSimplifyVo> getWholeStoreCoupon() {
        List<CouponSimplifyVo> couponSimplifyVoList = couponService.findWholeStoreCoupon().stream()
                .map(coupon -> new CouponSimplifyVo(coupon)).collect(Collectors.toList());
        return couponSimplifyVoList;
    }

    @ScopeLevel(1)
    @ApiOperation("按状态找出我的优惠券")
    @GetMapping("/myself/by/status/{status}")
    public List<CouponSimplifyVo> getCouponsByStatus(@PathVariable("status") Integer status) {
        Long userId = LocalUserUtil.getUserId();
        CouponStatusEnums statusEnum = CouponStatusEnums.getStatusEnums(status);
        if (statusEnum == null) {
            log.error("优惠券状态不正确");
            throw new ParameterException(40000);
        }
        List<Coupon> couponList;
        switch (statusEnum) {
            case USED:
                couponList = couponService.findUsedCoupons(userId);
                return couponList.stream().map(coupon -> new CouponSimplifyVo(coupon)).collect(Collectors.toList());
            case USABLE:
                couponList = couponService.findUsableCoupons(userId);
                return couponList.stream().map(coupon -> new CouponSimplifyVo(coupon)).collect(Collectors.toList());
            case EXPIRED:
                couponList = couponService.findExpiredCoupons(userId);
                return couponList.stream().map(coupon -> new CouponSimplifyVo(coupon)).collect(Collectors.toList());
            default:
                log.error("优惠券状态不正确");
                throw new ParameterException(40000);
        }
    }

    @ScopeLevel(1)
    @ApiOperation("查找我的可用优惠券带分类信息")
    @GetMapping("/myself/available/with_category")
    public List<CouponCategoryVo> getUsableCouponsWithCategories(){
        Long userId = LocalUserUtil.getUserId();
        List<CouponCategoryVo> couponCategoryVoList = couponService.findUsableCoupons(userId).stream()
                .map(coupon -> new CouponCategoryVo(coupon)).collect(Collectors.toList());
        return couponCategoryVoList;
    }

    @ScopeLevel(1)
    @ApiOperation("领取优惠券")
    @PostMapping("/collect/{id}")
    public ErrResponse collectCoupon(@PathVariable("id") Long couponId){
        Long userId = LocalUserUtil.getUserId();
        couponService.collectCoupon(userId,couponId);
        return new ErrResponse(0,"ok");
    }
}

package com.guomz.missyou.service;

import com.guomz.missyou.bo.OrderSkuBo;
import com.guomz.missyou.entity.Category;
import com.guomz.missyou.entity.Coupon;
import com.guomz.missyou.entity.UserCoupon;
import com.guomz.missyou.enums.CouponTypeEnums;
import com.guomz.missyou.exception.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 检查优惠券是否可用
 */
public class CouponChecker {

    Logger logger = LoggerFactory.getLogger(CouponChecker.class);
    private Coupon coupon;
    private UserCoupon userCoupon;
    private Boolean hasCoupon = false;

    public CouponChecker(Coupon coupon, UserCoupon userCoupon){
        this.coupon = coupon;
        this.userCoupon = userCoupon;
        this.hasCoupon = true;
    }

    public CouponChecker(){
        this.hasCoupon = false;
    }

    public Boolean hasCoupon(){
        return this.hasCoupon;
    }

    /**
     * 检查优惠券是否在可用时间内
     * @param now
     */
    public void checkCouponExpired(Date now){
        if (now.before(this.coupon.getStartTime()) || now.after(this.coupon.getEndTime())){
            logger.error("优惠券不在使用时间内");
            throw new ForbiddenException(40008);
        }
    }

    /**
     * 检查金额
     * @param orderFinalTotalPrice 前端传入最终金额
     * @param serverTotalPrice 服务器计算的未含优惠券总金额
     */
    public void checkPrice(BigDecimal orderFinalTotalPrice,BigDecimal serverTotalPrice){
        CouponTypeEnums couponType = CouponTypeEnums.getTypeEnum(this.coupon.getType());
        BigDecimal serverFinalTotalPrice;
        switch (couponType){
            case FULL_OFF:
                serverFinalTotalPrice = serverTotalPrice.multiply(this.coupon.getRate()).setScale(2, RoundingMode.UP);
                break;
            case FULL_MINUS:
                serverFinalTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
                break;
            case NO_THRESHOLD_MINUS:
                serverFinalTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
                if (serverFinalTotalPrice.compareTo(BigDecimal.ZERO) == 0){
                    logger.error("金额不正确");
                    throw new ForbiddenException(50005);
                }
                break;
            case NO_THRESHOLD_OFF:
                serverFinalTotalPrice = serverTotalPrice.multiply(this.coupon.getRate()).setScale(2, RoundingMode.UP);
                break;
            default:
                throw new ForbiddenException(40009);
        }
        //校验前端金额与计算金额
        if (orderFinalTotalPrice.compareTo(serverFinalTotalPrice) != 0){
            logger.error("金额不正确: {},{}", orderFinalTotalPrice, serverFinalTotalPrice);
            throw new ForbiddenException(50005);
        }
    }

    /**
     * 检查优惠券分类
     * @param skuBoList
     */
    public void canBeUsed(List<OrderSkuBo> skuBoList, BigDecimal serverTotalPrice){
        BigDecimal categoryTotalPrice;
        if (this.coupon.getWholeStore()){
            //全场券
            categoryTotalPrice = serverTotalPrice;
        }else {
            //找出分类计算总金额
            List<Long> categoryIdList = this.coupon.getCategories().stream()
                    .map(Category::getId).distinct().collect(Collectors.toList());
            categoryTotalPrice = skuBoList.stream()
                    .filter(orderSkuBo -> categoryIdList.contains(orderSkuBo.getCategoryId()))
                    .map(orderSkuBo -> orderSkuBo.getActualPrice().multiply(new BigDecimal(orderSkuBo.getCount())).setScale(2, RoundingMode.UP))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        //判断优惠券满减金额
        if (this.coupon.getType().equals(CouponTypeEnums.FULL_OFF.getCode())
                || this.coupon.getType().equals(CouponTypeEnums.FULL_MINUS.getCode())) {

            if (this.coupon.getFullMoney() != null
                    && this.coupon.getFullMoney().compareTo(categoryTotalPrice)>0){
                logger.error("未达到优惠券使用条件");
                throw new ForbiddenException(40008);
            }
        }
    }
}

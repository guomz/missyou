package com.guomz.missyou.vo;

import com.guomz.missyou.entity.Coupon;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CouponSimplifyVo {
    private Long id;

    private String title;

    private Date startTime;

    private Date endTime;

    private String description;

    private BigDecimal fullMoney;

    private BigDecimal minus;

    private BigDecimal rate;

    private Integer type;

    private Long activityId;

    private String remark;

    private Boolean wholeStore;

    public CouponSimplifyVo(Coupon coupon){
        BeanUtils.copyProperties(coupon, this);
    }
}

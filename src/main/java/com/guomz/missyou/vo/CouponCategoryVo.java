package com.guomz.missyou.vo;

import com.guomz.missyou.entity.Coupon;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CouponCategoryVo {
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

    private List<CategorySimplifyVo> categories;

    public CouponCategoryVo(Coupon coupon){
        BeanUtils.copyProperties(coupon, this);
        this.categories = coupon.getCategories().stream()
                .map(category -> {
                    CategorySimplifyVo categorySimplifyVo = new CategorySimplifyVo();
                    BeanUtils.copyProperties(category, categorySimplifyVo);
                    return categorySimplifyVo;
                }).collect(Collectors.toList());
    }
}

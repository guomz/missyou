package com.guomz.missyou.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ActivitySimplifyVo {
    private Long id;

    private String title;

    private String description;

    private Date startTime;

    private Date endTime;

    private String remark;

    private Integer online;

    private String entranceImg;

    private String internalTopImg;

    private List<CouponSimplifyVo> coupons;
}
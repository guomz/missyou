package com.guomz.missyou.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "coupon")
@Where(clause = "delete_time is null")
public class Coupon {

    @Id
    private Long id;

    private String title;

    private Date startTime;

    private Date endTime;

    private String description;

    private BigDecimal fullMoney;

    private BigDecimal minus;

    private BigDecimal rate;

    private Integer type;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private Long activityId;

    private String remark;

    private Boolean wholeStore;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy="coupons")
    private List<Category> categories;
}
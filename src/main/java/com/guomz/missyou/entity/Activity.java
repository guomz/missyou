package com.guomz.missyou.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "activity")
@Where(clause = "delete_time is null and online = 1")
public class Activity {

    @Id
    private Long id;

    private String title;

    private String description;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private String remark;

    private Integer online;

    private String entranceImg;

    private String internalTopImg;

    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "activityId", referencedColumnName = "id")
    private List<Coupon> coupons;
}
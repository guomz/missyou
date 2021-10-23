package com.guomz.missyou.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "category")
@Where(clause = "delete_time is null and online = 1")
public class Category {
    @Id
    private Long id;

    private String name;

    private String description;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private Integer isRoot;

    private Long parentId;

    private String img;

    private Integer index;

    private Integer online;

    private Integer level;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "coupon_category", joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private List<Coupon> coupons;
}
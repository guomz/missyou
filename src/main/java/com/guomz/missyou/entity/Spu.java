package com.guomz.missyou.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "spu")
@Where(clause = "delete_time is null and online = 1")
public class Spu {
    @Id
    private Long id;

    private String title;

    private String subtitle;

    private Long categoryId;

    private Long rootCategoryId;

    private Integer online;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private String price;

    private Integer sketchSpecId;

    private Integer defaultSkuId;

    private String img;

    private String discountPrice;

    private String description;

    private String tags;

    private Integer isTest;

    private String forThemeImg;

    private String spuThemeImg;

    @OneToMany
    @JoinColumn(name = "spuId")
    private List<Sku> skuList;

    @OneToMany
    @JoinColumn(name = "spuId")
    private List<SpuImg> spuImgList;

    @OneToMany
    @JoinColumn(name = "spuId")
    private List<SpuDetailImg> spuDetailImgList;
}
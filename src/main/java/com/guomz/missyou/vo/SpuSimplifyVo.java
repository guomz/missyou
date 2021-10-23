package com.guomz.missyou.vo;

import lombok.Data;

@Data
public class SpuSimplifyVo {

    private Long id;

    private String title;

    private String subtitle;

    private Integer categoryId;

    private Integer rootCategoryId;

    private Integer online;

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

}

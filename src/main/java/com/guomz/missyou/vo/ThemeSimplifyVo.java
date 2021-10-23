package com.guomz.missyou.vo;

import lombok.Data;

import java.util.List;

@Data
public class ThemeSimplifyVo {
    private Long id;

    private String title;

    private String description;

    private String name;

    private String tplName;

    private String entranceImg;

    private String extend;

    private String internalTopImg;

    private String titleImg;

    private Integer online;

    private List<SpuSimplifyVo> spuList;
}

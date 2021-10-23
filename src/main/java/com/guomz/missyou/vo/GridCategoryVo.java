package com.guomz.missyou.vo;

import lombok.Data;

@Data
public class GridCategoryVo {
    private Long id;

    private String title;

    private String img;

    private String name;

    private Long categoryId;

    private Long rootCategoryId;
}

package com.guomz.missyou.vo;

import lombok.Data;

@Data
public class CategorySimplifyVo {
    private Long id;

    private String name;

    private Integer isRoot;

    private Long parentId;

    private String img;

    private Integer index;

}

package com.guomz.missyou.vo;

import lombok.Data;

import java.util.List;

@Data
public class AllCategoryVo {

    private List<CategorySimplifyVo> roots;

    private List<CategorySimplifyVo> subs;

    public AllCategoryVo(List<CategorySimplifyVo> roots, List<CategorySimplifyVo> subs){
        this.roots = roots;
        this.subs = subs;
    }
}

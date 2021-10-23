package com.guomz.missyou.service;

import com.guomz.missyou.entity.Category;
import com.guomz.missyou.enums.CategoryEnums;
import com.guomz.missyou.repository.CategoryRepository;
import com.guomz.missyou.util.CopyUtil;
import com.guomz.missyou.vo.AllCategoryVo;
import com.guomz.missyou.vo.CategorySimplifyVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    @Resource
    private CategoryRepository categoryRepository;

    public AllCategoryVo getAllCategory(){
        List<Category> rootCategoryList = categoryRepository.findByIsRoot(CategoryEnums.IS_ROOT.getCode());
        List<Category> subCategoryList = categoryRepository.findByIsRoot(CategoryEnums.NOT_ROOT.getCode());

        return new AllCategoryVo(CopyUtil.copyList(rootCategoryList, CategorySimplifyVo::new),
                CopyUtil.copyList(subCategoryList, CategorySimplifyVo::new));
    }
}

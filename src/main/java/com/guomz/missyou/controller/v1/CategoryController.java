package com.guomz.missyou.controller.v1;

import com.guomz.missyou.service.CategoryService;
import com.guomz.missyou.service.GridCategoryService;
import com.guomz.missyou.vo.AllCategoryVo;
import com.guomz.missyou.vo.GridCategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/category")
@Api(tags = "分类接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private GridCategoryService gridCategoryService;

    @GetMapping("/all")
    @ApiOperation("获取全部分类")
    public AllCategoryVo getAllCategory(){
        return categoryService.getAllCategory();
    }

    @ApiOperation("获取全部宫格分类")
    @GetMapping("/grid/all")
    public List<GridCategoryVo> getAllGridCategory(){
        return gridCategoryService.getAllGridCategory();
    }
}

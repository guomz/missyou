package com.guomz.missyou.controller.v1;

import com.guomz.missyou.service.SearchService;
import com.guomz.missyou.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/search")
@Api(tags = "搜索接口")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @ApiOperation("按照简介查询商品")
    @GetMapping()
    public PageResult getSpusByTitle(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                     @RequestParam(value = "count", required = false, defaultValue = "5") Integer count,
                                     @RequestParam(value = "q", required = false, defaultValue = "") String title){
        return searchService.searchByTitle(start, count, "%"+title+"%");
    }
}

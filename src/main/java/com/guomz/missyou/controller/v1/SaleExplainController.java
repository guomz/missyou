package com.guomz.missyou.controller.v1;

import com.guomz.missyou.entity.SaleExplain;
import com.guomz.missyou.service.SaleExplainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/sale_explain")
@Api(tags = "商品额外信息接口")
public class SaleExplainController {

    @Autowired
    private SaleExplainService saleExplainService;

    @GetMapping("/fixed")
    @ApiOperation("获取全部额外信息")
    public List<SaleExplain> getAllExplains(){
        return saleExplainService.getAllExplains();
    }
}

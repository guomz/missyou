package com.guomz.missyou.controller.v1;

import com.guomz.missyou.entity.Sku;
import com.guomz.missyou.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/sku")
@Api(tags = "sku接口")
@Validated
public class SkuController {

    @Autowired
    private SkuService skuService;

    @GetMapping
    @ApiOperation("根据id查询一组sku")
    public List<Sku> getSkuByIds(@RequestParam(value = "ids", required = false) @NotBlank(message = "id为空") String idStr){
        List<Long> ids = Arrays.stream(idStr.split(",")).map(str -> Long.parseLong(str)).collect(Collectors.toList());
        return skuService.getSkuByIds(ids);
    }
}

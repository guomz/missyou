package com.guomz.missyou.controller.v1;

import com.guomz.missyou.entity.Spu;
import com.guomz.missyou.service.SpuService;
import com.guomz.missyou.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/spu")
@Validated
@Api(tags = "商品接口")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @ApiOperation("查询商品详情")
    @RequestMapping(value = "/id/{id}/detail", method = RequestMethod.GET)
    public Spu getSpuDetail(@PathVariable("id") @NotNull(message = "商品id为空") Long id){
        return spuService.getSpuDetail(id);
    }

    @ApiOperation("查询最新商品")
    @GetMapping("/latest")
    public PageResult getLatestSpu(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                   @RequestParam(value = "count", required = false, defaultValue = "5") Integer count){
        return spuService.getLatestSpu(start, count);
    }

    @GetMapping("/by/category/{id}")
    public PageResult getByCategory(@PathVariable("id") @NotNull(message = "id为空") Long id,
                                    @RequestParam(value = "start", required = false, defaultValue = "0")Integer start,
                                    @RequestParam(value = "count", required = false, defaultValue = "5")Integer count,
                                    @RequestParam(value = "is_root", required = false, defaultValue = "false") Boolean isRoot){
        return spuService.getSpuByCategory(start,count,id,isRoot);
    }
}

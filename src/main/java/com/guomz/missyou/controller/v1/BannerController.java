package com.guomz.missyou.controller.v1;

import com.guomz.missyou.entity.Banner;
import com.guomz.missyou.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/banner")
@Api(tags = "banner接口")
@Validated
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @ApiOperation("根据id查询")
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public Banner getBannerById(@PathVariable("id") @NotNull(message = "id为空") Long id){
        Banner banner = bannerService.getByBannerId(id);
        return banner;
    }

    @ApiOperation("根据名称查询")
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public Banner getBannerByName(@PathVariable("name") @NotBlank(message = "名称为空") String name){
        Banner banner = bannerService.getByBannerName(name);
        return banner;
    }
}

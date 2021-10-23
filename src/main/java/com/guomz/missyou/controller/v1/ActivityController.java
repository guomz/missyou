package com.guomz.missyou.controller.v1;

import com.guomz.missyou.service.ActivityService;
import com.guomz.missyou.vo.ActivitySimplifyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/v1/activity")
@Api(tags = "活动接口")
@Validated
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @ApiOperation("按名称获取活动")
    @GetMapping("/name/{name}")
    public ActivitySimplifyVo getByName(@PathVariable("name") @NotBlank(message = "名称为空") String name){
        return activityService.getActivityByName(name);
    }

    @ApiOperation("按名称获取活动包含优惠券")
    @GetMapping("/name/{name}/with_coupon")
    public ActivitySimplifyVo getByNameWithCoupon(@PathVariable("name") @NotBlank(message = "名称为空") String name){
        return activityService.getActivityByNameWithCoupon(name);
    }
}

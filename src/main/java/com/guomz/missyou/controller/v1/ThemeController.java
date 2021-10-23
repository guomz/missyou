package com.guomz.missyou.controller.v1;

import com.guomz.missyou.entity.Theme;
import com.guomz.missyou.service.ThemeService;
import com.guomz.missyou.vo.ThemeSimplifyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1/theme")
@Api(tags = "主题接口")
@Validated
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    @RequestMapping(value = "/by/names", method = RequestMethod.GET)
    @ApiOperation("根据名称获取一组主题")
    public List<ThemeSimplifyVo> getThemesByNames(@RequestParam(value = "names", required = false) @NotBlank(message = "名称为空") String names){
        return themeService.getByNames(Arrays.asList(names.split(",")));
    }

    @RequestMapping(value = "/name/{name}/with_spu", method = RequestMethod.GET)
    @ApiOperation("根据名称获取主题带spu")
    public ThemeSimplifyVo getThemeByNameWithSpu(@PathVariable("name") @NotBlank(message = "名称为空") String name){
        return themeService.getByNameWithSpu(name);
    }
}

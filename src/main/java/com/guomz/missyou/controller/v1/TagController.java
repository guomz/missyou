package com.guomz.missyou.controller.v1;

import com.guomz.missyou.entity.Tag;
import com.guomz.missyou.service.TagService;
import com.guomz.missyou.vo.TagSimplifyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/tag")
@Api(tags = "标签接口")
@Validated
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation("根据类型找tag")
    @GetMapping("/type/{type}")
    public List<TagSimplifyVo> getTagsByType(@PathVariable("type") @NotNull(message = "类型为空") Integer type){
        List<Tag> tagList = tagService.getByType(type);
        return tagList.stream().map(tag -> new TagSimplifyVo(tag)).collect(Collectors.toList());
    }
}

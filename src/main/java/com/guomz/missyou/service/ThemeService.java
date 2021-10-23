package com.guomz.missyou.service;

import com.guomz.missyou.entity.Theme;
import com.guomz.missyou.exception.NotFoundException;
import com.guomz.missyou.repository.ThemeRepository;
import com.guomz.missyou.util.CopyUtil;
import com.guomz.missyou.vo.SpuSimplifyVo;
import com.guomz.missyou.vo.ThemeSimplifyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ThemeService {

    @Resource
    private ThemeRepository themeRepository;

    public List<ThemeSimplifyVo> getByNames(List<String> nameList){
        List<Theme> themeList = themeRepository.findThemesByNameIn(nameList);
        return themeList.stream().map(theme -> {
            ThemeSimplifyVo themeSimplifyVo = new ThemeSimplifyVo();
            BeanUtils.copyProperties(theme, themeSimplifyVo);
            return themeSimplifyVo;
        }).collect(Collectors.toList());
    }

    public ThemeSimplifyVo getByNameWithSpu(String name){
        Theme theme = themeRepository.findThemeByName(name);
        if (theme == null){
            log.error("主题不存在");
            throw new NotFoundException(10002);
        }

        ThemeSimplifyVo themeSimplifyVo = new ThemeSimplifyVo();
        BeanUtils.copyProperties(theme, themeSimplifyVo);
        themeSimplifyVo.setSpuList(CopyUtil.copyList(theme.getSpuList(), SpuSimplifyVo::new));
        return themeSimplifyVo;
    }
}

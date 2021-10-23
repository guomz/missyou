package com.guomz.missyou.service;

import com.guomz.missyou.entity.GridCategory;
import com.guomz.missyou.repository.GridCategoryRepository;
import com.guomz.missyou.util.CopyUtil;
import com.guomz.missyou.vo.GridCategoryVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GridCategoryService {

    @Resource
    private GridCategoryRepository gridCategoryRepository;

    public List<GridCategoryVo> getAllGridCategory(){
        List<GridCategory> gridCategoryList = gridCategoryRepository.findAll();
        return CopyUtil.copyList(gridCategoryList, GridCategoryVo::new);
    }
}

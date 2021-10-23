package com.guomz.missyou.service;

import com.guomz.missyou.entity.Spu;
import com.guomz.missyou.repository.SpuRepository;
import com.guomz.missyou.util.PageUtil;
import com.guomz.missyou.vo.PageResult;
import com.guomz.missyou.vo.SpuSimplifyVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SearchService {

    @Resource
    private SpuRepository spuRepository;

    public PageResult<Spu, SpuSimplifyVo> searchByTitle(Integer start, Integer count, String title){
        Pageable pageable = PageUtil.getPage(start,count, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Spu> spuPage = spuRepository.findByTitleLike(title, pageable);
        return new PageResult<>(spuPage, SpuSimplifyVo::new);
    }
}

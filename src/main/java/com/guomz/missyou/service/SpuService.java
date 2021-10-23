package com.guomz.missyou.service;

import com.guomz.missyou.entity.Spu;
import com.guomz.missyou.exception.NotFoundException;
import com.guomz.missyou.repository.SpuRepository;
import com.guomz.missyou.util.PageUtil;
import com.guomz.missyou.vo.PageResult;
import com.guomz.missyou.vo.SpuSimplifyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class SpuService {

    @Resource
    private SpuRepository spuRepository;

    public Spu getSpuDetail(Long id){
        Spu spu = spuRepository.findSpuById(id);
        if (spu == null){
            log.error("商品不存在");
            throw new NotFoundException(30002);
        }
        return spu;
    }

    public PageResult<Spu, SpuSimplifyVo> getLatestSpu(Integer start, Integer count){
        Pageable pageable = PageUtil.getPage(start,count, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Spu> spuPage = spuRepository.findAll(pageable);
        return new PageResult<>(spuPage, SpuSimplifyVo::new);
    }

    public PageResult<Spu, SpuSimplifyVo> getSpuByCategory(Integer start, Integer count,Long id, boolean isRoot){
        Pageable pageable = PageUtil.getPage(start,count, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Spu> spuPage;
        if (isRoot){
            spuPage = spuRepository.findByRootCategoryId(id, pageable);
        }else {
            spuPage = spuRepository.findByCategoryId(id, pageable);
        }
        return new PageResult<>(spuPage, SpuSimplifyVo::new);
    }
}

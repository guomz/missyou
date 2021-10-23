package com.guomz.missyou.service;

import com.guomz.missyou.entity.Sku;
import com.guomz.missyou.repository.SkuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class SkuService {

    @Resource
    private SkuRepository skuRepository;

    public List<Sku> getSkuByIds(List<Long> ids){
        List<Sku> skuList = skuRepository.findByIdIn(ids);
        return skuList;
    }
}

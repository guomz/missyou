package com.guomz.missyou.service;

import com.guomz.missyou.entity.SaleExplain;
import com.guomz.missyou.repository.SaleExplainRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SaleExplainService {

    @Resource
    private SaleExplainRepository saleExplainRepository;

    public List<SaleExplain> getAllExplains(){
        return saleExplainRepository.findAll();
    }
}

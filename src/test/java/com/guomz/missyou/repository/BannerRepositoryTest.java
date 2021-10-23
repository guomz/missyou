package com.guomz.missyou.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest
class BannerRepositoryTest {

    @Resource
    private BannerRepository bannerRepository;

    @Test
    @Transactional
    public void testGetById(){
        System.out.println(bannerRepository.findBannerById(1L));
    }
}
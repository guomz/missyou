package com.guomz.missyou.service;

import com.guomz.missyou.entity.Banner;
import com.guomz.missyou.exception.NotFoundException;
import com.guomz.missyou.repository.BannerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class BannerService {

    @Resource
    private BannerRepository bannerRepository;

    public Banner getByBannerId(Long id){
        Banner banner = bannerRepository.findBannerById(id);
        if (banner == null){
            log.error("banner不存在");
            throw new NotFoundException(30005);
        }
        return banner;
    }

    public Banner getByBannerName(String name){
        Banner banner = bannerRepository.findBannerByName(name);
        if (banner == null){
            log.error("banner不存在");
            throw new NotFoundException(30005);
        }
        return banner;
    }
}

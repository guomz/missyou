package com.guomz.missyou.repository;

import com.guomz.missyou.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Integer> {

    Banner findBannerById(Long id);

    Banner findBannerByName(String name);
}

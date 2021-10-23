package com.guomz.missyou.repository;

import com.guomz.missyou.entity.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpuRepository extends JpaRepository<Spu, Long> {

    Spu findSpuById(Long id);

    Page<Spu> findAll(Pageable pageable);

    Page<Spu> findByRootCategoryId(Long id, Pageable pageable);

    Page<Spu> findByCategoryId(Long id, Pageable pageable);

    Page<Spu> findByTitleLike(String title, Pageable pageable);
}

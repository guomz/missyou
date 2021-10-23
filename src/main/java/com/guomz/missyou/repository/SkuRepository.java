package com.guomz.missyou.repository;

import com.guomz.missyou.entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SkuRepository extends JpaRepository<Sku, Long> {
    List<Sku> findByIdIn(List<Long> idList);

    @Modifying
    @Query("update Sku s \n" +
            "set s.stock = s.stock - :count\n" +
            "where s.id = :skuId\n" +
            "and s.stock >= :count")
    int reduceStock(Long skuId, Integer count);

    @Modifying
    @Query("update Sku sku\n" +
            "set sku.stock = sku.stock + :count\n" +
            "where sku.id = :skuId")
    int backStock(Long skuId, Integer count);
}

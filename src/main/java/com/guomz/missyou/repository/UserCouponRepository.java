package com.guomz.missyou.repository;

import com.guomz.missyou.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    Optional<UserCoupon> findByUserIdAndCouponId(Long userId, Long couponId);

    Optional<UserCoupon> findByUserIdAndCouponIdAndStatus(Long userId, Long couponId, Integer status);

    @Modifying
    @Query("update UserCoupon uc\n" +
            "set uc.status = 2,uc.orderId = :orderId,uc.updateTime = :now\n" +
            "where\n" +
            "uc.userId = :userId\n" +
            "and uc.couponId = :couponId\n" +
            "and uc.status = 1\n" +
            "and uc.orderId is null")
    int writeOffCoupon(Long userId, Long couponId, Long orderId, Date now);

    @Modifying
    @Query("update UserCoupon uc\n" +
            "set uc.status = 1, uc.orderId = null, uc.updateTime = :now\n" +
            "where\n" +
            "uc.userId = :userId\n" +
            "and uc.couponId = :couponId\n" +
            "and uc.status = 2\n" +
            "and uc.orderId is not null")
    int backCoupon(Long userId, Long couponId, Date now);

    List<UserCoupon> findByStatus(Integer status);
}

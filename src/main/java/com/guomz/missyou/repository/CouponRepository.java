package com.guomz.missyou.repository;

import com.guomz.missyou.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findById(Long id);

    @Query("select c\n" +
            "from Coupon c\n" +
            "join c.categories ca\n" +
            "join Activity a on c.activityId = a.id\n" +
            "where ca.id = :cid\n" +
            "and a.startTime < :now\n" +
            "and a.endTime > :now")
    List<Coupon> findByCategory(Long cid, Date now);

    @Query("select c \n" +
            "from " +
            "Coupon c \n" +
            "join Activity a on c.activityId = a.id\n" +
            "where a.startTime < :now\n" +
            "and a.endTime > :now\n" +
            "and c.wholeStore = true")
    List<Coupon> findWholeStore(Date now);

    @Query("select c\n" +
            "from Coupon c\n" +
            "join UserCoupon uc on c.id = uc.couponId\n" +
            "where\n" +
            "uc.userId = :uid\n" +
            "and uc.status = 1\n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now\n" +
            "and uc.orderId is null")
    List<Coupon> findUsableCoupons(Long uid, Date now);

    @Query("select c\n" +
            "from Coupon c\n" +
            "join UserCoupon uc on c.id = uc.couponId\n" +
            "where\n" +
            "uc.userId = :uid\n" +
            "and uc.status = 2\n" +
            "and uc.orderId is not null")
    List<Coupon> findUsedCoupons(Long uid);

    @Query("select c\n" +
            "from Coupon c \n" +
            "join UserCoupon uc on c.id = uc.couponId\n" +
            "where\n" +
            "uc.userId = :uid\n" +
            "and uc.status <> 2\n" +
            "and c.endTime < :now")
    List<Coupon> findExpiredCoupons(Long uid, Date now);

    List<Coupon> findByIdIn(List<Long> idList);
}

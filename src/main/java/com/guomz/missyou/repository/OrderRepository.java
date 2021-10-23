package com.guomz.missyou.repository;

import com.guomz.missyou.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByExpiredTimeIsGreaterThanAndUserIdAndStatus(Date now, Long userId, Integer status, Pageable pageable);

    Optional<Order> findByIdAndUserId(Long id, Long userId);

    Page<Order> findByUserIdAndStatus(Long userId, Integer status, Pageable pageable);

    Page<Order> findByUserId(Long userId, Pageable pageable);

    @Modifying
    @Query("update Order o\n" +
            "set o.status = 5 \n" +
            "where\n" +
            "o.id = :orderId\n" +
            "and o.status = 1")
    int cancelOrder(Long orderId);

    @Query("select o \n" +
            "from Order o\n" +
            "where o.status = 1\n" +
            "and o.expiredTime > :now\n" +
            "and o.userId = :userId")
    Page<Order> findUnpaidOrders(Long userId,Date now, Pageable pageable);

    @Query("select o\n" +
            "from Order o\n" +
            "where\n" +
            "o.userId = :userId\n" +
            "and (o.status = 5 or o.expiredTime <= :now)")
    Page<Order> findCanceledOrders(Long userId, Date now, Pageable pageable);

    @Modifying
    @Query("update Order o\n" +
            "set o.status = 2, o.updateTime = :now\n" +
            "where\n" +
            "o.userId = :userId\n" +
            "and o.id = :orderId\n" +
            "and o.status = 1\n" +
            "and o.expiredTime > :now")
    int payOrder(Long userId, Long orderId, Date now);
}

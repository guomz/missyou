package com.guomz.missyou.service;
import java.util.Calendar;
import java.util.Date;

import com.guomz.missyou.dto.OrderDto;
import com.guomz.missyou.dto.SkuDto;
import com.guomz.missyou.entity.*;
import com.guomz.missyou.enums.CouponStatusEnums;
import com.guomz.missyou.enums.OrderStatusEnums;
import com.guomz.missyou.exception.ForbiddenException;
import com.guomz.missyou.exception.NotFoundException;
import com.guomz.missyou.repository.CouponRepository;
import com.guomz.missyou.repository.OrderRepository;
import com.guomz.missyou.repository.SkuRepository;
import com.guomz.missyou.repository.UserCouponRepository;
import com.guomz.missyou.util.KeyUtil;
import com.guomz.missyou.util.LocalUserUtil;
import com.guomz.missyou.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    @Resource
    private SkuRepository skuRepository;
    @Resource
    private CouponRepository couponRepository;
    @Resource
    private UserCouponRepository userCouponRepository;
    @Resource
    private OrderRepository orderRepository;
    @Value("${order.expired-time}")
    private Integer orderExpiredTime;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 校验订单
     * @param orderDto
     * @return
     */
    public OrderChecker checkOrder(OrderDto orderDto){
        log.info("订单信息: {}", orderDto);
        //获取sku信息
        List<Long> idList = orderDto.getSkuInfoList().stream()
                .map(SkuDto::getId).distinct().collect(Collectors.toList());
        List<Sku> skuList = skuRepository.findByIdIn(idList);
        //判断是否存在优惠券
        Long userId = LocalUserUtil.getUserId();
        CouponChecker couponChecker;
        if (orderDto.getCouponId() != null){
            Coupon coupon = couponRepository.findById(orderDto.getCouponId())
                    .orElseThrow(()->new NotFoundException(40004));
            UserCoupon userCoupon = userCouponRepository.findByUserIdAndCouponIdAndStatus(userId, orderDto.getCouponId(), CouponStatusEnums.USABLE.getCode())
                    .orElseThrow(()-> new ForbiddenException(40014));
            couponChecker = new CouponChecker(coupon, userCoupon);
        }else {
            couponChecker = new CouponChecker();
        }
        //校验订单
        OrderChecker orderChecker = new OrderChecker(orderDto, skuList, couponChecker);
        orderChecker.isOk();
        return orderChecker;
    }

    /**
     * 下订单
     * @param orderChecker
     * @return
     */
    @Transactional
    public Long placeOrder(OrderDto orderDto, OrderChecker orderChecker){
        Date now  = new Date();
        //创建订单
        Order order = generateOrder(orderDto,orderChecker, now);
        orderRepository.save(order);
        //扣减库存
        reduceStock(orderChecker);
        //核销优惠券
        writeOffCoupon(orderDto, now, order);
        //发送给redis
        String expiredKey = order.getUserId() + "-" + order.getId() + "-" + orderDto.getCouponId();
        try {
            stringRedisTemplate.opsForValue().set(expiredKey, "1", this.orderExpiredTime, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
            log.error("发送redis失败");
        }
        return order.getId();
//        return 1L;
    }

    /**
     * 核销优惠券
     * @param orderDto
     * @param now
     * @param order
     */
    private void writeOffCoupon(OrderDto orderDto, Date now, Order order) {
        if (orderDto.getCouponId() != null){
            int result = userCouponRepository.writeOffCoupon(LocalUserUtil.getUserId(), orderDto.getCouponId(), order.getId(), now);
            if (result != 1){
                log.error("优惠券核销失败");
                throw new ForbiddenException(40012);
            }
        }
    }

    /**
     * 扣件库存
     * @param orderChecker
     */
    private void reduceStock(OrderChecker orderChecker) {
        for (OrderSku orderSku : orderChecker.getSnapItems()) {
            int result = skuRepository.reduceStock(orderSku.getId(), orderSku.getCount());
            if (result != 1){
                log.error("库存出现不足");
                throw new ForbiddenException(50003);
            }
        }
    }

    /**
     * 生成订单
     * @param orderChecker
     * @param now
     * @return
     */
    private Order generateOrder(OrderDto orderDto, OrderChecker orderChecker, Date now) {
        Order order = new Order();
        order.setOrderNo(KeyUtil.generateKey());
        order.setUserId(LocalUserUtil.getUserId());
        order.setTotalPrice(orderChecker.getServerTotalPrice());
        order.setTotalCount(orderChecker.getTotalCount());
        order.setCreateTime(now);
        order.setUpdateTime(now);
        order.setExpiredTime(getExpiredDate(now));
        order.setPlacedTime(now);
        order.setSnapImg(orderChecker.getSnapImg());
        order.setSnapTitle(orderChecker.getSnapTitle());
        order.setSnapItems(orderChecker.getSnapItems());
        order.setSnapAddress(orderDto.getAddress());
        order.setFinalTotalPrice(orderChecker.getServerFinalTotalPrice());
        order.setStatus(OrderStatusEnums.UNPAID.getCode());
        order.setPeriod(this.orderExpiredTime);
        return order;
    }

    /**
     * 计算订单过期时间
     * @param now
     * @return
     */
    private Date getExpiredDate(Date now){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.SECOND, this.orderExpiredTime);
        return calendar.getTime();
    }

    /**
     * 支付订单
     * @param orderId
     */
    @Transactional
    public void payOrder(Long orderId){
        Long userId = LocalUserUtil.getUserId();
        int result = orderRepository.payOrder(userId, orderId, new Date());
        if (result != 1){
            log.error("支付失败: {}", orderId);
            throw new ForbiddenException(50013);
        }
    }

    /**
     * 查询未支付订单
     * @param start
     * @param count
     * @return
     */
    public Page<Order> getUnpaidOrder(Integer start, Integer count){
        Pageable pageable = PageUtil.getPage(start,count, Sort.by(Sort.Direction.DESC, "createTime"));
        Long userId = LocalUserUtil.getUserId();
        return orderRepository.findByExpiredTimeIsGreaterThanAndUserIdAndStatus(new Date(), userId, OrderStatusEnums.UNPAID.getCode(), pageable);
    }

    /**
     * 按照状态查询订单
     * @param start
     * @param count
     * @param status
     * @return
     */
    public Page<Order> getByStatus(Integer start, Integer count, Integer status){
        Pageable pageable = PageUtil.getPage(start,count, Sort.by(Sort.Direction.DESC, "createTime"));
        Long userId = LocalUserUtil.getUserId();
        log.info("orderStatus: {}", status);
        OrderStatusEnums orderStatusEnums = OrderStatusEnums.getOrderStatus(status);
        switch (orderStatusEnums){
            case All:
                return orderRepository.findByUserId(userId, pageable);
            case PAID:
            case FINISHED:
            case DELIVERED:
                return orderRepository.findByUserIdAndStatus(userId, status, pageable);
            case UNPAID:
                return orderRepository.findUnpaidOrders(userId, new Date(), pageable);
            case CANCELED:
                return orderRepository.findCanceledOrders(userId,new Date(), pageable);
            default:
                throw new ForbiddenException(50012);
        }
    }

    /**
     * 查找订单详情
     * @param orderId
     * @return
     */
    public Order getOrderDetail(Long orderId){
        Long userId = LocalUserUtil.getUserId();
        return orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(()->new NotFoundException(50009));
    }
}

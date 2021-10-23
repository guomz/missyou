package com.guomz.missyou.service;

import com.guomz.missyou.bo.OrderSkuBo;
import com.guomz.missyou.dto.OrderDto;
import com.guomz.missyou.dto.SkuDto;
import com.guomz.missyou.entity.OrderSku;
import com.guomz.missyou.entity.Sku;
import com.guomz.missyou.exception.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderChecker {

    Logger logger = LoggerFactory.getLogger(OrderChecker.class);
    private OrderDto orderDto;
    private List<Sku> skuList;
    private Map<Long, Sku> skuMap;
    private CouponChecker couponChecker;
    private BigDecimal serverTotalPrice;
    private List<OrderSku> orderSkuList;

    public OrderChecker (OrderDto orderDto, List<Sku> skuList, CouponChecker couponChecker){
        this.orderDto = orderDto;
        this.skuList = skuList;
        this.skuMap = skuList.stream().collect(Collectors.toMap(Sku::getId, sku -> sku));
        this.couponChecker = couponChecker;

    }

    /**
     * 校验订单
     */
    public void isOk(){
        //检查订单
        checkSkuOnSale();
        this.serverTotalPrice = calculateServerTotalPrice();
        checkTotalPrice();
        checkOutOfStock();
        //检查优惠券
        if (couponChecker.hasCoupon()){
            List<OrderSkuBo> skuBoList = this.orderDto.getSkuInfoList().stream()
                            .map(orderSkuDto -> new OrderSkuBo(this.skuMap.get(orderSkuDto.getId()), orderSkuDto))
                                    .collect(Collectors.toList());
            couponChecker.checkCouponExpired(new Date());
            couponChecker.canBeUsed(skuBoList, this.serverTotalPrice);
            //验证最终总金额是否正确
            couponChecker.checkPrice(orderDto.getFinalTotalPrice(), this.serverTotalPrice);
        }else {
            //没有优惠券最终金额应该与总金额相同
            if (this.orderDto.getFinalTotalPrice().compareTo(this.serverTotalPrice)!=0){
                logger.error("最终总金额错误");
                throw new ForbiddenException(50005);
            }
        }
        //生成订单快照
        this.orderSkuList = this.orderDto.getSkuInfoList().stream()
                .map(skuDto -> new OrderSku(this.skuMap.get(skuDto.getId()), skuDto))
                .collect(Collectors.toList());
    }

    /**
     * 总金额
     * @return
     */
    public BigDecimal getServerTotalPrice(){
        return this.serverTotalPrice;
    }

    /**
     * 最总金额
     * @return
     */
    public BigDecimal getServerFinalTotalPrice(){
        return this.couponChecker == null ? this.serverTotalPrice: this.orderDto.getFinalTotalPrice();
    }

    /**
     * 总数量
     * @return
     */
    public Integer getTotalCount(){
        return this.orderSkuList.stream()
                .mapToInt(OrderSku::getCount).sum();
    }

    /**
     * 快照标题
     * @return
     */
    public String getSnapTitle(){
        return this.orderSkuList.get(0).getTitle();
    }

    /**
     * 快照图片
     * @return
     */
    public String getSnapImg(){
        return this.orderSkuList.get(0).getImg();
    }

    /**
     * 快照详细信息
     * @return
     */
    public List<OrderSku> getSnapItems(){
        return this.orderSkuList;
    }

    /**
     * 判断订单商品是否都合法
     */
    private void checkSkuOnSale(){
        if (this.orderDto.getSkuInfoList().size() != skuMap.size()){
            logger.error("商品存在下架商品");
            throw new ForbiddenException(50002);
        }
    }

    /**
     * 检查订单总金额
     */
    private void checkTotalPrice(){
        if (this.serverTotalPrice.compareTo(this.orderDto.getTotalPrice()) != 0){
            logger.error("总金额不正确");
            throw new ForbiddenException(50005);
        }
    }

    /**
     * 计算服务端订单总价
     * @return
     */
    private BigDecimal calculateServerTotalPrice(){
        return this.orderDto.getSkuInfoList().stream()
                .map(orderSkuDto -> skuMap.get(orderSkuDto.getId()).getActualPrice().multiply(new BigDecimal(orderSkuDto.getCount())).setScale(2, RoundingMode.UP))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    /**
     * 检查库存
     */
    private void checkOutOfStock(){
        for (SkuDto skuDto : this.orderDto.getSkuInfoList()) {
            Sku sku = skuMap.get(skuDto.getId());
            if (sku.getStock() == 0 || skuDto.getCount().compareTo(sku.getStock()) > 0){
                logger.error("商品存在库存不足");
                throw new ForbiddenException(50003);
            }
        }
    }
}

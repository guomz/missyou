package com.guomz.missyou.controller.v1;

import com.guomz.missyou.annotations.ScopeLevel;
import com.guomz.missyou.domain.resp.ErrResponse;
import com.guomz.missyou.dto.OrderDto;
import com.guomz.missyou.entity.Order;
import com.guomz.missyou.service.OrderChecker;
import com.guomz.missyou.service.OrderService;
import com.guomz.missyou.vo.OrderDetailVo;
import com.guomz.missyou.vo.OrderSimplifyVo;
import com.guomz.missyou.vo.OrderVo;
import com.guomz.missyou.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/order")
@Api(tags = "订单接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ScopeLevel(1)
    @ApiOperation("下订单")
    @PostMapping
    public OrderVo placeOrder(@RequestBody @Validated OrderDto orderDto){
        OrderChecker orderChecker = orderService.checkOrder(orderDto);
        Long orderId = orderService.placeOrder(orderDto, orderChecker);
        return new OrderVo(orderId);
    }

    @ScopeLevel(1)
    @ApiOperation("查询未支付订单")
    @GetMapping("/status/unpaid")
    public PageResult getUnpaidOrder(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                     @RequestParam(value = "count", required = false, defaultValue = "5") Integer count){
        Page<Order> orders = orderService.getUnpaidOrder(start,count);
        return new PageResult(orders, OrderSimplifyVo::new);
    }

    @ScopeLevel(1)
    @ApiOperation("按照状态查询")
    @GetMapping("/by/status/{status}")
    public PageResult getByStatus(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                  @RequestParam(value = "count", required = false, defaultValue = "5") Integer count,
                                  @PathVariable("status") Integer status){
        Page<Order> orders = orderService.getByStatus(start, count, status);
        return new PageResult(orders, OrderSimplifyVo::new);
    }

    @ScopeLevel(1)
    @ApiOperation("查询订单详情")
    @GetMapping("/detail/{id}")
    public OrderDetailVo getOrderDetail(@PathVariable("id") Long id){
        Order order = orderService.getOrderDetail(id);
        return new OrderDetailVo(order);
    }

    @ScopeLevel(1)
    @ApiOperation("模拟支付订单")
    @PostMapping("/pay/{id}")
    public ErrResponse payOrder(@PathVariable("id") Long orderId){
        orderService.payOrder(orderId);
        return new ErrResponse(0,"ok");
    }
}

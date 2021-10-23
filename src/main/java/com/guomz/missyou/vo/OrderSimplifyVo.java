package com.guomz.missyou.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OrderSimplifyVo {

    private Long id;

    private String orderNo;

    private BigDecimal totalPrice;

    private Integer totalCount;

    private Date placedTime;

    private Date createTime;

    private String snapImg;

    private String snapTitle;

    private String snapAddress;

    private BigDecimal finalTotalPrice;

    private Integer status;

    private Integer period;
}

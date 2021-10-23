package com.guomz.missyou.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "sale_explain")
@Data
public class SaleExplain {
    @Id
    private Long id;
    private Integer fixed;
    private String text;
    private Long spuId;
    private Integer index;
    private Integer replaceId;
    private Date createTime;
    private Date deleteTime;
    private Date updateTime;

}
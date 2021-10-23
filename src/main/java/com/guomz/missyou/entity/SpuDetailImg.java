package com.guomz.missyou.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Data
@Entity
@Table(name = "spu_detail_img")
@Where(clause = "delete_time is null")
public class SpuDetailImg {
    @Id
    private Long id;

    private String img;

    private Long spuId;

    private Integer index;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

}
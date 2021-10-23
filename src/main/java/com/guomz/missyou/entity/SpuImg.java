package com.guomz.missyou.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Entity
@Data
@Table(name = "spu_img")
@Where(clause = "delete_time is null")
public class SpuImg {
    @Id
    private Long id;

    private String img;

    private Long spuId;

    private Date deleteTime;

    private Date updateTime;

    private Date createTime;

}
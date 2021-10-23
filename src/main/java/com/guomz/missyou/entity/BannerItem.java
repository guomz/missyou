package com.guomz.missyou.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Data
@Entity
@Table(name = "banner_item")
@Where(clause = "delete_time is null")
public class BannerItem  {
    @Id
    private Long id;

    private String img;

    private String keyword;

    private Integer type;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private Integer bannerId;

    private String name;

}
package com.guomz.missyou.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
@Data
@Entity
@Table(name = "grid_category")
@Where(clause = "delete_time is null")
public class GridCategory {
    @Id
    private Long id;

    private String title;

    private String img;

    private String name;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private Long categoryId;

    private Long rootCategoryId;

}
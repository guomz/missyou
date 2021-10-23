package com.guomz.missyou.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Data
@Entity
@Table(name = "theme")
@Where(clause = "delete_time is null and online = 1")
public class Theme {
    @Id
    private Long id;

    private String title;

    private String description;

    private String name;

    private Date createTime;

    private String tplName;

    private Date updateTime;

    private Date deleteTime;

    private String entranceImg;

    private String extend;

    private String internalTopImg;

    private String titleImg;

    private Integer online;

    @ManyToMany
    @JoinTable(name = "theme_spu", joinColumns = @JoinColumn(name="theme_id"), inverseJoinColumns = @JoinColumn(name = "spu_id"))
    private List<Spu> spuList;

}
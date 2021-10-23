package com.guomz.missyou.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "banner")
@Where(clause = "delete_time is null")
public class Banner {
    @Id
    private Long id;

    private String name;

    private String description;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private String title;

    private String img;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bannerId", referencedColumnName = "id")
    private List<BannerItem> items;
}
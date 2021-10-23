package com.guomz.missyou.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tag")
@Data
public class Tag {

    @Id
    private Long id;
    private String title;
    private String description;
    private Date updateTime;
    private Date deleteTime;
    private Date createTime;
    private Integer highlight;
    private Integer type;

}

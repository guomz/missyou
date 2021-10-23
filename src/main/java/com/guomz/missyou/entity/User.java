package com.guomz.missyou.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String openid;

    private String nickname;

    private Integer unifyUid;

    private String email;

    private String password;

    private String mobile;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private String wxProfile;
}
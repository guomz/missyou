package com.guomz.missyou.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TokenGetDto {

    @NotBlank(message = "账户为空")
    private String account;
    private String password;
    private Integer loginType;
}

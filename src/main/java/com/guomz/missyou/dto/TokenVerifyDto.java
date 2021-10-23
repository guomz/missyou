package com.guomz.missyou.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TokenVerifyDto {

    @NotBlank(message = "token为空")
    private String token;
}

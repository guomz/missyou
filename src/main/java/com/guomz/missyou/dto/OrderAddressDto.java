package com.guomz.missyou.dto;

import lombok.Data;

@Data
public class OrderAddressDto {
    private String userName;
    private String province;
    private String city;
    private String county;
    private String mobile;
    private String nationalCode;
    private String postalCode;
    private String detail;
}

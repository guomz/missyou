package com.guomz.missyou.controller.v1;

import com.guomz.missyou.dto.TokenGetDto;
import com.guomz.missyou.dto.TokenVerifyDto;
import com.guomz.missyou.service.LoginService;
import com.guomz.missyou.vo.TokenVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/token")
@Api(tags = "token接口")
public class TokenController {

    @Autowired
    private LoginService loginService;

    @ApiOperation("获取token")
    @PostMapping("")
    public TokenVo getToken(@RequestBody @Validated TokenGetDto tokenGetDto){
        String token = loginService.getToken(tokenGetDto.getAccount());
        return new TokenVo(token);
    }

    @ApiOperation("验证token")
    @PostMapping("/verify")
    public Map<String, Boolean> verifyToken(@RequestBody @Validated TokenVerifyDto tokenVerifyDto){
        boolean result = loginService.verifyToken(tokenVerifyDto.getToken());
        Map<String, Boolean> resultMap = new HashMap<>();
        resultMap.put("is_valid", result);
        return resultMap;
    }
}

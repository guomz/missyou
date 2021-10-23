package com.guomz.missyou.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@PropertySource("classpath:config/exception-code.properties")
@ConfigurationProperties(prefix = "guo")
@Setter
public class ExceptionCodeConfig {

    private Map<Integer, String> codes;

    public String getExceptionMsg(Integer code){
        return codes.get(code);
    }
}

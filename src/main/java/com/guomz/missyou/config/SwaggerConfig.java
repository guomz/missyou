package com.guomz.missyou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(generateSecurity())
                ;
    }

    public List<ApiKey> generateSecurity(){
        List<ApiKey> arrayList = new ArrayList();
        arrayList.add(new ApiKey("Authorization", "Authorization", "header"));
        return arrayList;
    }
}

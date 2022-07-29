package com.example.rabbitmq.rabbitmqhello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chenzhiqin
 * @date 2022/7/15 9:34
 * @info swagger 配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket webApiConfig(){

        return  new Docket(DocumentationType.SWAGGER_2).groupName("webApi").apiInfo(webApiInfo()).select().build();

    }
    private ApiInfo webApiInfo(){
        return  new ApiInfoBuilder().title("rabbitmq接口文档").description("描述").version("1.0").contact(new Contact("123","baidu.com","")).build();
    }
}

package com.course.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
@EnableOpenApi //开启Swagger3
public class SwaggerConfig {
    //配置Swagger的Docket的bean实例
    @Bean
    public Docket docket() {
        //通过environment.acceptsProfiles判断是否处在自己设定的环境中
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())//配置Swagger信息
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.course.controller"))
                .paths(PathSelectors.regex("/.*"))
                .build();
    }

    //配置Swagger信息
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "大忽悠项目",
                "大忽悠的Swagger API文档",
                "2.0",//版本信息
                "https://blog.csdn.net/m0_53157173",//团队信息的url
                new Contact("大忽悠", "https://blog.csdn.net/m0_53157173", "3076679680@qq.com"),//作者信息
                /*Contact(String name, String url, String email)*/
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<VendorExtension>());
    }
}

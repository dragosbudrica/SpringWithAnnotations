package com.kepler.rominfo.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Created by amitrus on 8/3/2017.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build().apiInfo(apiInfo());
    }

    private static ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("Test", "Test", "1.0.0", "",
                new Contact("Me", null, "..."),
                "", "", Collections.emptyList());
        return apiInfo;
    }
}

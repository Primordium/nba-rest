package com.hro.exercise.nbachallenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


import java.util.Collections;

@Configuration

public class SpringFoxConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hro.exercise.nbachallenge.controller.rest"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "NBA-Challenge API",
                "REST service for RHO challenge, with integration of RapidApi and Database",
                "API TOS",
                "Terms of service",
                new Contact("Bruno Fernandes", "https://www.linkedin.com/in/bruno-ea-fernandes/", "b.araujofernandes@protonmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

}

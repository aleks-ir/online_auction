package com.tisserand.service.rest.config;

import com.tisserand.service.DateService;
import com.tisserand.service.ProductService;
import com.tisserand.service.UserService;
import com.tisserand.service.dto.ProductDtoService;
import com.tisserand.service.dto.UserDtoService;
import com.tisserand.service.rest.DateServiceRest;
import com.tisserand.service.rest.ProductServiceRest;
import com.tisserand.service.rest.UserServiceRest;
import com.tisserand.service.rest.dto.ProductDtoServiceRest;
import com.tisserand.service.rest.dto.UserDtoServiceRest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {

    private static final String PRODUCTS_DTO_URL = "http://localhost:8088/products-dto";
    private static final String PRODUCTS_URL = "http://localhost:8088/products";
    private static final String USERS_URL = "http://localhost:8088/users";
    private static final String USERS_DTO_URL = "http://localhost:8088/users-dto";
    public static final String DATE_URL = "http://localhost:8088/date";

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @Bean
    ProductDtoService productDtoService() {
        return new ProductDtoServiceRest(PRODUCTS_DTO_URL, restTemplate());
    }

    @Bean
    ProductService productService() {
        return new ProductServiceRest(PRODUCTS_URL, restTemplate());
    }


    @Bean
    UserDtoService userDtoService() {
        return new UserDtoServiceRest(USERS_DTO_URL, restTemplate());
    }

    @Bean
    UserService userService() {
        return new UserServiceRest(USERS_URL, restTemplate());
    }

    @Bean
    DateService dateService() {
        return new DateServiceRest(DATE_URL, restTemplate());
    }

}

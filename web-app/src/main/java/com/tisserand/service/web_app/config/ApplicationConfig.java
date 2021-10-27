package com.tisserand.service.web_app.config;

import com.tisserand.service.*;
import com.tisserand.service.dto.ProductDtoService;
import com.tisserand.service.dto.UserDtoService;
import com.tisserand.service.rest.*;
import com.tisserand.service.rest.dto.ProductDtoServiceRest;
import com.tisserand.service.rest.dto.UserDtoServiceRest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
public class ApplicationConfig {

    @Value("${rest.server.protocol}")
    private String protocol;
    @Value("${rest.server.host}")
    private String host;
    @Value("${rest.server.port}")
    private Integer port;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @Bean
    ProductDtoService productDtoService() {
        String url = String.format("%s://%s:%d/products-dto", protocol, host, port);
        return new ProductDtoServiceRest(url, restTemplate());
    };

    @Bean
    ProductService productService() {
        String url = String.format("%s://%s:%d/products", protocol, host, port);
        return new ProductServiceRest(url, restTemplate());
    };

    @Bean
    UserDtoService userDtoService() {
        String url = String.format("%s://%s:%d/users-dto", protocol, host, port);
        return new UserDtoServiceRest(url, restTemplate());
    };

    @Bean
    UserService userService() {
        String url = String.format("%s://%s:%d/users", protocol, host, port);
        return new UserServiceRest(url, restTemplate());
    };

    @Bean
    DateService dateService() {
        String url = String.format("%s://%s:%d/date", protocol, host, port);
        return new DateServiceRest(url, restTemplate());
    };


}

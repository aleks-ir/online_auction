package com.tisserand.service.rest;

import com.tisserand.model.dto.ProductDto;
import com.tisserand.service.ProductDtoService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Service
public class ProductDtoServiceRest implements ProductDtoService {

    private final String url;
    private final RestTemplate restTemplate;

    public ProductDtoServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ProductDto> findAllProductWithNameOwner() {
        ResponseEntity<List<ProductDto>> responseEntity = restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<>() {});
        return responseEntity.getBody();
    }

}

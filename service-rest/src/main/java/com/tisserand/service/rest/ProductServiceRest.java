package com.tisserand.service.rest;

import com.tisserand.model.Product;
import com.tisserand.service.ProductService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceRest implements ProductService {
    private final String url;
    private final RestTemplate restTemplate;

    public ProductServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Product> findAll() {
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Product>) responseEntity.getBody();
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        ResponseEntity<Product> responseEntity =
                restTemplate.getForEntity(url + "/" + productId, Product.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    @Override
    public Integer create(Product product) {
        ResponseEntity responseEntity = restTemplate.postForEntity(url, product, Integer.class);
        return (Integer) responseEntity.getBody();
    }

    @Override
    public Integer delete(Integer productId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Product> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(url + "/" + productId, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public List<Product> findAllIdByDate(String date) {
        ResponseEntity<Product> responseEntity =
                restTemplate.getForEntity(url + "/" + date, Product.class);
        return (List<Product>) responseEntity.getBody();
    }

    @Override
    public Integer updatePriceAndCustomer(Product product) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Product> entity = new HttpEntity<>(product, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }
}

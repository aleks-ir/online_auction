package com.tisserand.service.rest.dto;

import com.tisserand.model.dto.ProductDto;
import com.tisserand.service.dto.ProductDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Service
public class ProductDtoServiceRest implements ProductDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDtoServiceRest.class);

    private final String url;
    private final RestTemplate restTemplate;

    public ProductDtoServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ProductDto> findAllProductWithNameOwner() {
        LOGGER.debug("ProductDtoServiceRest: findAllProductWithNameOwner()");
        ResponseEntity<List<ProductDto>> responseEntity = restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<>() {});
        return responseEntity.getBody();
    }

    @Override
    public List<ProductDto> findAllProductWithNameOwnerByDate(String startDate, String endDate) {
        LOGGER.debug("ProductDtoServiceRest: findAllProductWithNameOwnerByDate({} {})", startDate, endDate);
        ResponseEntity<List<ProductDto>> responseEntity = restTemplate.exchange(url + "?startDate={startDate}&endDate={endDate}", GET, null, new ParameterizedTypeReference<>() {}, startDate, endDate);
        return responseEntity.getBody();
    }

}

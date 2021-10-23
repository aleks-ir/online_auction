package com.tisserand.service.rest.dto;

import com.tisserand.model.dto.UserDto;
import com.tisserand.service.dto.UserDtoService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Service
public class UserDtoServiceRest implements UserDtoService {
    private final String url;
    private final RestTemplate restTemplate;

    public UserDtoServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<UserDto> findAllUsersWithValueOfAllProducts() {
        ResponseEntity<List<UserDto>> responseEntity = restTemplate.exchange(url, GET, null, new ParameterizedTypeReference<>() {});
        return responseEntity.getBody();
    }
}

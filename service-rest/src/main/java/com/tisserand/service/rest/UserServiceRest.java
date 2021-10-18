package com.tisserand.service.rest;

import com.tisserand.model.Product;
import com.tisserand.model.User;
import com.tisserand.service.UserService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceRest implements UserService {
    private final String url;
    private final RestTemplate restTemplate;

    public UserServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }


    @Override
    public List<User> findAll() {
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<User>) responseEntity.getBody();
    }

    @Override
    public Optional<User> findById(Integer userId) {
        ResponseEntity<User> responseEntity =
                restTemplate.getForEntity(url + "/" + userId, User.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    @Override
    public Integer update(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer takeMoney(Float value, Integer userId) {
        return null;
    }

    @Override
    public Integer putMoney(Float value, Integer userId) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }
}

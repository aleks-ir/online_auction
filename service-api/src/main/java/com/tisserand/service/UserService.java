package com.tisserand.service;

import com.tisserand.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(Integer userId);

    Integer update(User user);

    Integer takeMoney(Float value, Integer userId);

    Integer putMoney(Float value, Integer userId);

    Integer count();
}
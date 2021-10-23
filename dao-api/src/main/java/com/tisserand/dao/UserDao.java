package com.tisserand.dao;

import com.tisserand.model.Product;
import com.tisserand.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll();

    Optional<User> findById(Integer userId);

    Integer update(User user);

    Integer count();
}

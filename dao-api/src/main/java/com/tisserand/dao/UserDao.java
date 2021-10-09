package com.tisserand.dao;

import com.tisserand.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll();

    Optional<User> findById(Integer userId);

    Integer create(User user);

    Integer update(User user);

    Integer delete(User userId);

    Integer count();
}

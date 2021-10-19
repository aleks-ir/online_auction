package com.tisserand.service.impl;

import com.tisserand.dao.UserDao;
import com.tisserand.model.User;
import com.tisserand.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> findById(Integer userId) {
        return userDao.findById(userId);
    }

    @Override
    public Integer update(User user) {
        return userDao.update(user);
    }

    @Override
    public Integer takeMoney(Float value, Integer userId) {
        return userDao.takeMoney(value, userId);
    }

    @Override
    public Integer putMoney(Float value, Integer userId) {
        return userDao.putMoney(value, userId);
    }

    @Override
    public Integer count() {
        return userDao.count();
    }
}

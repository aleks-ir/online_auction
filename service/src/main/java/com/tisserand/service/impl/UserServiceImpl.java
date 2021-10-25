package com.tisserand.service.impl;

import com.tisserand.dao.UserDao;
import com.tisserand.model.User;
import com.tisserand.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        LOGGER.debug("UserServiceImpl: findProducts({})", userDao);
        this.userDao = userDao;
    }

    @Override
    public List<User> findAll() {
        LOGGER.debug("UserServiceImpl: findAll({})");
        return userDao.findAll();
    }

    @Override
    public Optional<User> findById(Integer userId) {
        LOGGER.debug("UserServiceImpl: findById({})", userId);
        return userDao.findById(userId);
    }

    @Override
    public Integer update(User user) {
        LOGGER.debug("UserServiceImpl: update({})", user);
        return userDao.update(user);
    }

    @Override
    public Integer count() {
        return userDao.count();
    }
}

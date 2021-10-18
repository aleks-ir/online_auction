package com.tisserand.service.impl;

import com.tisserand.dao.UserDtoDao;
import com.tisserand.model.dto.UserDto;
import com.tisserand.service.UserDtoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserDtoServiceImpl implements UserDtoService {
    private final UserDtoDao userDtoDao;

    @Autowired
    public UserDtoServiceImpl(UserDtoDao userDtoDao) {
        this.userDtoDao = userDtoDao;
    }

    @Override
    public List<UserDto> findAllUsersWithValueOfAllProducts() {
        return userDtoDao.findAllUsersWithValueOfAllProducts();
    }
}

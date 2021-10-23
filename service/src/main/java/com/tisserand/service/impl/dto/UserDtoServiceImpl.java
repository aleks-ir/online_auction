package com.tisserand.service.impl.dto;

import com.tisserand.dao.dto.UserDtoDao;
import com.tisserand.model.dto.UserDto;
import com.tisserand.service.dto.UserDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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

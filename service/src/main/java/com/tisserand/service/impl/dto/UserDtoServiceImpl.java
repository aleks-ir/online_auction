package com.tisserand.service.impl.dto;

import com.tisserand.dao.dto.UserDtoDao;
import com.tisserand.model.dto.UserDto;
import com.tisserand.service.dto.UserDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserDtoServiceImpl implements UserDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDtoServiceImpl.class);

    private final UserDtoDao userDtoDao;

    @Autowired
    public UserDtoServiceImpl(UserDtoDao userDtoDao) {
        this.userDtoDao = userDtoDao;
    }

    @Override
    public List<UserDto> findAllUsersWithValueOfAllProducts() {
        LOGGER.debug("UserDtoServiceImpl: findAllUsersWithValueOfAllProducts()");
        return userDtoDao.findAllUsersWithValueOfAllProducts();
    }
}

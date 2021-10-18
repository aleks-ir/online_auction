package com.tisserand.service;

import com.tisserand.model.dto.UserDto;

import java.util.List;

public interface UserDtoService {
    List<UserDto> findAllUsersWithValueOfAllProducts();
}

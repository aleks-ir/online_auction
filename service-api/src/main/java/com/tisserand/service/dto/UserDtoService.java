package com.tisserand.service.dto;

import com.tisserand.model.dto.UserDto;

import java.util.List;

public interface UserDtoService {
    List<UserDto> findAllUsersWithValueOfAllProducts();
}

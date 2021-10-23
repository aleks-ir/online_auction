package com.tisserand.dao.dto;

import com.tisserand.model.dto.UserDto;

import java.util.List;

public interface UserDtoDao {

    List<UserDto> findAllUsersWithValueOfAllProducts();
}

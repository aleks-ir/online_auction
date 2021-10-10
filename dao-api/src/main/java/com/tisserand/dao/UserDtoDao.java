package com.tisserand.dao;

import com.tisserand.model.dto.UserDto;

import java.util.List;

public interface UserDtoDao {

    List<UserDto> findValueOfAllProducts();
}

package com.tisserand.service.rest_app.controllers.dto;

import com.tisserand.model.dto.UserDto;
import com.tisserand.service.dto.UserDtoService;
import com.tisserand.service.rest_app.controllers.DateController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@CrossOrigin
@RestController
public class UserDtoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDtoController.class);

    private UserDtoService userDtoService;

    public UserDtoController(UserDtoService userDtoService) {
        this.userDtoService = userDtoService;
    }

    @GetMapping(value = "/users-dto")
    public Collection<UserDto> findAllProductWithNameOwner(){
        LOGGER.debug("UserDtoService: getDate()");
        return userDtoService.findAllUsersWithValueOfAllProducts();
    }
}

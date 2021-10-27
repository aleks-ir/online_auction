package com.tisserand.service.impl.dto;

import com.tisserand.dao.dto.UserDtoDao;
import com.tisserand.model.dto.ProductDto;
import com.tisserand.model.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class UserDtoServiceImplTest {
    @Mock
    private UserDtoDao userDtoDao;


    @InjectMocks
    private UserDtoServiceImpl userDtoServiceImpl;


    @Test
    void findAllProductWithNameOwner() {
        List<UserDto> users = Collections.singletonList(new UserDto());
        Mockito.when(userDtoDao.findAllUsersWithValueOfAllProducts()).thenReturn(users);
        List<UserDto> resultList = userDtoServiceImpl.findAllUsersWithValueOfAllProducts();

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(users, resultList);

        Mockito.verify(userDtoDao).findAllUsersWithValueOfAllProducts();
        Mockito.verifyNoMoreInteractions(userDtoDao);
    }
}

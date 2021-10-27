package com.tisserand.service.impl;

import com.tisserand.dao.UserDao;
import com.tisserand.model.User;
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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void findAll() {
        List<User> users = Collections.singletonList(new User());
        Mockito.when(userDao.findAll()).thenReturn(users);
        List<User> resultList = userServiceImpl.findAll();

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(users, resultList);

        Mockito.verify(userDao).findAll();
        Mockito.verifyNoMoreInteractions(userDao);
    }

    @Test
    void findById() {
        User user = new User();
        user.setUserName("Some user");
        user.setUserEmail("Some email");
        user.setUserMoney(100F);
        Integer userId = 1;
        Mockito.when(userDao.findById(userId)).thenReturn(Optional.of(user));
        Optional<User> result = userServiceImpl.findById(userId);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(user, result.get());

        Mockito.verify(userDao).findById(userId);
        Mockito.verifyNoMoreInteractions(userDao);
    }

    @Test
    void update() {
        User user = new User();
        user.setUserName("Some user");
        user.setUserEmail("Some email");
        user.setUserMoney(100F);
        Integer responseValue = 1;
        Mockito.when(userDao.update(user)).thenReturn(responseValue);
        Integer result = userServiceImpl.update(user);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseValue, result);

        Mockito.verify(userDao).update(user);
        Mockito.verifyNoMoreInteractions(userDao);
    }

}

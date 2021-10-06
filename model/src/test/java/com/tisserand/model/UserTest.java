package com.tisserand.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void getUserConstructor() {
        User user = new User("test name", "testname@gmail.com", 200F);
        Assertions.assertEquals("test name", user.getUserName());
        Assertions.assertEquals("testname@gmail.com", user.getUserEmail());
        Assertions.assertEquals(200F, user.getUserMoney());
    }

    @Test
    public void getUserNameSetter() {
        User user = new User();
        user.setUserName("test name");
        Assertions.assertEquals("test name", user.getUserName());
    }

    @Test
    public void getUserEmailSetter() {
        User user = new User();
        user.setUserEmail("testname@gmail.com");
        Assertions.assertEquals("testname@gmail.com", user.getUserEmail());
    }

    @Test
    public void getUserMoneySetter() {
        User user = new User();
        user.setUserMoney(200F);
        Assertions.assertEquals(200F, user.getUserMoney());
    }


}
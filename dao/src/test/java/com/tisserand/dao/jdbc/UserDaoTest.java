package com.tisserand.dao.jdbc;


import com.tisserand.dao.UserDao;
import com.tisserand.model.User;
import com.tisserand.testdb.SpringJdbcConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJdbcTest
@Import({UserDaoJdbc.class})
@PropertySource({"classpath:dao.properties"})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void findAllTest() {
        List<User> users = userDao.findAll();
        Assertions.assertNotNull(users);
        assertTrue(users.size() > 0);
    }


    @Test
    public void findByIdTest() {
        List<User> users = userDao.findAll();
        Assertions.assertNotNull(users);
        assertTrue(users.size() > 0);

        User user = users.get(0);
        Integer userId = user.getUserId();
        User realUser = userDao.findById(userId).get();
        Assertions.assertEquals(userId, realUser.getUserId());
        Assertions.assertEquals(user.getUserName(), realUser.getUserName());
        Assertions.assertEquals(user.getUserEmail(), realUser.getUserEmail());
        Assertions.assertEquals(user.getUserMoney(), realUser.getUserMoney());
        Assertions.assertEquals(user, realUser);
    }


    @Test
    public void updatePriceAndCustomerTest() {
        List<User> users = userDao.findAll();
        Assertions.assertNotNull(users);
        assertTrue(users.size() > 0);

        User user = users.get(0);
        user.setUserName("Test");
        user.setUserEmail("test@test.com");
        user.setUserMoney(244F);
        userDao.update(user);

        User realUser = userDao.findAll().get(0);
        Assertions.assertEquals(user.getUserName(), realUser.getUserName());
        Assertions.assertNotNull(user.getUserEmail(), realUser.getUserEmail());
        Assertions.assertEquals(user.getUserMoney(), realUser.getUserMoney());
    }

    @Test
    public void takeMoneyTest(){
        List<User> users = userDao.findAll();
        Assertions.assertNotNull(users);
        assertTrue(users.size() > 0);

        User user = users.get(0);
        userDao.takeMoney(10F,user.getUserId());

        User realUser = userDao.findAll().get(0);
        Assertions.assertEquals(user.getUserMoney() - 10F, realUser.getUserMoney());
    }

    @Test
    public void putMoneyTest(){
        List<User> users = userDao.findAll();
        Assertions.assertNotNull(users);
        assertTrue(users.size() > 0);

        User user = users.get(0);
        userDao.putMoney(10F,user.getUserId());

        User realUser = userDao.findAll().get(0);
        Assertions.assertEquals(user.getUserMoney() + 10F, realUser.getUserMoney());
    }
}

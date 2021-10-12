package com.tisserand.dao.jdbc;


import com.tisserand.dao.UserDtoDao;;
import com.tisserand.model.dto.UserDto;
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
@Import({UserDtoDaoJdbc.class})
@PropertySource({"classpath:dao.properties"})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserDtoDaoTest {

    @Autowired
    private UserDtoDao userDtoDao;

    @Test
    public void findAllUserWithNameOwnerTest() {
        List<UserDto> userDto = userDtoDao.findAllUsersWithValueOfAllProducts();
        Assertions.assertNotNull(userDto);
        assertTrue(userDto.size() > 0);

        UserDto realUserDto = userDto.get(0);
        Assertions.assertNotNull(realUserDto.getUserId());
        Assertions.assertNotNull(realUserDto.getUserName());
        Assertions.assertNotNull(realUserDto.getUserEmail());
        Assertions.assertNotNull(realUserDto.getUserMoney());
        Assertions.assertNotNull(realUserDto.getValueOfAllProducts());

    }
}

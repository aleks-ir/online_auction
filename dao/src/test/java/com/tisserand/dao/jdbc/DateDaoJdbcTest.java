package com.tisserand.dao.jdbc;

import com.tisserand.dao.DateDao;
import com.tisserand.testdb.SpringJdbcConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;


@DataJdbcTest
@Import({DateDaoJdbc.class})
@PropertySource({"classpath:dao.properties"})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DateDaoJdbcTest {

    @Autowired
    private DateDao dateDao;

    @Test
    public void getDateTest() {
        String date = dateDao.getDate();
        Assertions.assertNotNull(date);
    }
}

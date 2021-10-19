package com.tisserand.dao.jdbc;

import com.tisserand.dao.UserDtoDao;
import com.tisserand.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@PropertySource("classpath:dao.properties")
public class UserDtoDaoJdbc implements UserDtoDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${userDto.selectAllUsersWithValueOfAllProducts}")
    private String findAllUsersWithValueOfAllProductsSql;

    public UserDtoDaoJdbc(DataSource dataSource   ) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public List<UserDto> findAllUsersWithValueOfAllProducts() {
        List<UserDto> users = namedParameterJdbcTemplate.query(findAllUsersWithValueOfAllProductsSql,
                BeanPropertyRowMapper.newInstance(UserDto.class));
        return users;
    }


}

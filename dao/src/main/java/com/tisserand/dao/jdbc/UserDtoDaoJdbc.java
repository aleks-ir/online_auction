package com.tisserand.dao.jdbc;

import com.tisserand.dao.UserDtoDao;
import com.tisserand.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class UserDtoDaoJdbc implements UserDtoDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${userDto.findValueOfAllProducts}")
    private String findValueOfAllProducts;

    public UserDtoDaoJdbc(DataSource dataSource   ) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public List<UserDto> findValueOfAllProducts() {

        List<UserDto> users = namedParameterJdbcTemplate.query(findValueOfAllProducts,
                BeanPropertyRowMapper.newInstance(UserDto.class));
        return users;
    }

}

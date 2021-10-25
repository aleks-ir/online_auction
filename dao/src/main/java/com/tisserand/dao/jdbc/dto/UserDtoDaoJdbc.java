package com.tisserand.dao.jdbc.dto;

import com.tisserand.dao.dto.UserDtoDao;
import com.tisserand.model.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@PropertySource("classpath:dao.properties")
public class UserDtoDaoJdbc implements UserDtoDao, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDtoDaoJdbc.class);

    private NamedParameterJdbcTemplate template;

    @Value("${userDto.selectAllUsersWithValueOfAllProducts}")
    private String findAllUsersWithValueOfAllProductsSql;

    public UserDtoDaoJdbc(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void afterPropertiesSet() {
        if (template == null){
            LOGGER.error("NamedParameterJdbcTemplate was not injected");
            throw new BeanCreationException("NamedParameterJdbcTemplate is null on JdbcDepartmentDAO");}
    }


    @Override
    public List<UserDto> findAllUsersWithValueOfAllProducts() {
        LOGGER.debug("UserDtoDaoJdbc: findAllUsersWithValueOfAllProducts()");
        List<UserDto> users = template.query(findAllUsersWithValueOfAllProductsSql,
                BeanPropertyRowMapper.newInstance(UserDto.class));
        return users;
    }


}

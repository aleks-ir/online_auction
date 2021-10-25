package com.tisserand.dao.jdbc;

import com.tisserand.dao.UserDao;
import com.tisserand.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
@PropertySource("classpath:dao.properties")
public class UserDaoJdbc implements UserDao, InitializingBean {

    @Value("${user.selectAll}")
    private String selectSql;

    @Value("${user.update}")
    private String updateSql;

    @Value("${user.findById}")
    private String findByIdSql;

    @Value("${user.count}")
    private String countSql;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoJdbc.class);

    private NamedParameterJdbcTemplate template;

    private RowMapper rowMapper = BeanPropertyRowMapper.newInstance(User.class);

    public UserDaoJdbc(NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    @Override
    public void afterPropertiesSet() {
        if (template == null){
            LOGGER.error("NamedParameterJdbcTemplate was not injected");
            throw new BeanCreationException("NamedParameterJdbcTemplate is null on JdbcDepartmentDAO");}
    }

    @Override
    public List<User> findAll() {
        LOGGER.debug("UserDaoJdbc: findAll()");
        return template.query(selectSql, rowMapper);
    }

    @Override
    public Optional<User> findById(Integer userId) {
        LOGGER.debug("UserDaoJdbc: findById({})", userId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("USER_ID", userId);
        List<User> results = template.query(findByIdSql, sqlParameterSource, rowMapper);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results));
    }


    @Override
    public Integer update(User user) {
        LOGGER.debug("UserDaoJdbc: update({})", user);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("USER_NAME", user.getUserName())
                        .addValue("USER_EMAIL", user.getUserEmail())
                        .addValue("USER_MONEY", user.getUserMoney())
                        .addValue("USER_ID", user.getUserId());
        return template.update(updateSql, sqlParameterSource);
    }



    @Override
    public Integer count() {
        return template.queryForObject(countSql, new HashMap<>(), Integer.class);
    }
}

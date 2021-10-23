package com.tisserand.dao.jdbc;

import com.tisserand.dao.UserDao;
import com.tisserand.model.User;
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

    private NamedParameterJdbcTemplate template;

    private RowMapper rowMapper = BeanPropertyRowMapper.newInstance(User.class);

    public UserDaoJdbc(NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    @Override
    public void afterPropertiesSet() {
        if (template == null){
            throw new BeanCreationException("NamedParameterJdbcTemplate is null on JdbcDepartmentDAO");}
    }

    @Override
    public List<User> findAll() {
        return template.query(selectSql, rowMapper);
    }

    @Override
    public Optional<User> findById(Integer departmentId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("USER_ID", departmentId);
        List<User> results = template.query(findByIdSql, sqlParameterSource, rowMapper);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results));
    }


    @Override
    public Integer update(User user) {
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

package com.tisserand.dao.jdbc;

import com.tisserand.dao.UserDao;
import com.tisserand.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoJdbc implements UserDao {

    @Value("${user.selectAll}")
    private String selectSql;

    @Value("${user.update}")
    private String updateSql;

    @Value("${user.findById}")
    private String findByIdSql;

    @Value("${user.takeMoney}")
    private String takeMoneySql;

    @Value("${user.putMoney}")
    private String putMoneySql;

    @Value("${user.count}")
    private String countSql;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper rowMapper = BeanPropertyRowMapper.newInstance(User.class);

    public UserDaoJdbc(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> findAll() {
        return namedParameterJdbcTemplate.query(selectSql, rowMapper);
    }

    @Override
    public Optional<User> findById(Integer departmentId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("USER_ID", departmentId);
        List<User> results = namedParameterJdbcTemplate.query(findByIdSql, sqlParameterSource, rowMapper);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results));
    }


    @Override
    public Integer update(User user) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("USER_NAME", user.getUserName())
                        .addValue("USER_EMAIL", user.getUserEmail())
                        .addValue("USER_MONEY", user.getUserMoney())
                        .addValue("USER_ID", user.getUserId());
        return namedParameterJdbcTemplate.update(updateSql, sqlParameterSource);
    }

    @Override
    public Integer takeMoney(Float value, Integer userId) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("USER_ID", userId)
                        .addValue("VALUE", value);
        return namedParameterJdbcTemplate.update(takeMoneySql, sqlParameterSource);
    }

    @Override
    public Integer putMoney(Float value, Integer userId) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("USER_ID", userId)
                        .addValue("VALUE", value);
        return namedParameterJdbcTemplate.update(putMoneySql, sqlParameterSource);
    }


    @Override
    public Integer count() {
        return namedParameterJdbcTemplate.queryForObject(countSql, new HashMap<>(), Integer.class);
    }
}

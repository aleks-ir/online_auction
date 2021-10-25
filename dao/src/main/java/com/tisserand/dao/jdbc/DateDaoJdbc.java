package com.tisserand.dao.jdbc;

import com.tisserand.dao.DateDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:dao.properties")
public class DateDaoJdbc implements DateDao, InitializingBean {
    @Value("${date.getDate}")
    private String getDateSql;

    @Value("${date.update}")
    private String updateSql;

    final Integer dateId = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(DateDaoJdbc.class);

    private NamedParameterJdbcTemplate template;

    @Override
    public void afterPropertiesSet() {
        if (template == null){
            LOGGER.error("NamedParameterJdbcTemplate was not injected");
            throw new BeanCreationException("NamedParameterJdbcTemplate is null on JdbcDepartmentDAO");}
    }

    public DateDaoJdbc(NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    @Override
    public String getDate() {
        LOGGER.debug("DateDaoJdbc: getDate()");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("DATE_ID", dateId);
        return template.queryForObject(getDateSql, sqlParameterSource, String.class);
    }

    @Override
    public Integer update(String date) {
        LOGGER.debug("DateDaoJdbc: update({})", date);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("TEST_DATE", date);
        parameterSource.addValue("DATE_ID", dateId);
        return template.update(updateSql, parameterSource);
    }
}

package com.tisserand.dao.jdbc;

import com.tisserand.dao.PaymentDao;
import com.tisserand.model.Product;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@PropertySource("classpath:dao.properties")
public class PaymentDaoJdbc implements PaymentDao, InitializingBean {

    @Value("${user.takeMoney}")
    private String takeMoneySql;

    @Value("${user.putMoney}")
    private String putMoneySql;


    private NamedParameterJdbcTemplate template;

    @Override
    public void afterPropertiesSet() {
        if (template == null) {
            throw new BeanCreationException("NamedParameterJdbcTemplate is null on JdbcDepartmentDAO");
        }
    }

    public PaymentDaoJdbc(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Transactional
    @Override
    public void payment(Product product) {
        SqlParameterSource sqlParameterSourcePutMoney =
                new MapSqlParameterSource("USER_ID", product.getSalesmanId())
                        .addValue("VALUE", product.getProductPrice());

        SqlParameterSource sqlParameterSourceTakeMoney =
                new MapSqlParameterSource("USER_ID", product.getCustomerId())
                        .addValue("VALUE", product.getProductPrice());

        template.update(putMoneySql, sqlParameterSourcePutMoney);
        template.update(takeMoneySql, sqlParameterSourceTakeMoney);
    }
}

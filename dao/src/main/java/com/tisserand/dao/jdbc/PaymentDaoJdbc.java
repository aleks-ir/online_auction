package com.tisserand.dao.jdbc;

import com.tisserand.dao.PaymentDao;
import com.tisserand.model.Product;
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
import org.springframework.transaction.annotation.Transactional;


@Repository
@PropertySource("classpath:dao.properties")
public class PaymentDaoJdbc implements PaymentDao, InitializingBean {

    @Value("${user.takeMoney}")
    private String takeMoneySql;

    @Value("${user.putMoney}")
    private String putMoneySql;

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentDaoJdbc.class);

    private NamedParameterJdbcTemplate template;

    @Override
    public void afterPropertiesSet() {
        if (template == null) {
            LOGGER.error("NamedParameterJdbcTemplate was not injected");
            throw new BeanCreationException("NamedParameterJdbcTemplate is null on JdbcDepartmentDAO");
        }
    }

    public PaymentDaoJdbc(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Transactional
    @Override
    public void payment(Product product) {
        LOGGER.debug("PaymentDaoJdbc: payment({})", product);
        SqlParameterSource sqlParameterSourcePutMoney =
                new MapSqlParameterSource("USER_ID", product.getSalesmanId())
                        .addValue("VALUE", product.getProductPrice());

        SqlParameterSource sqlParameterSourceTakeMoney =
                new MapSqlParameterSource("USER_ID", product.getCustomerId())
                        .addValue("VALUE", product.getProductPrice());

        template.update(putMoneySql, sqlParameterSourcePutMoney);
        template.update(takeMoneySql, sqlParameterSourceTakeMoney);
    }


    @Transactional
    @Override
    public void refund(Product product) {
        LOGGER.debug("PaymentDaoJdbc: refund({})", product);
        SqlParameterSource sqlParameterSourcePutMoney =
                new MapSqlParameterSource("USER_ID", product.getSalesmanId())
                        .addValue("VALUE", product.getProductPrice());

        template.update(putMoneySql, sqlParameterSourcePutMoney);
    }


    @Transactional
    @Override
    public void withdraw(Product product) {
        LOGGER.debug("PaymentDaoJdbc: withdraw({})", product);
        SqlParameterSource sqlParameterSourceTakeMoney =
                new MapSqlParameterSource("USER_ID", product.getSalesmanId())
                        .addValue("VALUE", product.getProductPrice());

        template.update(takeMoneySql, sqlParameterSourceTakeMoney);
    }
}

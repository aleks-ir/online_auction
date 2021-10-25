package com.tisserand.dao.jdbc.dto;

import com.tisserand.dao.dto.ProductDtoDao;
import com.tisserand.model.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@PropertySource("classpath:dao.properties")
public class ProductDtoDaoJdbc implements ProductDtoDao, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDtoDaoJdbc.class);

    private NamedParameterJdbcTemplate template;

    @Value("${productDto.selectAllProductWithNameOwner}")
    private String findAllProductWithNameOwnerSql;

    @Value("${productDto.selectAllProductWithNameOwnerByDate}")
    private String findAllProductWithNameOwnerByDateSql;

    @Override
    public void afterPropertiesSet() {
        if (template == null){
            LOGGER.error("NamedParameterJdbcTemplate was not injected");
            throw new BeanCreationException("NamedParameterJdbcTemplate is null on JdbcDepartmentDAO");}
    }

    public ProductDtoDaoJdbc(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    private RowMapper rowMapper = BeanPropertyRowMapper.newInstance(ProductDto.class);

    @Override
    public List<ProductDto> findAllProductWithNameOwner() {
        LOGGER.debug("ProductDtoDaoJdbc: findAllProductWithNameOwner()");
        List<ProductDto> products = template.query(findAllProductWithNameOwnerSql,
                rowMapper);
        return products;
    }

    @Override
    public List<ProductDto> findAllProductWithNameOwnerByDate(String startDate, String endDate) {
        LOGGER.debug("ProductDtoDaoJdbc: findAllProductWithNameOwnerByDate({} {})", startDate, endDate);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("START_DATE", startDate);
        parameterSource.addValue("END_DATE", endDate);

        return template.query(findAllProductWithNameOwnerByDateSql, parameterSource, rowMapper);
    }
}

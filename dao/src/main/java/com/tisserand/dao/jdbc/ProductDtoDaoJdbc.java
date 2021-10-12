package com.tisserand.dao.jdbc;

import com.tisserand.dao.ProductDtoDao;
import com.tisserand.model.dto.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class ProductDtoDaoJdbc implements ProductDtoDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${productDto.selectAllProductWithNameOwner}")
    private String findAllProductWithNameOwnerSql;

    public ProductDtoDaoJdbc(DataSource dataSource   ) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<ProductDto> findAllProductWithNameOwner() {
        List<ProductDto> products = namedParameterJdbcTemplate.query(findAllProductWithNameOwnerSql,
                BeanPropertyRowMapper.newInstance(ProductDto.class));
        return products;
    }
}

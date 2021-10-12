package com.tisserand.dao.jdbc;

import com.tisserand.dao.ProductDao;
import com.tisserand.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ProductDaoJdbc implements ProductDao {

    @Value("${product.selectAll}")
    private String findAllSql;

    @Value("${product.selectAllIdByDate}")
    private String findAllIdByDateSql;

    @Value("${product.selectAllWithSortByName}")
    private String findAllWithSortByNameSql;

    @Value("${product.selectAllWithSortByDate}")
    private String findAllWithSortByDateSql;

    @Value("${product.create}")
    private String createSql;

    @Value("${product.updatePriceAndCustomer}")
    private String updatePriceAndCustomerSql;

    @Value("${product.findById}")
    private String findByIdSql;

    @Value("${product.check}")
    private String checkSql;

    @Value("${product.count}")
    private String countSql;

    @Value("${product.delete}")
    private String deleteSql;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Product.class);

    public ProductDaoJdbc(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Product> findAll() {
        return namedParameterJdbcTemplate.query(findAllSql, rowMapper);
    }

    @Override
    public List<Product> findAllIdByDate(String date) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("PRODUCT_DATE", date);
        return namedParameterJdbcTemplate.query(findAllIdByDateSql, sqlParameterSource, rowMapper);
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("PRODUCT_ID", productId);
        List<Product> results = namedParameterJdbcTemplate.query(findByIdSql, sqlParameterSource, rowMapper);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results));
    }

    @Override
    public Integer create(Product product) {
        if (!isProductNameUnique(product)) {
            throw new IllegalArgumentException("Product with the same name already exists in DB.");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("PRODUCT_NAME", product.getProductName())
                .addValue("PRODUCT_PRICE", product.getProductPrice())
                .addValue("PRODUCT_DATE", product.getProductDate())
                .addValue("SALESMAN_ID", product.getSalesmanId());
        namedParameterJdbcTemplate.update(createSql, sqlParameterSource, keyHolder);
        Integer productId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        product.setProductId(productId);
        return productId;
    }

    private boolean isProductNameUnique(Product product) {
        return namedParameterJdbcTemplate.queryForObject(checkSql,
                new MapSqlParameterSource("PRODUCT_NAME", product.getProductName()), Integer.class) == 0;
    }

    @Override
    public Integer updatePriceAndCustomer(Product product) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("PRODUCT_PRICE", product.getProductPrice())
                        .addValue("CUSTOMER_ID", product.getCustomerId())
                        .addValue("PRODUCT_ID", product.getProductId());
        return namedParameterJdbcTemplate.update(updatePriceAndCustomerSql, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer productId) {
        return namedParameterJdbcTemplate.update(deleteSql, new MapSqlParameterSource()
                .addValue("PRODUCT_ID", productId));
    }

    @Override
    public Integer count() {
        return namedParameterJdbcTemplate.queryForObject(countSql, new HashMap<>(), Integer.class);
    }


}

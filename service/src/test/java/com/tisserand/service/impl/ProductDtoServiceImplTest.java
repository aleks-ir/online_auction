package com.tisserand.service.impl;

import com.tisserand.dao.jdbc.ProductDtoDaoJdbc;
import com.tisserand.model.dto.ProductDto;
import com.tisserand.service.ProductDtoService;
import com.tisserand.testdb.SpringJdbcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import({ProductDtoServiceImpl.class, ProductDtoDaoJdbc.class})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@ComponentScan(basePackages = {"com.tisserand.dao", "com.tisserand.testdb"})
@PropertySource({"classpath:dao.properties"})
@Transactional
public class ProductDtoServiceImplTest {
    @Autowired
    ProductDtoService productDtoService;

    @Test
    public void shouldFindAllWithAvgSalary() {
        List<ProductDto> products = productDtoService.findAllProductWithNameOwner();
        assertNotNull(products);

        assertTrue(products.size() > 0);
    }
}

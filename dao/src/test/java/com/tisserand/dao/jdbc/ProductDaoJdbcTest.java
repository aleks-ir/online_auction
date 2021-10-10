package com.tisserand.dao.jdbc;

import com.tisserand.dao.ProductDao;
import com.tisserand.model.Product;
import com.tisserand.testdb.SpringJdbcConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJdbcTest
@Import({ProductDaoJdbc.class})
@PropertySource({"classpath:dao.properties"})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductDaoJdbcTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void findAllTest() {
        List<Product> products = productDao.findAll();
        Assertions.assertNotNull(products);
        assertTrue(products.size() > 0);
    }

    @Test
    public void findByIdTest() {
        List<Product> products = productDao.findAll();
        Assertions.assertNotNull(products);
        assertTrue(products.size() > 0);

        Integer productId = products.get(0).getProductId();
        Product expDepartment = productDao.findById(productId).get();
        Assertions.assertEquals(productId, expDepartment.getProductId());
        Assertions.assertEquals(products.get(0).getProductName(), expDepartment.getProductName());
        Assertions.assertEquals(products.get(0), expDepartment);
    }

    @Test
    public void findByIdExceptionalTest() {

        Optional<Product> optionalDepartment = productDao.findById(999);
        assertTrue(optionalDepartment.isEmpty());
    }

    @Test
    public void createDepartmentTest() {
        List<Product> products = productDao.findAll();
        Assertions.assertNotNull(products);
        assertTrue(products.size() > 0);

        productDao.create(new Product("Dresse", 10F, "2022-01-07", 2));

        List<Product> realProducts = productDao.findAll();
        Assertions.assertEquals(products.size() + 1, realProducts.size());
    }

}

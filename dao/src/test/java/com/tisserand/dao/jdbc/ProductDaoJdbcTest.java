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
    public void findAllByDateTest() {
        productDao.create(new Product("Test", 800F, "2020-02-12", 2));
        List<Product> products = productDao.findAllIdByDate("2020-02-12");
        Assertions.assertNotNull(products);
        assertTrue(products.size() > 0);
    }


    @Test
    public void findByIdTest() {
        List<Product> products = productDao.findAll();
        Assertions.assertNotNull(products);
        assertTrue(products.size() > 0);

        Product product = products.get(0);
        Integer productId = product.getProductId();
        Product realProduct = productDao.findById(productId).get();
        Assertions.assertEquals(productId, realProduct.getProductId());
        Assertions.assertEquals(product.getProductName(), realProduct.getProductName());
        Assertions.assertEquals(product.getProductPrice(), realProduct.getProductPrice());
        Assertions.assertEquals(product.getProductDate(), realProduct.getProductDate());
        Assertions.assertEquals(product.getSalesmanId(), realProduct.getSalesmanId());
        Assertions.assertEquals(product, realProduct);
    }


    @Test
    public void createTest() {
        List<Product> products = productDao.findAll();
        Assertions.assertNotNull(products);
        assertTrue(products.size() > 0);

        productDao.create(new Product("Test", 800F, "2020-02-12", 2));
        List<Product> realProducts = productDao.findAll();
        Assertions.assertEquals(products.size() + 1, realProducts.size());
        Product realProduct = realProducts.get(realProducts.size() - 1);
        Assertions.assertEquals("Test", realProduct.getProductName());
        Assertions.assertEquals(800F, realProduct.getProductPrice());
        Assertions.assertEquals("2020-02-12", realProduct.getProductDate());
        Assertions.assertEquals(2, realProduct.getSalesmanId());


        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            productDao.create(new Product("Test", 800F, "2020-02-12", 2));
        });
    }

    @Test
    public void updatePriceAndCustomerTest() {
        List<Product> products = productDao.findAll();
        Assertions.assertNotNull(products);
        assertTrue(products.size() > 0);

        Product product = products.get(0);
        product.setProductPrice(100F);
        product.setCustomerId(1);
        productDao.updatePriceAndCustomer(product);

        Product realProduct = productDao.findAll().get(0);
        Assertions.assertEquals(100F, realProduct.getProductPrice());
        Assertions.assertNotNull(realProduct.getCustomerId());
        Assertions.assertEquals(1, realProduct.getCustomerId());
    }

    @Test
    public void deleteTest(){
        List<Product> products = productDao.findAll();
        Assertions.assertNotNull(products);
        assertTrue(products.size() > 0);

        productDao.delete(1);
        List<Product> realProducts = productDao.findAll();
        Assertions.assertEquals(products.size() - 1, realProducts.size());
    }




}

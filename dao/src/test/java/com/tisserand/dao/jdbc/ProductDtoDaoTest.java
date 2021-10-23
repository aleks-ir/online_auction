package com.tisserand.dao.jdbc;

import com.tisserand.dao.jdbc.dto.ProductDtoDaoJdbc;
import com.tisserand.model.dto.ProductDto;
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
@Import({ProductDtoDaoJdbc.class, ProductDaoJdbc.class})
@PropertySource({"classpath:dao.properties"})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductDtoDaoTest {

    @Autowired
    private ProductDtoDaoJdbc productDtoDao;

    @Autowired
    private ProductDaoJdbc productDao;

    @Test
    public void findAllProductWithNameOwnerTest() {
        List<ProductDto> productsDto = productDtoDao.findAllProductWithNameOwner();
        Assertions.assertNotNull(productsDto);
        assertTrue(productsDto.size() > 0);


        ProductDto realProductDto = productsDto.get(0);
        Assertions.assertNotNull(realProductDto.getProductId());
        Assertions.assertNotNull(realProductDto.getProductName());
        Assertions.assertNotNull(realProductDto.getProductDate());
        Assertions.assertNotNull(realProductDto.getProductPrice());
        Assertions.assertNotNull(realProductDto.getNameOwner());

    }

    @Test
    public void findAllProductWithNameOwnerByDateTest() {
        String date = productDao.findById(1).get().getProductDate();
        List<ProductDto> productsDto = productDtoDao.findAllProductWithNameOwnerByDate(date, date);
        Assertions.assertNotNull(productsDto);
        assertTrue(productsDto.size() > 0);



    }
}

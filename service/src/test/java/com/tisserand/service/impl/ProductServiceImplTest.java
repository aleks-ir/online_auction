package com.tisserand.service.impl;

import com.tisserand.dao.ProductDao;
import com.tisserand.model.Product;
import com.tisserand.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Test
    void findAll() {
        List<Product> users = Collections.singletonList(new Product());
        Mockito.when(productDao.findAll()).thenReturn(users);
        List<Product> resultList = productServiceImpl.findAll();

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(users, resultList);

        Mockito.verify(productDao).findAll();
        Mockito.verifyNoMoreInteractions(productDao);
    }

    @Test
    void findById() {
        Product product = new Product();
        product.setProductName("Some product");
        product.setProductPrice(20F);
        product.setProductDate("2000-01-01");
        product.setSalesmanId(2);
        Integer productId = 1;
        Mockito.when(productDao.findById(productId)).thenReturn(Optional.of(product));
        Optional<Product> result = productServiceImpl.findById(productId);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(product, result.get());

        Mockito.verify(productDao).findById(productId);
        Mockito.verifyNoMoreInteractions(productDao);
    }

    @Test
    void create() {
        Product product = new Product();
        product.setProductName("Some product");
        product.setProductPrice(20F);
        product.setProductDate("2000-01-01");
        product.setSalesmanId(2);
        Integer productId = 1;
        Mockito.when(productDao.create(product)).thenReturn(productId);
        Integer resultSongId = productServiceImpl.create(product);

        Assertions.assertNotNull(resultSongId);
        Assertions.assertEquals(productId, resultSongId);

        Mockito.verify(productDao).create(product);
        Mockito.verifyNoMoreInteractions(productDao);
    }

    @Test
    void delete() {
        Integer productId = 5;
        Integer responseValue = 1;
        Mockito.when(productDao.delete(productId)).thenReturn(responseValue);
        Integer result = productServiceImpl.delete(productId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(responseValue, result);

        Mockito.verify(productDao).delete(productId);
        Mockito.verifyNoMoreInteractions(productDao);
    }

}

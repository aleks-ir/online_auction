package com.tisserand.service.impl.dto;

import com.tisserand.dao.DateDao;
import com.tisserand.dao.ProductDao;
import com.tisserand.dao.dto.ProductDtoDao;
import com.tisserand.model.dto.ProductDto;
import com.tisserand.service.impl.DateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductDtoServiceImplTest {

    @Mock
    private ProductDtoDao productDtoDao;


    @InjectMocks
    private ProductDtoServiceImpl productDtoServiceImpl;


    @Test
    void findAllProductWithNameOwner() {
        List<ProductDto> products = Collections.singletonList(new ProductDto());
        Mockito.when(productDtoDao.findAllProductWithNameOwner()).thenReturn(products);
        List<ProductDto> resultList = productDtoServiceImpl.findAllProductWithNameOwner();

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(products, resultList);

        Mockito.verify(productDtoDao).findAllProductWithNameOwner();
        Mockito.verifyNoMoreInteractions(productDtoDao);
    }

    @Test
    void findAllProductWithNameOwnerByDate() {
        List<ProductDto> products = Collections.singletonList(new ProductDto());
        String startDate = "2000-01-01";
        String endDate = "2001-01-01";
        Mockito.when(productDtoDao.findAllProductWithNameOwnerByDate(startDate, endDate)).thenReturn(products);
        List<ProductDto> resultList = productDtoServiceImpl.findAllProductWithNameOwnerByDate(startDate, endDate);

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(products, resultList);

        Mockito.verify(productDtoDao).findAllProductWithNameOwnerByDate(startDate, endDate);
        Mockito.verifyNoMoreInteractions(productDtoDao);
    }

}

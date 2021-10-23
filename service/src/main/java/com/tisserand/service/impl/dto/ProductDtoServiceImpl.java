package com.tisserand.service.impl.dto;

import com.tisserand.dao.dto.ProductDtoDao;
import com.tisserand.model.dto.ProductDto;
import com.tisserand.service.dto.ProductDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductDtoServiceImpl implements ProductDtoService {

    private final ProductDtoDao productDtoDao;

    @Autowired
    public ProductDtoServiceImpl(ProductDtoDao productDtoDao) {
        this.productDtoDao = productDtoDao;
    }


    @Override
    public List<ProductDto> findAllProductWithNameOwner() {
        return productDtoDao.findAllProductWithNameOwner();
    }

    @Override
    public List<ProductDto> findAllProductWithNameOwnerByDate(String startDate, String endDate) {
        return productDtoDao.findAllProductWithNameOwnerByDate(startDate, endDate);
    }
}

package com.tisserand.service.impl.dto;

import com.tisserand.dao.dto.ProductDtoDao;
import com.tisserand.model.dto.ProductDto;
import com.tisserand.service.dto.ProductDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductDtoServiceImpl implements ProductDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDtoServiceImpl.class);

    private final ProductDtoDao productDtoDao;

    @Autowired
    public ProductDtoServiceImpl(ProductDtoDao productDtoDao) {
        this.productDtoDao = productDtoDao;
    }


    @Override
    public List<ProductDto> findAllProductWithNameOwner() {
        LOGGER.debug("ProductDtoServiceImpl: findAllProductWithNameOwner({})");
        return productDtoDao.findAllProductWithNameOwner();
    }

    @Override
    public List<ProductDto> findAllProductWithNameOwnerByDate(String startDate, String endDate) {
        LOGGER.debug("ProductDtoServiceImpl: update({} {})", startDate, endDate);
        return productDtoDao.findAllProductWithNameOwnerByDate(startDate, endDate);
    }
}

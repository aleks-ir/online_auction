package com.tisserand.service.impl;

import com.tisserand.dao.ProductDao;
import com.tisserand.model.Product;
import com.tisserand.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }


    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        return productDao.findById(productId);
    }

    @Override
    public Integer create(Product product) {
        return productDao.create(product);
    }

    @Override
    public Integer delete(Integer productId) {
        return productDao.delete(productId);
    }

    @Override
    public Integer count() {
        return productDao.count();
    }

    @Override
    public List<Product> findAllIdByDate(String date) {
        return productDao.findAllIdByDate(date);
    }

    @Override
    public Integer updatePriceAndCustomer(Product product) {
        return productDao.updatePriceAndCustomer(product);
    }
}

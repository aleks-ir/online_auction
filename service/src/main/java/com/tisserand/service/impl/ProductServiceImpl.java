package com.tisserand.service.impl;

import com.tisserand.dao.PaymentDao;
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
    private final PaymentService paymentService;

    @Autowired
    public ProductServiceImpl(ProductDao productDao, PaymentService paymentService) {
        this.productDao = productDao;
        this.paymentService = paymentService;
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
    public List<Product> findAllByDate(String date) {
        return productDao.findAllByDate(date);
    }

    @Override
    public Integer updatePriceAndCustomer(Product product) {
        Float oldProductPrice = findById(product.getProductId()).get().getProductPrice();
        Float newProductPrice = product.getProductPrice();
        if(newProductPrice > oldProductPrice){
            return productDao.updatePriceAndCustomer(product);
        }
        else throw new SecurityException("Incorrect price");
    }
}

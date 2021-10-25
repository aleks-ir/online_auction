package com.tisserand.service.impl;

import com.tisserand.dao.PaymentDao;
import com.tisserand.dao.ProductDao;
import com.tisserand.model.Product;
import com.tisserand.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductDao productDao;
    private final PaymentService paymentService;

    @Autowired
    public ProductServiceImpl(ProductDao productDao, PaymentService paymentService) {
        this.productDao = productDao;
        this.paymentService = paymentService;
    }


    @Override
    public List<Product> findAll() {
        LOGGER.debug("ProductServiceImpl: findById()");
        return productDao.findAll();
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        LOGGER.debug("ProductServiceImpl: findById({})", productId);
        return productDao.findById(productId);
    }

    @Override
    public Integer create(Product product) {
        LOGGER.debug("ProductServiceImpl: create({})", product);
        paymentService.withdraw(product);
        return productDao.create(product);
    }

    @Override
    public Integer delete(Integer productId) {
        LOGGER.debug("ProductServiceImpl: delete({})", productId);
        paymentService.refund(findById(productId).get());
        return productDao.delete(productId);
    }

    @Override
    public List<Product> findAllIdByDate(String date) {
        LOGGER.debug("ProductServiceImpl: findAllIdByDate({})", date);
        return productDao.findAllByDate(date);
    }

    @Override
    public Integer updatePriceAndCustomer(Product product) {
        LOGGER.debug("ProductServiceImpl: updatePriceAndCustomer({})", product);
        Float oldProductPrice = findById(product.getProductId()).get().getProductPrice();
        Float newProductPrice = product.getProductPrice();
        if(newProductPrice > oldProductPrice){
            return productDao.updatePriceAndCustomer(product);
        }
        else throw new SecurityException("Incorrect price");
    }

    @Override
    public Integer count() {
        return productDao.count();
    }
}

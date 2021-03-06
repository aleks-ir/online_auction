package com.tisserand.service.impl;

import com.tisserand.dao.PaymentDao;
import com.tisserand.dao.ProductDao;
import com.tisserand.dao.UserDao;
import com.tisserand.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentService {
    private final ProductDao productDao;
    private final PaymentDao paymentDao;
    private final UserDao userDao;

    @Autowired
    public PaymentService(ProductDao productDao, PaymentDao paymentDao, UserDao userDao) {
        this.productDao = productDao;
        this.paymentDao = paymentDao;
        this.userDao = userDao;
    }

    private boolean checkProductOnAccountability(Product product){
        Integer customerId = product.getCustomerId();
        if(customerId != null) {
            if (userDao.findById(customerId).get().getUserMoney() > product.getProductPrice() ) {
                return true;
            }
        }
        return false;
    }

    public void payment(String date) {
        List<Product> products = productDao.findAllByDate(date);
        for (Product product : products) {
            if(checkProductOnAccountability(product)){
                paymentDao.payment(product);
            }
            productDao.delete(product.getProductId());
        }
    }





}

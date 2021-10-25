package com.tisserand.service.impl;

import com.tisserand.dao.PaymentDao;
import com.tisserand.dao.ProductDao;
import com.tisserand.dao.UserDao;
import com.tisserand.model.Product;
import com.tisserand.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

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
        LOGGER.debug("PaymentService: checkProductOnAccountability({})", product);
        Integer customerId = product.getCustomerId();
        if(customerId != null) {
            if (userDao.findById(customerId).get().getUserMoney() > product.getProductPrice() ) {
                return true;
            }
        }
        return false;
    }

    public void payment(String date) {
        LOGGER.debug("PaymentService: payment({})", date);
        List<Product> products = productDao.findAllByDate(date);
        for (Product product : products) {
            if(checkProductOnAccountability(product)){
                paymentDao.payment(product);
            }else {
                paymentDao.refund(product);
            }
            productDao.delete(product.getProductId());
        }
    }

    public void withdraw(Product product) {
        LOGGER.debug("PaymentService: withdraw({})", product);
        Integer userId = product.getSalesmanId();
        User user = userDao.findById(userId).get();
        if(user.getUserMoney() > product.getProductPrice()){
            paymentDao.withdraw(product);
        }else throw new SecurityException("Not enough money in the account");
    }

    public void refund(Product product) {
        LOGGER.debug("PaymentService: refund({})", product);
        paymentDao.refund(product);
    }




}

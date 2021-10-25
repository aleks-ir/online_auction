package com.tisserand.dao;

import com.tisserand.model.Product;

public interface PaymentDao {
    void payment(Product product);

    void refund(Product product);

    void withdraw(Product product);
}

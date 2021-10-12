package com.tisserand.dao;

import com.tisserand.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    List<Product> findAll();

    Optional<Product> findById(Integer productId);

    Integer create(Product product);

    Integer delete(Integer productId);

    Integer count();

    List<Product> findAllIdByDate(String date);

    Integer updatePriceAndCustomer(Product product);
}

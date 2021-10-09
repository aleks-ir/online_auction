package com.tisserand.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    public void getProductConstructor() {
        Product product = new Product("test product", 21.0F, "2000-02-21", 2);
        Assertions.assertEquals("test product", product.getProductName());
        Assertions.assertEquals(21.0F, product.getProductPrice());
        Assertions.assertEquals("2000-02-21", product.getProductDate());
        Assertions.assertEquals(2, product.getSalesmanId());
    }

    @Test
    public void getProductNameSetter() {
        Product product = new Product();
        product.setProductName("test product");
        Assertions.assertEquals("test product", product.getProductName());
    }

    @Test
    public void getProductPriceSetter() {
        Product product = new Product();
        product.setProductPrice(20.0F);
        Assertions.assertEquals(20.0F, product.getProductPrice());
    }

    @Test
    public void getProductDateSetter() {
        Product product = new Product();
        product.setProductDate("2000-02-21");
        Assertions.assertEquals("2000-02-21", product.getProductDate());
    }

    @Test
    public void getProductSalesmanIdSetter() {
        Product product = new Product();
        product.setSalesmanId(2);
        Assertions.assertEquals(2, product.getSalesmanId());
    }

}
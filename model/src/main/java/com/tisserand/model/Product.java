package com.tisserand.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Objects;

public class Product {

    @Positive(message = "Id id should be positive")
    private Integer productId;

    @NotBlank(message = "Name should not be empty")
    @Size(min = 1, max = 30, message = "Name should be between 1 and 30 characters")
    private String productName;

    @NotNull(message = "Prise should not be empty")
    @Min(value = 0, message = "Price should be greater than zero")
    private Float productPrice;

    @NotBlank(message = "Date should not be empty")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)

    private String productDate;

    @Positive(message = "Salesman id should be positive")
    private Integer salesmanId;

    private Integer customerId;


    public Product(String productName, Float productPrice, String productDate, Integer salesmanId) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDate = productDate;
        this.salesmanId = salesmanId;
    }

    public Product(){

    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public Integer getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(Integer salesmanId) {
        this.salesmanId = salesmanId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDate='" + productDate + '\'' +
                ", salesmanId=" + salesmanId +
                ", costumerId=" + customerId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) && Objects.equals(productName, product.productName) && Objects.equals(productPrice, product.productPrice) && Objects.equals(productDate, product.productDate) && Objects.equals(salesmanId, product.salesmanId) && Objects.equals(customerId, product.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, productPrice, productDate, salesmanId, customerId);
    }




}


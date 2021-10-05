package com.tisserand.model;

import java.util.Objects;

public class Product {


    private Integer productId;

    private String productName;

    private Float productPrice;

    private String productDate;

    private Integer salesmanId;

    private Integer costumerId;

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

    public Integer getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(Integer costumerId) {
        this.costumerId = costumerId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDate='" + productDate + '\'' +
                ", salesmanId=" + salesmanId +
                ", costumerId=" + costumerId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) && Objects.equals(productName, product.productName) && Objects.equals(productPrice, product.productPrice) && Objects.equals(productDate, product.productDate) && Objects.equals(salesmanId, product.salesmanId) && Objects.equals(costumerId, product.costumerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, productPrice, productDate, salesmanId, costumerId);
    }




}


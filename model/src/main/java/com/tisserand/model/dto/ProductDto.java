package com.tisserand.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

public class ProductDto {

    @Positive(message = "Id id should be positive")
    private Integer productId;

    @NotBlank(message = "Name should not be empty")
    @Size(min = 1, max = 30, message = "Product name should be between 1 and 30 characters")
    private String productName;

    @NotNull(message = "Prise should not be empty")
    @Min(value = 0, message = "Price should be greater than zero")
    private Float productPrice;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String productDate;

    private String nameOwner;

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

    public String getNameOwner() {
        return nameOwner;
    }

    public void setNameOwner(String nameOwner) {
        this.nameOwner = nameOwner;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productDate='" + productDate + '\'' +
                ", nameOwner='" + nameOwner + '\'' +
                '}';
    }
}

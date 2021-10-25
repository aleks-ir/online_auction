package com.tisserand.model.dto;

import javax.validation.constraints.*;

public class UserDto {

    @Positive(message = "User id should be positive")
    private Integer userId;

    @NotBlank(message = "Name should not be empty")
    @Size(min = 1, max = 30, message = "User name should be between 1 and 30 characters")
    private String userName;

    @NotBlank(message = "Email should not be empty")
    @Size(min = 1, max = 30, message = "Email should be between 1 and 30 characters")
    private String userEmail;

    @NotNull(message = "Money should not be empty")
    @Min(value = 0, message = "Money should be greater than zero")
    private Float userMoney;

    private Float valueOfAllProducts;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Float getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(Float userMoney) {
        this.userMoney = userMoney;
    }

    public Float getValueOfAllProducts() {
        return valueOfAllProducts;
    }

    public void setValueOfAllProducts(Float valueOfAllProducts) {
        this.valueOfAllProducts = valueOfAllProducts;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userMoney=" + userMoney +
                ", valueOfAllProducts=" + valueOfAllProducts +
                '}';
    }

}

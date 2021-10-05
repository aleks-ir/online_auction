package com.tisserand.model.dto;

public class UserDto {

    private Integer userId;

    private String userName;

    private String userEmail;

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

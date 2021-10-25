package com.tisserand.model;

import javax.validation.constraints.*;
import java.util.Objects;

public class User {

    @Positive(message = "User id should be positive")
    private Integer userId;

    @NotBlank(message = "Name should not be empty")
    @Size(min = 1, max = 30, message = "Name should be between 1 and 30 characters")
    private String userName;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email should not be empty")
    @Size(min = 1, max = 30, message = "Email should be between 1 and 30 characters")
    private String userEmail;

    @NotNull(message = "Money should not be empty")
    @Min(value = 0, message = "Money should be greater than zero")
    private Float userMoney;

    public User(String userName, String userEmail, Float userMoney) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userMoney = userMoney;
    }

    public User() {
    }

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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userMoney=" + userMoney +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(userName, user.userName) && Objects.equals(userEmail, user.userEmail) && Objects.equals(userMoney, user.userMoney);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, userEmail, userMoney);
    }

}

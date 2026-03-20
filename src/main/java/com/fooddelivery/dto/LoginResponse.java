package com.fooddelivery.dto;

import com.fooddelivery.entity.User;

public class LoginResponse {
    private String token;
    private User user;
    private String message;
    private boolean success;
    
    public LoginResponse() {
    }
    
    public LoginResponse(String token, User user, String message, boolean success) {
        this.token = token;
        this.user = user;
        this.message = message;
        this.success = success;
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
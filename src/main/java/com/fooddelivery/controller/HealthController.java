package com.fooddelivery.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    
    @GetMapping("/health")
    public String health() {
        return "Food Delivery App is running!";
    }
    
    @GetMapping("/")
    public String home() {
        return "Welcome to Food Delivery API";
    }
}
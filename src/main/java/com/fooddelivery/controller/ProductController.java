package com.fooddelivery.controller;

import com.fooddelivery.dto.ProductDto;
import com.fooddelivery.dto.ApiResponse;
import com.fooddelivery.entity.Category;
import com.fooddelivery.entity.Product;
import com.fooddelivery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://localhost:3000",
    "https://java-full-stack-project-ivory.vercel.app"
})

public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setStatus(product.getStatus());
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }
        return dto;
    }
    
    @GetMapping
    public ApiResponse<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.getAllAvailableProducts();
        List<ProductDto> productDtos = products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ApiResponse.success(productDtos);
    }
    
    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<ProductDto>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        List<ProductDto> productDtos = products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ApiResponse.success(productDtos);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<ProductDto> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ApiResponse.error("Product not found");
        }
        return ApiResponse.success(convertToDto(product));
    }
    
    @GetMapping("/categories")
    public ApiResponse<List<Category>> getAllCategories() {
        return ApiResponse.success(productService.getAllCategories());
    }
}
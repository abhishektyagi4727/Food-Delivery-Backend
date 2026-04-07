package com.fooddelivery.controller;

import com.fooddelivery.dto.ApiResponse;
import com.fooddelivery.dto.CategoryDto;
import com.fooddelivery.dto.ProductDto;
import com.fooddelivery.entity.Category;
import com.fooddelivery.entity.Product;
import com.fooddelivery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://localhost:3000",
    "https://java-full-stack-project-ivory.vercel.app"
})

public class CategoryController {
    
    @Autowired
    private ProductService productService;
    
    // Convert Product to ProductDto
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
    
    // Convert Category to CategoryDto
    private CategoryDto convertToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setCreatedAt(category.getCreatedAt());
        
        // Convert products list to ProductDto list (avoiding circular reference)
        if (category.getProducts() != null && !category.getProducts().isEmpty()) {
            List<ProductDto> productDtos = category.getProducts().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
            dto.setProducts(productDtos);
        }
        
        return dto;
    }
    
    @GetMapping
    public ApiResponse<List<CategoryDto>> getAllCategories() {
        List<Category> categories = productService.getAllCategories();
        List<CategoryDto> categoryDtos = categories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ApiResponse.success(categoryDtos);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<CategoryDto> getCategoryById(@PathVariable Long id) {
        Category category = productService.getAllCategories().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
        
        if (category == null) {
            return ApiResponse.error("Category not found");
        }
        
        return ApiResponse.success(convertToDto(category));
    }
}
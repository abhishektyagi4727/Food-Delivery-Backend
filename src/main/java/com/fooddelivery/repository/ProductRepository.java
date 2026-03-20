package com.fooddelivery.repository;

import com.fooddelivery.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Find products by category
    List<Product> findByCategoryId(Long categoryId);
    
    // Find available products
    List<Product> findByStatus(String status);
}
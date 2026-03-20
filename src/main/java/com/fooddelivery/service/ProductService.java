package com.fooddelivery.service;

import com.fooddelivery.entity.Category;
import com.fooddelivery.entity.Product;
import com.fooddelivery.repository.CategoryRepository;
import com.fooddelivery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    // Get all available products
    public List<Product> getAllAvailableProducts() {
        return productRepository.findByStatus("available");
    }
    
    // Get products by category
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    
    // Get product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    
    // Add new product
    public Product addProduct(Product product) {
        product.setStatus("available");
        return productRepository.save(product);
    }
    
    // Update product
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
    
    // Delete product
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
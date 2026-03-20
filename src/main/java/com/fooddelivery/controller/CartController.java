package com.fooddelivery.controller;

import com.fooddelivery.dto.ApiResponse;
import com.fooddelivery.dto.CartItemDto;
import com.fooddelivery.entity.Product;
import com.fooddelivery.service.CartItem;
import com.fooddelivery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {
    
    @Autowired
    private ProductService productService;
    
    private Map<String, List<CartItem>> userCarts = new ConcurrentHashMap<>();
    
    @GetMapping("/{email}")
    public ApiResponse<List<CartItemDto>> getCart(@PathVariable String email) {
        List<CartItem> cart = userCarts.getOrDefault(email, new ArrayList<>());
        List<CartItemDto> cartDtos = cart.stream()
                .map(this::convertToDto)
                .toList();
        return ApiResponse.success(cartDtos);
    }
    
    @PostMapping("/add")
    public ApiResponse<List<CartItemDto>> addToCart(@RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");
            Long productId = ((Number) request.get("productId")).longValue();
            int quantity = ((Number) request.get("quantity")).intValue();
            
            Product product = productService.getProductById(productId);
            if (product == null) {
                return ApiResponse.error("Product not found");
            }
            
            List<CartItem> cart = userCarts.getOrDefault(email, new ArrayList<>());
            
            boolean found = false;
            for (CartItem item : cart) {
                if (item.getProductId().equals(productId)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                cart.add(new CartItem(productId, product.getName(), 
                        product.getPrice(), product.getImageUrl(), quantity));
            }
            
            userCarts.put(email, cart);
            
            List<CartItemDto> cartDtos = cart.stream()
                    .map(this::convertToDto)
                    .toList();
            
            return ApiResponse.success("Item added to cart successfully", cartDtos);
            
        } catch (Exception e) {
            return ApiResponse.error("Error: " + e.getMessage());
        }
    }
    
    // ✅ THIS IS THE IMPORTANT PART - Make sure this mapping exists!
    @PutMapping("/update")
    public ApiResponse<List<CartItemDto>> updateCart(@RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");
            Long productId = ((Number) request.get("productId")).longValue();
            int quantity = ((Number) request.get("quantity")).intValue();
            
            List<CartItem> cart = userCarts.getOrDefault(email, new ArrayList<>());
            
            if (quantity <= 0) {
                // Remove item if quantity is 0 or negative
                cart.removeIf(item -> item.getProductId().equals(productId));
            } else {
                // Update existing item
                boolean found = false;
                for (CartItem item : cart) {
                    if (item.getProductId().equals(productId)) {
                        item.setQuantity(quantity);
                        found = true;
                        break;
                    }
                }
                
                // If not found, add new item
                if (!found) {
                    Product product = productService.getProductById(productId);
                    if (product != null) {
                        cart.add(new CartItem(productId, product.getName(),
                                product.getPrice(), product.getImageUrl(), quantity));
                    }
                }
            }
            
            userCarts.put(email, cart);
            
            List<CartItemDto> cartDtos = cart.stream()
                    .map(this::convertToDto)
                    .toList();
            
            return ApiResponse.success("Cart updated successfully", cartDtos);
            
        } catch (Exception e) {
            return ApiResponse.error("Error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/remove/{email}/{productId}")
    public ApiResponse<List<CartItemDto>> removeFromCart(@PathVariable String email, @PathVariable Long productId) {
        List<CartItem> cart = userCarts.getOrDefault(email, new ArrayList<>());
        cart.removeIf(item -> item.getProductId().equals(productId));
        userCarts.put(email, cart);
        
        List<CartItemDto> cartDtos = cart.stream()
                .map(this::convertToDto)
                .toList();
        
        return ApiResponse.success("Item removed from cart", cartDtos);
    }
    
    @DeleteMapping("/clear/{email}")
    public ApiResponse<String> clearCart(@PathVariable String email) {
        userCarts.remove(email);
        return ApiResponse.success("Cart cleared successfully", null);
    }
    
    @GetMapping("/total/{email}")
    public ApiResponse<Double> getCartTotal(@PathVariable String email) {
        List<CartItem> cart = userCarts.getOrDefault(email, new ArrayList<>());
        double total = cart.stream()
                .mapToDouble(item -> item.getPrice().doubleValue() * item.getQuantity())
                .sum();
        return ApiResponse.success(total);
    }
    
    private CartItemDto convertToDto(CartItem item) {
        CartItemDto dto = new CartItemDto();
        dto.setProductId(item.getProductId());
        dto.setName(item.getName());
        dto.setPrice(item.getPrice());
        dto.setImageUrl(item.getImageUrl());
        dto.setQuantity(item.getQuantity());
        dto.setSubtotal(item.getPrice().multiply(new java.math.BigDecimal(item.getQuantity())));
        return dto;
    }
}
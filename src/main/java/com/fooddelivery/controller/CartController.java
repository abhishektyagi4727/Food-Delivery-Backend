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
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {
    
    @Autowired
    private ProductService productService;
    
    private Map<String, List<CartItem>> userCarts = new ConcurrentHashMap<>();
    
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
            // Validate request contains all required fields
            if (!request.containsKey("email") || !request.containsKey("productId") || !request.containsKey("quantity")) {
                return ApiResponse.error("Missing required fields: email, productId, quantity");
            }
            
            String email = request.get("email").toString();
            
            // Parse productId safely
            Long productId;
            try {
                productId = Long.parseLong(request.get("productId").toString());
            } catch (NumberFormatException e) {
                return ApiResponse.error("Invalid productId format");
            }
            
            // Parse quantity safely
            int quantity;
            try {
                quantity = Integer.parseInt(request.get("quantity").toString());
            } catch (NumberFormatException e) {
                return ApiResponse.error("Invalid quantity format");
            }
            
            if (quantity <= 0) {
                return ApiResponse.error("Quantity must be greater than 0");
            }
            
            Product product = productService.getProductById(productId);
            if (product == null) {
                return ApiResponse.error("Product not found with id: " + productId);
            }
            
            List<CartItem> cart = userCarts.getOrDefault(email, new ArrayList<>());
            
            // Check if product already in cart
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
            return ApiResponse.error("Error adding to cart: " + e.getMessage());
        }
    }
    
    @PutMapping("/update")
    public ApiResponse<List<CartItemDto>> updateCart(@RequestBody Map<String, Object> request) {
        try {
            // Validate request
            if (!request.containsKey("email") || !request.containsKey("productId") || !request.containsKey("quantity")) {
                return ApiResponse.error("Missing required fields");
            }
            
            String email = request.get("email").toString();
            Long productId = Long.parseLong(request.get("productId").toString());
            int quantity = Integer.parseInt(request.get("quantity").toString());
            
            List<CartItem> cart = userCarts.getOrDefault(email, new ArrayList<>());
            
            if (quantity <= 0) {
                // Remove item if quantity is 0 or negative
                cart.removeIf(item -> item.getProductId().equals(productId));
            } else {
                // Update existing item or add new one
                boolean found = false;
                for (CartItem item : cart) {
                    if (item.getProductId().equals(productId)) {
                        item.setQuantity(quantity);
                        found = true;
                        break;
                    }
                }
                
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
            return ApiResponse.error("Error updating cart: " + e.getMessage());
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
    
    @GetMapping("/count/{email}")
    public ApiResponse<Integer> getCartCount(@PathVariable String email) {
        List<CartItem> cart = userCarts.getOrDefault(email, new ArrayList<>());
        return ApiResponse.success(cart.size());
    }
    
    @GetMapping("/total/{email}")
    public ApiResponse<Double> getCartTotal(@PathVariable String email) {
        List<CartItem> cart = userCarts.getOrDefault(email, new ArrayList<>());
        double total = cart.stream()
                .mapToDouble(item -> item.getPrice().doubleValue() * item.getQuantity())
                .sum();
        return ApiResponse.success(total);
    }
}
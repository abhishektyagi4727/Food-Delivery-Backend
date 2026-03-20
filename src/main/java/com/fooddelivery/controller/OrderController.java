package com.fooddelivery.controller;

import com.fooddelivery.dto.ApiResponse;
import com.fooddelivery.dto.OrderDto;
import com.fooddelivery.dto.OrderItemDto;
import com.fooddelivery.entity.Order;
import com.fooddelivery.entity.OrderItem;
import com.fooddelivery.entity.User;
import com.fooddelivery.service.AuthService;
import com.fooddelivery.service.CartItem;
import com.fooddelivery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private AuthService authService;
    
    // Convert Order to OrderDto
    private OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUserName(order.getUser().getName());
        dto.setUserEmail(order.getUser().getEmail());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setCreatedAt(order.getCreatedAt());
        
        // Convert OrderItems to OrderItemDto
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            List<OrderItemDto> itemDtos = order.getItems().stream()
                    .map(this::convertItemToDto)
                    .collect(Collectors.toList());
            dto.setItems(itemDtos);
        }
        
        return dto;
    }
    
    // Convert OrderItem to OrderItemDto
    private OrderItemDto convertItemToDto(OrderItem item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(item.getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setSubtotal(item.getPrice().multiply(new java.math.BigDecimal(item.getQuantity())));
        return dto;
    }
    
    @PostMapping("/create")
    public ApiResponse<OrderDto> createOrder(@RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");
            String deliveryAddress = (String) request.get("deliveryAddress");
            String paymentMethod = (String) request.get("paymentMethod");
            
            List<Map<String, Object>> cartItemsMap = (List<Map<String, Object>>) request.get("cartItems");
            
            if (cartItemsMap == null || cartItemsMap.isEmpty()) {
                return ApiResponse.error("Cart is empty");
            }
            
            List<CartItem> cartItems = new ArrayList<>();
            for (Map<String, Object> itemMap : cartItemsMap) {
                CartItem cartItem = new CartItem();
                cartItem.setProductId(((Number) itemMap.get("productId")).longValue());
                cartItem.setName((String) itemMap.get("name"));
                cartItem.setPrice(new java.math.BigDecimal(itemMap.get("price").toString()));
                cartItem.setQuantity(((Number) itemMap.get("quantity")).intValue());
                cartItems.add(cartItem);
            }
            
            User user = authService.findByEmail(email);
            if (user == null) {
                return ApiResponse.error("User not found");
            }
            
            Order order = orderService.createOrder(user, deliveryAddress, paymentMethod, cartItems);
            
            return ApiResponse.success("Order created successfully", convertToDto(order));
            
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("Error creating order: " + e.getMessage());
        }
    }
    
    @GetMapping("/user/{email}")
    public ApiResponse<List<OrderDto>> getUserOrders(@PathVariable String email) {  // ✅ Returns OrderDto
        User user = authService.findByEmail(email);
        if (user == null) {
            return ApiResponse.error("User not found");
        }
        
        List<Order> orders = orderService.getUserOrders(user);
        List<OrderDto> orderDtos = orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        return ApiResponse.success(orderDtos);  // ✅ Returns DTO list
    }
    
    @GetMapping("/{id}")
    public ApiResponse<OrderDto> getOrderById(@PathVariable Long id) {  // ✅ Returns OrderDto
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return ApiResponse.error("Order not found");
        }
        return ApiResponse.success(convertToDto(order));  // ✅ Returns DTO
    }
}
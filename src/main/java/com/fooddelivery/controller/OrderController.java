package com.fooddelivery.controller;

import com.fooddelivery.dto.OrderDto;
import com.fooddelivery.dto.OrderItemDto;
import com.fooddelivery.dto.ApiResponse;
import com.fooddelivery.dto.CartItemDto;
import com.fooddelivery.entity.Order;
import com.fooddelivery.entity.OrderItem;
import com.fooddelivery.entity.User;
import com.fooddelivery.service.CartItem;
import com.fooddelivery.service.OrderService;
import com.fooddelivery.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private AuthService authService;
    
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
        
        List<OrderItemDto> itemDtos = order.getItems().stream()
                .map(this::convertItemToDto)
                .collect(Collectors.toList());
        dto.setItems(itemDtos);
        
        return dto;
    }
    
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
        String email = (String) request.get("email");
        String deliveryAddress = (String) request.get("deliveryAddress");
        String paymentMethod = (String) request.get("paymentMethod");
        
        List<CartItem> cartItems = (List<CartItem>) request.get("cartItems");
        
        User user = authService.findByEmail(email);
        if (user == null) {
            return ApiResponse.error("User not found");
        }
        
        Order order = orderService.createOrder(user, deliveryAddress, paymentMethod, cartItems);
        return ApiResponse.success("Order created successfully", convertToDto(order));
    }
    
    @GetMapping("/user/{email}")
    public ApiResponse<List<OrderDto>> getUserOrders(@PathVariable String email) {
        User user = authService.findByEmail(email);
        if (user == null) {
            return ApiResponse.error("User not found");
        }
        
        List<Order> orders = orderService.getUserOrders(user);
        List<OrderDto> orderDtos = orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        return ApiResponse.success(orderDtos);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<OrderDto> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return ApiResponse.error("Order not found");
        }
        return ApiResponse.success(convertToDto(order));
    }
}
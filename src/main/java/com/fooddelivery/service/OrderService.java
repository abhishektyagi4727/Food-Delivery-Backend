package com.fooddelivery.service;

import com.fooddelivery.entity.*;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    // Create new order
    @Transactional
    public Order createOrder(User user, String deliveryAddress, String paymentMethod, 
                            List<CartItem> cartItems) {
        
        // Calculate total
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if (product != null) {
                total = total.add(product.getPrice().multiply(new BigDecimal(item.getQuantity())));
            }
        }
        
        // Create order
        Order order = new Order(user, total, paymentMethod, deliveryAddress);
        order = orderRepository.save(order);
        
        // Add order items
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if (product != null) {
                OrderItem orderItem = new OrderItem(order, product, item.getQuantity());
                order.getItems().add(orderItem);
            }
        }
        
        return orderRepository.save(order);
    }
    
    // Get user orders
    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    // Get order by ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
    
    // Update order status
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }
    
    // Update payment status
    public Order updatePaymentStatus(Long orderId, String paymentStatus) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setPaymentStatus(paymentStatus);
            return orderRepository.save(order);
        }
        return null;
    }
}
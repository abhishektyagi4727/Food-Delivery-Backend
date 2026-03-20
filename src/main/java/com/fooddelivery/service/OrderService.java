package com.fooddelivery.service;

import com.fooddelivery.entity.*;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Transactional
    public Order createOrder(User user, String deliveryAddress, String paymentMethod, List<CartItem> cartItems) {
        
        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        
        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProductId()).orElse(null);
            if (product != null) {
                BigDecimal itemTotal = product.getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
                total = total.add(itemTotal);
                
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(product.getPrice());
                orderItems.add(orderItem);
            }
        }
        
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(total);
        order.setPaymentMethod(paymentMethod);
        order.setDeliveryAddress(deliveryAddress);
        order.setStatus("PENDING");
        order.setPaymentStatus("PENDING");
        
        // Set order reference in each order item
        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }
        order.setItems(orderItems);
        
        return orderRepository.save(order);
    }
    
    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
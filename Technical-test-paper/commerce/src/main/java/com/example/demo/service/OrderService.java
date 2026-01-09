package com.example.demo.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.OrderDetail;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    // 生成 orderId 方法：Ms + 14位數字
    private String generateOrderId() {
        
        // 生成 14 位正整數
        long random14 = Math.abs(new Random().nextLong() % 100000000000000L);
        String random14Str = String.format("%014d", random14);
        return "Ms" + random14Str;
    }

    @Transactional
    public void createOrder(
            Long memberId,
            List<OrderDetail> items
    ) {
        // 自動生成 orderId
        String orderId = generateOrderId();

        // 計算總價
        int totalPrice = items.stream().mapToInt(OrderDetail::getOrderPrice).sum();

        // 1️ 新增訂單
        orderRepository.insertOrder(orderId, memberId, totalPrice, true);

        // 2️ 新增訂單明細 + 扣庫存
        for (OrderDetail item : items) {
            orderDetailRepository.insertOrderDetail(
                item.getOrderItemSN(),
                orderId,
                item.getProductId(),
                item.getQuantity(),
                item.getPrice(),
                item.getOrderPrice()
            );

            // 扣庫存
            productRepository.updateQuantity(
                item.getProductId(),
                item.getQuantity()
            );
        }
    }
}


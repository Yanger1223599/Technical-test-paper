package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.OrderDetail;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(
        origins= { "http://127.0.0.1:5500",
                   "http://localhost:5500",
                   "http://localhost:3000",
                   "http://localhost:8080"},
        allowCredentials="true")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 建立訂單（前端不需傳 orderId）
    @PostMapping
    public ResponseEntity<String> createOrder(
            @RequestParam Long memberId,
            @RequestBody List<OrderDetail> items
    ) {
        // 使用後端 OrderService 生成 orderId
        orderService.createOrder(memberId, items);

        return ResponseEntity.ok("Order created successfully");
    }
}

package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 訂單編號 (如 Ms20250801186230)
    @Column(name = "order_id", unique = true, nullable = false, length = 45)
    private String orderId;

    // 會員編號
    @Column(name = "member_id", nullable = false, length = 45)
    private Long memberId;

    // 訂單總金額
    @Column(name = "order_price", nullable = false)
    private Integer price;

    // 付款狀態 (0:未付款 / 1:已付款)
    @Column(name = "paystatus", nullable = false)
    private Boolean payStatus = true;

  
}


package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orderdetail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    
    @Column(name = "orderitemSN", nullable = false)
    private String OrderItemSN;
    // 訂單編號
    @Column(name = "order_id", nullable = false, length = 45)
    private String OrderId;

    // 商品編號
    @Column(name = "product_id", nullable = false, length = 45)
    private String ProductId;

    // 購買數量
    @Column(name = "quantity", nullable = false)
    private Integer Quantity;

    // 單價
    @Column(name = "price", nullable = false)
    private Integer Price;

    // 單品項總價
    @Column(name = "order_price", nullable = false)
    private Integer OrderPrice;

    @Override
    public String toString() {
        return "OrderDetail [orderItemSn=" + OrderItemSN +
               ", orderId=" + OrderId +
               ", productId=" + ProductId +
               ", quantity=" + Quantity +
               ", standPrice=" + Price +
               ", itemPrice=" + OrderPrice + "]";
    }
}

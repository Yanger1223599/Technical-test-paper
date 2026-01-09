package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 商品編號（如 P001）
    @Column(name = "product_id", unique = true, nullable = false, length = 20)
    private String productId;

    // 商品名稱
    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    // 售價
    @Column(name = "price", nullable = false)
    private Integer price;

    // 庫存
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Override
    public String toString() {
        return "Product [id=" + id +
               ", productId=" + productId +
               ", productName=" + productName +
               ", price=" + price +
               ", quantity=" + quantity + "]";
    }
}

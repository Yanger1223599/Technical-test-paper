package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(
    origins = {
        "http://127.0.0.1:5500",
        "http://localhost:5500",
        "http://localhost:3000",
        "http://localhost:8080"
    },
    allowCredentials = "true"
)
public class ProductController {

    @Autowired
    private ProductService productService;

    // ✅ 查全部商品（後台）
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // 新增商品
    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return ResponseEntity.ok("Product added successfully");
    }
 // 更新商品
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        boolean updated = productService.updateProduct(id, product);
        if (updated) {
            return ResponseEntity.ok("Product updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 刪除商品
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // 前台可購買商品
    @GetMapping("/available")
    public ResponseEntity<List<Product>> getAvailableProducts() {
        return ResponseEntity.ok(productService.getAvailableProducts());
    }
}


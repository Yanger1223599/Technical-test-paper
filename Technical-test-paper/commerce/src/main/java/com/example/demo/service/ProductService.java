package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // 新增商品（自動生成 productId，例如 P0001, P0002）
    public void addProduct(Product product) {
        // 1. 取得目前最大 productId
        Optional<Product> lastProduct = productRepository.findTopByOrderByProductIdDesc();

        // 2. 如果沒有商品就從 P0000 開始
        String lastProductId = lastProduct.map(Product::getProductId).orElse("P000");

        // 3. 取數字部分 +1
        int nextNo = Integer.parseInt(lastProductId.substring(1)) + 1;

        // 4. 生成新 productId
        product.setProductId(String.format("P%03d", nextNo));

        // 5. 插入資料庫
        productRepository.insertProduct(
            product.getProductId(),
            product.getProductName(),
            product.getPrice(),
            product.getQuantity()
        );
    }
    
    public boolean updateProduct(Long id, Product updatedProduct) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            Product product = optional.get();
            product.setProductName(updatedProduct.getProductName());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            productRepository.save(product);
            return true;
        }
        return false;
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }


    // 查詢可購買商品
    public List<Product> getAvailableProducts() {
        List<Product> list = productRepository.findAvailableProducts();
        System.out.println("查到商品數量：" + list.size());
        return list;
    }

    // 查詢全部商品
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

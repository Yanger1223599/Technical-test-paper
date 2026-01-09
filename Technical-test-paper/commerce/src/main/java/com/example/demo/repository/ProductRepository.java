package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Procedure(procedureName = "sp_insert_product")
    void insertProduct(
        @Param("p_product_id") String productId,
        @Param("p_product_name") String productName,
        @Param("p_price") Integer price,
        @Param("p_quantity") Integer quantity
    );

    @Query(value = "CALL sp_find_available_products()", nativeQuery = true)
    List<Product> findAvailableProducts();

    @Procedure(procedureName = "sp_update_product_quantity")
    void updateQuantity(
        @Param("p_product_id") String productId,
        @Param("p_quantity") Integer quantity
    );

	Optional<Product> findTopByOrderByProductIdDesc();
}

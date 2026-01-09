package com.example.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Procedure(procedureName = "sp_insert_order_detail")
    void insertOrderDetail(
        @Param("p_orderitem_sn") String orderItemSN,
        @Param("p_order_id") String orderId,
        @Param("p_product_id") String productId,
        @Param("p_quantity") Integer quantity,
        @Param("p_price") Integer price,
        @Param("p_order_price") Integer orderPrice
    );
}

       
  



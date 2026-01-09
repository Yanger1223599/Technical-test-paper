package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Procedure(procedureName = "sp_insert_order")
    void insertOrder(
        @Param("p_order_id") String orderId,
        @Param("p_member_id") Long memberId,
        @Param("p_price") Integer price,
        @Param("p_paystatus") Boolean payStatus
    );
}
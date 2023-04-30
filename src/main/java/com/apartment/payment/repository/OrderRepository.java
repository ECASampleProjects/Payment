package com.apartment.payment.repository;

import com.apartment.payment.entity.MyOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<MyOrder,Long> {

    public MyOrder findByOrderId(String orderId);
}

package com.example.marketwebsite.repository;

import com.example.marketwebsite.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {

    List<OrderItemEntity> findAllByOrderId(Integer orderId);


}

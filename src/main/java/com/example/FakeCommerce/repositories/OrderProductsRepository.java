package com.example.FakeCommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FakeCommerce.schema.OrderProducts;

@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProducts, Long> {
    boolean existsByOrderIdAndProductId(Long orderId, Long productId);

    List<OrderProducts> findByOrderId(Long orderId);
}

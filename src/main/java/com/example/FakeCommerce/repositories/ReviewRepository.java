package com.example.FakeCommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FakeCommerce.schema.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByOrderIdAndProductId(Long orderId, Long productId);

    List<Review> findByProductId(Long productId);

    List<Review> findByOrderId(Long orderId);
}

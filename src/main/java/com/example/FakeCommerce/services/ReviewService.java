package com.example.FakeCommerce.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FakeCommerce.dtos.CreateReviewRequestDto;
import com.example.FakeCommerce.dtos.ReviewResponseDto;
import com.example.FakeCommerce.repositories.OrderProductsRepository;
import com.example.FakeCommerce.repositories.OrderRepository;
import com.example.FakeCommerce.repositories.ProductRepository;
import com.example.FakeCommerce.repositories.ReviewRepository;
import com.example.FakeCommerce.schema.Order;
import com.example.FakeCommerce.schema.OrderStatus;
import com.example.FakeCommerce.schema.Product;
import com.example.FakeCommerce.schema.Review;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductsRepository orderProductsRepository;

    private static ReviewResponseDto toResponseDto(Review review) {
        Product product = review.getProduct();

        return ReviewResponseDto.builder()
                .id(review.getId())
                .orderId(review.getOrder().getId())
                .productId(product.getId())
                .productTitle(product.getTitle())
                .reviewerName(review.getReviewerName())
                .rating(review.getRating())
                .comment(review.getComment())
                .build();
    }

    public ReviewResponseDto createReview(CreateReviewRequestDto requestDto) {
        validateRating(requestDto.getRating());

        Order order = orderRepository.findById(requestDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("order not found"));

        if (OrderStatus.CANCELLED.equals(order.getStatus())) {
            throw new RuntimeException("cancelled order cannot be reviewed");
        }

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("product not found"));

        boolean productWasOrdered = orderProductsRepository.existsByOrderIdAndProductId(
                order.getId(),
                product.getId()
        );
        if (!productWasOrdered) {
            throw new RuntimeException("product does not belong to this order");
        }

        boolean alreadyReviewed = reviewRepository.existsByOrderIdAndProductId(
                order.getId(),
                product.getId()
        );
        if (alreadyReviewed) {
            throw new RuntimeException("review already exists for this product in this order");
        }

        Review review = Review.builder()
                .order(order)
                .product(product)
                .reviewerName(requestDto.getReviewerName())
                .rating(requestDto.getRating())
                .comment(requestDto.getComment())
                .build();

        return toResponseDto(reviewRepository.save(review));
    }

    public List<ReviewResponseDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(ReviewService::toResponseDto)
                .toList();
    }

    public List<ReviewResponseDto> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId).stream()
                .map(ReviewService::toResponseDto)
                .toList();
    }

    public List<ReviewResponseDto> getReviewsByOrder(Long orderId) {
        return reviewRepository.findByOrderId(orderId).stream()
                .map(ReviewService::toResponseDto)
                .toList();
    }

    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("review not found");
        }
        reviewRepository.deleteById(id);
    }

    private static void validateRating(BigDecimal rating) {
        if (rating == null) {
            throw new RuntimeException("rating is required");
        }
        if (rating.compareTo(BigDecimal.ONE) < 0 || rating.compareTo(BigDecimal.valueOf(5)) > 0) {
            throw new RuntimeException("rating must be between 1 and 5");
        }
    }
}

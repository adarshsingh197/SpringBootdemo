package com.example.FakeCommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.FakeCommerce.dtos.CreateReviewRequestDto;
import com.example.FakeCommerce.dtos.ReviewResponseDto;
import com.example.FakeCommerce.services.ReviewService;
import com.example.FakeCommerce.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ApiResponse<ReviewResponseDto> createReview(@RequestBody CreateReviewRequestDto requestDto) {
        return ApiResponse.success("Review created successfully", reviewService.createReview(requestDto));
    }

    @GetMapping
    public ApiResponse<List<ReviewResponseDto>> getReviews(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long orderId
    ) {
        if (productId != null) {
            return ApiResponse.success("Product reviews fetched successfully",
                    reviewService.getReviewsByProduct(productId));
        }
        if (orderId != null) {
            return ApiResponse.success("Order reviews fetched successfully", reviewService.getReviewsByOrder(orderId));
        }
        return ApiResponse.success("Reviews fetched successfully", reviewService.getAllReviews());
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ApiResponse.success("Review deleted successfully", null);
    }
}

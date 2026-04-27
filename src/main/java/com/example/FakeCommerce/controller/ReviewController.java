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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ReviewResponseDto createReview(@RequestBody CreateReviewRequestDto requestDto) {
        return reviewService.createReview(requestDto);
    }

    @GetMapping
    public List<ReviewResponseDto> getReviews(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long orderId
    ) {
        if (productId != null) {
            return reviewService.getReviewsByProduct(productId);
        }
        if (orderId != null) {
            return reviewService.getReviewsByOrder(orderId);
        }
        return reviewService.getAllReviews();
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}

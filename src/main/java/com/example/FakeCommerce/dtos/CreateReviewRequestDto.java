package com.example.FakeCommerce.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class CreateReviewRequestDto {
    private Long orderId;
    private Long productId;
    private String reviewerName;
    private BigDecimal rating;
    private String comment;
}

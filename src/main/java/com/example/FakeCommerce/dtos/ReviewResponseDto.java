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
public class ReviewResponseDto {
    private Long id;
    private Long orderId;
    private Long productId;
    private String productTitle;
    private String reviewerName;
    private BigDecimal rating;
    private String comment;
}

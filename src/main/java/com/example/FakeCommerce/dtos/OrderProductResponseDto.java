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
public class OrderProductResponseDto {
    private Long productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}

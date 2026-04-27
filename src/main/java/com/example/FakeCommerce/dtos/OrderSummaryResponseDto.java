package com.example.FakeCommerce.dtos;

import java.math.BigDecimal;

import com.example.FakeCommerce.schema.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class OrderSummaryResponseDto {
    private Long orderId;
    private Long userId;
    private OrderStatus status;
    private Integer totalItems;
    private BigDecimal totalAmount;
}

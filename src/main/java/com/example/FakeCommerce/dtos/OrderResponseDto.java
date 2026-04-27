package com.example.FakeCommerce.dtos;

import java.util.List;

import com.example.FakeCommerce.schema.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private OrderStatus status;
    private List<OrderProductResponseDto> products;
}

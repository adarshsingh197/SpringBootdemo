package com.example.FakeCommerce.dtos;

import com.example.FakeCommerce.schema.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UpdateOrderStatusRequestDto {
    private OrderStatus status;
}

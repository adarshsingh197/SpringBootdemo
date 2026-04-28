package com.example.FakeCommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FakeCommerce.dtos.CreateOrderRequestDto;
import com.example.FakeCommerce.dtos.OrderResponseDto;
import com.example.FakeCommerce.dtos.OrderSummaryResponseDto;
import com.example.FakeCommerce.dtos.UpdateOrderStatusRequestDto;
import com.example.FakeCommerce.services.OrderService;
import com.example.FakeCommerce.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ApiResponse<List<OrderResponseDto>> getAllOrders() {
        return ApiResponse.success("Orders fetched successfully", orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponseDto> getOrderById(@PathVariable Long id) {
        return ApiResponse.success("Order fetched successfully", orderService.getOrderById(id));
    }

    @GetMapping("/{id}/summary")
    public ApiResponse<OrderSummaryResponseDto> getOrderSummary(@PathVariable Long id) {
        return ApiResponse.success("Order summary fetched successfully", orderService.getOrderSummary(id));
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<OrderResponseDto>> getOrdersByUserId(@PathVariable Long userId) {
        return ApiResponse.success("User orders fetched successfully", orderService.getOrdersByUserId(userId));
    }

    @PostMapping
    public ApiResponse<OrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto requestDto) {
        return ApiResponse.success("Order created successfully", orderService.createOrder(requestDto));
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<OrderResponseDto> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequestDto requestDto
    ) {
        return ApiResponse.success("Order status updated successfully", orderService.updateOrderStatus(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ApiResponse.success("Order deleted successfully", null);
    }
}

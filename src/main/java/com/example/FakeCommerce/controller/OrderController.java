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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderResponseDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderResponseDto getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/{id}/summary")
    public OrderSummaryResponseDto getOrderSummary(@PathVariable Long id) {
        return orderService.getOrderSummary(id);
    }

    @GetMapping("/user/{userId}")
    public List<OrderResponseDto> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @PostMapping
    public OrderResponseDto createOrder(@RequestBody CreateOrderRequestDto requestDto) {
        return orderService.createOrder(requestDto);
    }

    @PatchMapping("/{id}/status")
    public OrderResponseDto updateOrderStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequestDto requestDto
    ) {
        return orderService.updateOrderStatus(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}

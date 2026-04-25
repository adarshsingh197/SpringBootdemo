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
public class GetProductWithDetailsResponseDto {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    private BigDecimal rating;
    private String category;
    
}

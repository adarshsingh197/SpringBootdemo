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

import com.example.FakeCommerce.dtos.CreateProductRequestDto;
import com.example.FakeCommerce.dtos.GetProductWithDetailsResponseDto;
import com.example.FakeCommerce.dtos.ProductResponseDto;
import com.example.FakeCommerce.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponseDto> getAllProducts(@RequestParam(required = false) String category){
        return productService.getAllProductResponses(category);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return productService.getProductResponseById(id);
    }

    @GetMapping("/{id}/details")
    public GetProductWithDetailsResponseDto getProductWithDetailsResponseDto(@PathVariable Long id){
        return productService.getProductWithDetailsResponseById(id);
    }


    @GetMapping("/categories")
    public List<String> getDistinctCategories() {
        return productService.getDistinctCategories();
    }

    @PostMapping
    public ProductResponseDto createProduct(@RequestBody CreateProductRequestDto reauestDto){
        return productService.createProductResponse(reauestDto);
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        
    }
    

    
}

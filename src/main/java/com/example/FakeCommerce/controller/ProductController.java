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
import com.example.FakeCommerce.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductResponseDto>> getAllProducts(@RequestParam(required = false) String category){
        return ApiResponse.success("Products fetched successfully", productService.getAllProductResponses(category));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponseDto> getProductById(@PathVariable Long id) {
        return ApiResponse.success("Product fetched successfully", productService.getProductResponseById(id));
    }

    @GetMapping("/{id}/details")
    public ApiResponse<GetProductWithDetailsResponseDto> getProductWithDetailsResponseDto(@PathVariable Long id){
        return ApiResponse.success("Product details fetched successfully",
                productService.getProductWithDetailsResponseById(id));
    }


    @GetMapping("/categories")
    public ApiResponse<List<String>> getDistinctCategories() {
        return ApiResponse.success("Product categories fetched successfully", productService.getDistinctCategories());
    }

    @PostMapping
    public ApiResponse<ProductResponseDto> createProduct(@RequestBody CreateProductRequestDto reauestDto){
        return ApiResponse.success("Product created successfully", productService.createProductResponse(reauestDto));
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ApiResponse.success("Product deleted successfully", null);
        
    }
    

    
}

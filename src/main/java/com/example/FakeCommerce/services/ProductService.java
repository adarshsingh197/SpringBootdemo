package com.example.FakeCommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FakeCommerce.dtos.CreateProductRequestDto;
import com.example.FakeCommerce.dtos.GetProductWithDetailsResponseDto;
import com.example.FakeCommerce.dtos.ProductResponseDto;
import com.example.FakeCommerce.repositories.ProductRepository;
import com.example.FakeCommerce.schema.Category;
import com.example.FakeCommerce.schema.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    
    private static ProductResponseDto toResponseDto(Product p) {
        return ProductResponseDto.builder()
                .id(p.getId())
                .title(p.getTitle())
                .description(p.getDescription())
                .price(p.getPrice())
                .image(p.getImage())
                .rating(p.getRating())
                // .categoryId(p.getCategory() == null ? null : p.getCategory().getId())
                // .categoryName(p.getCategory() == null ? null : p.getCategory().getName())
                .build();
    }

    private static GetProductWithDetailsResponseDto toProductDetailsResponseDto(Product product) {
        return GetProductWithDetailsResponseDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .rating(product.getRating())
                .category(product.getCategory() == null ? null : product.getCategory().getName())
                .build();
    }

    public List<Product> gerAllProducts(){
       return productRepository.findAll();
    }

    public List<ProductResponseDto> getAllProductResponses(String category) {
        List<Product> products;
        if (category != null && !category.isBlank()) {
            products = getProductsByCategory(category);
        } else {
            products = gerAllProducts();
        }
        return products.stream().map(ProductService::toResponseDto).toList();
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    public List<String> getDistinctCategories() {
        return productRepository.findDistinctCategories();
    }
    
    public Product getProductById(Long Id){
        return productRepository
                .findById(Id)
                .orElseThrow(() -> new RuntimeException("product not found"));
    }

    public ProductResponseDto getProductResponseById(Long id) {
        return toResponseDto(getProductById(id));
    }

    public GetProductWithDetailsResponseDto getProductWithDetailsResponseById(Long id) {
        Product product = productRepository.findProductWithDetailsById(id)
                .orElseThrow(() -> new RuntimeException("product not found"));
        return toProductDetailsResponseDto(product);
    }



    public Product createProduct(CreateProductRequestDto requestDto){
        Category category = categoryService.getCategoryById(requestDto.getCategoryById());

        Product newProduct = Product.builder().title(requestDto.getTitle()).description(requestDto.getDescription())
        .image(requestDto.getImage()).price(requestDto.getPrice())
        .category(category)
        .rating(requestDto.getRating()).build();
        return productRepository.save(newProduct);
        

    }

    public ProductResponseDto createProductResponse(CreateProductRequestDto requestDto) {
        return toResponseDto(createProduct(requestDto));
    }
    public void deleteProduct(Long id){
        productRepository.deleteById(id);

    }

}

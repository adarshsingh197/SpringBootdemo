package com.example.FakeCommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.FakeCommerce.schema.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    @Query(value = "select distinct p.category from products p where p.category is not null", nativeQuery = true)
    List<String> findDistinctCategories();

    @Query("select p from Product p join fetch p.category c where p.id = :id")
    Optional<Product> findProductWithDetailsById(@Param("id") Long id);
    
}

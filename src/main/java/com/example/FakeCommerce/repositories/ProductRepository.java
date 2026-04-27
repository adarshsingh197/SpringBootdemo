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

    List<Product> findByCategoryName(String category);

    @Query("select distinct c.name from Product p join p.category c where c.name is not null")
    List<String> findDistinctCategories();

    @Query("select p from Product p join fetch p.category c where p.id = :id")
    Optional<Product> findProductWithDetailsById(@Param("id") Long id);
    
}

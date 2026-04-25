package com.example.FakeCommerce.schema;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
@SQLDelete(sql = "UPDATE products SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    private String image;
    @JoinColumn(name ="category_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category; 
    private BigDecimal rating;
    
}

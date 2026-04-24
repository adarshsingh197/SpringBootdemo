package com.example.FakeCommerce.schema;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "orders")
@SQLDelete(sql = "UPDATE orders SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Order extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}


//one more way to create a table is to use the @Entity annotation and the @Table annotation to create a table.
// @Entity
// @Table(name = "orders")
// public class Order extends BaseEntity {
//     private OrderStatus status;

//     @ManyToMany
//     @JoinTable(name = "order_products",
//         joinColumns = @JoinColumn(name = "order_id"),
//         inverseJoinColumns = @JoinColumn(name = "product_id"))
//     private List<OrderProducts> products;
// }
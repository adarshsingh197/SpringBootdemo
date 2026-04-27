CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT NOT NULL AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    reviewer_name VARCHAR(255),
    rating DECIMAL(3,2) NOT NULL,
    comment TEXT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6),
    deleted_at DATETIME(6),
    PRIMARY KEY(id),
    CONSTRAINT fk_reviews_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id),
    CONSTRAINT fk_reviews_product
        FOREIGN KEY (product_id)
        REFERENCES products(id),
    CONSTRAINT uk_reviews_order_product
        UNIQUE (order_id, product_id),
    CONSTRAINT chk_reviews_rating
        CHECK (rating >= 1.00 AND rating <= 5.00)
);

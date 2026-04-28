package com.sql.sqlextra.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "products")
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "item_id")
    private Long itemId;

    @Column(nullable = false)
    private String name;

    private String category;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "short_description")
    private String shortDescription;

    public Product(Long itemId, String name, String category, BigDecimal price, String shortDescription) {
        this.itemId = itemId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.shortDescription = shortDescription;
    }
}
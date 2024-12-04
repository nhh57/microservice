package com.example.productservice.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "product", schema = "testdata")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    @Column(name = "category_id")
    private int categoryId;
    private String description;
    private BigDecimal price;

}

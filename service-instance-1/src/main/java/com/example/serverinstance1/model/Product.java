package com.example.serverinstance1.model;

import lombok.*;

import javax.persistence.*;
import java.lang.annotation.Documented;
import java.math.BigDecimal;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "product", schema = "testdata", catalog = "" )
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;


}

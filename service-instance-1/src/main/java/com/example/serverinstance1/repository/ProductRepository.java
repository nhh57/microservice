package com.example.serverinstance1.repository;

import com.example.serverinstance1.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}

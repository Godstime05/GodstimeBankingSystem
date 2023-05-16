package com.godtime.GodstimeBankingSystem.repository;

import com.godtime.GodstimeBankingSystem.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

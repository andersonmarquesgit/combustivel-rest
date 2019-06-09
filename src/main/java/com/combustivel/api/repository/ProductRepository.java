package com.combustivel.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.combustivel.api.entity.Product;

public interface ProductRepository  extends JpaRepository<Product, String> {

}

package com.combustivel.api.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.combustivel.api.entity.Product;

public interface ProductRepository  extends JpaRepository<Product, String> {

	@Query("SELECT AVG(p.salesValue) FROM Product p"
			+ " JOIN p.resale r ON p.resale.id = r.id"
			+ " JOIN r.city c ON r.city.id = c.id"
			+ " WHERE c.city = :city")
	BigDecimal averageFuelPriceCity(@Param("city") String city);
}

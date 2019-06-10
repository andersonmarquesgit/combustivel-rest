package com.combustivel.api.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.combustivel.api.entity.Product;
import com.combustivel.api.response.AverageValues;

public interface ProductRepository  extends JpaRepository<Product, String> {

	@Query("SELECT AVG(p.salesValue) FROM Product p"
			+ " JOIN p.resale r ON p.resale.id = r.id"
			+ " JOIN r.city c ON r.city.id = c.id"
			+ " WHERE c.city = :city")
	BigDecimal averageFuelPriceCity(@Param("city") String city);
	
	
	@Query("SELECT new com.combustivel.api.response.AverageValues(AVG(p.purchaseValue), AVG(p.salesValue), c.city)"
			+ " FROM Product p"
			+ " JOIN p.resale r ON p.resale.id = r.id"
			+ " JOIN r.city c ON r.city.id = c.id"
			+ " GROUP BY c.city")
	List<AverageValues> averageByCity(); 
	
	@Query("SELECT new com.combustivel.api.response.AverageValues(AVG(p.purchaseValue), AVG(p.salesValue), p.flag)"
			+ " FROM Product p"
			+ " GROUP BY p.flag")
	List<AverageValues> averageByFlag(); 
	
	@Query("SELECT p FROM Product p"
			+ " JOIN p.resale r ON p.resale.id = r.id"
			+ " JOIN r.city c ON r.city.id = c.id"
			+ " JOIN c.state s ON c.state.id = s.id"
			+ " WHERE s.region = :region")
	List<Product> findAllByRegion(@Param("region") String region);
	
	@Query("SELECT p FROM Product p"
			+ " JOIN p.resale r ON p.resale.id = r.id"
			+ " WHERE r.desc = :resaleDesc")
	List<Product> findAllByResale(@Param("resaleDesc") String resaleDesc);
	
	List<Product> findByDtCollect(LocalDate dtCollect);
}

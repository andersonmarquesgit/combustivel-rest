package com.combustivel.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.combustivel.api.entity.Product;
import com.combustivel.api.request.RegionRequest;
import com.combustivel.api.request.ResaleRequest;
import com.combustivel.api.response.Response;
import com.combustivel.api.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*") // Permitindo o acesso de qualquer IP, porta, etc.
@Api(value = "Product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/region")
	@PreAuthorize("hasAnyRole('ANALYST')")
	@ApiOperation(value = "Recurso que retorna todas as informações importadas por sigla da região", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(@ApiResponse(code = 200, message = "ok", response = Product.class))
	public ResponseEntity<?> findAllByRegion(HttpServletRequest request, 
			@ApiParam(
				    value="RegionRequest", 
				    name="region", 
				    required=true)
			@RequestBody RegionRequest region, BindingResult result){
		
		Response<List<Product>> response = new Response<List<Product>>();
		List<Product> products;
		
		try {
			this.validateRegionRequest(region, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			products = productService.findAllByRegion(region.getRegion());
			
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(products);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/resale")
	@PreAuthorize("hasAnyRole('ANALYST')")
	@ApiOperation(value = "Recurso que retorna os dados agrupados por distribuidora", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(@ApiResponse(code = 200, message = "ok", response = Product.class))
	public ResponseEntity<?> findAllByResale(HttpServletRequest request, 
			@ApiParam(
				    value="ResaleRequest", 
				    name="resale", 
				    required=true)
			@RequestBody ResaleRequest resale, BindingResult result){
		
		Response<List<Product>> response = new Response<List<Product>>();
		List<Product> products;
		
		try {
			this.validateResaleRequest(resale, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			products = productService.findAllByResale(resale.getResaleDesc());
			
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(products);
		return ResponseEntity.ok(response);
	}
	
	private void validateRegionRequest(RegionRequest region, BindingResult result) {
		if (region.getRegion() == null || region.getRegion().isEmpty()) {
			result.addError(new ObjectError("RegionRequest", "Region no information"));
		}
	}
	
	private void validateResaleRequest(ResaleRequest resale, BindingResult result) {
		if (resale.getResaleDesc() == null || resale.getResaleDesc().isEmpty()) {
			result.addError(new ObjectError("ResaleRequest", "Resale no information"));
		}
	}
}

package com.combustivel.api.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.combustivel.api.entity.Product;
import com.combustivel.api.request.PriceRequest;
import com.combustivel.api.request.ProductRequest;
import com.combustivel.api.request.RegionRequest;
import com.combustivel.api.request.ResaleRequest;
import com.combustivel.api.response.Response;
import com.combustivel.api.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

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
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Product.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
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
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Product.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
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
	
	@GetMapping(value = "{dtCollect}")
	@PreAuthorize("hasAnyRole('ANALYST')")
	@ApiOperation(value = "Recurso que retorna os dados agrupados pela data da coleta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Product.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	public ResponseEntity<?> findAllByDtCollect(HttpServletRequest request,
			@ApiParam(
				    value="dtCollect", 
				    name="dtCollect", 
				    required=true)
			@PathVariable String dtCollect, BindingResult result){
		
		Response<List<Product>> response = new Response<List<Product>>();
		List<Product> products;
		
		LocalDate dateCollect = LocalDate.parse(dtCollect);
		try {
			this.validateDateCollect(dateCollect, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			products = productService.findByDtCollect(dateCollect);
			
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(products);
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ANALYST')") // Autorização com base no perfil. Nesse caso apenas ADMIN podem criar
											// usuários.
	@ApiOperation(value = "Recurso para CRUD de histórico de preço de combustível", 
		consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(@ApiResponse(code = 201, message = "Novo preço criado", response = Product.class, 
		responseHeaders = @ResponseHeader(name = "User", description = "Preço criado", response = Product.class)))
	public ResponseEntity<Response<Product>> create(HttpServletRequest request, 
			@ApiParam(
				    value="ProductRequest", 
				    name="productRequest", 
				    required=true)
			@RequestBody ProductRequest productRequest,
			BindingResult result) {
		Response<Product> response = new Response<Product>();

		try {
			validateCreateProduct(productRequest, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			Product product = new Product(
					productRequest.getSiglaRegion(), 
					productRequest.getDescState(), 
					productRequest.getDescCity(), 
					productRequest.getCodResale(), 
					productRequest.getDescResale(), 
					productRequest.getDescProduct(), 
					productRequest.getDtCollect(), 
					productRequest.getPurchaseValue(), 
					productRequest.getSalesValue(), 
					productRequest.getUnity(), 
					productRequest.getFlag());
			
			Product productPersisted = this.productService.createOrUpdate(product);
			response.setData(productPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PutMapping
	@PreAuthorize("hasAnyRole('ANALYST')") // Autorização com base no perfil. Nesse caso apenas ADMIN podem atualizar usuários.
	@ApiOperation(value = "Atualização de preços", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(@ApiResponse(code = 200, message = "Preço atualizado", response = Product.class, 
		responseHeaders = @ResponseHeader(name = "Location", description = "uri do preço atualizado", response = String.class)))
	public ResponseEntity<Response<Product>> update(HttpServletRequest request, 
			@ApiParam(
				    value="PriceRequest", 
				    name="priceRequest", 
				    required=true)
			@RequestBody PriceRequest priceRequest,
			BindingResult result) {
		Response<Product> response = new Response<Product>();
		try {
			validateUpdatePrices(priceRequest, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			Product product = this.productService.findById(priceRequest.getProductId());
			if(product == null) {
				result.addError(new ObjectError("Product", "Product no exists"));
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}else {
				product.setPurchaseValue(priceRequest.getPurchaseValue());
				product.setSalesValue(priceRequest.getSalesValue());
			}
			
			Product productUpdated = this.productService.createOrUpdate(product);
			response.setData(productUpdated);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	private void validateUpdatePrices(PriceRequest priceRequest, BindingResult result) {
		if (priceRequest.getProductId() == null || priceRequest.getProductId().isEmpty()) {
			result.addError(new ObjectError("Product", "productID no information"));
		}
		
		if (priceRequest.getPurchaseValue() == null) {
			result.addError(new ObjectError("Product", "purchaseValue no information"));
		}
		
		if (priceRequest.getSalesValue() == null) {
			result.addError(new ObjectError("Product", "salesValue no information"));
		}
	}

	private void validateCreateProduct(ProductRequest productRequest, BindingResult result) {
		if (productRequest.getDescProduct() == null || productRequest.getDescProduct().isEmpty()) {
			result.addError(new ObjectError("Product", "descProduct no information"));
		}
		
		if (productRequest.getPurchaseValue() == null) {
			result.addError(new ObjectError("Product", "purchaseValue no information"));
		}
		
		if (productRequest.getSalesValue() == null) {
			result.addError(new ObjectError("Product", "salesValue no information"));
		}
		
		if (productRequest.getDtCollect() == null) {
			result.addError(new ObjectError("Product", "dtCollect no information"));
		}
		
		if (productRequest.getFlag() == null || productRequest.getFlag().isEmpty()) {
			result.addError(new ObjectError("Product", "flag no information"));
		}
		
		if (productRequest.getCodResale() == null || productRequest.getCodResale().isEmpty()) {
			result.addError(new ObjectError("Product", "codResale no information"));
		}
		
		if (productRequest.getDescResale() == null || productRequest.getDescResale().isEmpty()) {
			result.addError(new ObjectError("Product", "descResale no information"));
		}
		
		if (productRequest.getDescCity() == null || productRequest.getDescCity().isEmpty()) {
			result.addError(new ObjectError("Product", "descCity no information"));
		}
		
		if (productRequest.getSiglaRegion() == null || productRequest.getSiglaRegion().isEmpty()) {
			result.addError(new ObjectError("Product", "siglaRegion no information"));
		}
		
		if (productRequest.getDescState() == null || productRequest.getDescState().isEmpty()) {
			result.addError(new ObjectError("Product", "descState no information"));
		}
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
	
	private void validateDateCollect(LocalDate dtCollect, BindingResult result) {
		if (dtCollect == null) {
			result.addError(new ObjectError("Date Collect", "Date Collect no information"));
		}
	}
}

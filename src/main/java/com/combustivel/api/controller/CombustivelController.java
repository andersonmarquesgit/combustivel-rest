package com.combustivel.api.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.combustivel.api.request.CityRequest;
import com.combustivel.api.response.Response;
import com.combustivel.api.service.CombustivelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/combustivel")
@CrossOrigin(origins = "*") // Permitindo o acesso de qualquer IP, porta, etc.
@Api(value = "Combustivel")
public class CombustivelController {

	@Autowired
	private CombustivelService combustivelService;
	
	@GetMapping(value = "/average")
	@PreAuthorize("hasAnyRole('ANALYST')")
	@ApiOperation(value = "Retorna a média de preço de combustível com base no nome do município", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(@ApiResponse(code = 200, message = "ok", response = BigDecimal.class))
	public ResponseEntity<?> averageFuelPriceCity(HttpServletRequest request, 
			@ApiParam(
				    value="String", 
				    name="city", 
				    required=true)
			@RequestBody CityRequest city, BindingResult result){
		
		Response<BigDecimal> response = new Response<BigDecimal>();
		BigDecimal averageFuelPrice = combustivelService.averageFuelPriceCity(city.getCity());
		
		response.setData(averageFuelPrice);
		return ResponseEntity.ok(response);
	}
}

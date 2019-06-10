package com.combustivel.api.controller;

import java.math.BigDecimal;
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

import com.combustivel.api.request.CityRequest;
import com.combustivel.api.response.AverageValues;
import com.combustivel.api.response.Response;
import com.combustivel.api.service.StatsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/stats")
@CrossOrigin(origins = "*") // Permitindo o acesso de qualquer IP, porta, etc.
@Api(value = "Stats")
public class StatsController {

	@Autowired
	private StatsService statsService;
	
	@GetMapping(value = "/averages")
	@PreAuthorize("hasAnyRole('ANALYST')")
	@ApiOperation(value = "Retorna a média de preço de combustível com base no nome do município", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = BigDecimal.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	public ResponseEntity<?> averageFuelPriceCity(HttpServletRequest request, 
			@ApiParam(
				    value="CityRequest", 
				    name="city", 
				    required=true)
			@RequestBody CityRequest city, BindingResult result){
		
		Response<BigDecimal> response = new Response<BigDecimal>();
		BigDecimal averageFuelPrice;
		
		try {
			this.validateCityRequest(city, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			averageFuelPrice = statsService.averageFuelPriceCity(city.getCity());
		
			
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(averageFuelPrice);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/averages/cities")
	@PreAuthorize("hasAnyRole('ANALYST')")
	@ApiOperation(value = "Retorna o valor médio do valor da compra e do valor da venda por município", 
		consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = AverageValues.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	public ResponseEntity<?> averageValuesByCity(){
		Response<List<AverageValues>> response = new Response<List<AverageValues>>();
		List<AverageValues> averageValues;
		averageValues = statsService.averageByCity();
		response.setData(averageValues);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/averages/flags")
	@PreAuthorize("hasAnyRole('ANALYST')")
	@ApiOperation(value = "Retorna o valor médio do valor da compra e do valor da venda por bandeira", 
		consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = AverageValues.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	public ResponseEntity<?> averageValuesByFlag(){
		Response<List<AverageValues>> response = new Response<List<AverageValues>>();
		List<AverageValues> averageValues;
		averageValues = statsService.averageByFlag();
		response.setData(averageValues);
		return ResponseEntity.ok(response);
	}
	
	private void validateCityRequest(CityRequest cityResquest, BindingResult result) {
		if (cityResquest.getCity() == null || cityResquest.getCity().isEmpty()) {
			result.addError(new ObjectError("CityRequest", "City no information"));
		}
	}
}

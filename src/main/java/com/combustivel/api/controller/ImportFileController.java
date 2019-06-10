package com.combustivel.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.combustivel.api.service.ImportFileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "*") // Permitindo o acesso de qualquer IP, porta, etc.
@Api(value = "File")
public class ImportFileController {

	@Autowired
	private ImportFileService importFileService;
    
	@PostMapping
	@PreAuthorize("hasAnyRole('ANALYST')") // Autorização com base no perfil. Nesse caso apenas ANALYST pode importar
	@ApiOperation(value = "Recurso para importação de csv")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	public void importFile(@RequestParam MultipartFile file) {
		importFileService.importFile(file);
	}
}

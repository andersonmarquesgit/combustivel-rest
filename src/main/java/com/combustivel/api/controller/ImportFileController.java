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

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "*") // Permitindo o acesso de qualquer IP, porta, etc.
@Api(value = "File")
public class ImportFileController {

	@Autowired
	private ImportFileService importFileService;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ANALYST')") // Autorização com base no perfil. Nesse caso apenas ANALYST pode importar
	public void importFile(@RequestParam MultipartFile file) {
		importFileService.importFile(file);
	}
}

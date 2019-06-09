package com.combustivel.api.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface ImportFileService {

	void importFile(MultipartFile file);
}

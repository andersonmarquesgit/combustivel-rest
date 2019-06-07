package com.combustivel.api.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImportFileService {

	void importFile(MultipartFile file);
}

package com.combustivel.api.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.combustivel.api.entity.Product;
import com.combustivel.api.service.ImportFileService;
import com.combustivel.api.service.ProductService;

@Service
public class ImportFileServiceImpl implements ImportFileService {

    @Value("${import.raiz}")
    private String raiz;
    
    @Autowired
    private ProductService productService;
    
	@Override
	public void importFile(MultipartFile file) {
		try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(raiz + file.getOriginalFilename());
            Files.write(path, bytes);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            try {
    			List<Product> products = Files.lines(path)
    			 .skip(1)
    			 .map(line -> line.split(";"))
    		     .map(col-> new Product(
    		    		 col[0], //Região
    		    		 col[1], //Sigla do Estado
    		    		 col[2], //Cidade
    		    		 col[3], //Descrição da revenda
    		    		 col[4], //Código da revenda
    		    		 col[5], //Descrição do produto
    		    		 LocalDate.parse(col[6], formatter), //Data da coleta
    		    		 Double.parseDouble(col[7].replace(",",".")), //Valor de compra
    		    		 Double.parseDouble(col[8].replace(",",".")), //Valor de revenda
    		    		 col[9], //Unidade
    		    		 col[10] //Bandeira
    		    				 ))
    			 .collect(Collectors.toList());
    			
    			this.productService.createProducts(products);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            

        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

}

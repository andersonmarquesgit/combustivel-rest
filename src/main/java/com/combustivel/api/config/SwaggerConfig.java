package com.combustivel.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())
          .build().apiInfo(this.informacoesApi().build());                                           
    }
	
	private ApiInfoBuilder informacoesApi() {

		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

		apiInfoBuilder.title("Simios Api");
		apiInfoBuilder.description("Api para detecção se DNA pertence a um Humano ou a uma Simio.");
		apiInfoBuilder.version("1.0");
		apiInfoBuilder.termsOfServiceUrl("Termo de uso: Deve ser usada pelo Mercado Pago.");
		apiInfoBuilder.license("Licença - Open Source");
		apiInfoBuilder.licenseUrl("https://goo.gl/Qm3SM9");

		return apiInfoBuilder;

	}

}
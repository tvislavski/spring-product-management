package com.kolotree.springproductmanagement;

import com.kolotree.springproductmanagement.adapters.JsonDbProductRepository;
import com.kolotree.springproductmanagement.ports.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@ComponentScan(basePackages = "com.kolotree.springproductmanagement.web")
@EnableSwagger2
public class SpringProductManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringProductManagementApplication.class, args);
	}

	@Value(value = "${db-directory}")
	private String dbDirectory;

	@Bean
	public ProductRepository productRepository() {
		return new JsonDbProductRepository(dbDirectory);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.kolotree.springproductmanagement"))
				.paths(PathSelectors.any())
				.build()
				.genericModelSubstitutes(ResponseEntity.class)
				.directModelSubstitute(ResponseEntity.class, Void.class)
				.globalResponseMessage(RequestMethod.GET, Collections.emptyList())
				.globalResponseMessage(RequestMethod.POST, Collections.emptyList())
				.globalResponseMessage(RequestMethod.DELETE, Collections.emptyList());
	}
}

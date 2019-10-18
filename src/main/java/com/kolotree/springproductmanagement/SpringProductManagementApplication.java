package com.kolotree.springproductmanagement;

import com.kolotree.springproductmanagement.adapters.JsonDbProductRepository;
import com.kolotree.springproductmanagement.ports.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.kolotree.springproductmanagement.web")
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
}

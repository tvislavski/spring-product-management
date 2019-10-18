package com.kolotree.springproductmanagement.dto;


import com.kolotree.springproductmanagement.domain.Product;
import com.kolotree.springproductmanagement.domain.SKU;

import java.time.LocalDate;

public class ProductDto {

    private String sku;
    private String name;
    private double price;
    private String createdAt;

    public ProductDto() {
    }

    public ProductDto(String sku, String name, double price, String createdAt) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public static ProductDto from(Product product) {
        return new ProductDto(product.getId().toString(), product.getName(), product.getPrice().toDouble(),
                product.getCreatedAt().toString());
    }

    public Product toDomain() {
        return Product.productFrom(sku, name, price, LocalDate.parse(createdAt));
    }
}

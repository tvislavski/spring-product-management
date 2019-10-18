package com.kolotree.springproductmanagement.adapters;

import com.kolotree.springproductmanagement.domain.Product;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

import java.time.LocalDate;

@Document(collection = "products", schemaVersion = "1.0")
public class PersistedProduct {

    private boolean removed;

    @Id
    private String sku;
    private String name;
    private double price;
    private String createdAt;

    public PersistedProduct() {
    }

    public PersistedProduct(String sku, String name, double price, String createdAt, boolean removed) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
        this.removed = removed;
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

    public Product toDomain() {
        return createdAt == null ?
                Product.newProductFrom(sku, name, price) :
                Product.productFrom(sku, name, price, LocalDate.parse(createdAt));
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public static PersistedProduct from(Product product) {
        return new PersistedProduct(product.getId().toString(), product.getName(),
                product.getPrice().toDouble(), product.getCreatedAt().toString(), false);
    }
}

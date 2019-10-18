package com.kolotree.springproductmanagement.domain;

import io.vavr.control.Option;

import java.time.LocalDate;

public class Product {

    private final SKU sku;
    private String name;
    private Price price;
    private final LocalDate createdAt;

    private Product(SKU sku, LocalDate createdAt) {
        this.sku = sku;
        this.createdAt = createdAt;
    }

    public SKU getId() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    private void setName(String name) {
        Option.of(name).filter(s -> !s.isBlank()).getOrElseThrow(() -> new IllegalArgumentException("Name must not be empty"));
        this.name = name;
    }

    private void setPrice(double price) {
        this.price = Price.priceFrom(price);
    }

    public Product updateName(String name) {
        setName(name);
        return this;
    }

    public Product updatePrice(double price) {
        setPrice(price);
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || !obj.getClass().equals(Product.class)) return false;
        return sku.equals(((Product)obj).sku);
    }

    @Override
    public int hashCode() {
        return sku.hashCode();
    }

    @Override
    public String toString() {
        return Product.class.getSimpleName() + "[" + sku + "]";
    }

    public static Product newProductFrom(String sku, String name, double price) {
        var product = new Product(SKU.stockKeepingUnitFrom(sku), LocalDate.now());
        product.setName(name);
        product.setPrice(price);
        return product;
    }

    public static Product productFrom(String sku, String name, double price, LocalDate createdAt) {
        Option.of(createdAt).filter(localDate -> !localDate.isAfter(LocalDate.now()))
                .getOrElseThrow(() -> new IllegalArgumentException("Creation date cannot be in the future"));
        var product = new Product(SKU.stockKeepingUnitFrom(sku), createdAt);
        product.setName(name);
        product.setPrice(price);
        return product;
    }
}

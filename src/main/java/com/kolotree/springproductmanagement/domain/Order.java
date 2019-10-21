package com.kolotree.springproductmanagement.domain;

import io.vavr.control.Option;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Order {

    private final UUID id;
    private final Email buyersEmail;
    private final List<Product> products;
    private final LocalDateTime placementTimestamp;

    private Order(UUID id, Email buyersEmail, List<Product> products, LocalDateTime placementTimestamp) {
        this.id = id;
        this.buyersEmail = buyersEmail;
        this.products = products;
        this.placementTimestamp = placementTimestamp;
    }

    public double totalAmount() {
        return products.stream().mapToDouble(product -> product.getPrice().toDouble()).sum();
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getPlacementTimestamp() {
        return placementTimestamp;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public Email getBuyersEmail() {
        return buyersEmail;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || !obj.getClass().equals(Order.class)) return false;
        return id.equals(((Order) obj).id);
    }

    @Override
    public String toString() {
        return Order.class.getSimpleName() + "[" + id + "]";
    }

    public static Order orderFrom(String buyersEmail, List<Product> products) {
        var email = Email.newEmailFrom(buyersEmail);
        return orderFrom(UUID.randomUUID(), email, products, LocalDateTime.now());
    }

    public static Order orderFrom(UUID id, Email buyersEmail, List<Product> products, LocalDateTime timestamp) {
        Option.of(products).filter(list -> !list.isEmpty())
                .getOrElseThrow(() -> new IllegalArgumentException("Order must contain at least one product"));
        Option.of(id).getOrElseThrow(() -> new IllegalArgumentException("Id cannot be null"));
        Option.of(buyersEmail).getOrElseThrow(() -> new IllegalArgumentException("Email cannot be null"));
        Option.of(timestamp).getOrElseThrow(() -> new IllegalArgumentException("Timestamp cannot be null"));

        if (timestamp.isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("Timestamp cannot be in the future");

        return new Order(id, buyersEmail, products, timestamp);
    }
}

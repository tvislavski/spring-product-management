package com.kolotree.springproductmanagement.dto;

import com.kolotree.springproductmanagement.domain.Order;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDto {

    private String id;
    private String buyersEmail;
    private List<ProductDto> products;
    private String placementTimestamp;

    public OrderDto() {
    }

    public OrderDto(String id, String buyersEmail, List<ProductDto> products, String placementTimestamp) {
        this.id = id;
        this.buyersEmail = buyersEmail;
        this.products = products;
        this.placementTimestamp = placementTimestamp;
    }

    public static OrderDto from(Order order) {
        return new OrderDto(order.getId().toString(), order.getBuyersEmail().toString(),
                order.getProducts().stream().map(ProductDto::from).collect(Collectors.toList()),
                DateTimeFormatter.ISO_DATE_TIME.format(order.getPlacementTimestamp()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyersEmail() {
        return buyersEmail;
    }

    public void setBuyersEmail(String buyersEmail) {
        this.buyersEmail = buyersEmail;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public String getPlacementTimestamp() {
        return placementTimestamp;
    }

    public void setPlacementTimestamp(String placementTimestamp) {
        this.placementTimestamp = placementTimestamp;
    }
}

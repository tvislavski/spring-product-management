package com.kolotree.springproductmanagement.dto;

import java.util.List;

public class OrderPlacementDto {

    private String buyersEmail;
    private List<String> productIds;

    public String getBuyersEmail() {
        return buyersEmail;
    }

    public void setBuyersEmail(String buyersEmail) {
        this.buyersEmail = buyersEmail;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    @Override
    public String toString() {
        return OrderPlacementDto.class.getSimpleName() + "(" + buyersEmail + ", " + productIds + ")";
    }
}

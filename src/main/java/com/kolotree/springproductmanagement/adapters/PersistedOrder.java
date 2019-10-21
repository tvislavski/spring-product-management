package com.kolotree.springproductmanagement.adapters;

import com.kolotree.springproductmanagement.domain.Email;
import com.kolotree.springproductmanagement.domain.Order;
import com.kolotree.springproductmanagement.domain.Product;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Document(collection = "orders", schemaVersion = "1.0")
public class PersistedOrder {

    @Id
    private UUID id;
    private String buyersEmail;
    private String placementTimestamp;
    private List<OrderedProduct> products;

    public PersistedOrder() {
    }

    public PersistedOrder(UUID id, String buyersEmail, String placementTimestamp, List<OrderedProduct> products) {
        this.id = id;
        this.buyersEmail = buyersEmail;
        this.placementTimestamp = placementTimestamp;
        this.products = products;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBuyersEmail() {
        return buyersEmail;
    }

    public void setBuyersEmail(String buyersEmail) {
        this.buyersEmail = buyersEmail;
    }

    public String getPlacementTimestamp() {
        return placementTimestamp;
    }

    public void setPlacementTimestamp(String placementTimestamp) {
        this.placementTimestamp = placementTimestamp;
    }

    public List<OrderedProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderedProduct> products) {
        this.products = products;
    }

    static PersistedOrder from(Order order) {
        return new PersistedOrder(order.getId(), order.getBuyersEmail().toString(), order.getPlacementTimestamp().toString(),
                order.getProducts().stream().map(product -> new OrderedProduct(
                        product.getId().toString(),
                        product.getPrice().toDouble())).collect(Collectors.toList())
        );
    }

    Order toDomain(Function<String, Product> getProductFunction) {
        return Order.orderFrom(id, Email.newEmailFrom(buyersEmail),
                products.stream().map(product -> getProductBy(product, getProductFunction)).collect(Collectors.toList()),
                LocalDateTime.parse(placementTimestamp));
    }

    private Product getProductBy(OrderedProduct product, Function<String, Product> getProductFunction) {
        return getProductFunction.apply(product.getProductId()).updatePrice(product.getPrice());
    }
}

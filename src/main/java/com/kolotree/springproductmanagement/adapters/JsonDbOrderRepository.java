package com.kolotree.springproductmanagement.adapters;

import com.kolotree.springproductmanagement.domain.Order;
import com.kolotree.springproductmanagement.domain.Product;
import com.kolotree.springproductmanagement.ports.DatabaseException;
import com.kolotree.springproductmanagement.ports.OrderRepository;
import io.jsondb.JsonDBTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonDbOrderRepository implements OrderRepository {

    private final JsonDBTemplate jsonDBTemplate;

    public JsonDbOrderRepository(String dbDirectory) {
        this.jsonDBTemplate = new JsonDBTemplate(
                dbDirectory,
                "com.kolotree.springproductmanagement.adapters"
        );
        if (!jsonDBTemplate.collectionExists(PersistedOrder.class))
            jsonDBTemplate.createCollection(PersistedOrder.class);
    }

    @Override
    public Order save(Order order) {
        try {
            jsonDBTemplate.upsert(PersistedOrder.from(order));
            return order;
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Order> getAllOrdersInRange(LocalDate start, LocalDate end) {
        try {
            Function<String, Product> getProductFunction = productId -> jsonDBTemplate.findById(productId, PersistedProduct.class).toDomain();
            return jsonDBTemplate.findAll(PersistedOrder.class).stream()
                    .filter(order -> !LocalDateTime.parse(order.getPlacementTimestamp()).isBefore(start.atStartOfDay()) &&
                            !LocalDateTime.parse(order.getPlacementTimestamp()).isAfter(end.atTime(23, 59, 59)))
                    .map(persistedOrder -> persistedOrder.toDomain(getProductFunction))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}

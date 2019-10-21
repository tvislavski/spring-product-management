package com.kolotree.springproductmanagement.service;

import com.kolotree.springproductmanagement.domain.Order;
import com.kolotree.springproductmanagement.domain.SKU;
import com.kolotree.springproductmanagement.ports.OrderRepository;
import com.kolotree.springproductmanagement.ports.ProductRepository;
import io.vavr.control.Option;

import java.util.List;
import java.util.stream.Collectors;

public class OrderPlacementService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderPlacementService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Order placeOrder(String buyersEmail, List<String> productIds) {
        Option.of(productIds).getOrElseThrow(() -> new IllegalArgumentException("Products can't be null"));

        var products = productIds.stream().map(SKU::stockKeepingUnitFrom).map(productRepository::findBy)
                .filter(Option::isDefined).map(Option::get)
                .collect(Collectors.toList());

        if (products.size() < productIds.size())
            throw new IllegalArgumentException("Not all provided product ids are valid");

        return orderRepository.save(Order.orderFrom(buyersEmail, products));
    }
}

package com.kolotree.springproductmanagement.ports;

import com.kolotree.springproductmanagement.domain.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository {

    Order save(Order order);

    List<Order> getAllOrdersInRange(LocalDate start, LocalDate end);
}

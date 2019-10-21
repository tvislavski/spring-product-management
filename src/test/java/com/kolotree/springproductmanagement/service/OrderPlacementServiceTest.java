package com.kolotree.springproductmanagement.service;

import com.kolotree.springproductmanagement.domain.Order;
import com.kolotree.springproductmanagement.domain.Product;
import com.kolotree.springproductmanagement.domain.SKU;
import com.kolotree.springproductmanagement.ports.OrderRepository;
import com.kolotree.springproductmanagement.ports.ProductRepository;
import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class OrderPlacementServiceTest {

    private OrderPlacementService service = new OrderPlacementService(productRepository, orderRepository);

    @Test
    public void testOrderPlacementWithInvalidEmailFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.placeOrder("aaa", List.of("id1")));
    }

    @Test
    public void testOrderPlacementWithEmptyProductListFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.placeOrder("test@gmail.com", List.of()));
    }

    @Test
    public void testOrderPlacementWithValidArgumentsSucceeds() {
        var order = Assertions.assertDoesNotThrow(() -> service.placeOrder("test@gmail.com", List.of("id1")));
        Assertions.assertEquals(20, order.totalAmount());
    }

    private static final OrderRepository orderRepository = new OrderRepository() {
        @Override
        public Order save(Order order) {
            return order;
        }

        @Override
        public List<Order> getAllOrdersInRange(LocalDate start, LocalDate end) {
            return List.of();
        }
    };

    private static final ProductRepository productRepository = new ProductRepository() {
        @Override
        public Product save(Product product) {
            return product;
        }

        @Override
        public List<Product> getAll() {
            return List.of();
        }

        @Override
        public boolean delete(SKU productId) {
            return true;
        }

        @Override
        public Option<Product> findBy(SKU id) {
            return Option.some(Product.newProductFrom(id.toString(), "name", 20));
        }
    };
}

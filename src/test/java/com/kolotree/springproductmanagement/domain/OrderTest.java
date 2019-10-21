package com.kolotree.springproductmanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderTest {

    private static final UUID id = UUID.randomUUID();
    private static final Email email = Email.newEmailFrom("buyer@gmail.com");
    private static final List<Product> products = List.of(
            Product.newProductFrom("123", "product 1", 50),
            Product.newProductFrom("456", "product 2", 75.5)
    );
    private static final LocalDateTime timestamp = LocalDateTime.now();

    @Test
    public void testCreationWithNullIdFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Order.orderFrom(null, email, products, timestamp));
    }

    @Test
    public void testCreationWithNullEmailFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Order.orderFrom(id, null, products, timestamp));
    }

    @Test
    public void testCreationWithNullProductsFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Order.orderFrom(id, email, null, timestamp));
    }

    @Test
    public void testCreationWithEmptyProductsFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Order.orderFrom(id, email, List.of(), timestamp));
    }

    @Test
    public void testCreationWithNullTimestampFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Order.orderFrom(id, email, products, null));
    }

    @Test
    public void testCreationWithTimestampInTheFutureFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Order.orderFrom(id, email, products, LocalDateTime.now().plusHours(1)));
    }

    @Test
    public void testCreationWithValidArgumentsSucceeds() {
        var order = Assertions.assertDoesNotThrow(() -> Order.orderFrom(id, email, products, timestamp));
        Assertions.assertEquals(id, order.getId());
        Assertions.assertEquals(email, order.getBuyersEmail());
        Assertions.assertIterableEquals(products, order.getProducts());
        Assertions.assertEquals(timestamp, order.getPlacementTimestamp());
        Assertions.assertEquals(125.5, order.totalAmount());
    }

    @Test
    public void testEquality() {
        var order1 = Assertions.assertDoesNotThrow(() -> Order.orderFrom(id, email, products, timestamp));
        var order2 = Assertions.assertDoesNotThrow(() -> Order.orderFrom(id, Email.newEmailFrom("buyer2@gmail.com"), products.stream().limit(1).collect(Collectors.toList()), timestamp));
        Assertions.assertEquals(order1, order2);
    }
}


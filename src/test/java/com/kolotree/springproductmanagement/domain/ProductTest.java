package com.kolotree.springproductmanagement.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private static final String SKU = "ID_123";
    private static final String NAME = "Product X";
    private static final double PRICE = 50;
    private static final LocalDate DATE = LocalDate.now();


    @Test
    public void testCreationWithNullSKUFails() {
        assertThrows(IllegalArgumentException.class, () -> Product.newProductFrom(null, NAME, PRICE));
    }

    @Test
    public void testCreationWithEmptySKUFails() {
        assertThrows(IllegalArgumentException.class, () -> Product.newProductFrom("", NAME, PRICE));
    }

    @Test
    public void testCreationWithNullNameFails() {
        assertThrows(IllegalArgumentException.class, () -> Product.newProductFrom(SKU, null, PRICE));
    }

    @Test
    public void testCreationWithEmptyNameFails() {
        assertThrows(IllegalArgumentException.class, () -> Product.newProductFrom(SKU, "", PRICE));
    }

    @Test
    public void testCreationWithBlankNameFails() {
        assertThrows(IllegalArgumentException.class, () -> Product.newProductFrom(SKU, "   ", PRICE));
    }

    @Test
    public void testCreationWithInvalidPriceFails() {
        assertThrows(IllegalArgumentException.class, () -> Product.newProductFrom(SKU, NAME, -5));
    }

    @Test
    public void testCreationWithValidArgumentsSucceeds() {
        var product = assertDoesNotThrow(() -> Product.newProductFrom(SKU, NAME, PRICE));
        assertEquals(SKU, product.getId().toString());
        assertEquals(NAME, product.getName());
        assertEquals(Price.priceFrom(PRICE), product.getPrice());
        assertEquals(LocalDate.now(), product.getCreatedAt());
    }

    @Test
    public void testUpdateAndEquality() {
        var product1 = assertDoesNotThrow(() -> Product.newProductFrom(SKU, NAME, PRICE));
        var product2 = assertDoesNotThrow(() -> Product.newProductFrom(SKU, NAME, PRICE));

        product1.updateName("Other name").updatePrice(75);
        assertEquals(product1, product2);
        assertEquals("Other name", product1.getName());
        assertEquals(Price.priceFrom(75), product1.getPrice());
    }
}

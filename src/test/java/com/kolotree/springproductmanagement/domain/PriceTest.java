package com.kolotree.springproductmanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    public void testCreationWithNegativeValueFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Price.priceFrom(-0.001));
    }

    @Test
    public void testCreationWithZeroFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Price.priceFrom(0.0001));
    }

    @Test
    public void testCreationWithValidValueSucceeds() {
        Assertions.assertDoesNotThrow(() -> Price.priceFrom(1.52));
    }

    @Test
    public void testEquality() {
        Price price1 = Assertions.assertDoesNotThrow(() -> Price.priceFrom(123));
        Price price2 = Assertions.assertDoesNotThrow(() -> Price.priceFrom(123.0002));
        Price price3 = Assertions.assertDoesNotThrow(() -> Price.priceFrom(150));

        Assertions.assertEquals(price1, price2);
        Assertions.assertNotEquals(price1, price3);
    }
}

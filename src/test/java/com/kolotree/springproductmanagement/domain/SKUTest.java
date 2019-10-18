package com.kolotree.springproductmanagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SKUTest {

    @Test
    public void testCreationWithNullValueFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SKU.stockKeepingUnitFrom(null));
    }

    @Test
    public void testCreationWithEmptyValueFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SKU.stockKeepingUnitFrom(""));
    }

    @Test
    public void testCreationWithBlankValueFails() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SKU.stockKeepingUnitFrom("    "));
    }

    @Test
    public void testCreationWithValidValueSucceeds() {
        SKU sku = Assertions.assertDoesNotThrow(() -> SKU.stockKeepingUnitFrom("ID_123"));
        Assertions.assertEquals("ID_123", sku.toString());
    }

    @Test
    public void testEquality() {
        SKU sku1 = Assertions.assertDoesNotThrow(() -> SKU.stockKeepingUnitFrom("ID_123"));
        SKU sku2 = Assertions.assertDoesNotThrow(() -> SKU.stockKeepingUnitFrom("ID_123"));
        SKU sku3 = Assertions.assertDoesNotThrow(() -> SKU.stockKeepingUnitFrom("ID_456"));

        Assertions.assertEquals(sku1, sku2);
        Assertions.assertNotEquals(sku1, sku3);
    }
}

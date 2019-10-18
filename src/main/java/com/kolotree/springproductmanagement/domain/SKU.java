package com.kolotree.springproductmanagement.domain;

import io.vavr.control.Option;

public class SKU {

    private final String value;

    private SKU(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || !obj.getClass().equals(SKU.class)) return false;
        return value.equals(((SKU)obj).value);
    }

    @Override
    public String toString() {
        return value;
    }

    public static SKU stockKeepingUnitFrom(String value) {
        Option.of(value).filter(s -> !s.isBlank()).getOrElseThrow(() -> new IllegalArgumentException("SKU must be defined"));
        return new SKU(value);
    }
}

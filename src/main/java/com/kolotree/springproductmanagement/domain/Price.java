package com.kolotree.springproductmanagement.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Price implements Comparable<Price> {

    private final BigDecimal value;

    private Price(BigDecimal value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || !obj.getClass().equals(Price.class)) return false;
        return value.equals(((Price)obj).value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public static Price priceFrom(double value) {
        BigDecimal price = new BigDecimal(value).setScale(3, RoundingMode.HALF_EVEN);
        if (price.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Price must be a positive value");
        return new Price(price);
    }

    public double toDouble() {
        return value.doubleValue();
    }

    @Override
    public int compareTo(Price o) {
        return value.compareTo(o.value);
    }
}

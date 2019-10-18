package com.kolotree.springproductmanagement.ports;

import com.kolotree.springproductmanagement.domain.Product;
import com.kolotree.springproductmanagement.domain.SKU;

import java.util.List;

public interface ProductRepository {

    Product save(Product product);

    List<Product> getAll();

    boolean delete(SKU productId);
}

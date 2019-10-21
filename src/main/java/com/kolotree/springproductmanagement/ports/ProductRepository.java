package com.kolotree.springproductmanagement.ports;

import com.kolotree.springproductmanagement.domain.Product;
import com.kolotree.springproductmanagement.domain.SKU;
import io.vavr.control.Option;

import java.util.List;

public interface ProductRepository {

    Product save(Product product);

    List<Product> getAll();

    boolean delete(SKU productId);

    Option<Product> findBy(SKU id);

}

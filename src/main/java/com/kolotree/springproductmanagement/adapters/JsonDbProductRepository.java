package com.kolotree.springproductmanagement.adapters;

import com.kolotree.springproductmanagement.domain.Product;
import com.kolotree.springproductmanagement.domain.SKU;
import com.kolotree.springproductmanagement.ports.ProductRepository;
import io.jsondb.JsonDBTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class JsonDbProductRepository implements ProductRepository {

    private final JsonDBTemplate jsonDBTemplate;

    public JsonDbProductRepository(String dbDirectory) {
        this.jsonDBTemplate = new JsonDBTemplate(
                dbDirectory,
                "com.kolotree.springproductmanagement.adapters"
        );
        if (!jsonDBTemplate.collectionExists(PersistedProduct.class))
            jsonDBTemplate.createCollection(PersistedProduct.class);
    }

    @Override
    public Product save(Product product) {
        try {
            PersistedProduct existingProduct = jsonDBTemplate.findById(product.getId().toString(), PersistedProduct.class);
            if (existingProduct != null && existingProduct.isRemoved())
                throw new IllegalArgumentException("Duplicate id " + product.getId());

            jsonDBTemplate.upsert(PersistedProduct.from(product));
            return product;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Product> getAll() {
        try {
            return jsonDBTemplate.findAll(PersistedProduct.class).stream()
                    .filter(productDto -> !productDto.isRemoved())
                    .map(PersistedProduct::toDomain).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public boolean delete(SKU productId) {
        try {
            PersistedProduct existingProduct = jsonDBTemplate.findById(productId.toString(), PersistedProduct.class);
            if (existingProduct == null || existingProduct.isRemoved()) return false;

            existingProduct.setRemoved(true);
            jsonDBTemplate.upsert(existingProduct);
            return true;
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}

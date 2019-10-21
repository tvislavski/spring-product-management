package com.kolotree.springproductmanagement.adapters;

import com.kolotree.springproductmanagement.domain.Product;
import com.kolotree.springproductmanagement.domain.SKU;
import com.kolotree.springproductmanagement.ports.DatabaseException;
import com.kolotree.springproductmanagement.ports.ProductRepository;
import io.jsondb.JsonDBTemplate;
import io.vavr.control.Option;

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

            if (existingProduct != null) {
                product = existingProduct.toDomain().updateName(product.getName()).updatePrice(product.getPrice().toDouble());
            }

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

    @Override
    public Option<Product> findBy(SKU id) {
        try {
            return Option.of(jsonDBTemplate.findById(id.toString(), PersistedProduct.class))
                    .filter(persistedProduct -> !persistedProduct.isRemoved()).map(PersistedProduct::toDomain);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}

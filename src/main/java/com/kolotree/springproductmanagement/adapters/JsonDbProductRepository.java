package com.kolotree.springproductmanagement.adapters;

import com.kolotree.springproductmanagement.domain.Product;
import com.kolotree.springproductmanagement.domain.SKU;
import com.kolotree.springproductmanagement.dto.ProductDto;
import com.kolotree.springproductmanagement.ports.ProductRepository;
import io.jsondb.JsonDBTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class JsonDbProductRepository implements ProductRepository {

    private final JsonDBTemplate jsonDBTemplate;

    public JsonDbProductRepository(String dbDirectory) {
        this.jsonDBTemplate = new JsonDBTemplate(
                dbDirectory,
                "com.kolotree.springproductmanagement.dto"
        );
        if (!jsonDBTemplate.collectionExists(ProductDto.class))
            jsonDBTemplate.createCollection(ProductDto.class);
    }

    @Override
    public Product save(Product product) {
        try {
            jsonDBTemplate.upsert(ProductDto.from(product));
            return product;
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Product> getAll() {
        try {
            return jsonDBTemplate.findAll(ProductDto.class).stream().map(ProductDto::toDomain).collect(Collectors.toList());
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public boolean delete(SKU productId) {
        try {
            ProductDto productDto = new ProductDto();
            productDto.setSku(productId.toString());
            if (jsonDBTemplate.findById(productId.toString(), ProductDto.class) == null) return false;
            return jsonDBTemplate.remove(productDto, ProductDto.class) != null;
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}

package com.kolotree.springproductmanagement.web;

import com.kolotree.springproductmanagement.domain.SKU;
import com.kolotree.springproductmanagement.dto.ProductDto;
import com.kolotree.springproductmanagement.ports.ProductRepository;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/product")
    @ApiOperation(value = "Returns all products")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    public ResponseEntity<List<ProductDto>> getAll() {
        LOGGER.debug("Executing getAll products...");
        var response = ResponseEntity.ok(productRepository.getAll().stream().map(ProductDto::from).collect(Collectors.toList()));
        LOGGER.debug("Getting all products finished");
        return response;
    }

    @PostMapping("/product")
    @ApiOperation(value = "Saves a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    public ResponseEntity<ProductDto> save(@RequestBody ProductDto productDto) {
        LOGGER.debug("Executing save product {}...", productDto);
        productDto = ProductDto.from(productRepository.save(productDto.toDomain()));
        var response = ResponseEntity.ok(productDto);
        LOGGER.debug("Saving of product {} finished", productDto);
        return response;
    }

    @DeleteMapping("/product/{productId}")
    @ApiOperation(value = "Deletes a product with id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = void.class),
            @ApiResponse(code = 404, message = "Product with given id not found"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    public ResponseEntity delete(@PathVariable String productId) {
        LOGGER.debug("Executing delete product with id {}...", productId);
        boolean deleted = productRepository.delete(SKU.stockKeepingUnitFrom(productId));
        var response = deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        LOGGER.debug("Deleting of product with id {} finished {}", productId, deleted ? "successfully" : "unsuccessfully");
        return response;
    }
}

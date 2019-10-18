package com.kolotree.springproductmanagement.web;

import com.kolotree.springproductmanagement.domain.SKU;
import com.kolotree.springproductmanagement.dto.ProductDto;
import com.kolotree.springproductmanagement.ports.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController(value = "/product")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(productRepository.getAll().stream().map(ProductDto::from).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<ProductDto> save(@RequestBody ProductDto productDto) {
        productDto = ProductDto.from(productRepository.save(productDto.toDomain()));
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity delete(@PathVariable String productId) {
        boolean deleted = productRepository.delete(SKU.stockKeepingUnitFrom(productId));
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @ExceptionHandler({IllegalArgumentException.class, DateTimeParseException.class})
    public ResponseEntity handleBadRequestExceptions(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handle(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sorry, something went wrong");
    }
}

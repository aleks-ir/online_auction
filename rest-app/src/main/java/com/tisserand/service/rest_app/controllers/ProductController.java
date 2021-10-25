package com.tisserand.service.rest_app.controllers;

import com.tisserand.model.Product;
import com.tisserand.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Optional;

@CrossOrigin
@RestController
@Validated
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    public Collection<Product> products() {
        LOGGER.debug("ProductController: findById()");
        return productService.findAll();
    }

    @GetMapping(value = "/products/{id}")
    public ResponseEntity<Product> findById(@PathVariable @Positive(message = "Path variable should be positive") Integer id) {
        LOGGER.debug("ProductController: findById({})", id);
        Optional<Product> optional = productService.findById(id);
        return optional.isPresent()
                ? new ResponseEntity<>(optional.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/products", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> createProduct(@Valid  @RequestBody Product product) {
        LOGGER.debug("ProductController: createProduct({})", product);
        Integer id = productService.create(product);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping(value = "/products", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updatePriceAndCustomer(@RequestBody Product product) {
        LOGGER.debug("ProductController: updatePriceAndCustomer({})", product);
        Integer id = productService.updatePriceAndCustomer(product);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping(value = "/products/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteProduct(@PathVariable @Positive(message = "Path variable should be positive") Integer id) {
        LOGGER.debug("ProductController: deleteProduct({})", id);
        Integer result = productService.delete(id);
        return result > 0
                ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = "/products/count")
    public ResponseEntity<Integer> count() {
        return new ResponseEntity<>(productService.count(), HttpStatus.OK);
    }
}

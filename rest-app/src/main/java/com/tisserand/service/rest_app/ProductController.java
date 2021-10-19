package com.tisserand.service.rest_app;

import com.tisserand.model.Product;
import com.tisserand.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@CrossOrigin
@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    public Collection<Product> products() {
        return productService.findAll();
    }

    @GetMapping(value = "/product/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id) {
        Optional<Product> optional = productService.findById(id);
        return optional.isPresent()
                ? new ResponseEntity<>(optional.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/products", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> createProduct(@RequestBody Product department) {
        Integer id = productService.create(department);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

//    @PutMapping(value = "/products", consumes = {"application/json"}, produces = {"application/json"})
//    public ResponseEntity<Integer> updateDepartment(@RequestBody Product department) {
//        Integer id = departmentService.update(department);
//        return new ResponseEntity<>(id, HttpStatus.OK);
//    }

    @DeleteMapping(value = "/product/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteProduct(@PathVariable Integer id) {
        Integer result = productService.delete(id);
        return result > 0
                ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = "/product/count")
    public ResponseEntity<Integer> count() {
        return new ResponseEntity<>(productService.count(), HttpStatus.OK);
    }
}

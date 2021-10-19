package com.tisserand.service.rest_app;

import com.tisserand.model.dto.ProductDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import com.tisserand.service.ProductDtoService;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@CrossOrigin
@RestController
public class ProductDtoController {
    private ProductDtoService productDtoService;

    public ProductDtoController(ProductDtoService productDtoService) {
        this.productDtoService = productDtoService;
    }

    @GetMapping(value = "/product-dto")
    public Collection<ProductDto> findAllWithAvgSalary() {
        return productDtoService.findAllProductWithNameOwner();
    }
}

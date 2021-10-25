package com.tisserand.service.rest_app.controllers.dto;

import com.tisserand.model.dto.ProductDto;
import com.tisserand.service.rest_app.controllers.DateController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import com.tisserand.service.dto.ProductDtoService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@CrossOrigin
@RestController
public class ProductDtoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDtoController.class);

    private ProductDtoService productDtoService;

    public ProductDtoController(ProductDtoService productDtoService) {
        this.productDtoService = productDtoService;
    }


    @GetMapping(value = "/products-dto")
    public Collection<ProductDto> findProducts(@RequestParam(name = "startDate", required = false)
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                            String startDate,
                                                                    @RequestParam(name = "endDate", required = false)
                                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                            String endDate) {
        LOGGER.debug("ProductDtoController: findProducts({} {})", startDate, endDate);
        if(startDate!=null && endDate!=null) {
            if(startDate.length()!=0 && endDate.length()!=0) {
                return productDtoService.findAllProductWithNameOwnerByDate(startDate, endDate);
            }
        }
        return productDtoService.findAllProductWithNameOwner();
    }
}

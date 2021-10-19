package com.tisserand.service;

import com.tisserand.model.dto.ProductDto;

import java.util.List;

public interface ProductDtoService {
    List<ProductDto> findAllProductWithNameOwner();
}

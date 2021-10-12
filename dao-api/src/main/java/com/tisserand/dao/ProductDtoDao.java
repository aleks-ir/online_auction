package com.tisserand.dao;

import com.tisserand.model.dto.ProductDto;

import java.util.List;

public interface ProductDtoDao {
    List<ProductDto> findAllProductWithNameOwner();
}
